package de.tisan.flatui.helpers.fadehelper;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

public class FadeHelper {
	public static void fadeIn(JComponent component, Runnable... onFinish) {
		FadePainter pnlPainter = new FadePainter();
		pnlPainter.setBounds(component.getBounds());

		pnlPainter.setImage(getImageOfComponent(component));
		component.getParent().add(pnlPainter);
		pnlPainter.repaint();
		new Thread(new Runnable() {

			@Override
			public void run() {
				pnlPainter.fadeIn();
				component.setVisible(true);
				pnlPainter.setVisible(false);
				component.getParent().remove(pnlPainter);
				if (onFinish != null) {
					for (Runnable r : onFinish) {
						r.run();
					}
				}
				//System.out.println("Elapsed time: " + (System.currentTimeMillis() - time1) + " ms");
			}
		}).start();

	}

	public static void fadeOut(JComponent component, Runnable... onFinish) {
		FadePainter pnlPainter = new FadePainter();
		pnlPainter.setBounds(component.getBounds());
		pnlPainter.setImage(getImageOfComponent(component));
		component.getParent().add(pnlPainter);
		pnlPainter.repaint();
		component.setVisible(false);
		new Thread(new Runnable() {

			@Override
			public void run() {
				pnlPainter.fadeOut();
				pnlPainter.setVisible(false);
				component.getParent().remove(pnlPainter);
				if (onFinish != null) {
					for (Runnable r : onFinish) {
						r.run();
					}
				}
				//System.out.println("Elapsed time: " + (System.currentTimeMillis() - time1) + " ms");
			}
		}).start();
	}

	private static BufferedImage getImageOfComponent(JComponent component) {
		component.repaint();
		BufferedImage img = new BufferedImage(component.getWidth(), component.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics g = img.createGraphics();
		component.paint(g);
		return img;
	}
}
