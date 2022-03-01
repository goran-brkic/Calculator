package hr.fer.zemris.java.gui.charts;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class BarChartComponent extends JComponent{
	private static final long serialVersionUID = 1L;
	BarChart chart;
	
	public BarChartComponent(BarChart chart) {
		this.chart = chart;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		int windowWidth = getWidth();
		int windowHeight = getHeight();

		// x-axis name
		FontMetrics fm = g.getFontMetrics();
		int centerOfText = fm.stringWidth(chart.getxAxis())/2;
		int centerOfScreen = windowWidth/2;
		g2d.drawString(chart.getxAxis(), centerOfScreen-centerOfText+25, windowHeight-10);
		
		
		AffineTransform pocetna = g2d.getTransform();
		AffineTransform at = new AffineTransform(pocetna);
		at.rotate(-Math.PI / 2);
		g2d.setTransform(at);
		// y-axis name
		centerOfText = fm.stringWidth(chart.getyAxis())/2;
		centerOfScreen = windowHeight/2;
		g2d.drawString(chart.getyAxis(), -(centerOfScreen+centerOfText-25), 15);
		g2d.setTransform(pocetna);
		
		g2d.setFont(new Font("default", Font.BOLD, 12));
		int xCoordinateOfAxes = 5 + fm.getHeight() + 5 + fm.stringWidth(""+chart.getyMax()) + 5;
		
		// draw y-axis numbers
		int height = windowHeight-xCoordinateOfAxes + 2; // because this is where the y-axis starts, +2 to make it nicer
		int lengthOfYAxis = (windowHeight-xCoordinateOfAxes) - 20;
		int spacingBetweenYAxisNumbers = lengthOfYAxis / (chart.getyMax()-chart.getyMin());
		spacingBetweenYAxisNumbers *=2;
		g2d.setColor(Color.BLACK);
		for(int i=0; i<=chart.getyMax(); i+=chart.getSpacing()) {
			g2d.drawString(""+i, xCoordinateOfAxes-5-fm.stringWidth(""+i), height);
			height -= spacingBetweenYAxisNumbers;
		}
		
		// draw rectangles
		// windowWidth-xCoordinateOfAxes-20-5 , -xCoordinateOfAxes because the y axis starts at xCoordinateOfAxes, -20 because the arrow takes up 20 pixels, -6 to separate from the arrow
		int width = (windowWidth-xCoordinateOfAxes-20-6) / chart.getValues().size();
		int xAxisPosition = xCoordinateOfAxes;
		for(XYValue v : chart.getValues()) {
			height = v.getY()*(spacingBetweenYAxisNumbers/2);
			Rectangle r = new Rectangle(xAxisPosition+2, (windowHeight-xCoordinateOfAxes)-height-1, width, height);
			// x value under each rectangle
			g2d.setColor(Color.BLACK);
			g2d.drawString(""+v.getX(), xAxisPosition+width/2, windowHeight-(xCoordinateOfAxes-15));
			xAxisPosition += width+1;
			g2d.setColor(Color.ORANGE);
			g2d.fillRect(r.x, r.y, r.width, r.height);
		}
		
		// x-axis and y-axis lines
		g2d.setColor(Color.BLACK);
		
		g2d.drawLine(xCoordinateOfAxes, windowHeight-xCoordinateOfAxes, windowWidth-20, windowHeight-xCoordinateOfAxes);
		g2d.drawLine(xCoordinateOfAxes, windowHeight-xCoordinateOfAxes, xCoordinateOfAxes, 20);
		g2d.fillPolygon(new int[] {xCoordinateOfAxes-5, xCoordinateOfAxes, xCoordinateOfAxes+5}, new int[] {20, 0, 20}, 3);
		
		at = new AffineTransform(pocetna);
		at.rotate(-Math.PI / 2);
		g2d.setTransform(at);
		g2d.fillPolygon(new int[] {-(windowHeight-(xCoordinateOfAxes-5)), -(windowHeight-xCoordinateOfAxes), -(windowHeight-(xCoordinateOfAxes+5))}, new int[] {windowWidth-20, windowWidth, windowWidth-20}, 3);
		g2d.setTransform(pocetna);
	}
}
