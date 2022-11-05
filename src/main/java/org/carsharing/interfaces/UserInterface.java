package org.carsharing.interfaces;

import java.util.LinkedHashMap;
import java.util.Set;
public class UserInterface implements InteractiveInterface, ScanUserInput {
    protected LinkedHashMap<Integer, String> interfaceOptions;

    public UserInterface() {
        interfaceOptions = new LinkedHashMap<>();
    }

    @Override
    public void printAndRun() {
        boolean loop = true;

        Set<Integer> keys = interfaceOptions.keySet();

        while (loop) {
            for (Integer key : keys) {
                System.out.printf("%d. %s\n", key, interfaceOptions.get(key));
            }
            String input = inputRequest();
            if (checkInput(input) && Integer.parseInt(input) != 0) {
                execute(Integer.parseInt(input));
            } else if (Integer.parseInt(input) == 0) {
                execute(Integer.parseInt(input));
                loop = false;
            }
        }

    }

    @Override
    public void execute(int input) {}

    @Override
    public boolean checkInput(String input) {
        boolean isKey = false;

        Set<Integer> keys = interfaceOptions.keySet();

        for (Integer key : keys) {
            if (key.toString().equals(input)) {
                isKey = true;
            }
        }
        if (isKey) {
            return true;
        } else {
            return false;
        }
    }
}
