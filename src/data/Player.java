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
	private ArrayList<Tower> towerList;
	private boolean leftMouseButtonDown, rightMouseButtonDown, holdingTower;
	private Tower tempTower;
	public static int cash, lives;

	public Player(TileGrid grid, WaveManager waveManager) {
		this.grid = grid;
		this.waveManager = waveManager;
		this.towerList = new ArrayList<Tower>();
		this.leftMouseButtonDown = false;
		this.rightMouseButtonDown = false;
		this.holdingTower = false;
		this.tempTower = null;
		this.cash = 0;
		this.lives = 0;
	}

	// Initialize cash and lives values for Player
	public void setup() {
		cash = 200;
		lives = 10;
	}

	// Check if player can afford tower, if so: charge player tower cost
	public static boolean modifyCash(int amount) {
		boolean result = false;
		if (cash + amount >= 0) {
			cash += amount;
			result = true;
		}
		System.out.println("Cash amount: " + cash);
		return result;
	}

	public static void modifyLives(int amount) {
		lives += amount;
		System.out.println("Lives: " + lives);
	}

	public void update() {
		// Update holding tower
		if (holdingTower) {
			float mouseX = getMouseTile().getX();
			float mouseY = getMouseTile().getY();
			if (mouseX > 0 && mouseX < this.grid.getTilesWide() * TILE_SIZE) {
				tempTower.setX(mouseX);
			}
			if (mouseY > 0 && mouseY < this.grid.getTilesHigh() * TILE_SIZE) {
				tempTower.setY(mouseY);
			}
			// System.out.println("Player update: mouseX = " + mouseX + ", mouseY = " + mouseY);
			tempTower.draw();
		}

		// Update all towers in the game
		for (Tower t: towerList) {
			t.update();
			t.updateEnemyList(waveManager.getCurrentWave().getEnemyList());
		}

		// Handle Mouse Input
		if (Mouse.isButtonDown(0) && !leftMouseButtonDown) {
			placeTower(tempTower);
		}
		leftMouseButtonDown = Mouse.isButtonDown(0);

		if (Mouse.isButtonDown(1) && !rightMouseButtonDown) {
			System.out.println("Right mouse button clicked.");
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
			if ((Keyboard.getEventKey() == Keyboard.KEY_ESCAPE || Keyboard.getEventKey() == Keyboard.KEY_P) &&
					Keyboard.getEventKeyState()) {
				Clock.pause();
				StateManager.setState(GameState.MAINMENU);
			}
		}
	}

	public ArrayList<Tower> getTowerList() {
		return this.towerList;
	}

	private void placeTower(Tower tower) {
		Tile currentTile = getMouseTile();
		if (holdingTower) {
			if (!currentTile.getOccupied() && modifyCash(-tower.getCost())) {
				towerList.add(tower);
				currentTile.setOccupied(true);
				holdingTower = false;
				tempTower = null;
				System.out.println("Placed tower " + tower.type.name());
			}
		}
	}

	public void pickTower(Tower tower) {
		tempTower = tower;
		holdingTower = true;
	}

	private Tile getMouseTile() {
		return grid.getTile(Mouse.getX() / TILE_SIZE, (HEIGHT - Mouse.getY() - 1) / TILE_SIZE);
	}
}
