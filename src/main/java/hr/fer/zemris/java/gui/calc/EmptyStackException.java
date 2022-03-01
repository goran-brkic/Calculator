package hr.fer.zemris.java.gui.calc;

/*
 * This class defines an exception for when the stack is empty.
 * @author Goran
 */
public class EmptyStackException extends RuntimeException {

	public EmptyStackException() {}
		
	public EmptyStackException(String message) {
		super(message);
	}

}
