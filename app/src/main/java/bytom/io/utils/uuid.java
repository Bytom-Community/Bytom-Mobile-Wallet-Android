package bytom.io.utils;
// These values are from the public domain, “ref10” implementation of ed25519
// from SUPERCOP.

import java.io.UnsupportedEncodingException;

// d is a constant in the Edwards curve equation.
public class uuid {


    // xvalues returns the value of a byte as a hexadecimal digit or 255.

    private static int[] xvalues = new int[]{
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 255, 255, 255, 255, 255, 255,
            255, 10, 11, 12, 13, 14, 15, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 10, 11, 12, 13, 14, 15, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
            255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255
    };

    // Parse decodes s into a UUID or returns nil.  Both the UUID form of
    // xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx and
    // urn:uuid:xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx are decoded.
    public static byte[] Parse(String s)  {

        if (s.length() == 36+9) {

            if (!s.toLowerCase().startsWith("urn:uuid:")) {
                return null;
            }
            s = s.replace("urn:uuid:","");
        } else if (s.length() == 36) {
            return null;
        }
        byte[] originarray ;
        byte[] destarray ;
        try{
            originarray = s.getBytes("utf-8");
            destarray = s.replace("-","").getBytes("utf-8");
        } catch (UnsupportedEncodingException ex){
            return null;
        }
        if (originarray[8] != '-' || originarray[13] != '-' || originarray[18] != '-' || originarray[23] != '-') {
            return null;
        }

        byte[] uuid  = new byte[16];
        int v = 0;
        for(int i =0;i<16;i++){
            if (xtob(destarray,v)){
                return null;
            } else {
                uuid[i] = (byte)(v&0x00ff);
            }
        }
        return uuid;
    }
    private static boolean xtob(byte[] x,int v)  {

        int b1 = xvalues[x[0]];

        int b2 = xvalues[x[1]];

        v = (b1 << 4) | b2;

        return  b1 != 255 && b2 != 255;
    }
}


