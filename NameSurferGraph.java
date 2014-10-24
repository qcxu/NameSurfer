/*
 * File: NameSurferGraph.java
 * ---------------------------
 * This class represents the canvas on which the graph of
 * names is drawn. This class is responsible for updating
 * (redrawing) the graphs whenever the list of entries changes
 * or the window is resized.
 */

import acm.graphics.*;
import java.awt.event.*;
import java.util.*;
import java.awt.*;

public class NameSurferGraph extends GCanvas
	implements NameSurferConstants, ComponentListener {

	/**
	 * Creates a new NameSurferGraph object that displays the data.
	 */
	public NameSurferGraph() {
		addComponentListener(this);
		existEntry = new ArrayList <NameSurferEntry>();
		// You fill in the rest //
	}
	
	
	/**
	 * Clears the list of name surfer entries stored inside this class.
	 */
	public void clear() {
		existEntry.clear();;
	}
	
	
	/* Method: addEntry(entry) */
	/**
	 * Adds a new NameSurferEntry to the list of entries on the display.
	 * Note that this method does not actually draw the graph, but
	 * simply stores the entry; the graph is drawn by calling update.
	 */
	public void addEntry(NameSurferEntry entry) {
		if (entry != null) {
			existEntry.add(entry);
		}
	}
	
	
	/**
	 * Updates the display image by deleting all the graphical objects
	 * from the canvas and then reassembling the display according to
	 * the list of entries. Your application must call update after
	 * calling either clear or addEntry; update is also called whenever
	 * the size of the canvas changes.
	 */
	public void update() {
		removeAll();
		backgroundGrid();
		inventoryDisplay();
	}
	
	
	/* Implementation of the ComponentListener interface */
	public void componentHidden(ComponentEvent e) { }
	public void componentMoved(ComponentEvent e) { }
	public void componentResized(ComponentEvent e) { update(); }
	public void componentShown(ComponentEvent e) { }
	
/* Draw background graph margin and grid */
	private void backgroundGrid() {
		marginSize = (double)getHeight()/APPLICATION_HEIGHT*GRAPH_MARGIN_SIZE;
		verticalWidth = (double)getWidth()/(NDECADES-1);
		add(new GLine(0, marginSize, getWidth(), marginSize));
		add(new GLine(0, getHeight()-marginSize, getWidth(), getHeight()-marginSize));
		for (int i = 1; i < NDECADES-1; i++) {
			add(new GLine(verticalWidth*i, 0, verticalWidth*i, getHeight()));
			add(new GLabel(Integer.toString(START_DECADE+(i-1)*10), verticalWidth*(i-1), getHeight()));
		}	
		add(new GLabel(Integer.toString(START_DECADE+(NDECADES-2)*10), verticalWidth*(NDECADES-2), getHeight()));
	}
	
/* Draw the entries displayed on the graph */
	private void inventoryDisplay() {
		if (existEntry != null) {
			Iterator <NameSurferEntry> it = existEntry.iterator();
			double linePoint1x;
			double linePoint1y;
			double linePoint2x;
			double linePoint2y;
			int colorCount = 0;
			String labelRank = null;
			Color color = Color.BLACK;
			while (it.hasNext()) {
				NameSurferEntry nextEntry = it.next();
				// Determine line color
				colorCount = colorCount + 1;
				switch(colorCount % 4) {
				case 0: color = Color.magenta; break;
				case 1: color = Color.black; break;
				case 2: color = Color.red; break;
				case 3: color = Color.blue; break;
				}
				// The point of first decade's rank
				linePoint1x = 0;
				if (nextEntry.getRank(0) == 0) {
					linePoint1y = getHeight() - marginSize;	
					labelRank = "*";
				} else {
					linePoint1y = marginSize + (nextEntry.getRank(0)-1)*(getHeight()-2*marginSize)/MAX_RANK;
					labelRank = Integer.toString(nextEntry.getRank(0));
				}
				GLabel label = new GLabel(nextEntry.getName() + " " + labelRank, linePoint1x+1, linePoint1y-2);
				label.setColor(color);
				label.setFont(new Font("Serif",Font.PLAIN,10));
				add(label);
				// The point of next decade's rank
				for (int i = 1; i < NDECADES; i++) {	
					if (nextEntry.getRank(i) == 0) {
						linePoint2y = getHeight() - marginSize;	
						labelRank = "*";
					} else{
						linePoint2y = marginSize + (nextEntry.getRank(i)-1)*(getHeight()-2*marginSize)/MAX_RANK;
						labelRank = Integer.toString(nextEntry.getRank(i));
					}
					linePoint2x = verticalWidth*i;
					GLine line = new GLine(linePoint1x, linePoint1y, linePoint2x, linePoint2y);
					line.setColor(color);
					add(line);
					GLabel nextLabel = new GLabel(nextEntry.getName() + " " + labelRank, linePoint2x+1, linePoint2y-2);
					nextLabel.setColor(color);
					nextLabel.setFont(new Font("Serif",Font.PLAIN,10));
					add(nextLabel);
					linePoint1x = linePoint2x;
					linePoint1y = linePoint2y;
				}
				
			}
		}
	}
	
/* Instance variables */
	private ArrayList <NameSurferEntry> existEntry;
	private double marginSize;
	private double verticalWidth;
	
}
