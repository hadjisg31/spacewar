package spacewar;

/*
 * This class handles the game operation and main logic
 * 
 */

import java.awt.event.*;
import java.util.Random;

import javax.swing.*;

import java.awt.*;

public class SpaceWar implements GameDefaults
{	
	static int cur_bombXPos = -1;
	static int cur_bombYPos = -1;
	
	private static SpaceObject spaceShip;
	private static SpaceObject warpZone;
	private static EnemyCraft [] enemies;
	
	private static JFrame gameFrame;
	
	SpaceWar() {
		
		// set spaceship initial screen location to 395 and 380 with pixel size ENEMY_PIX_SIZE+10 -- make it bigger
		spaceShip = new SpaceObject(SPACE_SHIP_FILE, 395, 380, ENEMY_PIX_SIZE+10, ENEMY_PIX_SIZE+10);
		spaceShip.set_numLives(1);
		
		// initialize warpZone game screen starting from 0,0 location to maximum screen size of X and Y.
		warpZone = new SpaceObject(WARP_ZONE_FILE, 0, 0, SCREEN_SIZE_X, SCREEN_SIZE_Y);
		
		enemies = new EnemyCraft[NO_OF_GAME_ENEMIES];
		
		/* 
		 * need to improve this part to dynamically allocate enemy positions without assigning locations for each
		 * number of enemies corresponds to NO_OF_GAME_ENEMIES count
		 */
		
		enemies[0]= new EnemyCraft(200, 50, new RandomEnemyData());
		enemies[1] = new EnemyCraft(300,50, new RandomEnemyData());
		enemies[2] = new EnemyCraft(400, 50, new RandomEnemyData());
		enemies[3] = new EnemyCraft(500, 50, new RandomEnemyData());
		enemies[4] = new EnemyCraft (600, 50, new RandomEnemyData());
		enemies[5]= new EnemyCraft(200, 85, new RandomEnemyData());
		enemies[6]= new EnemyCraft(300,85, new RandomEnemyData());
		enemies[7] = new EnemyCraft(400, 85, new RandomEnemyData());
		enemies[8] = new EnemyCraft(500, 85, new RandomEnemyData());
		enemies[9] = new EnemyCraft (600, 85, new RandomEnemyData());
		enemies[10]= new EnemyCraft(200, 120, new RandomEnemyData());
		enemies[11]= new EnemyCraft(300, 120, new RandomEnemyData());
		enemies[12] = new EnemyCraft(400, 120, new RandomEnemyData());
		enemies[13] = new EnemyCraft(500, 120, new RandomEnemyData());
		enemies[14] = new EnemyCraft (600, 120, new RandomEnemyData());
		enemies[15]= new EnemyCraft(200, 155, new RandomEnemyData());
		enemies[16]= new EnemyCraft(300, 155, new RandomEnemyData());
		enemies[17] = new EnemyCraft(400, 155, new RandomEnemyData());
		enemies[18] = new EnemyCraft(500, 155, new RandomEnemyData());
		enemies[19] = new EnemyCraft (600, 155, new RandomEnemyData());
		
	}
	
	
	public static void main (String []args)
	{
		// call constructor to initialize game objects
		new SpaceWar();
		
		gameFrame = new JFrame();
		
		gameFrame.setTitle("Space War");
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		GraphicPanel gamePanel = new GraphicPanel();
		gamePanel.setFocusable(true);			
		// add game panel to frame for keylistener
		gameFrame.getContentPane().add(gamePanel);		
		gameFrame.pack();
		// set the size of the game frame
		gameFrame.setSize(SCREEN_SIZE_X, SCREEN_SIZE_Y);			
		gameFrame.setVisible(true);
		
	}
	
	/* 
	 * create and display dialog screen to display game over or success
	 */
	public static class  MissionControlComm extends Dialog {
		
		private String dialogMsg1;
		private String dialogMsg2;
		 
		public MissionControlComm(Frame parent, String msg1, String msg2){
		     super(parent, true);         
		     
		     this.dialogMsg1 = msg1;
		     this.dialogMsg2 = msg2;
		     
		     setBackground(Color.gray);
		     setLayout(new BorderLayout());
		     Panel panel = new Panel();
		     panel.add(new Button("Close"));
		     add("South", panel);
		     setSize(300,200);
		
		     addWindowListener(new WindowAdapter() {
		        public void windowClosing(WindowEvent windowEvent){
		           dispose();
		        }
		     });
			}

		  public boolean action(Event evt, Object arg){
		     if(arg.equals("Close")){
		        dispose();
		        return true;
		     }
		     return false;
		  }
	
		  public void paint(Graphics g){
		     g.setColor(Color.white);
		     g.drawString(dialogMsg1, 25,70 );
		     g.drawString(dialogMsg2, 60, 90);      
		      }
	}	

