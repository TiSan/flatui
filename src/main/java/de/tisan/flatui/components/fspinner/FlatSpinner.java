package de.tisan.flatui.components.fspinner;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.IllegalComponentStateException;
import java.awt.MouseInfo;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import de.tisan.flatui.components.fcommons.FlatColors;
import de.tisan.flatui.components.fcommons.FlatComponent;
import de.tisan.flatui.components.fcommons.FlatLayoutManager;
import de.tisan.flatui.components.fcommons.FlatUI;
import de.tisan.flatui.components.ffont.FlatFont;

/**
 * A flat slider.
 * 
 * @author TiSan
 * 
 */
public class FlatSpinner extends FlatComponent {
	private static final long serialVersionUID = 2101586280928468248L;
	private ArrayList<String> values;
	private Color foreground;
	private Font font;
	private int index;
	private int arrow;

	/**
	 * Creates a new FlatSlider
	 * 
	 * @param man
	 */
	public FlatSpinner(FlatLayoutManager man) {
		super(man);
		man.addComponent(this);
		if(man.getContentPane() != null)
		man.getContentPane().add(this, 0);
		this.values = new ArrayList<String>();
		this.background = FlatColors.HIGHLIGHTBACKGROUND;
		this.backOrigin = new Color(background.getRed(), background.getGreen(), background.getBlue());
		this.foreground = FlatColors.WHITE;
		this.font = FlatFont.getInstance();
		this.index = 0;
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					try {
						if (MouseInfo.getPointerInfo().getLocation().getX() > FlatSpinner.this.getLocationOnScreen().x + FlatSpinner.this.getSize().width - 20
								&& MouseInfo.getPointerInfo().getLocation().getX() < FlatSpinner.this.getLocationOnScreen().x + FlatSpinner.this.getSize().width) {
							if (MouseInfo.getPointerInfo().getLocation().getY() > FlatSpinner.this.getLocationOnScreen().y && MouseInfo.getPointerInfo().getLocation().getY() < FlatSpinner.this.getLocationOnScreen().y + 10) {
								// Pfeil nach oben
								arrow = 1;
							} else if (MouseInfo.getPointerInfo().getLocation().getY() > FlatSpinner.this.getLocationOnScreen().y + 11
									&& MouseInfo.getPointerInfo().getLocation().getY() < FlatSpinner.this.getLocationOnScreen().y + FlatSpinner.this.getSize().height) {
								// Pfeil nach unten
								arrow = 2;
							} else {
								arrow = 0;
							}
						}
					} catch (IllegalComponentStateException ex) {
						// Ignore this error
					}
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {

						e.printStackTrace();
					}
				}
			}

		});
		t.setPriority(Thread.MAX_PRIORITY);
		t.start();
		addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (arrow == 1) {
					// Up
					if (index > 0) {
						index--;
					}
				} else if (arrow == 2) {
					// Down
					if (index < values.size() - 1) {
						index++;
					}
				}
				repaint();
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
			}

		});
	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(2));
		super.paintComponent(g2);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, !FlatUI.isFastRendering() ? RenderingHints.VALUE_ANTIALIAS_ON : RenderingHints.VALUE_ANTIALIAS_OFF);
		g2.setColor(background);
		g2.fillRect(0, 0, getBounds().getSize().width, getBounds().getSize().height);
		g2.setColor(foreground);
		g2.setFont(font);
		FontMetrics fm = g2.getFontMetrics(font);
		int x = 5;
		int y = fm.getAscent() - 2;
		g2.drawString(getCurrentValue(), x, y);
		g2.setColor(background.brighter());
		g2.fillRect(getBounds().getSize().width - 20, 0, getBounds().getSize().width, getBounds().getSize().height);
		g2.setColor(foreground);
		int[] xList1 = new int[] { getBounds().getSize().width - 18, getBounds().getSize().width - 10, getBounds().getSize().width - 2 };
		int[] yList1 = new int[] { 9, 2, 9 };
		int[] xList2 = new int[] { getBounds().getSize().width - 18, getBounds().getSize().width - 10, getBounds().getSize().width - 2 };
		int[] yList2 = new int[] { getBounds().getSize().height - 9, getBounds().getSize().height - 2, getBounds().getSize().height - 9 };

		g2.fillPolygon(xList1, yList1, xList1.length);
		g2.fillPolygon(xList2, yList2, xList2.length);
		// g2.setColor(foreground.darker());
		// g2.fillRect(getBounds().getSize().width - 18,
		// getBounds().getSize().height / 2 -1, 15, 1);
	}

	private String getCurrentValue() {
		if (values.size() > 0 && values.size() > index && values.get(index) != null) {
			return values.get(index);
		}
		return "";
	}

	/**
	 * Adds a new spinner entry
	 * 
	 * @param value
	 */
	public void addValue(String value) {
		this.values.add(value);
		repaint();
	}

	/**
	 * Removes a value / entry
	 * 
	 * @param value
	 * @return if the value exists
	 */
	public boolean removeValue(String value) {
		for (int i = 0; i < values.size(); i++) {
			if (values.get(i).equals(value)) {
				values.remove(i);
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns the current values
	 * 
	 * @return
	 */
	public String[] getValues() {
		return (String[]) values.toArray();
	}

	/**
	 * Returns current index
	 * 
	 * @return
	 */
	public int getIndex() {
		return this.index;
	}

	/**
	 * Sets current index
	 * 
	 * @param index
	 */
	public void setIndex(int index) {
		this.index = index;
		repaint();
	}

	@Override
	public Color getForeground() {
		return this.foreground;
	}

	@Override
	public void setForeground(Color color) {
		this.foreground = color;
	}

	@Override
	public void setFont(Font font) {
		this.font = font;
	}

	@Override
	public Font getFont() {
		return this.font;
	}
}
