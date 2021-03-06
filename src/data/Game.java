package data;

import static helpers.Artist.*;


import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;
import helpers.StateManager;
import ui.UI;

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
	public boolean gameResumed = false;
	private Texture minimap;
	private static final int MINIMAP_WIDTH = 160;
	private static final int MINIMAP_HEIGHT = 128;
	private Level level;

	public Game(TileGrid grid, Level level) {
		this.grid = grid;
		this.level = level;
		this.menuBackground = quickLoad("menu_background_towers");
		enemyTypes = new Enemy[7];
		enemyTypes[ENEMY_ALIEN_1] = new EnemyAlien(2, 2, grid);
		enemyTypes[ENEMY_ALIEN_2] = new EnemyAlien(2, 2, grid);
		enemyTypes[ENEMY_ALIEN_3] = new EnemyAlien(2, 2, grid);
		enemyTypes[ENEMY_TANK_1] = new EnemyTank(2, 2, grid);
		enemyTypes[ENEMY_TANK_2] = new EnemyTank(2, 2, grid);
		enemyTypes[ENEMY_TANK_3] = new EnemyTank(2, 2, grid);
		enemyTypes[ENEMY_UFO] = new EnemyUFO(2, 2, grid);
		waveManager = new WaveManager(enemyTypes, 2, level.getType().enemiesPerWave);
		player = new Player(grid, waveManager);
		player.setup();
		setupUI();
		loadMinimap();
		System.out.println("Game object initiated, level " + level.getType().name());
	}

	public Game(TileGrid grid, boolean gameSplash) {
		this.grid = grid;
		this.level = new Level(LevelType.Level1);
		this.menuBackground = quickLoad("menu_background_towers");
		enemyTypes = new Enemy[1];
		enemyTypes[0] = new EnemyUFO(2, 2, grid);
		waveManager = new WaveManager(enemyTypes, 1, 1);
		player = new Player(grid, waveManager);
		player.setup();
		setupUI();
		loadMinimap();
		System.out.println("Game splash object initiated.");
	}

	public void setupUI() {
		gameUI = new UI();
		towerPickerMenu = gameUI.createMenu("TowerPicker", 1280, 80, 3 * TILE_SIZE, grid.getTilesHigh() * TILE_SIZE, 3, 0);
		towerPickerMenu.quickAdd("CannonBlue",  "buttonCannonBlue");
		towerPickerMenu.quickAdd("CannonRed",   "buttonCannonRed");
		towerPickerMenu.quickAdd("CannonIce",   "buttonCannonIce");
	}

	private void updateUI() {
		gameUI.draw();
		UI.drawString(1310, 730, level.getType().getName());
		UI.drawString(1310, 760, "Lives: " + Player.lives);
		UI.drawString(1310, 790, "Cash: " + Player.cash);
		UI.drawString(1310, 820, "Wave: " + waveManager.getWaveNumber());
		UI.drawString(1310, 850, "FPS: " + StateManager.framesInLastSecond);

		drawQuadTex(minimap, 1296, 250, MINIMAP_WIDTH, MINIMAP_HEIGHT);

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

	public void loadMinimap() {
		Map map = new Map(StateManager.getMapData(), grid.getTilesWide(), grid.getTilesHigh());
		// minimapBuffImg = map.createMiniMapBuffImg();
		minimap = map.createMiniMap();
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

	public Level getLevel() {
		return level;
	}
}
