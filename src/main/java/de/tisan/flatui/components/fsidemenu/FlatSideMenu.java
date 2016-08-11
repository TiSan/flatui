package de.tisan.flatui.components.fsidemenu;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import de.tisan.flatui.components.fcommons.FlatColors;
import de.tisan.flatui.components.fcommons.FlatComponent;
import de.tisan.flatui.components.fcommons.FlatLayoutManager;
import de.tisan.flatui.components.ffont.FlatFont;
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

public class FlatSideMenu extends FlatComponent {
	private static final long serialVersionUID = 5543373549552169741L;
	private ArrayList<FlatSideMenuEntry> rootEntries;
	private ArrayList<FlatSideMenuEntry> currentEntries;
	private FlatSideMenuEntry displayed;
	private int entryWidth;
	private int entryHeight;

	public FlatSideMenu(FlatLayoutManager man) {
		super(man);
		entryWidth = 150;
		entryHeight = 50;
		rootEntries = new ArrayList<FlatSideMenuEntry>();
		currentEntries = new ArrayList<FlatSideMenuEntry>();
		background = FlatColors.BACKGROUND;
		disableEffects();

		addMouseListener(Priority.HIGH, new MouseListener() {

			@Override
			public void onMouseRelease(MouseReleaseHandler handler) {
				int x = handler.getEvent().getX();
				int y = handler.getEvent().getY();

				if (x >= 0 && x <= entryWidth) {
					int nr = (int) (y / entryHeight);
					if (nr < currentEntries.size()) {
						FlatSideMenuEntry e = currentEntries.get(nr);
						e.setSelected(false);
						e.getMenu().repaint();
					}
				}
			}

			@Override
			public void onMousePress(MousePressHandler handler) {
				int x = handler.getEvent().getX();
				int y = handler.getEvent().getY();

				if (x >= 0 && x <= entryWidth) {
					int nr = (int) (y / entryHeight);

					if (nr < currentEntries.size()) {
						FlatSideMenuEntry e = currentEntries.get(nr);
						e.setSelected(true);
						e.getMenu().repaint();
					}
				}
			}

			@Override
			public void onMouseMove(MouseMoveHandler handler) {
			}

			@Override
			public void onMouseLeave(MouseLeaveHandler handler) {

			}

			@Override
			public void onMouseEnter(MouseEnterHandler handler) {
			}

			@Override
			public void onMouseDrag(MouseDragHandler handler) {

			}

			@Override
			public void onMouseClicked(MouseClickedHandler handler) {
				int x = handler.getEvent().getX();
				int y = handler.getEvent().getY();

				if (x >= 0 && x <= entryWidth) {
					int nr = (int) (y / entryHeight);

					if (nr < currentEntries.size()) {
						FlatSideMenuEntry e = currentEntries.get(nr);
						for (FlatSideMenuListener l : e.getListeners()) {
							l.onClick();
						}
					}
				}
			}
		});
	}

	public void addRootEntry(FlatSideMenuEntry entry) {
		rootEntries.add(entry);
		currentEntries.add(entry);
	}

	public ArrayList<FlatSideMenuEntry> getRootEntries() {
		return rootEntries;
	}

	public void goIntoEntry(FlatSideMenuEntry e, boolean up) {
		if (e.getSubEntries().contains(displayed)) {
			// System.out.println("Sonderfall -- goto root");
			displayed = null;
			currentEntries.clear();
			for (FlatSideMenuEntry ee : rootEntries) {
				currentEntries.add(ee);
				ee.setSelected(false);
			}
		} else if (e.getSubEntries().size() > 0) {
			currentEntries.clear();
			for (FlatSideMenuEntry en : e.getSubEntries()) {
				currentEntries.add(en);
				en.setSelected(false);
			}
			repaint();
		} else {

			e.setSelected(true);
			for (FlatSideMenuEntry ent : currentEntries) {
				if (ent != e) {
					ent.setSelected(false);
				}
			}

		}
		JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
		if (displayed != null && displayed.getContentPane() != null) {
			getParent().remove(displayed.getContentPane());
		}
		if (!up && e.getContentPane() != null) {
			e.getContentPane().setBounds(getLocation().x + 150, getLocation().y, getWidth() - 150, getHeight());
			topFrame.getContentPane().add(e.getContentPane(), 0);
			// topFrame.getContentPane().remove(this);
			// getParent().add(e.getContentPane(), 0);
			topFrame.repaint();
		}

		displayed = e;
		repaint();

	}

	@Override
	protected void paintComponent(Graphics arg0) {
		super.paintComponent(arg0);
		Graphics2D g = (Graphics2D) arg0;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(background);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(background.brighter());
		g.fillRect(0, 0, entryWidth, getHeight());
		int x = 0;
		int y = 0;
		for (FlatSideMenuEntry e : currentEntries) {
			if (e.isSelected()) {
				g.setColor(e.getHighlightForeground());

			} else {
				g.setColor(e.getBackground());

			}
			g.fillRect(x, y, entryWidth, entryHeight);
			g.setColor(e.getForeground());
			g.setFont(FlatFont.getInstance(24, Font.PLAIN));
			int x1 = 0;
			int y1 = 0;
			while (true) {
				FontMetrics fm = g.getFontMetrics(g.getFont());
				java.awt.geom.Rectangle2D rect = fm.getStringBounds(e.getName(), g);
				int textHeight = (int) (rect.getHeight());
				int textWidth = (int) (rect.getWidth());
				int panelHeight = entryHeight;
				int panelWidth = (e.getIcon() != null ? entryWidth - 50 : entryWidth);
				x1 = (panelWidth - textWidth) / 2;
				y1 = (panelHeight - textHeight) / 2 + fm.getAscent();
				if (x1 < 2 || y1 < 2) {
					g.setFont(FlatFont.getInstance(g.getFont().getSize() - 1, g.getFont().getStyle()));
				} else {
					break;
				}
			}
			if (e.getIcon() != null) {
				x = 50;
			} else {
				x = 0;
			}
			g.drawString(e.getName(), x + x1, y + y1);
			if (e.getIcon() != null) {
				g.setColor(e.getForeground());
				g.setFont(FlatIconFont.getInstance());
				FontMetrics fm1 = g.getFontMetrics(FlatIconFont.getInstance(24, Font.PLAIN));
				java.awt.geom.Rectangle2D rect1 = fm1.getStringBounds(e.getIcon().getValue(), g);
				int textHeight1 = (int) (rect1.getHeight());
				int panelHeight1 = entryHeight;
				int x2 = (int) ((e.getName() != null && e.getName().equals("")) ? ((entryWidth - rect1.getWidth()) / 2) : 16);
				int y2 = (panelHeight1 - textHeight1) / 2 + fm1.getAscent();
				g.drawString(e.getIcon().getValue(), x2, y + y2);
			}
			x = 0;
			y += entryHeight;
		}

	}

}
