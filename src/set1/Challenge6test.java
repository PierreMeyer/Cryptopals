package set1;

import org.junit.jupiter.api.Test;
import utilities.DataAnalyser;
import utilities.DataConverter;
import utilities.DataEncoder;

import javax.print.DocFlavor;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

/*
    Set 1 Challenge 6: Break repeating-key XOR

    File6.txt has been base64'd after being encrypted with repeating-key XOR. Decrypt it.

    1. Let KEYSIZE be the guessed length of the key; try values from 2 to (say) 40.
    2. Write a function to compute the edit distance/Hamming distance between two strings. The Hamming distance is just the number of differing bits. The distance between:
        this is a test
    and
        wokka wokka!!!
    is 37. Make sure your code agrees before you proceed.
    3. For each KEYSIZE, take the first KEYSIZE worth of bytes, and the second KEYSIZE worth of bytes, and find the edit distance between them. Normalize this result by dividing by KEYSIZE.
    4. The KEYSIZE with the smallest normalized edit distance is probably the key. You could proceed perhaps with the smallest 2-3 KEYSIZE values. Or take 4 KEYSIZE blocks instead of 2 and average the distances.
    5. Now that you probably know the KEYSIZE: break the ciphertext into blocks of KEYSIZE length.
    6. Now transpose the blocks: make a block that is the first byte of every block, and a block that is the second byte of every block, and so on.
    7. Solve each block as if it was single-character XOR. You already have code to do this.
    8. For each block, the single-byte XOR key that produces the best looking histogram is the repeating-key XOR key byte for that block. Put them together and you have the key.

*/
public class Challenge6test {

    @Test
    public void testingHummingDistanceCalculationIsWorking() {
        String input1 = "this is a test";
        String input2 = "wokka wokka!!!";
        int expectedHummingDistance = 37;
        assertEquals(expectedHummingDistance, DataAnalyser.hummingDistance(input1.getBytes(StandardCharsets.UTF_8), input2.getBytes(StandardCharsets.UTF_8)));
    }

    @Test
    public void testingBase64ToByteArrayWorksCorrectly() {
        String input = "AgQGCAoMDhASFA==";
        byte[] expectedOutput = {2, 4, 6, 8, 10, 12, 14, 16, 18, 20};
        assertArrayEquals(expectedOutput, DataConverter.base64TobyteArray(input));
    }

    @Test
    public void findKey() throws IOException {
        InputStream inputStream = new FileInputStream("src/set1/File6.txt");
        InputStreamReader isReader = new InputStreamReader(inputStream);
        BufferedReader reader = new BufferedReader(isReader);
        StringBuilder encodedString = new StringBuilder();
        String line;
        while((line = reader.readLine())!= null){
            encodedString.append(line);
        }
        byte[] encodedByteArray = DataConverter.base64TobyteArray(encodedString.toString());


        // Using 4 keysize worth of bytes to calculate humming distance
        int likelyKeyLength = 0;
        double lowestHummingDistance = 1;
        for (int keySize=2 ; keySize<41 ;keySize++) {
            byte[] block1 = Arrays.copyOfRange(encodedByteArray,0,keySize*4);
            byte[] block2 = Arrays.copyOfRange(encodedByteArray,keySize*4,keySize*2*4);
            double normalisedEditDistance = DataAnalyser.hummingDistance(block1, block2)/Double.valueOf(keySize*8*4);
            if (normalisedEditDistance < lowestHummingDistance) {
                lowestHummingDistance = normalisedEditDistance;
                likelyKeyLength = keySize;
            }
        }
        System.out.println("Likely key size: " + likelyKeyLength);

        // Transposing step
        int[] counter = new int[likelyKeyLength];
        byte[][] transposedByteArray = new byte[likelyKeyLength][encodedByteArray.length / likelyKeyLength + 1];
        for (int i = 0; i < encodedByteArray.length; i++) {
            transposedByteArray[i % likelyKeyLength][counter[i % likelyKeyLength]] = encodedByteArray[i];
            counter[i % likelyKeyLength]++;
        }

        // Find key character by character
        byte[] key = new byte[likelyKeyLength];
        for (int i = 0; i < likelyKeyLength; i++) {
            double bestScore = 0.0;
            byte eK = 0;
            for (int j = 0; j < transposedByteArray[i].length; j++) {
                for (byte k = -128; k < 127; k++) {
                    byte[] m = {k};
                    String text = new String(DataEncoder.xor(transposedByteArray[i], m), StandardCharsets.UTF_8);
                    double score = DataAnalyser.englishScore(text);
                    if (score > bestScore) {
                        bestScore = score;
                        eK = k;
                    }
                }
            }
            key[i] = eK;
         }

        System.out.println("Key length: " + likelyKeyLength);
        System.out.println("Key: " + new String(key));
        System.out.println(new String(DataEncoder.xor(encodedByteArray, key)));

    }
}
