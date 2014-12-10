package com.brett.processing.visualize.splatter;

import processing.core.PApplet;
import processing.core.PConstants;

/**
 * Splatter Trails adapted from
 * http://www.openprocessing.org/sketch/157792
 *
 */
public class Splatter {
    private static final float POS_DEV = 45;
    private static final float R_DEV = 15;
    private static final float VELOCITY_DEV = (float) 2.5;
    private static final float VELOCITY_MEAN = 1;
    private static final float TURNING_SPEED_DEV = (float) .25;
    private static final float TURNING_SPEED_MEAN = (float) .25;
    private static final float COLOR_DEV = 40;
    private static final float FADE_SPEED = (float) .3;
    private static final float SHRINK_SPEED = (float) .1;

    private float direction;
    private float velocity;
    private float turningSpeed;

    private float x, y, radius;
    private int color;
    private boolean finished;
    private PApplet parent;

    public Splatter(PApplet parent, int baseColor, float x, float y, float r) {
        this.parent = parent;
        this.x = nextGaussian() * POS_DEV + x;
        this.y = nextGaussian() * POS_DEV + y;
        this.radius = nextGaussian() * R_DEV + r;
        float colorDelta = nextGaussian() * COLOR_DEV;
        this.color = parent.color(colorDelta + ((baseColor >> 16) & 0xFF), // red
                colorDelta + ((baseColor >> 8) & 0xFF), // green
                colorDelta + (baseColor & 0xFF),  // blue
                colorDelta + ((baseColor >> 24) & 0xFF));  // alpha
        finished = false;
        direction = parent.random(PConstants.TWO_PI);
        velocity = nextGaussian() * VELOCITY_DEV + VELOCITY_MEAN;
        turningSpeed = nextGaussian() * TURNING_SPEED_DEV + TURNING_SPEED_MEAN;
    }

    protected float nextGaussian() {
        return ((parent.random(1) * 2 - 1) + (parent.random(1) * 2 - 1) + (parent.random(1) * 2 - 1));
    }

    public void update() {
        radius -= SHRINK_SPEED;
        if (radius < 0) {
            finished = true;
            return;
        }
        float a = parent.alpha(color);
        a -= FADE_SPEED;
       
        if (a < 0) {
            finished = true;
            return;
        }
        this.color = parent.color(((this.color >> 16) & 0xFF), ((this.color >> 8) & 0xFF), (this.color & 0xFF), a);
        walk();
    }

    public void walk() {
        direction += parent.random(-turningSpeed, turningSpeed);
        x += PApplet.cos(direction) * velocity;
        y += PApplet.sin(direction) * velocity;

        if (x < -radius || x > parent.width + radius || y < -radius || y > parent.height + radius)
            finished = true;
    }

    public void draw() {
        parent.noStroke();
        parent.fill(color);
        parent.ellipse(x, y, radius, radius);
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }
    
    
}
