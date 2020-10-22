package curve;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

import cglib2d.utils.W2V;


public class Curve extends JFrame {

	public static void main(String[] args) {
		
		JFrame frame = new Curve();
		frame.setTitle("");
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
		setBackground(Color.LIGHT_GRAY);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);	

		Graphics2D g2 = (Graphics2D) g;
		
		g2.translate(200, 200); //Para mudar de posicao
		
		//drawCircle1(g2);
		//drawCircle2(g2);
		
		drawCurve(g2);
		}
	
	private void drawCircle1(Graphics2D g2) { //Exemplo 1 de desenhar um circulo, not good!!!
		int r = 100;
		g2.setColor(Color.RED);
		for (int x = -r; x <=r; x++) {
			int y = (int) Math.sqrt(r*r -x*x);
			g2.drawLine(x, y, x, y);
			g2.drawLine(x, -y, x, -y);
		}
		
	}
	
	private void drawCircle2(Graphics2D g2) {//outra abordagem
		int r = 100;
		int points = 80; //quanto mais pontos tiver mais perfeito o circulo fica
		int x1 = (int) (r*Math.cos(0));
		int y1 = (int) (r*Math.sin(0));
		int x2;
		int y2;
		
		double dt = 2 * Math.PI / points;
		
		for (int i = 0; i <= points;i++) {
			double t = i * dt;
			x2 = (int) (r * Math.cos(t));
			y2 = (int) (r * Math.sin(t));
			g2.drawLine(x1, y1, x2, y2);
			x1 = x2;
			y1 = y2;
		}
		
	}
	
	private void drawCurve(Graphics2D g2) {//Isto é diferente, nao tem a haver com o circulo
		
		//x=100 x cos(3 x t)
		//y=100 x sin(2 x t)
		// 0 <= t <= 2Pi
		
		int r = 100;
		int points = 80; //quanto mais pontos tiver mais perfeito o circulo fica
		
		// ** Adaptar **
		double tmin = 0;
		double tmax = 2 * Math.PI;
		
		// ** Adaptar **
		int x1 = (int) (100 * Math.cos(3 * tmin));
		int y1 = (int) (100 * Math.sin(2 * tmin));
		int x2;
		int y2;
		
		double dt = (tmax - tmin)/ points;
		
		for (int i = 0; i <= points;i++) {
			
			double t = i * dt;
			
			// ** Adaptar **
			x2 = (int) (100 * Math.cos(3 * t));
			y2 = (int) (100 * Math.sin(2 * t));
			
			g2.drawLine(x1, y1, x2, y2);
			x1 = x2;
			y1 = y2;
		}
		
	}
	
	
}