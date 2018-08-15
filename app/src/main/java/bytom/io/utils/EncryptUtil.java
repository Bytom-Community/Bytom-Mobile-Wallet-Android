package bytom.io.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

public class EncryptUtil {

    // PublicKeySize is the size, in bytes, of public keys as used in this package.
    private static final int PublicKeySize = 32;
    // PrivateKeySize is the size, in bytes, of private keys as used in this package.
    private static final int PrivateKeySize = 64;
    // SignatureSize is the size, in bytes, of signatures generated and verified by this package.
    private static final int SignatureSize = 64;

    public boolean generateKeyPair(byte[] publicKey, byte[] privateKey ) throws Exception {

        byte []privkey = null;
        Edwards25519Util edwards25519Util = new Edwards25519Util();
        Edwards25519Util.ExtendedGroupElement A  = edwards25519Util.new ExtendedGroupElement();

        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");

        random.nextBytes(privkey);
        System.arraycopy(privkey,0,privateKey,0,PublicKeySize);

        byte [] digest = Sha512Util.Encoder(Arrays.copyOf(privateKey, PublicKeySize));
        if(null == digest){
            return false;
        }
        digest[0] &= 248;
        digest[31] &= 127;
        digest[31] |= 64;

        byte []hBytes = Arrays.copyOf(digest, PublicKeySize);
        edwards25519Util.GeScalarMultBase(A,hBytes);

        byte[] publicKeyBytes = new byte[32];
        A.ToBytes(publicKeyBytes);
        System.arraycopy(publicKeyBytes,0,privateKey,PublicKeySize,PublicKeySize);
        System.arraycopy(publicKeyBytes,0,publicKey,0,PublicKeySize);

        return true;
    }

    public static byte[] sign(byte[] privateKey, byte [] message){
        if (null == privateKey){
            throw  new NullPointerException("ed25519: bad private key is null ");
        }
        if (  privateKey.length!= PrivateKeySize) {
            throw  new IllegalArgumentException("ed25519: bad private key length: "+ privateKey.length );
        }

        byte[] expandedSecretKey = new byte[PublicKeySize];
        byte[] messageDigestReduced = new byte[PublicKeySize];
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-512");
        }catch (NoSuchAlgorithmException ex){
            ex.printStackTrace();
            return null;
        }
        digest.update(privateKey,0,PublicKeySize);
        byte[] digest1 = digest.digest();
        System.arraycopy(digest1,0,expandedSecretKey,0,PublicKeySize);
        expandedSecretKey[0] &= 248;
        expandedSecretKey[31] &= 63;
        expandedSecretKey[31] |= 64;
        digest.reset();

        digest.update(digest1,PublicKeySize,PublicKeySize);
        digest.update(message);
        byte[] messageDigest = digest.digest();

        Edwards25519Util edwards25519Util = new Edwards25519Util();
        Edwards25519Util.ExtendedGroupElement R  = edwards25519Util.new ExtendedGroupElement();

        messageDigestReduced = edwards25519Util.ScReduce(messageDigest);
        edwards25519Util.GeScalarMultBase(R,messageDigestReduced);

        byte[] encodedR = new byte[32];
        R.ToBytes(encodedR);

        digest.reset();
        digest.update(encodedR);
        digest.update(privateKey,PublicKeySize,PublicKeySize);
        digest.update(message);
        byte[] hramDigest = digest.digest();

        byte[] hramDigestReduced = edwards25519Util.ScReduce(hramDigest);

        byte[] s = edwards25519Util.multiplyAndAdd(hramDigestReduced, expandedSecretKey, messageDigestReduced);

        byte []signature = new byte[SignatureSize];

        System.arraycopy(encodedR,0, signature,0,PublicKeySize);
        System.arraycopy(s,0,signature,PublicKeySize,PublicKeySize);

        return signature;
    }

    public static boolean verify(byte[] publicKey, byte[] message, byte [] sign){
        if (null == publicKey){
            throw  new NullPointerException("ed25519: bad public key is null ");
        }
        if (publicKey.length!= PublicKeySize) {
            throw  new IllegalArgumentException("ed25519: bad public key length: "+ publicKey.length );
        }
        if (sign.length != SignatureSize || (sign[63] & 224) != 0 ){
            return false;
        }

        Edwards25519Util edwards25519Util = new Edwards25519Util();
        Edwards25519Util.ExtendedGroupElement A  = edwards25519Util.new ExtendedGroupElement();

        byte[] publicKeyBytes = new byte[32];
        System.arraycopy(publicKey,0,publicKeyBytes,0,PublicKeySize);
        if (!A.FromBytes(publicKeyBytes)) {
            return false;
        }
        edwards25519Util.FeNeg(A.X, A.X);
        edwards25519Util.FeNeg(A.T, A.T);

        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-512");
        }catch (NoSuchAlgorithmException ex){
            ex.printStackTrace();
            return false;
        }
        digest.update(sign,0,PublicKeySize);
        digest.update(publicKey);
        digest.update(message);
        byte[] digestmessage = digest.digest();

        byte[] hReduced = edwards25519Util.ScReduce(digestmessage);
        Edwards25519Util.ProjectiveGroupElement R  = edwards25519Util.new ProjectiveGroupElement();

        byte[] b = new byte[32];
        System.arraycopy(sign,32,b, PublicKeySize,PublicKeySize);
        edwards25519Util.GeDoubleScalarMultVartime(R, hReduced, A, b);

        byte[] checkR = new byte[32];
        R.ToBytes(checkR);
        System.arraycopy(sign,32,b, PublicKeySize,PublicKeySize);
        return ConstantTimeCompare(b, checkR) == 1;
    }

    private static int ConstantTimeCompare(byte[]x,byte[] y)  {
        if (x.length !=y.length) {
            return 0;
        }

        byte v = 0;

        for (int i = 0; i < x.length; i++) {
            v |= x[i] ^ y[i];
        }

        return ConstantTimeByteEq(v, (byte)0);
    }

    // ConstantTimeByteEq returns 1 if x == y and 0 otherwise.
    private static int ConstantTimeByteEq(byte x, byte y )  {
        byte z = (byte)(x ^ y);
        z &= z >> 4;
        z &= z >> 2;
        z &= z >> 1;

        return (int)(z);
    }
}
