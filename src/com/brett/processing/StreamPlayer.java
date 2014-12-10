package com.brett.processing;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.prefs.Preferences;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import javazoom.spi.mpeg.sampled.file.IcyListener;
import processing.core.PApplet;

public class StreamPlayer extends BasePlayer {

	private static final long serialVersionUID = 6143981543624315245L;

	private String currentUrl = "";

	private String currentStreamTitle = "";

	private IcyListener icyListener = IcyListener.getInstance();

	private Preferences preferences = null;

	protected long songStart = 0L;
	
	public void setup() {
		size(500, 500);
		//size(displayWidth, displayHeight);
		if (frame != null) {
		    frame.setResizable(true);
		}
		colorMode(HSB, 255);
		background(random(255), random(255), random(255));
		rectMode(CENTER);
		ellipseMode(CENTER);
		frameRate(24);
		textSize(20);
		showInstructions();
		openUrlDialog();
	}

	public boolean startPlaying(String url) {
	   // background(random(255), random(255), random(255));
		System.out.println("startPlaying: " + url);
		if (player != null) {
			player.pause();
			player.close();
			player = null;
		}

		currentUrl = url;
		player = minim.loadFile(url);
		currentStreamTitle = url.replace('.', ' ');
		currentStreamTitle = currentStreamTitle.replace('/', ' ');
		currentStreamTitle = currentStreamTitle.replace(':', ' ');
		setPlaying(true); 
		newSongStarted();
		player.play();
		return true;
	}

	private void newSongStarted() {
	  //  background(random(255), random(255), random(255));
		List<String> terms = imageFetcher.createSearchTerms(currentStreamTitle);
		System.out.println("image search terms for: '" + currentStreamTitle + "' = " + terms);
		imagesFuture = imageFetcher.fetchImageHoldersAsync(terms);
		setHaveImages(false);
		imageManager.resetImages();
		lyricsFuture = null;
		setHaveLyrics(false);
		songStart = System.currentTimeMillis();
		if (isUseLyrics()) {
			String parts[] = currentStreamTitle.split("-");
			if (parts.length == 2) {
				lyricsFuture = lyricsFetcher.findLyricsAsync(parts[0].trim(), parts[1].trim());
			}
		}
	}

	public void draw() {
		isNewSongStarting();
		if  (isPlaying()) {
		    super.draw();
			if (isUseLyrics() && isHaveLyrics()) {
				// we are guessing any given song is 3 minutes long... we guess.
				lyricsVisualizer.lyricsVisualise(frameCount, (int) (System.currentTimeMillis() - songStart), 180000);
			}
		}
	}

    protected boolean isNewSongStarting() {
        boolean result = false;
        if ((frameCount % 30) == 0) {
			String newTitle = icyListener.getStreamTitle();
			if (newTitle == null) {
				newTitle = currentStreamTitle;
			}
			if (! currentStreamTitle.equals(newTitle)) {
				currentStreamTitle = newTitle;
				setDisplayTitle(currentStreamTitle + "  " + currentUrl);
				newSongStarted();
				result = true;
			}
		}
        return result;
    }

	public void keyPressed() {
		System.out.println("in keypressed..." + key);
		if (key == ' ') {
			if (player.isPlaying()) {
				player.pause();
				setPlaying(false);
				showInstructions();
			}
			else {
				player.play();
				setPlaying(true);
			}
		}
		else if (key == 'f') {
            setAdultFilter(!isAdultFilter());
            showInstructions();
        }
		else if (key == 'l') {
			setUseLyrics(!isUseLyrics());
			showInstructions();
		}
		else if (key == 'o') {
			openUrlDialog();
		}
		else if (key == 's') {
		    SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMddHHmmss");
		    Date date = new Date();
		    saveFrame("StreamPlayer_" + dateFormat.format(date) + ".jpg");
		}
		  else if (key == 't') {
	            setShowTitle(!isShowTitle());
	        }
		else if (key == 'x') {
			if (player != null) {
				player.pause();
			}
			System.exit(0);
		}
	}

	private void openUrlDialog() {
		if (player != null) {
			player.pause();
		}
		background(0);
		setPlaying(false);
		ArrayList<String> recentItems = loadPreferences();
		String[] prefUrls = recentItems.toArray(new String[recentItems.size()]);
		JComboBox<String> jcb = new JComboBox<String>(prefUrls);
		jcb.setEditable(true);
		String url = "";
		int result = JOptionPane.showConfirmDialog(frame, jcb,
				"Enter or select Url to open:",
				JOptionPane.OK_CANCEL_OPTION);
		System.out.println("" + jcb.getSelectedItem() + " result = " + result);
		if (result == JOptionPane.OK_OPTION) {
			url = (String) jcb.getSelectedItem();
			if (url != null) {
				updatePreferences(url, recentItems);
				startPlaying(url);
			}
		}
		else {
			showInstructions();
		}
	}

	private void updatePreferences(String url, ArrayList<String> recentItems) {
		if (recentItems.contains(url)) {
			int u = recentItems.indexOf(url);
			recentItems.remove(u);
		}
		recentItems.add(0, url);
		int i = 0;
		for (String recentUrl: recentItems) {
			getPreferences().put("recent.url." + i, recentUrl);
			i++;
		}
		try {
			getPreferences().flush();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	protected ArrayList<String> loadPreferences() {
		ArrayList<String> recentItems = new ArrayList<String>();
		setPreferences(Preferences.userRoot().node(this.getClass().getName()));
		for (int i = 0; i < 20; i++) {
			String option = getPreferences().get("recent.url." + i, null);
			if (null != option) {
				recentItems.add(option);
			}
		}
		if (recentItems.isEmpty()) {  // preload prefs.  too much soma?
			preloadPreferences(recentItems);
		}
		return recentItems;
	}

    protected void preloadPreferences(ArrayList<String> recentItems) {
        recentItems.add("http://ice.somafm.com/indiepop");
        recentItems.add("http://ice.somafm.com/covers");
        recentItems.add("http://streaming.sensoryresearch.net:8000/ncn128k");
        recentItems.add("http://wknc.sma.ncsu.edu:8000/wknchq");
        recentItems.add("http://ice.somafm.com/dubstep");
        recentItems.add("http://ice.somafm.com/missioncontrol");
        recentItems.add("http://ice.somafm.com/dronezone");
        recentItems.add("http://ice.somafm.com/deepspaceone");
        recentItems.add("http://ice.somafm.com/spacestation");
        recentItems.add("http://ice.somafm.com/doomed");
    }
	
	public void showInstructions() {
	    background(0);
	    fill(255, 255, random(255));
		String instructions =  "space bar - play/pause " + System.lineSeparator() +
                                "f - filter - adult filtering, current setting: " + isAdultFilter() + System.lineSeparator() +
		                        "l - lyrics - show lyrics, current setting: " + isUseLyrics() + System.lineSeparator() +
		                        "o - open a URL" + System.lineSeparator() +
		                        "s - save screen" + System.lineSeparator() +
								"x - exit";
		text(instructions, 20, 20, 10);
		
	}
	
	static public void main(String args[]) {
	    // "--present", 
	    PApplet.main(new String[] {"com.brett.processing.StreamPlayer" });
	    
	}

    private Preferences getPreferences() {
        return preferences;
    }

    private void setPreferences(Preferences preferences) {
        this.preferences = preferences;
    }

}
