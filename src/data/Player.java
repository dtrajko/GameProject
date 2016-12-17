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
	private boolean leftMouseButtonDown, rightMouseButtonDown;
	public static int cash, lives;

	public Player(TileGrid grid, WaveManager waveManager) {
		this.grid = grid;
		this.waveManager = waveManager;
		this.towerList = new ArrayList<Tower>();
		this.leftMouseButtonDown = false;
		this.rightMouseButtonDown = false;
		this.cash = 0;
		this.lives = 0;
	}

	public void setup() {
		cash = 50;
		lives = 10;
	}

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
		for (Tower t: towerList) {
			t.update();
			t.updateEnemyList(waveManager.getCurrentWave().getEnemyList());
		}

		// Handle Mouse Input
		if (Mouse.isButtonDown(0) && !leftMouseButtonDown) {
			System.out.println("Mouse button 0 down.");
			if (modifyCash(-20)) {
				towerList.add(new TowerCannonBlue(TowerType.CannonBlue, 
						grid.getTile(Mouse.getX() / TILE_SIZE, (HEIGHT - Mouse.getY() - 1) / TILE_SIZE),
						waveManager.getCurrentWave().getEnemyList()));
			}
		}
		leftMouseButtonDown = Mouse.isButtonDown(0);

		if (Mouse.isButtonDown(1) && !rightMouseButtonDown) {
			System.out.println("Mouse button 1 down.");
			if (modifyCash(-55)) {
				towerList.add(new TowerIce(TowerType.CannonIce, 
						grid.getTile(Mouse.getX() / TILE_SIZE, (HEIGHT - Mouse.getY() - 1) / TILE_SIZE),
						waveManager.getCurrentWave().getEnemyList()));				
			}
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
}
