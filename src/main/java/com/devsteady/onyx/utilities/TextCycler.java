package com.devsteady.onyx.utilities;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;

/**
 * A class which rotates through text to keep it within a specific length limit.
 *
 * @author Glen Husman
 */
public class TextCycler {
    protected final String textPrefix;
    protected final String originalText;
    protected final int trimLength;
    protected int currentTrimIndex = 0;
    /**
     * Used to cache the string instance, which (if constant and applicabgle) will be used instead of rebuilding the builder each tick.
     */
    private String cachedValue = null;

    /**
     * Creates a text cycler with the given text.
     *
     * @param text       The untrimmed text of the cycler.
     * @param trimLength The length of the trimmed text.
     */
    public TextCycler(String text, int trimLength) {
        this(null, text, trimLength);
    }

    /**
     * Gets the length to which text is trimmed by this cycler.
     *
     * @return The trim length of this text cycler instance.
     */
    public int getTrimLength() {
        return trimLength;
    }

    /**
     * Creates a text cycler with the given text and an uncycled prefix.
     * <p>
     * Note that the text cycler does not automatically append spacing to the body text string.
     * The caller may wish to do so, such as via a call to {@link StringUtils#leftPad(String, int)}.
     * If this is called with the desired pad length on the input string, the padding affect will occur.
     *
     * @param prefix     The constant prefix of the text.
     * @param text       The untrimmed text of the cycler.
     * @param trimLength The length of the trimmed text.
     */
    public TextCycler(String prefix, String text, int trimLength) {
        if (prefix == null) {
            prefix = StringUtils.EMPTY;
        }
        Validate.notEmpty(text, "Text must be specified.");
        Validate.isTrue(trimLength > 0, "The length to trim to must be positive.");
        this.trimLength = trimLength;
        originalText = text;
        textPrefix = prefix.trim();
        if (originalText.length() <= this.trimLength - textPrefix.length()) {
            cachedValue = textPrefix + originalText;
        }
    }

    /**
     * Gets the untrimmed constant prefix to the text in this cycler.
     *
     * @return A non-null string representing the prefix to the cycled text.
     */
    public String getPrefix() {
        return textPrefix;
    }

    /**
     * Gets the untrimmed text in this cycler which is cycled.
     *
     * @return A non-null string representing the original text.
     */
    public String getText() {
        return originalText;
    }

    /**
     * Ticks the text cycler, incrementing the start position of the text.
     *
     * @return The <em>old</em> value of the text cycler, including the prefix. This value can also be retrieved by the appropriate methods, however its computation is not cached.
     */
    public String tick() {
        String val = toString();
// Increment counter
        currentTrimIndex = (currentTrimIndex + 1) % (originalText.length());
        return val;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + currentTrimIndex;
        result = prime * result
                + ((originalText == null) ? 0 : originalText.hashCode());
        result = prime * result + ((textPrefix == null) ? 0 : textPrefix.hashCode());
        result = prime * result + trimLength;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof TextCycler)) {
            return false;
        }
        TextCycler other = (TextCycler) obj;
        if (currentTrimIndex != other.currentTrimIndex) {
            return false;
        }
        if (originalText == null) {
            if (other.originalText != null) {
                return false;
            }
        } else if (!originalText.equals(other.originalText)) {
            return false;
        }
        if (textPrefix == null) {
            if (other.textPrefix != null) {
                return false;
            }
        } else if (!textPrefix.equals(other.textPrefix)) {
            return false;
        }
        return trimLength == other.trimLength;
    }

    /**
     * Computes the current value of this text cycler instance.
     *
     * @return The current trimmed text value.
     */
    @Override
    public String toString() {
        if (cachedValue != null) {
            // No need to toString twice
            return cachedValue;
        }
        StringBuilder display = new StringBuilder(originalText.substring(currentTrimIndex, Math.min(currentTrimIndex + trimLength, originalText.length())));
        if (display.length() < trimLength) {
            int add = trimLength - display.length();
            display.append(originalText.substring(0, Math.min(add, originalText.length())));
        }
        // Add prefix if needed
        if (textPrefix.length() > 0) {
            int newLen = trimLength - textPrefix.length();
            //Bukkit.getLogger().log(Level.INFO, "New length: " + newLen + ", existing display len: " + display.length() + "(val = '" + display.toString() + "')");
            display.replace(newLen, display.length(), "");
            display.insert(0, textPrefix);
        }
        return display.toString();
    }
}