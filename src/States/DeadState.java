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

public class DeadState extends GameState {
	
	private Image menu;
	
	//constructor to get the default background image
	public DeadState(GameStateManager gsm){
		this.gsm = gsm;
		menu = new ImageIcon("res/deadBG.jpg").getImage();
	}
	
	
	public void update() {
		//no need to update dead screen
	}

	//draw the background and text
	public void draw(Graphics g) {
		g.drawImage(menu, 0, 0, GamePanel.WIDTH, GamePanel.HEIGHT, null);
		g.drawString("You Died!", 100, 100);
		g.drawString("Press ENTER to return to Menu", 100, 150);
	}

	//override the key pressed functions so that only relevenat keys pressed are appliciple to this state
	public void keyPressed(int k) {
		if (k == KeyEvent.VK_ENTER){
			gsm.setCurrentState(GameStateManager.MENUSTATE);		}
	}
	public void keyReleased(int k) {
	//not required for this state, but also a abstract method	
	}

}
