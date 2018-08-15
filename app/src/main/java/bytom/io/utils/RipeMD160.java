package bytom.io.utils;

import java.util.Arrays;
// RIPEMD160, cryptographic hash functions, 160-bit version;

// hash = Ripemd160.getHash(bytes);

// Ripemd160.getInstance().add(bytes, 0, 10).add(bytes2, 22, 200).getHash(hash, 0);

public class RipeMD160 {
    private static final int K_LEFT[] = {0x00000000, 0x5A827999, 0x6ED9EBA1, 0x8F1BBCDC, 0xA953FD4E};
    private static final int K_RIGHT[] = {0x50A28BE6, 0x5C4DD124, 0x6D703EF3, 0x7A6D76E9, 0x00000000};
    private static final int R_LEFT[][] = {
            {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15},
            {7, 4, 13, 1, 10, 6, 15, 3, 12, 0, 9, 5, 2, 14, 11, 8},
            {3, 10, 14, 4, 9, 15, 8, 1, 2, 7, 0, 6, 13, 11, 5, 12},
            {1, 9, 11, 10, 0, 8, 12, 4, 13, 3, 7, 15, 14, 5, 6, 2},
            {4, 0, 5, 9, 7, 12, 2, 10, 14, 1, 3, 8, 11, 6, 15, 13}};

    private static final int R_RIGHT[][] = {
            {5, 14, 7, 0, 9, 2, 11, 4, 13, 6, 15, 8, 1, 10, 3, 12},
            {6, 11, 3, 7, 0, 13, 5, 10, 14, 15, 8, 12, 4, 9, 1, 2},
            {15, 5, 1, 3, 7, 14, 6, 9, 11, 8, 12, 2, 10, 0, 4, 13},
            {8, 6, 4, 1, 3, 11, 15, 0, 5, 12, 2, 13, 9, 7, 10, 14},
            {12, 15, 10, 4, 1, 5, 8, 7, 6, 2, 13, 14, 0, 3, 9, 11}};
    private static final int S_LEFT[][] = {
            {11, 14, 15, 12, 5, 8, 7, 9, 11, 13, 14, 15, 6, 7, 9, 8},
            {7, 6, 8, 13, 11, 9, 7, 15, 7, 12, 15, 9, 11, 7, 13, 12},
            {11, 13, 6, 7, 14, 9, 13, 15, 14, 8, 13, 6, 5, 12, 7, 5},
            {11, 12, 14, 15, 14, 15, 9, 8, 9, 14, 5, 6, 8, 6, 5, 12},
            {9, 15, 5, 11, 6, 8, 13, 12, 5, 12, 13, 14, 11, 8, 5, 6}};

    private static final int S_RIGHT[][] = {
            {8, 9, 9, 11, 13, 15, 15, 5, 7, 7, 8, 11, 14, 14, 12, 6},
            {9, 13, 15, 7, 12, 8, 9, 11, 7, 7, 12, 7, 6, 15, 13, 11},
            {9, 7, 15, 11, 8, 6, 6, 14, 12, 13, 5, 14, 13, 13, 7, 5},
            {15, 5, 8, 11, 14, 14, 6, 14, 6, 9, 12, 9, 12, 5, 15, 8},
            {8, 5, 12, 9, 12, 5, 14, 6, 8, 13, 6, 5, 15, 13, 11, 11}};

    private int[] h = new int[5]; // Hash, h0...h4, 160 bit;
    private int[] block = new int[16]; // 512-bit block, contain 16 words * 32 bits;
    private int blockN; // Next word in block to be filled;
    private byte[] word = new byte[4]; // 32-bits word, contain 4 words * 8 bits;
    private int wordN; // Next 8-bits word in 32-bits word to be filled;
    private long byteCount; // Message byte length;
    private RipeMD160() {
        reset();
    }

