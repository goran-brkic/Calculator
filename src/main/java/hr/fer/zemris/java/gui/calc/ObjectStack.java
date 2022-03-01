package hr.fer.zemris.java.gui.calc;

import java.util.ArrayList;

/**
 * This class emulates a stack using an ArrayIndexedCollection and delegation.
 * @author Goran
 *
 */
public class ObjectStack {
		private ArrayList<Double> aic;
		
		/**
		 * Constructs a stack using ArrayIndexedCollection.
		 * @param An instance of ArrayIndexedCollection.
		 */
		public ObjectStack(ArrayList<Double> aic) {
			this.aic = aic;
		}
		
		/**
		 * Checks if the stack is empty.
		 * @return True if stack is empty, false otherwise.
		 */
		public boolean isEmpty() {
			return aic.isEmpty();
		}
		
		/**
		 * Returns the size of the stack.
		 * @return Returns the size of the stack.
		 */
		public int size() {
			return aic.size();
		}
		
		/**
		 * Pushes given value on the stack
		 * @param value The value to be pushed onto the stack.
		 */
		public void push(Double value) {
			if(value == null)
				throw new NullPointerException("Null value must not be placed on the stack.");
			aic.add(value);
		}
		
		/**
		 * Removes last value pushed on stack from stack and returns it.
		 * @return Returns the object removed from the top of the stack.
		 * @throws EmptyStackException when the stack is empty.
		 */
		public Object pop() {
			if(isEmpty())
				throw new EmptyStackException("The stack is empty.");
			Object ret = aic.get(aic.size()-1);
			aic.remove(aic.size()-1);
			return ret;
		}
		
		/**
		 * Shows the last item added onto the stack.
		 * @return Returns the last item added onto the stack.
		 */
		public Object peek() {
			if(isEmpty())
				throw new EmptyStackException("The stack is empty.");
			return aic.get(aic.size()-1);
		}
		
		/**
		 * Removes everything from the stack.
		 */
		public void clear() {
			aic.clear();
		}
}
