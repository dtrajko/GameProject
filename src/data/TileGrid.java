package data;

import static helpers.Artist.*;

public class TileGrid {

	public Tile[][] map;
	private int tilesWide, tilesHigh;
	
	public TileGrid() {
		map = new Tile[20][14];
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				map[i][j] = new Tile(i * 64, j * 64, 64, 64, TileType.Grass);
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
					map[i][j] = new Tile(i * 64, j * 64, 64, 64, TileType.Grass);
					break;
				case 1:
					map[i][j] = new Tile(i * 64, j * 64, 64, 64, TileType.Dirt);
					break;
				case 2:
					map[i][j] = new Tile(i * 64, j * 64, 64, 64, TileType.Water);
					break;
				}
			}
		}
	}

	public void SetTile(int xCoord, int yCoord, TileType type) {
		map[xCoord][yCoord] = new Tile(xCoord * 64, yCoord * 64, 64, 64, type);
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
