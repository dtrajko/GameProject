package data;

import static helpers.Artist.*;
import static helpers.Clock.*;

public class Game {

	private TileGrid grid;
	private Player player;
	private static WaveManager waveManager;
	public static final int TILE_SIZE = 64;

	private float test;

	// Temp variables
	TowerCannon tower;

	public Game(int[][] map) {
		this.grid = new TileGrid(map);
		Enemy e = new Enemy(QuickLoad("UFO64"), grid.GetTile(1, 0), grid, Game.TILE_SIZE, Game.TILE_SIZE, 70, 25);
		waveManager = new WaveManager(e, 2, 3);
		this.player = new Player(grid, waveManager);
		// tower = new TowerCannon(QuickLoad("cannonBase"), grid.GetTile(7, 1), 10);
	}

	public void update() {
		grid.Draw();
		waveManager.update();
		player.update();
		// tower.update();
	}

	public static WaveManager getWaveManager() {
		return waveManager;
	}
}
