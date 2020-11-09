package testheart;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D.Double;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.sun.jdi.connect.Connector.SelectedArgument;

import cglib2d.shapes.Heart;


public class TestHeart extends JFrame {

	public static void main(String[] args) {
		JFrame frame = new TestHeart();
		frame.setTitle("Ex3_4");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new MyPanel();
		frame.getContentPane().add(panel);
		frame.pack();
		frame.setVisible(true);

	}
	
	
	
}
class MyPanel extends JPanel implements MouseListener, MouseMotionListener, KeyListener {
	
	Shape heart = new Heart(10, 100, 100, 100);
	Shape target = new Ellipse2D.Double(200-50,200-50,100,100);

	int firstX =0;
	int firstY = 0;
	
	int deltaX = 0;
	int deltaY = 0;
	boolean selected = false;
	boolean colision = false;
	
	AffineTransform at = new AffineTransform();
	
	public MyPanel() {
		setPreferredSize(new Dimension(400, 400));
		setBackground(Color.WHITE);
		
		this.setFocusable(true);
		addMouseListener(this);
		addMouseMotionListener(this);
		addKeyListener(this);
		
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		if (colision)
			g2.setColor(Color.RED);
			else
			g2.setColor(Color.BLUE);
			
			
		
			g2.fill(target);
			g2.draw(target.getBounds());
			
			int x = (int) target.getBounds().getCenterX();
			int y = (int) target.getBounds().getCenterY();
			g2.setColor(Color.GREEN);
			g2.drawLine(x, y,x,y);
		
		if (colision)
		g2.setColor(Color.RED);
		else		
		g2.setColor(Color.GREEN);
		
		g2.fill(heart);
		g2.draw(heart.getBounds());
		
		
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (selected) {
			deltaX = e.getX() - firstX;
			deltaY = e.getY() - firstY;
			
			at.setToTranslation(deltaX, deltaY);
			heart = at.createTransformedShape(heart);
			
			firstX += deltaX;
			firstY += deltaY;
			
			/*
			if(heart.intersects(target.getBounds())) 
				colision = true;
				
				else 
					colision = false;
				
			*/
			if (heart.contains(target.getBounds().getCenterX(),target.getBounds().getCenterY()))
				colision = true;
			else
				colision = false;
			
			
			repaint();
		}
		
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
	
		if (heart.contains(e.getX(),e.getY())) {
			
			firstX = e.getX();
			firstY = e.getY();
			selected = true;
			
		}else {
			selected = false;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_LEFT)
			deltaX = -5;
		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
			deltaX = 5;
		if(e.getKeyCode() == KeyEvent.VK_UP)
			deltaY = -5;
		if(e.getKeyCode() == KeyEvent.VK_DOWN)
			deltaY = 5;
		
		at.setToTranslation(deltaX, deltaY);
		heart = at.createTransformedShape(heart);
		
		firstX += deltaX;
		firstY += deltaY;
		deltaX =0;
		deltaY=0;
		
	
		if (heart.contains(target.getBounds().getCenterX(),target.getBounds().getCenterY()))
			colision = true;
		else
			colision = false;
		
		
		repaint();
	}	

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
