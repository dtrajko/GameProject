package data;

import static helpers.Artist.*;
import static helpers.Leveler.*;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import helpers.Clock;
import helpers.StateManager;
import helpers.StateManager.GameState;

public class Editor {

	private TileGrid grid;
	private TileType[] types;
	private int index;
	private boolean leftMouseButtonDown;
	private boolean rightMouseButtonDown;
	
	public Editor() {
		this.grid = loadMap("newMap1");
		this.types= new TileType[3];
		this.types[0] = TileType.Grass;
		this.types[1] = TileType.Dirt;
		this.types[2] = TileType.Water;
		this.index = 0;
		this.leftMouseButtonDown = false;
		this.rightMouseButtonDown = false;
	}

	public void update() {
		grid.Draw();

		// Handle Mouse Input
		if (Mouse.isButtonDown(0) && !leftMouseButtonDown) {
			System.out.println("Mouse button 0 down.");
			setTile();
		}
		leftMouseButtonDown = Mouse.isButtonDown(0);

		if (Mouse.isButtonDown(1) && !rightMouseButtonDown) {

		}
		rightMouseButtonDown = Mouse.isButtonDown(1);

		// Handle Keyboard Input
		while (Keyboard.next()) {
			if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT && Keyboard.getEventKeyState()) {
				moveIndex();
			}

			if (Keyboard.getEventKey() == Keyboard.KEY_S && Keyboard.getEventKeyState()) {
				saveMap("newMap1", grid);
			}

			if ((Keyboard.getEventKey() == Keyboard.KEY_ESCAPE || Keyboard.getEventKey() == Keyboard.KEY_P) &&
					Keyboard.getEventKeyState()) {
				Clock.Pause();
				StateManager.setState(GameState.MAINMENU);
			}
		}
	}

	public void setTile() {
		grid.SetTile((int) Math.floor(Mouse.getX() / Game.TILE_SIZE), 
			(int) Math.floor((HEIGHT - Mouse.getY() - 1) / Game.TILE_SIZE), types[index]);
	}

	private void moveIndex() {
		index++;
		if (index > types.length - 1) {
			index = 0;
		}
	}
}
