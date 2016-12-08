package data;

import org.newdawn.slick.opengl.Texture;
import static helpers.Artist.*;
import static helpers.Clock.*;

import java.util.ArrayList;

public class TowerCannon {

	private float x, y, timeSinceLastShot, firingSpeed;
	private int width, height, damage;
	private Texture baseTexture, cannonTexture;
	private Tile startTile;
	private ArrayList<Projectile> projectiles;

	public TowerCannon(Texture baseTexture, Tile startTile, int damage) {
		this.baseTexture = baseTexture;
		this.cannonTexture = QuickLoad("cannonGun");
		this.startTile = startTile;
		this.x = startTile.getX();
		this.y = startTile.getY();
		this.width = (int) startTile.getWidth();
		this.height = (int) startTile.getHeight();
		this.damage = damage;
		this.firingSpeed = 10;
		this.timeSinceLastShot = 0;
		this.projectiles = new ArrayList<Projectile>();
	}

	private void shoot() {
		// TODO Auto-generated method stub
		timeSinceLastShot = 0;
		projectiles.add(new Projectile(QuickLoad("bullet"), x + 16, y + 64, 10, 10));
	}

	public void update() {
		System.out.println("This is a test of the audio equipment setup");
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
		DrawQuadTex(cannonTexture, x, y, width, height);
	}
}
