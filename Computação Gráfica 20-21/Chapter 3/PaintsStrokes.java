package paintsstrokes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.TexturePaint;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import cglib2d.shapes.Arrow;


public class PaintsStrokes extends JFrame{

	public static void main(String[] args) {
		JFrame frame = new PaintsStrokes();
		frame.setTitle("Paints & Strokes");
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
		
		AffineTransform at = g2.getTransform();
		
		Arrow arrow = new Arrow(100,50,200,200);
		//Arrow arrow = new Arrow(50,25,100,100);
		
		g2.setPaint(new Color(255,255,0)); //Amarelo
		g2.fill(arrow);
		//Contornos a Azul
		g2.setPaint(Color.BLUE);
		g2.draw(arrow);
		
		//Transição de Vermelho para Azul
		GradientPaint gp = new GradientPaint(100, 50+100,Color.RED,100+200,50+100,Color.BLUE);
		g2.setPaint(gp);
		g2.translate(0, 210);
		g2.fill(arrow);
		
		g2.setColor(Color.GREEN);
		g2.drawLine(100,50+100,100+200,50+100);
		
		//Transição das mesmas coisas mas com parametros diferentes
	
		gp = new GradientPaint(100, 50,Color.RED, 100+200,50+200,Color.BLUE);
		g2.setPaint(gp);
		g2.translate(0, 210);
		g2.fill(arrow);
		
		g2.setColor(Color.GREEN);
		g2.drawLine(100,50,100+200,50+200);
		
		gp = new GradientPaint(100+50, 50+100,Color.RED, 100+100,50+100, Color.BLUE);
		g2.setPaint(gp);
		g2.translate(250,0);
		g2.fill(arrow);
		
		
		g2.setColor(Color.GREEN);
		g2.drawLine(100+50,50+100,100+100,50+100);
		
		
		
		//Mostrar uma imagem
		BufferedImage image = null;
		
		URL url = getClass().getClassLoader().getResource("images/Smile.jpg");
		
		try {
			image = ImageIO.read(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		Rectangle2D anchor = new Rectangle2D.Double(0,0,image.getWidth()/4,image.getHeight()/4);
		TexturePaint tp = new TexturePaint(image, anchor);
		
		
		g2.setTransform(at);
		g2.translate(210, 0);
		g2.setPaint(tp);
		g2.fill(arrow);
		g2.setColor(Color.RED);
		g2.draw(arrow);
		
		Stroke s = new BasicStroke(5);
		g2.setStroke(s);
		g2.translate(0, 210);
		g2.setPaint(tp);
		g2.fill(arrow);
		g2.setColor(Color.RED);
		g2.draw(arrow);
		
		float[] dash = {3,3};
		s = new BasicStroke(3,BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL,0,dash,0);
		g2.setStroke(s);
		g2.translate(400,0);
		g2.setPaint(tp);
		g2.fill(arrow);
		g2.setColor(Color.RED);
		g2.draw(arrow);
		
	}
}
