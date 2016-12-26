package data;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.util.BufferedImageUtil;
import static helpers.Leveler.*;

public class Map {

	private int width, height;
	private int[] pixels;
	private String mapData;
	private static final int LWJGL_TEXTURE_SIZE = 256; // safe results are with X * 32 texture dimensions

	public Map(String mapData, int width, int height) {
		this.mapData = mapData;
		this.width = width;
		this.height = height;
		this.pixels = new int[width * height];
	}

	public Texture createMiniMap() {
		Texture minimap = null;

		BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int pixel = getTileType(mapData.substring(x * height + y, x * height + y + 1)).color.getRGB();
				System.out.println("X = " + x + ", Y = " + y + ", RGB: " + pixels[y * width + x]);
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
}
