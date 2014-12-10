package com.brett.processing.christmas.snow;

import processing.core.PApplet;
import processing.core.PVector;

public class Snow {

	private PVector location;
	private PVector acceleration;
	private PVector velocity;
	private PVector wind;
	private PVector gravity;

	private float snowHeight, snowWidth;
	private float mass;
	
	private boolean death = false;

	private PApplet parent = null;
	
	public Snow(PApplet parent) {
		this.parent = parent;
		snowHeight = parent.random(5, 60);
		snowWidth  = snowHeight;

		//location   = new PVector(random(z), -1000, random(-z, z)); //Only for 3D
		location = new PVector(parent.random(parent.width), -snowHeight);
		velocity   = new PVector(0, 0);
		acceleration = new PVector(0, 0);
		wind = new PVector(parent.random((float)-0.004, (float) 0.004), 0);

		mass = 100/snowWidth;
		gravity = new PVector(0, (float) 0.05);
	}

	void display() {
		drawSnow();
		moveSnow();
		applyForce(gravity);
		applyForce(wind);
	}

	void drawSnow() {
		parent.noStroke();
		parent.fill(255, 200);
		parent.ellipse(location.x, location.y, snowWidth, snowHeight);
		//Only for 3D
		/*pushMatrix();
		     translate(location.x, location.y, location.z);
		     fill(255);
		     noStroke();
		     sphere(snowHeight);
		     popMatrix();*/
	}

	void applyForce(PVector force) {
		PVector f = PVector.div(force, mass);
		acceleration.add(f);
	}

	void moveSnow() {
		velocity.add(acceleration);
		location.add(velocity);
		acceleration.mult(0);
		//if (location.y > 1000+snowHeight) { //Only for 3D
		if (location.y > parent.height+snowHeight) {
			death = true;
		}
	}

	public boolean isDeath() {
		return death;
	}

	public void setDeath(boolean death) {
		this.death = death;
	}
	
	
}