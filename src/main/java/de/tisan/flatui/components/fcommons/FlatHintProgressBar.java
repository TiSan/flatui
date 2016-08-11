package de.tisan.flatui.components.fcommons;

import java.awt.Color;
import java.awt.Graphics;
/**
 * This is a fork of the FlatProgressbar which is optimized for the FlatHintBox
 * @author Hendrik
 *
 */
public class FlatHintProgressBar extends FlatComponent {

	private static final long serialVersionUID = -210872572814865539L;
	private int value;

	private Color colorBar;
	private int pntValue;
	private boolean waiting;
	
	public FlatHintProgressBar(boolean animation, FlatLayoutManager man) {
		super(man);
		man.addComponent(this);
		this.value = 0;
		this.background = FlatColors.HIGHLIGHTBACKGROUND;
		this.backOrigin = new Color(background.getRed(), background.getGreen(), background.getBlue());
		this.colorBar = FlatColors.BLUE;
		this.waiting = animation;
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				int tmpValue = value;
				while (true) {
					if (tmpValue != value * 10) {
						if (tmpValue < value * 10) {
							for (int i = tmpValue; i < value * 10; i += 1) {
								tmpValue += 1;
								pntValue = tmpValue;
								repaint();
								if (waiting) {
									try {
										Thread.sleep(6);

									} catch (InterruptedException e) {
									}
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

	protected int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
		repaint();
	}

	@Override
	public Color getForeground() {
		return this.colorBar;
	}

	@Override
	public void setForeground(Color foreground) {
		this.colorBar = foreground;
	}
}
