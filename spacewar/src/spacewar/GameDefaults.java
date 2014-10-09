package spacewar;

import java.awt.Color;

public interface GameDefaults {
	
	// game area
	public static final int SCREEN_SIZE_X=859;
	public static final int SCREEN_SIZE_Y=524;
	
	// left and right playable limit
	public static final int SCREEN_EDGE_LEFT=5;
	public static final int SCREEN_EDGE_RIGHT=850;
	
	public static final int ENEMY_PIX_SIZE=25;
	public static final int MISSILE_PIX_SIZE=3;
	
	public static final int SCN_OBJ_PIX_ADJ_FACTOR=20;
	
	public static final Color SCN_OBJ_BG_COLOR=Color.black;

	/*
	 *  path where the java classes and image files are stored
	 *  the images have to be in the same location as java classes
	 */
	public static final String GAME_IMAGE_PATH="C:/Dev/Android/adt-workspace/spacewar/bin/spacewar/";

	/*
	 * score system 
	 */
	public	static final int ROOKIE=250;
	public	static final int SENIOR=500;
	public static final int COMMANDER=1000;
	
	/*
	 * section indicates number of enemy types you can use in the game
	 * NO_OF_ENEMIES corresponds to number of entries in ENEMY_TYPES
	 * 
	 */
	public final static int NUM_ENEMY_TYPES=5;
	public final static String[] ENEMY_TYPES = {"Redgalaga.png","Bluegalaxian.png","Purplegalaxian.png","Yellowgalaga.png","Galagancommander.png"};
	public final static int[] ENEMY_SCORE_LEVELS = {SENIOR, ROOKIE, SENIOR, ROOKIE, COMMANDER };
	
	public final static String SPACE_SHIP_FILE = GAME_IMAGE_PATH+"/Galagaship.png";
	public final static String WARP_ZONE_FILE = GAME_IMAGE_PATH+"/Spacebackground.png";
	
	/*
	 *  when updating this, remember to update SpaceWar class and add/remove entries in enemies[] array.
	 *  number of array elements must be equal to below number
	 */
	public final static int NO_OF_GAME_ENEMIES = 20;
	
}
