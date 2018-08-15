package bytom.io.utils;
// Copyright 2009 The Go Authors. All rights reserved.
// Use of this source code is governed by a BSD-style
// license that can be found in the LICENSE file.

import java.security.MessageDigest;
import java.util.Arrays;

/*
Package hmac implements the Keyed-Hash Message Authentication Code (HMAC) as
defined in U.S. Federal Information Processing Standards Publication 198.
An HMAC is a cryptographic hash that uses a key to sign a message.
The receiver verifies the hash by recomputing it using the same key.

Receivers should be careful to use Equal to compare MACs in order to avoid
timing side-channels:

	// CheckMAC reports whether messageMAC is a valid HMAC tag for message.
	func CheckMAC(message, messageMAC, key []byte) bool {
		mac := hmac.New(sha256.New, key)
		mac.Write(message)
		expectedMAC := mac.Sum(nil)
		return hmac.Equal(messageMAC, expectedMAC)
	}
*/
public class Hmac {

// FIPS 198-1:
// http://csrc.nist.gov/publications/fips/fips198-1/FIPS-198-1_final.pdf

// key is zero padded to the block size of the hash function
// ipad = 0x36 byte repeated for key length
// opad = 0x5c byte repeated for key length
// hmac = H([key ^ opad] H([key ^ ipad] text))
    int size ;
    int blocksize;
    byte[] opad, ipad ;
    MessageDigest outer;
    MessageDigest inner;

    public byte[] Sum(byte[] in ){
        int origLen = in.length;
        in = inner.digest(in);
        outer.reset();
        outer.update(opad);
        outer.update(Arrays.copyOfRange(in,origLen,in.length));
        return outer.digest(Arrays.copyOfRange(in,0,origLen));
    }

    public void Write(byte[] p) {
        inner.update(p);
    }

    private int Size() { return size; }

    private int BlockSize() { return blocksize; }

    public void Reset() {
        inner.reset();
        inner.update(ipad);
    }
    private int BlockSize(MessageDigest digest){
        switch (digest.getAlgorithm()){
            case "MD4":return 16;           // import golang.org/x/crypto/md4
            case "MD5":return 16;           // import crypto/md5
            case "SHA1":return 20;          // import crypto/sha1
            case "SHA224":return 28;        // import crypto/sha256
            case "SHA256":return 32;        // import crypto/sha256
            case "SHA384":return 48;        // import crypto/sha512
            case "SHA512":return 64;        // import crypto/sha512
            case "MD5SHA1":return 36;       // no implementation; MD5+SHA1 used for TLS RSA
            case "RIPEMD160":return 20;     // import golang.org/x/crypto/ripemd160
            case "SHA3_224":return 28;      // import golang.org/x/crypto/sha3
            case "SHA3_256":return 32;      // import golang.org/x/crypto/sha3
            case "SHA3_384":return 48;      // import golang.org/x/crypto/sha3
            case "SHA3_512":return 64;      // import golang.org/x/crypto/sha3
            case "SHA512_224":return 28;    // import crypto/sha512
            case "SHA512_256":return 32;    // import crypto/sha512
            case "BLAKE2s_256":return 32;   // import golang.org/x/crypto/blake2s
            case "BLAKE2b_256":return 32;   // import golang.org/x/crypto/blake2b
            case "BLAKE2b_384":return 48;   // import golang.org/x/crypto/blake2b
            case "BLAKE2b_512":return 64;   // import golang.org/x/crypto/blake2b
            default:return 0;
        }
    }
    // New returns a new HMAC hash using the given hash.Hash type and key.
    // Note that unlike other hash implementations in the standard library,
    // the returned Hash does not implement encoding.BinaryMarshaler
    // or encoding.BinaryUnmarshaler.
    public Hmac(MessageDigest h,byte[] key )  {
        outer = h;
        inner = h;
        size = inner.getDigestLength();
        blocksize = BlockSize(inner);
        ipad = new byte[blocksize];
        opad = new byte[blocksize];
        if (key.length > blocksize) {
            // If key is too big, hash it.
            outer.update(key);
            key = outer.digest();
        }
        ipad = Arrays.copyOf(key,key.length);
        opad = Arrays.copyOf(key,key.length);

        for (int i = 0; i <ipad.length ; i++) {
            ipad[i] ^=0x36;
            opad[i] ^=0x5c;
        }
        inner.update(ipad);
    }

    // Equal compares two MACs for equality without leaking timing information.
    private boolean Equal(byte[] mac1, byte[] mac2 )  {
        // We don't have to be constant time if the lengths of the MACs are
        // different as that suggests that a completely different hash function
        // was used.
        return MessageDigest.isEqual(mac1,mac2);
    }

}
