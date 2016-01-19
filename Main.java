/**
 * Main class
 *
 * Creates and displays the ship with a background
 * This class includes Arrays and a key listener
 * 
 * @author Rehan Hajee
 **/
 
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.io.*;
import javax.swing.*;
import javax.imageio.*;

public class Main extends JFrame implements KeyListener {
	
	//setting coordinates of the ship from top left going counter-clockwise
	static int shipX[] = {400, 400, 408, 420, 428, 428, 420, 420, 408, 408};
	static int shipY[] = {300, 320, 328, 328, 320, 300, 300, 320, 320, 300};
	
	//place background strategically on the screen
	static int backgroundX = -159;
	static int backgroundY = -1105;
	
	static double averageX = 0.0;
	static double averageY = 0.0;
	
	//angle of rotation for the ship
	static double angle = 0.0;
	
	static DrawPanel pnlGraphics = new DrawPanel();
	
	static BufferedImage backgroundImg = null;
	
	static boolean cheatEnabled = false;
	
	static Color shipColor = Color.RED;
	
	static int mouseX = (int) MouseInfo.getPointerInfo().getLocation().x;
	static int mouseY = (int) MouseInfo.getPointerInfo().getLocation().y;
	
	//setting ship constructor
	public Main() {
		super("Asteroids");
		try {
			backgroundImg = ImageIO.read(new File("img\\Background.jpg"));
		} catch (IOException e) {
			System.out.println("The following problem writing to a file occurred:\n" + e);
		}
		
		pnlGraphics.setPreferredSize(new Dimension(800, 600));
		
		addKeyListener(this);	
		
		add(pnlGraphics);
		
		setVisible(true);
		setResizable(false);
		
		pack();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}//end ship constructor
	
	static class DrawPanel extends JPanel {
		
		public DrawPanel() {
			repaint();
		}
		
