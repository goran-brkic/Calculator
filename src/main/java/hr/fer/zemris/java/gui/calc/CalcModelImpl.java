package hr.fer.zemris.java.gui.calc;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleBinaryOperator;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalcValueListener;
import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;

/**
 * This  class represents a specific implementation of a calculator
 * defined with the CalcModel interace.
 * @author Goran
 *
 */
public class CalcModelImpl implements CalcModel{
	List<CalcValueListener> observers = new ArrayList<>();
	boolean editable = true;
	boolean positive = true;
	String entry = "";
	double entryValue = 0;
	String frozenValue = null;
	double activeOperand = -1;
	DoubleBinaryOperator pendingOperation;

	@Override
	public void addCalcValueListener(CalcValueListener l) {
		observers.add(l);
	}

	@Override
	public void removeCalcValueListener(CalcValueListener l) {
		observers.remove(l);
	}
	
	private void notifyObservers() {
		for(CalcValueListener observer : observers) {
			observer.valueChanged(this);
		}
	}
	
	@Override
	public double getValue() {
		return entryValue;
	}

	@Override
	public void setValue(double value) {
		entryValue = value;
		entry = "";
		entry += value;
		editable = false;
	}

	@Override
	public boolean isEditable() {
		return editable;
	}

	@Override
	public void clear() {
		entry = "";
		entryValue = 0;
		editable = true;
	}

	@Override
	public void clearAll() {
		positive = true;
		entry = "";
		entryValue = 0;
		frozenValue = null;
		activeOperand = -1;
		pendingOperation = null;
		editable = true;
	}

	@Override
	public void swapSign() throws CalculatorInputException {
		if(!isEditable())
			throw new CalculatorInputException();
		if(positive) {
			entry = "-" + entry;
		} else {
			if(!entry.isBlank())
				entry = entry.substring(1);
		}
		if(!entry.equals("-") && !entry.equals(".") && !entry.isBlank())
			entryValue = Double.parseDouble(entry);
		positive = positive ? false : true;
		frozenValue = null;
		notifyObservers();
	}

	@Override
	public void insertDecimalPoint() throws CalculatorInputException {
		if(!isEditable() || (entry.isBlank() || entry.equals("-") ))
			throw new CalculatorInputException();
		
		if(!entry.contains(".")) {
			if(entryValue == 0)
				entry = "0.";
			else
				entry += ".";
			
			frozenValue = null;
			notifyObservers();
		} else {
			throw new CalculatorInputException();
		}
	}

	@Override
	public void insertDigit(int digit) throws CalculatorInputException, IllegalArgumentException {
		if(!editable || entry.length() == 308)
			throw new CalculatorInputException();
		if(entryValue == 0 && digit == 0) {
			if(!entry.equals("0"))
				entry += "0";
			return;
		}
		if(entry.length() == 2 && entry.charAt(0) == '0' && entry.charAt(1) != '.')
			entry = entry.substring(1);
		entry += digit;
		if(!entry.matches("^[+-]?([0-9]+(\\.[0-9]+)*)$")) {
			entry = entry.substring(0, entry.length()-1);
			throw new CalculatorInputException();
		} else {
			entryValue = Double.parseDouble(entry);
			frozenValue = null;
		}
	}

	@Override
	public boolean isActiveOperandSet() {
		if(activeOperand == -1)
			return false;
		return true;
	}

	@Override
	public double getActiveOperand() throws IllegalStateException {
		if(activeOperand == -1)
			throw new IllegalStateException();
		return activeOperand;
	}

	@Override
	public void setActiveOperand(double activeOperand) {
		this.activeOperand = activeOperand;
	}

	@Override
	public void clearActiveOperand() {
		activeOperand = -1;
	}

	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		return pendingOperation;
	}

	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {
		this.pendingOperation = op;
	}
	
	/**
	 * Sets the frozen value of this model.
	 * @param value
	 */
	public void freezeValue(String value) {
		frozenValue = value;
	}
	
	/**
	 * 
	 * @return true if model has a frozen value set, false otherwise
	 */
	public boolean hasFrozenValue() {
		if(frozenValue != null)
			return true;
		return false;
	}
	
	@Override
	public String toString() {
		if(hasFrozenValue()) {
			return frozenValue;
		} else if(entry.isBlank() || entry.equals("-")){
			if(positive)
				return "0";
			else
				return "-0";
		} else {
			return entry;
		}
	}
}
