package com.brett.processing.visualize.randompix;

import processing.core.PApplet;

import com.brett.processing.visualize.Visualizer;

public class RandomPixelVisualizer extends Visualizer {


    private int baseColor;
    
    private int numberOfPixels;
    
    boolean sparkle = false;

    public RandomPixelVisualizer(PApplet parent) {
        super(parent);
    }

    @Override
    public void init() {
        baseColor = parent.color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255), 125);
        numberOfPixels = 250 + rand.nextInt(200);
        sparkle = !sparkle;
    }

    @Override
    public void draw() {
        if (parent.frameCount % 5 == 0) {
            if (sparkle) {
                sparkle();
            }
            else {
                snow();
            }
        }
    }
    
    private void sparkle() {
        parent.loadPixels();
        for (int i = 0; i < numberOfPixels; i++) {
            parent.pixels[rand.nextInt(parent.pixels.length)] = parent.color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255), 125);
        }
        parent.updatePixels();
        numberOfPixels++;
    }
    
    private void snow() {
        parent.loadPixels();
        for (int i = 0; i < numberOfPixels; i++) {
            parent.pixels[rand.nextInt(parent.pixels.length)] =  baseColor++;
        }
        parent.updatePixels();
        numberOfPixels++;
    }

}