	private static class GraphicPanel extends JPanel implements KeyListener, ActionListener
	{
		private Timer timer;
		private int spaceShipXVelocity;
		private int cur_missileXPos, cur_missileYPos, missileXVelocity, mdy; // add missile variables here
		private int shooter = -1;
		private int score;
		int count = 300;
		boolean hitByBomb;

		public GraphicPanel()
		{
			timer = new Timer(10, this);
			
			spaceShipXVelocity=0;
			cur_missileYPos = -1;	
			score = 0;// initialize the score to 0
			hitByBomb = false;

			timer.start();
			addKeyListener(this);
		}
	
		public void keyPressed(KeyEvent e)
		{
			if(e.getKeyCode()==KeyEvent.VK_Q) {
				// if player hits Q, quit the game.
				System.exit(0);
			}
			
			if(e.getKeyCode()==KeyEvent.VK_P) {
				// if player hits P, pause the game.
				timer.stop();
			}
			
			if(e.getKeyCode()==KeyEvent.VK_X) {
				// if player hits spaceShip.get_xPos(), resume the game.
				timer.start();
			}
			
			if(e.getKeyCode()==KeyEvent.VK_A) { 
				// if player hits A, increase left velocity cur_bombYPos one.
				spaceShipXVelocity--;
				if(spaceShipXVelocity<-3) spaceShipXVelocity=-3;
			}
			else if(e.getKeyCode()==KeyEvent.VK_D) { 
				// if player hits d, increase right velocity cur_bombYPos one.
				
				spaceShipXVelocity++;
				if(spaceShipXVelocity>3) spaceShipXVelocity=3;
			}
			else if(e.getKeyCode()==KeyEvent.VK_S) { 
				// if player hits s, the launcher will stop moving.
				
				spaceShipXVelocity=0;
			}	
			else if (cur_missileYPos==-1 && e.getKeyCode()==KeyEvent.VK_SPACE) {  
			 // if player presses space bar, launch a missile (if the player still has missiles left)
				
				cur_missileXPos=spaceShip.get_xPos()+SCN_OBJ_PIX_ADJ_FACTOR;
				cur_missileYPos=spaceShip.get_yPos();
				missileXVelocity=spaceShipXVelocity;
			}
			
		}

		
		public void keyTyped(KeyEvent e) {}     // implement the rest 
		public void keyReleased(KeyEvent e) {} //of the KeyListener

		public void actionPerformed(ActionEvent e)	// on timer event
		{	
			boolean won = false;
			Random g = new Random();
			
			spaceShip.set_xPos(spaceShip.get_xPos()+spaceShipXVelocity);		// move missile launcher
			
			if(spaceShip.get_xPos()>SCREEN_EDGE_RIGHT) 	{
				// wrap missile launcher around screen, back to left most
				spaceShip.set_xPos(SCREEN_EDGE_LEFT) ;
			}
			else if(spaceShip.get_xPos() < SCREEN_EDGE_LEFT)  {
				// back to right most
				spaceShip.set_xPos(SCREEN_EDGE_RIGHT) ;
			}
				
			if (cur_missileYPos!=-1)	// move missile code here
			{
				cur_missileYPos=cur_missileYPos-4;
				cur_missileXPos=cur_missileXPos+missileXVelocity;
			}
			
			if (cur_bombYPos!=-1)
				cur_bombYPos+=4;
			
			if (cur_missileYPos<0)
					cur_missileYPos=-1;
			
			if (cur_missileXPos<SCREEN_EDGE_LEFT||cur_missileXPos>SCREEN_EDGE_RIGHT)
				cur_missileXPos = -1;
			
			count++;
			
			if (count > 300)
			{	
				count = 0;
				
				for (int h = 0; h<NO_OF_GAME_ENEMIES; h++)
				{
					if (enemies[h] != null)
						enemies[h].moveLeft();
				}
			}
			else if (count == 150)
			{
				for (int h = 0; h<NO_OF_GAME_ENEMIES; h++)
				{
					if (enemies[h] != null)
						enemies[h].moveRight();
				}
		
			}
			
			// set won flag initiall to true
			won = true;
			// loop true each enemy object and check if any is still active, and detect collision
			for(int cnt=0;cnt<=NO_OF_GAME_ENEMIES-1;cnt++) {

				if(enemies[cnt]!=null) {
					
					won = false;
					if (detectCollision( enemies[cnt], enemies[cnt].get_xPos(), cur_missileXPos, enemies[cnt].get_yPos(), cur_missileYPos )) {
						cur_missileYPos = -1;
						score += enemies[cnt].get_scoreVal();
						// if enemy is hit, set it to null, means terminated and removed from screen
						enemies[cnt] = null;
					}
				}
				
			}
			
			// Test if won flag is true
			if(won) {
				
				MissionControlComm aboutDialog = new MissionControlComm(gameFrame, "Congratulations! All enemies defeated.", "Mission successful!!");
	            aboutDialog.setVisible(true);
	            
				System.exit(0);					
			}
			
			// Hit by bomb .. GAME OVER!
			if (detectCollision( spaceShip, spaceShip.get_xPos(), cur_bombXPos, spaceShip.get_yPos(), cur_bombYPos )) {
				
				// ship got hit by bomb, decrease live by 1
				spaceShip.set_numLives(spaceShip.get_numLives()-1);
				cur_bombXPos = -1;
				cur_bombYPos = -1;
				
				hitByBomb = true;
				
				if(spaceShip.get_numLives()<1) {
	
					MissionControlComm aboutDialog = new MissionControlComm(gameFrame, "Yikes! You got hit by enemy bomb.", "Mission failed!!"+String.valueOf(spaceShip.get_numLives()));
		            aboutDialog.setVisible(true);
		            
					System.exit(0);
				}	
				
			}
			
			repaint();
		}

		
		/*
		 * Detect collision between enemy bombs to spaceship, and spaceship missile to enemy
		 */
		private boolean detectCollision( SpaceObject en, int _ex, int _mx, int _ey, int _my) {
			
			if(((en !=null && Math.abs((_ex+SCN_OBJ_PIX_ADJ_FACTOR)-_mx)<SCN_OBJ_PIX_ADJ_FACTOR) && (Math.abs((_ey+SCN_OBJ_PIX_ADJ_FACTOR)-_my)<SCN_OBJ_PIX_ADJ_FACTOR)))
			 return true;
			
			return false;
		}
		
