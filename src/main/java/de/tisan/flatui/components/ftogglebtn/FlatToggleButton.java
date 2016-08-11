package de.tisan.flatui.components.ftogglebtn;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import de.tisan.flatui.components.fcommons.FlatColors;
import de.tisan.flatui.components.fcommons.FlatComponent;
import de.tisan.flatui.components.fcommons.FlatLayoutManager;
import de.tisan.flatui.components.ficon.FlatIconFont;
import de.tisan.flatui.components.flisteners.MouseClickedHandler;
import de.tisan.flatui.components.flisteners.MouseDragHandler;
import de.tisan.flatui.components.flisteners.MouseEnterHandler;
import de.tisan.flatui.components.flisteners.MouseLeaveHandler;
import de.tisan.flatui.components.flisteners.MouseListener;
import de.tisan.flatui.components.flisteners.MouseMoveHandler;
import de.tisan.flatui.components.flisteners.MousePressHandler;
import de.tisan.flatui.components.flisteners.MouseReleaseHandler;
import de.tisan.flatui.components.flisteners.Priority;

public class FlatToggleButton extends FlatComponent {
	private static final long serialVersionUID = 2193391974230755289L;
	private boolean on;
	private boolean locked;

	public FlatToggleButton(FlatLayoutManager man) {
		super(man);
		man.addComponent(this);
		if(man.getContentPane() != null)
		man.getContentPane().add(this, 0);

		//disableEffects();
		setForeground(FlatColors.FLATUI_ACCENT);

		addMouseListener(Priority.HIGH, new MouseListener() {

			@Override
			public void onMouseRelease(MouseReleaseHandler handler) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onMousePress(MousePressHandler handler) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onMouseMove(MouseMoveHandler handler) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onMouseLeave(MouseLeaveHandler handler) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onMouseEnter(MouseEnterHandler handler) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onMouseDrag(MouseDragHandler handler) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onMouseClicked(MouseClickedHandler handler) {
				if (!locked) {
					on = !on;
					repaint();
				}
			}
		});

	}

	public boolean isOn() {
		return on;
	}

	public void setOn(boolean on) {
		this.on = on;
		repaint();
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g2) {
		super.paintComponent(g2);
		Graphics2D g = (Graphics2D) g2;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(getBackground());
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(getForeground());
		for (int i = 2; i < 6; i++) {
			g.drawRect(i, i, getWidth() - i * 2 - 1, getHeight() - i * 2 - 1);
		}
		if (!on) {
			g.fillRect(9, 9, (int) (getWidth() * 0.2), getHeight() - 18);
			if (locked) {
				g.setFont(FlatIconFont.getInstance(12, Font.PLAIN));
				g.setColor(getBackground());
				g.drawString(FlatIconFont.LOCK.getValue(), 15, 30);
			}
		} else {
			g.fillRect((int) (getWidth() - 9 - (getWidth() * 0.2)), 9, (int) (getWidth() * 0.2), getHeight() - 18);
			if (locked) {
				g.setFont(FlatIconFont.getInstance(12, Font.PLAIN));
				g.setColor(getBackground());
				g.drawString(FlatIconFont.LOCK.getValue(), (int) (getWidth() - 2 - (getWidth() * 0.2)), 30);
			}
		}

	}

}
