package data;

import static helpers.Artist.*;
import static helpers.Clock.*;
import org.newdawn.slick.opengl.Texture;

import helpers.Artist;

public abstract class Projectile implements Entity {

	private Texture texture;
	private float x, y, speed, xVelocity, yVelocity;
	private int width, height, damage;
	private ProjectileType type;
	private Enemy target;
	private boolean alive;

	public Projectile(ProjectileType type, Enemy target, float x, float y, int width, int height) {
		this.texture = type.texture; 
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.type = type;
		this.speed = type.speed;
		this.damage = type.damage;
		this.target = target;
		this.alive = true;
		this.xVelocity = 0f;
		this.yVelocity = 0f;
		calculateDirection();
	}

	protected void calculateDirection() {
		if (target != null) {
			float totalAllowedMovement = 1.0f;
			float xDistanceFromTarget = Math.abs(target.getX() - x + TILE_SIZE / 4);
			float yDistanceFromTarget = Math.abs(target.getY() - y + TILE_SIZE / 4);
			float totalDistanceFromTarget = xDistanceFromTarget + yDistanceFromTarget;
			float xPercentOfMovement = xDistanceFromTarget / totalDistanceFromTarget;
			xVelocity = xPercentOfMovement;
			yVelocity = totalAllowedMovement - xPercentOfMovement;
			// Set direction based on position of target relative to tower
			if (target.getX() < x) {
				xVelocity *= -1;
			}
			if (target.getY() < y) {
				yVelocity *= -1;
			}			
		}
	}

	//Deal damage to Enemy
	public void damage() {
		target.damage(damage);
		alive = false;
	}

	public void update() {
		if (alive) {
			x += xVelocity * speed * delta();
			y += yVelocity * speed * delta();
			// don't draw projectiles outside the grid (in the right side menu area)
			if (x <= 0 || x >= target.getGrid().getTilesWide() * TILE_SIZE - TILE_SIZE / 2 ||
				y <= 0 || y >= target.getGrid().getTilesHigh() * TILE_SIZE) {
				alive = false;
			}
			if (alive) {
				if (target != null) {
					if (checkCollision(x, y, texture.getWidth(), texture.getHeight(), target.getX(), target.getY(), target.getWidth(), target.getHeight())) {
						System.out.println("Projectile " + this.type.name() + " hit its target.");
						damage();
					}				
				}
				draw();
			}
		}
	}

	public void draw() {
		drawQuadTex(texture, x, y, 32, 32);
	}

	@Override
	public float getX() {
		return x;
	}

	@Override
	public float getY() {
		return y;
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public void setX(float x) {
		this.x = x;
	}

	@Override
	public void setY(float y) {
		this.y = y;
	}

	@Override
	public void setWidth(int width) {
		this.width = width;
		
	}

	@Override
	public void setHeight(int height) {
		this.height = height;
	}

	public Enemy getTarget() {
		return target;
	}

	public void setAlive(boolean status) {
		alive = status;
	}

	public ProjectileType getType() {
		return type;
	}
}
