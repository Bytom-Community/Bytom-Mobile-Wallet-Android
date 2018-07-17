package bytom.io.utils;


public class Edwards25519Util {

    private static final int SIZE = 10;
    private static int []zero = new int[SIZE];
    class ProjectiveGroupElement {
        int [] X = new int[SIZE];
        int [] Y = new int[SIZE];
        int [] Z = new int[SIZE];
        public void Double(CompletedGroupElement r) {
            int[] t0 = new int[SIZE];
            FeSquare(r.X,X);
            FeSquare(r.Z,Y);
            FeSquare2(r.T,Z);
            FeAdd(r.Y, X, Y);
            FeSquare(t0, r.Y);
            FeAdd(r.Y, r.Z, r.X);
            FeSub(r.Z, r.Z, r.X);
            FeSub(r.X, t0, r.Y);
            FeSub(r.T, r.T, r.Z);
        }
        public void ToBytes(byte []s ) {
            int [] recip = new int[SIZE];
            int [] x = new int[SIZE];
            int [] y = new int[SIZE];

            FeInvert(recip, Z);
            FeMul(x,X, recip);
            FeMul(y,Y, recip);
            FeToBytes(s, y);
            s[31] ^= FeIsNegative(x) << 7;
        }
        public void Zero() {
            FeZero(X);
            FeOne(Y);
            FeOne(Z);
        }

    }
    static {
        for(int i=0;i<SIZE;i++){
            zero[i] =0;
        }
    }
    class CompletedGroupElement {
        int [] X = new int[SIZE];
        int [] Y = new int[SIZE];
        int [] Z = new int[SIZE];
        int [] T = new int[SIZE];

        public void ToExtended( ExtendedGroupElement r) {
            FeMul(r.X, X,T);
            FeMul(r.Y, Y,Z);
            FeMul(r.Z, Z,T);
            FeMul(r.T, X,Y);
        }
        public void ToProjective(ProjectiveGroupElement r) {
            FeMul(r.X, X, T);
            FeMul(r.Y, Y, Z);
            FeMul(r.Z, Z, T);
        }
    }

    public class ExtendedGroupElement {
        int [] X = new int[SIZE];
        int [] Y = new int[SIZE];
        int [] Z = new int[SIZE];
        int [] T = new int[SIZE];
        public void Double(CompletedGroupElement r) {
            ProjectiveGroupElement q = new ProjectiveGroupElement();
            ToProjective(q);
            q.Double(r);
        }
        public void ToProjective(ProjectiveGroupElement r) {
            System.arraycopy(r.X,0, X,0,SIZE);
            System.arraycopy(r.Y,0, Y,0,SIZE);
            System.arraycopy(r.Z,0, Z,0,SIZE);
        }
        public void ToBytes(byte[] s) {
            int [] recip = new int[SIZE];
            int [] x = new int[SIZE];
            int [] y = new int[SIZE];
            FeInvert(recip, Z);
            FeMul(x,X, recip);
            FeMul(y,Y, recip);
            FeToBytes(s, y);
            s[31] ^= FeIsNegative(x) << 7;
        }

        public boolean FromBytes(byte []s)  {
            int [] u = new int[SIZE];
            int [] v = new int[SIZE];
            int [] v3 = new int[SIZE];
            int [] vxx = new int[SIZE];
            int [] check = new int[SIZE];

            FeFromBytes(Y, s);
            FeOne(Z);
            FeSquare(u, Y);

            FeMul(v, u, Constant.d);
            FeSub(u, u, Z); // y = y^2-1
            FeAdd(v, v, Z); // v = dy^2+1

            FeSquare(v3, v);
            FeMul(v3, v3, v); // v3 = v^3
            FeSquare(X, v3);
            FeMul(X, X, v);
            FeMul(X, X, u); // x = uv^7

            fePow22523(X, X); // x = (uv^7)^((q-5)/8)
            FeMul(X, X, v3);
            FeMul(X, X, u); // x = uv^3(uv^7)^((q-5)/8)

            byte[] tmpX = new byte[32];
            byte[] tmp2 = new byte[32];

            FeSquare(vxx, X);
            FeMul(vxx, vxx, v);
            FeSub(check, vxx, u); // vx^2-u
            if (FeIsNonZero(check) == 1) {
                FeAdd(check, vxx, u); // vx^2+u
                if (FeIsNonZero(check) == 1) {
                    return false;
                }
                FeMul(X, X, Constant.SqrtM1);

                FeToBytes(tmpX, X);
                for ( int i=0;i<tmpX.length;i++) {
                    tmp2[31-i] = tmpX[i];
                }
            }

            if (FeIsNegative(X) != (s[31] >> 7)) {
                FeNeg(X, X);
            }

            FeMul(T, X, Y);
            return true;
        }

        public void ToCached( CachedGroupElement r) {
            FeAdd(r.yPlusX, Y, X);
            FeSub(r.yMinusX, Y, X);
            FeCopy(r.Z,Z);
            FeMul(r.T2d, T, Constant.d2);
        }
    }

    class CachedGroupElement {
        int [] yPlusX = new int[SIZE];
        int [] yMinusX = new int[SIZE];
        int [] Z = new int[SIZE];
        int [] T2d = new int[SIZE];
    }
    private void FeZero(int[]fe){
        System.arraycopy(zero,0,fe,0,SIZE);
    }
    private void FeCopy(int[]dst, int []src ) {
        System.arraycopy(src,0,dst,0,SIZE);
    }
    private void FeOne(int[] fe) {
        FeZero(fe);
        fe[0] = 1;
    }
    private void FeFromBytes(int[]dst , byte[] src ) {
        long []h0 = new long[SIZE];
        h0[0]= load_4(src,0);
        h0[1]= load_3(src,4) << 6;
        h0[2]= load_3(src,7 ) << 5;
        h0[3]= load_3(src,10) << 3;
        h0[4]= load_3(src,13) << 2;
        h0[5]= load_3(src,16);
        h0[6]= load_3(src,20) << 7;
        h0[7]= load_3(src,23) << 5;
        h0[8]= load_3(src,26) << 4;
        h0[9]= (load_3(src,29) & 8388607) << 2;

        FeCombine(dst, h0[0], h0[1], h0[2], h0[3], h0[4], h0[5], h0[6], h0[7], h0[8], h0[9]);
    }

    private void fePow22523(int[] out, int[] z ) {
        int [] t0 = new int[SIZE];
        int [] t1 = new int[SIZE];
        int [] t2 = new int[SIZE];
        int i =0;

        FeSquare(t0, z);
        for( i = 1; i < 1; i++) {
            FeSquare(t0, t0);
        }
        FeSquare(t1, t0);
        for( i = 1; i < 2; i++) {
            FeSquare(t1, t1);
        }
        FeMul(t1, z, t1);
        FeMul(t0, t0, t1);
        FeSquare(t0, t0);
        for (i = 1; i < 1; i++) {
            FeSquare(t0, t0);
        }
        FeMul(t0, t1, t0);
        FeSquare(t1, t0);
        for (i = 1; i < 5; i++) {
            FeSquare(t1, t1);
        }
        FeMul(t0, t1, t0);
        FeSquare(t1, t0);
        for (i = 1; i < 10; i++) {
            FeSquare(t1, t1);
        }
        FeMul(t1, t1, t0);
        FeSquare(t2, t1);
        for (i = 1; i < 20; i++) {
            FeSquare(t2, t2);
        }
        FeMul(t1, t2, t1);
        FeSquare(t1, t1);
        for (i = 1; i < 10; i++) {
            FeSquare(t1, t1);
        }
        FeMul(t0, t1, t0);
        FeSquare(t1, t0);
        for(i = 1; i < 50; i++ ){
            FeSquare(t1, t1);
        }
        FeMul(t1, t1, t0);
        FeSquare(t2, t1);
        for (i = 1; i < 100; i++) {
            FeSquare(t2, t2);
        }
        FeMul(t1, t2, t1);
        FeSquare(t1, t1);
        for (i = 1; i < 50; i++) {
            FeSquare(t1, t1);
        }
        FeMul(t0, t1, t0);
        FeSquare(t0,t0);
        for (i = 1; i < 2; i++) {
            FeSquare(t0, t0);
        }
        FeMul(out, t0, z);
    }

    public short getUint8(short s){
        return (short)( s & 0x00ff);
    }

