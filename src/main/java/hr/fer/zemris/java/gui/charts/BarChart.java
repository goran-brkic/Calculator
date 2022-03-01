package hr.fer.zemris.java.gui.charts;

import java.util.ArrayList;
import java.util.List;

/**
 * A class representing a bar chart and all the values mandatory for drawing it.
 * Constructor takes a list of XYValue objects, x-axis and y-axis name, minimal y on the y-axis, 
 * maximal y on the y-axis and spacing between two y values on the y-axis
 * @author Goran
 *
 */
public class BarChart {
	private ArrayList<XYValue> values;
	private String xAxis, yAxis;
	private int yMin, yMax, spacing;
	
	public BarChart(ArrayList<XYValue> values, String xAxis, String yAxis, int yMin, int yMax, int spacing) {
		if(yMin < 0 || yMax <= yMin)
			throw new IllegalArgumentException();
		this.values = (ArrayList<XYValue>) values;
		for(XYValue v : values) {
			if(v.getY() < yMin)
				throw new IllegalArgumentException("The list contains a value smaller than yMin.");
		}
		if((yMax-yMin)%spacing != 0) {
			spacing = (int) Math.round((yMax-yMin)/spacing + 0.5);
		}
		
		this.xAxis = xAxis;
		this.yAxis = yAxis;
		this.yMin = yMin;
		this.yMax = yMax;
		this.spacing = spacing;
	}
	
	public ArrayList<XYValue> getValues() {
		return values;
	}

	public String getxAxis() {
		return xAxis;
	}

	public String getyAxis() {
		return yAxis;
	}

	public int getyMin() {
		return yMin;
	}

	public int getyMax() {
		return yMax;
	}

	public int getSpacing() {
		return spacing;
	}
	
}
