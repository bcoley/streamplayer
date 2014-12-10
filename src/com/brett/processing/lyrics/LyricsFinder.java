package com.brett.processing.lyrics;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.lang3.StringEscapeUtils;

public class LyricsFinder {

	public String readUrl(String urlStr) {
		System.out.println(urlStr);
		StringBuilder result = new StringBuilder();
		try {
			//            long startTime = System.currentTimeMillis();
			URL url = new URL(urlStr);
			URLConnection urlConnection = url.openConnection();
			urlConnection.setConnectTimeout(5000);
			urlConnection.setReadTimeout(5000);
			//urlConnection.setRequestProperty("Accept","application/json");
			urlConnection.connect();
			InputStream inputStream = urlConnection.getInputStream();
			//            Object content = urlConnection.getContent();
			//            System.out.println("content: " + content);
			Reader reader = new InputStreamReader(inputStream);
			//            System.out.println("Processing time: " + (System.currentTimeMillis() - startTime) + " ms for url = " + urlStr);
			int c = 0;
			char ch;
			while (c != -1) {
				c = reader.read();
				ch = (char) c;
				result.append(ch);
			}
			result.append(System.lineSeparator());
			reader.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return result.toString();
	}

	private String getUrl(String content) {
		String parts[] = content.split("<lyrics>");
		parts = parts[1].split("</lyrics>");
		if ("Not found".equals(parts[0])) {
			return null;
		}
		String result = "";
		parts = content.split("<url>");
		parts = parts[1].split("</url>");
		result = parts[0];
		return result;
	}

	private String fixup(String input) {
	    String result = input.trim();
		result = result.replaceAll(" ", "_");
		return result;
	}

	private LyricsData findLyrics(String content) {
		StringBuilder result = new StringBuilder();
		String[] parts = content.split("<div class='lyricbox'>");
		if ((parts == null) || (parts.length < 2)) {
			return null;
		}
		parts = parts[1].split("</script>");
		parts = parts[1].split("<!--");

		parts = parts[0].split("<br />");
		for (String part: parts) {
			result.append(StringEscapeUtils.unescapeHtml4(part));
			result.append(System.lineSeparator());

		}
		//System.out.println(result);
		//return result.toString();
		LyricsData lyricsData = new LyricsData();
		lyricsData.setText(result.toString());
		return lyricsData;
	}



	public LyricsData find(final String artist, final String title) {
		// need to call out and get url  http://lyrics.wikia.com/api.php?func=getSong&artist=Tool&song=Schism&fmt=xml
		String url = "http://lyrics.wikia.com/api.php?func=getSong&artist=" + fixup(artist) + 
				"&song=" + fixup(title) + "&fmt=xml";
		String content = readUrl(url);
		url = getUrl(content);
		if (url == null) {
		    System.out.println("no lyrics found for " + artist + " - " + title);
			return null;
		}
		content = readUrl(url);
		//System.out.println(content);
		LyricsData  lyrics = findLyrics(content);
		if (lyrics == null) {
			System.out.println("no lyrics found for " + artist + " - " + title);
		}
		else {
			System.out.println(lyrics.getText());
		}
		
		return lyrics;
	}


	public static void main(String[] args) {
		if (args.length != 2) {
			System.err.println("usage: LyricsFinder artist title");
			System.exit(8);
		}
		LyricsFinder finder = new LyricsFinder();
		LyricsData lyrics = finder.find(args[0], args[1]);
		System.out.println("=====");
		System.out.println(lyrics);
		System.out.println("=====");
	}
}
