package ui;

import java.awt.Font;
import java.util.ArrayList;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.TrueTypeFont;

import data.Map;
import data.Menu;

import static helpers.Artist.*;

public class UI {
	
	private ArrayList<Button> buttonList;
	private ArrayList<Menu> menuList;
	private ArrayList<Map> mapList;
	
	private static TrueTypeFont font;
	private Font awtFont;

	private boolean needRefresh = true;

	
	public UI() {
		buttonList = new ArrayList<Button>();
		menuList = new ArrayList<Menu>();
		mapList = new ArrayList<Map>();
		awtFont = new Font(Font.SERIF, Font.BOLD, 18);
		font = new TrueTypeFont(awtFont, false);
	}

	public static void drawString(int x, int y, String text) {
		font.drawString(x, y, text);
	}

	public void addButton(String name, String textureName, int x, int y) {
		buttonList.add(new Button(name, quickLoad(textureName), x, y));
	}

	public void addMinimap(String name, String mapFileName, int x, int y, int width, int height) {
		mapList.add(new Map(name, mapFileName, x, y, width, height));
	}

	public boolean isButtonClicked(String buttonName) {
		Button b = getButton(buttonName);
		float mouseY = HEIGHT - Mouse.getY() - 1;
		if (Mouse.getX() > b.getX() && Mouse.getX() < b.getX() + b.getWidth() &&
		mouseY > b.getY() && mouseY < b.getY() + b.getHeight()) {
			return true;
		}
		return false;
	}

	private Button getButton(String buttonName) {
		for (Button b: buttonList) {
			if (b.getName().equals(buttonName)) {
				return b;
			}
		}
		return null;
	}

	public boolean isMinimapClicked(String minimapName) {
		Map map = getMinimap(minimapName);
		float mouseY = HEIGHT - Mouse.getY() - 1;
		if (Mouse.getX() > map.getX() && Mouse.getX() < map.getX() + map.getWidth() &&
		mouseY > map.getY() && mouseY < map.getY() + map.getHeight()) {
			return true;
		}
		return false;
	}

	private Map getMinimap(String minimapName) {
		for (Map map: mapList) {
			if (map.getName().equals(minimapName)) {
				return map;
			}
		}
		return null;
	}

	public Menu createMenu(String name, int x, int y, int width, int height, int optionsWidth, int optionsHeight) {
		Menu menu = new Menu(name, x, y, width, height, optionsWidth, optionsHeight);
		menuList.add(menu);
		return menu;
	}

	public Menu getMenu(String name) {
		for (Menu m: menuList) {
			if (name.equals(m.getName()))
				return m;
		}
		return null;
	}

	public void draw() {
		for (Button b: buttonList) {
			drawQuadTex(b.getTexture(), b.getX(), b.getY(), b.getWidth(), b.getHeight());
		}
		for (Map map: mapList) {
			map.draw();
		}
		for (Menu m: menuList) {
			m.draw();
		}
	}

	public void setNeedRefresh(boolean needRefresh) {
		this.needRefresh = needRefresh;
	}

	public boolean getNeedRefresh() {
		return needRefresh;
	}
}
