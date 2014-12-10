package com.brett.processing.lyrics;

import processing.core.PApplet;

public class LyricsVisualizer {

	private Lyrics lyrics = null;

	private PApplet parent;

	private String currentVerse = "";

	private boolean haveLyrics = false;

	public LyricsVisualizer(PApplet applet) {
		this.parent = applet;
	}

	/**
	 * Show the verse of the lyrics, and roughly time it to the duration 
	 * of the song.   The fill() calls are setting a white highlight, 
	 * then the current color, then a black shadow.
	 * 
	 * @param frameCount current frame count, from PApplet
	 * @param position - position of the current song, in milliseconds
	 * @param duration - duration of the current song, in milliseconds
	 */
	public void lyricsVisualise(int frameCount, int position, int duration) { 
		if (haveLyrics) {
			if (frameCount % 48 == 0) {
				int slice = duration / lyrics.getVerseCount(); 
				int verse = (position / slice) % lyrics.getVerseCount();
				currentVerse = lyrics.getVerse(verse);
			}
			int prevColor = parent.g.fillColor;
			parent.fill(0, 0, 255);
			parent.text(currentVerse, 60, 60, 10);
			parent.fill(0);
			parent.text(currentVerse, 61, 61, 10);
			parent.fill(prevColor);
			//wordCram.fromTextString(lyrics.getVerse(verse)).drawAll();
		}
	}

	public Lyrics getLyrics() {
		return lyrics;
	}

	public void setLyrics(Lyrics lyrics) {
		this.lyrics = lyrics;
		if ((lyrics != null) && (lyrics.getVerseCount() > 0)) {
			haveLyrics = true;
		}
		else {
			haveLyrics = false;
		}
	}

	public PApplet getParent() {
		return parent;
	}

	public void setParent(PApplet parent) {
		this.parent = parent;
	}

}