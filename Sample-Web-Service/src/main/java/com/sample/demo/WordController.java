package com.sample.demo;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WordController {

    static final int SIZE = 26;

    /**
     *  If the Pyramid Word follows the value sequence. sample {b=5, a=4,c=3, n=2, q=1} 
     * @param word
     * 
     */
    @RequestMapping("v1/isPyramidWord/{word}")
    public boolean isPyramidWordWithSorted(@PathVariable("word") String word) {
        Map<Character, Integer> result = getCharWithFrequency(word);
        if (result.isEmpty()) {
            return false;
        }
        return isNotDuplicateValues(getSortedEntries(result));
    }
    
    /**
     * If pyramid word just checks whether same occurrence have more than once .  {b=5, c=3, n=2, q=1} 
     * @param word
     * @return
     */
    @RequestMapping("v2/isPyramidWord/{word}")
    public boolean isPyramidWord(@PathVariable("word") String word) {
        Map<Character, Integer> result = getCharWithFrequency(word);
        if (result.isEmpty()) {
            return false;
        }
        return isNotDuplicates(result);
    }

    private Map<Character, Integer> getCharWithFrequency(String str) {        
        HashMap<Character, Integer> charMap = new HashMap<>();
        int[] freqArray = new int[SIZE];

        for (int i = 0; i < str.length(); i++)
            freqArray[str.charAt(i) - 'a']++;

        for (int i = 0; i < str.length(); i++) {
            if (freqArray[str.charAt(i) - 'a'] != 0) {
                charMap.put(str.charAt(i), freqArray[str.charAt(i) - 'a']);
                freqArray[str.charAt(i) - 'a'] = 0;
            }
        }

        return charMap;

    }

    private boolean isNotDuplicateValues(Map<Character, Integer> inputMap) {
        int temp = 0;
        if (inputMap.isEmpty()) {
            return false;
        }
        Object[] keys = inputMap.keySet().toArray();
        for (int x = 0; x < keys.length; x++) {
            
            if (temp != 0 && temp != inputMap.get(keys[x]) - 1) {
                return false;
            }
            
            Object value = inputMap.get(keys[x]);
            temp = inputMap.get(keys[x]);
            inputMap.remove(keys[x]);
            if (inputMap.containsValue(value)) {
                return false;
            }

        }
        return true;
    }
    
    private  boolean isNotDuplicates(Map<Character, Integer> inputMap) {
        Object[] keys = inputMap.keySet().toArray();
        for (int x = 0; x < keys.length; x++) {
            Object value = inputMap.get(keys[x]);
            inputMap.remove(keys[x]);
            if (inputMap.containsValue(value)) {
                return false;
            }
        }
        return true;

    }

    private Map<Character, Integer> getSortedEntries(Map<Character, Integer> charMap) {
        HashMap<Character, Integer> sortedResult = new LinkedHashMap<>();
        charMap.entrySet().stream().sorted(Map.Entry.<Character, Integer>comparingByValue())
                .forEachOrdered(x -> sortedResult.put(x.getKey(), x.getValue()));

        return sortedResult;
    }
    
}
