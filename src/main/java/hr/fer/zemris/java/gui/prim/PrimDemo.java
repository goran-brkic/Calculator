package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class PrimDemo extends JFrame{
	
	public PrimDemo() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(500,500);
		initGUI();
	}
	
	private void initGUI() {
		Container c = getContentPane();
		c.setLayout(new BorderLayout());
		
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(1,0));
		
		PrimListModel plm = new PrimListModel();
		
		JList<Integer> lista1 = new JList<>(plm);
		JList<Integer> lista2 = new JList<>(plm);
		
		// bez ovoga, selekcije su nezavisne
		lista2.setSelectionModel(lista1.getSelectionModel());
		
		p.add(new JScrollPane(lista1));
		p.add(new JScrollPane(lista2));
		
		c.add(p, BorderLayout.CENTER);
		
		JPanel p2 = new JPanel();
		p2.setLayout(new GridLayout(1,0));
		c.add(p2, BorderLayout.PAGE_END);
		
		JButton b1 = new JButton("sljedeći");
		
		p2.add(b1);
		
		b1.addActionListener(e->plm.next());
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(()->{
			new PrimDemo().setVisible(true);
		});
	}
}
