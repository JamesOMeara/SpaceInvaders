//James O'Meara
//13715519

package States;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import Entitys.Bullet;
import Entitys.Enemy;
import Entitys.GameObject;
import Entitys.Player;
import Main.GamePanel;
import StateManager.GameState;
import StateManager.GameStateManager;

public class PlayState extends GameState {
	

	private ArrayList<Enemy> List;
	private ArrayList<GameObject> Bullets;
	private ArrayList<Bullet> AlienBullets;

	private Enemy spawn;
	private GameObject bulletFire;
	private Bullet alienFire;
	private Player player;
	
	private Image playerImage;
	private Image enemy1;
	private Image enemy2;
	private Image bullet;
	private Image space;
	private Image alienBullet;

	private int score = 0;
	private static int topScore = 0;
	private int bulletsFired = 0;
	private double accuracy = 0; 
	
	private int lives;
	private int level = 1;
	private int fireRate;
	
	boolean newGamestarted = false;
	private int x, y;
	
	
	//constructor, gets the images of each entity and initalises the Game State Manager to this
	public PlayState(GameStateManager gsm){
		this.gsm = gsm;
		playerImage = new ImageIcon("res/player_ship.png").getImage();
		enemy1 = new ImageIcon("res/alien_ship_1.png").getImage();
		enemy2 = new ImageIcon("res/alien_ship_2.png").getImage();
		bullet = new ImageIcon("res/bullet.png").getImage();
		space = new ImageIcon("res/space.png").getImage();
		alienBullet = new ImageIcon("res/AlienBullet.png").getImage();			
	}
	
	
	//create a new game and also reset variables
	public void newGame(){
		//pre-sets for everything
		Bullets = new ArrayList<GameObject>();
		List = new ArrayList<Enemy>();
		AlienBullets = new ArrayList<Bullet>();
		
		fireRate = 70;
		lives = 4;
		score = 0;
		bulletsFired = 0;
		accuracy = 0;
		level = 1;
		x = 20;
		y = 20;
		
		//create the player
		player = new Player(GamePanel.WIDTH /2  , 
				GamePanel.HEIGHT - 40, 
				30,
				playerImage);	
		
		//creat a few enemies to begin with for level 1
		for (int i = 0; i < 2; i++){
			spawn = new Enemy( x , y , 25, enemy1, enemy2, 1);
			List.add(spawn);
			x += 35;
		}
	}
	
	
	
	
	//update this game state, the movememnt of enemies and adding new ones
	private static boolean alienDirectionReversalNeeded = false;
	private int ticks = 0;
	public void update() {
		//==================================================================================
		ticks++;
		//if no game started create a new one
		//this allows you to hit enter and return to the main menu to pause the game
		if (!newGamestarted){
			newGame();
			newGamestarted = true;
		}
		
		//==================================================================================
		//make the aliens go in formation
		for (Enemy obj : List){
			if (obj.move())
				alienDirectionReversalNeeded=true;
		}
		if (alienDirectionReversalNeeded) {
			for (Enemy obj : List){
				obj.reverseDirection();
				alienDirectionReversalNeeded=false;
			}
		}
		//==================================================================================
		//update player
		player.update();
		
		//update the bullets
		for (GameObject obj : Bullets){
			obj.update();		}
		for (Bullet obj : AlienBullets){
			obj.alienBullet();		}
		
		
		//only fire bullets from aliens if there are some aliens
		if(List.size() > 0){
			if(ticks > fireRate){
				int alienNumber = (int)(Math.random()*List.size());
				alienFire = new Bullet(alienBullet, List.get(alienNumber).getX(), List.get(alienNumber).getY()+20  );
				AlienBullets.add(alienFire);
				ticks = 0;
			}
		}
		//==================================================================================
		//test collision for each bullet in the list with the list of enemies
		for (int i = 0; i < Bullets.size(); i++){
			collision(Bullets.get(i));	
		}
		//test each collision with the aliens bullets against the player
		for (int i = 0; i < AlienBullets.size(); i++){
			collisionPlayer(AlienBullets.get(i));
		}
		//==================================================================================
		//function to determine whether you are dead, calculate your points and various other things
		cleanup();
	}

	
	
	public void draw(Graphics g) {
		//draw everything here
	    g.drawImage(space,0, 0, GamePanel.WIDTH, GamePanel.HEIGHT, null);

	    //text for scoring and bullets fired
		Font f = new Font( "Times", Font.PLAIN, 24 );
		g.setFont(f);
		Color c = Color.white;
		g.setColor(c);
		g.drawString("Lives: " + lives, 15, GamePanel.HEIGHT-90);
		g.drawString("Score: " + score + ", " + (int)accuracy + "%", 15, GamePanel.HEIGHT-70);
		g.drawString("Bullets Fired: " + bulletsFired, 15, GamePanel.HEIGHT -50);
		g.drawString("Level: " + level, 10, 25);

	
		player.paint(g);
		//paint the list of game objects
		if(List.size() > 0){
			for (GameObject obj : List){
				obj.paint(g);
			}
		}
		
		
		//reason i have this is, i was getting weird errors, seemed like it was trying to paint when there were no objects there
		//yet i had a check in place to make sure there was something in the bullets list
		//befoer had using the enhanced for loop the game would crash after creating mutiple bullets at a time
		//now it seems to have solved the problem
		int numBullets = Bullets.size();
		if(Bullets.size() > 0){
			for (int i = 0; i < numBullets; i++ ){
				Bullets.get(i).paint(g);
			}
		}
		
		//paint all alien bullets
		for (Bullet bul : AlienBullets){
			bul.paint(g);
		}
	}
	

