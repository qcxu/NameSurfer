/*
 * File: NameSurfer.java
 * ---------------------
 * When it is finished, this program will implements the viewer for
 * the baby-name database described in the assignment handout.
 */

import acm.program.*;
import java.awt.event.*;
import javax.swing.*;

public class NameSurfer extends Program implements NameSurferConstants {

	/* Method: init() */
	/**
	 * This method has the responsibility for reading in the data base
	 * and initializing the interactors at the top of the window.
	 */
	
	
	public void init() {
	    // You fill this in, along with any helper methods //
		
		// Initialize the interactors
		add(new JLabel("Name "), SOUTH);
		tf = new JTextField(20);
		tf.addActionListener(this);
		Graph = new JButton("Graph");
		Clear = new JButton("Clear");
		graph = new NameSurferGraph();
		add(graph);
		add(tf, SOUTH);
		add(Graph, SOUTH);
		add(Clear, SOUTH);
		addActionListeners();
		file = new NameSurferDataBase(NAMES_DATA_FILE);
	}

	/* Method: actionPerformed(e) */
	/**
	 * This class is responsible for detecting when the buttons are
	 * clicked, so you will have to define a method to respond to
	 * button actions.
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == tf | e.getSource() == Graph) {
			graph.addEntry(file.findEntry(tf.getText()));
			graph.update();
		} else if (e.getSource() == Clear) {
			graph.clear();
			graph.update();
		}
		
	}
	
	
	/* instante variables */
	private JTextField tf;
	private JButton Graph;
	private JButton Clear;
	private NameSurferGraph graph;
	private NameSurferDataBase file;
}
