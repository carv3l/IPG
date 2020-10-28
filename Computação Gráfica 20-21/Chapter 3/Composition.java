package composition;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class Composition extends JFrame {

	public static void main(String[] args) {
		JFrame frame = new Composition();
		frame.setTitle("Composition");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		MyPanel panel = new MyPanel();
		frame.getContentPane().add(panel);
		frame.pack();
		frame.setVisible(true);

	}

}
class MyPanel extends JPanel{
	public MyPanel() {
		setPreferredSize(new Dimension(400,400));
		setBackground(Color.WHITE);
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);	
		  Graphics2D g2 = (Graphics2D) g;
		  int w = getWidth();
		  int h = getHeight();
		  drawAxis(g2, Color.RED);
			
		  /*
		  g2.translate(w/2, h/2);
		  g2.rotate(Math.toRadians(30));
		  drawAxis(g2, Color.BLUE);
		  Shape r = new Rectangle2D.Double(-50, -50, 100, 100);
		  g2.fill(r);
		  */
				
		  //The composition of transformations is not commutative  
		  g2.rotate(Math.toRadians(30));
		  g2.translate(w/2, h/2);
		  drawAxis(g2, Color.BLUE);
		  Shape r = new Rectangle2D.Double(-50, -50, 100, 100);
		  g2.fill(r);	
		}

		void drawAxis(Graphics2D g2, Color c) {
		  g2.setColor(c);
		  g2.drawLine(-400, 0, 400, 0);
		  g2.drawLine(0, -400, 0, 400);
		}
	}
	
