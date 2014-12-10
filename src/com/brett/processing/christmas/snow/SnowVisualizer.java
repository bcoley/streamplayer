package com.brett.processing.christmas.snow;

import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;

import com.brett.processing.visualize.Visualizer;

public class SnowVisualizer extends Visualizer {
	private List<Snow> snows = null;

	private int myColor;

	public SnowVisualizer(PApplet parent) {
		super(parent);
	}

	@Override
	public void init() {
		snows = new ArrayList<Snow>(20);
		myColor = parent.color(75 + parent.random(75), 151, 255);
	}

	void drawSnow() {
		for (Snow s: snows) {
			s.display();
		}
	}

	private void addSnow() {
		snows.add(new Snow(parent));
	}

	@Override
	public void draw() {
		parent.background(myColor);
		if ((parent.frameCount % 3) == 0) {
			addSnow();
		}

		//lights();  //Only for 3D
		drawSnow();
		for(int i = 0; i < snows.size(); i++){
			Snow s = snows.get(i);
			if(s.isDeath()){
				snows.remove(s);
			}
		}
	}

}
