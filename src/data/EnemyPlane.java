package data;

public class EnemyPlane extends Enemy {

	public EnemyPlane(int tileX, int tileY, TileGrid grid) {
		super(tileX, tileY, grid);
		super.setTexture("enemy_floating_1");
	}
}
