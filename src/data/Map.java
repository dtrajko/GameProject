package data;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.util.BufferedImageUtil;
import static helpers.Artist.*;
import static helpers.Leveler.*;

public class Map {

	private int x, y, width, height, tilesWidth, tilesHeight;
	private TileGrid grid;
	private int[] pixels;
	private String name;
	private String mapDataFilename;
	private String mapData;
	private Texture texture;
	private static final int LWJGL_TEXTURE_SIZE = 256; // safe results are with X * 32 texture dimensions
	private int outline = 2; // in pixels
	private Texture minimap_outline;

	public Map(String mapData, int tilesWidth, int tilesHeight) {
		this.mapData = mapData;
		this.width = tilesWidth;
		this.height = tilesHeight;
		this.tilesWidth = tilesWidth;
		this.tilesHeight = tilesHeight;
		this.pixels = new int[tilesWidth * tilesHeight];
	}

	public Map(String name, String mapDataFilename, int x, int y, int width, int height) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.mapDataFilename = mapDataFilename;
		this.grid = loadMap(mapDataFilename);
		this.tilesWidth = grid.getTilesWide();
		this.tilesHeight = grid.getTilesHigh();
		this.mapData = loadMapData(mapDataFilename);
		this.texture = createMiniMap();
		this.minimap_outline = quickLoad("minimap_outline");
	}

	public void draw() {
		drawQuadTex(minimap_outline, x - outline, y - outline, width + 2 * outline, height + 2 * outline);
		drawQuadTex(texture, x, y, width, height);
	}

	public Texture createMiniMap() {
		Texture minimap = null;

		BufferedImage buffImg = new BufferedImage(tilesWidth, tilesHeight, BufferedImage.TYPE_INT_ARGB);

		for (int x = 0; x < tilesWidth; x++) {
			for (int y = 0; y < tilesHeight; y++) {
				int pixel = getTileType(mapData.substring(x * tilesHeight + y, x * tilesHeight + y + 1)).color.getRGB();
				buffImg.setRGB(x, y, pixel);
			}
		}
		BufferedImage buffImgScaled = resize(buffImg, BufferedImage.TYPE_INT_ARGB, LWJGL_TEXTURE_SIZE, LWJGL_TEXTURE_SIZE);
		try {
			minimap = BufferedImageUtil.getTexture("", buffImgScaled);
			System.out.println("IW = " + minimap.getImageWidth() + "," + " | IH = " + minimap.getImageHeight() + "," + 
					" | TW = " +  minimap.getTextureWidth() + "," + " | TW = " +  minimap.getTextureHeight());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return minimap;
	}

	public static BufferedImage resize(BufferedImage sourceImg, int imageType, int newWidth, int newHeight) {
		BufferedImage destImage = new BufferedImage(newWidth, newHeight, imageType);
		Graphics g = destImage.createGraphics();
		g.drawImage(sourceImg, 0, 0, newWidth, newHeight, null);
		g.dispose();
		return destImage;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Texture getTexture() {
		return texture;
	}

	public String getName() {
		return name;
	}
}
