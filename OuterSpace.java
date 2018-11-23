//(c) A+ Computer Science
//www.apluscompsci.com
//Name -

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class OuterSpace extends JPanel implements KeyListener, Runnable {
	private Ship ship;

	private AlienHorde horde;
	private Bullets shots;

	private boolean[] keys;
	private BufferedImage back;
	
	private int score;
	
	boolean RUNNING;

	public OuterSpace(JFrame par) {
		keys = new boolean[5];
		
		RUNNING = true;

		setBackground(Color.black);

		ship = new Ship(310, 450, 5);

		horde = new AlienHorde(5);

		shots = new Bullets();

		this.addKeyListener(this);
		new Thread(this).start();

		setVisible(true);
	}

	public void update(Graphics window) {

		paint(window);
	}

	public boolean collision() {
		Rectangle2D a = new Rectangle(ship.getX(), ship.getY(), ship.getWidth(), ship.getHeight());

		for (Alien b : horde.getAliens()) {
			if (a.intersects(new Rectangle(b.getX(), b.getY(), b.getWidth(), b.getHeight()))) {
				return true;
			}
		}
		return false;
	}

	public void paint(Graphics window) {

		// set up the double buffering to make the game animation nice and
		// smooth
		Graphics2D twoDGraph = (Graphics2D) window;

		// take a snap shop of the current screen and same it as an image
		// that is the exact same width and height as the current screen
		if (back == null) {
			StarFighter.HEIGHT = this.getHeight();
			StarFighter.WIDTH = this.getWidth();
			back = (BufferedImage) (createImage(this.getWidth(), this.getHeight()));
		}

		// create a graphics reference to the back ground image
		// we will draw all changes on the ound image
		Graphics graphToBack = back.createGraphics();

		graphToBack.setColor(Color.BLUE);
		graphToBack.drawString("StarFighter ", 25, 50);
		graphToBack.setColor(Color.BLACK);
		graphToBack.fillRect(0, 0, this.getWidth(), this.getHeight());

		if (keys[0] == true) {
			ship.move("LEFT");
		}
		if (keys[1] == true) {
			ship.move("RIGHT");
		}
		if (keys[2] == true) {
			ship.move("UP");
		}
		if (keys[3] == true) {
			ship.move("DOWN");
		}
		if (keys[4] == true) {
			shots.add(new Ammo(ship.getX() + ship.getWidth() / 2 - 5, ship.getY(), 10, 10, 5));
			keys[4] = false;
		}

		
		shots.move();
		shots.draw(graphToBack);

		ship.draw(graphToBack);
		horde.move();
		horde.draw(graphToBack);

		// collision detection
		shots.cleanEmUp();
		score = score + horde.removeDeadOnes(shots.getList());

		twoDGraph.drawImage(back, null, 0, 0);
		back = null;

		if (collision()) {

			window.setColor(Color.RED);
			window.fillRect(0, 0, 800, 600);
			
			RUNNING = false;
			
			Font f = new Font("Dialog", Font.PLAIN, 24);
			window.setFont(f);
			window.setColor(Color.white);
			window.drawString("GAME OVER!", 350, 300);
			
		}

		Font f = new Font("Dialog", Font.PLAIN, 24);
		window.setFont(f);
		window.setColor(Color.white);
		window.drawString("SCORE: "+score, 550, 30);
	}	

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			keys[0] = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			keys[1] = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			keys[2] = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			keys[3] = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			keys[4] = true;
		}		
	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			keys[0] = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			keys[1] = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			keys[2] = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			keys[3] = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			keys[4] = false;
		}
		
	}

	public void keyTyped(KeyEvent e) {

	}

	public void run() {
		try {
			while (RUNNING) {
				Thread.currentThread().sleep(10);
				repaint();
			}
		} catch (Exception e) {
		}
	}
}
