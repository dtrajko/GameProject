package data;

import org.newdawn.slick.opengl.Texture;
import static helpers.Artist.*;

public enum TowerType {

	CannonRed(new Texture[] { quickLoad("cannonBase"), quickLoad("cannonGun") }, 10, 1000, 1.5f),
	CannonBlue(new Texture[] { quickLoad("cannonBaseBlue"), quickLoad("cannonGunBlue") }, 30, 1000, 1.5f),
	CannonIce(new Texture[] { quickLoad("cannonIceBase2"), quickLoad("cannonIceGun2") }, 30, 1000, 1.5f);

	Texture[] textures;
	int damage, range;
	float firingSpeed;

	TowerType(Texture[] textures, int damage, int range, float firingSpeed) {
		this.textures = textures;
		this.damage = damage;
		this.range = range;
		this.firingSpeed = firingSpeed;
	}
}
