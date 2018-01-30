package aes;

/**
 * Created by Gheorghe on 1/30/2018.
 */
public class AES_Implementation {

    private static int BLOCK_LENGTH = 16;
    public static boolean encryptFlag = true;

    public void encrypt(char[] message, char[] key){
        char[] state = new char[BLOCK_LENGTH];

        for(int i = 0; i < BLOCK_LENGTH; i++){
            state[i] = message[i];
        }

        int numberOfRounds = 1;

        keyExpansion();
        addRoundKey(state, key);

        for(int i = 0; i < numberOfRounds; i++) {
            subBytes(state);
            state = shiftRows(state);
            mixColumns();
            addRoundKey(state, key);
        }
    }

    private void mixColumns() {
    }

    private char[] shiftRows(char[] state) {

        char[] tmp = new char[BLOCK_LENGTH];
        for(int i = 0; i < BLOCK_LENGTH; i++) {
            int shift = i % 4;

            tmp[i] = state[(shift * 4 + i) % BLOCK_LENGTH];
        }

        return tmp;
    }

    private void subBytes(char[] state) {
        for(int i = 0; i < BLOCK_LENGTH; i++) {
            state[i] = subByte(state[i], encryptFlag);
        }
    }

    private void keyExpansion() {

    }

    public void addRoundKey(char [] state, char[] roundKey){
        for(int i = 0; i < BLOCK_LENGTH; i++) {
            state[i] ^= roundKey[i];
        }
    }






    public static char subByte(char b, boolean useSBox) {
        int x = (b >> 4) &0x0F;
        int y = b & 0x0F;

        return useSBox ? (char)AES_Utils.sBox[x][y] : (char)AES_Utils.invSBox[x][y];
    }


}
