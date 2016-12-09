package data;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import helpers.Clock;

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
		this.types= new TileType[3];
		this.types[0] = TileType.Grass;
		this.types[1] = TileType.Dirt;
		this.types[2] = TileType.Water;
		this.index = 0;
		this.waveManager = waveManager;
		this.towerList = new ArrayList<TowerCannon>();
		this.leftMouseButtonDown = false;
	}

	public void setTile() {
		grid.SetTile((int) Math.floor(Mouse.getX() / 64), 
			(int) Math.floor((HEIGHT - Mouse.getY() - 1) / 64), types[index]);
	}
	
	public void update() {
		
		for (TowerCannon t: towerList) {
			t.update();
		}

		// Handle Mouse Input
		if (Mouse.isButtonDown(0) && !leftMouseButtonDown) {
			System.out.println("Mouse button 0 down.");
			// setTile();
		}
		leftMouseButtonDown = Mouse.isButtonDown(0);

		if (Mouse.isButtonDown(1) && !rightMouseButtonDown) {
			int towerX = (int) Mouse.getX() / 64;
			int towerY = grid.map[0].length - 1 - (int) Mouse.getY() / 64;
			towerList.add(new TowerCannon(QuickLoad("cannonBase"),
				grid.GetTile(towerX, towerY), 10, waveManager.getCurrentWave().getEnemyList()));
		}
		rightMouseButtonDown = Mouse.isButtonDown(1);

		// Handle Keyboard Input
		while (Keyboard.next()) {
			if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT && Keyboard.getEventKeyState()) {
				moveIndex();
			}

			if (Keyboard.getEventKey() == Keyboard.KEY_T && Keyboard.getEventKeyState()) {
				towerList.add(new TowerCannon(QuickLoad("cannonBase"),
					grid.GetTile(3, 1), 10, waveManager.getCurrentWave().getEnemyList()));
			}

			if (Keyboard.getEventKey() == Keyboard.KEY_P && Keyboard.getEventKeyState()) {
				Clock.Pause();
			}
		}
	}

	private void moveIndex() {
		index++;
		if (index > types.length - 1) {
			index = 0;
		}
	}
}

