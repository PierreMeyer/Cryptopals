package set1;

import org.junit.jupiter.api.Test;
import utilities.DataAnalyser;
import utilities.DataConverter;
import utilities.DataEncoder;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


/*
    Set 1 Challenge 4: Detect single-character XOR

    One of the 60-character strings in file4.txt has been encrypted by a single-character XOR. Need to find it.

*/
public class Challenge4test {

    @Test
    public void testingDecryptionWithSingleByteXorIsWorking() throws IOException {

        String expectedOutput = "Now that the party is jumping\n";

        //Creating an InputStream object
        InputStream inputStream = new FileInputStream("src/set1/File4.txt");
        //creating an InputStreamReader object
        InputStreamReader isReader = new InputStreamReader(inputStream);
        //Creating a BufferedReader object
        BufferedReader reader = new BufferedReader(isReader);
        List<String> lineList = new ArrayList();
        String str;
        while((str = reader.readLine())!= null){
            lineList.add(str);
        }

        double bestScore = 0.0;
        String decipheredText = "";
        int lineNumber = 0;

        while (lineNumber < lineList.size()) {
            // Why not value 127?
            for (byte i=-128; i<127; i++) {
                byte[] j = {i};
                String text = new String(DataEncoder.xor(DataConverter.hexToByteArray(lineList.get(lineNumber)), j),StandardCharsets.UTF_8);
                double score = DataAnalyser.englishScore(text);
                if (score > bestScore) {
                    bestScore = score;
                    decipheredText = text;
                }
            }
            lineNumber++;
        }
        assertEquals(expectedOutput, decipheredText);
        // Need to reformat to %
        System.out.println("Line number: " + lineNumber);
        System.out.println("English text probability: " + bestScore);
        System.out.println("Plain text: " + decipheredText);
    }

}
