package aes;

import static aes.AESUtils.MATRIX_DIMENSION;

/**
 * Created by Gheorghe on 1/31/2018.
 */
public class MatrixUtils {
    public static char[][] arrayToMatrix(char[] array) {
        char [][] matrix = new char[MATRIX_DIMENSION][MATRIX_DIMENSION];
        int k = 0;
        for(int j = 0; j < MATRIX_DIMENSION; j++) {
            for(int i = 0; i < MATRIX_DIMENSION; i++) {
                matrix[i][j] = array[k++];
            }
        }

        return matrix;
    }

    public static void printArrayAsHexa(char [] input){
        for(int i = 0; i < input.length; i++) {
            if(i % 16 == 0){
                System.out.println("");
            }
            System.out.print(Integer.toHexString((int)input[i]) + " ");
        }
    }
}
