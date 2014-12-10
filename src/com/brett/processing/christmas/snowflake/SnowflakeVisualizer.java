package com.brett.processing.christmas.snowflake;

import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;

import com.brett.processing.visualize.Visualizer;

public class SnowflakeVisualizer extends Visualizer {

    private List<Flake> flakes;


    public SnowflakeVisualizer(PApplet parent) {
        super(parent);
    }

    @Override
    public void init() {
        int flakeCount = (int) parent.random(6, 14);
        flakes = new ArrayList<Flake>(flakeCount);
        for (int i = 0; i < flakeCount; i++) {
            flakes.add(new Flake(parent.random(parent.width), parent.random(parent.height), parent.random(5,7), parent));
        }
    }

    @Override
    public void draw() {
        if ((parent.frameCount % 3) == 0) {
            parent.background(0, 0, 255);
            for (Flake flake: flakes) {
                flake.drawFlake();
            }
        }
    }

}
