package data;

import static helpers.Artist.*;

import org.lwjgl.input.Mouse;

import ui.Button;
import ui.UI;
import ui.UI.Menu;

public class Game {

	private TileGrid grid;
	private Player player;
	private static WaveManager waveManager;
	private float test;
	private UI gameUI;
	private Menu towerPickerMenu;

	// Temp variables
	TowerCannon tower;

	public Game(TileGrid grid) {
		this.grid = grid;
		Enemy e = new Enemy(quickLoad("UFO64"), grid.getTile(2, 2), grid, TILE_SIZE, TILE_SIZE, 100, 100);
		waveManager = new WaveManager(e, 2, 3);
		player = new Player(grid, waveManager);
		player.setup();
		setupUI();
	}

	private void setupUI() {
		drawQuadTex(quickLoad("menu_background_towers"), 1280, 0, 192, HEIGHT);
		gameUI = new UI();
		towerPickerMenu = gameUI.createMenu("TowerPicker", 1280, 80, 3 * TILE_SIZE, grid.getTilesHigh() * TILE_SIZE, 3, 0);
		// towerPickerMenu.addButton(new Button("CannonBlue", quickLoad("buttonCannonBlue"), 0, 0));
		// towerPickerMenu.addButton(new Button("CannonRed", quickLoad("buttonCannonRed"), 0, 0));
		// towerPickerMenu.addButton(new Button("CannonIce", quickLoad("buttonCannonIce"), 0, 0));
		towerPickerMenu.quickAdd("CannonBlue", "buttonCannonBlue");
		towerPickerMenu.quickAdd("CannonRed", "buttonCannonRed");
		towerPickerMenu.quickAdd("CannonIce", "buttonCannonIce");
		towerPickerMenu.quickAdd("CannonIce2", "buttonCannonIce");
		towerPickerMenu.quickAdd("CannonBlue2", "buttonCannonBlue");
		towerPickerMenu.quickAdd("CannonRed2", "buttonCannonRed");
		gameUI.draw();
	}

	private void updateUI() {

		// Handle mouse input
		if (Mouse.next()) {
			boolean mouseClicked = Mouse.isButtonDown(0);
			if (mouseClicked) {
				if (towerPickerMenu.isButtonClicked("CannonBlue")) {
					player.pickTower(new TowerCannonBlue(TowerType.CannonBlue, grid.getTile(19, 0), waveManager.getCurrentWave().getEnemyList()));
				}
				if (towerPickerMenu.isButtonClicked("CannonRed")) {
					player.pickTower(new TowerCannonRed(TowerType.CannonRed, grid.getTile(19, 0), waveManager.getCurrentWave().getEnemyList()));
				}
				if (towerPickerMenu.isButtonClicked("CannonIce")) {
					player.pickTower(new TowerIce(TowerType.CannonIce, grid.getTile(19, 0), waveManager.getCurrentWave().getEnemyList()));
				}
				if (towerPickerMenu.isButtonClicked("CannonIce2")) {
					player.pickTower(new TowerIce(TowerType.CannonIce, grid.getTile(19, 0), waveManager.getCurrentWave().getEnemyList()));
				}
				if (towerPickerMenu.isButtonClicked("CannonBlue2")) {
					player.pickTower(new TowerCannonBlue(TowerType.CannonBlue, grid.getTile(19, 0), waveManager.getCurrentWave().getEnemyList()));
				}
				if (towerPickerMenu.isButtonClicked("CannonRed2")) {
					player.pickTower(new TowerCannonRed(TowerType.CannonRed, grid.getTile(19, 0), waveManager.getCurrentWave().getEnemyList()));
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
