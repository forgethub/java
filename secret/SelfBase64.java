package secret;
import java.io.UnsupportedEncodingException;
 
/**
 * 1.补码 2.位运算 3.base64
 *
 * @description 学习base64加密 第一步，将每三个字节作为一组，一共是24个二进制位。
 *              第二步，将这24个二进制位分为四组，每个组有6个二进制位。 第三步，在每组前面加两个00，扩展成32个二进制位，即四个字节。
 *
 */
public class SelfBase64 {
    static private final int SIXTEENBIT = 16;
    static private final int EIGHTBIT = 8;
    static private final char PAD = '=';
    public static void main(String[] args) throws UnsupportedEncodingException {
        System.out.println(Base64.toBase64("中国fggfgfgf234234%#$%^#$$", "UTF-8"));//5Lit5Zu9ZmdnZmdmZ2YyMzQyMzQlIyQlXiMkJA==
    }
    /**
     * base64加密
     * @param str
     * @param charsetName
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String toBase64(String str, String charsetName) throws UnsupportedEncodingException {
        if (str.length() < 0)
            return "";
        byte[] text = str.getBytes(charsetName);
        char[] base = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();// 加密
        int lengthDataBits = text.length * 8;
        int fewerThan24bits = lengthDataBits % 24;// 加密字符串长度是否超过24
        int numberTriplets = lengthDataBits / 24;
        int number = fewerThan24bits != 0 ? numberTriplets + 1 : numberTriplets;// 计算字符串加密后字符总个数
        char[] toBase64Text = new char[number * 4];// 用来保存结果
        byte s1, s2, s3;
        int index = 0, order = 0;
        for (int i = 0; i < numberTriplets; i++) {
            s1 = text[index++];
            s2 = text[index++];
            s3 = text[index++];
            toBase64Text[order++] = base[(s1 & 0xFC) >> 2];// 第一个6位
            toBase64Text[order++] = base[((s1 & 0x03) << 4) + ((s2 & 0xF0) >> 4)];// 第二个6位
            toBase64Text[order++] = base[((s2 & 0x0F) << 2) + ((s3 & 0xC0) >> 6)];// 第三个6位
            toBase64Text[order++] = base[s3 & 0x3f];// 第四个6位
        }
        /**
         * 一个字节的情况：将这一个字节的8个二进制位最后一组除了前面加二个0以外，后面再加4个0。这样得到一个二位的Base64编码，
         * 再在末尾补上两个"="号。
         */
        if (fewerThan24bits == EIGHTBIT) {
            byte last = text[index++];
            toBase64Text[order++] = base[(last & 0xFC) >> 2];
            toBase64Text[order++] = base[((last & 0x03) << 4)];
            toBase64Text[order++] = PAD;
            toBase64Text[order++] = PAD;
        }
        /**
         * 二个字节的情况：将这二个字节的一共16个二进制位,转成三组，最后一组除了前面加两个0以外，后面也要加两个0。
         * 这样得到一个三位的Base64编码，再在末尾补上一个"="号。
         */
        if (fewerThan24bits == SIXTEENBIT) {
            s1 = text[index++];
            s2 = text[index++];
            toBase64Text[order++] = base[(s1 & 0xFC) >> 2];
            toBase64Text[order++] = base[(s1 & 0x03) << 4 + ((s2 & 0xF0) >> 4)];
            toBase64Text[order++] = base[(s2 & 0x0f) << 2];
            toBase64Text[order++] = PAD;
        }
        return new String(toBase64Text);
    }
}