package set1;

import org.junit.jupiter.api.Test;
import utilities.DataConverter;
import utilities.DataEncoder;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*
    Set 1 Challenge 5: Implement repeating-key XOR

    Encrypt text, under the key "ICE", using repeating-key XOR.

    In repeating-key XOR, sequentially apply each byte of the key:
    the first byte of plaintext will be XOR'd against I, the next C, the next E,
    then I again for the 4th byte, and so on.

*/
public class Challenge5test {

    @Test
    public void testingRepeatingKeyXorIsWorking() {
        String input = "Burning 'em, if you ain't quick and nimble\n" +
                "I go crazy when I hear a cymbal";
        String expectedOutput = "0b3637272a2b2e63622c2e69692a23693a2a3c6324202d623d63343c2a26226324272765272" +
                "a282b2f20430a652e2c652a3124333a653e2b2027630c692b20283165286326302e27282f";
        String key = "ICE";
        assertEquals(expectedOutput, DataConverter.byteArrayToHex(DataEncoder.xor(input.getBytes(StandardCharsets.UTF_8),key.getBytes(StandardCharsets.UTF_8))));
    }

}
