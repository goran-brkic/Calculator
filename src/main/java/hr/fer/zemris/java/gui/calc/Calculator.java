package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalcValueListener;
import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

/**
 * Creates a Swing application of a calculator using a specific CalcModel implementation.
 * @author Goran
 *
 */
public class Calculator extends JFrame{
	CalcModel model = new CalcModelImpl();
	JLabel ekran;
	NumberButton[] numberButtons;
	BinaryOperatorButton[] binaryOperatorButtons;
	UnaryOperationButton[] unaryOperationButtons;
	JCheckBox inverseBox;
	boolean calculateBecauseAnOperatorWasClicked = false;
	ArrayList<Double> aic = new ArrayList<>();
	ObjectStack stack = new ObjectStack(aic);
	
	public Calculator() {
		super();
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Calculator");
		setLocation(20, 20);
//		setSize(500, 500);
		initGUI();
		pack();
	}
	
	private void initGUI() {
//		Container p = getContentPane();
		
		JPanel p = new JPanel(new CalcLayout(2));
		p.setLayout(new CalcLayout(5));
		
		ekran = new JLabel("0");
		ekran.setBackground(Color.YELLOW);
		ekran.setOpaque(true);
		ekran.setHorizontalAlignment(SwingConstants.RIGHT);
		ekran.setFont(ekran.getFont().deriveFont(30f));
//		ekran.setPreferredSize(new Dimension(108, 15));
		model.addCalcValueListener(new CalcValueListener() {
			@Override
			public void valueChanged(CalcModel model) {
				ekran.setText(model.toString());
			}});
		
		p.add(ekran, "1,1");
		
		unaryOperationButtons = new UnaryOperationButton[7];
		unaryOperationButtons[0] = new UnaryOperationButton("1/x", model, this);
		unaryOperationButtons[1] = new UnaryOperationButton("sin", model, this);
		unaryOperationButtons[2] = new UnaryOperationButton("log", model, this);
		unaryOperationButtons[3] = new UnaryOperationButton("cos", model, this);
		unaryOperationButtons[4] = new UnaryOperationButton("ln", model, this);
		unaryOperationButtons[5] = new UnaryOperationButton("tan", model, this);
		unaryOperationButtons[6] = new UnaryOperationButton("ctg", model, this);
		int i = 2;
		for(int k = 0; k < 7; k++) {
			if(k==6) {
				p.add(unaryOperationButtons[k], new RCPosition(5, 2));
				break;
			}
			p.add(unaryOperationButtons[k], new RCPosition(i, (k%2==0)?1:2));
			if(k%2 == 1) i++;
		}
		
		numberButtons = new NumberButton[10];
		i = 2;
		int j = 5;
		for(int k = 9; k >= 0; k--) {
			numberButtons[k] = new NumberButton("" + k, model, this);
			if(k == 0) {
				p.add(numberButtons[k], new RCPosition(5,3));
				break;
			}
			p.add(numberButtons[k], new RCPosition(i, j));
			j--;
			if(j == 2) {
				i++;
				j = 5;
			}
		}
		
		binaryOperatorButtons = new BinaryOperatorButton[11];
		binaryOperatorButtons[0] = new BinaryOperatorButton("=", model, this);
		binaryOperatorButtons[1] = new BinaryOperatorButton("/", model, this);
		binaryOperatorButtons[2] = new BinaryOperatorButton("*", model, this);
		binaryOperatorButtons[3] = new BinaryOperatorButton("-", model, this);
		binaryOperatorButtons[4] = new BinaryOperatorButton("+", model, this);
		binaryOperatorButtons[5] = new BinaryOperatorButton("x^n", model, this);
		binaryOperatorButtons[5].setMargin(new Insets(10, 30, 10, 30));
		p.add(binaryOperatorButtons[5], new RCPosition(5, 1));
		
		for(int k = 0; k < 5; k++) {
			p.add(binaryOperatorButtons[k], new RCPosition(k+1, 6));
		}
		
		JButton signSwap = new JButton("+/-");
		signSwap.setFont(signSwap.getFont().deriveFont(15f));
		ActionListener signSwapAction = a -> {
			model.swapSign();
		};
		signSwap.addActionListener(signSwapAction);
		p.add(signSwap, new RCPosition(5,4));
		
		JButton decimalPoint = new JButton(".");
		decimalPoint.setFont(decimalPoint.getFont().deriveFont(15f));
		ActionListener decimalPointAction = a -> {
			model.insertDecimalPoint();
		};
		decimalPoint.addActionListener(decimalPointAction);
		p.add(decimalPoint, new RCPosition(5,5));
		
		JButton clear = new JButton("clr");
		ActionListener clearAction = a -> {
			model.clear();
			ekran.setText("0");
		};
		clear.addActionListener(clearAction);
		p.add(clear, new RCPosition(1, 7));
		
		JButton reset = new JButton("reset");
		ActionListener resetAction = a -> {
			model.clearAll();
			ekran.setText("0");
		};
		reset.addActionListener(resetAction);
		p.add(reset, new RCPosition(2, 7));
		
		JButton push = new JButton("push");
		ActionListener pushAction = a -> {
			stack.push(model.getValue());
		};
		push.addActionListener(pushAction);
		p.add(push, new RCPosition(3, 7));
		
		JButton pop = new JButton("pop");
		ActionListener popAction = a -> {
			try{
				model.setValue((Double) stack.pop());
				ekran.setText(""+model.getValue());
			}catch(Exception e) {
				System.out.println("Nothing to pop.");
			}
		};
		pop.addActionListener(popAction);
		p.add(pop, new RCPosition(4,7));
		
		inverseBox = new JCheckBox("Inv");
		ActionListener checkBoxListener = a -> {
			if(((JCheckBox)a.getSource()).isSelected()) {
				unaryOperationButtons[1].setText("arcsin");
				unaryOperationButtons[2].setText("10^x");
				unaryOperationButtons[3].setText("arccos");
				unaryOperationButtons[4].setText("e^x");
				unaryOperationButtons[5].setText("arctan");
				binaryOperatorButtons[5].setText("x^(1/n)");
				binaryOperatorButtons[5].setMargin(new Insets(0, 0, 0, 0));
				unaryOperationButtons[6].setText("arcctg");
				
			} else {
				unaryOperationButtons[1].setText("sin");
				unaryOperationButtons[2].setText("log");
				unaryOperationButtons[3].setText("cos");
				unaryOperationButtons[4].setText("ln");
				unaryOperationButtons[5].setText("tan");
				binaryOperatorButtons[5].setText("x^n");
				
				binaryOperatorButtons[5].setMargin(new Insets(10, 30, 10, 30));
				unaryOperationButtons[6].setText("ctg");
			}
			p.repaint();
		};
		inverseBox.addActionListener(checkBoxListener);
		p.add(inverseBox, new RCPosition(5,7));
		
		
		getContentPane().add(p);
//		getContentPane().setPreferredSize(p.getPreferredSize());
//		setPreferredSize(p.getPreferredSize());
	}

	
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				Calculator calculator = new Calculator();
				calculator.setVisible(true);
			}
		});
	}
	
}
