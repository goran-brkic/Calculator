package hr.fer.zemris.java.gui.calc;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.model.CalcModel;

/**
 * This class is used to create a JButton that performs a 
 * mathematical operation requiring one operator.
 * @author Goran
 *
 */
public class UnaryOperationButton extends JButton{
	CalcModel model;
	Calculator calc;
	
	public UnaryOperationButton(String operation, CalcModel model, Calculator calc) {
		super(operation);
		this.calc = calc;
		this.model = model;
		super.setFont(super.getFont().deriveFont(15f));
		super.setMargin(new Insets(30, 10, 30, 10));
		
		ActionListener action = a -> {
			JButton b = (JButton)a.getSource();
			double result = 0;
			
			switch(b.getText()) {
			case "1/x":
				result = 1/Double.parseDouble(model.toString());
				break;
			case "sin":
			case "arcsin":
				if(!calc.inverseBox.isSelected()) {
					result = Math.sin(Double.parseDouble(model.toString()));
				} else {
					result = Math.asin(Double.parseDouble(model.toString()));
				}
				break;
			case "log":
			case "10^x":
				if(!calc.inverseBox.isSelected()) {
					result = Math.log10(Double.parseDouble(model.toString()));
				} else {
					result = Math.pow(10, Double.parseDouble(model.toString()));
				}
				break;
			case "cos":
			case "arccos":
				if(!calc.inverseBox.isSelected()) {
					result = Math.cos(Double.parseDouble(model.toString()));
				} else {
					result = Math.acos(Double.parseDouble(model.toString()));
				}
				break;
			case "ln":
			case "e^x":
				if(!calc.inverseBox.isSelected()) {
					result = Math.log(Double.parseDouble(model.toString()));
				} else {
					result = Math.exp(Double.parseDouble(model.toString()));
				}
				break;
			case "tan":
			case "arctan":
				if(!calc.inverseBox.isSelected()) {
					result = Math.tan(Double.parseDouble(model.toString()));
				} else {
					result = Math.atan(Double.parseDouble(model.toString()));
				}
				break;
			case "ctg":
			case "arcctg":
				if(!calc.inverseBox.isSelected()) {
					result = 1.0/Math.tan(Double.parseDouble(model.toString()));
				} else {
					result = Math.PI / 2 - Math.atan(Double.parseDouble(model.toString()));
				}
				break;
			}
			((CalcModelImpl) model).freezeValue(""+result);
			calc.ekran.setText(""+result);
//			model.clearAll();
		};
		
//		setPreferredSize(super.getPreferredSize());

		super.addActionListener(action);
	}
}
