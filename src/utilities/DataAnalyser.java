package utilities;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DataAnalyser {

    final static Map<Character, Double> letterFrequencyEnglish = Map.ofEntries(
            Map.entry('a', 0.0651738), Map.entry('b', 0.0124248), Map.entry('c', 0.0217339),
            Map.entry('d', 0.0349835), Map.entry('e', 0.1041442), Map.entry('f', 0.0197881),
            Map.entry('g', 0.0158610), Map.entry('h', 0.0492888), Map.entry('i', 0.0558094),
            Map.entry('j', 0.0009033), Map.entry('k', 0.0050529), Map.entry('l', 0.0331490),
            Map.entry('m', 0.0202124), Map.entry('n', 0.0564513), Map.entry('o', 0.0596302),
            Map.entry('p', 0.0137645), Map.entry('q', 0.0008606), Map.entry('r', 0.0497563),
            Map.entry('s', 0.0515760), Map.entry('t', 0.0729357), Map.entry('u', 0.0225134),
            Map.entry('v', 0.0082903), Map.entry('w', 0.0171272), Map.entry('x', 0.0013692),
            Map.entry('y', 0.0145984), Map.entry('z', 0.0007836), Map.entry(' ', 0.1918182));

    /**
     * Returns am English text probability score, using letter frequencies in english literature
     *
     * @param   input   Text evaluated
     * @return  score   English probability score. The closer to one, the more likely the piece of text is in English
     */
    public static double englishScore(String input) {
        // Local variables initialisation
        double score = 0.0;
        Map<Character, Integer> charCount = new HashMap<Character, Integer>();
        for (Character key: letterFrequencyEnglish.keySet()) {
            charCount.put(key, 0);
        }
        // Counting occurrences of all letters in the alphabet
        for (Character c: input.toLowerCase().toCharArray()) {
            if (letterFrequencyEnglish.keySet().contains(c)) {
                Integer originalValue = charCount.get(c);
                charCount.replace(c, originalValue + 1);
            }
        }
        // Calculating 'English text probability' using the Bhattacharyya coefficint

        for (Character c: charCount.keySet())
            score += Math.sqrt(letterFrequencyEnglish.get(c) * charCount.get(c) / input.length());

        return score;
    }

    /**
     * Returns the humming distance (number of different bits) between two strings already converted to byte arrays
     * @param   input1 byte array
     * @param   input2 byte array
     * @return  humming distance
     */
    public static int hummingDistance(byte[] input1, byte[] input2) {
        int count = 0;
        for (int i=0 ; i<input1.length ; i++)
            count += Integer.bitCount(input1[i] ^ input2[i]);
        return count;
    }

}
