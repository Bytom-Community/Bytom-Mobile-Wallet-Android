package bytom.io.utils;
// These values are from the public domain, “ref10” implementation of ed25519
// from SUPERCOP.

// d is a constant in the Edwards curve equation.
public class PreComputedGroupElement {
        private static final int SIZE = 10;
        int [] yPlusX ;
        int [] yMinusX ;
        int [] xy2d;
        public PreComputedGroupElement() {
                yPlusX = new int[SIZE];
                yMinusX = new int[SIZE];
                xy2d = new int[SIZE];
        }
        public PreComputedGroupElement(int[] new_yplusx, int[] new_yminusx,
                                       int[] new_xy2d) {
                yPlusX = new_yplusx;
                yMinusX = new_yminusx;
                xy2d = new_xy2d;

        }
}
