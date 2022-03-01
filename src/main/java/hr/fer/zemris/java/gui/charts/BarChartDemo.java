package hr.fer.zemris.java.gui.charts;

import java.awt.BorderLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * This class renders a Swing application demonstrating
 * the BarChartComponent component.
 * @author Goran
 *
 */
public class BarChartDemo extends JFrame{
	
	public BarChartDemo(String[] args) {
		super();
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("BarChartDemo");
		setLocation(20, 20);
		setSize(600, 600);
		initGUI(args);
		
	}
	
	private void initGUI(String[] args) {
		getContentPane().setLayout(new BorderLayout());
		JLabel pathToFile = new JLabel(args[0], SwingConstants.CENTER);
		getContentPane().add(pathToFile, BorderLayout.PAGE_START);
		ArrayList<XYValue> values = new ArrayList<>();
		File f = new File(args[0]);
		try {
			String xAxis = "", yAxis = "";
			int yMin = 0, yMax = 0, spacing = 0;
			Scanner sc = new Scanner(f);
			for(int i=1; i<=6; i++) {
				if(!sc.hasNextLine())
					throw new IllegalArgumentException("Invalid number of arguments in provided file.");
				String line = sc.nextLine();
				switch(i) {
				case 1: 
					xAxis += line;
					break;
				case 2:
					yAxis += line;
					break;
				case 3:
					String[] entries = line.split(" ");
					for(String entry : entries) {
						values.add(new XYValue(Integer.parseInt(entry.split(",")[0]), Integer.parseInt(entry.split(",")[1])));
					}
					break;
				case 4:
					yMin = Integer.parseInt(line);
					break;
				case 5:
					yMax = Integer.parseInt(line);
					break;
				case 6:
					spacing = Integer.parseInt(line);
				}
			}
			sc.close();
			
			BarChart chart = new BarChart(values, xAxis, yAxis, yMin, yMax, spacing);
			BarChartComponent barchart = new BarChartComponent(chart);
			barchart.setOpaque(true);
			getContentPane().add(barchart);
		} catch (FileNotFoundException e) {
			System.out.println("Error while reading from file.");
			return;
		}
	}

	
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				BarChartDemo prozor = new BarChartDemo(args);
				prozor.setVisible(true);
			}
		});
	}
}
