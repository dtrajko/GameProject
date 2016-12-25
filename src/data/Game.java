package data;

import static helpers.Artist.*;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

import helpers.StateManager;
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
	private Texture menuBackground;
	private Enemy[] enemyTypes;
	public static final int ENEMY_ALIEN_1 = 0;
	public static final int ENEMY_ALIEN_2 = 1;
	public static final int ENEMY_ALIEN_3 = 2;
	public static final int ENEMY_TANK_1 = 3;
	public static final int ENEMY_TANK_2 = 4;
	public static final int ENEMY_TANK_3 = 5;
	public static final int ENEMY_UFO = 6;
	public static boolean gameResumed = false;

	// Temp variables
	TowerCannon tower;

	public Game(TileGrid grid) {
		this.grid = grid;
		this.menuBackground = quickLoad("menu_background_towers");
		enemyTypes = new Enemy[7];
		enemyTypes[ENEMY_ALIEN_1] = new EnemyAlien(2, 2, grid);
		enemyTypes[ENEMY_ALIEN_2] = new EnemyAlien(2, 2, grid);
		enemyTypes[ENEMY_ALIEN_3] = new EnemyAlien(2, 2, grid);
		enemyTypes[ENEMY_TANK_1] = new EnemyTank(2, 2, grid);
		enemyTypes[ENEMY_TANK_2] = new EnemyTank(2, 2, grid);
		enemyTypes[ENEMY_TANK_3] = new EnemyTank(2, 2, grid);
		enemyTypes[ENEMY_UFO] = new EnemyUFO(2, 2, grid);
		waveManager = new WaveManager(enemyTypes, 2, 4);
		player = new Player(grid, waveManager);
		player.setup();
		setupUI();
	}

	public void setupUI() {
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
	}

	private void updateUI() {

		gameUI.draw();
		gameUI.drawString(1310, 760, "Lives: " + Player.lives);
		gameUI.drawString(1310, 790, "Cash: " + Player.cash);
		gameUI.drawString(1310, 820, "Wave: " + waveManager.getWaveNumber());
		gameUI.drawString(1310, 850, "FPS: " + StateManager.framesInLastSecond);

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
		drawQuadTex(menuBackground, 1280, 0, 192, HEIGHT);
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

	public Player getPlayer() {
		return player;
	}
}
