package com.brett.processing.image;

import java.util.Random;

import processing.core.PImage;

public class ImageHolder {
    
    private PImage image;
    
    private float x;
    
    private float y;
    
    private boolean nowShowing;
    
    private long expirationTime;
    
    private static Random rand = new Random(System.currentTimeMillis());
    
    public ImageHolder(PImage pimage) {
        this.image = pimage;
    }

    public PImage getImage() {
        return image;
    }

    public void setImage(PImage image) {
        this.image = image;
    }

    public float getX() {
        x = randomizeDelta(x);
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        y = randomizeDelta(y);
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public boolean isNowShowing() {
        if (isExpired()) {
            nowShowing = false;
        }
        return nowShowing;
    }

    public void setNowShowing(boolean nowShowing) {
        this.nowShowing = nowShowing;
    }

    public long getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(long expirationTime) {
        this.expirationTime = expirationTime;
    }
    
    public boolean isExpired() {
        return (System.currentTimeMillis() > expirationTime);
    }
    
    public int height() {
        if (image == null) {
            return 0;
        }
        else {
            return image.height;
        }
    }
    
    public int width() {
        if (image == null) {
            return 0;
        }
        else {
            return image.width;
        }
    }
    
    // function for generating a small random delta on a variable
    private float randomizeDelta(float x) {
        int val = rand.nextInt(5);
        if (val == 0)
            return x;
        else if (val == 1) 
            return x + 1;
        else if (val == 2) 
            return x + 2;
        else if (val == 3) 
            return x - 1;
        return x - 2;
    }
}
