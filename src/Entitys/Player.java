//James O'Meara
//13715519

package Entitys;


import java.awt.Graphics;
import java.awt.Image;

import Main.GamePanel;


public class Player extends GameObject {
	

	private static final long serialVersionUID = 1L;
	protected int distance = 8;

	Player(Image p){
		this( GamePanel.WIDTH /2  , 
				GamePanel.HEIGHT - 40, 
				30,
				p);		
	}

	public Player(int x, int y, int size, Image p){
		this.x=x;
		this.y=y;
		this.size=size;
		this. i = p;
	}
	
	
	public void update() {
		x+= super.dx;
		y+= super.dy;
	}

	public void paint(Graphics g) {
       g.drawImage(i, x + dx, y + dy, size, size, null);

	}
	
	public int getX(){
		return this.x;
	}
	public void setX(int n){
		this.x = n;
		
	}

	public void up(){
		dy = -distance;
		dx = 0;
	}
	public void down(){
		dy = distance;
		dx = 0;
	}
	public void left(){
		if (x < 10){
			x = 0;
			stop();
			return;
		}
		dy = 0;
		dx = -distance;
	}
	public void right(){
		if (x > GamePanel.WIDTH - size - 10){
			x = GamePanel.WIDTH - size;
			stop();
			return;
		}
		dy = 0;
		dx = distance;
	}
	public void stop(){
		dx = 0;
		dy = 0;
		
		if (x < 10){
			x = 0;
		} else if (x > GamePanel.WIDTH - size - 10){
			x = GamePanel.WIDTH - size;
			}
	}

}
