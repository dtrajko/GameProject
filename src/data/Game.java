package data;

import static helpers.Artist.*;

public class Game {

	private TileGrid grid;
	private Player player;
	private static WaveManager waveManager;

	private float test;

	// Temp variables
	TowerCannon tower;

	public Game(int[][] map) {
		this.grid = new TileGrid(map);
		Enemy e = new Enemy(quickLoad("UFO64"), grid.getTile(1, 0), grid, TILE_SIZE, TILE_SIZE, 70, 25);
		waveManager = new WaveManager(e, 2, 3);
		this.player = new Player(grid, waveManager);
	}

	public void update() {
		grid.draw();
		waveManager.update();
		player.update();
	}

	public static WaveManager getWaveManager() {
		return waveManager;
	}

	public float getTest() {
		return test;
	}

	public void setTest(float test) {
		this.test = test;
	}
}
