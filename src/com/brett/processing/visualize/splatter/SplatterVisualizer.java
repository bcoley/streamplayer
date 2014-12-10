package com.brett.processing.visualize.splatter;

import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;

import com.brett.processing.visualize.Visualizer;

public class SplatterVisualizer extends Visualizer {

    private List<Splatter> splatters;

    private int baseColor;

    public SplatterVisualizer(PApplet parent) {
        super(parent);
    }

    @Override
    public void init() {
        splatters = new ArrayList<Splatter>();
    }

    @Override
    public void draw() {
        if (parent.frameCount % 5 == 0) {
            baseColor = parent.color(parent.random(255), parent.random(255), parent.random(255), 125);
            Splatter newSplatter = new Splatter(parent, baseColor, parent.random(parent.width), parent.random(parent.height), 50);
            splatters.add(newSplatter); 

            for (int i = splatters.size()-1; i >= 0; i--) {
                Splatter splatter = splatters.get(i);
                splatter.draw();
                splatter.update();
                if (splatter.isFinished()) {
                    splatters.remove(i);
                }
            }
        }
    }

}