package de.tisan.flatui.components.fslider;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.IllegalComponentStateException;
import java.awt.MouseInfo;

import de.tisan.flatui.components.fcommons.FlatColors;
import de.tisan.flatui.components.fcommons.FlatComponent;
import de.tisan.flatui.components.fcommons.FlatLayoutManager;

/**
 * A flat slider
 * 
 * @author TiSan
 * 
 */
public class FlatSlider extends FlatComponent {
	private static final long serialVersionUID = -6619404084138108339L;
	private int value;
	private Color foreground;
	private int minX;
	private int maxX;
	public boolean mouseDown;

	/**
	 * Creates a new FlatSlider
	 * 
	 * @param man
	 *            Instance of FlatLayoutManager
	 */
	public FlatSlider(FlatLayoutManager man) {
		super(man);
		man.addComponent(this);
		if(man.getContentPane() != null)
		man.getContentPane().add(this, 0);
		this.value = 0;
		this.mouseDown = false;
		this.background = FlatColors.HIGHLIGHTBACKGROUND;
		this.backOrigin = new Color(background.getRed(), background.getGreen(), background.getBlue());
		this.foreground = FlatColors.BLUE;
		this.minX = 6;
		this.maxX = 0;
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					try {
						if (mouseDown && MouseInfo.getPointerInfo().getLocation().getX() >= FlatSlider.this.getLocationOnScreen().x + minX
								&& MouseInfo.getPointerInfo().getLocation().getX() <= FlatSlider.this.getLocationOnScreen().x + maxX + 6) {
							int x = (int) (MouseInfo.getPointerInfo().getLocation().getX() - FlatSlider.this.getLocationOnScreen().x - minX);
							value = x * 100 / maxX;
							repaint();
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

	}

	/**
	 * Returns the current value
	 * 
	 * @return
	 */
	public int getValue() {
		return this.value;
	}

	/**
	 * Sets the current value
	 * 
	 * @param value
	 */
	public void setValue(int value) {
		this.value = value;
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

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(background);
		g.fillRect(0, 0, getBounds().getSize().width, getBounds().getSize().height);
		g.setColor(background.brighter().brighter());
		g.fillRect(10, getBounds().getSize().height / 2 - 5, getBounds().getSize().width - 20, 10);
		this.maxX = getBounds().getSize().width - 20;
		g.setColor(foreground);
		g.fillRect(minX + (value * maxX / 100), getBounds().getSize().height / 2 - 10, 10, 20);
	}
}
