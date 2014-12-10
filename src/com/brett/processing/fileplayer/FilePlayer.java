package com.brett.processing.fileplayer;

import java.awt.EventQueue;
import java.awt.FileDialog;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

import javax.swing.JFileChooser;

import processing.core.PApplet;

import com.brett.processing.BasePlayer;
import com.brett.processing.image.PImageFetcher;
import com.brett.processing.lyrics.Lyrics;

import ddf.minim.AudioMetaData;
import ddf.minim.Minim;

public class FilePlayer extends BasePlayer {

    private static final long serialVersionUID = 6143981543624315245L;
    
    private ControlPanel controlPanel = null;
    
    private String currentMp3FileName = null;
    
    private String currentMp3ShortName = null;

    private PlaylistTableModel playlist = new PlaylistTableModel(this);

    private int duration = 0;
    
    private AudioMetaData meta = null;
    
    private long timeLeft = 0;
    
    
    public void setup() {
        minim = new Minim(this);
        imageFetcher = new PImageFetcher();
        size(200,200);
        colorMode(HSB);
        rectMode(CENTER);
        ellipseMode(CENTER);
        background(random(255), random(255), random(255));
        frameRate(24);
        textSize(18);
    }

    private void playNextSong() {
        String nextSongFilename = playlist.getNextSongInPlayList();
        startPlaying(nextSongFilename);
    }

    private void playPreviousSong() {
        // 'previous' button will go back to start of the current song, unless the current song has just started
        if (player.position() > 5000) {
            player.rewind();
        }
        else {
            String songFilename = playlist.getPreviousSongInPlayList();
            startPlaying(songFilename);
        }
    }

    private boolean startPlaying(String fileName) {
        System.out.println("startPlaying: " + fileName);
        if (player != null) {
            player.pause();
            player.close();
            player = null;
        }

        setCurrentMp3FileName(fileName);
        File file = new File(getCurrentMp3FileName());
        setCurrentMp3ShortName(file.getName());
        player = minim.loadFile(fileName);
        meta = player.getMetaData();
        String searchString = getCurrentMp3ShortName();
        setHaveLyrics(false);
        
        if (null != meta) {
            String title = meta.title();
            String artist = meta.author();
            
            if (isUseLyrics()) {
                String lyricsText = meta.lyrics();
                if (lyricsText != null  && lyricsText.length() > 10) {
                    System.out.println(" lyrics:\n" + lyricsText);
                    lyricsVisualizer.setLyrics(new Lyrics(lyricsText));
                    setHaveLyrics(true);
                }
                else {
                    if (artist != null && title != null) {
                        lyricsFuture = lyricsFetcher.findLyricsAsync(artist, title);
                    }
                }
            }
            searchString = artist + " " + title + " " + getCurrentMp3ShortName();
            System.out.println(searchString);
        }
        
        setPlaying(true); 
        player.play();
        duration = player.length();
        List<String> terms = imageFetcher.createSearchTerms(searchString);
        System.out.println(terms);
        imagesFuture = imageFetcher.fetchImageHoldersAsync(terms);
        setHaveImages(false);
        imageManager.resetImages();
        return true;
    }


    public void draw() {
        if  (isPlaying()) {
            String timeLeftStr = getCurrentMp3ShortName() + "   " + String.format("%d:%02d", timeLeft/1000/60, timeLeft/1000%60) + String.format("w %d h %d", width, height);
            text(timeLeftStr, (width-textWidth(timeLeftStr))/2, 40);
            super.draw();
            if (isUseLyrics() && isHaveLyrics()) {
                lyricsVisualizer.lyricsVisualise(frameCount, player.position(), duration);
            }
            timeLeft = duration -  player.position();
            //System.out.println("length/pos/remain " +  player.length() + ", " + player.position() + ", " + timeLeft);
            if (timeLeft < 1000) {
                player.pause();
                background(random(255), random(255), random(255));
                setPlaying(false); 
                playNextSong();
            }
        }
    }

    private void showControlPanel() {
        if (controlPanel == null) {
            controlPanel = new ControlPanel(this);
        }
    }


    // callback for load playlist
    public void fileSelectedInput(File inputFile) {
        if (inputFile == null) {
            PApplet.println("Window was closed or the user hit cancel.");
        } 
        else {
            PApplet.println("User selected " + inputFile.getAbsolutePath());
            playlist.loadPlayList(inputFile.getAbsolutePath());
            playNextSong();
        }
    }

