//James O'Meara
//13715519

package StateManager;

import java.awt.Graphics;

public abstract class GameState {
	//abstract class to create new states for the game
	protected GameStateManager gsm;

	//each game state must have these methods
	public abstract void update();
	public abstract void draw(Graphics g);
	public abstract void keyPressed(int k);
	public abstract void keyReleased(int k);

}
