package hr.fer.zemris.java.gui.calc;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.model.CalcModel;

/**
 * This class is used to create a JButton that performs a 
 * mathematical operation requiring two operators.
 * @author Goran
 *
 */
public class BinaryOperatorButton extends JButton{
	CalcModel model;
	Calculator calc;
	
	
	public BinaryOperatorButton(String operator, CalcModel model, Calculator calc) {
		super(operator);
		this.calc = calc;
		this.model = model;
		super.setFont(super.getFont().deriveFont(15f));
//		super.setMargin(new Insets(10, 10, 10, 10));
		
		/**
		 * Defining a specific action based on the text on the button which was clicked.
		 */
		ActionListener action = a -> {
			JButton b = (JButton)a.getSource();
			double result;
			
			switch(b.getText()) {
			case "+":
				if(((CalcModelImpl) model).isActiveOperandSet() && model.getValue() != 0) {
					calc.calculateBecauseAnOperatorWasClicked = true;
					calc.binaryOperatorButtons[0].doClick();
				}
				calc.calculateBecauseAnOperatorWasClicked = false;
				model.setPendingBinaryOperation((x, y) -> { return x + y; });
//				model.setActiveOperand(model.getValue());
				model.setActiveOperand(Double.parseDouble(model.toString()));
				((CalcModelImpl) model).freezeValue(model.toString());
				model.clear();
				break;
			case "-":
				if(((CalcModelImpl) model).isActiveOperandSet() && model.getValue() != 0) {
					calc.calculateBecauseAnOperatorWasClicked = true;
					calc.binaryOperatorButtons[0].doClick();
				}
				calc.calculateBecauseAnOperatorWasClicked = false;
				model.setPendingBinaryOperation((x, y) -> { return x - y; });
				model.setActiveOperand(Double.parseDouble(model.toString()));
				((CalcModelImpl) model).freezeValue(model.toString());
				model.clear();
				break;
			case "/":
				if(((CalcModelImpl) model).isActiveOperandSet() && model.getValue() != 0) {
					calc.calculateBecauseAnOperatorWasClicked = true;
					calc.binaryOperatorButtons[0].doClick();
				}
				calc.calculateBecauseAnOperatorWasClicked = false;
				model.setPendingBinaryOperation((x, y) -> { return x / y; });
//				model.setActiveOperand(model.getValue());
				model.setActiveOperand(Double.parseDouble(model.toString()));
				((CalcModelImpl) model).freezeValue(model.toString());
				model.clear();
				break;
			case "*":
				if(((CalcModelImpl) model).isActiveOperandSet() && model.getValue() != 0) {
					calc.calculateBecauseAnOperatorWasClicked = true;
					calc.binaryOperatorButtons[0].doClick();
				}
				calc.calculateBecauseAnOperatorWasClicked = false;
				model.setPendingBinaryOperation((x, y) -> { return x * y; });
//				model.setActiveOperand(model.getValue());
				model.setActiveOperand(Double.parseDouble(model.toString()));
				((CalcModelImpl) model).freezeValue(model.toString());
				model.clear();
				break;
			case "=":
				if(!model.isActiveOperandSet())
					result = model.getPendingBinaryOperation().applyAsDouble(Double.parseDouble(model.toString()), model.getValue());
				else
					result = model.getPendingBinaryOperation().applyAsDouble(model.getActiveOperand(), Double.parseDouble(model.toString()));
				calc.ekran.setText(""+result);
				((CalcModelImpl) model).freezeValue(""+result);
				if(calc.calculateBecauseAnOperatorWasClicked) {
					model.setValue(result);
//					model.clearActiveOperand();
//					((CalcModelImpl) model).freezeValue(null);
				} else {
					((CalcModelImpl) model).freezeValue(model.toString());
					model.clear();
					model.clearActiveOperand();
					model.setPendingBinaryOperation(null);
				}
				break;
			case "x^n":
			case "x^(1/n)":
				if(!calc.inverseBox.isSelected()) {
					if(((CalcModelImpl) model).isActiveOperandSet() && model.getValue() != 0) {
						calc.calculateBecauseAnOperatorWasClicked = true;
						calc.binaryOperatorButtons[0].doClick();
					}
					model.setPendingBinaryOperation((x, n) -> { return Math.pow(x, n); });
//					model.setActiveOperand(model.getValue());
					model.setActiveOperand(Double.parseDouble(model.toString()));
					((CalcModelImpl) model).freezeValue(model.toString());
					model.clear();
					break;
				} else {
					if(((CalcModelImpl) model).isActiveOperandSet() && model.getValue() != 0) {
						calc.calculateBecauseAnOperatorWasClicked = true;
						calc.binaryOperatorButtons[0].doClick();
					}
					model.setPendingBinaryOperation((x, n) -> { return Math.pow(x, 1.0/n); });
//					model.setActiveOperand(model.getValue());
					model.setActiveOperand(Double.parseDouble(model.toString()));
					((CalcModelImpl) model).freezeValue(model.toString());
					model.clear();
					break;
				}
			}
			
		};
//		setPreferredSize(super.getPreferredSize());
		super.addActionListener(action);
	}
	
//	@Override
//	public void setText(String text) {
//		super.setText(text);
//	}
}
