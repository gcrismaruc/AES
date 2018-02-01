package aes;

/**
 * Created by Gheorghe on 2/2/2018.
 */
public class TextUtils {

    public static char[] getArrayFromString(String s) {
        if(s == null || s.equals("")) {
            System.out.println("Null or empty input string.");
            return null;
        }

        char[] chars = s.toCharArray();
        int length = chars.length;

        char[] charsMultipleBy16;

        if(length % 16 != 0) {
            int newDimention = length + 16 - (length % 16);
            charsMultipleBy16 = new char[newDimention];
            for(int i = 0; i < length; i++) {
                charsMultipleBy16[i] = chars[i];
            }

            for(int i = length; i < newDimention; i++) {
                charsMultipleBy16[i] = '0';
            }
        } else {
            charsMultipleBy16 = new char[length];
            for(int i = 0; i < length; i++) {
                charsMultipleBy16[i] = chars[i];
            }
        }
        return charsMultipleBy16;
    }

    public static char[] getChunk(char [] input, int offset) {
        char[] ret = new char[16];
        int k = offset;
        for(int i = 0; i < 16; i++) {
            ret[i] = input[k++];
        }
        return ret;
    }
}