    private int FeIsNonZero(int[] f )  {
        byte[] s = new byte[32];
        FeToBytes(s, f);
        short x = 0;
        for (int i = 0; i < 32; i++) {
            x = getUint8( (short)(x|s[i]));
        }
        x |= x >> 4;
        x |= x >> 2;
        x |= x >> 1;
        return (x & 1);
    }
    private byte FeIsNegative(int [] f )  {
        byte[] s  = new byte[32];
        FeToBytes(s, f);
        return (byte)(s[0] & 1);
    }
    public void GeScalarMultBase(ExtendedGroupElement h,byte[]a ){
        byte[] e = new byte[64];

        for(int i=0;i<a.length;i++){
            e[2*i] = (byte)(a[i] & 15);
            e[2*i+1] = (byte)((a[i] >> 4) & 15);
        }

        // each e[i] is between 0 and 15 and e[63] is between 0 and 7.
        byte carry = 0;
        for (int i = 0; i < 63; i++ ){
            e[i] += carry;
            carry = (byte)((e[i] + 8) >> 4);
            e[i] -= carry << 4;
        }
        e[63] += carry;
        // each e[i] is between -8 and 8.
        PreComputedGroupElement t = new PreComputedGroupElement();
        CompletedGroupElement r = new CompletedGroupElement();
        for (int i = 1; i < 64; i += 2 ){
            selectPoint(t, i/2, (int)(e[i]));
            geMixedAdd(r, h, t);
            r.ToExtended(h);
        }

        ProjectiveGroupElement s= new ProjectiveGroupElement();

        h.Double(r);
        r.ToProjective(s);
        s.Double(r);
        r.ToProjective(s);
        s.Double(r);
        r.ToProjective(s);
        s.Double(r);
        r.ToExtended(h);

        for (int i = 0; i < 64; i += 2 ){
            selectPoint(t, i/2, (int)(e[i]));
            geMixedAdd(r, h, t);
            r.ToExtended(h);
        }
    }
    private static long getUint32(long l){
        return l & 0x00000000ffffffff;
    }

    private static int equal(int b, int c)  {
        long x = getUint32(b ^ c);
        x--;
        return (int)(x >> 31);
    }

    private static int negative(int b){
        return (b >> 31) & 1;
    }
    private static void FeCMove(int []f, int []g , int b) {
        b = -b;
        f[0] ^= b & (f[0] ^ g[0]);
        f[1] ^= b & (f[1] ^ g[1]);
        f[2] ^= b & (f[2] ^ g[2]);
        f[3] ^= b & (f[3] ^ g[3]);
        f[4] ^= b & (f[4] ^ g[4]);
        f[5] ^= b & (f[5] ^ g[5]);
        f[6] ^= b & (f[6] ^ g[6]);
        f[7] ^= b & (f[7] ^ g[7]);
        f[8] ^= b & (f[8] ^ g[8]);
        f[9] ^= b & (f[9] ^ g[9]);
    }
    public static void FeNeg(int []h,int[] f ) {
        h[0] = -f[0];
        h[1] = -f[1];
        h[2] = -f[2];
        h[3] = -f[3];
        h[4] = -f[4];
        h[5] = -f[5];
        h[6] = -f[6];
        h[7] = -f[7];
        h[8] = -f[8];
        h[9] = -f[9];
    }
    private static void FeAdd(int[] dst, int[] a, int[] b ) {
        dst[0] = a[0] + b[0];
        dst[1] = a[1] + b[1];
        dst[2] = a[2] + b[2];
        dst[3] = a[3] + b[3];
        dst[4] = a[4] + b[4];
        dst[5] = a[5] + b[5];
        dst[6] = a[6] + b[6];
        dst[7] = a[7] + b[7];
        dst[8] = a[8] + b[8];
        dst[9] = a[9] + b[9];
    }
    private static void FeSub(int[] dst, int[] a, int[] b ) {
        dst[0] = a[0] - b[0];
        dst[1] = a[1] - b[1];
        dst[2] = a[2] - b[2];
        dst[3] = a[3] - b[3];
        dst[4] = a[4] - b[4];
        dst[5] = a[5] - b[5];
        dst[6] = a[6] - b[6];
        dst[7] = a[7] - b[7];
        dst[8] = a[8] - b[8];
        dst[9] = a[9] - b[9];
    }
    private static void FeSquare(int[] h, int []f ) {
        long [] h0 = feSquare(f);
        FeCombine(h, h0[0], h0[1], h0[2], h0[3], h0[4], h0[5], h0[6], h0[7], h0[8], h0[9]);
    }
    private static void FeSquare2(int []h, int[]f ) {
        long []h0 = feSquare(f);

        h0[0] += h0[0];
        h0[1] += h0[1];
        h0[2] += h0[2];
        h0[3] += h0[3];
        h0[4] += h0[4];
        h0[5] += h0[5];
        h0[6] += h0[6];
        h0[7] += h0[7];
        h0[8] += h0[8];
        h0[9] += h0[9];

        FeCombine(h, h0[0], h0[1], h0[2], h0[3], h0[4], h0[5], h0[6], h0[7], h0[8], h0[9]);
    }

