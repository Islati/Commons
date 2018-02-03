package com.devsteady.onyx.utilities;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

/**
 * A util to scroll coloured Strings
 *
 * @author Chinwe
 */
public class StringScroller {
    private static final char COLOUR_CHAR = '&';
    private int position;
    private List<String> list = new ArrayList<>();
    private ChatColor colour = ChatColor.RESET;


    /**
     * @param message      The String to scroll
     * @param width        The width of the window to scroll across (i.e. 16 for signs)
     * @param spaceBetween The amount of spaces between each repetition
     * @param colourChar   The colour code character you're using (i.e. & or �)
     */
    public StringScroller(String message, int width, int spaceBetween, char colourChar) {
        // Validation
        // String is too short for window
        if (message.length() < width) {
            StringBuilder sb = new StringBuilder(message);
            while (sb.length() < width) {
                sb.append(" ");
            }
            message = sb.toString();
        }

        // Allow for colours which add 2 to the width
        width -= 2;

        // Invalid width/space size
        if (width < 1) {
            width = 1;
        }

        if (spaceBetween < 0) {
            spaceBetween = 0;
        }

        // Change to &
        if (colourChar != '&') {
            message = ChatColor.translateAlternateColorCodes(colourChar, message);
        }


        int msgLength = message.length();

        // Add substrings
        for (int i = 0; i < msgLength - width; i++) {
            list.add(message.substring(i, i + width));
        }

        // Add space between repeats
        StringBuilder space = new StringBuilder();
        for (int i = 0; i < spaceBetween; ++i) {
            list.add(message.substring(msgLength - width + (i > width ? width : i), msgLength) + space);
            if (space.length() < width) {
                space.append(" ");
            }
        }

        // Wrap
        for (int i = 0; i < width - spaceBetween; ++i) {
            list.add(message.substring(msgLength - width + spaceBetween + i, msgLength) + space + message.substring(0, i));
        }

        int spaceLength = space.length();
        // Join up
        for (int i = 0; i < spaceBetween; i++) {
            if (i > spaceLength) {
                break;
            }
            list.add(space.substring(0, spaceLength - i) + message.substring(0, width - (spaceBetween > width ? width : spaceBetween) + i));
        }
    }

    /**
     * @return Gets the next String to display
     */
    public String next() {
        StringBuilder sb = getNext();
        int sbLength = sb.length();
        if (sb.charAt(sbLength - 1) == COLOUR_CHAR) {
            sb.setCharAt(sbLength - 1, ' ');
        }

        if (sb.charAt(0) == COLOUR_CHAR) {
            ChatColor c = ChatColor.getByChar(sb.charAt(1));
            if (c != null) {
                colour = c;
                sb = getNext();
                if (sb.charAt(0) != ' ') {
                    sb.setCharAt(0, ' ');
                }
            }
        }

        return colour + sb.toString();

    }

    private StringBuilder getNext() {
        return new StringBuilder(list.get(position++ % list.size()).substring(0));
    }

}
