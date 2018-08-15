package bytom.io.account;

import android.util.Log;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

import bytom.io.utils.common;

public class Signers {
    byte AssetKeySpace   = 0;
    static byte AccountKeySpace = 1;
    static String TAG = "Signers";
    // ErrBadQuorum is returned by Create when the quorum
    // provided is less than 1 or greater than the number
    // of xpubs provided.
    final String ErrBadQuorum = "quorum must be greater than 1 and less than or equal to the length of xpubs";

    // ErrBadXPub is returned by Create when the xpub
    // provided isn't valid.
    final String ErrBadXPub = "invalid xpub format";

    // ErrNoXPubs is returned by create when the xpubs
    // slice provided is empty.
    final String ErrNoXPubs = "at least one xpub is required";

    // ErrBadType is returned when a find operation
    // retrieves a signer that is not the expected type.
    final String ErrBadType = "retrieved type does not match expected type";

    // ErrDupeXPub is returned by create when the same xpub
    // appears twice in a single call.
    final String ErrDupeXPub = "xpubs cannot contain the same key more than once";
    // Signer is the abstract concept of a signer,
    // which is composed of a set of keys as well as
    // the amount of signatures needed for quorum.
    class Signer {
        String Type;    //`json:"type"`
        byte[][] XPubs;  //`json:"xpubs"`
        int Quorum;    //`json:"quorum"`
        long KeyIndex; //`json:"key_index"`
        Signer(String type,byte[][] xpubs,int quorum,long keyindex){
            Type = type;
            XPubs=xpubs;
            Quorum=quorum;
            KeyIndex =keyindex;
        }
    }

    // Path returns the complete path for derived keys
    public static byte[][]  Path(Signer s,byte ks , long ... itemIndexes )  {
        byte[][] path=null;
        int i=0;
        byte[] signerPath = new byte[9];
        signerPath[0] = ks;
        System.arraycopy(common.PutUint64byte(s.KeyIndex),0,signerPath,1,8);;
        path[i++] =  signerPath;
        for( long idx : itemIndexes) {
            path[i++] = common.PutUint64byte(idx);
        }
        return path;
    }

    // Create creates and stores a Signer in the database
    public Signer Create(String signerType ,byte[][] xpubs , int quorum ,long keyIndex )  {
        if (xpubs.length == 0){
            Log.e(TAG,ErrNoXPubs);
            return null;
        }

        Arrays.sort(xpubs); // this transforms the input slice
        for (int i = 1; i < xpubs.length; i++) {
            if (Arrays.equals(xpubs[i], xpubs[i-1])) {
                Log.e(TAG,ErrDupeXPub + "duplicated key=" + xpubs[i].toString());
                return null;
            }
        }

        if (quorum == 0 || quorum > xpubs.length){
            Log.e(TAG,ErrBadQuorum);
            return null;
        }

        return new Signer(signerType,xpubs,quorum,keyIndex);
    }

    int seqID ;
    int nextSeqID()  {
        AtomicInteger auto = new AtomicInteger(seqID);
        seqID = auto.getAndIncrement();
        return seqID;
    }

    //IDGenerate generate signer unique id
    String IDGenerate()  {
        long ourEpochMS = 1496635208000l;
        long nowMS = System.currentTimeMillis();
        long seqIndex = (long)(nextSeqID());
        long seqID = (long)(seqIndex % 1024);
        long shardID = (long)(5);

        long n = (nowMS - ourEpochMS) << 23;
        n = n | (shardID << 10);
        n = n | seqID;

        byte []bin = common.PutUint64byte(n);
        return common.encode(bin);

    }
}