    private void FeToBytes(byte []s , int []h ) {
        int [] carry = new int[SIZE];

        int q = (19*h[9] + (1 << 24)) >> 25;
        q = (h[0] + q) >> 26;
        q = (h[1] + q) >> 25;
        q = (h[2] + q) >> 26;
        q = (h[3] + q) >> 25;
        q = (h[4] + q) >> 26;
        q = (h[5] + q) >> 25;
        q = (h[6] + q) >> 26;
        q = (h[7] + q) >> 25;
        q = (h[8] + q) >> 26;
        q = (h[9] + q) >> 25;

        // Goal: Output h-(2^255-19)q, which is between 0 and 2^255-20.
        h[0] += 19 * q;
        // Goal: Output h-2^255 q, which is between 0 and 2^255-20.

        carry[0] = h[0] >> 26;
        h[1] += carry[0];
        h[0] -= carry[0] << 26;
        carry[1] = h[1] >> 25;
        h[2] += carry[1];
        h[1] -= carry[1] << 25;
        carry[2] = h[2] >> 26;
        h[3] += carry[2];
        h[2] -= carry[2] << 26;
        carry[3] = h[3] >> 25;
        h[4] += carry[3];
        h[3] -= carry[3] << 25;
        carry[4] = h[4] >> 26;
        h[5] += carry[4];
        h[4] -= carry[4] << 26;
        carry[5] = h[5] >> 25;
        h[6] += carry[5];
        h[5] -= carry[5] << 25;
        carry[6] = h[6] >> 26;
        h[7] += carry[6];
        h[6] -= carry[6] << 26;
        carry[7] = h[7] >> 25;
        h[8] += carry[7];
        h[7] -= carry[7] << 25;
        carry[8] = h[8] >> 26;
        h[9] += carry[8];
        h[8] -= carry[8] << 26;
        carry[9] = h[9] >> 25;
        h[9] -= carry[9] << 25;
        // h10 = carry9

        // Goal: Output h[0]+...+2^255 h10-2^255 q, which is between 0 and 2^255-20.
        // Have h[0]+...+2^230 h[9] between 0 and 2^255-1;
        // evidently 2^255 h10-2^255 q = 0.
        // Goal: Output h[0]+...+2^230 h[9].

        s[0] = (byte)(h[0] >> 0);
        s[1] = (byte)(h[0] >> 8);
        s[2] = (byte)(h[0] >> 16);
        s[3] = (byte)((h[0] >> 24) | (h[1] << 2));
        s[4] = (byte)(h[1] >> 6);
        s[5] = (byte)(h[1] >> 14);
        s[6] = (byte)((h[1] >> 22) | (h[2] << 3));
        s[7] = (byte)(h[2] >> 5);
        s[8] = (byte)(h[2] >> 13);
        s[9] = (byte)((h[2] >> 21) | (h[3] << 5));
        s[10] = (byte)(h[3] >> 3);
        s[11] = (byte)(h[3] >> 11);
        s[12] = (byte)((h[3] >> 19) | (h[4] << 6));
        s[13] = (byte)(h[4] >> 2);
        s[14] = (byte)(h[4] >> 10);
        s[15] = (byte)(h[4] >> 18);
        s[16] = (byte)(h[5] >> 0);
        s[17] = (byte)(h[5] >> 8);
        s[18] = (byte)(h[5] >> 16);
        s[19] = (byte)((h[5] >> 24) | (h[6] << 1));
        s[20] = (byte)(h[6] >> 7);
        s[21] = (byte)(h[6] >> 15);
        s[22] = (byte)((h[6] >> 23) | (h[7] << 3));
        s[23] = (byte)(h[7] >> 5);
        s[24] = (byte)(h[7] >> 13);
        s[25] = (byte)((h[7] >> 21) | (h[8] << 4));
        s[26] = (byte)(h[8] >> 4);
        s[27] = (byte)(h[8] >> 12);
        s[28] = (byte)((h[8] >> 20) | (h[9] << 6));
        s[29] = (byte)(h[9] >> 2);
        s[30] = (byte)(h[9] >> 10);
        s[31] = (byte)(h[9] >> 18);
    }
    private static void FeCombine(int []h , long h0, long h1, long h2, long h3, long h4, long h5, long h6, long h7, long h8, long h9 ) {
        long c0, c1, c2, c3, c4, c5, c6, c7, c8, c9 ;

	/*
	  |h0| <= (1.1*1.1*2^52*(1+19+19+19+19)+1.1*1.1*2^50*(38+38+38+38+38))
	    i.e. |h0| <= 1.2*2^59; narrower ranges for h2, h4, h6, h8
	  |h1| <= (1.1*1.1*2^51*(1+1+19+19+19+19+19+19+19+19))
	    i.e. |h1| <= 1.5*2^58; narrower ranges for h3, h5, h7, h9
	*/

        c0 = (h0 + (1 << 25)) >> 26;
        h1 += c0;
        h0 -= c0 << 26;
        c4 = (h4 + (1 << 25)) >> 26;
        h5 += c4;
        h4 -= c4 << 26;
        /* |h0| <= 2^25 */
        /* |h4| <= 2^25 */
        /* |h1| <= 1.51*2^58 */
        /* |h5| <= 1.51*2^58 */

        c1 = (h1 + (1 << 24)) >> 25;
        h2 += c1;
        h1 -= c1 << 25;
        c5 = (h5 + (1 << 24)) >> 25;
        h6 += c5;
        h5 -= c5 << 25;
        /* |h1| <= 2^24; from now on fits into int32 */
        /* |h5| <= 2^24; from now on fits into int32 */
        /* |h2| <= 1.21*2^59 */
        /* |h6| <= 1.21*2^59 */

        c2 = (h2 + (1 << 25)) >> 26;
        h3 += c2;
        h2 -= c2 << 26;
        c6 = (h6 + (1 << 25)) >> 26;
        h7 += c6;
        h6 -= c6 << 26;
        /* |h2| <= 2^25; from now on fits into int32 unchanged */
        /* |h6| <= 2^25; from now on fits into int32 unchanged */
        /* |h3| <= 1.51*2^58 */
        /* |h7| <= 1.51*2^58 */

        c3 = (h3 + (1 << 24)) >> 25;
        h4 += c3;
        h3 -= c3 << 25;
        c7 = (h7 + (1 << 24)) >> 25;
        h8 += c7;
        h7 -= c7 << 25;
        /* |h3| <= 2^24; from now on fits into int32 unchanged */
        /* |h7| <= 2^24; from now on fits into int32 unchanged */
        /* |h4| <= 1.52*2^33 */
        /* |h8| <= 1.52*2^33 */

        c4 = (h4 + (1 << 25)) >> 26;
        h5 += c4;
        h4 -= c4 << 26;
        c8 = (h8 + (1 << 25)) >> 26;
        h9 += c8;
        h8 -= c8 << 26;
        /* |h4| <= 2^25; from now on fits into int32 unchanged */
        /* |h8| <= 2^25; from now on fits into int32 unchanged */
        /* |h5| <= 1.01*2^24 */
        /* |h9| <= 1.51*2^58 */

        c9 = (h9 + (1 << 24)) >> 25;
        h0 += c9 * 19;
        h9 -= c9 << 25;
        /* |h9| <= 2^24; from now on fits into int32 unchanged */
        /* |h0| <= 1.8*2^37 */

        c0 = (h0 + (1 << 25)) >> 26;
        h1 += c0;
        h0 -= c0 << 26;
        /* |h0| <= 2^25; from now on fits into int32 unchanged */
        /* |h1| <= 1.01*2^24 */

        h[0] = (int)(h0);
        h[1] = (int)(h1);
        h[2] = (int)(h2);
        h[3] = (int)(h3);
        h[4] = (int)(h4);
        h[5] = (int)(h5);
        h[6] = (int)(h6);
        h[7] = (int)(h7);
        h[8] = (int)(h8);
        h[9] = (int)(h9);
    }
    private static void  FeMul(int[] h, int[] f, int[] g) {
        long f0 = (long) (f[0]);
        long f1 = (long) (f[1]);
        long f2 = (long) (f[2]);
        long f3 = (long) (f[3]);
        long f4 = (long) (f[4]);
        long f5 = (long) (f[5]);
        long f6 = (long) (f[6]);
        long f7 = (long) (f[7]);
        long f8 = (long) (f[8]);
        long f9 = (long) (f[9]);

        long f1_2 = (long) (2 * f[1]);
        long f3_2 = (long) (2 * f[3]);
        long f5_2 = (long) (2 * f[5]);
        long f7_2 = (long) (2 * f[7]);
        long f9_2 = (long) (2 * f[9]);

        long g0 = (long) (g[0]);
        long g1 = (long) (g[1]);
        long g2 = (long) (g[2]);
        long g3 = (long) (g[3]);
        long g4 = (long) (g[4]);
        long g5 = (long) (g[5]);
        long g6 = (long) (g[6]);
        long g7 = (long) (g[7]);
        long g8 = (long) (g[8]);
        long g9 = (long) (g[9]);

        long g1_19= (long) (19 * g[1]); /* 1.4*2^29 */
        long g2_19= (long) (19 * g[2]); /* 1.4*2^30; still ok */
        long g3_19= (long) (19 * g[3]);
        long g4_19= (long) (19 * g[4]);
        long g5_19= (long) (19 * g[5]);
        long g6_19= (long) (19 * g[6]);
        long g7_19= (long) (19 * g[7]);
        long g8_19= (long) (19 * g[8]);
        long g9_19= (long) (19 * g[9]);

        long h0 = f0*g0 + f1_2*g9_19 + f2*g8_19 + f3_2*g7_19 + f4*g6_19 + f5_2*g5_19 + f6*g4_19 + f7_2*g3_19 + f8*g2_19 + f9_2*g1_19;
        long h1 = f0*g1 + f1*g0 + f2*g9_19 + f3*g8_19 + f4*g7_19 + f5*g6_19 + f6*g5_19 + f7*g4_19 + f8*g3_19 + f9*g2_19;
        long h2 = f0*g2 + f1_2*g1 + f2*g0 + f3_2*g9_19 + f4*g8_19 + f5_2*g7_19 + f6*g6_19 + f7_2*g5_19 + f8*g4_19 + f9_2*g3_19;
        long h3 = f0*g3 + f1*g2 + f2*g1 + f3*g0 + f4*g9_19 + f5*g8_19 + f6*g7_19 + f7*g6_19 + f8*g5_19 + f9*g4_19;
        long h4 = f0*g4 + f1_2*g3 + f2*g2 + f3_2*g1 + f4*g0 + f5_2*g9_19 + f6*g8_19 + f7_2*g7_19 + f8*g6_19 + f9_2*g5_19;
        long h5 = f0*g5 + f1*g4 + f2*g3 + f3*g2 + f4*g1 + f5*g0 + f6*g9_19 + f7*g8_19 + f8*g7_19 + f9*g6_19;
        long h6 = f0*g6 + f1_2*g5 + f2*g4 + f3_2*g3 + f4*g2 + f5_2*g1 + f6*g0 + f7_2*g9_19 + f8*g8_19 + f9_2*g7_19;
        long h7 = f0*g7 + f1*g6 + f2*g5 + f3*g4 + f4*g3 + f5*g2 + f6*g1 + f7*g0 + f8*g9_19 + f9*g8_19;
        long h8 = f0*g8 + f1_2*g7 + f2*g6 + f3_2*g5 + f4*g4 + f5_2*g3 + f6*g2 + f7_2*g1 + f8*g0 + f9_2*g9_19;
        long h9 = f0*g9 + f1*g8 + f2*g7 + f3*g6 + f4*g5 + f5*g4 + f6*g3 + f7*g2 + f8*g1 + f9*g0;

        FeCombine(h, h0, h1, h2, h3, h4, h5, h6, h7, h8, h9);
    }
    private void FeInvert(int []out, int [] z ) {
        int[] t0 = new int[SIZE];
        int[] t1 = new int[SIZE];
        int[] t2 = new int[SIZE];
        int[] t3 = new int[SIZE];
        int i = 0;

        FeSquare(t0, z);        // 2^1
        FeSquare(t1, t0);      // 2^2
        for( i = 1; i < 2; i++) { // 2^3
            FeSquare(t1, t1);
        }
        FeMul(t1, z, t1);     // 2^3 + 2^0
        FeMul(t0, t0, t1);    // 2^3 + 2^1 + 2^0
        FeSquare(t2, t0);     // 2^4 + 2^2 + 2^1
        FeMul(t1, t1, t2);    // 2^4 + 2^3 + 2^2 + 2^1 + 2^0
        FeSquare(t2, t1);      // 5,4,3,2,1
        for( i = 1; i < 5; i++) { // 9,8,7,6,5
            FeSquare(t2, t2);
        }
        FeMul(t1,t2, t1);     // 9,8,7,6,5,4,3,2,1,0
        FeSquare(t2, t1);       // 10..1
        for (i = 1; i < 10; i++) { // 19..10
            FeSquare(t2, t2);
        }
        FeMul(t2, t2, t1);     // 19..0
        FeSquare(t3, t2);       // 20..1
        for (i = 1; i < 20; i++) { // 39..20
            FeSquare(t3, t3);
        }
        FeMul(t2, t3, t2);   // 39..0
        FeSquare(t2, t2);       // 40..1
        for (i = 1; i < 10; i++) { // 49..10
            FeSquare(t2, t2);
        }
        FeMul(t1, t2, t1);     // 49..0
        FeSquare(t2, t1);       // 50..1
        for (i = 1; i < 50; i++) { // 99..50
            FeSquare(t2, t2);
        }
        FeMul(t2, t2, t1);      // 99..0
        FeSquare(t3, t2);        // 100..1
        for (i = 1; i < 100; i++) { // 199..100
            FeSquare(t3, t3);
        }
        FeMul(t2, t3, t2);     // 199..0
        FeSquare(t2, t2);       // 200..1
        for (i = 1; i < 50; i++) { // 249..50
            FeSquare(t2, t2);
        }
        FeMul(t1, t2, t1);    // 249..0
        FeSquare(t1, t1);      // 250..1
        for (i = 1; i < 5; i++ ){ // 254..5
            FeSquare(t1, t1);
        }
        FeMul(out, t1, t0); // 254..5,3,1,0
    }
    private static long[] feSquare(int[] f) {
        long[] f0 = new long[10];
        System.arraycopy(f0,0,f,0,SIZE);
        long f0_2 =(long)(2 * f[0]);
        long f1_2 =(long)(2 * f[1]);
        long f2_2 =(long)(2 * f[2]);
        long f3_2 =(long)(2 * f[3]);
        long f4_2 =(long)(2 * f[4]);
        long f5_2 =(long)(2 * f[5]);
        long f6_2 =(long)(2 * f[6]);
        long f7_2 =(long)(2 * f[7]);
        long f5_38 = 38 * f0[5]; // 1.31*2^30
        long f6_19 = 19 * f0[6]; // 1.31*2^30
        long f7_38 = 38 * f0[7]; // 1.31*2^30
        long f8_19 = 19 * f0[8]; // 1.31*2^30
        long f9_38 = 38 * f0[9]; // 1.31*2^30

        long [] h0 = new long[SIZE];

        h0[0] = f0[0]*f0[0] + f1_2*f9_38 + f2_2*f8_19 + f3_2*f7_38 + f4_2*f6_19 + f0[5]*f5_38;
        h0[1] = f0_2*f0[1] + f0[2]*f9_38 + f3_2*f8_19 + f0[4]*f7_38 + f5_2*f6_19;
        h0[2] = f0_2*f0[2] + f1_2*f0[1] + f3_2*f9_38 + f4_2*f8_19 + f5_2*f7_38 + f0[6]*f6_19;
        h0[3] = f0_2*f0[3] + f1_2*f0[2] + f0[4]*f9_38 + f5_2*f8_19 + f0[6]*f7_38;
        h0[4] = f0_2*f0[4] + f1_2*f3_2 + f0[2]*f0[2] + f5_2*f9_38 + f6_2*f8_19 + f0[7]*f7_38;
        h0[5] = f0_2*f0[5] + f1_2*f0[4] + f2_2*f0[3] + f0[6]*f9_38 + f7_2*f8_19;
        h0[6] = f0_2*f0[6] + f1_2*f5_2 + f2_2*f0[4] + f3_2*f0[3] + f7_2*f9_38 + f0[8]*f8_19;
        h0[7] = f0_2*f0[7] + f1_2*f0[6] + f2_2*f0[5] + f3_2*f0[4] + f0[8]*f9_38;
        h0[8] = f0_2*f0[8] + f1_2*f7_2 + f2_2*f0[6] + f3_2*f5_2 + f0[4]*f0[4] + f0[9]*f9_38;
        h0[9] = f0_2*f0[9] + f1_2*f0[8] + f2_2*f0[7] + f3_2*f0[6] + f4_2*f0[5];

        return h0;
    }
    private static void PreComputedGroupElementCMove(PreComputedGroupElement t, PreComputedGroupElement u , int b) {
        FeCMove(t.yPlusX,u.yPlusX, b);
        FeCMove(t.yMinusX, u.yMinusX, b);
        FeCMove(t.xy2d, u.xy2d, b);
    }
    private void selectPoint(PreComputedGroupElement t, int pos , int b ) {
        PreComputedGroupElement minusT = new PreComputedGroupElement();
        int bNegative = negative(b);
        int bAbs = b - (((-bNegative) & b) << 1);

        FeOne(t.yPlusX);
        FeOne(t.yMinusX);
        FeZero(t.xy2d);
        for ( int i = 0; i < 8; i++) {
            //TODO
            PreComputedGroupElementCMove(t, Constant.base[pos][i], equal(bAbs, i+1));
        }
        System.arraycopy(minusT.yPlusX,0, t.yMinusX,0,SIZE);
        System.arraycopy(minusT.yMinusX, 0,t.yPlusX,0,SIZE);
        FeNeg(minusT.xy2d, t.xy2d);
        PreComputedGroupElementCMove(t, minusT, bNegative);
    }
    private void geMixedAdd(CompletedGroupElement r, ExtendedGroupElement p, PreComputedGroupElement q) {
        int[] t0 =new int[SIZE];

        FeAdd(r.X, p.Y, p.X);
        FeSub(r.Y, p.Y, p.X);
        FeMul(r.Z, r.X, q.yPlusX);
        FeMul(r.Y, r.Y, q.yMinusX);
        FeMul(r.T, q.xy2d,p.T);
        FeAdd(t0,  p.Z, p.Z);
        FeSub(r.X, r.Z, r.Y);
        FeAdd(r.Y, r.Z, r.Y);
        FeAdd(r.Z, t0, r.T);
        FeSub(r.T, t0, r.T);
    }
    private void geSub(CompletedGroupElement r, ExtendedGroupElement p, CachedGroupElement q) {
        int []t0 = new int[SIZE];

        FeAdd(r.X, p.Y, p.X);
        FeSub(r.Y, p.Y, p.X);
        FeMul(r.Z, r.X, q.yMinusX);
        FeMul(r.Y, r.Y, q.yPlusX);
        FeMul(r.T, q.T2d, p.T);
        FeMul(r.X, p.Z, q.Z);
        FeAdd(t0,  r.X, r.X);
        FeSub(r.X, r.Z, r.Y);
        FeAdd(r.Y, r.Z, r.Y);
        FeSub(r.Z, t0, r.T);
        FeAdd(r.T, t0, r.T);
    }
    private void geAdd(CompletedGroupElement r, ExtendedGroupElement p, CachedGroupElement q) {
        int []t0 = new int[SIZE];

        FeAdd(r.X, p.Y, p.X);
        FeSub(r.Y, p.Y, p.X);
        FeMul(r.Z, r.X, q.yPlusX);
        FeMul(r.Y, r.Y, q.yMinusX);
        FeMul(r.T, q.T2d, p.T);
        FeMul(r.X, p.Z, q.Z);
        FeAdd(t0,  r.X, r.X);
        FeSub(r.X, r.Z, r.Y);
        FeAdd(r.Y, r.Z, r.Y);
        FeAdd(r.Z, t0, r.T);
        FeSub(r.T, t0, r.T);
    }
    private void slide(byte []r,  byte[] a) {
        for(int i =0;i<256;i++) {
            r[i] = (byte)(1 & (a[i>>3] >> (byte)(i&7)));
        }

        for (int i =0;i<256;i++) {
            if (r[i] != 0) {
                for( int b = 1; b <= 6 && i+b < 256; b++) {
                    if (r[i+b] != 0) {
                        if (r[i]+(r[i+b]<<(short)(b)) <= 15) {
                            r[i] += r[i+b] << (short)(b);
                            r[i+b] = 0;
                        } else if (r[i]-(r[i+b]<<(short)(b)) >= -15) {
                            r[i] -= r[i+b] << (short)(b);
                            for (int k = i + b; k < 256; k++) {
                                if (r[k] == 0) {
                                    r[k] = 1;
                                    break;
                                }
                                r[k] = 0;
                            }
                        } else {
                            break;
                        }
                    }
                }
            }
        }
    }
    private static int load_3(byte[] in, int offset) {

        int result = in[offset++] & 0xff;

        result |= (in[offset++] & 0xff) << 8;

        result |= (in[offset] & 0xff) << 16;

        return result;

    }
    private void geMixedSub(CompletedGroupElement r, ExtendedGroupElement p, PreComputedGroupElement q) {
        int []t0 = new int[SIZE];

        FeAdd(r.X, p.Y, p.X);
        FeSub(r.Y, p.Y, p.X);
        FeMul(r.Z, r.X, q.yMinusX);
        FeMul(r.Y, r.Y, q.yPlusX);
        FeMul(r.T, q.xy2d, p.T);
        FeAdd(t0, p.Z, p.Z);
        FeSub(r.X, r.Z, r.Y);
        FeAdd(r.Y, r.Z, r.Y);
        FeSub(r.Z, t0, r.T);
        FeAdd(r.T, t0, r.T);
    }
    private static long load_4(byte[] in, int offset) {

        int result = in[offset++] & 0xff;

        result |= (in[offset++] & 0xff) << 8;

        result |= (in[offset++] & 0xff) << 16;

        result |= in[offset] << 24;

        return ((long)result) & 0xffffffffL;

    }
    //TODO
    public void GeDoubleScalarMultVartime(ProjectiveGroupElement r, byte[] a , ExtendedGroupElement A, byte[] b) {
        byte[] aSlide = new byte[256];
        byte[] bSlide = new byte[256];

        CachedGroupElement[] Ai = new CachedGroupElement[8];// A,3A,5A,7A,9A,11A,13A,15A
        CompletedGroupElement t = new CompletedGroupElement();
        ExtendedGroupElement u = new ExtendedGroupElement();
        ExtendedGroupElement A2 = new ExtendedGroupElement();
        int i = 0;

        slide(aSlide, a);
        slide(bSlide, b);

        A.ToCached(Ai[0]);
        A.Double(t);
        t.ToExtended(A2);

        for (i = 0; i < 7; i++) {
            geAdd(t, A2, Ai[i]);
            t.ToExtended(u);
            u.ToCached(Ai[i+1]);
        }

        r.Zero();

        for (i = 255; i >= 0; i--) {
            if (aSlide[i] != 0 || bSlide[i] != 0) {
                break;
            }
        }

        for( ; i >= 0; i--) {
            r.Double(t);

            if (aSlide[i] > 0) {
                t.ToExtended(u);
                geAdd(t, u, Ai[aSlide[i]/2]);
            } else if (aSlide[i] < 0 ){
                t.ToExtended(u);
                geSub(t, u, Ai[(-aSlide[i])/2]);
            }

            if (bSlide[i] > 0) {
                t.ToExtended(u);
                geMixedAdd(t, u, Constant.Bi[bSlide[i]/2]);
            } else if (bSlide[i] < 0) {
                t.ToExtended(u);
                geMixedSub(t, u, Constant.Bi[(-bSlide[i])/2]);
            }

            t.ToProjective(r);
        }
    }
    public static byte[] ScReduce(byte[] s) {

        // s0,..., s22 have 21 bits, s23 has 29 bits

        long s0 = 0x1FFFFF & load_3(s, 0);

        long s1 = 0x1FFFFF & (load_4(s, 2) >> 5);

        long s2 = 0x1FFFFF & (load_3(s, 5) >> 2);

        long s3 = 0x1FFFFF & (load_4(s, 7) >> 7);

        long s4 = 0x1FFFFF & (load_4(s, 10) >> 4);

        long s5 = 0x1FFFFF & (load_3(s, 13) >> 1);

        long s6 = 0x1FFFFF & (load_4(s, 15) >> 6);

        long s7 = 0x1FFFFF & (load_3(s, 18) >> 3);

        long s8 = 0x1FFFFF & load_3(s, 21);

        long s9 = 0x1FFFFF & (load_4(s, 23) >> 5);

        long s10 = 0x1FFFFF & (load_3(s, 26) >> 2);

        long s11 = 0x1FFFFF & (load_4(s, 28) >> 7);

        long s12 = 0x1FFFFF & (load_4(s, 31) >> 4);

        long s13 = 0x1FFFFF & (load_3(s, 34) >> 1);

        long s14 = 0x1FFFFF & (load_4(s, 36) >> 6);

        long s15 = 0x1FFFFF & (load_3(s, 39) >> 3);

        long s16 = 0x1FFFFF & load_3(s, 42);

        long s17 = 0x1FFFFF & (load_4(s, 44) >> 5);

        long s18 = 0x1FFFFF & (load_3(s, 47) >> 2);

        long s19 = 0x1FFFFF & (load_4(s, 49) >> 7);

        long s20 = 0x1FFFFF & (load_4(s, 52) >> 4);

        long s21 = 0x1FFFFF & (load_3(s, 55) >> 1);

        long s22 = 0x1FFFFF & (load_4(s, 57) >> 6);

        long s23 = (load_4(s, 60) >> 3);

        long carry0;

        long carry1;

        long carry2;

        long carry3;

        long carry4;

        long carry5;

        long carry6;

        long carry7;

        long carry8;

        long carry9;

        long carry10;

        long carry11;

        long carry12;

        long carry13;

        long carry14;

        long carry15;

        long carry16;


        s11 += s23 * 666643;

        s12 += s23 * 470296;
        s13 += s23 * 654183;
        s14 -= s23 * 997805;
        s15 += s23 * 136657;
        s16 -= s23 * 683901;
        // not used again

        //s23 = 0;
        s10 += s22 * 666643;
        s11 += s22 * 470296;
        s12 += s22 * 654183;
        s13 -= s22 * 997805;
        s14 += s22 * 136657;
        s15 -= s22 * 683901;
        // not used again

        //s22 = 0;



        s9 += s21 * 666643;

        s10 += s21 * 470296;

        s11 += s21 * 654183;

        s12 -= s21 * 997805;

        s13 += s21 * 136657;

        s14 -= s21 * 683901;

        // not used again

        //s21 = 0;



        s8 += s20 * 666643;

        s9 += s20 * 470296;

        s10 += s20 * 654183;

        s11 -= s20 * 997805;

        s12 += s20 * 136657;

        s13 -= s20 * 683901;

        // not used again

        //s20 = 0;



        s7 += s19 * 666643;

        s8 += s19 * 470296;

        s9 += s19 * 654183;

        s10 -= s19 * 997805;

        s11 += s19 * 136657;

        s12 -= s19 * 683901;

        // not used again

        //s19 = 0;



        s6 += s18 * 666643;

        s7 += s18 * 470296;

        s8 += s18 * 654183;

        s9 -= s18 * 997805;

        s10 += s18 * 136657;

        s11 -= s18 * 683901;


        carry6 = (s6 + (1<<20)) >> 21; s7 += carry6; s6 -= carry6 << 21;

        carry8 = (s8 + (1<<20)) >> 21; s9 += carry8; s8 -= carry8 << 21;

        carry10 = (s10 + (1<<20)) >> 21; s11 += carry10; s10 -= carry10 << 21;

        carry12 = (s12 + (1<<20)) >> 21; s13 += carry12; s12 -= carry12 << 21;

        carry14 = (s14 + (1<<20)) >> 21; s15 += carry14; s14 -= carry14 << 21;

        carry16 = (s16 + (1<<20)) >> 21; s17 += carry16; s16 -= carry16 << 21;



        carry7 = (s7 + (1<<20)) >> 21; s8 += carry7; s7 -= carry7 << 21;

        carry9 = (s9 + (1<<20)) >> 21; s10 += carry9; s9 -= carry9 << 21;

        carry11 = (s11 + (1<<20)) >> 21; s12 += carry11; s11 -= carry11 << 21;

        carry13 = (s13 + (1<<20)) >> 21; s14 += carry13; s13 -= carry13 << 21;

        carry15 = (s15 + (1<<20)) >> 21; s16 += carry15; s15 -= carry15 << 21;



        /**

         * Continue with above procedure.

         */

        s5 += s17 * 666643;

        s6 += s17 * 470296;

        s7 += s17 * 654183;

        s8 -= s17 * 997805;

        s9 += s17 * 136657;

        s10 -= s17 * 683901;

        // not used again

        //s17 = 0;



        s4 += s16 * 666643;

        s5 += s16 * 470296;

        s6 += s16 * 654183;

        s7 -= s16 * 997805;

        s8 += s16 * 136657;

        s9 -= s16 * 683901;

        // not used again

        //s16 = 0;



        s3 += s15 * 666643;

        s4 += s15 * 470296;

        s5 += s15 * 654183;

        s6 -= s15 * 997805;

        s7 += s15 * 136657;

        s8 -= s15 * 683901;

        // not used again

        //s15 = 0;



        s2 += s14 * 666643;

        s3 += s14 * 470296;

        s4 += s14 * 654183;

        s5 -= s14 * 997805;

        s6 += s14 * 136657;

        s7 -= s14 * 683901;

        // not used again

        //s14 = 0;



        s1 += s13 * 666643;

        s2 += s13 * 470296;

        s3 += s13 * 654183;

        s4 -= s13 * 997805;

        s5 += s13 * 136657;

        s6 -= s13 * 683901;

        // not used again

        //s13 = 0;



        s0 += s12 * 666643;

        s1 += s12 * 470296;

        s2 += s12 * 654183;

        s3 -= s12 * 997805;

        s4 += s12 * 136657;

        s5 -= s12 * 683901;

        // set below

        //s12 = 0;



        /**

         * Reduce coefficients again.

         */

        carry0 = (s0 + (1<<20)) >> 21; s1 += carry0; s0 -= carry0 << 21;

        carry2 = (s2 + (1<<20)) >> 21; s3 += carry2; s2 -= carry2 << 21;

        carry4 = (s4 + (1<<20)) >> 21; s5 += carry4; s4 -= carry4 << 21;

        carry6 = (s6 + (1<<20)) >> 21; s7 += carry6; s6 -= carry6 << 21;

        carry8 = (s8 + (1<<20)) >> 21; s9 += carry8; s8 -= carry8 << 21;

        carry10 = (s10 + (1<<20)) >> 21; s11 += carry10; s10 -= carry10 << 21;



        carry1 = (s1 + (1<<20)) >> 21; s2 += carry1; s1 -= carry1 << 21;

        carry3 = (s3 + (1<<20)) >> 21; s4 += carry3; s3 -= carry3 << 21;

        carry5 = (s5 + (1<<20)) >> 21; s6 += carry5; s5 -= carry5 << 21;

        carry7 = (s7 + (1<<20)) >> 21; s8 += carry7; s7 -= carry7 << 21;

        carry9 = (s9 + (1<<20)) >> 21; s10 += carry9; s9 -= carry9 << 21;

        //carry11 = (s11 + (1<<20)) >> 21; s12 += carry11; s11 -= carry11 << 21;

        carry11 = (s11 + (1<<20)) >> 21; s12 = carry11; s11 -= carry11 << 21;



        s0 += s12 * 666643;

        s1 += s12 * 470296;

        s2 += s12 * 654183;

        s3 -= s12 * 997805;

        s4 += s12 * 136657;

        s5 -= s12 * 683901;

        // set below

        //s12 = 0;



        carry0 = s0 >> 21; s1 += carry0; s0 -= carry0 << 21;

        carry1 = s1 >> 21; s2 += carry1; s1 -= carry1 << 21;

        carry2 = s2 >> 21; s3 += carry2; s2 -= carry2 << 21;

        carry3 = s3 >> 21; s4 += carry3; s3 -= carry3 << 21;

        carry4 = s4 >> 21; s5 += carry4; s4 -= carry4 << 21;

        carry5 = s5 >> 21; s6 += carry5; s5 -= carry5 << 21;

        carry6 = s6 >> 21; s7 += carry6; s6 -= carry6 << 21;

        carry7 = s7 >> 21; s8 += carry7; s7 -= carry7 << 21;

        carry8 = s8 >> 21; s9 += carry8; s8 -= carry8 << 21;

        carry9 = s9 >> 21; s10 += carry9; s9 -= carry9 << 21;

        carry10 = s10 >> 21; s11 += carry10; s10 -= carry10 << 21;

        //carry11 = s11 >> 21; s12 += carry11; s11 -= carry11 << 21;

        carry11 = s11 >> 21; s12 = carry11; s11 -= carry11 << 21;

        s0 += s12 * 666643;

        s1 += s12 * 470296;

        s2 += s12 * 654183;

        s3 -= s12 * 997805;

        s4 += s12 * 136657;

        s5 -= s12 * 683901;

        // not used again

        //s12 = 0;



        carry0 = s0 >> 21; s1 += carry0; s0 -= carry0 << 21;

        carry1 = s1 >> 21; s2 += carry1; s1 -= carry1 << 21;

        carry2 = s2 >> 21; s3 += carry2; s2 -= carry2 << 21;

        carry3 = s3 >> 21; s4 += carry3; s3 -= carry3 << 21;

        carry4 = s4 >> 21; s5 += carry4; s4 -= carry4 << 21;

        carry5 = s5 >> 21; s6 += carry5; s5 -= carry5 << 21;

        carry6 = s6 >> 21; s7 += carry6; s6 -= carry6 << 21;

        carry7 = s7 >> 21; s8 += carry7; s7 -= carry7 << 21;

        carry8 = s8 >> 21; s9 += carry8; s8 -= carry8 << 21;

        carry9 = s9 >> 21; s10 += carry9; s9 -= carry9 << 21;

        carry10 = s10 >> 21; s11 += carry10; s10 -= carry10 << 21;



        // s0, ..., s11 got 21 bits each.

        byte[] result = new byte[32];

        result[0] = (byte) s0;

        result[1] = (byte) (s0 >> 8);

        result[2] = (byte) ((s0 >> 16) | (s1 << 5));

        result[3] = (byte) (s1 >> 3);

        result[4] = (byte) (s1 >> 11);

        result[5] = (byte) ((s1 >> 19) | (s2 << 2));

        result[6] = (byte) (s2 >> 6);

        result[7] = (byte) ((s2 >> 14) | (s3 << 7));

        result[8] = (byte) (s3 >> 1);

        result[9] = (byte) (s3 >> 9);

        result[10] = (byte) ((s3 >> 17) | (s4 << 4));

        result[11] = (byte) (s4 >> 4);

        result[12] = (byte) (s4 >> 12);

        result[13] = (byte) ((s4 >> 20) | (s5 << 1));

        result[14] = (byte) (s5 >> 7);

        result[15] = (byte) ((s5 >> 15) | (s6 << 6));

        result[16] = (byte) (s6 >> 2);

        result[17] = (byte) (s6 >> 10);

        result[18] = (byte) ((s6 >> 18) | (s7 << 3));

        result[19] = (byte) (s7 >> 5);

        result[20] = (byte) (s7 >> 13);

        result[21] = (byte) s8;

        result[22] = (byte) (s8 >> 8);

        result[23] = (byte) ((s8 >> 16) | (s9 << 5));

        result[24] = (byte) (s9 >> 3);

        result[25] = (byte) (s9 >> 11);

        result[26] = (byte) ((s9 >> 19) | (s10 << 2));

        result[27] = (byte) (s10 >> 6);

        result[28] = (byte) ((s10 >> 14) | (s11 << 7));

        result[29] = (byte) (s11 >> 1);

        result[30] = (byte) (s11 >> 9);

        result[31] = (byte) (s11 >> 17);

        return result;

    }

