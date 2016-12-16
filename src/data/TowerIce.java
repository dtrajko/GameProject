package data;

import java.util.ArrayList;

public class TowerIce extends Tower {

	public TowerIce(TowerType type, Tile startTile, ArrayList<Enemy> enemies) {
		super(type, startTile, enemies);	
	}
	
	@Override
	public void shoot() {
		super.shoot();
	}
}
