package com.caved_in.commons.plugin;

import com.caved_in.commons.utilities.LogUtils;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

public class Plugins {
	public enum ExtractWhen {ALWAYS, IF_NOT_EXISTS, IF_NEWER}

	public static final Charset TARGET_ENCODING = Charset.forName("UTF-8");
	public static final Charset SOURCE_ENCODING = Charset.forName("UTF-8");

	private static PluginManager pluginManager = Bukkit.getPluginManager();

	public static boolean pluginExists(String name) {
		return pluginManager.isPluginEnabled(name);
	}

	public static void disablePlugin(Plugin plugin) {
		pluginManager.disablePlugin(plugin);
	}

	public static boolean enablePlugin(String name) {
		Plugin plugin = pluginManager.getPlugin(name);
		if (plugin == null || pluginManager.isPluginEnabled(name)) {
			return false;
		}
		pluginManager.enablePlugin(plugin);
		return true;
	}

	public static Plugin getPlugin(String name) {
		if (!pluginExists(name)) {
			return null;
		}

		return pluginManager.getPlugin(name);
	}

	public static boolean hasDataFolder(Plugin plugin) {
		return plugin.getDataFolder().exists();
	}

	public static boolean makeDataFolder(Plugin plugin) {
		return hasDataFolder(plugin) || plugin.getDataFolder().mkdirs();
	}

	public static void unregisterHooks(Plugin plugin) {
		Server server = plugin.getServer();
		server.getScheduler().cancelTasks(plugin);
		HandlerList.unregisterAll(plugin);
	}

	public static File getJarFile(Plugin plugin) {
		URL url = plugin.getClass().getProtectionDomain().getCodeSource().getLocation();
		try {
			return new File(url.toURI());
		} catch (URISyntaxException e) {
			return null;
		}
	}

	public static void extractResource(Plugin plugin, String from, File to) {
		extractResource(plugin, from, to, ExtractWhen.IF_NEWER);
	}


	public static void extractResource(Plugin plugin, String from, File to, ExtractWhen when) {
		File of = to;
		if (to.isDirectory()) {
			String fname = new File(from).getName();
			of = new File(to, fname);
		} else if (!of.isFile()) {
			LogUtils.warning("not a file: " + of);
			return;
		}

		File jarFile = getJarFile(plugin);

		LogUtils.finer("extractResource: file=" + of +
				", file-last-mod=" + of.lastModified() +
				", file-exists=" + of.exists() +
				", jar-last-mod=" + jarFile.lastModified() +
				", when=" + when);

		// if the file exists and is newer than the JAR, then we'll leave it alone
		if (of.exists() && when == ExtractWhen.IF_NOT_EXISTS) {
			return;
		}
		if (of.exists() && of.lastModified() > jarFile.lastModified() && when != ExtractWhen.ALWAYS) {
			return;
		}

		if (!from.startsWith("/")) {
			from = "/" + from;
		}

		LogUtils.fine(String.format("extracting resource: %s (%s) -> %s (%s)", from, SOURCE_ENCODING.name(), to, TARGET_ENCODING.name()));

		final char[] cbuf = new char[1024];
		int read;
		try {
			final Reader in = new BufferedReader(new InputStreamReader(openResourceNoCache(plugin, from), SOURCE_ENCODING));
			final Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(of), TARGET_ENCODING));
			while ((read = in.read(cbuf)) > 0) {
				out.write(cbuf, 0, read);
			}
			out.close();
			in.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static InputStream openResourceNoCache(Plugin plugin, String resource) throws IOException {
		URL res = plugin.getClass().getResource(resource);
		if (res == null) {
			LogUtils.warning("can't find " + resource + " in plugin JAR file"); //$NON-NLS-1$
			return null;
		}
		URLConnection resConn = res.openConnection();
		resConn.setUseCaches(false);
		return resConn.getInputStream();
	}

	public static void registerListener(Plugin plugin, Listener listener) {
		plugin.getServer().getPluginManager().registerEvents(listener, plugin);
	}

	public static void registerListeners(Plugin plugin, Listener... listeners) {
		PluginManager pl = plugin.getServer().getPluginManager();
		for (Listener listener : listeners) {
			pl.registerEvents(listener, plugin);
		}
	}
}
