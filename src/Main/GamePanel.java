//James O'Meara
//13715519

package Main;

import javax.swing.JPanel;
import StateManager.GameStateManager;

import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
 
public class GamePanel extends JPanel implements Runnable, KeyListener {
       
	private static final long serialVersionUID = 1L;
		public static final int WIDTH = 800;
        public static final int HEIGHT = 600;
       
        private Thread thread;

        private BufferedImage image;
        private Graphics g;
        
        //variabel to see if the game is running or not,
        private boolean running;
        
        private GameStateManager gsm;
       
        public GamePanel() {
                super();
                
                setPreferredSize(new Dimension(WIDTH, HEIGHT));
                setFocusable(true);
                requestFocus();
        }
       
        public void addNotify() {
                super.addNotify();
                if(thread == null) {
                        addKeyListener(this);
                        //start thread
                        thread = new Thread(this);
                        thread.start();
                }
                
        }
       
        // initializes variables
        private void init() {
               //initalize the buffered image to get a smooth image
                image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
                g = image.getGraphics();
               
                running = true;
                
                //create the game state manager
                //will automatically select main menu 
                gsm = new GameStateManager();
               
        }
       
        // the "main" function
        public void run() {
               
        	//initalise the thread
        	init();
        	
        	//caculate how long the program should wait, if it executes to quickly
        	int FPS = 60;
        	int targetTime = 1000 / FPS;

        	long start;
        	long elapsed;
        	long wait;

        	// simple game loop
        	while(running) {
        		start = System.nanoTime();
                       
        		update();
        		draw();
        		drawToScreen();
        		
        		elapsed = (System.nanoTime() - start) / 1000000;
        		
        		wait = targetTime - elapsed;
        		if(wait < 0) wait = 1;
        			try {
        				Thread.sleep(wait);
	                    }catch(Exception e) {	e.printStackTrace();	}
        			}
        	}
       
        // updates the game
        private void update() {
        	gsm.update();
        }
       
        // draws the game onto an off-screen buffered image
        private void draw() {
        	gsm.draw(g);
        }
       
        // draws the off-screen buffered image to the screen
        private void drawToScreen() {
                Graphics g = getGraphics();
                g.drawImage(image, 0, 0, null);
                g.dispose();
        }
       
       //pass key input down to selected states
        public void keyPressed(KeyEvent k) {
        	gsm.keyPressed(k.getKeyCode());
        }
        public void keyReleased(KeyEvent k) {
        	gsm.keyReleased(k.getKeyCode());
        }
        public void keyTyped(KeyEvent k) {
               //gsm.keyTyped(k.getKeyCode());
        }
    
}