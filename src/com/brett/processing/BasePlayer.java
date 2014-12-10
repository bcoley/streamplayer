package com.brett.processing;

import java.util.List;
import java.util.concurrent.Future;

import com.brett.processing.image.ImageHolder;
import com.brett.processing.image.ImageManager;
import com.brett.processing.image.PImageFetcher;
import com.brett.processing.lyrics.LyricsData;
import com.brett.processing.lyrics.LyricsFetcher;
import com.brett.processing.lyrics.LyricsVisualizer;
import com.brett.processing.lyrics.LyricsWordCram;
import com.brett.processing.visualize.VisualizationManager;

import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import processing.core.PApplet;

public class BasePlayer extends PApplet {

    private static final long serialVersionUID = -8344167395668571622L;
    protected Minim minim = new Minim(this);
    protected AudioPlayer player = null;
    private boolean playing = false;
    protected PImageFetcher imageFetcher = new PImageFetcher();
    protected Future<List<ImageHolder>> imagesFuture = null;
    private boolean haveImages = false;
    protected ImageManager imageManager = new ImageManager();
    protected LyricsFetcher lyricsFetcher = new LyricsFetcher();
    protected LyricsVisualizer lyricsVisualizer = new LyricsVisualizer(this);
    protected LyricsWordCram lyricsWordCram = new LyricsWordCram(this);
    protected VisualizationManager visualizationManager = new VisualizationManager(this);
    private boolean useLyrics = true;
    protected Future<LyricsData> lyricsFuture = null;
    private boolean haveLyrics = false;
    private boolean adultFilter = true;
    private boolean showTitle = true;
    
    private String displayTitle = "";

    public BasePlayer() {
        super();
    }

    protected void imageDisplay() {
    	// This nested if polls for images and deals with cycling through them.
    	// Every 20 frames, check to see if we have images from async calls.
    	// If we do have them, show the first group.
    	// Once we have images, every so often poll for the next group.
    	// Once we are through the entire list, shuffle and repeat, see ImageManager.
    	if ((frameCount % 20) == 0) {
    		if (isUseLyrics() && !isHaveLyrics()) {
    			checkLyrics();
    		}
    		if (!isHaveImages()) {
    			List<ImageHolder> images = checkImages();
    			if (images != null) {
    				imageManager.addImages(images);
    				imageManager.nextGroup(width, height, 7, 14);
    			}
    		}
    		else if ((frameCount % 240) == 0) {
    			if (imageManager.numberDisplaying() == 0) {
    				imageManager.nextGroup(width, height, 3, 10);
    			}
    		}
    	}
    
    	for (ImageHolder holder: imageManager.getImages()) {
    		if (holder.isNowShowing()) {
    			image(holder.getImage(), holder.getX(), holder.getY());
    		}
    	}
    }

    protected boolean checkLyrics() {
    	if (!isUseLyrics()) {
    		return false;
    	}
    	if ((lyricsFuture != null) && lyricsFuture.isDone()) {
    		try {
    			LyricsData lyricsData = lyricsFuture.get();
    			if (lyricsData != null)  {
    			    lyricsData = lyricsWordCram.createWordCramImage(lyricsData);
    				lyricsVisualizer.setLyrics(lyricsData.getLyrics());
    				imageManager.addImage(new ImageHolder(lyricsData.getWordcramImage()));
    				background(lyricsData.getWordcramImage());
    				setHaveLyrics(true);
    			}
    			else {
    				setHaveLyrics(false);
    			}
    		}
    		catch (Exception e) {
    			e.printStackTrace();
    		}
    	}
    	return isHaveLyrics();
    }

    protected List<ImageHolder> checkImages() {
        List<ImageHolder> images = null;
        if ((imagesFuture != null) && imagesFuture.isDone()) {
            try {
                images = imagesFuture.get();
                if ((images != null) && (images.size() > 0)) {
                    setHaveImages(true);
                    return images;
                }
                else {
                    setHaveImages(false);
                    return null;
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        setHaveImages(false);
        return null;
    }

    public void draw() {
        if  (isPlaying()) {
            visualizationManager.draw();
            imageDisplay();
            copy(1, 1, width-2, height-2, 0, 0, width, height);
            drawTitle();
        }
    }

    protected void drawTitle() {
        if (showTitle) {
            int prevColor = g.fillColor;
            fill(0, 0, 255);
            text(displayTitle, (width-textWidth(displayTitle))/2, 39);
            fill(0);
            text(displayTitle, (width-textWidth(displayTitle))/2, 40);
            fill(prevColor);
        }
    }
    
//    public boolean sketchFullScreen() {
//    	return true;
//    }

    public boolean isPlaying() {
        return playing;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    public boolean isHaveImages() {
        return haveImages;
    }

    public void setHaveImages(boolean haveImages) {
        this.haveImages = haveImages;
    }

    public boolean isUseLyrics() {
        return useLyrics;
    }

    public void setUseLyrics(boolean useLyrics) {
        this.useLyrics = useLyrics;
    }

    public boolean isHaveLyrics() {
        return haveLyrics;
    }

    public void setHaveLyrics(boolean haveLyrics) {
        this.haveLyrics = haveLyrics;
    }

    public boolean isAdultFilter() {
        return adultFilter;
    }

    public void setAdultFilter(boolean adultFilter) {
        this.adultFilter = adultFilter;
        imageFetcher.setAdultFilter(this.adultFilter);
    }

    public String getDisplayTitle() {
        return displayTitle;
    }

    public void setDisplayTitle(String displayTitle) {
        this.displayTitle = displayTitle;
    }

    public boolean isShowTitle() {
        return showTitle;
    }

    public void setShowTitle(boolean showTitle) {
        this.showTitle = showTitle;
    }

}