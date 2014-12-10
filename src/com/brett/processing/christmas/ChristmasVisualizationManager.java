package com.brett.processing.christmas;

import processing.core.PApplet;

import com.brett.processing.christmas.snow.SnowVisualizer;
import com.brett.processing.christmas.snowflake.SnowflakeVisualizer;
import com.brett.processing.visualize.VisualizationManager;

public class ChristmasVisualizationManager extends VisualizationManager {

    public ChristmasVisualizationManager(PApplet parent) {
        setParent(parent);
        addVisualizer(new SnowVisualizer(parent));
        addVisualizer(new SnowflakeVisualizer(parent));
    }
}
