package aes;

import static aes.AESUtils.AlogTable;
import static aes.AESUtils.LogTable;

/**
 * Created by Gheorghe on 1/31/2018.
 */
public class GaloisUtils {
    public static char mul(int a, char b)
    {
        int indexMultiplicationBy = (a < 0) ? (a + 256) : a;
        int indexMultiplier = (b < 0) ? (b + 256) : b;

        if ( (a != 0) && (b != 0) )
        {
            int index = (LogTable[indexMultiplicationBy] + LogTable[indexMultiplier]);
            return (char) AlogTable[ index % 255 ];
        }
        else
            return 0;
    }

    public static char multiply_by_x(int a, int b) {
        int p = 0;

        for (int n=0; n<8; n++) {

            p = ((b & 0x01) > 0) ? p^a : p;

            boolean ho = ((a & 0x80) > 0);

            a = ((a<<1) & 0xFE);

            if (ho)
                a = a ^ 0x1b;

            b = ((b>>1) & 0x7F);
        }
        return (char)p;
    }
}
