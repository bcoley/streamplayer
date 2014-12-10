package com.brett.processing.christmas;

import java.util.ArrayList;

import processing.core.PApplet;

import com.brett.processing.StreamPlayer;

public class ChristmasStreamer extends StreamPlayer {

    public ChristmasStreamer() {
        visualizationManager = new ChristmasVisualizationManager(this);
    }
    
    private static final long serialVersionUID = 2880513881857998854L;

    protected void preloadPreferences(ArrayList<String> recentItems) {
        recentItems.add("http://ice.somafm.com/xmasrocks");
        recentItems.add("http://ice.somafm.com/xmasinfrisko");
        recentItems.add("http://ice.somafm.com/christmas");
    }
    
    public void draw() {
        if  (isPlaying()) {
            isNewSongStarting();
            visualizationManager.draw();
            imageDisplay();
            if (isUseLyrics() && isHaveLyrics()) {
                // we are guessing any given song is 3 minutes long... we guess.
                lyricsVisualizer.lyricsVisualise(frameCount, (int) (System.currentTimeMillis() - songStart), 180000);
            }
            drawTitle();
        }
    }
    
    static public void main(String args[]) {
        // "--present", 
        PApplet.main(new String[] { "com.brett.processing.christmas.ChristmasStreamer" });
    }
    
}
