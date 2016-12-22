package helpers;

import data.MainMenu;
import data.TileGrid;
import data.Game;
import data.Editor;
import static helpers.Leveler.loadMap;

public class StateManager {

	public static enum GameState {
		MAINMENU, GAME, EDITOR
	}

	public static GameState gameState = GameState.MAINMENU;
	public static MainMenu mainMenu;
	public static Game game;
	public static Editor editor;

	private static TileGrid map = loadMap("newMap1");

	public static void update() {
		switch(gameState) {
		case MAINMENU:
			if (mainMenu == null)
				mainMenu = new MainMenu();
			mainMenu.update();
			break;
		case GAME:
			if (game == null) {
				game = new Game(map);
			}
			Clock.pause();
			game.update();
			break;
		case EDITOR:
			if (editor == null)
				editor = new Editor();
			editor.update();
			break;
		}
	}

	public static void setState(GameState newState) {
		gameState = newState;
	}
}
