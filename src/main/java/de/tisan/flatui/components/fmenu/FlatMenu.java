package de.tisan.flatui.components.fmenu;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;

import javax.swing.JPanel;

import de.tisan.flatui.components.fcommons.FlatColors;
import de.tisan.flatui.components.fcommons.FlatComponent;
import de.tisan.flatui.components.fcommons.FlatLayoutManager;
import de.tisan.flatui.components.fcommons.FlatUI;
import de.tisan.flatui.components.ficon.FlatIcon;

/**
 * A flat menu with several menu point options.
 * 
 * @author TiSan
 * 
 */
public class FlatMenu extends FlatComponent {
	private static final long serialVersionUID = 2427745051395965139L;
	private ArrayList<FlatMenuPoint> menuPoints;
	private JPanel pane;

	/**
	 * Creates a new FlatMenu
	 * 
	 * @param pane
	 *            A referent to the contentPane of the JFrame where this menu
	 *            will be shown.
	 */
	public FlatMenu(FlatLayoutManager man) {
		super(man);
		man.addComponent(this);
		if(man.getContentPane() != null)
		this.pane = man.getContentPane();
		this.menuPoints = new ArrayList<FlatMenuPoint>();
		super.disableEffects();
		this.background = FlatColors.MIDNIGHTBLUE;
		//pane.add(this);
	}

	/**
	 * Adds a new menu point to the menu.
	 * 
	 * @param text
	 *            Specific text / label
	 * @param listener
	 *            A listener that you know when the menu point is pressed
	 * @return the index of the new created menu point
	 */
	public FlatMenuPoint addMenuPoint(String text, FlatMenuActionListener listener) {
		FlatMenuPoint point = new FlatMenuPoint(text);
		point.addMouseListener(listener);
		int temp = 10;
		for (int i = 0; i < menuPoints.size(); i++) {
			// temp += menuPoints.get(i).getSize().width + 20;
			temp += menuPoints.get(i).getSize().width;
		}
		this.menuPoints.add(point);
		FontMetrics fm = point.getFontMetrics(point.getFont());
		java.awt.geom.Rectangle2D rect = fm.getStringBounds(point.getText(), point.getGraphics());
		point.setLocation(temp, getLocation().y);
		int textWidth = (int) (rect.getWidth());
		point.setSize(textWidth + 40, getSize().height);
		//
		point.background = this.background;
		pane.add(point);
		
		return point;
	}

	/**
	 * Adds a new menu point to the menu.
	 * 
	 * @param text
	 *            Specific text / label
	 * @param listener
	 *            A listener that you know when the menu point is pressed
	 * @return the index of the new created menu point
	 */
	public FlatMenuPoint addMenuPoint(String text, FlatIcon icon, FlatMenuActionListener listener) {
		FlatMenuPoint point = new FlatMenuPoint(text, icon);
		point.addMouseListener(listener);
		int temp = 0;
		for (int i = 0; i < menuPoints.size(); i++) {
			// temp += menuPoints.get(i).getSize().width + 20;
			temp += menuPoints.get(i).getSize().width;
		}
		this.menuPoints.add(point);
		FontMetrics fm = point.getFontMetrics(point.getFont());
		java.awt.geom.Rectangle2D rect = fm.getStringBounds(point.getText(), point.getGraphics());
		point.setLocation(temp, getLocation().y);
		int textWidth = (int) (rect.getWidth());
		point.setSize(textWidth + 60, getSize().height);
		point.background = this.background;
		pane.add(point);
		return point;
	}

	/**
	 * Returns a menu point.
	 * 
	 * @param text
	 *            An "identifier" of the menu point.
	 * @return The FlatMenuPoint instance
	 */
	public FlatMenuPoint getMenuPoint(String text) {
		for (int i = 0; i < menuPoints.size(); i++) {
			if (menuPoints.get(i).getText().equals(text)) {
				return menuPoints.get(i);
			}
		}
		return null;
	}

	/**
	 * Returns a menu point.
	 * 
	 * @param text
	 *            An exact identifier of the menu point.
	 * @return The FlatMenuPoint instance
	 */
	public FlatMenuPoint getMenuPoint(int index) {
		return menuPoints.get(index);
	}

	protected void removeMenuPoint(String text) {
		for (int i = 0; i < menuPoints.size(); i++) {
			if (menuPoints.get(i).getText().equals(text)) {
				menuPoints.remove(i);
			}
		}
	}

	protected void removeMenuPoint(int index) {
		menuPoints.remove(index);
	}

	/**
	 * Returns the current index of an specific FlatMenuPoint.
	 * 
	 * @param point
	 *            instance of FlatMenuPoint
	 * @return index of this instance in this menu
	 */
	public int getIndex(FlatMenuPoint point) {
		for (int i = 0; i < menuPoints.size(); i++) {
			if (menuPoints.get(i).equals(point)) {
				return i;
			}
		}
		return -1;
	}

	public ArrayList<FlatMenuPoint> getMenuPoints() {
		return menuPoints;
	}

	@Override
	public void paintComponent(Graphics gr) {
		super.paintComponent(gr);
		Graphics2D g = (Graphics2D) gr;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, !FlatUI.isFastRendering() ? RenderingHints.VALUE_ANTIALIAS_ON : RenderingHints.VALUE_ANTIALIAS_OFF);
		g.setColor(background);
		g.fillRect(0, 0, getWidth(), getHeight());
		for (int i = 0; i < menuPoints.size(); i++) {
			FlatMenuPoint point = menuPoints.get(i);
			point.repaint();

		}
	}
}
