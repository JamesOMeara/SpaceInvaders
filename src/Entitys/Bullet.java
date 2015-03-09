//James O'Meara
//13715519

package Entitys;

import java.awt.Graphics;
import java.awt.Image;

public class Bullet extends GameObject {


	
	private static final long serialVersionUID = 1L;
	
	//constructor
	public Bullet(Image i, int x, int y){
		this.i = i;
		this.x = x;
		this.y = y;
		this.size = 10;
	}

	//update the bullet to move downwards
	public void update() {
		y-=6;
	}
	//update bullet to move downwards
	public void alienBullet(){
		y+=3;
	}

	//paint method
	public void paint(Graphics g) {
        g.drawImage(i, x, y + dy, size, size, null);

	}


}
