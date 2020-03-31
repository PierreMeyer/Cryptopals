package utilities;

/**
 * DataEncoder object
 * @author  Pierre Meyer
 * @version 1.0.0
 */

public class DataEncoder {

    /**
     * XOR two ByteArrays
     *
     * @param input input as a ByteArray XOR will be applied to
     * @param key   key as a ByteArray XOR is using
     * @return      result as a ByteArray
     */
    public static byte[] xor(byte[] input, byte[] key) {
        byte[] output = new byte[input.length];

        int j = 0;
        for (int i=0; i<input.length; i++) {
            // If key is smaller than array to encode, points back to the start of the key when reaching the end of it
            if (j == key.length)
                j = 0;
            output[i] = (byte) (input[i] ^ key[j]);
            j++;
        }
        return output;
    }
}
