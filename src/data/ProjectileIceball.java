package data;

import org.newdawn.slick.opengl.Texture;

public class ProjectileIceball extends Projectile {

	public ProjectileIceball(Texture texture, Enemy target, float x, float y, int width, int height, float speed,
			int damage) {
		super(texture, target, x, y, width, height, speed, damage);
	}

	@Override
	public void damage() {
		super.getTarget().setSpeed(super.getTarget().getSpeed() - 10);
		super.damage();
	}
}
