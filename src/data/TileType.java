package data;

import java.awt.Color;

public enum TileType {

	Grass("grass64", true, new Color(104, 219, 101)),
	Dirt("dirt64", false, new Color(218, 136, 59)),
	Water("water64", false, new Color(56, 124, 171)),
	NULL("water64", false, Color.BLACK);

	String textureName;
	boolean buildable;
	Color color;

	TileType(String textureName, boolean buildable, Color color) {
		this.textureName = textureName;
		this.buildable = buildable;
		this.color = color;
	}
}
