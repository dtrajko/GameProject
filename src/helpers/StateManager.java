package helpers;

import data.MainMenu;
import data.TileGrid;
import data.Game;
import data.Level;
import data.LevelType;
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
	private static String[] allMaps = new String[5];
	private static LevelType levelType = LevelType.Level1;

	public static void initMap(String mapFile) {
		mapFileName = mapFile;
		mapData = loadMapData(mapFileName);
		map = loadMapFromData(mapData);
		allMaps[0] = "newMap1";
		allMaps[1] = "newMap2";
		allMaps[2] = "newMap3";
		allMaps[3] = "newMap4";
		allMaps[4] = "newMap5";
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
				if (game == null) {
					gameSplash = new Game(map, true);
					gameSplash.update();
				}
				reloadMinimaps = false;
			}
			mainMenu.update();
			if (gameSplash != null) {
				gameSplash = null;
				System.out.println("gameSplash is null.");	
			}
			break;
		case GAME:
			if (game == null) {
				game = new Game(map, new Level(levelType));
			}
			if (game.gameResumed) {
				System.out.println("Game resumed");
				mapDataUpdate = loadMapData(mapFileName);
				if (!mapDataUpdate.equals(mapData) || levelChanged) {
					System.out.println("Map or Level change detected. Loading the new map.");
					initMap(mapFileName);
					game = new Game(map, new Level(levelType));
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
		mainMenu.getMenuUI().setNeedRefresh(true);
		if (newState == GameState.MAINMENU) {
			mainMenu.redisplaySplashScreen();
		}
		if (newState == GameState.GAME) {
			if (game != null)
				game.gameResumed = true;
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

	public static void setMap(String newMapFileName) {
		if (newMapFileName != mapFileName) {
			levelChanged = true;
			initMap(mapFileName);	
		}
	}

	public static void reloadMinimaps() {
		reloadMinimaps = true;
	}

	public static String getCurrentMap() {
		return mapFileName;
	}

	public static void switchCurrentMap(String direction) {
		int currentMapIndex = 0;
		for (int i = 0; i < allMaps.length; i++) {
			if (allMaps[i] == mapFileName) {
				currentMapIndex = i;
			}
		}
		switch(direction) {
		case "right":
			currentMapIndex++;
			break;
		case "left":
			currentMapIndex--;
			break;
		}
		if (currentMapIndex < 0)
			currentMapIndex = allMaps.length - 1;
		if (currentMapIndex > allMaps.length - 1)
			currentMapIndex = 0;
		System.out.println("switchCurrentMap to " + allMaps[currentMapIndex]);
		mapFileName = allMaps[currentMapIndex];

		// change level type based on map name
		switch(mapFileName) {
		case "newMap1":
			levelType = LevelType.Level1;
			break;
		case "newMap2":
			levelType = LevelType.Level2;
			break;
		case "newMap3":
			levelType = LevelType.Level3;
			break;
		case "newMap4":
			levelType = LevelType.Level4;
			break;
		case "newMap5":
			levelType = LevelType.Level5;
			break;
		}
	}
}
