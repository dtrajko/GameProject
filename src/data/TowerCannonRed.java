package data;

import static helpers.Artist.TILE_SIZE;
import java.util.concurrent.CopyOnWriteArrayList;

public class TowerCannonRed extends Tower {

	public TowerCannonRed(TowerType type, Tile startTile, CopyOnWriteArrayList<Enemy> enemies) {
		super(type, startTile, enemies);
	}

	@Override
	public void shoot(Enemy target) {
		timeSinceLastShot = 0f;
		super.projectiles.add(new ProjectileCannonball(super.type.projectileType, target,
			super.getX() + TILE_SIZE / 4, super.getY() + TILE_SIZE / 4, TILE_SIZE / 2, TILE_SIZE / 2));			
	}
}
