package utilities;

import java.util.Base64;

/**
 * DataConverter class
 * @author  Pierre Meyer
 * @version 1.0.0
 */

public class DataConverter {

    /**
     * Converts an hexadecimal String into a ByteArray
     *
     * Knowledge context:
     *      - Radix is a term used to describe the number of digits utilized in a positional number system
     *      before "rolling over" to the next digit's place.
     *      e.g. in the base 16 hexadecimal system, there is a total of 16 digits used ('0'...'9','a'...'f'),
     *      therefore, its radix is 16
     *      - 1 hexadecimal character represents a nibble (4 bits, 2^4 = 16), which is half of a byte (8 bits)
     *
     * @param   input   Hexadecimal String to be converted into a ByteArray
     * @return  ByteArray
     *
     * To be done:
     * - Add validity check of the string, only including 0..f characters?
     * - Add validity check that the string has an even length?
     */

    public static byte[] hexToByteArray(String input){
        int len = input.length();
        byte[] output = new byte[len / 2];

        // Character.digit method returns the numeric value of a specified character in the specified radix
        for (int i = 0; i < len; i += 2)
            output[i / 2] = (byte) ((Character.digit(input.charAt(i), 16) << 4)
                             + Character.digit(input.charAt(i+1), 16));

        return output;
    }

    /**
     * Converts a ByteArray into an hexadecimal String
     * @param   input   ByteArray to be converted into an hexadecimal String
     * @return  hexadecimal String
     * To be done:
     * - Add validity check of the string, only including 0..f characters?
     * - Add comments for bit right shifts
     */

    public static String byteArrayToHex(byte[] input){
        char[] HEXARRAY = "0123456789abcdef".toCharArray();
        char[] hexChars = new char[input.length * 2];
        for (int i=0; i<input.length; i++) {
            // & is a bitwise operator and compares each operand bitwise (vs. && operates on boolean operands)
            int c = input[i] & 0xFF;
            // Unsigned right shift >>> will always put a 0 in the left most bit
            // Signed right shift >> will put a 1 or a 0 depending on what the sign of it is
            hexChars[i*2] = HEXARRAY[c >>> 4];
            hexChars[i*2 + 1] = HEXARRAY[c & 0x0F];
        }
        return new String(hexChars);
    }

    /**
     * Converts a ByteArray into a base64 String
     * @param   input   ByteArray to process
     * @return  Base64 String
     * Final version
     */

    public static String byteArrayToBase64(byte[] input) {
        return Base64.getEncoder().encodeToString(input);
    }

    /**
     * Converts a base64 String into a ByteArray
     * @param   input   Base64 string to process
     * @return  ByteArray
     * Final version
     */

    public static byte[] base64TobyteArray(String input) {
        return Base64.getDecoder().decode(input);
    }

}