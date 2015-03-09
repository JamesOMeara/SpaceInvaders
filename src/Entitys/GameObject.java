//James O'Meara
//13715519

package Entitys;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.JPanel;

public abstract class GameObject extends JPanel{


	private static final long serialVersionUID = 1L;
	//sharded variables
	protected int x;
	protected int y;
	protected int size;
	protected Image i;
	protected int dx = 0;
	protected int dy = 0;


	public int getY(){
		return this.y;
	}
	public int getX(){
		return this.x;
	}
	
	//abstract class, each game object should have these methods 
	//and can implement the randomcolour method
	public abstract void update();
	public abstract void paint(Graphics g);
	
	
	public Rectangle getBounds(){
		return new Rectangle(x,y,size,size);
	}
	

}
