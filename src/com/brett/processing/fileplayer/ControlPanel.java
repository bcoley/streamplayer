package com.brett.processing.fileplayer;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTable;
import java.awt.BorderLayout;

public class ControlPanel extends JFrame {

    private static final long serialVersionUID = 5072954532433523505L;

    private FilePlayer parentPlayer = null;

    PlaylistTablePanel playlistTablePanel = null;

    public ControlPanel(FilePlayer player) {
        super("Control Panel");
        this.parentPlayer = player;
        this.setSize(400, 500);
        createMenus();
        getContentPane().setLayout(new BorderLayout(0, 0));
        getContentPane().add(createTablePanel(), BorderLayout.CENTER);
        getContentPane().add(createButtonPanel(), BorderLayout.SOUTH);
        this.setVisible(true);
    }

    private void createMenus() {
        JMenuBar mb = new JMenuBar();

        JMenu menu = new JMenu("File");

        ActionListener actionLoad;

        JMenuItem mLoad = new JMenuItem("Load Playlist...");

        actionLoad = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                parentPlayer.selectInput("Load playlist:", "fileSelectedInput", null, parentPlayer);
            }
        };

        mLoad.addActionListener(actionLoad);
        menu.add(mLoad);

        JMenuItem mSave = new JMenuItem("Save Playlist...");

        ActionListener actionSave = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                parentPlayer.selectOutput("Save playlist:", "fileSelectedOutput", null, parentPlayer);
            }
        };

        mSave.addActionListener(actionSave);
        menu.add(mSave);
        mb.add(menu);
        setJMenuBar(mb);
    }

    public JPanel createTablePanel() {
        playlistTablePanel = new PlaylistTablePanel(parentPlayer.getPlaylist());
        return playlistTablePanel;
    }

    private JPanel newGroupBox(String name, GridLayout layout)
    {
        JPanel panel = new JPanel();
        if (null != name) {
            panel.setBorder(BorderFactory.createTitledBorder(name));
        }
        else {
            panel.setBorder(BorderFactory.createEmptyBorder());
        }
        panel.setLayout(layout);

        return panel;
    }

    public JPanel createButtonPanel() {
        JPanel buttonPanel = newGroupBox(null, new GridLayout(1, 4));

        JButton addButton  = new JButton("Add");

        ActionListener addFile = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                parentPlayer.selectInputMulti("Add file to playlist:", "fileAddInputs", parentPlayer);
            }
        };

        addButton.addActionListener(addFile);
        buttonPanel.add(addButton);

        JButton deleteButton  = new JButton("Delete");

        ActionListener deleteRow = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteRow();
            }
        };

        deleteButton.addActionListener(deleteRow);
        buttonPanel.add(deleteButton);

        JButton upButton  = new JButton("Up");

        ActionListener upListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                moveUp();
            }
        };

        upButton.addActionListener(upListener);
        buttonPanel.add(upButton);

        JButton downButton  = new JButton("Down");

        ActionListener downListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                moveDown();
            }
        };

        downButton.addActionListener(downListener);
        buttonPanel.add(downButton);

        return buttonPanel;
    }

    private void deleteRow() {
        JTable table = playlistTablePanel.getTable();
        PlaylistTableModel model =  (PlaylistTableModel) table.getModel();
        int[] rows = table.getSelectedRows();
        if (rows.length > 0) {
            model.remove(rows[0]);
        }
    }

    private void moveUp(){
        JTable table = playlistTablePanel.getTable();
        PlaylistTableModel model =  (PlaylistTableModel) table.getModel();
        int[] rows = table.getSelectedRows();
        if ((rows.length > 0) && (rows[0] > 0)) {
            model.swap(rows[0],rows[0]-1);
            table.setRowSelectionInterval(rows[0]-1, rows[0]);
        }
    }

    private void moveDown(){
        JTable table = playlistTablePanel.getTable();
        PlaylistTableModel model =  (PlaylistTableModel) table.getModel();
        int[] rows = table.getSelectedRows();
        if ((rows.length > 0) && (1 + rows[0] < model.size())) {
            model.swap(rows[0],rows[0]+1);
            table.setRowSelectionInterval(rows[0]+1, rows[0]+1);
        }
    }

}

