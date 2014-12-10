package com.brett.processing.visualize;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.brett.processing.visualize.randompix.RandomPixVisualizer;
import com.brett.processing.visualize.splatter.SplatterVisualizer;

import processing.core.PApplet;
import processing.core.PConstants;

public class VisualizationManager {

    protected PApplet parent;

    protected List<Visualizer> visualizers = new ArrayList<Visualizer>();

    protected Visualizer currentVisualizer = null;
    
    long nextSwitchTime = 0;
    
    protected Random random = new Random(System.currentTimeMillis());
    
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
    	if (System.currentTimeMillis() > (500 + nextSwitchTime)) {
            nextVisualization();
    	}
        if (System.currentTimeMillis() > nextSwitchTime) {
            parent.filter(PConstants.BLUR);
        }
        else {
        	currentVisualizer.draw();
        }
    }
    
    private void nextVisualization() {
        nextSwitchTime = System.currentTimeMillis() + (1000L * (5L + random.nextInt(15)));
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
}
