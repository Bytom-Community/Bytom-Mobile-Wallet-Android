package bytom.io.utils;
// These values are from the public domain, “ref10” implementation of ed25519
// from SUPERCOP.

// d is a constant in the Edwards curve equation.
public class Keystore {
    public static final String TAG = "Keystore";
    public static final int MAX_LENGTH = 1024*1024*1024;
/*
    public class encryptedKeyJSON  {
        cryptoJSON Crypto;  //`json:"crypto"`
        String ID;          // `json:"id"`
        String Type;        // `json:"type"`
        int Version;       //`json:"version"`
        String Alias;       //`json:"alias"`
        String XPub;        //`json:"xpub"`
    }

    public class cryptoJSON {
        String Cipher;                      //`json:"cipher"`
        String CipherText;                 //`json:"ciphertext"`
        String CipherParams;               //`json:"cipherparams"`
        String KDF;                         //`json:"kdf"`
        scryptParamsJSON KDFParams;        // `json:"kdfparams"`
        String MAC;                         //`json:"mac"`
    }

    public class scryptParamsJSON {
        int N;      //`json:"n"`
        int R;      //`json:"r"`
        int P;      //`json:"p"`
        int DkLen; //`json:"dklen"`
        String Salt;//`json:"salt"`
    }

    public static final int version = 1;
    public static final String keytype = "bytom_kd";
    public  Constant.XKey GetKey(String alias, String keystore, String auth ) {
        // Load the key from the keystore and decrypt its contents
//        File file = new File(filename);
//        if(file == null){
//            Log.e(TAG,"open file failed "+ filename);
//            return null;
//        }
//        Long fileLength =  file.length();
//        int JsonSize = 0;
//        if (fileLength > MAX_LENGTH){
//            Log.e(TAG,"file context is too large "+ fileLength);
//            return null;
//        }else{
//            JsonSize = (int)file.length();
//        }
//        byte[] keyjson = new byte[JsonSize];
//        try {
//            FileInputStream in = new FileInputStream(file);
//            in.read(keyjson);
//            in.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//            Log.e(TAG,filename +" is not found ");
//            return null;
//        } catch (IOException e) {
//            e.printStackTrace();
//            Log.e(TAG,"read file is occured error "+ fileLength);
//            return null;
//        }
        Constant.XKey key = null;
        try {
            key = DecryptKey(keystore, auth);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        // Make sure we're really operating on the requested key (no swap attacks)
        if (key.Alias != alias) {
            Log.e(TAG,"key content mismatch: have account " +key.Alias + "but want "+ alias);
            return null;
        }
        return key;
    }


    // DecryptKey decrypts a key from a json blob, returning the private key itself.
    private Constant.XKey DecryptKey( String keyjson, String auth) {
        // Parse the json into a simple map to fetch the key version
        try {
            JsonParser parser = new JsonParser();
            parser.parse(keyjson); // throws JsonSyntaxException
        } catch (JsonSyntaxException ex) {
            Log.e(TAG,"invalid json string");
            return null;
        }

        // Depending on the version try to parse one way or another
        var (
                keyBytes, keyID []byte
        err             error
	    )

        encryptedKeyJSON k = new encryptedKeyJSON();

        if err := json.Unmarshal(keyjson, k); err != nil {
            return nil, err
        }

        keyBytes, keyID, err = decryptKey(k, auth);
        // Handle any decryption errors and return the key
        if err != nil {
            return nil, err
        }
        var xprv chainkd.XPrv
        copy(xprv[:], keyBytes[:])
        xpub := xprv.XPub()

        //key := crypto.ToECDSA(keyBytes)
        return &XKey{
            ID:      uuid.UUID(keyID),
                    XPrv:    xprv,
                    XPub:    xpub,
                    KeyType: k.Type,
                    Alias:   k.Alias,
        }, nil
    }*/
}


