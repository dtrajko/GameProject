package data;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

import ui.UI;
import helpers.StateManager;
import helpers.StateManager.*;
import static helpers.Artist.*;

public class MainMenu {

	private Texture background;
	private UI menuUI;
	private boolean leftMouseButtonDown;

	public MainMenu() {
		background = quickLoad("mainmenu");
		menuUI = new UI();
		menuUI.addButton("Play", "playButton", WIDTH / 2 - 128, (int) (HEIGHT * 0.45f));
		menuUI.addButton("Editor", "editorButton", WIDTH / 2 - 128, (int) (HEIGHT * 0.55f));
		menuUI.addButton("Quit", "quitButton", WIDTH / 2 - 128, (int) (HEIGHT * 0.65f));
		this.leftMouseButtonDown = false;
	}
	
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

	public void update() {
		drawQuadTex(background, 0, 0, 2048, 1024);
		menuUI.draw();
		updateButtons();
	}
}
