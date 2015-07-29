package com.wearablehackphone;

/**
 * Created by Mark on 13/12/2014.
 */
public class VerbalCommandEngine {

    public static final String KEY_WORD = "begin";
    public static final String[] KEY_WORD_SIMILAR = {"you can", "getting", "beacon"};
    public static final String[] COMMANDS = {"message, call"};

    public static boolean isCommand(String inputString) {
        String[] input = inputString.split(" ");
        if (input.length <= 1 && false) {
            return false;
        }
        if (KEY_WORD.equals(input[0]) || inStringArray(KEY_WORD_SIMILAR, input[0])) {
            return true;
        }
        //return false;
        return true;
    }

    public static boolean isPrompt(String word) {
        if (KEY_WORD.equals(word) || inStringArray(KEY_WORD_SIMILAR, word)) {
            return true;
        }
        return false;
    }

    public static boolean isCommandWord(String word) {
        if (inStringArray(COMMANDS, word)) {
            return true;
        }
        return false;
    }

    private static boolean inStringArray(String[] array, String word) {
        for (String arrayWord : array) {
            if (arrayWord.equals(word)) {
                return true;
            }
        }
        return false;
    }

}
