package data;

import org.newdawn.slick.opengl.Texture;
import static helpers.Artist.*;
import static helpers.Clock.*;

import java.util.ArrayList;

public class TowerCannon {

	private float x, y, timeSinceLastShot, firingSpeed, angle;
	private int width, height, damage, range;
	private Texture baseTexture, cannonTexture;
	private Tile startTile;
	private ArrayList<Projectile> projectiles;
	private ArrayList<Enemy> enemies;
	private Enemy target;
	private boolean targeted;

	public TowerCannon(Texture baseTexture, Tile startTile, int damage, int range, ArrayList<Enemy> enemies) {
		this.baseTexture = baseTexture;
		this.cannonTexture = QuickLoad("cannonGun");
		this.startTile = startTile;
		this.x = startTile.getX();
		this.y = startTile.getY();
		this.width = (int) startTile.getWidth();
		this.height = (int) startTile.getHeight();
		this.damage = damage;
		this.range = range;
		this.firingSpeed = 1;
		this.timeSinceLastShot = 0;
		this.projectiles = new ArrayList<Projectile>();
		this.enemies = enemies;
		this.targeted = false;
		// this.target = aquireTarget();
		// this.angle = calculateAngle();
	}

	private Enemy aquireTarget() {
		// enemies = Game.getWaveManager().getCurrentWave().getEnemyList();
		Enemy closest = null;
		float closestDistance = 10000;
		for (Enemy e: enemies) {
			if (isInRange(e)) {
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

	private void shoot() {
		// TODO Auto-generated method stub
		timeSinceLastShot = 0;
		projectiles.add(new Projectile(QuickLoad("bullet"), target, 
		    x + Game.TILE_SIZE / 4, y + Game.TILE_SIZE / 4, Game.TILE_SIZE / 2, Game.TILE_SIZE / 2, 900, 10));
	}

	public void updateEnemyList(ArrayList<Enemy> newList) {
		enemies = newList;
	}

	public void update() {

		if (!targeted) {
			this.target = aquireTarget();
		}

		if (target != null && target.isAlive()) {
			angle = calculateAngle();
		} else {
			targeted = false;
		}

		timeSinceLastShot += Delta();
		if (timeSinceLastShot > firingSpeed) {
			shoot();
		}
		
		for (Projectile p: projectiles) {
			p.update();
		}

		draw();
	}

	public void draw() {
		DrawQuadTex(baseTexture, x, y, width, height);
		DrawQuadTexRot(cannonTexture, x, y, width, height, angle);
	}
}
