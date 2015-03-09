//James O'Meara
//13715519

package Entitys;

import java.awt.Graphics;
import java.awt.Image;

import Main.GamePanel;

public class Enemy extends GameObject {

	private static final long serialVersionUID = 1L;
	//Distance is how much pixels the Enemy travels per re paint
	private Image i2;
	public static int amount = 0;


	
	//Customize your Enemy use this constructor when creating object
	public Enemy(int x, int y, int size, Image e, Image e2, int dx){
		this.x=x;
		this.y=y;
		this.size=size;
		this.i = e;
		this.i2 = e2;
		this.dx = dx;
		amount++;
	}

	//determine what is going to be updated in the enemy
	public void update() {

		move();
	}
	
	
	private int ticks = 0;
	public void paint(Graphics g){
		if (ticks < 25){
	        g.drawImage(i, x + dx, y + dy, size, size, null);
		}else{
	        g.drawImage(i2, x + dx, y + dy, size, size, null);
		}
		
		if(ticks >50){
			//reset ticks 
			ticks = 0;
		}
		ticks++;
	}
	
	
	public boolean move() {
		x += dx;
		// direction reversal needed?
		if (x<=0 || x>= GamePanel.WIDTH - i .getWidth(null))
			return true;
		else
		 return false;
		}

	
	public void reverseDirection() {
		dx = - dx;
		y+=25;
		}

	
}
