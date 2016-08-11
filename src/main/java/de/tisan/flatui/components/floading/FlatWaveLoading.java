package de.tisan.flatui.components.floading;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;

import de.tisan.flatui.components.fcommons.FlatColors;
import de.tisan.flatui.components.fcommons.FlatComponent;
import de.tisan.flatui.components.fcommons.FlatLayoutManager;

public class FlatWaveLoading extends FlatComponent {
	private static final long serialVersionUID = -4090999263482799056L;
	private ArrayList<Dot> d = new ArrayList<Dot>();
	private boolean running;
	private Color foreground;

	public FlatWaveLoading(int dots, FlatLayoutManager man) {
		super(man);
		this.foreground = FlatColors.FLATUI_ACCENT.brighter();
		setOpaque(false);
		int count = 20;
		int cc = 10;
		long runningTime = System.currentTimeMillis();
		for (int i = 0; i < dots; i++) {
			d.add(new Dot(cc, getForeground(), count, 100, runningTime));
			count += 20;
			runningTime += 100;
			
		}
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

					int time = 2;
					while (running) {
						for (int j = 1; j <= d.size(); j++) {
							Dot dd = d.get(j - 1);
							if(System.currentTimeMillis() - dd.runningTIme >= 0){
								if (dd.d >= getHeight() - 60) {
									dd.up = false;
								} else if (dd.d <= 10) {
									dd.up = true;
								}
								if (dd.up) {
									dd.d++;
								} else {
									dd.d--;
								}
							}
							
						}
						
						repaint();

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
		int w = (width / d.size());
		int c = 10;
		
		for(Dot dd : d){
			dd.width = c;
			System.out.println(height);
			dd.height = height - 50;
			c+= w;
		}
//		if (width >= height) {
//			r = (height / 3);
//
//		} else {
//			r = (width / 3);
//
//		}
//		h = width / 2 - r / 5;
//		k = height / 2 - r / 5;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void paintComponent(Graphics arg0) {
		super.paintComponent(arg0);
		Graphics2D g = (Graphics2D) arg0;

		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		for (Dot dd : (ArrayList<Dot>) d.clone()) {
			int x = dd.width;
			int y = (int) (dd.height - dd.d);
			g.setColor(dd.color);
			g.fillRect(x, y, 4, 4);
		}

	}

	private class Dot {
		private double d;
		private Color color;
		private int height;
		private int width;
		private boolean up;
		private long runningTIme;
		private Dot(double d, Color color, int width, int height, long runningtime) {
			this.d = d;
			this.color = color;
			this.width = width;
			this.height = height;
			up = true;
			this.runningTIme = runningtime; 
		}

	}
}
