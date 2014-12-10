package com.brett.processing.fileplayer;

import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;

public class PlaylistImpl implements Playlist {

    private PApplet parent = null;

    private int currentSong = -1;

    private List<String> songs = new ArrayList<String>();


    public PlaylistImpl(PApplet parent) {
        this.parent  = parent;
    }

    /* (non-Javadoc)
     * @see com.brett.processing.PlaylistIntfc#addFileToPlayList(java.lang.String)
     */
    @Override
    public void addFileToPlayList(String fileName) {
        songs.add(fileName);
    }

    /* (non-Javadoc)
     * @see com.brett.processing.PlaylistIntfc#getNextSongInPlayList()
     */
    @Override
    public String getNextSongInPlayList() {
        String result = null;
        if (size() > 0) {
            if (currentSong == -1) {
                currentSong = 0;
            }
            else if (currentSong == (size() - 1)) {
                currentSong = 0;
            }
            else {
                currentSong++;
            }
            result = songs.get(currentSong);
        }
        return result;
    }

    @Override
    public String getPreviousSongInPlayList() {
        String result = null;
        if (size() > 0) {
            if (currentSong <= 0) {
                currentSong = size() - 1;
            }
            else {
                currentSong = currentSong - 1;
            }
            result = songs.get(currentSong);
        }
        return result;
    }

    /* (non-Javadoc)
     * @see com.brett.processing.PlaylistIntfc#savePlayList(java.lang.String)
     */
    @Override
    public void savePlayList(String fileName) {
        String[] songArray = songs.toArray(new String[songs.size()]);
        parent.saveStrings(fileName, songArray);
    }

    /* (non-Javadoc)
     * @see com.brett.processing.PlaylistIntfc#size()
     */
    @Override
    public int size() {
        return songs.size();
    }

    /* (non-Javadoc)
     * @see com.brett.processing.PlaylistIntfc#loadPlayList(java.lang.String)
     */
    @Override
    public int loadPlayList(String fileName) {
        String[] data = parent.loadStrings(fileName);

        if (data.length > 0) {
            songs = new ArrayList<String>();
            for (String song: data) {
                songs.add(song);
            }
        }
        else {
            System.out.println("no data loaded for file = " + fileName);
        }
        return data.length;
    }

    /* (non-Javadoc)
     * @see com.brett.processing.PlaylistIntfc#getSongs()
     */
    @Override
    public List<String> getSongs() {
        return songs;
    }

    /* (non-Javadoc)
     * @see com.brett.processing.PlaylistIntfc#setSongs(java.util.List)
     */
    @Override
    public void setSongs(List<String> songs) {
        this.songs = songs;
    }

}
