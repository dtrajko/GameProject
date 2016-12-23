package data;

import org.newdawn.slick.opengl.Texture;
import static helpers.Artist.*;

public enum TowerType {

	CannonRed(new Texture[] { quickLoad("cannonBase"), quickLoad("cannonGun") }, ProjectileType.CannonBall, 10, 1000, 1.5f, 30),
	CannonBlue(new Texture[] { quickLoad("cannonBaseBlue"), quickLoad("cannonGunBlue") }, ProjectileType.CannonBall, 30, 1000, 1.5f, 30),
	CannonIce(new Texture[] { quickLoad("cannonIceBase2"), quickLoad("cannonIceGun2") }, ProjectileType.Iceball, 30, 1000, 3f, 50);

	Texture[] textures;
	ProjectileType projectileType;
	int damage, range, cost;
	float firingSpeed;

	TowerType(Texture[] textures, ProjectileType projectileType, int damage, int range, float firingSpeed, int cost) {
		this.textures = textures;
		this.projectileType = projectileType;
		this.damage = damage;
		this.range = range;
		this.firingSpeed = firingSpeed;
		this.cost = cost;
	}
}
