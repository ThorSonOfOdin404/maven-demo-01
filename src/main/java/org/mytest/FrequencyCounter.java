package org.mytest;

import java.util.*;

public class FrequencyCounter {
    public static void main(String[] args) {
        FrequencyCounter counter = new FrequencyCounter();
        /*System.out.println(
                counter.getMaxFrequencyChar("AFGHANISTAN")
        );
        System.out.println(
                counter.getMaxFrequencyChar("INDIA")
        );
*/

        String paragraph = "Growth begins the moment you stop running from discomfort and start listening to what it’s teaching you. Patience is not waiting; it’s the quiet confidence that what’s meant for you will unfold at the right time. True strength doesn’t come from what you conquer, but from what you choose not to surrender to. The calmest minds often belong to those who’ve faced the loudest storms and learned to find peace within them.";

        System.out.println( "\nWord with max frequency : "
                + counter.getMaxFrequencyWord(paragraph) );

    }

    private Character getMaxFrequencyChar(CharSequence seq) {
        char[] chars = seq.toString().toCharArray();
        Map<Character, Integer> frequencyMap = new HashMap<>();
        for (char ch : chars) {
            if (frequencyMap.containsKey(ch)) {
                int frequency = frequencyMap.get(ch);
                frequencyMap.put(ch, frequency + 1);
            } else {
                frequencyMap.put(ch, 1);
            }
        }

        char maxFreqChar = '\u0000';
        int maxFreq = -1;
        for (Map.Entry<Character, Integer> entry : frequencyMap.entrySet()) {
            if (entry.getValue() >= maxFreq) {
                maxFreqChar = entry.getKey();
                maxFreq = entry.getValue();
            }
        }
        System.out.println(frequencyMap);
        return maxFreqChar;

    }

    private String getMaxFrequencyWord(CharSequence seq) {
        String[] words = seq.toString().toLowerCase().split("[^A-Za-z0-9']+");
        Map<String, Integer> frequencyMap = new TreeMap<>();
        for (String word : words) {
            if (frequencyMap.containsKey(word)) {
                int frequency = frequencyMap.get(word);
                frequencyMap.put(word, frequency + 1);
            } else {
                frequencyMap.put(word, 1);
            }
        }

        String maxFreqWord = "";
        int maxFreq = -1;
        for (Map.Entry<String, Integer> entry : frequencyMap.entrySet()) {
            if (entry.getValue() > maxFreq) {
                maxFreqWord = entry.getKey();
                maxFreq = entry.getValue();
            }
        }
        System.out.println("\n" + frequencyMap);
        System.out.println("Max Frequency : " + maxFreq);
        return maxFreqWord;
    }
}
