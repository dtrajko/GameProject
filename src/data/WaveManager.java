package data;

public class WaveManager {

	private float timeSinceLastWave, timeBetweenEnemies;
	private int waveNumber, enemiesPerWave;
	private Enemy[] enemyTypes;
	private Wave currentWave;

	public WaveManager(Enemy[] enemyTypes, float timeBetweenEnemies, int enemiesPerWave) {
		this.enemyTypes = enemyTypes;
		this.timeBetweenEnemies = timeBetweenEnemies;
		this.enemiesPerWave = enemiesPerWave;
		this.setTimeSinceLastWave(0);
		this.waveNumber = 0;
		this.currentWave = null;
		newWave();
	}

	public void update() {		
		if (!currentWave.isCompleted())
			currentWave.update();
		else
			newWave();
	}

	private void newWave() {
		currentWave = new Wave(enemyTypes, timeBetweenEnemies, enemiesPerWave);
		waveNumber++;
		System.out.println("Beginning Wave " + waveNumber);
	}
	
	public Wave getCurrentWave() {
		return currentWave;
	}

	public float getTimeSinceLastWave() {
		return timeSinceLastWave;
	}

	public void setTimeSinceLastWave(float timeSinceLastWave) {
		this.timeSinceLastWave = timeSinceLastWave;
	}

	public int getWaveNumber() {
		return waveNumber;
	}
}
