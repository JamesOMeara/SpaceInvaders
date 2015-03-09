//James O'Meara
//13715519

package States;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

import Main.GamePanel;
import StateManager.GameState;
import StateManager.GameStateManager;

public class MenuState extends GameState {

	private Image menu;
	
	//constructor to get the image for the background
	public MenuState(GameStateManager gsm){
		this.gsm = gsm;
		menu = new ImageIcon("res/menuBG.jpg").getImage();
	}
	
	
	public void update() {
		//again nothing to update in this state
	}

	
	//the draw mehtod for this state
	public void draw(Graphics g) {
	    g.drawImage(menu, 0, 0, GamePanel.WIDTH, GamePanel.HEIGHT, null);
		g.drawString("MENU", 100, 100);
		g.drawString("Press eneter to Play!  ", 100, 140);
		g.drawString("Top Score to Beat is:  " + PlayState.getTopScore(), 100, 500);
		g.drawString("Use arrow Keys Left & Right to Move Player ", 100, 530);
		g.drawString("Use space bar to Shoot! ", 100, 550);
		g.drawString("Press enter to pause and return to menu. ", 100, 570);
	}

	
	//specific keyboard input for this state
	public void keyPressed(int k) {
		if (k == KeyEvent.VK_ENTER){
			gsm.setCurrentState(GameStateManager.PLAYSTATE);
		}
		if (k == KeyEvent.VK_ESCAPE){
			System.exit(0);;
		}
	}
	public void keyReleased(int k) {
	//again not needed for this state in particular but still required to be a state class	
	}

}
