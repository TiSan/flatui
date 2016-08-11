package de.tisan.flatui.components.fcontextmenu;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JPanel;

import de.tisan.flatui.components.fcommons.FlatColors;
import de.tisan.flatui.components.fcommons.FlatComponent;
import de.tisan.flatui.components.fcommons.FlatUI;
import de.tisan.flatui.components.ffont.FlatFont;
import de.tisan.flatui.components.ficon.FlatIconFont;
/**
 * This class is able to display a flat context menu for the flat components.
 * @author TiSan
 *
 */
public class FlatContextMenu extends JFrame {

	private static final long serialVersionUID = 851671409460560748L;
	private ArrayList<FlatContextMenuEntry> entries;
	private FlatContextMenuPainter painter;
	private JPanel contentPane;
	private Font font;
	private int numberOfEntryMarked = 0;
	private ArrayList<FlatContextMenuEntryListenerGlobal> globalListeners;
	/**
	 * Creates a context menu with some entries.
	 * @param entries Your collection of entries
	 */
	public FlatContextMenu(FlatContextMenuEntry... entries) {
		this.entries = new ArrayList<>(Arrays.asList(entries));
		setTitle("");
		this.globalListeners = new ArrayList<FlatContextMenuEntryListenerGlobal>();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1, 598);
		setAlwaysOnTop(true);
		setUndecorated(true);
		contentPane = new JPanel();
		contentPane.setLayout(null);
		contentPane.setFocusable(true);
		contentPane.setBackground(FlatColors.SILVER);
		setContentPane(contentPane);
		setVisible(false);
		this.painter = new FlatContextMenuPainter(this);
		this.painter.setBounds(0, 0, getWidth(), getHeight());
		contentPane.add(painter);
		painter.setFont(FlatFont.getInstance(22, Font.PLAIN));
		painter.setForeground(FlatColors.BACKGROUND);
		painter.setBackground(FlatColors.SILVER);
		// ~ setExtendedState(getExtendedState() & (~JFrame.ICONIFIED));
		painter.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent arg0) {
				// System.out.println("Hi");
				int y = arg0.getY();
				if (y > 0) {
					int numberOfEntry = y / 20;
					// System.out.println("Number: " + numberOfEntry);
					numberOfEntryMarked = numberOfEntry;
					repaint();
				}
			}

			@Override
			public void mouseDragged(MouseEvent arg0) {
				int y = arg0.getY();
				if (y > 0) {
					int numberOfEntry = y / 20;
					// System.out.println("Number: " + numberOfEntry);
					numberOfEntryMarked = numberOfEntry;
					repaint();
				}
			}
		});

		painter.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent arg0) {
				int y = arg0.getY();
				if (y > 0) {
					int numberOfEntry = y / 20;
					// System.out.println("Number: " + numberOfEntry);
					numberOfEntryMarked = numberOfEntry;
					FlatContextMenuEntry entry = FlatContextMenu.this.entries.get(numberOfEntry);
					if (entry.getType().equals(FlatContextMenuEntryType.CHECKBOX)) {
						entry.setChecked(!entry.isChecked());
					}
					for (FlatContextMenuEntryListener l : entry.getListeners()) {
						l.onClick();
					}
					for (FlatContextMenuEntryListenerGlobal l : globalListeners) {
						l.onClick(entry);
					}
					setVisible(false);
				}
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
		addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				setVisible(false);
			}

			@Override
			public void focusGained(FocusEvent e) {
			}
		});
	}
	/**
	 * Displays the contextmenu
	 */
	public void showMenu() {

		Point p = MouseInfo.getPointerInfo().getLocation();
		setLocation(p.x, p.y);
		setSize(this.painter.getSize().width, this.painter.getSize().height);
		setVisible(true);
		requestFocus();
	}
	/**
	 * Returns all Menu entries
	 * @return
	 */
	public ArrayList<FlatContextMenuEntry> getEntries() {
		return entries;
	}
	/**
	 * Adds a global listener
	 * @param l
	 * @return
	 */
	public FlatContextMenu addGlobalListener(FlatContextMenuEntryListenerGlobal l) {
		this.globalListeners.add(l);
		return this;
	}

	public class FlatContextMenuPainter extends FlatComponent {

		private static final long serialVersionUID = 6137105261503609093L;
		private FlatContextMenu menu;

		private FlatContextMenuPainter(FlatContextMenu menu) {
			super(null);
			this.menu = menu;
		}

		@Override
		protected void paintComponent(Graphics arg0) {
			super.paintComponent(arg0);
			Graphics2D g = (Graphics2D) arg0;
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, !FlatUI.isFastRendering() ? RenderingHints.VALUE_ANTIALIAS_ON : RenderingHints.VALUE_ANTIALIAS_OFF);
			// int x = 3;
			int y = 0;
			int width = getWidth();

			int i = 0;
			for (FlatContextMenuEntry entry : menu.getEntries()) {

				if (i == numberOfEntryMarked) {
					g.setColor(FlatColors.SILVER.darker());
					g.fillRect(0, y, width, 20);
				}
				if (entry.getType().equals(FlatContextMenuEntryType.NORMAL) || entry.getType().equals(FlatContextMenuEntryType.INPUT)) {
					if (entry.getIcon() != null) {
						g.drawImage(entry.getIcon(), 0, 0, null);
					}
					if (entry.getFlatIcon() != null) {
						g.setColor(this.getForeground());
						g.setFont(FlatIconFont.getInstance().deriveFont(Font.PLAIN, 15));
						g.drawString(entry.getFlatIcon().getValue(), 3, y + 15);
					}
				} else {
					g.setColor(FlatColors.SILVER.darker().darker());
					g.fillRect(0, y, 20, 20);
					if (entry.isChecked()) {
						g.setColor(FlatColors.LIME.brighter());
						// g.fillRoundRect(3, y + 19, 19, y + 26, 4, 4);
						g.setFont(FlatIconFont.getInstance().deriveFont(Font.PLAIN, 15));
						g.drawString(FlatIconFont.CHECK.getValue(), 3, y + 15);
					}
				}

				FontMetrics fm = g.getFontMetrics(getFont());
				java.awt.geom.Rectangle2D rect = fm.getStringBounds(entry.getName(), g);
				g.setFont(getFont().deriveFont(Font.PLAIN, 15));
				int textHeight = (int) (rect.getHeight());
				int textWidth = (int) (rect.getWidth());
				if (width < textWidth) {
					width = textWidth + 5;
					setSize(width, getHeight());
				}
				int panelHeight = 20;
				int yString = (panelHeight - textHeight) / 2 + fm.getAscent();
				g.setColor(getForeground());
				g.drawString(entry.getName(), 21, yString + y - 5);
				y += 20;
				i++;
			}
			setSize(getSize().width, y);
			menu.setSize(getSize());

		}
	}

	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		this.font = font;
	}

}
