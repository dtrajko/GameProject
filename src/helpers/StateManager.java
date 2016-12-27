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

	private static String mapFileName;
	private static String mapData;
	private static TileGrid map;
	private static String mapDataUpdate;
	private static boolean levelChanged = false;
	private static boolean reloadMinimaps = false;

	public static void initMap(String mapFile) {
		mapFileName = mapFile;
		mapData = loadMapData(mapFileName);
		map = loadMapFromData(mapData);
		System.out.println("initMap " + mapFile);
	}

	public static void update() {
		if (map == null) {
			initMap("newMap1");
		}
		switch(gameState) {
		case MAINMENU:
			if (mainMenu == null || reloadMinimaps) {
				mainMenu = new MainMenu();
				gameSplash = new Game(map);
				gameSplash.update();
				reloadMinimaps = false;
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
				mapDataUpdate = loadMapData(mapFileName);
				if (!mapDataUpdate.equals(mapData) || levelChanged) {
					System.out.println("Map or Level change detected. Loading the new map.");
					initMap(mapFileName);
					game = new Game(map);
					levelChanged = false;
				}
			}
			Clock.pause();
			game.gameResumed = false;
			game.update();
			break;
		case EDITOR:
			if (editor == null)
				editor = new Editor(mapFileName);
			if (levelChanged) {
				editor.setMap(mapFileName);
				levelChanged = false;
			}
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
			Game.gameResumed = true;
			mainMenu.redisplaySplashScreen();
		}
		if (newState == GameState.EDITOR) {
			if (editor != null) {
				editor.unselectTileType();				
			}
			levelChanged = true;
		}
	}

	public static String getMapData() {
		return mapData;
	}

	public static void setMap(String mapFileName) {
		levelChanged = true;
		initMap(mapFileName);
	}

	public static void reloadMinimaps() {
		reloadMinimaps = true;
	}
}
