package set1;

import org.junit.jupiter.api.Test;
import utilities.DataConverter;
import utilities.DataEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*
    Set 1 Challenge 2:
    Write a function that takes two equal-length buffers and produces their XOR combination

*/
public class Challenge2test {

    @Test
    public void testingByteArrayToHexFunctionWorksCorrectly() {
        byte[] input = {127, 27};
        String expectedOutput = "7f1b";
        assertEquals(expectedOutput, DataConverter.byteArrayToHex(input));
    }

    @Test
    // Main test answering the challenge
    public void testingXOR() {
        String input = "1c0111001f010100061a024b53535009181c";
        String key = "686974207468652062756c6c277320657965";
        String expectedOutput ="746865206b696420646f6e277420706c6179";
        assertEquals(expectedOutput, DataConverter.byteArrayToHex(DataEncoder.xor(DataConverter.hexToByteArray(input), DataConverter.hexToByteArray(key))));
    }
}
