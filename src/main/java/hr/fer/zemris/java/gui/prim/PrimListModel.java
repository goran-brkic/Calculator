package hr.fer.zemris.java.gui.prim;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

public class PrimListModel implements ListModel<Integer>{
	private List<Integer> elementi;
	private List<ListDataListener> promatraci;
	
	public PrimListModel() {
		elementi = new ArrayList<>();
		elementi.add(1);
		promatraci = new ArrayList<>();
	}
	
	@Override
	public int getSize() {
		return elementi.size();
	}

	@Override
	public Integer getElementAt(int index) {
		return elementi.get(index);
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		promatraci.add(l);
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		promatraci.remove(l);
	}
	
	public void next() {
		int position = elementi.size();
		int i = elementi.get(elementi.size()-1);
		
		do{
			boolean isPrime = true;
			// checks if the number is a prime or not
			i++;
			for(int check = 2; check < i; ++check) {
		        if(i % check == 0) {
		            isPrime = false;
		        }
		    }

		    if(isPrime) {
		       elementi.add(i);
		       break;
		    }
		}while(true);
		
		
		fireIntervalAdded(position, position);
	}
	
	private void fireIntervalAdded(int fromIndex, int toIndex) {
		ListDataEvent ev = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, fromIndex, toIndex);
		for(ListDataListener l : promatraci) {
			l.intervalAdded(ev);
		}
	}


	private void fireIntervalChanged(int fromIndex, int toIndex) {
		ListDataEvent ev = new ListDataEvent(this, ListDataEvent.CONTENTS_CHANGED, fromIndex, toIndex);
		for(ListDataListener l : promatraci) {
			l.contentsChanged(ev);
		}
	}

}