    public static byte[] multiplyAndAdd(byte[] a, byte[] b, byte[] c) {

        long a0 = 0x1FFFFF & load_3(a, 0);

        long a1 = 0x1FFFFF & (load_4(a, 2) >> 5);

        long a2 = 0x1FFFFF & (load_3(a, 5) >> 2);

        long a3 = 0x1FFFFF & (load_4(a, 7) >> 7);

        long a4 = 0x1FFFFF & (load_4(a, 10) >> 4);

        long a5 = 0x1FFFFF & (load_3(a, 13) >> 1);

        long a6 = 0x1FFFFF & (load_4(a, 15) >> 6);

        long a7 = 0x1FFFFF & (load_3(a, 18) >> 3);

        long a8 = 0x1FFFFF & load_3(a, 21);

        long a9 = 0x1FFFFF & (load_4(a, 23) >> 5);

        long a10 = 0x1FFFFF & (load_3(a, 26) >> 2);

        long a11 = (load_4(a, 28) >> 7);

        long b0 = 0x1FFFFF & load_3(b, 0);

        long b1 = 0x1FFFFF & (load_4(b, 2) >> 5);

        long b2 = 0x1FFFFF & (load_3(b, 5) >> 2);

        long b3 = 0x1FFFFF & (load_4(b, 7) >> 7);

        long b4 = 0x1FFFFF & (load_4(b, 10) >> 4);

        long b5 = 0x1FFFFF & (load_3(b, 13) >> 1);

        long b6 = 0x1FFFFF & (load_4(b, 15) >> 6);

        long b7 = 0x1FFFFF & (load_3(b, 18) >> 3);

        long b8 = 0x1FFFFF & load_3(b, 21);

        long b9 = 0x1FFFFF & (load_4(b, 23) >> 5);

        long b10 = 0x1FFFFF & (load_3(b, 26) >> 2);

        long b11 = (load_4(b, 28) >> 7);

        long c0 = 0x1FFFFF & load_3(c, 0);

        long c1 = 0x1FFFFF & (load_4(c, 2) >> 5);

        long c2 = 0x1FFFFF & (load_3(c, 5) >> 2);

        long c3 = 0x1FFFFF & (load_4(c, 7) >> 7);

        long c4 = 0x1FFFFF & (load_4(c, 10) >> 4);

        long c5 = 0x1FFFFF & (load_3(c, 13) >> 1);

        long c6 = 0x1FFFFF & (load_4(c, 15) >> 6);

        long c7 = 0x1FFFFF & (load_3(c, 18) >> 3);

        long c8 = 0x1FFFFF & load_3(c, 21);

        long c9 = 0x1FFFFF & (load_4(c, 23) >> 5);

        long c10 = 0x1FFFFF & (load_3(c, 26) >> 2);

        long c11 = (load_4(c, 28) >> 7);

        long s0;

        long s1;

        long s2;

        long s3;

        long s4;

        long s5;

        long s6;

        long s7;

        long s8;

        long s9;

        long s10;

        long s11;

        long s12;

        long s13;

        long s14;

        long s15;

        long s16;

        long s17;

        long s18;

        long s19;

        long s20;

        long s21;

        long s22;

        long s23;

        long carry0;

        long carry1;

        long carry2;

        long carry3;

        long carry4;

        long carry5;

        long carry6;

        long carry7;

        long carry8;

        long carry9;

        long carry10;

        long carry11;

        long carry12;

        long carry13;

        long carry14;

        long carry15;

        long carry16;

        long carry17;

        long carry18;

        long carry19;

        long carry20;

        long carry21;

        long carry22;


        s0 = c0 + a0 * b0;

        s1 = c1 + a0 * b1 + a1 * b0;

        s2 = c2 + a0 * b2 + a1 * b1 + a2 * b0;

        s3 = c3 + a0 * b3 + a1 * b2 + a2 * b1 + a3 * b0;

        s4 = c4 + a0 * b4 + a1 * b3 + a2 * b2 + a3 * b1 + a4 * b0;

        s5 = c5 + a0 * b5 + a1 * b4 + a2 * b3 + a3 * b2 + a4 * b1 + a5 * b0;

        s6 = c6 + a0 * b6 + a1 * b5 + a2 * b4 + a3 * b3 + a4 * b2 + a5 * b1 + a6 * b0;

        s7 = c7 + a0 * b7 + a1 * b6 + a2 * b5 + a3 * b4 + a4 * b3 + a5 * b2 + a6 * b1 + a7 * b0;

        s8 = c8 + a0 * b8 + a1 * b7 + a2 * b6 + a3 * b5 + a4 * b4 + a5 * b3 + a6 * b2 + a7 * b1 + a8 * b0;

        s9 = c9 + a0 * b9 + a1 * b8 + a2 * b7 + a3 * b6 + a4 * b5 + a5 * b4 + a6 * b3 + a7 * b2 + a8 * b1 + a9 * b0;

        s10 = c10 + a0 * b10 + a1 * b9 + a2 * b8 + a3 * b7 + a4 * b6 + a5 * b5 + a6 * b4 + a7 * b3 + a8 * b2 + a9 * b1 + a10 * b0;

        s11 = c11 + a0 * b11 + a1 * b10 + a2 * b9 + a3 * b8 + a4 * b7 + a5 * b6 + a6 * b5 + a7 * b4 + a8 * b3 + a9 * b2 + a10 * b1 + a11 * b0;

        s12 = a1 * b11 + a2 * b10 + a3 * b9 + a4 * b8 + a5 * b7 + a6 * b6 + a7 * b5 + a8 * b4 + a9 * b3 + a10 * b2 + a11 * b1;

        s13 = a2 * b11 + a3 * b10 + a4 * b9 + a5 * b8 + a6 * b7 + a7 * b6 + a8 * b5 + a9 * b4 + a10 * b3 + a11 * b2;

        s14 = a3 * b11 + a4 * b10 + a5 * b9 + a6 * b8 + a7 * b7 + a8 * b6 + a9 * b5 + a10 * b4 + a11 * b3;

        s15 = a4 * b11 + a5 * b10 + a6 * b9 + a7 * b8 + a8 * b7 + a9 * b6 + a10 * b5 + a11 * b4;

        s16 = a5 * b11 + a6 * b10 + a7 * b9 + a8 * b8 + a9 * b7 + a10 * b6 + a11 * b5;

        s17 = a6 * b11 + a7 * b10 + a8 * b9 + a9 * b8 + a10 * b7 + a11 * b6;

        s18 = a7 * b11 + a8 * b10 + a9 * b9 + a10 * b8 + a11 * b7;

        s19 = a8 * b11 + a9 * b10 + a10 * b9 + a11 * b8;

        s20 = a9 * b11 + a10 * b10 + a11 * b9;

        s21 = a10 * b11 + a11 * b10;

        s22 = a11 * b11;

        // set below

        //s23 = 0;


        carry0 = (s0 + (1 << 20)) >> 21;
        s1 += carry0;
        s0 -= carry0 << 21;

        carry2 = (s2 + (1 << 20)) >> 21;
        s3 += carry2;
        s2 -= carry2 << 21;

        carry4 = (s4 + (1 << 20)) >> 21;
        s5 += carry4;
        s4 -= carry4 << 21;

        carry6 = (s6 + (1 << 20)) >> 21;
        s7 += carry6;
        s6 -= carry6 << 21;

        carry8 = (s8 + (1 << 20)) >> 21;
        s9 += carry8;
        s8 -= carry8 << 21;

        carry10 = (s10 + (1 << 20)) >> 21;
        s11 += carry10;
        s10 -= carry10 << 21;

        carry12 = (s12 + (1 << 20)) >> 21;
        s13 += carry12;
        s12 -= carry12 << 21;

        carry14 = (s14 + (1 << 20)) >> 21;
        s15 += carry14;
        s14 -= carry14 << 21;

        carry16 = (s16 + (1 << 20)) >> 21;
        s17 += carry16;
        s16 -= carry16 << 21;

        carry18 = (s18 + (1 << 20)) >> 21;
        s19 += carry18;
        s18 -= carry18 << 21;

        carry20 = (s20 + (1 << 20)) >> 21;
        s21 += carry20;
        s20 -= carry20 << 21;

        //carry22 = (s22 + (1<<20)) >> 21; s23 += carry22; s22 -= carry22 << 21;

        carry22 = (s22 + (1 << 20)) >> 21;
        s23 = carry22;
        s22 -= carry22 << 21;


        carry1 = (s1 + (1 << 20)) >> 21;
        s2 += carry1;
        s1 -= carry1 << 21;

        carry3 = (s3 + (1 << 20)) >> 21;
        s4 += carry3;
        s3 -= carry3 << 21;

        carry5 = (s5 + (1 << 20)) >> 21;
        s6 += carry5;
        s5 -= carry5 << 21;

        carry7 = (s7 + (1 << 20)) >> 21;
        s8 += carry7;
        s7 -= carry7 << 21;

        carry9 = (s9 + (1 << 20)) >> 21;
        s10 += carry9;
        s9 -= carry9 << 21;

        carry11 = (s11 + (1 << 20)) >> 21;
        s12 += carry11;
        s11 -= carry11 << 21;

        carry13 = (s13 + (1 << 20)) >> 21;
        s14 += carry13;
        s13 -= carry13 << 21;

        carry15 = (s15 + (1 << 20)) >> 21;
        s16 += carry15;
        s15 -= carry15 << 21;

        carry17 = (s17 + (1 << 20)) >> 21;
        s18 += carry17;
        s17 -= carry17 << 21;

        carry19 = (s19 + (1 << 20)) >> 21;
        s20 += carry19;
        s19 -= carry19 << 21;

        carry21 = (s21 + (1 << 20)) >> 21;
        s22 += carry21;
        s21 -= carry21 << 21;


        s11 += s23 * 666643;

        s12 += s23 * 470296;

        s13 += s23 * 654183;

        s14 -= s23 * 997805;

        s15 += s23 * 136657;

        s16 -= s23 * 683901;

        // not used again

        //s23 = 0;


        s10 += s22 * 666643;

        s11 += s22 * 470296;

        s12 += s22 * 654183;

        s13 -= s22 * 997805;

        s14 += s22 * 136657;

        s15 -= s22 * 683901;

        // not used again

        //s22 = 0;


        s9 += s21 * 666643;

        s10 += s21 * 470296;

        s11 += s21 * 654183;

        s12 -= s21 * 997805;

        s13 += s21 * 136657;

        s14 -= s21 * 683901;

        // not used again

        //s21 = 0;


        s8 += s20 * 666643;

        s9 += s20 * 470296;

        s10 += s20 * 654183;

        s11 -= s20 * 997805;

        s12 += s20 * 136657;

        s13 -= s20 * 683901;

        // not used again

        //s20 = 0;


        s7 += s19 * 666643;

        s8 += s19 * 470296;

        s9 += s19 * 654183;

        s10 -= s19 * 997805;

        s11 += s19 * 136657;

        s12 -= s19 * 683901;

        // not used again

        //s19 = 0;


        s6 += s18 * 666643;

        s7 += s18 * 470296;

        s8 += s18 * 654183;

        s9 -= s18 * 997805;

        s10 += s18 * 136657;

        s11 -= s18 * 683901;

        // not used again

        //s18 = 0;


        carry6 = (s6 + (1 << 20)) >> 21;
        s7 += carry6;
        s6 -= carry6 << 21;

        carry8 = (s8 + (1 << 20)) >> 21;
        s9 += carry8;
        s8 -= carry8 << 21;

        carry10 = (s10 + (1 << 20)) >> 21;
        s11 += carry10;
        s10 -= carry10 << 21;

        carry12 = (s12 + (1 << 20)) >> 21;
        s13 += carry12;
        s12 -= carry12 << 21;

        carry14 = (s14 + (1 << 20)) >> 21;
        s15 += carry14;
        s14 -= carry14 << 21;

        carry16 = (s16 + (1 << 20)) >> 21;
        s17 += carry16;
        s16 -= carry16 << 21;


        carry7 = (s7 + (1 << 20)) >> 21;
        s8 += carry7;
        s7 -= carry7 << 21;

        carry9 = (s9 + (1 << 20)) >> 21;
        s10 += carry9;
        s9 -= carry9 << 21;

        carry11 = (s11 + (1 << 20)) >> 21;
        s12 += carry11;
        s11 -= carry11 << 21;

        carry13 = (s13 + (1 << 20)) >> 21;
        s14 += carry13;
        s13 -= carry13 << 21;

        carry15 = (s15 + (1 << 20)) >> 21;
        s16 += carry15;
        s15 -= carry15 << 21;


        s5 += s17 * 666643;

        s6 += s17 * 470296;

        s7 += s17 * 654183;

        s8 -= s17 * 997805;

        s9 += s17 * 136657;

        s10 -= s17 * 683901;

        // not used again

        //s17 = 0;


        s4 += s16 * 666643;

        s5 += s16 * 470296;

        s6 += s16 * 654183;

        s7 -= s16 * 997805;

        s8 += s16 * 136657;

        s9 -= s16 * 683901;

        // not used again

        //s16 = 0;


        s3 += s15 * 666643;

        s4 += s15 * 470296;

        s5 += s15 * 654183;

        s6 -= s15 * 997805;

        s7 += s15 * 136657;

        s8 -= s15 * 683901;

        // not used again

        //s15 = 0;


        s2 += s14 * 666643;

        s3 += s14 * 470296;

        s4 += s14 * 654183;

        s5 -= s14 * 997805;

        s6 += s14 * 136657;

        s7 -= s14 * 683901;

        // not used again

        //s14 = 0;


        s1 += s13 * 666643;

        s2 += s13 * 470296;

        s3 += s13 * 654183;

        s4 -= s13 * 997805;

        s5 += s13 * 136657;

        s6 -= s13 * 683901;

        // not used again

        //s13 = 0;


        s0 += s12 * 666643;

        s1 += s12 * 470296;

        s2 += s12 * 654183;

        s3 -= s12 * 997805;

        s4 += s12 * 136657;

        s5 -= s12 * 683901;

        // set below

        //s12 = 0;


        carry0 = (s0 + (1 << 20)) >> 21;
        s1 += carry0;
        s0 -= carry0 << 21;

        carry2 = (s2 + (1 << 20)) >> 21;
        s3 += carry2;
        s2 -= carry2 << 21;

        carry4 = (s4 + (1 << 20)) >> 21;
        s5 += carry4;
        s4 -= carry4 << 21;

        carry6 = (s6 + (1 << 20)) >> 21;
        s7 += carry6;
        s6 -= carry6 << 21;

        carry8 = (s8 + (1 << 20)) >> 21;
        s9 += carry8;
        s8 -= carry8 << 21;

        carry10 = (s10 + (1 << 20)) >> 21;
        s11 += carry10;
        s10 -= carry10 << 21;


        carry1 = (s1 + (1 << 20)) >> 21;
        s2 += carry1;
        s1 -= carry1 << 21;

        carry3 = (s3 + (1 << 20)) >> 21;
        s4 += carry3;
        s3 -= carry3 << 21;

        carry5 = (s5 + (1 << 20)) >> 21;
        s6 += carry5;
        s5 -= carry5 << 21;

        carry7 = (s7 + (1 << 20)) >> 21;
        s8 += carry7;
        s7 -= carry7 << 21;

        carry9 = (s9 + (1 << 20)) >> 21;
        s10 += carry9;
        s9 -= carry9 << 21;

        //carry11 = (s11 + (1<<20)) >> 21; s12 += carry11; s11 -= carry11 << 21;

        carry11 = (s11 + (1 << 20)) >> 21;
        s12 = carry11;
        s11 -= carry11 << 21;


        s0 += s12 * 666643;

        s1 += s12 * 470296;

        s2 += s12 * 654183;

        s3 -= s12 * 997805;

        s4 += s12 * 136657;

        s5 -= s12 * 683901;

        // set below

        //s12 = 0;


        carry0 = s0 >> 21;
        s1 += carry0;
        s0 -= carry0 << 21;

        carry1 = s1 >> 21;
        s2 += carry1;
        s1 -= carry1 << 21;

        carry2 = s2 >> 21;
        s3 += carry2;
        s2 -= carry2 << 21;

        carry3 = s3 >> 21;
        s4 += carry3;
        s3 -= carry3 << 21;

        carry4 = s4 >> 21;
        s5 += carry4;
        s4 -= carry4 << 21;

        carry5 = s5 >> 21;
        s6 += carry5;
        s5 -= carry5 << 21;

        carry6 = s6 >> 21;
        s7 += carry6;
        s6 -= carry6 << 21;

        carry7 = s7 >> 21;
        s8 += carry7;
        s7 -= carry7 << 21;

        carry8 = s8 >> 21;
        s9 += carry8;
        s8 -= carry8 << 21;

        carry9 = s9 >> 21;
        s10 += carry9;
        s9 -= carry9 << 21;

        carry10 = s10 >> 21;
        s11 += carry10;
        s10 -= carry10 << 21;

        //carry11 = s11 >> 21; s12 += carry11; s11 -= carry11 << 21;

        carry11 = s11 >> 21;
        s12 = carry11;
        s11 -= carry11 << 21;


        s0 += s12 * 666643;

        s1 += s12 * 470296;

        s2 += s12 * 654183;

        s3 -= s12 * 997805;

        s4 += s12 * 136657;

        s5 -= s12 * 683901;

        // not used again

        //s12 = 0;


        carry0 = s0 >> 21;
        s1 += carry0;
        s0 -= carry0 << 21;

        carry1 = s1 >> 21;
        s2 += carry1;
        s1 -= carry1 << 21;

        carry2 = s2 >> 21;
        s3 += carry2;
        s2 -= carry2 << 21;

        carry3 = s3 >> 21;
        s4 += carry3;
        s3 -= carry3 << 21;

        carry4 = s4 >> 21;
        s5 += carry4;
        s4 -= carry4 << 21;

        carry5 = s5 >> 21;
        s6 += carry5;
        s5 -= carry5 << 21;

        carry6 = s6 >> 21;
        s7 += carry6;
        s6 -= carry6 << 21;

        carry7 = s7 >> 21;
        s8 += carry7;
        s7 -= carry7 << 21;

        carry8 = s8 >> 21;
        s9 += carry8;
        s8 -= carry8 << 21;

        carry9 = s9 >> 21;
        s10 += carry9;
        s9 -= carry9 << 21;

        carry10 = s10 >> 21;
        s11 += carry10;
        s10 -= carry10 << 21;


        byte[] result = new byte[32];

        result[0] = (byte) s0;

        result[1] = (byte) (s0 >> 8);

        result[2] = (byte) ((s0 >> 16) | (s1 << 5));

        result[3] = (byte) (s1 >> 3);

        result[4] = (byte) (s1 >> 11);

        result[5] = (byte) ((s1 >> 19) | (s2 << 2));

        result[6] = (byte) (s2 >> 6);

        result[7] = (byte) ((s2 >> 14) | (s3 << 7));

        result[8] = (byte) (s3 >> 1);

        result[9] = (byte) (s3 >> 9);

        result[10] = (byte) ((s3 >> 17) | (s4 << 4));

        result[11] = (byte) (s4 >> 4);

        result[12] = (byte) (s4 >> 12);

        result[13] = (byte) ((s4 >> 20) | (s5 << 1));

        result[14] = (byte) (s5 >> 7);

        result[15] = (byte) ((s5 >> 15) | (s6 << 6));

        result[16] = (byte) (s6 >> 2);

        result[17] = (byte) (s6 >> 10);

        result[18] = (byte) ((s6 >> 18) | (s7 << 3));

        result[19] = (byte) (s7 >> 5);

        result[20] = (byte) (s7 >> 13);

        result[21] = (byte) s8;

        result[22] = (byte) (s8 >> 8);

        result[23] = (byte) ((s8 >> 16) | (s9 << 5));

        result[24] = (byte) (s9 >> 3);

        result[25] = (byte) (s9 >> 11);

        result[26] = (byte) ((s9 >> 19) | (s10 << 2));

        result[27] = (byte) (s10 >> 6);

        result[28] = (byte) ((s10 >> 14) | (s11 << 7));

        result[29] = (byte) (s11 >> 1);

        result[30] = (byte) (s11 >> 9);

        result[31] = (byte) (s11 >> 17);

        return result;
    }
}
