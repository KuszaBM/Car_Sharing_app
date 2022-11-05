package org.carsharing.interfaces;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public interface ScanUserInput {
    public default boolean checkInput(String input, char[] availableCharacters) {
        boolean isCorrect = false;
        if (input == null || input.length() > 1) {
            return false;
        } else {
            char inputToChar = input.charAt(0);
            return true;
        }
    }
    public default boolean checkInput(String input) {
        return false;
    }
    public default String inputRequest() {
        String temp = null;
        boolean isCorrectInput = false;
        while (!isCorrectInput){
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                String input = reader.readLine();
                if(input != null) {
                    isCorrectInput = true;
                }
                temp = input;
            } catch(IOException e){
                temp = "no input";
            }
        }
        return temp;
    }
}
