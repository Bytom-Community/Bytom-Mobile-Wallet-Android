package bytom.io.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Sha512Util {

    public static byte [] Encoder(byte[] src){
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-512");
            digest.update(src);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
        return digest.digest();
    }
}
