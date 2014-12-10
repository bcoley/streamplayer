package com.brett.processing.lyrics;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class Lyrics {

	private String text;

	int lineCount = 0;

	int verseCount = 0;

	private List<String> lines = new ArrayList<String>();

	private List<String> verses = new ArrayList<String>();

	public Lyrics(String text) {
		this.text = text;
		processText();
	}

	public int getLineCount() {
		return lineCount;
	}

	public String getLine(int lineNumber) {
		String result = "";
		try {
			result = lines.get(lineNumber);  
		}
		catch(Exception e) {
		}
		return result;
	}

	public int getVerseCount() {
		return verseCount;
	}

	public String getVerse(int verseNumber) {
		String result = "";
		try {
			result = verses.get(verseNumber);  
		}
		catch(Exception e) {
		}
		return result;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
		processText();
	}

	private void processText() {
		lines = new ArrayList<String>();
		verses = new ArrayList<String>();

		try {
			BufferedReader reader = new BufferedReader(new StringReader(text));
			lineCount = 0;
			String s = null;
			while((s = reader.readLine()) != null ) {
				lines.add(s);
				lineCount++;
			}
		} catch(Exception e) {
		}

		try {
			BufferedReader reader = new BufferedReader(new StringReader(text));
			verseCount = 0;
			String s = null;
			String verse = "";
			while((s = reader.readLine()) != null ) {
				if ("".equals(s.trim())) {
					if (!"".equals(verse)) {  // could be multiple blank lines
						verses.add(verse);
					verseCount++;
					verse = "";
					}
				}
				else {
					verse = verse + s + System.lineSeparator();
				}
			}
			if (!"".equals(verse)) { // no ending line?
				verses.add(verse);
				verseCount++;  
			}
		} catch(Exception e) {
		}
	}

}


