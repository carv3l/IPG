package circle;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

import cglib2d.utils.W2V;


public class Circle extends JFrame {

	public static void main(String[] args) {
		
		JFrame frame = new Circle();
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
		
		drawCircle1(g2);
	
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
}