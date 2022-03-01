package hr.fer.zemris.java.gui.layouts;

public class RCPosition {
	private int row = 5;
	private int column = 7;
	
	public RCPosition(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	public static RCPosition parse(String text) {
		int rows, columns;
		try {
			rows = Integer.parseInt(""+text.charAt(0));
			columns = Integer.parseInt(""+text.charAt(2));
			return new RCPosition(rows, columns);
		} catch(Exception ex) {
			return null;
		}
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}
}
