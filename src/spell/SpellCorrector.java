package spell;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class SpellCorrector implements ISpellCorrector{
    private final Trie dictionaryTrie;
    private String curSug;
    private int curSugCount;
    private final int MAX_EDIT_DISTANCE;

    {
        MAX_EDIT_DISTANCE = 2;
    }

    public SpellCorrector() {
        dictionaryTrie = new Trie();
        curSug = null;
        curSugCount = 0;
    }


    @Override
    public void useDictionary(String dictionaryFileName) throws IOException {
        File dictionary = new File(dictionaryFileName);

        Scanner scanner = new Scanner(dictionary);
        while (scanner.hasNext()) {
            dictionaryTrie.add(scanner.next().toLowerCase());
        }
    }

    @Override
    public String suggestSimilarWord(String inputWord) {

        Set<String> possibleTestWords = new TreeSet<>();
        possibleTestWords.add(inputWord);
        curSug = null;
        curSugCount = 0;
        inputWord = inputWord.toLowerCase();
        if (dictionaryTrie.find(inputWord) != null) {
            return inputWord;
        }

        int editDistance = 0;
        do {
            editDistance++;
            Set<String> possibleWords = new TreeSet<>();

            for (String testWord : possibleTestWords) {

                //deletion
                for (int i = 0; i < testWord.length(); ++i) {
                    StringBuilder tempString = new StringBuilder(testWord);
                    tempString.deleteCharAt(i);
                    possibleWords.add(tempString.toString());
                }

                //insertion
                for (int i = 0; i < testWord.length(); ++i) {
                    for (int j = 0; j < 26; ++j) {
                        StringBuilder tempString = new StringBuilder(testWord);
                        char toInsert = (char) (j + 'a');
                        tempString.insert(i, toInsert);
                        possibleWords.add(tempString.toString());
                    }
                }
                for (int j = 0; j < 26; ++j) {
                    char toInsert = (char) (j + 'a');
                    possibleWords.add(testWord + toInsert);
                }

                //transpose
                for (int i = 0; i < testWord.length() - 1; ++i) {
                    StringBuilder tempString = new StringBuilder(testWord);
                    char toMove = tempString.charAt(i);
                    tempString.deleteCharAt(i);
                    tempString.insert(i + 1, toMove);
                    possibleWords.add(tempString.toString());
                }

                //alteration
                for (int i = 0; i < testWord.length(); ++i) {
                    for (int j = 0; j < 26; ++j) {
                        StringBuilder tempString = new StringBuilder(testWord);
                        tempString.deleteCharAt(i);
                        char toInsert = (char) (j + 'a');
                        tempString.insert(i, toInsert);
                        possibleWords.add(tempString.toString());
                    }
                }

            }

            // search for edit distance 1 words with greatest value or first alphabetically
            for (String possibleWord : possibleWords) {
                INode returnVal = dictionaryTrie.find(possibleWord);
                if (returnVal != null) {
                    if (curSug == null) {
                        curSug = possibleWord;
                        curSugCount = returnVal.getValue();
                    } else {
                        /*
                        if (curSugCount == returnVal.getValue())
                         */
                        if (curSugCount != returnVal.getValue()) {
                            if (curSugCount < returnVal.getValue()) {
                                curSug = possibleWord;
                                curSugCount = returnVal.getValue();
                            }
                        }
                    }
                }

            }

            possibleTestWords = possibleWords;
        } while (curSug == null && editDistance < MAX_EDIT_DISTANCE);

        return curSug;
    }
}
