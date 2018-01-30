import aes.AES_Implementation;

public class Main {

    public static void main(String[] args) {

        char c = 0x0b;
        System.out.println((byte)AES_Implementation.subByte(c, true));
    }
}
