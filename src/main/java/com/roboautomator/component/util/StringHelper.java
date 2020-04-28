package com.roboautomator.component.util;

public class StringHelper {

    private StringHelper() {
        // Empty private constructor
    }

    /**
     * <p>Removes the following characters to protect users from log injection:</p>
     * <ul>
     *     <li>NEWLINE - \n</li>
     *     <li>CARRIAGE RETURN - \r</li>
     *     <li>TAB - \t</li>
     * </ul>
     *
     * <p>Replaces the characters using "".</p>
     *
     * @param stringToParse the String to clean
     *
     * @return the stringToParse without the characters that have been removed.
     */
    public static String cleanString(String stringToParse) {
        return stringToParse.replaceAll("[\n|\r|\t]", "");
    }
}