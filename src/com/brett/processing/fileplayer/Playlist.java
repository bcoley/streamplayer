package com.brett.processing.fileplayer;

import java.util.List;

public interface Playlist {

    public abstract void addFileToPlayList(String fileName);

    public abstract String getNextSongInPlayList();
    
    public abstract String getPreviousSongInPlayList();

    public abstract void savePlayList(String fileName);

    public abstract int size();

    public abstract int loadPlayList(String fileName);

    public abstract List<String> getSongs();

    public abstract void setSongs(List<String> songs);

}