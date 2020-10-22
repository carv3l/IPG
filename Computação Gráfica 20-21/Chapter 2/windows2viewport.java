package windows2viewport;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Panel;

import javax.swing.JFrame;
import javax.swing.JPanel;

import cglib2d.utils.W2V;

public class windows2viewport extends JFrame {


	public static void main(String[] args) {
		
		
		JFrame frame = new windows2viewport();
		frame.setTitle("w2V");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		MyPanel panel = new MyPanel();
		frame.getContentPane().add(panel);
		frame.pack();
		frame.setVisible(true);
		
	}

}

	
	class MyPanel extends JPanel{
		
		int[][] Data = {
				{6824,7479,122},
				{2625,1990,880},
				{6088,7404,266},
				{8790,2522,755},
				{0,0,900}};
		
		int XUmin = -1000;
		int YUmin = -1000;
		int XUmax = 9000;
		int YUmax = 9000;
		
		int XDmin =30;
		int YDmin = 50;
		int XDmax =270;
		int YDmax =300;
		
		public MyPanel() {
			setPreferredSize(new Dimension(400,400));
			setBackground(Color.LIGHT_GRAY);
		}
		
		public void paintComponent(Graphics g) {
			super.paintComponent(g);	

			Graphics2D g2 = (Graphics2D) g;
			
			
			W2V w2v = new W2V(XUmin,YUmin,XUmax,YUmax,XDmin,YDmin,XDmax,YDmax);
			
			g2.setColor(Color.RED);
			g2.drawRect(XDmin, YDmin, XDmax - XDmin, YDmax - YDmin);
			
			g2.setColor(Color.BLUE);
			
			float Fr = 20f/900.0f;
			
			for (int i = 0; i < 5; i++) {
				int x = w2v.MapX(Data[i][0]);
				int y = w2v.MapY(Data[i][1]);
				int r = (int) (Fr * Data[i][2]);
				
				g2.fillOval(x-r, y-r,2*r, 2*r);
				
			}
		
			
			
	}
		
	}



