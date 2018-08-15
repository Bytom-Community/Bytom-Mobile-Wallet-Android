package bytom.io.account;

import android.text.TextUtils;

import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import bytom.io.utils.Chainkd;
import bytom.io.utils.common;

public class Manager {

    public static final int maxAccountCache = 1000;
    public static String accountIndexKey     = "AccountIndex";
    public static String accountPrefix       = "Account:";
    public static String aliasPrefix         = "AccountAlias:";
    public static String contractIndexPrefix = "ContractIndex";
    public static String contractPrefix      = "Contract:";
    public static String miningAddressKey    = "MiningAddress";

    //CtrlProgram is structure of account control program
    class CtrlProgram  {
        String AccountID;
        String Address;
        long KeyIndex;
        byte[] ControlProgram;
        boolean Change;// Mark whether this control program is for UTXO change
    }
    //class Manager  {
       Map<String,String> db;
        //chain      *protocol.Chain
        //utxoKeeper *utxoKeeper

        Object cacheMu;
        //cache      *lru.Cache
        //aliasCache *lru.Cache

        Object delayedACPsMu;
        //delayedACPs   map[*txbuilder.TemplateBuilder][]*CtrlProgram

        Lock accIndexMu;
        Lock accountMu;
    //}

    String aliasKey(String name)  {
        return aliasPrefix.concat( name);
    }

    // Key account store prefix
    String Key(String name) {
        return accountPrefix.concat(name);
    }

    // ContractKey account control promgram store prefix
    String ContractKey(String hash ) {
        return contractPrefix.concat(hash);
    }

    String contractIndexKey(String accountID ) {
        return contractIndexPrefix.concat(accountID);
    }
    class Account {
        Signers.Signer signer;
        String ID;//    string `json:"id"`
        String Alias;//  `json:"alias"`
        Account(Signers.Signer sn,String id,String alias){
            signer = sn;
            ID = id;
            Alias = alias;
        }
    }

    public long getNextAccountIndex()  {
        accIndexMu.lock();
        try{
            long nextIndex  = 1;
            if (db.get(accountIndexKey)!= null) {
                //TODO add utf-8
                nextIndex = common.Uint64(db.get(accountIndexKey).getBytes()) + 1;
            }
            db.put(accountIndexKey, common.PutUint64(nextIndex));
            return nextIndex;
        }finally {
            accIndexMu.unlock();
        }
    }


    private long getNextContractIndex(String accountID) {
        accIndexMu.lock();
        try{
            long nextIndex = 1;
            String rawIndexBytes = db.get(contractIndexKey(accountID));
            if (rawIndexBytes != null) {
                //TODO uft-8
                nextIndex = common.Uint64(rawIndexBytes.getBytes()) + 1;
            }
            db.put(contractIndexKey(accountID), common.PutUint64(nextIndex));
            return nextIndex;
        }finally {
            accIndexMu.unlock();
        }
    }
    // Create creates a new Account.
    public Account Create(byte[][]xpubs ,int quorum , String alias ){
        Lock lock = new ReentrantLock();
        lock.lock();
        try{
            String normalizedAlias = alias.replaceAll(" ","").trim().toLowerCase();
            if (TextUtils.isEmpty(db.get(aliasKey(normalizedAlias)))) {
                return null;
            }
            Signers signers = new Signers();
            Signers.Signer signer = signers.Create("account", xpubs, quorum, getNextAccountIndex());
            if (signer == null){
                return null;
            }
            String id = signers.IDGenerate();

            Account account =  new Account(signer, id, normalizedAlias);
            /* TODO
            String accountID = Key(id);
            storeBatch := db.NewBatch();;
            storeBatch.Set(accountID, rawAccount);
            storeBatch.Set(aliasKey(normalizedAlias), []byte(id));
            storeBatch.Write();
            */
            return account;
        } finally{
            lock.unlock();
        }
    }

    // CreateAddress generate an address for the select account
    public CtrlProgram createAddress(Account account, boolean change)  {
        CtrlProgram cp;
        if (account.signer.XPubs.length == 1 ){
            cp = createP2PKH(account, change);
        } else {
            cp = createP2SH(account, change);
        }
        if (cp == null) {
            return null;
        }
        //TODO
        // insertControlPrograms(cp);
        return cp;
    }

    public CtrlProgram createP2PKH(Account account,boolean change) {
        long idx = getNextContractIndex(account.ID);
        byte [][] path = Signers.Path(account.signer, Signers.AccountKeySpace, idx);
        derivedXPubs := Chainkd.DeriveXPubs(account.signer.XPubs, path);
        derivedPK := derivedXPubs[0].PublicKey();
        pubHash := crypto.Ripemd160(derivedPK);

        address, err := common.NewAddressWitnessPubKeyHash(pubHash, &consensus.ActiveNetParams)
        if err != nil {
            return nil, err
        }

        control, err := vmutil.P2WPKHProgram([]byte(pubHash))


        return CtrlProgram{
            AccountID:      account.ID,
                    Address:        address.EncodeAddress(),
                    KeyIndex:       idx,
                    ControlProgram: control,
                    Change:         change,
        };
    }

    public  CtrlProgram createP2SH(Account account,boolean change) {
        long idx = getNextContractIndex(account.ID);
        path := signers.Path(account.Signer, signers.AccountKeySpace, idx)
        derivedXPubs := chainkd.DeriveXPubs(account.XPubs, path)
        derivedPKs := chainkd.XPubKeys(derivedXPubs)
        signScript, err := vmutil.P2SPMultiSigProgram(derivedPKs, account.Quorum)
        if err != nil {
            return nil, err
        }
        scriptHash := crypto.Sha256(signScript)

        address, err := common.NewAddressWitnessScriptHash(scriptHash, &consensus.ActiveNetParams)
        if err != nil {
            return nil, err
        }

        control, err := vmutil.P2WSHProgram(scriptHash)
        if err != nil {
            return nil, err
        }

        return &CtrlProgram{
            AccountID:      account.ID,
                    Address:        address.EncodeAddress(),
                    KeyIndex:       idx,
                    ControlProgram: control,
                    Change:         change,
        };
    }

}
