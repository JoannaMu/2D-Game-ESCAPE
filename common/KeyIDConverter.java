package com.group8project.common;


import java.util.ArrayList;
import java.util.Arrays;

/**
 * The class that converts String KeyID/LockID to integer such that it is easily comparable
 */
public class KeyIDConverter {
    // ArrayList that holds the string format of door lockID corresponding to index number as keyID. 0 = ), 1 = !, 2 = @, etc.
    private static ArrayList<String> locks = new ArrayList<>(Arrays.asList(")", "!", "@", "#", "$", "%", "^", "&", "*", "("));

    /**
     * @param doorString the door LockID in string format
     * @return the keyID in integer format
     */
    public static int doorStringToKeyID(String doorString) {
        return locks.indexOf(doorString);
    }

    /**
     * @param keyString the key keyID in string format
     * @return the keyID in integer format
     */
    public static int keyStringToKeyID(String keyString) {
        return Integer.parseInt(keyString);
    }
}
