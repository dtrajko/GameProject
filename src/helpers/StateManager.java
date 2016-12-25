package helpers;

import data.MainMenu;
import data.TileGrid;
import data.Game;
import data.Editor;
import static helpers.Leveler.*;

public class StateManager {

	public static enum GameState {
		MAINMENU, GAME, EDITOR
	}

	public static GameState gameState = GameState.MAINMENU;
	public static MainMenu mainMenu;
	public static Game game;
	public static Game gameSplash;
	public static Editor editor;

	public static long nextSecond = System.currentTimeMillis() + 1000;
	public static int framesInLastSecond = 0;
	public static int framesInCurrentSecond = 0;

	private static TileGrid map = loadMap("newMap1");
	private static String mapRaw = loadMapRaw("newMap1");
	private static String mapRawUpdate;

	public static void update() {
		switch(gameState) {
		case MAINMENU:
			if (mainMenu == null) {
				mainMenu = new MainMenu();
				gameSplash = new Game(map);
				gameSplash.update();
			}
			mainMenu.update();
			if (gameSplash != null) {
				gameSplash = null;
				System.out.println("gameSplash is NULL!!!!");	
			}
			break;
		case GAME:
			if (game == null) {
				game = new Game(map);
			}
			if (game.gameResumed) {
				System.out.println("Game resumed");
				mapRawUpdate = loadMapRaw("newMap1");
				if (!mapRawUpdate.equals(mapRaw)) {
					System.out.println("Map change detected. Loading the new map.");
					map = loadMap("newMap1");
					game = new Game(map);
				}
			}
			Clock.pause();
			game.gameResumed = false;
			game.update();
			break;
		case EDITOR:
			if (editor == null)
				editor = new Editor();
			editor.update();
			break;
		}

		// Calculate FPS
		long currentTime = System.currentTimeMillis();
		if (currentTime > nextSecond) {
			nextSecond += 1000;
			framesInLastSecond = framesInCurrentSecond;
			framesInCurrentSecond = 0;
		}
		framesInCurrentSecond++;
		// System.out.println(framesInLastSecond + " fps");
	}

	public static void setState(GameState newState) {
		gameState = newState;
		if (newState == GameState.MAINMENU) {
			mainMenu.redisplaySplashScreen();
		}
		if (newState == GameState.GAME) {
			game.gameResumed = true;
			mainMenu.redisplaySplashScreen();
		}
		if (newState == GameState.EDITOR) {
			if (editor != null) {
				editor.unselectTileType();				
			}
		}
	}
}
