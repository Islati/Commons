package com.caved_in.commons.http;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

class Connection implements Serializable {
	static final long serialVersionUID = 1L;

	private String domain;
	private String referer;
	private Map<String, String> cookies;

	//Google chrome User Agent
	private static String rpUseragent = "Mozilla/5.0 (Windows NT 6.2; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1667.0 Safari/537.36";
	private static String rpAcceptText = "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8";
	private static String rpAcceptPng = "image/png,image/*;q=0.8,*/*;q=0.5";
	private static String rpAcceptLanguage = "en-us,en;q=0.5";
	private static String rpAcceptEncoding = "gzip, deflate";
	private static String rpAcceptCharset = "ISO-8859-1,utf-8;q=0.7,*;q=0.7";
	private static String rpKeepAlive = "300";
	private static String rpConnection = "keep-alive";
	private static String rpContentType = "application/x-www-form-urlencoded";

	private Connection(String domain, Map<String, String> cookies, String referer) {
		this.domain = domain;
		this.cookies = cookies;
		this.referer = referer;
	}

	public Connection(String domain, Map<String, String> cookies) {
		this(domain, cookies, null);
	}

	public Connection(String domain, String referer) {
		this(domain, new HashMap<String, String>(), referer);
	}

	public Connection(String domain) {
		this(domain, new HashMap<String, String>(), null);
	}

	public String get(String url) {
		if (url.charAt(0) == '/') {
			url = domain + url;
		}

		try {
			HttpURLConnection conn = (HttpURLConnection) (new URL(url.replaceAll(" ", "%20")).openConnection());
			setRequestProperties(conn);
			conn.setRequestMethod("GET");
			referer = url;
			return read(conn);
		} catch (IOException e1) {
			e1.printStackTrace();
			return null;
		}
	}

	public String post(String url, String[][] data) {
		if (url.charAt(0) == '/') {
			url = domain + url;
		}

		try {
			HttpURLConnection conn = (HttpURLConnection) (new URL(url.replaceAll(" ", "%20")).openConnection());
			setRequestProperties(conn);
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);

			StringBuilder sb = new StringBuilder();

			for (int i = 0; i < data[0].length; i++) {
				sb.append(URLEncoder.encode(data[0][i], "UTF-8")).append('=').append(URLEncoder.encode(data[1][i], "UTF-8")).append('&');
			}

			conn.setRequestProperty("Content-Type", rpContentType);
			conn.setRequestProperty("Content-Length", Integer.toString(sb.length() - 1));

			PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(conn.getOutputStream())));
			out.write(sb.substring(0, sb.length() - 1));
			out.close();

			referer = url;
			return read(conn);
		} catch (IOException e1) {
			e1.printStackTrace();
			return null;
		}
	}

	public BufferedImage getImage(String url) {
		try {
			HttpURLConnection conn = (HttpURLConnection) (new URL((url.charAt(0) == '/' ? domain + url : url).replaceAll(" ", "%20")).openConnection());
			setRequestProperties(conn);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", rpAcceptPng);
			return ImageIO.read(conn.getInputStream());
		} catch (IOException e1) {
			e1.printStackTrace();
			return null;
		}
	}

	public boolean hasCookie(String key) {
		return cookies.containsKey(key);
	}

	String getCookieString() {
		StringBuilder sb = new StringBuilder();

		for (String s : cookies.keySet()) {
			sb.append(s).append('=').append(cookies.get(s)).append(';');
		}

		return sb.toString();
	}

	private void setRequestProperties(HttpURLConnection conn) {
		conn.setInstanceFollowRedirects(false);
		conn.setRequestProperty("User-Agent", rpUseragent);
		conn.setRequestProperty("Accept", rpAcceptText);
		conn.setRequestProperty("Accept-Language", rpAcceptLanguage);
		conn.setRequestProperty("Accept-Encoding", rpAcceptEncoding);
		conn.setRequestProperty("Accept-Charset", rpAcceptCharset);
		conn.setRequestProperty("Keep-Alive", rpKeepAlive);
		conn.setRequestProperty("Connection", rpConnection);

		if (referer != null && referer.length() != 0) {
			conn.setRequestProperty("Referer", referer);
		}

		if (cookies != null && cookies.size() != 0) {
			conn.setRequestProperty("Cookie", getCookieString());
		}
	}

	private String read(HttpURLConnection conn) throws IOException {
		BufferedReader in = null;

		if (conn.getContentEncoding() == null) {
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else if (conn.getContentEncoding().equalsIgnoreCase("gzip")) {
			in = new BufferedReader(new InputStreamReader(new GZIPInputStream(conn.getInputStream())));
		} else if (conn.getContentEncoding().equalsIgnoreCase("deflate")) {
			in = new BufferedReader(new InputStreamReader(new InflaterInputStream(conn.getInputStream(), new Inflater(true))));
		}

		StringBuilder sb = new StringBuilder();
		String s;

		while ((s = in.readLine()) != null) {
			sb.append(s).append('\n');
		}

		putCookies(conn.getHeaderFields().get("Set-Cookie"));
		return sb.toString();
	}

	private void putCookies(List<String> cookieList) {
		if (cookieList == null) {
			return;
		}

		int index;

		for (String cookie : cookieList) {
			cookies.put(cookie.substring(0, index = cookie.indexOf('=')), cookie.substring(index + 1, cookie.indexOf(';', index)));
		}
	}
}