package com.brett.processing.visualize;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import processing.core.PApplet;

import com.brett.processing.visualize.randompix.RandomPixVisualizer;
import com.brett.processing.visualize.splatter.SplatterVisualizer;

public class VisualizationManager {

    protected PApplet parent;

    protected List<Visualizer> visualizers = new ArrayList<Visualizer>();

    protected Visualizer currentVisualizer = null;
    
    long nextSwitchTime = 0;
    
    protected Random random = new Random(System.currentTimeMillis());
    
    protected int minimumTime = 5;
    
    protected int maximumTime = 20;
    
    public VisualizationManager() {
        
    }
    
    public VisualizationManager(PApplet parent) {
        this.parent = parent;
        visualizers.add(new DefaultVisualizer(parent));
        visualizers.add(new SplatterVisualizer(parent));
        visualizers.add(new DoNothingVisualizer(parent));
        visualizers.add(new RandomPixVisualizer(parent));
    }

    public void addVisualizer(Visualizer visualizer) {
        visualizers.add(visualizer);
    }

    public void draw() {
        if (System.currentTimeMillis() > nextSwitchTime) {
            nextVisualization();
        }
        else {
        	currentVisualizer.draw();
        }
    }
    
    private void nextVisualization() {
        nextSwitchTime = System.currentTimeMillis() + (1000L * (minimumTime + random.nextInt(maximumTime - minimumTime)));
        Visualizer nextVisualizer = visualizers.get(random.nextInt(visualizers.size()));
        if (nextVisualizer != currentVisualizer) {
            currentVisualizer = nextVisualizer;
            currentVisualizer.init();
        }
    }

    public PApplet getParent() {
        return parent;
    }

    public void setParent(PApplet parent) {
        this.parent = parent;
    }

    public int getMinimumTime() {
        return minimumTime;
    }

    public void setMinimumTime(int minimumTime) {
        this.minimumTime = minimumTime;
    }

    public int getMaximumTime() {
        return maximumTime;
    }

    public void setMaximumTime(int maximumTime) {
        this.maximumTime = maximumTime;
    }
    
    
}
