package data;

import static helpers.Artist.*;
import static helpers.Clock.delta;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import org.newdawn.slick.opengl.Texture;

public abstract class Tower implements Entity {

	public TowerType type;
	public Enemy target;

	protected float timeSinceLastShot;
	protected ArrayList<Projectile> projectiles;
	protected float x, y;

	private float firingSpeed, angle;
	private int width, height, damage, range;
	private Texture[] textures;
	private CopyOnWriteArrayList<Enemy> enemies;
	private boolean targeted;
  
	public Tower(TowerType type, Tile startTile, CopyOnWriteArrayList<Enemy> enemies) {
		System.out.println("Tower type: " + type);
		this.type = type;
		this.textures = type.textures;
		this.damage = type.damage;
		this.range = type.range;
		this.x = startTile.getX();
		this.y = startTile.getY();
		this.width = startTile.getWidth();
		this.height = startTile.getHeight();
		this.enemies = enemies;
		this.targeted = false;
		this.timeSinceLastShot = 0f;
		this.projectiles = new ArrayList<Projectile>();
		this.firingSpeed = type.firingSpeed;
		this.angle = 0f;
	}

	private Enemy aquireTarget() {
		Enemy closest = null;
		float closestDistance = 10000;
		int enemy_index = 0;
		for (Enemy e: enemies) {
			enemy_index++;
			if (e.isAlive() && isInRange(e)) {
				System.out.println("Enemy " + enemy_index + " is in range.");
				if (findDistance(e) < closestDistance) {
					closestDistance = findDistance(e);
					closest = e;
				}
			}
		}
		if (closest != null) {
			targeted = true;
		}
		return closest;
	}

	private boolean isInRange(Enemy e) {
		float xDistance = Math.abs(e.getX() - x);
		float yDistance = Math.abs(e.getY() - y);
		if (xDistance < range && yDistance < range) {
			return true;
		}
		return false;
	}

	private float findDistance(Enemy e) {
		float xDistance = Math.abs(e.getX() - x);
		float yDistance = Math.abs(e.getY() - y);
		return xDistance + yDistance;
	}

	private float calculateAngle() {
		double angleTemp = 0;
		angleTemp = Math.atan2(target.getY() - y, target.getX() - x);
		return (float) Math.toDegrees(angleTemp) - 90;
	}

	public abstract void shoot(Enemy target);

	public void updateEnemyList(CopyOnWriteArrayList<Enemy> newList) {
		enemies = newList;
	}

	public void update() {

		if (!targeted) {
			target = aquireTarget();
		} else {
			angle = calculateAngle();
			if (timeSinceLastShot > firingSpeed) {
				shoot(target);
			}
		}

		if (target == null || target.isAlive() == false) {
			targeted = false;
		}

		timeSinceLastShot += delta();

		for (Projectile p: projectiles) {
			p.update();
		}

		draw();
	}

	@Override
	public void draw() {
		drawQuadTex(textures[0], x, y, width, height);	
		if (textures.length > 1) {
			for (int i = 1; i < textures.length; i++) {
				drawQuadTexRot(textures[i], x, y, width, height, angle);
			}			
		}
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

	public void setTarget(Enemy target) {
		this.target = target;
	}
}
