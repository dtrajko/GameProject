package data;

import static helpers.Artist.*;

import org.lwjgl.input.Mouse;

public class Game {

	private TileGrid grid;
	private Player player;
	private static WaveManager waveManager;
	private float test;

	// Temp variables
	TowerCannon tower;

	public Game(int[][] map) {
		this.grid = new TileGrid(map);
		Enemy e = new Enemy(quickLoad("UFO64"), grid.getTile(0, 5), grid, TILE_SIZE, TILE_SIZE, 100, 100);
		waveManager = new WaveManager(e, 2, 3);
		player = new Player(grid, waveManager);
		player.setup();
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