    private static RipeMD160 sInstance = null;
    // Return instance of Ripemd160;
    public static RipeMD160 getInstance() {
        synchronized (RipeMD160.class) {
            if (sInstance == null) {
                sInstance = new RipeMD160();
            }
            return sInstance;
        }
    }
    // Return hash;
    public static byte[] getHash(byte[] bytes) {
        byte[] hash = new byte[20];
        getInstance().add(bytes, 0, bytes.length).getHash(hash, 0);
        return hash;
    }
    // Reset inputs;
    // Return Ripemd160;
    public RipeMD160 reset() {
        h[0] = 0x67452301;
        h[1] = 0xEFCDAB89;
        h[2] = 0x98BADCFE;
        h[3] = 0x10325476;
        h[4] = 0xC3D2E1F0;
        Arrays.fill(block, 0);
        blockN = 0;
        Arrays.fill(word, (byte) 0);
        wordN = 0;
        byteCount = 0;
        return this;
    }
    // Add bytes, from bytes[start] till bytes[start + length];
    // Return Ripemd160;
    public RipeMD160 add(byte[] bytes, int start, int length) {
        int byteN = start;
        // Fill current 32-bits word;
        if (wordN != 0) {
            while (byteN < start + length) {
                word[wordN++] = bytes[byteN++];
                if (wordN == 4) {
                    addWord(word, 0);
                    wordN = 0;
                    break;
                }
            }
        }
        // Add whole words;
        int stop = ((start + length - byteN) & ~0b11) + byteN;
        while (byteN < stop) {
            addWord(bytes, byteN);
            byteN += 4;
        }
        // Fill buffer with rest;
        while (byteN < start + length) {
            word[wordN++] = bytes[byteN++];
        }
        byteCount += length;
        return this;
    }
    // Add hash, 20 bytes, from bytes[start], then reset;
    // Return Ripemd160;

    public RipeMD160 getHash(byte[] bytes, int start) {
        // Add 0b10000000 in the end of message;
        word[wordN++] = (byte) 0b10000000;
        // Fill with 0 and add current 32-bits word;
        while (wordN != 4) {
            word[wordN++] = 0;
        }
        addWord(word, 0);
        // Fill current block with 0 till 448 bit;
        if (blockN == 15) {
            block[15] = 0;
            processBlock();
        }
        for (int i = blockN; i < 14; i++) {
            block[i] = 0;
        }
        // Add length in the end of last block, little endian;
        long bitLength = byteCount << 3;
        block[14] = (int) (bitLength);
        block[15] = (int) (bitLength >>> 32);
        processBlock();
        // Get hash, little endian;
        for (int word : h) {
            bytes[start++] = (byte) word;
            bytes[start++] = (byte) (word >>> 8);
            bytes[start++] = (byte) (word >>> 16);
            bytes[start++] = (byte) (word >>> 24);
        }
        reset();
        return this;
    }
    // Add 32-bits word in block, little endian;
    private void addWord(byte[] word, int start) {
        block[blockN] = (word[start] & 0b11111111) | (word[++start] & 0b11111111) << 8
                | (word[++start] & 0b11111111) << 16 | word[++start] << 24;
        if (++blockN == 16) {
            processBlock();
        }
    }
    private void processBlock() {
        int aLeft = h[0];
        int bLeft = h[1];
        int cLeft = h[2];
        int dLeft = h[3];
        int eLeft = h[4];
        int aRight = h[0];
        int bRight = h[1];
        int cRight = h[2];
        int dRight = h[3];
        int eRight = h[4];
        int temp;
        // 5 rounds * 16 times;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 16; j++) {
                temp = rol((aLeft + f(i, bLeft, cLeft, dLeft) +
                        block[R_LEFT[i][j]] + K_LEFT[i]), S_LEFT[i][j]) + eLeft;
                aLeft = eLeft;
                eLeft = dLeft;
                dLeft = rol(cLeft, 10);
                cLeft = bLeft;
                bLeft = temp;
                temp = rol((aRight + f(4 - i, bRight, cRight, dRight) +
                        block[R_RIGHT[i][j]] + K_RIGHT[i]), S_RIGHT[i][j]) + eRight;
                aRight = eRight;
                eRight = dRight;
                dRight = rol(cRight, 10);
                cRight = bRight;
                bRight = temp;
            }
        }
        temp = h[1] + cLeft + dRight;
        h[1] = h[2] + dLeft + eRight;
        h[2] = h[3] + eLeft + aRight;
        h[3] = h[4] + aLeft + bRight;
        h[4] = h[0] + bLeft + cRight;
        h[0] = temp;
        blockN = 0;
    }
    private int f(int i, int b, int c, int d) {
        switch (i) {
            case 0:
                return b ^ c ^ d;
            case 1:
                return (b & c) | (~b & d);
            case 2:
                return (b | ~c) ^ d;
            case 3:
                return (b & d) | (c & ~d);
            default:
                return b ^ (c | ~d);
        }
    }
    // Cyclic shift x << s;
    private int rol(int x, int s) {
        return (x << s) | (x >>> 32 - s);
    }
}
