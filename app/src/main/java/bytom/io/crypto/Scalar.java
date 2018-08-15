package bytom.io.crypto;

import java.security.MessageDigest;

public class Scalar {
    // Scalar is a 256-bit little-endian scalar.
    private byte[] z = new byte[32];
    private byte[] Zero = new byte[32];
    private byte[] One = new byte[]{
         (byte)0x01, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00,
         (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00,
         (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00,
         (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00,
	};

    // NegOne is the number -1 mod L
    private byte[] NegOne = new byte[]{
        (byte)0xec, (byte)0xd3, (byte)0xf5, (byte)0x5c, (byte)0x1a, (byte)0x63, (byte)0x12, (byte)0x58,
        (byte)0xd6, (byte)0x9c, (byte)0xf7, (byte)0xa2, (byte)0xde, (byte)0xf9, (byte)0xde, (byte)0x14,
        (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00,
        (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x10,
    };

    // L is the subgroup order:
    // 2^252 + 27742317777372353535851937790883648493
    private byte[] L = new byte[]{
        (byte)0xed, (byte)0xd3, (byte)0xf5, (byte)0x5c, (byte)0x1a, (byte)0x63, (byte)0x12, (byte)0x58,
        (byte)0xd6, (byte)0x9c, (byte)0xf7, (byte)0xa2, (byte)0xde, (byte)0xf9, (byte)0xde, (byte)0x14,
        (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00,
        (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x10,
    };

    // Add computes x+y (mod L) and places the result in z, returning
    // that. Any or all of x, y, and z may be the same pointer.
    private byte[] Add(byte[] x, byte[] y ) {
        return MulAdd(x, One, y);
    }

    // Sub computes x-y (mod L) and places the result in z, returning
    // that. Any or all of x, y, and z may be the same pointer.
    private byte[] Sub(byte[] x, byte[] y ) {
        return MulAdd(y, NegOne, x);
    }

    // Neg negates x (mod L) and places the result in z, returning that. X
    // and z may be the same pointer.
    private byte[] Neg(byte[] x) {
        return MulAdd(x, NegOne, Zero);
    }

    // MulAdd computes ab+c (mod L) and places the result in z, returning
    // that. Any or all of the pointers may be the same.
    private byte[] MulAdd(byte[] a, byte[] b, byte[] c) {
        z = Ed25519Util.multiplyAndAdd(a, b, c);
        return z;
    }

    private boolean Equal(byte[]z,byte[] x ) {
        return MessageDigest.isEqual(z,x);
    }

    // Prune performs the pruning operation in-place.
    private void  Prune() {
        z[0] &= 248;
        z[31] &= 127;
        z[31] |= 64;
    }

    // Reduce takes a 512-bit scalar and reduces it mod L, placing the
    // result in z and returning that.
    private byte[] Reduce(byte[]x ) {
        z = Edwards25519Util.ScReduce(x);
        return z;
    }
}
