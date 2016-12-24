package data;

import helpers.Clock;
import helpers.Artist;
import static helpers.Artist.*;
import static helpers.Leveler.*;
import helpers.StateManager;
import helpers.StateManager.GameState;
import ui.UI;
import ui.UI.Menu;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

public class Editor {

	private TileGrid grid;
	private TileType[] types;
	private int index;
	private boolean leftMouseButtonDown;
	private boolean rightMouseButtonDown;
	private UI editorUI;
	private Menu tilePickerMenu;
	private Texture menuBackground;

	public Editor() {
		this.grid = loadMap("newMap1");
		this.index = 0;
		this.types= new TileType[3];
		this.types[0] = TileType.Grass;
		this.types[1] = TileType.Dirt;
		this.types[2] = TileType.Water;
		this.menuBackground = quickLoad("menu_background_tiles");
		this.leftMouseButtonDown = false;
		this.rightMouseButtonDown = false;
		setupUI();
	}

	private void setupUI() {
		editorUI = new UI();
		tilePickerMenu = editorUI.createMenu("TilePicker", 1280, 80, 3 * TILE_SIZE, grid.getTilesHigh() * TILE_SIZE, 3, 0);
		tilePickerMenu.quickAdd("Grass", "grass64");
		tilePickerMenu.quickAdd("Dirt", "dirt64");
		tilePickerMenu.quickAdd("Water", "water64");
	}

	public void update() {

		// Handle Mouse Input
		if (Mouse.next()) {
			boolean mouseClicked = Mouse.isButtonDown(0);
			if (mouseClicked) {
				if (tilePickerMenu.isButtonClicked("Grass")) {
					index = 0;
					System.out.println("Grass button clicked!");
				} else if (tilePickerMenu.isButtonClicked("Dirt")) {
					index = 1;
					System.out.println("Dirt button clicked!");
				} else if (tilePickerMenu.isButtonClicked("Water")) {
					index = 2;
					System.out.println("Water button clicked!");
				} else {					
					if (Mouse.getX() > 0 && Mouse.getX() < this.grid.getTilesWide() * TILE_SIZE &&
						Mouse.getY() > 0 && Mouse.getY() < this.grid.getTilesHigh() * TILE_SIZE) {
						setTile();
					}
				}
			}
		}

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
		draw();
	}

	public void draw() {
		drawQuadTex(menuBackground, 1280, 0, 192, HEIGHT);
		grid.draw();
		editorUI.draw();
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
