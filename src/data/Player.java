package data;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import helpers.Clock;
import helpers.StateManager;
import helpers.StateManager.GameState;

import static helpers.Artist.*;
import static helpers.Clock.*;

import java.util.ArrayList;

public class Player {

	private TileGrid grid;
	private TileType[] types;
	private int index;
	private WaveManager waveManager;
	private ArrayList<TowerCannon> towerList;
	private boolean leftMouseButtonDown;
	private boolean rightMouseButtonDown;

	public Player(TileGrid grid, WaveManager waveManager) {
		this.grid = grid;
		this.index = 0;
		this.waveManager = waveManager;
		this.towerList = new ArrayList<TowerCannon>();
		this.leftMouseButtonDown = false;
		this.rightMouseButtonDown = false;
	}

	public void update() {
		
		for (TowerCannon t: towerList) {
			t.update();
			t.updateEnemyList(waveManager.getCurrentWave().getEnemyList());
		}

		// Handle Mouse Input
		if (Mouse.isButtonDown(0) && !leftMouseButtonDown) {
			System.out.println("Mouse button 0 down.");
			towerList.add(new TowerCannon(QuickLoad("cannonBase"),
				grid.GetTile(Mouse.getX() / Game.TILE_SIZE, (HEIGHT - Mouse.getY() - 1) / Game.TILE_SIZE),
				10, Game.TILE_SIZE * 20, waveManager.getCurrentWave().getEnemyList()));
			// setTile();
		}
		leftMouseButtonDown = Mouse.isButtonDown(0);

		if (Mouse.isButtonDown(1) && !rightMouseButtonDown) {
			int towerX = (int) Mouse.getX() / Game.TILE_SIZE;
			int towerY = grid.map[0].length - 1 - (int) Mouse.getY() / Game.TILE_SIZE;
			towerList.add(new TowerCannon(QuickLoad("cannonBase"),
				grid.GetTile(towerX, towerY), 10, Game.TILE_SIZE * 20, waveManager.getCurrentWave().getEnemyList()));
		}
		rightMouseButtonDown = Mouse.isButtonDown(1);

		// Handle Keyboard Input
		while (Keyboard.next()) {
			if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT && Keyboard.getEventKeyState()) {
				Clock.ChangeMultiplier(0.2f);
			}

			if (Keyboard.getEventKey() == Keyboard.KEY_LEFT && Keyboard.getEventKeyState()) {
				Clock.ChangeMultiplier(-0.2f);
			}

			if (Keyboard.getEventKey() == Keyboard.KEY_T && Keyboard.getEventKeyState()) {
				towerList.add(new TowerCannon(QuickLoad("cannonBase"),
					grid.GetTile(3, 1), 10, Game.TILE_SIZE * 20, waveManager.getCurrentWave().getEnemyList()));
			}

			if ((Keyboard.getEventKey() == Keyboard.KEY_ESCAPE || Keyboard.getEventKey() == Keyboard.KEY_P) &&
					Keyboard.getEventKeyState()) {
				Clock.Pause();
				StateManager.setState(GameState.MAINMENU);
			}
		}
	}
}

