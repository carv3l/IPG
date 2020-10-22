package cag;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

import javax.swing.JFrame;
import javax.swing.JPanel;



public class CAG extends JFrame {

	public static void main(String[] args) {
		JFrame frame = new CAG();
		frame.setTitle("CAG");
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
		
		
		//g2.translate(200, 200); //Para mudar de posicao
		
		
		Shape s1 = new Ellipse2D.Double(0,0,100,100); //Desenhar um circulo
		Shape s2 = new Ellipse2D.Double(60,0,100,100); // Desenhar um circulo, ligeiramente ao lado

		g2.translate(20, 50); //Para mudar de posicao
		g2.draw(s1);
		g2.draw(s2);
		
		//Criação de Areas
		Area a1 = new Area(s1);
		Area a2 = new Area(s2);
		//Adicionar o objeto a2 ao objeto a1
		a1.add(a2);
		
		
		g2.translate(0, 200); //mudar de sitio para nao ficar sobreposto
		g2.fill(a1); // desenhar o a1
		
		
		
		//Operação Intersectar
		a1 = new Area(s1);
		a1.intersect(a2);
		g2.translate(180,0);
		g2.fill(a1);
		
		//Operacao Subtrair
		a1 = new Area(s1);
		a1.subtract(a2);
		g2.translate(180,0);
		g2.fill(a1);
		
		//Operacao Exclusive OR
		a1 = new Area(s1);
		a1.exclusiveOr(a2);
		g2.translate(180, 0);
		g2.fill(a1);
		}
}
