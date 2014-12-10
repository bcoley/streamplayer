package com.brett.processing.fileplayer;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import processing.core.PApplet;

class PlaylistTableModel extends AbstractTableModel implements Playlist {

    private static final long serialVersionUID = 3536137153419574079L;
    
    private String[] columnNames = {"Song"};

    private boolean DEBUG = true;

    private Playlist playlist = null;
    
    public PlaylistTableModel(PApplet parent) {
        //this.parent = parent;
        this.playlist = new PlaylistImpl(parent);
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        if (playlist == null) {
            return 0;
        }
        return playlist.size();
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col) {
        return playlist.getSongs().get(row);
    }

    public Class<? extends Object> getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    public boolean isCellEditable(int row, int col) {
        return false;
    }

    public void setValueAt(Object value, int row, int col) {
        if (DEBUG ) {
            System.out.println("Setting value at " + row + "," + col
                               + " to " + value
                               + " (an instance of "
                               + value.getClass() + ")");
        }

        //data[row][col] = value;
        fireTableCellUpdated(row, col);

        if (DEBUG) {
            System.out.println("New value of data:");
            printDebugData();
        }
    }

    public void addFileToPlayList(String fileName) {
        playlist.addFileToPlayList(fileName);
        fireTableDataChanged();
    }

    public String getNextSongInPlayList() {
        return playlist.getNextSongInPlayList();
    }

    public String getPreviousSongInPlayList() {
        return playlist.getPreviousSongInPlayList();
    }
    
    public void savePlayList(String fileName) {
        playlist.savePlayList(fileName);
    }

    public int size() {
        return playlist.size();
    }

    public int loadPlayList(String fileName) {
        int result = playlist.loadPlayList(fileName);
        fireTableDataChanged();
        return result;
    }

    public List<String> getSongs() {
        return playlist.getSongs();
    }

    public void setSongs(List<String> songs) {
        playlist.setSongs(songs);
        fireTableDataChanged();
    }

    private void printDebugData() {
        int numRows = getRowCount();
        int numCols = getColumnCount();

        for (int i=0; i < numRows; i++) {
            System.out.print("    row " + i + ":");
            for (int j=0; j < numCols; j++) {
                System.out.print("  " + playlist.getSongs().get(i));
            }
            System.out.println();
        }
        System.out.println("--------------------------");
    }

    public void swap(int from, int to) {
        List<String> list = playlist.getSongs();
        String element = list.get(to);
        list.set(to, list.get(from));
        list.set(from, element);
        playlist.setSongs(list);
        fireTableDataChanged();
    }

    public void remove(int i) {
        List<String> list = playlist.getSongs();
        list.remove(i);
        playlist.setSongs(list);
        fireTableDataChanged();
    }

}
