package com.brett.processing.lyrics;

import java.util.List;

import processing.core.PImage;

public class LyricsData {

    private String text;
    
    private Lyrics lyrics;
    
    private PImage wordcramImage;
    
    private List<String> words;

    public Lyrics getLyrics() {
        return lyrics;
    }

    public void setLyrics(Lyrics lyrics) {
        this.lyrics = lyrics;
    }

    public String getText() {
        return text;
    }

    public void setText(String lyrics) {
        this.text = lyrics;
        setLyrics(new Lyrics(text));
    }

    public PImage getWordcramImage() {
        return wordcramImage;
    }

    public void setWordcramImage(PImage wordcramImage) {
        this.wordcramImage = wordcramImage;
    }

    public List<String> getWords() {
        return words;
    }

    public void setWords(List<String> words) {
        this.words = words;
    }
    
    
    
}
