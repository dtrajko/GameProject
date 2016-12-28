package data;

import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import static helpers.Clock.*;
import static helpers.Artist.*;

public class Wave {

	private float timeSinceLastSpawn, spawnTime;
	private Enemy[] enemyTypes;
	private CopyOnWriteArrayList<Enemy> enemyList;
	private int enemiesPerWave, enemiesSpawned;
	private boolean waveCompleted;
	
	public Wave(Enemy[] enemyTypes, float spawnTime, int enemiesPerWave) {
		this.enemyTypes = enemyTypes;
		this.spawnTime = spawnTime;
		this.enemiesPerWave = enemiesPerWave;
		this.enemiesSpawned = 0;
		this.timeSinceLastSpawn = 0;
		this.enemyList = new CopyOnWriteArrayList<Enemy>();
		this.waveCompleted = false;
	}

	public void update() {
		// Assume all enemies are dead, until for loop proves otherwise
		boolean allEnemiesDead = true;
		if (enemiesSpawned < enemiesPerWave) {
			timeSinceLastSpawn += delta();
			if (timeSinceLastSpawn > spawnTime) {
				spawn();
				timeSinceLastSpawn = 0;
			}
		}
		for (Enemy e: enemyList) {
			if (e.isAlive()) {
				allEnemiesDead = false;
				e.update();
				e.draw();
			} else
				enemyList.remove(e);
		}
		if (allEnemiesDead && enemiesSpawned == enemiesPerWave)
			waveCompleted = true;
	}

	public void spawn() {
		Random random = new Random();
		// int enemyChosen = ThreadLocalRandom.current().nextInt(0, enemyTypes.length);
		int enemyChosen = random.nextInt(enemyTypes.length);
		switch (enemyChosen) {
		case Game.ENEMY_ALIEN_1:
		case Game.ENEMY_ALIEN_2:
		case Game.ENEMY_ALIEN_3:
			enemyList.add(new EnemyAlien(enemyTypes[enemyChosen].getStartTile().getXPlace(), 
					                     enemyTypes[enemyChosen].getStartTile().getXPlace(),
					                     enemyTypes[enemyChosen].getTileGrid()));
			break;
		case Game.ENEMY_TANK_1:
		case Game.ENEMY_TANK_2:
		case Game.ENEMY_TANK_3:
			enemyList.add(new EnemyTank(enemyTypes[enemyChosen].getStartTile().getXPlace(), 
                    enemyTypes[enemyChosen].getStartTile().getXPlace(),
                    enemyTypes[enemyChosen].getTileGrid()));
			break;
		case Game.ENEMY_UFO:
			enemyList.add(new EnemyUFO(enemyTypes[Game.ENEMY_UFO].getStartTile().getXPlace(), 
                    enemyTypes[Game.ENEMY_UFO].getStartTile().getXPlace(),
                    enemyTypes[Game.ENEMY_UFO].getTileGrid()));
			break;
		default:
			enemyList.add(new Enemy(enemyTypes[enemyChosen].getTexture(), enemyTypes[enemyChosen].getStartTile(),
					enemyTypes[enemyChosen].getTileGrid(), TILE_SIZE, TILE_SIZE, 
					enemyTypes[enemyChosen].getSpeed(), enemyTypes[enemyChosen].getHealth()));
		}
		System.out.println("enemiesSpawned = " + enemiesSpawned + ", enemyChosen = " + enemyChosen);
		enemiesSpawned++;
	}

	public boolean isCompleted() {
		return waveCompleted;
	}

	public CopyOnWriteArrayList<Enemy> getEnemyList() {
		return this.enemyList;
	}
}
