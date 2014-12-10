package com.brett.processing.christmas.snowflake;

import processing.core.PApplet;

public class Flake {
    float x = 0;
    float y = 0;
    float sizeOfFlake = 10;
    float xInc = 0;
    float yInc = 0;
    private PApplet parent;
    private int myColor;

    public Flake(float x, float y, float size, PApplet parent) {
        this.x = x;
        this.y = y;
        this.parent = parent;
        this.sizeOfFlake = size;
        xInc = parent.random(1, 3);
        yInc = parent.random(3, 7);
        myColor = parent.color(parent.random(255), 128, 128);
    }

    void drawFlake() {
        int prevColor = parent.g.strokeColor;
        parent.stroke(myColor);
        parent.pushMatrix();
        parent.translate(x, y);
        int a = 360 / 6;
        for (int i = 0; i < 6; i++) {
            parent.strokeWeight((float) 0.1);

            parent.pushMatrix();
            for (int j = 0; j < sizeOfFlake; j++) {
                parent.strokeWeight(PApplet.map(j, 0, sizeOfFlake, 3, (float) 0.1));
                parent.line(0, 0, sizeOfFlake, sizeOfFlake);
                parent.line(0, 0, sizeOfFlake, -sizeOfFlake);
                parent.translate(sizeOfFlake, 0);
            }
            parent.popMatrix();
            parent.rotate(PApplet.radians((a)));
        }
        parent.popMatrix();
        x += xInc;
        y += yInc;

        if (y > parent.height) {
            y = -30;
            x = parent.random(parent.width);
        }
        parent.stroke(prevColor);
    }

}
