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

		menuUI.addMinimap("Level_1", "newMap1", WIDTH / 2 - 128 - 32 - 256 - 32 - 256, (int) (HEIGHT * 0.38f), MINIMAP_WIDTH, MINIMAP_HEIGHT);
		menuUI.addMinimap("Level_2", "newMap2", WIDTH / 2 - 128 - 32 - 256,            (int) (HEIGHT * 0.38f), MINIMAP_WIDTH, MINIMAP_HEIGHT);
		menuUI.addMinimap("Level_3", "newMap3", WIDTH / 2 - 128,                       (int) (HEIGHT * 0.38f), MINIMAP_WIDTH, MINIMAP_HEIGHT);
		menuUI.addMinimap("Level_4", "newMap4", WIDTH / 2 - 128 + 32 + 256,            (int) (HEIGHT * 0.38f), MINIMAP_WIDTH, MINIMAP_HEIGHT);
		menuUI.addMinimap("Level_5", "newMap5", WIDTH / 2 - 128 + 32 + 256 + 32 + 256, (int) (HEIGHT * 0.38f), MINIMAP_WIDTH, MINIMAP_HEIGHT);

		menuUI.addButton("Play", "playButton", WIDTH / 2 - 128 - 256 - 128, (int) (HEIGHT * 0.8f));
		menuUI.addButton("Editor", "editorButton", WIDTH / 2 - 128, (int) (HEIGHT * 0.8f));
		menuUI.addButton("Quit", "quitButton", WIDTH / 2 - 128 + 256 + 128, (int) (HEIGHT * 0.8f));

		this.leftMouseButtonDown = false;
		this.keyPressed = false;
	}

	// Check if a button is clicked by the user, and if so do an action
	private void updateButtons() {
		if (Mouse.isButtonDown(0) && !leftMouseButtonDown) {

			if (menuUI.isMinimapClicked("Level_1")) {
				System.out.println("Level_1 minimap clicked.");
				StateManager.setMap("newMap1");
				StateManager.setState(GameState.GAME);
			}
			if (menuUI.isMinimapClicked("Level_2")) {
				System.out.println("Level_2 minimap clicked.");
				StateManager.setMap("newMap2");
				StateManager.setState(GameState.GAME);
			}
			if (menuUI.isMinimapClicked("Level_3")) {
				System.out.println("Level_3 minimap clicked.");
				StateManager.setMap("newMap3");
				StateManager.setState(GameState.GAME);
			}
			if (menuUI.isMinimapClicked("Level_4")) {
				System.out.println("Level_4 minimap clicked.");
				StateManager.setMap("newMap4");
				StateManager.setState(GameState.GAME);
			}
			if (menuUI.isMinimapClicked("Level_5")) {
				System.out.println("Level_5 minimap clicked.");
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
			keyPressed = Keyboard.getEventKeyState();
		}
	}

	public void update() {
		if (!menuBackgroundDisplayed) {
			drawQuadTex(background, 0, 0, 2048, 1024);
			menuBackgroundDisplayed = true;
		}
		menuUI.draw();
		updateButtons();
		updateKeyboard();
	}

	public void redisplaySplashScreen() {
		menuBackgroundDisplayed = false;
	}
}
