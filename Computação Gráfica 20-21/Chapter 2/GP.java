package gp;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;

import javax.swing.JFrame;
import javax.swing.JPanel;

import cglib2d.shapes.Arrow;


public class GP extends JFrame {

	public static void main(String[] args) {
		JFrame frame = new GP();
		frame.setTitle("GP");
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
	/*	
		//int x = 100;
		//int y = 100;
		//int w = 200;
		//int h = 50;
		
		// Para a seta ter um feitio diferente
		int x = 50;
		int y = 200;
		int w = 100;
		int h = 100;
		
		float x0 = x+w;
		float y0= y+0.5f *h;
		
		float x1 = x + 0.7f * w;
		float y1 = y + h;
		float x2 = x + 0.7f * w;
		float y2 = y + 0.7f * h;
		
		float x3 = x;
		float y3 = y + 0.7f * h;
		 
		float x4 = x;
		float y4 = y + 0.3f * h;
	
		float x5 = x + 0.7f * w;
		float y5 = y + 0.3f * h;
		
		float x6 = x + 0.7f * w;
		float y6 = y;
		
		GeneralPath path = new GeneralPath();
		
		path.moveTo(x0, y0);
		path.lineTo(x1, y1);
		path.lineTo(x2, y2);
		path.lineTo(x3, y3);
		path.lineTo(x4, y4);
		path.lineTo(x5, y5);
		path.lineTo(x6, y6);
		path.closePath(); // para fechar o ponto final ao ponto inicial
		
		g2.setColor(Color.BLUE);
		g2.fill(path); //apresentar no ecrã o shape criado pelo generalpath tudo da mesma cor
		
		g2.setColor(Color.RED);
		g2.draw(path); // Só os contornos
	*/
	
		Arrow arrow = new Arrow(50, 100, 200, 200);
		g2.setColor(Color.BLUE);
		g2.fill(arrow);
		g2.setColor(Color.RED);
		g2.draw(arrow);
		
		
		}
}
