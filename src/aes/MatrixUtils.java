package aes;

import static aes.AESUtils.BLOCK_LENGTH;
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


    public static char[] matrixToArray(char[][] matrix){
        char[] tmp = new char[BLOCK_LENGTH];
        int k = 0;
        for(int j = 0; j < MATRIX_DIMENSION; j++) {
            for(int i = 0; i < MATRIX_DIMENSION; i++) {
                tmp[k++] = matrix[i][j];
            }
        }

        return tmp;
    }

    public static void printMatrix(char[][] matrix) {
        for(int j = 0; j < MATRIX_DIMENSION; j++) {
            for (int i = 0; i < MATRIX_DIMENSION; i++) {
                System.out.print(Integer.toHexString((int)matrix[i][j]) + " ");
            }
            System.out.println("");
        }
    }

    public static void printArrayAsHexa(char [] input){
        for(int i = 0; i < input.length; i++) {
            if(i % 16 == 0){
                System.out.println("");
            }
            System.out.print(Integer.toHexString((int)input[i]) + " ");
//            System.out.print(input[i] + " ");
        }
    }
}
