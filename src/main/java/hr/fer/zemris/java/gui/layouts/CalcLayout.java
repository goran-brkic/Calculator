package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager2;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JComponent;

public class CalcLayout implements LayoutManager2{
	Map<String, JComponent> components = new TreeMap<>();
	int spacing;
	
	public CalcLayout() {
		spacing = 0;
	}
	
	public CalcLayout(int spacing) {
		this.spacing = spacing;
	}
	
	@Override
	public void addLayoutComponent(String name, Component comp) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		JComponent c = (JComponent) comp;
		components.remove(c.getWidth() + "," + c.getHeight());
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		double height = 0;
		double width = 0;
		for(Map.Entry<String, JComponent> e : components.entrySet()) {
			JComponent c = e.getValue();
			if(c == null)
				continue;
			if(c.getPreferredSize().height > height)
				height = c.getPreferredSize().height;
			if(!e.getKey().equals("1,1") && c.getPreferredSize().width > width)
				width = c.getPreferredSize().width;
		}
		return new Dimension((r(width)+spacing)*7-spacing, (r(height)+spacing)*5-spacing);
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		double height = 0;
		double width = 0;
		for(Map.Entry<String, JComponent> e : components.entrySet()) {
			JComponent c = e.getValue();
			if(c == null)
				continue;
			if(c.getMinimumSize().height > height)
				height = c.getMinimumSize().height;
			if(!e.getKey().equals("1,1") && c.getMinimumSize().width > width)
				width = c.getMinimumSize().width;
		}
		return new Dimension((r(width)+spacing)*7-spacing, (r(height)+spacing)*5-spacing);
	}

	@Override
	public void layoutContainer(Container parent) {
		int w = r(((double)parent.getWidth()) / 7.0) - spacing + 1;	// width one block takes up
		int h = r(((double)parent.getHeight()) / 5.0) - spacing + 1;	// height one block takes up
//		if(w > h)
//			h = w;
//		else
//			w = h;
		JComponent a = components.get("1,1");
//		w = (a.getPreferredSize().width - 4*spacing)/5;
//		h = w;
		// first row
		if(a != null)
			a.setBounds(spacing, spacing, (w+spacing)*5-spacing, h);
		a = components.get("1,6");
		if(a != null)
			a.setBounds((w+spacing)*5+spacing, spacing, w, h);
		a = components.get("1,7");
		if(a != null)
			a.setBounds((w+spacing)*6+spacing, spacing, w, h);
		// first row
		
		if((w+2*spacing)*7 > parent.getWidth()) {
			for(int i = 2; i <= 5; i++) {
				for(int j = 1; j <= 7; j++) {
					JComponent c = components.get(i + "," + j);
					if(c != null) {
						if(j%2 == 0)
							c.setBounds((j==1)?spacing:((j-1)*w+j*spacing), (i-1)*h+i*spacing, w-1, h);
						else
							c.setBounds((j==1)?spacing:((j-1)*w+j*spacing), (i-1)*h+i*spacing, w, h);
					}
				}
			}
		} else {
			for(int i = 2; i <= 5; i++) {
				for(int j = 1; j <= 7; j++) {
					JComponent c = components.get(i + "," + j);
					if(c != null)
						c.setBounds((j==1)?spacing:((j-1)*w)+j*spacing, (i-1)*h+i*spacing, w, h);
				}
			}
		}
		
	}

	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		if(comp == null || constraints == null)
			throw new NullPointerException();
		if(constraints.getClass() != RCPosition.class && constraints.getClass() != String.class)
			throw new IllegalArgumentException();
		RCPosition position = null;
		if(constraints.getClass() == RCPosition.class)
			position = (RCPosition) constraints;
		else if(constraints.getClass() == String.class)
			position = RCPosition.parse((String) constraints);
		if(position == null)
			throw new IllegalArgumentException("Couldn't parse " + (String) constraints);
		int row = position.getRow();
		int column = position.getColumn();
		if(row<1 || row>5 || column<1 || column>7 || (row == 1 && (column>1 && column<6))) {
			throw new CalcLayoutException();
		}
		if(components.containsKey(position.getRow() + "," + position.getColumn()))
			throw new CalcLayoutException("This position is already occupied");
		
		components.put(position.getRow() + "," + position.getColumn(), (JComponent) comp);
	}

	@Override
	public Dimension maximumLayoutSize(Container target) {
		double height = 0;
		double width = 0;
		for(Map.Entry<String, JComponent> e : components.entrySet()) {
			JComponent c = e.getValue();
			if(c.getMaximumSize().height > height)
				height = c.getMaximumSize().height;
			if(!e.getKey().equals("1,1") && c.getMaximumSize().width > width)
				width = c.getMaximumSize().width;
		}
		return new Dimension((r(width)+spacing)*7-spacing, (r(height)+spacing)*5-spacing);
	}

	@Override
	public float getLayoutAlignmentX(Container target) {
		return 0;
	}

	@Override
	public float getLayoutAlignmentY(Container target) {
		return 0;
	}

	@Override
	public void invalidateLayout(Container target) {
//		throw new UnsupportedOperationException();
	}

	private static int r(double d) {
		return (int) (d+0.5);
	}
}
