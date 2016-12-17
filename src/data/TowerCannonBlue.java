package data;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class TowerCannonBlue extends Tower {

	public TowerCannonBlue(TowerType type, Tile startTile, CopyOnWriteArrayList<Enemy> enemies) {
		super(type, startTile, enemies);
	}
}
