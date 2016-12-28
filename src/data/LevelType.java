package data;

public enum LevelType {

	Level1("Level 1", 1),
	Level2("Level 2", 2),
	Level3("Level 3", 3),
	Level4("Level 4", 4),
	Level5("Level 5", 5);

	String name;
	int enemiesPerWave;

	LevelType(String name, int enemiesPerWave) {
		this.name = name;
		this.enemiesPerWave = enemiesPerWave;
	}

	public String getName() {
		return name;
	}
}
