package data;

import static helpers.Artist.*;

import org.lwjgl.input.Mouse;

import ui.UI;

public class Game {

	private TileGrid grid;
	private Player player;
	private static WaveManager waveManager;
	private float test;
	private UI towerPickerUI;

	// Temp variables
	TowerCannon tower;

	public Game(int[][] map) {
		this.grid = new TileGrid(map);
		Enemy e = new Enemy(quickLoad("UFO64"), grid.getTile(0, 5), grid, TILE_SIZE, TILE_SIZE, 100, 100);
		waveManager = new WaveManager(e, 2, 3);
		player = new Player(grid, waveManager);
		player.setup();
		setupUI();
	}

	private void setupUI() {
		towerPickerUI = new UI();
		towerPickerUI.addButton("CannonBlue", "buttonCannonBlue", 0 * TILE_SIZE, 0);
		towerPickerUI.addButton("CannonRed", "buttonCannonRed", 1 * TILE_SIZE, 0);
		towerPickerUI.addButton("CannonIce", "buttonCannonIce", 2 * TILE_SIZE, 0);
	}

	private void updateUI() {
		towerPickerUI.draw();

		if (Mouse.next()) {
			boolean mouseClicked = Mouse.isButtonDown(0);
			if (mouseClicked) {
				if (towerPickerUI.isButtonClicked("CannonBlue")) {
					player.pickTower(new TowerCannonBlue(TowerType.CannonBlue, grid.getTile(0, 0), waveManager.getCurrentWave().getEnemyList()));
				}
				if (towerPickerUI.isButtonClicked("CannonRed")) {
					player.pickTower(new TowerCannonRed(TowerType.CannonRed, grid.getTile(1, 0), waveManager.getCurrentWave().getEnemyList()));
				}
				if (towerPickerUI.isButtonClicked("CannonIce")) {
					player.pickTower(new TowerIce(TowerType.CannonIce, grid.getTile(2, 0), waveManager.getCurrentWave().getEnemyList()));
				}
			}
		}
	}

	public void update() {
		grid.draw();
		waveManager.update();
		player.update();
		updateUI();
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
