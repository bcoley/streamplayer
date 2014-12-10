package com.brett.processing.lyrics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;
import wordcram.Colorers;
import wordcram.Word;
import wordcram.WordCram;

public class LyricsWordCram {

    protected PApplet parent = null;


    public LyricsWordCram(PApplet parent) {
        this.parent = parent;
    }

    public LyricsData createWordCramImage(LyricsData data) {
        PGraphics buffer = parent.createGraphics(parent.width, parent.height, PConstants.JAVA2D);
        buffer.beginDraw(); 
        buffer.background(255, 255, 255);
        @SuppressWarnings("deprecation")
        WordCram wordCram = new WordCram(parent)
        .fromTextString(data.getText())
        .lowerCase()
        .withCustomCanvas(buffer)
        .withColorer(Colorers.twoHuesRandomSatsOnWhite(parent))
        .sizedByWeight(8, 70);
        wordCram.drawAll();
        buffer.endDraw();
        Word[] words = wordCram.getWords();
        Arrays.sort(words);
        data.setWords(createWordList(words));
        data.setWordcramImage(buffer);
        return data;
    }
    
    protected List<String> createWordList(Word[] words) {
        List<String> result = new ArrayList<String>();
        for (Word word: words) {
            result.add(word.word);
        }
        return result;
    }

}
