package data;

import static helpers.Artist.*;
import org.newdawn.slick.opengl.Texture;

public enum ProjectileType {

	CannonBall(quickLoad("bullet"), 10, 500),
	Iceball(quickLoad("projectileIceball"), 6, 300);

	Texture texture;
	int damage;
	float speed;

	ProjectileType(Texture texture, int damage, float speed) {
		this.texture = texture;
		this.damage = damage;
		this.speed = speed;
		
	}
}
