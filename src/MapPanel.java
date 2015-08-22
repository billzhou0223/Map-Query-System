import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.FlowLayout;

import javax.swing.BoxLayout;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class MapPanel extends JPanel{
	private Image img;

	public MapPanel() {
		try {
			img = ImageIO.read(new File("map.JPG"));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(img, 0, 0, this);
	}
	
	public void drawPolygon(double[] coodinate, Color c){
		Graphics g = this.getGraphics();
		g.setColor(c);
		
		int num = coodinate.length/2;
		int[] x = new int[num];
		int[] y = new int[num];
		for(int i=0; i<num; i++){
			x[i] = (int)coodinate[2*i];
			y[i] = (int)coodinate[2*i+1];
		}
		g.drawPolygon(x, y, num);
	}
	
	public void drawSquare(double[] coodinate, Color c){
		Graphics g = this.getGraphics();
		g.setColor(c);
		
		int x = (int)coodinate[0];
		int y = (int)coodinate[1];
		g.drawRect(x-2, y-2, 5, 5);
	}
	
	public void drawCircle(double[] coodinate, int r, Color c){
		Graphics g = this.getGraphics();
		g.setColor(c);
		
		int x = (int)coodinate[0];
		int y = (int)coodinate[1];
		g.drawOval(x-r, y-r, 2*r, 2*r);
	}
	
	public void drawCircle(double[] coodinate, Color c){
		Graphics g = this.getGraphics();
		g.setColor(c);
		
		int x = (int)coodinate[0];
		int y = (int)coodinate[1];
		g.drawOval(x-3, y-3, 6, 6);
	}
	
	public void drawSegment(String str, Color c){
		Graphics g = this.getGraphics();
		g.setColor(c);
		
		String[] coodinate = str.split(",");
		int x = Integer.parseInt(coodinate[0]);
		int y = Integer.parseInt(coodinate[1]);
		int a = Integer.parseInt(coodinate[2]);
		int b = Integer.parseInt(coodinate[3]);
		g.drawLine(x, y, a, b);
	}
}
