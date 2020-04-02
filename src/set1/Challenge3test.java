package set1;

import org.junit.jupiter.api.Test;
import utilities.DataAnalyser;
import utilities.DataConverter;
import utilities.DataEncoder;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*
    Set 1 Challenge 3:
    Message has been XOR'd against a single character. Find the key, decrypt the message.

    Tip: Devise some method for "scoring" a piece of English plaintext.
    Character frequency is a good metric. Evaluate each output and choose the one with the best score.

*/
public class Challenge3test {

    @Test
    public void testingDecryptionWithSingleByteXorIsWorking() {
        String input = "1b37373331363f78151b7f2b783431333d78397828372d363c78373e783a393b3736";
        String expectedOutput = "Cooking MC's like a pound of bacon";

        double bestScore = 0.0f;
        String decipheredText = "";

        // Why not value 127?
        for (byte i=-128; i<127; i++) {
            byte[] j = {i};
            String text = new String(DataEncoder.xor(DataConverter.hexToByteArray(input), j),StandardCharsets.UTF_8);
            double score = DataAnalyser.englishScore(text);
            if (score > bestScore) {
                bestScore = score;
                decipheredText = text;
            }
        }
        assertEquals(expectedOutput, decipheredText);
        // Need to reformat to %
        System.out.println("English text probability: " + bestScore);
        System.out.println("Plain text: "+decipheredText);
    }

}
