package spacewar;

import java.util.*;
import java.awt.*;
import java.awt.image.ImageObserver;

/*
 *  this class is for enemy properties and methods
 */
public class EnemyCraft extends SpaceObject implements ImageObserver, GameDefaults 
{
	//private int _scnPixSize, _xPosMove, _yPosMove, _bombXPos, _bombYPos, _numMissiles;
	//private int _scnPixSize, 
	private int _xPosMove;
	
	public EnemyCraft(int x1, int y1, RandomEnemyData randEnemy) 
    {
		
		super(randEnemy.get_randEnemyFileName());

		set_scnPixHeight(ENEMY_PIX_SIZE);
		set_scnPixWidth(ENEMY_PIX_SIZE);
    	_xPosMove = 0;
    	
    	//_yPosMove = 0;
    	
    	set_xPos(x1);
    	set_yPos(y1);
    	set_scoreVal(randEnemy.get_randEnemyScore());

    	//_numMissiles = 5;
    }


   public void moveLeft()
    {	
    	_xPosMove = -1;

    	// wrap to other end
    	if (get_xPos()<=SCREEN_EDGE_LEFT) {
    		
    		set_xPos(SCREEN_EDGE_RIGHT);
    	}
    }
    public void moveRight()
    {
    	_xPosMove = 1;
    	// wrap to other end
    	if (get_xPos() >= SCREEN_EDGE_RIGHT) {
			
			set_xPos(SCREEN_EDGE_LEFT+1);
		}
		
    }
    
    public void draw(Graphics gr)
    {
    	// adjust enmey delta positon before calling draw
    	set_xPos(get_xPos()+_xPosMove);
    	
    	super.draw(gr);
	}

	public boolean imageUpdate(Image img, int infoflags, int x, int y,
			int width, int height) {
		// TODO Auto-generated method stub
		return false;
	}

}