		/*
		 * Draws bomb/missile on screen oval shape red/green
		 */
		private void drawMissileTrajectory( Graphics g, Color c, int x1, int y1) {

			g.setColor(c);
			g.drawOval(x1, y1, MISSILE_PIX_SIZE, MISSILE_PIX_SIZE);
			
		}
		
		
		public void paintComponent(Graphics gr)
		{	

			int scorex = 0;
			
			if (score == 0) {
				scorex = 350;
			}	
			else if (score >=250 && score<1000) {
				scorex = 365;
			}	
			else {
				scorex = 350;
			}	
			
			String zcore = (String.valueOf(score));
				
			super.paintComponent(gr);
			
			// color the background
			gr.setColor(Color.black);	
			
			// show objects on screen
			warpZone.draw(gr);
			spaceShip.draw(gr);
			
			//insert code to blink spaceship when hit by bomb
			/*
			  if (hitByBomb) {
				gr.drawImage(blankSpaceShip.get_image(), spaceShip.get_xPos(), spaceShip.get_yPos(), Color.black, this);
				gr.drawImage(spaceShip.get_image(), spaceShip.get_xPos(), spaceShip.get_yPos(), Color.black, this);
				// not working currently
			}
			*/
			hitByBomb = false;
			
			gr.setFont(new Font("Lucida Bright Regular", Font.PLAIN, 15));
			gr.setColor(Color.white);
			gr.drawString("Score", 350, 450);
			gr.drawString(zcore, scorex, 470);
			gr.drawString("Lives", 450, 450);
			gr.drawString(String.valueOf(spaceShip.get_numLives()), scorex+100, 470);
			
			 // draw the missile
			if (cur_missileYPos!=-1) 
				{
					drawMissileTrajectory( gr, Color.green, cur_missileXPos, cur_missileYPos);
				}
			
			if ( (cur_bombYPos > 0 ))
			{
				drawMissileTrajectory( gr, Color.red, cur_bombXPos, cur_bombYPos);
			}
			 
			// draw all existing enemies on screen
			for (int i = 0; i<=NO_OF_GAME_ENEMIES-1; i++)
			{
				if(enemies[i]!=null) {
					enemies[i].draw(gr);
				}
			}
			
			// draw ship missile on screen
			if (cur_missileYPos!=-1)
				{
					drawMissileTrajectory( gr, Color.green, cur_missileXPos, cur_missileYPos);
				}

			Random gen = new Random();
			if (count == 10)
			{
				gr.setColor(Color.red);
				shooter = gen.nextInt(NO_OF_GAME_ENEMIES);
				
				// debugger message
				//System.out.println("Count = " + count + " shooter = " + shooter );
				
				// loop into enemy screen positions
				for(int cnt=0;cnt<=NO_OF_GAME_ENEMIES-1;cnt++) {
					
					// only test if shooter value matches counter
					if (shooter == cnt && enemies[cnt]!=null)
					{	
						// get starting bomb location equal from firing enemy location
						cur_bombXPos = enemies[cnt].get_xPos();
						cur_bombYPos = enemies[cnt].get_yPos();

						drawMissileTrajectory( gr, Color.red, cur_bombXPos, cur_bombYPos);
						
						// stop the loop after finding the match
						break;
					}
					
				}
				
			}
			
				// add the code to output the current score
		}
	}


} 
