package de.tisan.flatui.helpers.fadehelper;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class FadePainter extends JPanel {

	private static final long serialVersionUID = -2835519938646414629L;
	private BufferedImage img;
	private int width, height, x, y;
	private float opacity;

	protected FadePainter() {
		img = null;
		width = 1;
		height = 1;
		x = 1;
		y = 1;
		opacity = 0F;
		setBackground(new Color(0, 0, 0, 0));
		setOpaque(false);
	}

	protected void setImage(BufferedImage img) {
		this.img = img;

	}

	public void fadeOut() {
		width = img.getWidth();
		height = img.getHeight();
		opacity = 1F;
		x = getWidth() / 2 - width / 2;
		y = getHeight() / 2 - height / 2;
		repaint();
		double perc = 100.0;
		while (opacity > 0F) {
			repaint();
			try {
				Thread.sleep(8);
			} catch (InterruptedException e) {

			}
			opacity -= 0.04F;
			width = (int) ((perc * img.getWidth()) / 100);
			height = (int) ((perc * img.getHeight()) / 100);
			x = getWidth() / 2 - width / 2;
			y = getHeight() / 2 - height / 2;
			perc-= 0.4;
		}
		opacity = 0F;
		repaint();
	}

	public void fadeIn() {
		width = img.getWidth() - 25;
		height = img.getHeight() - 25;
		opacity = 0F;
		x = getWidth() / 2 - width / 2;
		y = getHeight() / 2 - height / 2;
		repaint();
		double perc = 90.0;
		while (opacity < 1F) {
			repaint();
			try {
				Thread.sleep(8);
			} catch (InterruptedException e) {

			}
			opacity += 0.04F;
			width = (int) ((perc * img.getWidth()) / 100);
			height = (int) ((perc * img.getHeight()) / 100);
			x = getWidth() / 2 - width / 2;
			y = getHeight() / 2 - height / 2;
			perc+=0.4;
		}
		opacity = 1F;
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g2) {
		super.paintComponent(g2);
		Graphics2D g = (Graphics2D) g2;
		g.setColor(new Color(0, 0, 0, 0));
		g.fillRect(0, 0, getWidth(), getHeight());
		AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity);
		g.setComposite(ac);
		if (img != null) {
			//g.drawImage(img.getScaledInstance(width, height, Image.SCALE_FAST), x, y, null);
			g.drawImage(img, x, y, width, height, null);
		}
	}

}
