package de.tisan.flatui.helpers.resizehelpers;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JFrame;

import de.tisan.flatui.components.fcommons.Anchor;

public class SwingResizeHelper {
	private ArrayList<ComponentHelper> helpers;
	private JFrame frame;

	private SwingResizeHelper(JFrame frame) {
		this.frame = frame;
		this.helpers = new ArrayList<ComponentHelper>();
		frame.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				for (ComponentHelper h : helpers) {
					if (h.anchors != null) {
						boolean center = contains(h.anchors, Anchor.CENTER);
						boolean down = contains(h.anchors, Anchor.DOWN);
						boolean left = contains(h.anchors, Anchor.LEFT);
						boolean right = contains(h.anchors, Anchor.RIGHT);
						if (down && !center && !left && !right) {
							// Only down
							h.component.setSize(h.component.getWidth(), (int) (e.getComponent().getHeight() * (h.getPercentHeight() / 100)));
						} else if (!down && !center && !left && right) {
							// Only right
							h.component.setLocation((int) (e.getComponent().getWidth() - h.component.getWidth() - h.getCornerRight()), h.component.getLocation().y);
						} else if (down && !center && !left && right) {
							// Right and down
							h.component.setLocation(e.getComponent().getWidth() - h.component.getWidth() - (int)h.getCornerRight(), e.getComponent().getHeight() - h.component.getHeight() - (int)h.getCornerDown());
						} else if (!down && !center && left && right) {
							// Only left and right
//							h.component.setSize((int) (e.getComponent().getWidth() * (h.getPercentWidth() / 100)), h.component.getHeight());
							h.component.setSize(frame.getBounds().width - h.offsetRight, h.component.getHeight());
						} else if (center) {
							// all directions
							h.component.setSize((int) (e.getComponent().getWidth() * (h.getPercentWidth() / 100)), (int) (e.getComponent().getHeight() * (h.getPercentHeight() / 100)));
						}
						
					}
				}

			}

		});
	}

	public void addComponent(JComponent component, Anchor... anchors) {
		ComponentHelper h = new ComponentHelper(component, anchors);
		helpers.add(h);
	}

	public void useNowSize() {
		for (ComponentHelper h : helpers) {
			h.percentWidth = (h.getComponent().getWidth() * 100) / frame.getBounds().width;
			h.percentHeight = (h.getComponent().getHeight() * 100) / frame.getBounds().height;
			h.cornerRight = frame.getBounds().width - (h.getComponent().getWidth() * 100) - h.component.getX();
			h.cornerDown = frame.getBounds().height - (h.getComponent().getHeight() * 100) - h.component.getY();
			h.offsetRight = frame.getBounds().width - h.getComponent().getWidth() - h.getComponent().getX();
		}
	}

	public static SwingResizeHelper getResizeHelperForSwing(JFrame frame) {
		return new SwingResizeHelper(frame);
	}

	private static boolean contains(Anchor[] anchors, Anchor anchor) {
		for (Anchor a : anchors) {
			if (a.equals(anchor)) {
				return true;
			}
		}
		return false;
	}

	private class ComponentHelper {
		private JComponent component;
		private Anchor[] anchors;
		private double percentWidth;
		private double percentHeight;
		private double cornerRight;
		private double cornerDown;
		private int offsetRight;
		private ComponentHelper(JComponent component, Anchor[] anchors) {
			this.component = component;
			this.anchors = anchors;
		}

		public JComponent getComponent() {
			return component;
		}

		public double getPercentWidth() {
			return percentWidth;
		}

		public double getCornerRight() {
			return cornerRight;
		}

		public double getCornerDown() {
			return cornerDown;
		}

		public double getPercentHeight() {
			return percentHeight;
		}
	}
}
