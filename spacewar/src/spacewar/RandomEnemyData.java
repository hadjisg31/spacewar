package spacewar;

import java.util.Random;

public class RandomEnemyData implements GameDefaults {
	
	private static String _randEnemyFileName;
	private static int _randEnemyScore;	
	
	
	public RandomEnemyData() {
		int randEnemyNum = new Random().nextInt(NUM_ENEMY_TYPES);
		
		set_randEnemyFileName(GAME_IMAGE_PATH + ENEMY_TYPES[randEnemyNum]);
		set_randEnemyScore(ENEMY_SCORE_LEVELS[randEnemyNum]);
	}


	public String get_randEnemyFileName() {
		return _randEnemyFileName;
	}


	private void set_randEnemyFileName(String _randEnemyFileName) {
		this._randEnemyFileName = _randEnemyFileName;
	}


	public int get_randEnemyScore() {
		return _randEnemyScore;
	}


	private void set_randEnemyScore(int _randEnemyScore) {
		this._randEnemyScore = _randEnemyScore;
	}		

}
