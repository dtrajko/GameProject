package data;

import static helpers.Artist.*;
import static helpers.Clock.*;
import org.newdawn.slick.opengl.Texture;

public abstract class Projectile implements Entity {

	private Texture texture;
	private float x, y, speed, xVelocity, yVelocity;
	private int width, height, damage;
	private Enemy target;
	private boolean alive;

	public Projectile(ProjectileType type, Enemy target, float x, float y, int width, int height) {
		this.texture = type.texture; 
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.speed = type.speed;
		this.damage = type.damage;
		this.target = target;
		this.alive = true;
		this.xVelocity = 0f;
		this.yVelocity = 0f;
		calculateDirection();
	}

	private void calculateDirection() {
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
			if (target != null) {
				if (checkCollision(x, y, texture.getWidth(), texture.getHeight(), target.getX(), target.getY(), target.getWidth(), target.getHeight())) {
					System.out.println("Projectile hit its target.");
					damage();
				}				
			}
			draw();
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
}
