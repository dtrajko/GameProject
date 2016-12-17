package data;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class TowerIce extends Tower {

	public TowerIce(TowerType type, Tile startTile, CopyOnWriteArrayList<Enemy> enemies) {
		super(type, startTile, enemies);	
	}
	
	@Override
	public void shoot() {
		super.shoot();
	}
}
