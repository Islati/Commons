package com.devsteady.onyx.plugin;

public interface CommonPlugin {
    /**
     * Equal to the {@link org.bukkit.plugin.java.JavaPlugin#onEnable} method.
     * All operations that you want to be called when the plugin is enabled will
     * go here.
     * <p>
     * Note: Do NOT initialize your configuration (unless explicitly required) in this method.
     * Config initialization should be handled in the {@link #initConfig} method.
     */
    void startup();

    /**
     * All operations that you wish to happen when your plugin is disabled; Handler un-hooking,
     * and thread(s) shutdowns / handling are automagically handled, so you need not worry about doing so.
     */
    void shutdown();

    /**
     * The version of the plugin; Not utilized anywhere, though it's semantically correct.
     *
     * @return the version-identifier of the plugin
     */
    String getVersion();

    /**
     * The author of the plugin; Again, not utilized internally but it's semantically correct to have the plugin author.
     *
     * @return
     */
    String getAuthor();

    /**
     * Called before the {@link #startup} method to handle the initialization of your plugin config.
     */
    void initConfig();
}
