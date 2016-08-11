package de.tisan.flatui.components.fprogressbar;

import java.awt.Color;
import java.awt.Graphics;

import de.tisan.flatui.components.fcommons.FlatColors;
import de.tisan.flatui.components.fcommons.FlatComponent;
import de.tisan.flatui.components.fcommons.FlatLayoutManager;

/**
 * A flat progress bar.
 * 
 * @author TiSan
 * 
 */
public class FlatProgressBar extends FlatComponent {

	private static final long serialVersionUID = -210872572814865539L;
	private int value;

	private Color colorBar;
	private int pntValue;
	private boolean waiting;
	
	/**
	 * Creates a new FlatProgressbar
	 * 
	 * @param animation
	 *            Should the progress bar have an animation?
	 * @param man
	 *            Instance of the flatLayoutManager
	 */
	public FlatProgressBar(boolean animation, FlatLayoutManager man) {
		super(man);
		man.addComponent(this);
		if(man.getContentPane() != null)
		man.getContentPane().add(this, 0);
		this.value = 0;
		this.background = FlatColors.HIGHLIGHTBACKGROUND;
		this.backOrigin = new Color(background.getRed(), background.getGreen(), background.getBlue());
		this.colorBar = FlatColors.BLUE;
		this.waiting = animation;
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				int tmpValue = value;
				boolean b = false;
				while (true) {
					if (tmpValue != value * 10) {
						if (tmpValue < value * 10) {
							for (int i = tmpValue; i < value * 10; i += 1) {
								tmpValue += 1;
								pntValue = tmpValue;
								repaint();
								if (waiting) {
									if(b){
										try {
											Thread.sleep(1);

										} catch (InterruptedException e) {
										}
									}
									b = !b;
								}
							}

							tmpValue = value * 10;
						} else {
							pntValue = value * 10;
							tmpValue = value * 10;
							repaint();
						}
					} else {
						try {
							Thread.sleep(100);

						} catch (InterruptedException e) {
						}
					}
				}
			}

		});
		t.setPriority(Thread.MAX_PRIORITY);
		t.start();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(background);
		g.fillRect(0, 0, this.getBounds().getSize().width, this.getBounds().getSize().height);
		g.setColor(colorBar);
		g.fillRect(0, 0, ((pntValue * this.getBounds().getSize().width) / 1000), this.getBounds().getSize().height);
	}

	/**
	 * Returns the current value of the progress bar. 100 = full, 0 = empty
	 * 
	 * @return progress bar value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Sets the value of the progress bar. More than a value of 100 is not
	 * allowed.
	 * 
	 * @param value
	 *            0 <= value <= 100
	 */
	public void setValue(int value) {
		if (value > 100) {
			this.value = 100;
		} else if (value < 0) {
			this.value = 0;
		} else {
			this.value = value;
		}
		repaint();
	}

	/**
	 * Returns the color of the progress bar.
	 */
	@Override
	public Color getForeground() {
		return this.colorBar;
	}

	/**
	 * Changes the color of the progress bar.
	 */
	@Override
	public void setForeground(Color foreground) {
		this.colorBar = foreground;
	}
}
