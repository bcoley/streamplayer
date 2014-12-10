package com.brett.processing.test;

import javazoom.spi.mpeg.sampled.file.IcyListener;
import processing.core.PApplet;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;

public class TestMinim extends PApplet {

	private static final long serialVersionUID = -8725265778966298871L;

	private String url = "http://ice.somafm.com/indiepop"; //"http://streaming.sensoryresearch.net:8000/ncn128k";

	private Minim minim;

	private AudioPlayer player;

	IcyListener icyListener = IcyListener.getInstance();
	String title = "";

	public void setup() {
		minim = new Minim(this);
		size(300,300);
		colorMode(HSB);
		ellipseMode(CENTER);
		background(random(32));
		frameRate(24);
		player = minim.loadFile(url);
		player.play();
	}

	public void draw() {
		if ((frameCount % 30) == 0) {
			String newTitle = icyListener.getStreamTitle();
			if (newTitle == null) {
				newTitle = "";
			}
			if (! title.equals(newTitle)) {
				title = newTitle;
				fill(random(128) + 127, random(255), random(255));
				background(random(128) + 127);
			}
			text(title, 20, 20);
		}
	}

}
