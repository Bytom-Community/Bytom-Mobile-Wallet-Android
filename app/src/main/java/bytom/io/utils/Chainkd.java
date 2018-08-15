package bytom.io.utils;

import android.graphics.Point;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.Arrays;

public class Chainkd {

    public static byte[][] DeriveXPubs(byte[][] xpubs,byte[][]  path) {
        byte[][] res = new byte[path.length][];
        for(byte[] xpub :  xpubs) {
            byte[] d = Derive(xpub,path);
            res = append(res, d)
        }
        return res;
    }

    private byte[] Derive(byte[] xpub,byte[][] path )  {
        byte[] res = xpub;
        for(byte[] p :  path) {
            res = Child(res,p);
        }
        return res;
    }

    // Child derives a child xpub based on `selector` string.
    // The corresponding child xprv can be derived from the parent xprv
    // using non-hardened derivation: `parentxprv.Child(sel, false)`.
    private byte[] Child(byte[] xpub,byte[] sel ) {
        MessageDigest digest = null;
        byte[] res = new byte[Constant.KEYSIZE];
        try {
            digest = MessageDigest.getInstance("SHA-512");
        }catch (NoSuchAlgorithmException ex){
            ex.printStackTrace();
            return null;
        }
        Hmac h = new Hmac(digest, Arrays.copyOfRange(xpub,32,xpub.length));
        h.Write(new byte[]{'N'});
        h.Write(Arrays.copyOfRange(xpub,0,32));
        h.Write(sel);
        res = h.Sum(res);

        pruneIntermediateScalar(res);
        byte[] f = new byte[32];

        copy(f[:], res[:32])
        F.ScMulBase(&f)

        var (
                pubkey [32]byte
        P      ecmath.Point
	)
        copy(pubkey[:], xpub[:32])
        _, ok := P.Decode(pubkey)
        if !ok {
            panic("XPub should have been validated on initialization")
        }

        P.Add(&P, &F)
        pubkey = P.Encode()
        copy(res[:32], pubkey[:])

        return
    }

    // PublicKey extracts the ed25519 public key from an xpub.
    private  ed25519.PublicKey PublicKey()

    {
        return ed25519.PublicKey(xpub[:32]);
    }

    // s must be >= 32 bytes long and gets rewritten in place.
// This is NOT the same pruning as in Ed25519: it additionally clears the third
// highest bit to ensure subkeys do not overflow the second highest bit.
    private void pruneRootScalar(byte[] s) {
        s[0] &= 248;
        s[31] &= 31; // clear top 3 bits
        s[31] |= 64; // set second highest bit
    }

    // Clears lowest 3 bits and highest 23 bits of `f`.
    private void pruneIntermediateScalar(byte[] f) {
        f[0] &= 248; // clear bottom 3 bits
        f[29] &= 1;  // clear 7 high bits
        f[30] = 0 ;  // clear 8 bits
        f[31] = 0 ;  // clear 8 bits
    }

}
