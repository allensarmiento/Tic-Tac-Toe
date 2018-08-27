package main;

import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class GameObject {

	protected int x, y;
	protected int velX, velY; // Controls speed

	public GameObject(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public abstract void tick();
	public abstract void render(Graphics g);
	public abstract Rectangle getBounds();

	public float getX() { return x; }
	public float getY() { return y; }
}