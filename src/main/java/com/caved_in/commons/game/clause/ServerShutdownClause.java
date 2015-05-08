package com.caved_in.commons.game.clause;

/**
 * A Clause used to determine whether or not to perform a server shutdown.
 */
public interface ServerShutdownClause {

    /**
     * If the conditions defined in the method are met, then the server will be shut down.
     * @return true to shutdown the server, false otherwise.
     */
    boolean shutdown();
}