	//boolean to prevent machine gun fire when holding down the space bar
	private boolean firing = false;
	//provide key Pressed methods for this state
	public void keyPressed(int k) {
		if (k == KeyEvent.VK_ENTER){
			if (score > topScore){
				topScore = score;
			}
			//chaneg state
			gsm.setCurrentState(GameStateManager.MENUSTATE);
		}
		if (k == KeyEvent.VK_RIGHT){
			player.right();		}
		if (k == KeyEvent.VK_LEFT){
			player.left();		}
		if (k == KeyEvent.VK_SPACE){
			if(! firing){
				bulletFire = new Bullet(bullet, player.getX() + 10, GamePanel.HEIGHT - 50);
				Bullets.add(bulletFire);
				bulletsFired++;
				firing = true;
			}	
		}
	}
	
	
	//provide the key released methods for this state
	public void keyReleased(int k) {
		if (k == KeyEvent.VK_RIGHT){
			player.stop();		}
		if (k == KeyEvent.VK_LEFT){
			player.stop();		}
		if (k == KeyEvent.VK_SPACE){
			firing = false;		}
	}
	
	
	
	//detect if the players bullets have hit the aliens
	public boolean collision(GameObject bullet){
		for (int i = 0; i < List.size(); i++){
			if( bullet.getBounds().intersects(List.get(i).getBounds())){
				//if bullet hit alien remove both, increase score
				Bullets.remove(bullet);
				List.remove(i);
				Enemy.amount--;
				score++;
				if(List.size() == 0){
					nextLevel();
				}
				return true;
			}
		}
		return false;
	}
	
	//detect if the aliens have shot the player
	public boolean collisionPlayer(Bullet Alienbullet){
		for (int i = 0; i < List.size(); i++){
			if( Alienbullet.getBounds().intersects(player.getBounds())){
				AlienBullets.remove(Alienbullet);
				lives--;
				//if ran out of lives end the current game
				if(lives <= 0){
					endGame();
				}
				return true;
			}
		}
		return false;
	}


	//for bullets and enemies that go offscreen remove them from the list
	public void cleanup(){
		//end game if aliens have reached the bottom of the screen == dead
		for (int i = 0; i< List.size(); i++){
			if (List.get(i).getY() > (GamePanel.HEIGHT - 50)){
				endGame();
			}
		}
		//remove bullets if out of screen
		for (int i = 0; i< Bullets.size(); i++){
			if (Bullets.get(i).getY() < 0){
				Bullets.remove(i);
			}
		}
		//if new high score set high score
		if (score > 0){
			accuracy = (double)score / (double)bulletsFired * 100;
		}
	}
	
	
	//check to see if we can end the game
	public void endGame(){
		//reset the game so when you hit eneter on menu after dying you start a new game
		newGamestarted = false;
		System.out.println("Died" + List.size());
		//set the top score
		if (score > topScore){
			topScore = score;
		}
		//set current state to dead
		gsm.setCurrentState(GameStateManager.DEADSTATE);
	}
	
	
	//levels are determined here
	public void nextLevel(){
		//increase level 
		//and reset various variables
		level++;
		fireRate -= 10;
		
		//if firerate if less taht every 10 ticks keep it here
		if (fireRate <10){
			fireRate = 10;}
		
		//create the levels and the combinations of aliens here
		switch (level){
			case 2 :
				newLevel(20, 20, 2, 5, 1);
				break;
			case 3 :
				newLevel(20, 20, 4, 7, 1);
				break;
			case 4 :
				newLevel(20, 20, 4, 4, 2);
				break;
			case 5 :
				newLevel(50, 20, 4, 6, 2);
				break;
			case 6 :
				newLevel(200, 20, 7, 5, 2);
				break;
			case 7 :
				newLevel(80, 20, 6, 7, 2);
				break;
			case 8 :
				newLevel(50, 20, 5, 10, 3);
				break;
		}
	}
	
	
	//creates new level new array of aliens based on input parameters
	//the number of aliens and the max horizontal number arrangement
	public void newLevel(int x, int y, int maxX, int number, int speed){
		//spawn a gri of enemies based on the specified cordinates and length and height of grid
		for (int i = 0; i < number; i++){
			for(int k = 0; k< maxX; k++){
				spawn = new Enemy( x , y , 25, enemy1, enemy2, speed);
				List.add(spawn);
				x += 35;
			}
			//begin a new row
			y+=35;
			x -= 35 * maxX;
			
			//remove the player bullets after each wave
			//as so you start fresh 
			int p = Bullets.size();
			for(int j = 0; j < p; j++){
				Bullets.remove(i);
			}
		}
	}
	
	
	//return top score to be used in menustate
	public static int getTopScore(){
		return topScore;
	}
	
	
}
