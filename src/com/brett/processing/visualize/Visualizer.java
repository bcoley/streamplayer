package com.brett.processing.visualize;

import java.util.Random;

import processing.core.PApplet;

public abstract class Visualizer {

    protected Random rand = new Random(System.currentTimeMillis());
    
    protected PApplet parent;
    
    public Visualizer(PApplet parent) {
        this.parent = parent;
    }
    
    public PApplet getParent() {
        return parent;
    }
    
    public void setParent(PApplet parent) {
        this.parent = parent;
    }
    
    public abstract void init();
    
    public abstract void draw();
    
}
