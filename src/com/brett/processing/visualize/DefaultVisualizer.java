package com.brett.processing.visualize;

import processing.core.PApplet;

public class DefaultVisualizer extends Visualizer {

    public DefaultVisualizer(PApplet parent) {
        super(parent);
    }

    @Override
    public void init() {
        parent.stroke(parent.random(255));
    }

    @Override
    public void draw() {
        if (parent.frameCount % 10 == 0) {
            parent.fill(parent.random(255), parent.random(255), parent.random(255));
            float x = parent.random(parent.width/2);
            float y = parent.random(parent.height/2);
            if (parent.frameCount % 7 == 0) {
                parent.ellipse(parent.random(parent.width), parent.random(parent.height), x, y);
            }
            else if (parent.frameCount % 23 == 0) {
                parent.triangle(parent.random(parent.width), parent.random(parent.height), x, y, parent.random(parent.width), parent.random(parent.height));
            }
            else if (parent.frameCount % 4 == 0) {
                parent.rect(parent.random(parent.width), parent.random(parent.height), x, x, parent.random(50));
            }
            else {
                parent.rect(parent.random(parent.width), parent.random(parent.height), x, y, parent.random(50));
            }
        }
    }

}
