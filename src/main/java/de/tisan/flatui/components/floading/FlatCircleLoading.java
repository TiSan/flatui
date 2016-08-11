package de.tisan.flatui.components.floading;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import de.tisan.flatui.components.fcommons.FlatColors;
import de.tisan.flatui.components.fcommons.FlatComponent;
import de.tisan.flatui.components.fcommons.FlatLayoutManager;
import de.tisan.flatui.components.ffont.FlatFont;

public class FlatCircleLoading extends FlatComponent {
	private static final long serialVersionUID = -4090999263482799056L;
	private int r = 50;
	private int h = 100;
	private int k = 100;
	private ArrayList<Dot> d = new ArrayList<Dot>();
	private boolean running;
	private Color foreground;
	private int percent;
	private boolean showPercent;
	private Font textFont;

	public FlatCircleLoading(int dots, FlatLayoutManager man) {
		super(man);
		this.foreground = FlatColors.FLATUI_ACCENT.brighter();
		setOpaque(false);
		for (int i = 0; i < dots; i++) {
			d.add(new Dot(0, getForeground()));
		}

	}

	public ArrayList<Dot> getDots() {
		return d;
	}

	public int getPercent() {
		return percent;
	}

	public void setPercent(int percent) {
		this.percent = percent;
	}

	public boolean isShowPercent() {
		return showPercent;
	}

	public void setShowPercent(boolean showPercent) {
		this.showPercent = showPercent;
	}

	public Color getForeground() {
		return foreground;
	}

	public void setForeground(Color foreground) {
		this.foreground = foreground;
	}

	public synchronized void startAnimation() {
		if (!running) {
			running = true;
			new Thread(new Runnable() {

				@Override
				public void run() {
					int i = 0;
					int time = 10;
					while (running) {
						for (int j = 1; j <= d.size(); j++) {
							d.get(j - 1).d = ((i * j) * (Math.PI / 180));
						}

						repaint();
						if (i == 360) {
							i = 0;

						}

						i++;
						try {
							Thread.sleep(time);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}).start();
		}
	}

	public void stopAnimation() {
		running = false;
	}

	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, width, height);
		if (width >= height) {
			r = (height / 3);

		} else {
			r = (width / 3);

		}
		h = width / 2 - r / 5;
		k = height / 2 - r / 5;

		calcTextFont();

	}

	private void calcTextFont() {
		if (getGraphics() != null) {
			int j = 200;
			boolean found = false;
			Font f = textFont;
			if (textFont == null) {
				f = FlatFont.getInstance(j, Font.PLAIN);
			}
			while (j > 0) {
				f = f.deriveFont(Font.PLAIN, j);
				String text = percent + "";
				FontMetrics fm = getGraphics().getFontMetrics(f);
				java.awt.geom.Rectangle2D rect = fm.getStringBounds(text, getGraphics());
				int textHeight = (int) (rect.getHeight());
				int textWidth = (int) (rect.getWidth());
				if (textWidth <= r * 2 - 30 && textHeight <= r * 2 - 30) {
					textFont = f;
					found = true;
					break;
				}
				j--;
			}
			if (!found) {
				textFont = f.deriveFont(Font.PLAIN, 200);
			}
			//System.out.println(textFont.getSize());
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	protected void paintComponent(Graphics arg0) {
		super.paintComponent(arg0);
		Graphics2D g = (Graphics2D) arg0;
		// g.setColor(FlatColors.LIGHTWHITE);
		// g.fillRect(0, 0, getWidth(),getHeight());
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		int ampli = (int) (0.005 * getWidth());
		for (Dot dd : (ArrayList<Dot>) d.clone()) {
			int x = (int) (r * Math.cos(dd.d) + h);
			int y = (int) (r * Math.sin(dd.d) + k);
			g.setColor(dd.color);

			g.fillRect(x, y, 4 + ampli, 4 + ampli);
		}

		if (showPercent) {
			if (textFont == null) {
				calcTextFont();
			}
			g.setColor(getForeground());
			g.setFont(textFont);
			String text = percent + "";
			FontMetrics fm = g.getFontMetrics(textFont);
			java.awt.geom.Rectangle2D rect = fm.getStringBounds(text, g);
			int textHeight = (int) (rect.getHeight());
			int textWidth = (int) (rect.getWidth());
			int x = h - textWidth / 2;
			int y = k - textHeight / 2 + fm.getAscent();
			// System.out.println("x=" + x + ";y=" + y);
			g.drawString(text, x, y);

		}

	}

	public class Dot {
		private double d;
		private Color color;

		private Dot(double d, Color color) {
			this.d = d;
			this.color = color;
		}

		public Color getColor() {
			return color;
		}

		public void setColor(Color color) {
			if(color != null){
				this.color = color;
			}
			
		}

	}
}