		@Override
		public void paintComponent (Graphics g) {
			super.paintComponent(g);
			
			g.drawImage(backgroundImg, backgroundX, backgroundY, null);
			if (PauseMenu.pause){
				mouseX = (int) MouseInfo.getPointerInfo().getLocation().x;			
				mouseY = (int) MouseInfo.getPointerInfo().getLocation().y;
				if (mouseX >= getWidth() - 10)
					mouseX = getWidth() - 10 ;
				if (mouseY >= getHeight() - 10)
					mouseY = getHeight() - 10;
			}
			pnlGraphics.repaint();
			System.out.println("Mouse: "+ mouseX + ", " + mouseY);
			//recalculating averageX and averageY
			averageX = averageY = 0.0;
			for (int i = 0; i < shipX.length;i++) {
				averageX += shipX[i];
				averageY += shipY[i];
			}//end for
			averageX /= 10;
			averageY /= 10;
			
			Graphics2D g2 = (Graphics2D) g;
			//save current rotation setting
			AffineTransform old = g2.getTransform();
			AffineTransform afx = new AffineTransform();
			//rotate shape certain number of degrees around a certain point
			afx.setToRotation(Math.toRadians(angle), averageX, averageY);
			g2.setTransform(afx);
			g.setColor(shipColor);
			g2.fillPolygon(shipX, shipY, 10);
			if (cheatEnabled){
				System.out.println("Mouse: "+ mouseX + ", " + mouseY);
				System.out.println("backgroundX = " + backgroundX + "\nbackgroundY = " + backgroundY);
				System.out.print("\nMainX: ");
				for (int i = 0; i < shipX.length; i++) {
					System.out.print(shipX[i] + " ");
				}
				System.out.print("\nMainY: ");
				for (int i = 0; i < shipY.length; i++) {
					System.out.print(shipY[i] + " ");
				}
				System.out.println("\n");
				System.out.println("Angle = " + angle + " sin (" + (angle) + ") = " + Math.sin(Math.toRadians(angle)));
				System.out.println("Angle = " + angle + " cos (" + (angle) + ") = " + Math.cos(Math.toRadians(angle)));
				System.out.println();
			}
			
			//restore old rotation seting
			g2.setTransform(old);
			// everything after this is not rotated
		}//end paintComponent
	}//end DrawPanel
	public void keyTyped(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {
		
		//if letter p is pressed and released
		if (e.getKeyCode() == KeyEvent.VK_P || e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			PauseMenu.pause = !PauseMenu.pause;
			new PauseMenu();
		}//end if
		
		if (e.getKeyCode() == 192) {
			cheatEnabled = !cheatEnabled;
			pnlGraphics.repaint();
		}
	}//end keyReleased
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()){
		
		case KeyEvent.VK_W:
		case KeyEvent.VK_UP:
		}
		//if arrow up is pressed
		if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
			if (!PauseMenu.pause){
				//if the ship is not at the border
				if (shipX[0] >= 7 && shipX[0] <= 777 && shipY[0] >= 2 && shipY[0] <= 582) {
					for (int i = 0; i < shipX.length; i++) {
						shipX[i] -= Math.cos(Math.toRadians(angle + 90))*10;
						shipY[i] -= Math.sin(Math.toRadians(angle + 90))*10;
					}//end for loop
				
				}//end if
				//if the ship is at the border
				else {
					if (shipX[0] < 7) {
						if (Math.sin(Math.toRadians(angle)) >= 0) {
							for (int i = 0; i < shipX.length; i++) {
								shipX[i] -= Math.cos(Math.toRadians(angle + 90))*10;
								shipY[i] -= Math.sin(Math.toRadians(angle + 90))*10;
							}//end for loop
						}//end if
						
					}//end if
					if (shipX[0] > 777) {
						if (Math.sin(Math.toRadians(angle)) <= 0) {
							for (int i = 0; i < shipX.length; i++) {
								shipX[i] -= Math.cos(Math.toRadians(angle + 90))*10;
								shipY[i] -= Math.sin(Math.toRadians(angle + 90))*10;
							}//end for loop
						}//end if
						
					}//end if
					if (shipY[0] < 2) {
						if (Math.cos(Math.toRadians(angle)) <= 0) {
							for (int i = 0; i < shipX.length; i++) {
								shipX[i] -= Math.cos(Math.toRadians(angle + 90))*10;
								shipY[i] -= Math.sin(Math.toRadians(angle + 90))*10;
							}//end for loop
						}//end if
						
					}//end if
					if (shipY[0] > 582) {
						if (Math.cos(Math.toRadians(angle)) >= 0) {
							for (int i = 0; i < shipX.length; i++) {
								shipX[i] -= Math.cos(Math.toRadians(angle + 90))*10;
								shipY[i] -= Math.sin(Math.toRadians(angle + 90))*10;
							}//end for loop
						}//end if
						
					}//end if
				}//end else
					
				backgroundX += Math.cos(Math.toRadians(angle + 90))*10;
				backgroundY += Math.sin(Math.toRadians(angle + 90))*10;
				
				if (Math.round(backgroundX / 10) * 10 == -30)
					backgroundX = -356;
				else if (Math.round(backgroundX / 10) * 10 == -550)
					backgroundX = -356;
				if (Math.round(backgroundY / 10) * 10 -5 == -395)
					backgroundY = -1105;
				else if (Math.round(backgroundY / 10) * 10 == -1750)
					backgroundY = -340;
			}
			
			
			pnlGraphics.repaint();
		}//end if
		
		
		//if right arrow is pressed
		if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
			if (!PauseMenu.pause)
				angle += 7.5;
			
			pnlGraphics.repaint();
			
		}//end else if
		
		//if left arrow is pressed
		if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			if (!PauseMenu.pause)
				angle -= 7.5;
			
			pnlGraphics.repaint();
		}//end if
		
		
	}//end keyPressed
}//end Main