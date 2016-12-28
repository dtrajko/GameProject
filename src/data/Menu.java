package data;

import static helpers.Artist.HEIGHT;
import static helpers.Artist.TILE_SIZE;
import static helpers.Artist.drawQuadTex;
import static helpers.Artist.quickLoad;
import java.util.ArrayList;
import org.lwjgl.input.Mouse;
import ui.Button;

public class Menu {

	private String name;
	private ArrayList<Button> menuButtons;
	private int x, y, buttonAmount, optionsWidth, padding;

	public Menu(String name, int x, int y, int width, int height, int optionsWidth, int optionsHeight) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.optionsWidth = optionsWidth;
		this.padding = (width - (optionsWidth * TILE_SIZE)) / (optionsWidth + 1);
		this.buttonAmount = 0;
		this.menuButtons = new ArrayList<Button>();
	}

	public void addButton(Button b) {
		setButton(b);
	}

	public void quickAdd(String name, String buttonTextureName) {
		Button b = new Button(name, quickLoad(buttonTextureName), 0, 0);
		setButton(b);
	}

	public void setButton(Button b) {
		if (optionsWidth != 0) {
			b.setY(y + buttonAmount / optionsWidth * TILE_SIZE);
		}
		b.setX(x + (buttonAmount % optionsWidth) * (padding + TILE_SIZE) + padding);
		buttonAmount++;
		menuButtons.add(b);
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
		for (Button b: menuButtons) {
			if (b.getName().equals(buttonName)) {
				return b;
			}
		}
		return null;
	}

	public void draw() {
		for (Button b: menuButtons)
			drawQuadTex(b.getTexture(), b.getX(), b.getY(), b.getWidth(), b.getHeight());
	}

	public String getName() {
		return name;
	}
}
