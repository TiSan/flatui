package de.tisan.flatui.components.fbutton;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Creates a FlatButtonListener with only one useful method.
 * 
 * @author Hendrik
 *
 */
public abstract class FlatButtonListener implements MouseListener {
	/**
	 * Invoked by a mouse click
	 */
	public void mouseClicked(MouseEvent e) {
	}

	/**
	 * Invoked by a mouse entering
	 */
	public void mouseEntered(MouseEvent e) {
	}

	/**
	 * Invoked by a mouse exitting
	 */
	public void mouseExited(MouseEvent e) {
	}

	/**
	 * Invoked by a mouse pressing
	 */
	public void mousePressed(MouseEvent e) {
	}

	/**
	 * Invoked by mouse clicking
	 */
	public abstract void mouseReleased(MouseEvent paramMouseEvent);
}