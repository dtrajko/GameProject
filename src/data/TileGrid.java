package data;

import static helpers.Artist.*;

public class TileGrid {

	public Tile[][] map;
	private int tilesWide, tilesHigh;
	
	public TileGrid() {
		this.tilesWide = 20;
		this.tilesHigh = 14;
		map = new Tile[tilesWide][tilesHigh];
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				map[i][j] = new Tile(i * Game.TILE_SIZE, j * Game.TILE_SIZE, Game.TILE_SIZE, Game.TILE_SIZE, TileType.Grass);
			}
		}
	}

	public TileGrid(int[][] newMap) {
		this.tilesWide = newMap[0].length;
		this.tilesHigh = newMap.length;
		map = new Tile[tilesWide][tilesHigh];
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				switch (newMap[j][i]) {
				case 0:
					map[i][j] = new Tile(i * Game.TILE_SIZE, j * Game.TILE_SIZE, Game.TILE_SIZE, Game.TILE_SIZE, TileType.Grass);
					break;
				case 1:
					map[i][j] = new Tile(i * Game.TILE_SIZE, j * Game.TILE_SIZE, Game.TILE_SIZE, Game.TILE_SIZE, TileType.Dirt);
					break;
				case 2:
					map[i][j] = new Tile(i * Game.TILE_SIZE, j * Game.TILE_SIZE, Game.TILE_SIZE, Game.TILE_SIZE, TileType.Water);
					break;
				}
			}
		}
	}

	public void SetTile(int xCoord, int yCoord, TileType type) {
		map[xCoord][yCoord] = new Tile(xCoord * Game.TILE_SIZE, yCoord * Game.TILE_SIZE, Game.TILE_SIZE, Game.TILE_SIZE, type);
	}
	
	public Tile GetTile(int xPlace, int yPlace) {
		if (xPlace > -1 && xPlace < tilesWide && 
			yPlace > -1 && yPlace < tilesHigh) {
			return map[xPlace][yPlace];			
		} else {
			return new Tile(0, 0, 0, 0, TileType.NULL);
		}
	}

	public void Draw() {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				map[i][j].Draw();
			}
		}
	}

	public int getTilesWide() {
		return tilesWide;
	}

	public void setTilesWide(int tilesWide) {
		this.tilesWide = tilesWide;
	}

	public int getTilesHigh() {
		return tilesHigh;
	}

	public void setTilesHigh(int tilesHigh) {
		this.tilesHigh = tilesHigh;
	}
}
