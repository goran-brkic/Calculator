package hr.fer.zemris.java.gui.calc;


import java.awt.event.ActionListener;
import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.model.CalcModel;

/**
 * This class is used to create a JButton that enters a 
 * digit in the calculator model.
 * @author Goran
 *
 */
public class NumberButton extends JButton{
	CalcModel model;
	Calculator calc;
	
	public NumberButton(String number, CalcModel model, Calculator calc) {
		super(number);
		this.calc = calc;
		this.model = model;
		super.setFont(super.getFont().deriveFont(30f));
		
		ActionListener action = a -> {
			JButton b = (JButton)a.getSource();

			if(model.toString().equals("0") || model.toString().equals("-0"))
				calc.ekran.setText(b.getText());
			else
				if(!((CalcModelImpl) model).hasFrozenValue()) {
					calc.ekran.setText(model.toString()+b.getText());
				} else
					calc.ekran.setText(b.getText());
			model.insertDigit(Integer.parseInt(number));
		};
		
		super.addActionListener(action);
	}
	
}
