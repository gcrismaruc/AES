import aes.AESImplementation;
import aes.MatrixUtils;
import aes.TextUtils;

public class Main {

    public static void main(String[] args) {

        char[] key = {0x11, 0x23, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF,
                0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF};

        char[] expandedKey = AESImplementation.keyExpansion(key);


        String input = "Cool Fancy Text Generator is a copy and paste font changer that creates Twitter, Facebook, Instagram fonts. It converts a normal text to different free cool fonts styles, such as tattoo fonts, calligraphy fonts, web script fonts, cursive fonts, handwriting fonts, old English fonts, word fonts, pretty fonts, font art...\n" +
                "Basically, Cool Text Generator a cute copy and paste font generator online, font maker, font creator, font changer, special text maker, cool text creator, weird text changer, word art generator, fancy letter generator, webfont generator, signature maker, signature creator, free text generator, logo animation maker, font manager... This tool helps generate text symbols, cool Unicode fancy letters, fancy writing, fancy fonts, stylish fonts, cool symbols, cool symbol text, fancy letters, letter fonts, funky cool text, fancy chat message, fancy facebook status, fancy nick, love text, beautiful ASCII text art, text pictures, emoticons, emoji text... with different serif, sans-serif font types.\n" +
                "Fancy Text Generator is fun and handy, just copy these stylish beautiful design text and paste them anywhere such as in Facebook, Twitter, Whatsapp, Snapchat, Instagram Bio... with fancy fonts and impress your friends. If you are on mobile, you can also check out the fancy text ios app and fancy text android app";
        char[] inputChars = TextUtils.getArrayFromString(input);

        char[] encryptedText;
        char[] decryptedText;

        StringBuilder encryptedStringBuilder = new StringBuilder();
        StringBuilder decryptedStringBuilder = new StringBuilder();

        int offset = 0;

        System.out.println("\nEncrypting...");
        while (offset < inputChars.length) {
            char[] textToEncrypt = TextUtils.getChunk(inputChars, offset);

            char[] encryptedChunk = AESImplementation.encrypt(textToEncrypt, expandedKey);
            encryptedStringBuilder.append(encryptedChunk);
            offset += 16;
        }


        encryptedText = encryptedStringBuilder.toString().toCharArray();
        MatrixUtils.printArrayAsHexa(encryptedText);

        offset = 0;
        System.out.println("\nDecrypting...");
        while (offset < encryptedText.length) {

            char[] textToDecrypt = TextUtils.getChunk(encryptedText, offset);
            offset += 16;

            char[] decryptedChunk = AESImplementation.decrypt(textToDecrypt, expandedKey);
            decryptedStringBuilder.append(decryptedChunk);
        }

        decryptedText = decryptedStringBuilder.toString().toCharArray();

        System.out.println("\nDecriptat hexa:");
        MatrixUtils.printArrayAsHexa(decryptedText);

        System.out.println("\nDecriptat");
        System.out.println(decryptedStringBuilder.toString());
    }
}
