package com.caved_in.commons.utilities;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtils {
    private static MessageDigest MD5;
    private static MessageDigest SHA1;

    private static StringBuilder MD5_BUILDER;
    private static StringBuilder SHA1_BUILDER;

    static {
        try {
            MD5 = MessageDigest.getInstance("MD5");
            SHA1 = MessageDigest.getInstance("SHA-1");

            MD5_BUILDER = new StringBuilder(32);
            SHA1_BUILDER = new StringBuilder(40);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates an MD5 hash of the input returning the 32 char hex string.
     *
     * @param input The input
     * @return The hex string
     * @throws UnsupportedEncodingException if UTF-8 is not supported.
     */
    public static String md5(String input) throws UnsupportedEncodingException {
        MD5_BUILDER.delete(0, MD5_BUILDER.length());
        MD5.reset();

        for (byte b : MD5.digest(input.getBytes("UTF-8"))) {
            String hex = Integer.toHexString(0x000000FF & b);

            if (hex.length() % 2 != 0) {
                MD5_BUILDER.append("0");
            }

            MD5_BUILDER.append(hex);
        }

        return MD5_BUILDER.toString();
    }

    /**
     * Creates an SHA1 hash of the input returning the 40 char hex string.
     *
     * @param input The input
     * @return The hex string
     * @throws UnsupportedEncodingException if UTF-8 is not supported.
     */
    public static String sha1(String input) throws UnsupportedEncodingException {
        SHA1_BUILDER.delete(0, SHA1_BUILDER.length());
        SHA1.reset();

        for (byte b : SHA1.digest(input.getBytes("UTF-8"))) {
            String hex = Integer.toHexString(0x000000FF & b);

            if (hex.length() % 2 != 0) {
                SHA1_BUILDER.append("0");
            }

            SHA1_BUILDER.append(hex);
        }

        return SHA1_BUILDER.toString();
    }
}