    // callback for save playlist
    public void fileSelectedOutput(File outputFile) {
        if (outputFile == null) {
            PApplet.println("Window was closed or the user hit cancel.");
        } 
        else {
            PApplet.println("User selected " + outputFile.getAbsolutePath());
            playlist.savePlayList(outputFile.getAbsolutePath());
            playNextSong();
        }
    }

    // callback for add a single file to playlist
    public void fileAddInput(File fileToAdd) {
        if (fileToAdd == null) {
            PApplet.println("Window was closed or the user hit cancel.");
        } 
        else {
            PApplet.println("User selected " + fileToAdd.getAbsolutePath());
            playlist.addFileToPlayList(fileToAdd.getAbsolutePath());
        }
    }

    // callback for add multiple files to playlist
    public void fileAddInputs(File[] files) {
        if (files == null) {
            PApplet.println("Window was closed or the user hit cancel.");
        } 
        else {
            for (File file: files) {
                playlist.addFileToPlayList(file.getAbsolutePath());
            }
        }
    }

    public void keyPressed() {
        System.out.println("in keypressed..." + key);
        if (key == ' ') {
            if (player.isPlaying()) {
                player.pause();
                setPlaying(false);
            }
            else {
                player.play();
                setPlaying(true);
            }
        }
        else if (key == 'c') {
            showControlPanel();
        }
        else if (key == 'f') {
            setAdultFilter(!isAdultFilter());
        }
        else if (key == 'l') {
            setUseLyrics(!isUseLyrics());
        }
        else if (key == 'n') {
            if (player != null) {
                player.pause();
            }
            background(random(255), random(255), random(255));
            setPlaying(false);
            playNextSong();
        }
        else if (key == 'p') {
            if (player != null) {
                player.pause();
            }
            background(random(255), random(255), random(255));
            setPlaying(false);
            playPreviousSong();
        }
        else if (key == 'r') {
            Collections.shuffle(playlist.getSongs());
        }
    }

    public PlaylistTableModel getPlaylist() {
        return playlist;
    }

    public void setPlaylist(PlaylistTableModel playlist) {
        this.playlist = playlist;
    }

    protected void selectInputMulti(final String prompt, final String callbackMethod,
            final Object callbackObject) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                File[] selectedFiles = null;
                if (PApplet.useNativeSelect) {
                    FileDialog dialog = new FileDialog(FilePlayer.this.frame, prompt, FileDialog.LOAD);
                    dialog.setMultipleMode(true);
                    dialog.setVisible(true);
                    selectedFiles = dialog.getFiles();
                }
                else {
                    JFileChooser chooser = new JFileChooser();
                    chooser.setMultiSelectionEnabled(true);
                    chooser.setDialogTitle(prompt);
                    int result = -1;
                    result = chooser.showOpenDialog(FilePlayer.this);
                    if (result == 0) {
                        selectedFiles = chooser.getSelectedFiles();
                    }
                }
                selectCallbackMulti(selectedFiles, callbackMethod, callbackObject);
            }
        });
    }

    private void selectCallbackMulti(File[] selectedFiles, String callbackMethod,
            Object callbackObject) {
        try {
            Class<?> callbackClass = callbackObject.getClass();
            Method selectMethod = callbackClass.getMethod(callbackMethod,
                    new Class[] { File[].class });
            selectMethod.invoke(callbackObject, new Object[] { selectedFiles });
        }
        catch (IllegalAccessException iae) {
            System.err.println(callbackMethod + "() must be public");
        }
        catch (InvocationTargetException ite) {
            ite.printStackTrace();
        }
        catch (NoSuchMethodException nsme) {
            System.err.println(callbackMethod + "() could not be found");
        }
    }

    public String getCurrentMp3FileName() {
        return currentMp3FileName;
    }

    public void setCurrentMp3FileName(String currentMp3FileName) {
        this.currentMp3FileName = currentMp3FileName;
    }

    public String getCurrentMp3ShortName() {
        return currentMp3ShortName;
    }

    public void setCurrentMp3ShortName(String currentMp3ShortName) {
        this.currentMp3ShortName = currentMp3ShortName;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public AudioMetaData getMeta() {
        return meta;
    }

    public void setMeta(AudioMetaData meta) {
        this.meta = meta;
    }

    public long getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(long timeLeft) {
        this.timeLeft = timeLeft;
    }
    
    
    static public void main(String args[]) {
        // "--present", 
        PApplet.main(new String[] {"com.brett.processing.fileplayer.FilePlayer" });
        
    }
    
}
