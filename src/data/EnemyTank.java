package data;

import org.newdawn.slick.opengl.Texture;
import static helpers.Artist.*;

public class EnemyTank extends Enemy {

	private Texture texture_up;
	private Texture texture_right;
	private Texture texture_down;
	private Texture texture_left;
	
	public EnemyTank(int tileX, int tileY, TileGrid grid) {
		super(tileX, tileY, grid);
		this.texture_up = quickLoad("enemy_tank_up");
		this.texture_right = quickLoad("enemy_tank_right");
		this.texture_down = quickLoad("enemy_tank_down");
		this.texture_left = quickLoad("enemy_tank_left");
		super.setTexture(texture_right);
		System.out.println("A new EnemyTank created");
	}

	public void update() {
		setDirectionTexture();
		super.update();
	}

	private void setDirectionTexture() {
		int directionX = getCurrentCheckpoint().getxDirection();
		int directionY = getCurrentCheckpoint().getyDirection();

		if (directionX == 0 && directionY == -1) {
			super.setTexture(texture_up);
		} else if (directionX == 1 && directionY == 0) {
			super.setTexture(texture_right);
		} else if (directionX == 0 && directionY == 1) {
			super.setTexture(texture_down);
		} else if (directionX == -1 && directionY == 0) {
			super.setTexture(texture_left);
		} else {
			// any of these textures, down direction makes some sense
			setTexture(texture_down);
		}
	}
}
