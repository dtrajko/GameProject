package data;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

import ui.UI;
import helpers.Clock;
import helpers.StateManager;
import helpers.StateManager.*;
import static helpers.Artist.*;

public class MainMenu {

	private Texture background;
	private UI menuUI;
	private boolean leftMouseButtonDown;
	public boolean menuBackgroundDisplayed = false;
	public boolean keyPressed;
	private static final int MINIMAP_WIDTH = 256;
	private static final int MINIMAP_HEIGHT = 192;

	public MainMenu() {
		background = quickLoad("mainmenu_transparent");
		menuUI = new UI();

		menuUI.addMinimap(LevelType.Level1.name, "newMap1", WIDTH / 2 - 128 - 32 - 256 - 32 - 256, (int) (HEIGHT * 0.36f), MINIMAP_WIDTH, MINIMAP_HEIGHT);
		menuUI.addMinimap(LevelType.Level2.name, "newMap2", WIDTH / 2 - 128 - 32 - 256,            (int) (HEIGHT * 0.36f), MINIMAP_WIDTH, MINIMAP_HEIGHT);
		menuUI.addMinimap(LevelType.Level3.name, "newMap3", WIDTH / 2 - 128,                       (int) (HEIGHT * 0.36f), MINIMAP_WIDTH, MINIMAP_HEIGHT);
		menuUI.addMinimap(LevelType.Level4.name, "newMap4", WIDTH / 2 - 128 + 32 + 256,            (int) (HEIGHT * 0.36f), MINIMAP_WIDTH, MINIMAP_HEIGHT);
		menuUI.addMinimap(LevelType.Level5.name, "newMap5", WIDTH / 2 - 128 + 32 + 256 + 32 + 256, (int) (HEIGHT * 0.36f), MINIMAP_WIDTH, MINIMAP_HEIGHT);

		menuUI.addButton("Play", "playButton", WIDTH / 2 - 128 - 256 - 128, (int) (HEIGHT * 0.8f));
		menuUI.addButton("Editor", "editorButton", WIDTH / 2 - 128, (int) (HEIGHT * 0.8f));
		menuUI.addButton("Quit", "quitButton", WIDTH / 2 - 128 + 256 + 128, (int) (HEIGHT * 0.8f));

		this.leftMouseButtonDown = false;
		this.keyPressed = false;
	}

	// Check if a button is clicked by the user, and if so do an action
	private void updateButtons() {
		if (Mouse.isButtonDown(0) && !leftMouseButtonDown) {

			if (menuUI.isMinimapClicked(LevelType.Level1.name)) {
				System.out.println(LevelType.Level1.name + " minimap clicked.");
				StateManager.setMap("newMap1");
				StateManager.setState(GameState.GAME);
			}
			if (menuUI.isMinimapClicked(LevelType.Level2.name)) {
				System.out.println(LevelType.Level2.name + " minimap clicked.");
				StateManager.setMap("newMap2");
				StateManager.setState(GameState.GAME);
			}
			if (menuUI.isMinimapClicked(LevelType.Level3.name)) {
				System.out.println(LevelType.Level3.name + " minimap clicked.");
				StateManager.setMap("newMap3");
				StateManager.setState(GameState.GAME);
			}
			if (menuUI.isMinimapClicked(LevelType.Level4.name)) {
				System.out.println(LevelType.Level4.name + " minimap clicked.");
				StateManager.setMap("newMap4");
				StateManager.setState(GameState.GAME);
			}
			if (menuUI.isMinimapClicked(LevelType.Level5.name)) {
				System.out.println(LevelType.Level5.name + " minimap clicked.");
				StateManager.setMap("newMap5");
				StateManager.setState(GameState.GAME);
			}

			if (menuUI.isButtonClicked("Play")) {
				System.out.println("Play button clicked.");
				StateManager.setState(GameState.GAME);
			}
			if (menuUI.isButtonClicked("Editor")) {
				System.out.println("Editor button clicked.");
				StateManager.setState(GameState.EDITOR);
			}
			if (menuUI.isButtonClicked("Quit")) {
				System.out.println("Quit button clicked.");
				System.exit(0);
			}
		}
		leftMouseButtonDown = Mouse.isButtonDown(0);
	}

	private void updateKeyboard() {
		// Handle Keyboard Input
		while (Keyboard.next()) {
			if ((Keyboard.getEventKey() == Keyboard.KEY_ESCAPE || Keyboard.getEventKey() == Keyboard.KEY_P) && keyPressed) {
				Clock.pause();
				StateManager.setState(GameState.GAME);
			}
			if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT && keyPressed) {
				StateManager.switchCurrentMap("right");
				menuUI.setNeedRefresh(true);
			}
			if (Keyboard.getEventKey() == Keyboard.KEY_LEFT && keyPressed) {
				StateManager.switchCurrentMap("left");
				menuUI.setNeedRefresh(true);
			}
			if (Keyboard.getEventKey() == Keyboard.KEY_RETURN && keyPressed) {
				Clock.pause();
				StateManager.setState(GameState.GAME);
			}
			keyPressed = Keyboard.getEventKeyState();
		}
	}

	public void update() {
		if (!menuBackgroundDisplayed) {
			drawQuadTex(background, 0, 0, 2048, 1024);
			menuBackgroundDisplayed = true;
		}
		if (menuUI.getNeedRefresh()) {
			menuUI.draw();
			menuUI.setNeedRefresh(false);
		}
		updateButtons();
		updateKeyboard();
	}

	public void draw() {
	}

	public void redisplaySplashScreen() {
		menuBackgroundDisplayed = false;
	}

	public UI getMenuUI() {
		return menuUI;
	}
}
