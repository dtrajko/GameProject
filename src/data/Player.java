package data;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import helpers.Clock;
import static helpers.Artist.*;
import helpers.StateManager;
import helpers.StateManager.GameState;
import java.util.ArrayList;

public class Player {

	private TileGrid grid;
	private WaveManager waveManager;
	private ArrayList<TowerCannon> towerList;
	private boolean leftMouseButtonDown;
	private boolean rightMouseButtonDown;

	public Player(TileGrid grid, WaveManager waveManager) {
		this.grid = grid;
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
			towerList.add(new TowerCannon(quickLoad("cannonBase"),
				grid.getTile(Mouse.getX() / TILE_SIZE, (HEIGHT - Mouse.getY() - 1) / TILE_SIZE),
				10, TILE_SIZE * 20, waveManager.getCurrentWave().getEnemyList()));
			// setTile();
		}
		leftMouseButtonDown = Mouse.isButtonDown(0);

		if (Mouse.isButtonDown(1) && !rightMouseButtonDown) {
			int towerX = (int) Mouse.getX() / TILE_SIZE;
			int towerY = grid.map[0].length - 1 - (int) Mouse.getY() / TILE_SIZE;
			towerList.add(new TowerCannon(quickLoad("cannonBase"),
				grid.getTile(towerX, towerY), 10, TILE_SIZE * 20, waveManager.getCurrentWave().getEnemyList()));
		}
		rightMouseButtonDown = Mouse.isButtonDown(1);

		// Handle Keyboard Input
		while (Keyboard.next()) {
			if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT && Keyboard.getEventKeyState()) {
				Clock.changeMultiplier(0.2f);
			}
			if (Keyboard.getEventKey() == Keyboard.KEY_LEFT && Keyboard.getEventKeyState()) {
				Clock.changeMultiplier(-0.2f);
			}
			if (Keyboard.getEventKey() == Keyboard.KEY_T && Keyboard.getEventKeyState()) {
				towerList.add(new TowerCannon(quickLoad("cannonBase"),
					grid.getTile(3, 1), 10, TILE_SIZE * 20, waveManager.getCurrentWave().getEnemyList()));
			}
			if ((Keyboard.getEventKey() == Keyboard.KEY_ESCAPE || Keyboard.getEventKey() == Keyboard.KEY_P) &&
					Keyboard.getEventKeyState()) {
				Clock.pause();
				StateManager.setState(GameState.MAINMENU);
			}
		}
	}
}
