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
}
