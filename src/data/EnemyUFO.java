package data;

public class EnemyUFO extends Enemy {

	public EnemyUFO(int tileX, int tileY, TileGrid grid) {
		super(tileX, tileY, grid);
		super.setTexture("UFO64");
		super.setSpeed(80);
	}
}
