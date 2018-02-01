import aes.AESImplementation;
import aes.MatrixUtils;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        char c = 0xFF;
        System.out.println((byte) AESImplementation.subByte(c, true));
     

        char[] key = {0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF,
                0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF};
        System.out.println(AESImplementation.keyExpansion(key)[231]);

        char [] expanded = AESImplementation.keyExpansion(key);

        char [] message = "test aes 256 azi".toCharArray();

        char [] criptat = AESImplementation.encrypt(message, expanded);
        char [] decriptat = AESImplementation.decrypt(criptat, expanded);

            MatrixUtils.printArrayAsHexa(criptat);
            MatrixUtils.printArrayAsHexa(decriptat);
            MatrixUtils.printArrayAsHexa(message);
//        MatrixUtils.printArrayAsHexa(expanded);

    }
}
