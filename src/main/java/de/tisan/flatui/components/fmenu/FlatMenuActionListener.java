package de.tisan.flatui.components.fmenu;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * ActionListener for the FlatMenu items.
 * 
 * @author TiSan
 * 
 */
public abstract class FlatMenuActionListener implements MouseListener {

	@Override
	public void mouseClicked(MouseEvent arg0){}

	@Override
	public void mouseEntered(MouseEvent arg0){}

	@Override
	public void mouseExited(MouseEvent arg0){}

	@Override
	public void mousePressed(MouseEvent arg0){}

	@Override
	public abstract void mouseReleased(MouseEvent arg0);

}
