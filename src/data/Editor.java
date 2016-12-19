package data;

import helpers.Clock;
import helpers.Artist;
import static helpers.Artist.*;
import static helpers.Leveler.*;
import helpers.StateManager;
import helpers.StateManager.GameState;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class Editor {

	private TileGrid grid;
	private TileType[] types;
	private int index;
	private boolean leftMouseButtonDown;
	private boolean rightMouseButtonDown;
	
	public Editor() {
		this.grid = loadMap("newMap1");
		this.index = 0;
		this.types= new TileType[3];
		this.types[0] = TileType.Grass;
		this.types[1] = TileType.Dirt;
		this.types[2] = TileType.Water;
		this.leftMouseButtonDown = false;
		this.rightMouseButtonDown = false;
	}

	public void update() {
		grid.draw();

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
				Clock.pause();
				StateManager.setState(GameState.MAINMENU);
			}
		}
	}

	public void setTile() {
		grid.setTile((int) Math.floor(Mouse.getX() / TILE_SIZE), 
			(int) Math.floor((Artist.HEIGHT - Mouse.getY() - 1) / TILE_SIZE), types[index]);
	}

	// Allows editor to change which TileType is selected
	private void moveIndex() {
		index++;
		if (index > types.length - 1) {
			index = 0;
		}
	}
}
