package com.brett.processing.test;

import processing.core.PApplet;
import processing.core.PImage;
import wordcram.Word;

import com.brett.processing.lyrics.LyricsData;
import com.brett.processing.lyrics.LyricsFinder;
import com.brett.processing.lyrics.LyricsWordCram;
public class TestWordCram extends PApplet {

	//private WordCram wordCram = null;
	private LyricsFinder lyricsFinder = new LyricsFinder();
	private static final long serialVersionUID = -6991074796253010548L;
	PImage image = null;
	LyricsData lyrics;
	public void setup() { 
		lyrics = lyricsFinder.find("Wire", "Ahead");
		size(640, 480);
		colorMode(HSB);
		background(255);
		LyricsWordCram lwc = new LyricsWordCram(this);
		lyrics = lwc.createWordCramImage(lyrics);
//		wordCram = new WordCram(this);
//		wordCram.fromTextString(lyrics) //fromWebPage("http://cnn.com/")
//		.withFont(createFont("GurmukhiMN-20.vlw", 1))
//		.withColorer(Colorers.twoHuesRandomSatsOnWhite(this))
//		.sizedByWeight(7, 100);
//		wordCram.drawAll();
//		noLoop();

	}

		public void draw() {
			image(lyrics.getWordcramImage(), 0, 0);
		}
		
	public void wordsSorted(Word[] words) {
		for (int i = 0; i < words.length; i++) {
			System.out.println(words[i].toString());
		}
	}

	static public void main(String[] passedArgs) {
	    // { "--full-screen", "--bgcolor=#666666", "--hide-stop", "com.brett.processing.TestWordCram" };
		String[] appletArgs = new String[] {"com.brett.processing.TestWordCram" };
		if (passedArgs != null) {
			PApplet.main(concat(appletArgs, passedArgs));
		} else {
			PApplet.main(appletArgs);
		}
	}
}
