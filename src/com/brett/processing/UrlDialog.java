package com.brett.processing;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class UrlDialog extends JFrame {

    private static final long serialVersionUID = 5072954532433523505L;

    private StreamPlayer parentPlayer = null;
    
    private JComboBox<String> urlTextArea = null;
    
    private Preferences preferences = null;
    
    ArrayList<String> recentItems = null;

    public UrlDialog(StreamPlayer player) {
        super("URL:");
        this.parentPlayer = player;
        this.preferences = Preferences.userRoot().node(this.getClass().getName());
        this.setSize(600, 100);
        setLayout(new BorderLayout());
        //getContentPane().setLayout(new GridLayout(2, 1));
        add(createTextComboPanel(), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);
        
        this.setVisible(true);
    }

    public JPanel createTextComboPanel() {
    	recentItems = new ArrayList<String>();
    	for (int i = 0; i < 20; i++) {
    		String option = preferences.get("recent.url." + i, null);
    		if (null != option) {
    			recentItems.add(option);
    		}
    	}
    	JPanel textPanel = newGroupBox(null, new GridLayout(1, 1));
    	urlTextArea = new JComboBox<String>(recentItems.toArray(new String[recentItems.size()]));
    	urlTextArea.setEditable(true);
    	textPanel.add(urlTextArea);
    	return textPanel;
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
        JPanel buttonPanel = newGroupBox("", new GridLayout(1, 2));

        JButton okButton = new JButton("OK");

        ActionListener saveURL = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String url = (String) urlTextArea.getSelectedItem();
                if ((null != url) && !recentItems.contains(url)) {
                	recentItems.add(0, url);
                }
                int i = 0;
                for (String recentUrl: recentItems) {
                	preferences.put("recent.url." + i, recentUrl);
                	i++;
                }
                try {
					preferences.flush();
				} catch (BackingStoreException e1) {
					e1.printStackTrace();
				}
                parentPlayer.startPlaying(url);
            }
        };

        okButton.addActionListener(saveURL);
        buttonPanel.add(okButton);

        JButton cancelButton  = new JButton("Cancel");

        ActionListener cancel = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	 // TODO: 
            }
        };

        cancelButton.addActionListener(cancel);
        buttonPanel.add(cancelButton);
        return buttonPanel;
    }

}

