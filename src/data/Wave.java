package data;

import java.util.ArrayList;

import static helpers.Clock.*;

public class Wave {

	private float timeSinceLastSpawn, spawnTime;
	private Enemy enemyType;
	private ArrayList<Enemy> enemyList;
	private int enemiesPerWave;
	private boolean waveCompleted;

	public Wave(Enemy enemyType, float spawnTime, int enemiesPerWave) {
		this.enemyType = enemyType;
		this.spawnTime = spawnTime;
		this.enemiesPerWave = enemiesPerWave;
		this.timeSinceLastSpawn = 0;
		this.enemyList = new ArrayList<Enemy>();
		this.waveCompleted = false;
	}

	public void Update() {
		boolean allEnemiesDead = true;
		if (enemyList.size() < enemiesPerWave) {
			timeSinceLastSpawn += Delta();
			if (timeSinceLastSpawn > spawnTime) {
				Spawn();
				timeSinceLastSpawn = 0;
			}
		}
		for (Enemy e: enemyList) {
			if (e.isAlive()) {
				allEnemiesDead = false;
				e.update();
				e.draw();
			}
		}
		if (allEnemiesDead && enemyList.size() == enemiesPerWave) {
			waveCompleted = true;
		}
	}

	public void Spawn() {
		enemyList.add(new Enemy(enemyType.getTexture(), enemyType.getStartTile(), enemyType.getTileGrid(),
			Game.TILE_SIZE, Game.TILE_SIZE, enemyType.getSpeed(), enemyType.getHealth()));
	}

	public boolean isCompleted() {
		return waveCompleted;
	}

	public ArrayList<Enemy> getEnemyList() {
		return this.enemyList;
	}
}
