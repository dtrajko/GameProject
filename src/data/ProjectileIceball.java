package data;

public class ProjectileIceball extends Projectile {

	public ProjectileIceball(ProjectileType type, Enemy target, float x, float y, int width, int height) {
		super(type, target, x, y, width, height);
		System.out.println("ProjectileIceball fired!");
	}

	@Override
	public void damage() {
		int speedDecrease = 10;
		if (super.getTarget().getSpeed() - speedDecrease > 0) {
			super.getTarget().setSpeed(super.getTarget().getSpeed() - speedDecrease);			
		}
		super.damage();
	}

	@Override
	public void update() {
		calculateDirection();
		super.update();
	}
}
