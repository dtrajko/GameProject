package data;

import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import static helpers.Clock.*;
import static helpers.Artist.*;
import java.util.concurrent.ThreadLocalRandom;

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
		System.out.println("enemiesSpawned = " + enemiesSpawned + ", enemyChosen = " + enemyChosen);
		enemyList.add(new Enemy(enemyTypes[enemyChosen].getTexture(), enemyTypes[enemyChosen].getStartTile(),
			enemyTypes[enemyChosen].getTileGrid(), TILE_SIZE, TILE_SIZE, 
			enemyTypes[enemyChosen].getSpeed(), enemyTypes[enemyChosen].getHealth()));
		enemiesSpawned++;
	}

	public boolean isCompleted() {
		return waveCompleted;
	}

	public CopyOnWriteArrayList<Enemy> getEnemyList() {
		return this.enemyList;
	}
}
