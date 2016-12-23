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

	public MainMenu() {
		background = quickLoad("mainmenu_transparent");
		menuUI = new UI();
		menuUI.addButton("Play", "playButton", WIDTH / 2 - 128, (int) (HEIGHT * 0.35f));
		menuUI.addButton("Editor", "editorButton", WIDTH / 2 - 128, (int) (HEIGHT * 0.45f));
		menuUI.addButton("Quit", "quitButton", WIDTH / 2 - 128, (int) (HEIGHT * 0.55f));
		this.leftMouseButtonDown = false;
		this.keyPressed = false;
	}

	// Check if a button is clicked by the user, and if so do an action
	private void updateButtons() {
		if (Mouse.isButtonDown(0) && !leftMouseButtonDown) {
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
