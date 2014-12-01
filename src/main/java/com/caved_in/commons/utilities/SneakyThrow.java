package com.caved_in.commons.utilities;

/**
 * Credits for the SneakyThrow class go to Spigot, from the org.spigotmc package!
 * <p/>
 * http://www.spigotmc.org
 */
public class SneakyThrow {

    public static void sneaky(Throwable t) {
        throw SneakyThrow.<RuntimeException>superSneaky(t);
    }

    private static <T extends Throwable> T superSneaky(Throwable t) throws T {
        throw (T) t;
    }
}