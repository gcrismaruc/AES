package aes;

import static aes.AESUtils.*;
import static aes.MatrixUtils.arrayToMatrix;

/**
 * Created by Gheorghe on 1/30/2018.
 */
public class AESImplementation {

    public static final int KEY_LENGTH = 32;

    public static char[] encrypt(char[] message, char[] key) {
        char[] state = new char[BLOCK_LENGTH];

        for (int i = 0; i < BLOCK_LENGTH; i++) {
            state[i] = message[i];
        }

        int numberOfRounds = 14;
        state = addRoundKey(state, getRoundKeyFromExpandedKey(key, 0));

        for (int round = 1; round < numberOfRounds; round++) {
            state = subBytes(state, true);
            state = shiftRows(state);
            state = mixColumns(arrayToMatrix(state));
            state = addRoundKey(state, getRoundKeyFromExpandedKey(key, round));
        }

        state = subBytes(state, true);
        state = shiftRows(state);
        state = addRoundKey(state, getRoundKeyFromExpandedKey(key, numberOfRounds));
        return state;
    }

    public static char[] decrypt(char[] message, char[] key) {
        char[] state = new char[BLOCK_LENGTH];

        for (int i = 0; i < BLOCK_LENGTH; i++) {
            state[i] = message[i];
        }
        int numberOfRounds = 14;

        state = addRoundKey(state, getRoundKeyFromExpandedKey(key, numberOfRounds));

        for (int round = numberOfRounds - 1; round >= 1 ; round--) {
            state = invShiftRows(state);
            state = invSubBytes(state, false);
            state = addRoundKey(state, getRoundKeyFromExpandedKey(key, round));
            state = invMixColumns(arrayToMatrix(state));
        }

        state = invShiftRows(state);
        state = invSubBytes(state, false);
        state = addRoundKey(state, getRoundKeyFromExpandedKey(key, 0));
        return state;
    }

    private static char[] invMixColumns(char[][] matrixState) {
        char[][] temp = new char[MATRIX_DIMENSION][MATRIX_DIMENSION];

        for (int i = 0; i < MATRIX_DIMENSION; i++) {
            for (int j = 0; j < MATRIX_DIMENSION; j++) {
                for (int k = 0; k < MATRIX_DIMENSION; k++) {
                    temp[i][j] ^= GaloisUtils.multiply_by_x(AESUtils.invMixBoxM[i][k], matrixState[k][j]);
                }
            }
        }

        return MatrixUtils.matrixToArray(temp);
    }

    private static char[] invSubBytes(char[] state, boolean encryptFlag) {
        for (int i = 0; i < BLOCK_LENGTH; i++) {
            state[i] = subByte(state[i], encryptFlag);
        }
        return state;
    }

    public static char[] invShiftRows(char[] state) {
        char[][] tmp = new char[MATRIX_DIMENSION][MATRIX_DIMENSION];
        char [][] newState = MatrixUtils.arrayToMatrix(state);

        for (int i = 0; i < MATRIX_DIMENSION; i++) {
            tmp[0][i] = newState[0][i];
            tmp[1][i] = newState[1][(i + 3) % MATRIX_DIMENSION];
            tmp[2][i] = newState[2][(i + 2) % MATRIX_DIMENSION];
            tmp[3][i] = newState[3][(i + 1) % MATRIX_DIMENSION];
        }
        return MatrixUtils.matrixToArray(tmp);
    }

    private static char[] getRoundKeyFromExpandedKey(char [] expandedKey, int round) {
        char[] key = new char[BLOCK_LENGTH];
        int index = BLOCK_LENGTH * (round);

        for(int i = 0; i < BLOCK_LENGTH; i++) {
            key[i] = expandedKey[index];
            index++;
        }

        return key;
    }

    private static char[] mixColumns(char[][] state) {
        char[][] temp = new char[MATRIX_DIMENSION][MATRIX_DIMENSION];

        for (int i = 0; i < MATRIX_DIMENSION; i++) {
            for (int j = 0; j < MATRIX_DIMENSION; j++) {
                for (int k = 0; k < MATRIX_DIMENSION; k++) {
                    temp[i][j] ^= GaloisUtils.multiply_by_x(AESUtils.mixBoxM[i][k], state[k][j]);
                }
            }
        }
        return MatrixUtils.matrixToArray(temp);
    }

    public static char[] shiftRows(char[] state) {

        char[] tmp = new char[BLOCK_LENGTH];
        for (int i = 0; i < BLOCK_LENGTH; i++) {
            int shift = i % 4;
            tmp[i] = state[(shift * 4 + i) % BLOCK_LENGTH];
        }
        return tmp;
    }

    private static char[] subBytes(char[] state, boolean encryptFlag) {
        for (int i = 0; i < BLOCK_LENGTH; i++) {
            state[i] = subByte(state[i], encryptFlag);
        }
        return state;
    }

    public static char[] keyExpansion(char[]inputKey) {
        char[] expandedKey = new char[240];

        for(int i = 0; i < KEY_LENGTH; i++) {
            expandedKey[i] = inputKey[i];
        }

        int bytesGenerated = KEY_LENGTH;
        int rConIteration = 1;
        char [] temp = new char[4];

        while(bytesGenerated < 240) {

            for(int i = 0; i < 4; i++)
                temp[i] = expandedKey[i + bytesGenerated - 4];

            if(bytesGenerated % KEY_LENGTH == 0){
                temp = keyExpansionCore(temp, rConIteration);
                rConIteration++;
            }

            if(bytesGenerated % KEY_LENGTH == KEY_LENGTH / 2){
                for(int i = 0; i < 4; i++) {
                    temp[i] = subByte(temp[i], true);
                }
            }

            for(int i = 0; i < 4; i++){
                expandedKey[bytesGenerated] = (char)(expandedKey[bytesGenerated - KEY_LENGTH] ^ temp[i]);
                bytesGenerated++;
            }
        }

        return expandedKey;
    }


    public static char[] keyExpansionCore(char[] input, int i) {
        char a;
        /* Rotate the input 8 bits to the left */
        input = rotate(input);
        /* Apply Rijndael's s-box on all 4 bytes */
        for(a = 0; a < 4; a++)
            input[a] = subByte(input[a], true);
        /* On just the first byte, add 2^i to the byte */
        input[0] ^= rCon[i];

        return input;
    }

    private static char[] rotate( char[] input) {
        char a,c;
        a = input[0];
        for(c=0;c<3;c++)
            input[c] = input[c + 1];
        input[3] = a;
        return input;
    }

    public static char[] addRoundKey(char[] state, char[] roundKey) {
        for (int i = 0; i < BLOCK_LENGTH; i++) {
            state[i] ^= roundKey[i];
        }
        return state;
    }


    public static char subByte(char b, boolean useSBox) {
        int x = (b >> 4) & 0x0F;
        int y = b & 0x0F;

        if(useSBox) {
            return (char) AESUtils.sBox[x][y];
        }
        return (char) AESUtils.invSBox[x][y];
    }
}
