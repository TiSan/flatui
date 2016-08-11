package de.tisan.flatui.components.ftitlebar;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import de.tisan.flatui.components.fcommons.FlatColors;
import de.tisan.flatui.components.fcommons.FlatComponent;
import de.tisan.flatui.components.fcommons.FlatLayoutManager;
import de.tisan.flatui.components.fcommons.FlatUI;
import de.tisan.flatui.components.ffont.FlatFont;
import de.tisan.flatui.components.fmenu.FlatMenu;
import de.tisan.flatui.components.fmenu.FlatMenuPoint;

/**
 * Paints a stylish title bar to your upper window corner. setUndecorated(true)
 * is recommended. Use a FlatTitleBarListener for interaction with buttons.
 * 
 * @author TiSan
 *
 */
public class FlatTitleBarWin10 extends FlatComponent {
	private static final long serialVersionUID = -8772478458235629389L;
	private String text;
	private boolean closeable = true;
	private boolean maximizable = true;
	private boolean hideable = true;
	private boolean moveable = true;
	private Color foreground;
	private Color backgroundClose;
	private Color backgroundMax;
	private Color backgroundMin;
	private Color backgroundCloseOrigin;
	private Color backgroundMaxOrigin;
	private Color backgroundMinOrigin;
	private Color backgroundOptions;
	private boolean closePressed;
	private boolean maxPressed;
	private boolean minPressed;
	private boolean optPressed;
	private ArrayList<FlatTitleBarListener> listeners = new ArrayList<FlatTitleBarListener>();
	private JFrame frame;
	private boolean mouseDown = false;
	private Point lastMousePoint = null;
	private Font font;
	private FlatMenu optionMenu;
	private FlatLayoutManager man;
	private boolean optDisplayed;
	private boolean optionMenuDefaultDisplayed;
	private boolean optionMenuToggleEnabled;
	private boolean optionMenuOverlay;

	/**
	 * Creates a titlebar without an icon.
	 * 
	 * @param man
	 *            FlatLayoutManager instance
	 * @param text
	 *            Window title text
	 */
	public FlatTitleBarWin10(FlatLayoutManager man, String text) {
		super(man);
		frame = (JFrame) SwingUtilities.getWindowAncestor(this);
		this.man = man;
		man.addComponent(this);
		if (man.getContentPane() != null)
			man.getContentPane().add(this, 0);
		background = FlatColors.GREEN.darker();
		effects = false;
		this.backOrigin = new Color(background.getRed(), background.getGreen(), background.getBlue());
		this.text = text;
		this.optionMenuDefaultDisplayed = false;
		this.optionMenuToggleEnabled = true;
		this.backgroundOptions = background.darker();
		this.backgroundClose = background;
		this.backgroundCloseOrigin = FlatColors.ALIZARINRED;
		this.backgroundMax = background;
		this.backgroundMaxOrigin = FlatColors.LIGHTBLUE;
		this.backgroundMin = background;
		this.backgroundMinOrigin = FlatColors.LIGHTBLUE;
		this.foreground = FlatColors.WHITE;
		this.optionMenuOverlay = false;
		this.font = FlatFont.getInstance(16, Font.PLAIN);
		addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
			}

			@Override
			public void mouseEntered(final MouseEvent e) {
				mouseEntered = true;
				performColorChange(e.getX(), false, false, new Point(e.getXOnScreen(), e.getYOnScreen()));

			}

			@Override
			public void mouseExited(final MouseEvent e) {
				mouseEntered = false;
				performColorChange(e.getX(), false, true, new Point(e.getXOnScreen(), e.getYOnScreen()));
			}

			@Override
			public void mousePressed(final MouseEvent e) {
				mouseEntered = true;
				performColorChange(e.getX(), true, false, new Point(e.getXOnScreen(), e.getYOnScreen()));
				mouseDown = true;
				frame = (JFrame) SwingUtilities.getWindowAncestor(FlatTitleBarWin10.this);
				lastMousePoint = new Point(e.getLocationOnScreen().x - frame.getLocation().x, e.getLocationOnScreen().y - frame.getLocation().y);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				mouseEntered = false;
				mouseDown = false;
				performColorChange(e.getX(), false, true, new Point(e.getXOnScreen(), e.getYOnScreen()));
				minPressed = false;
				maxPressed = false;
				closePressed = false;
				optPressed = false;
			}
		});
		addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent e) {
				performColorChange(e.getX(), false, false, new Point(e.getXOnScreen(), e.getYOnScreen()));
				repaint();
			}

			@Override
			public void mouseDragged(MouseEvent e) {

				performColorChange(e.getX(), false, false, new Point(e.getXOnScreen(), e.getYOnScreen()));
				repaint();

			}
		});

	}

	@Override
	public void paintComponent(Graphics gr) {
		super.paintComponent(gr);
		Graphics2D g = (Graphics2D) gr;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, !FlatUI.isFastRendering() ? RenderingHints.VALUE_ANTIALIAS_ON : RenderingHints.VALUE_ANTIALIAS_OFF);
		g.setColor(background);
		g.fillRect(0, 0, getWidth(), getHeight());

		int x_tools = getWidth() - 30;

		if (closeable) {
			g.setColor(backgroundClose);
			g.fillRect(x_tools, 0, getHeight(), getHeight());
			g.setColor(foreground);
			g.drawLine(x_tools + 9, 9, x_tools + 21, 21);
			g.drawLine(x_tools + 9, 21, x_tools + 21, 9);
			x_tools -= 31;
		}
		if (maximizable) {
			g.setColor(backgroundMax);
			g.fillRect(x_tools, 0, getHeight(), getHeight());
			g.setColor(foreground);
			g.drawRect(x_tools + 9, 9, 12, 12);
			g.drawLine(x_tools + 9, 10, x_tools + 21, 10);
			x_tools -= 31;
		}
		if (hideable) {
			g.setColor(backgroundMin);
			g.fillRect(x_tools, 0, getHeight(), getHeight());
			g.setColor(foreground);
			g.drawLine(x_tools + 9, 20, x_tools + 21, 20);
			g.drawLine(x_tools + 9, 21, x_tools + 21, 21);

			x_tools -= 31;
		}
		g.setFont(font);
		FontMetrics fm = g.getFontMetrics(font);
		java.awt.geom.Rectangle2D rect = fm.getStringBounds(text, g);

		int textHeight = (int) (rect.getHeight());
		int panelHeight = this.getHeight();
		int y = (panelHeight - textHeight) / 2 + fm.getAscent();
		g.drawString(text, 40, y);
		if (optionMenuToggleEnabled) {
			g.setColor(backgroundOptions);
			g.fillRect(0, 0, 30, 30);
			g.setColor(backgroundOptions.brighter().brighter().brighter().brighter());
			g.drawLine(10, 10, 20, 10);
			g.drawLine(10, 15, 20, 15);
			g.drawLine(10, 20, 20, 20);
		}

	}

	private int getControlNr(int x) {
		int controlNr = 0;
		if (x >= getWidth() - 30) {
			if (closeable) {
				controlNr = 3;

			} else if (maximizable) {
				if (maximizable && hideable) {
					controlNr = 2;
				} else if (hideable) {
					controlNr = 1;
				} else if (maximizable) {
					controlNr = 2;
				} else {

				}

			} else if (hideable) {
				controlNr = 1;
			} else {
				controlNr = 0;
			}
		} else if (x >= getWidth() - 60 && x < getWidth() - 30) {
			if (closeable) {
				if (maximizable) {
					controlNr = 2;
				} else {
					controlNr = 1;
				}

			} else if (maximizable) {
				controlNr = 1;

			} else {
				controlNr = 0;
			}
		} else if (x >= getWidth() - 90 && x < getWidth() - 60) {
			if (closeable) {
				if (maximizable && hideable) {
					controlNr = 1;
				}
			} else {
				controlNr = 0;
			}
		} else if (x <= 30 && optionMenuToggleEnabled) {
			controlNr = 4;
		}
		return controlNr;
	}

	private void performColorChange(int x, boolean press, boolean black, Point onScreen) {
		int controlNr = getControlNr(x);
		switch (controlNr) {
		case 3:
			if (press) {
				backgroundClose = new Color(backgroundCloseOrigin.getRed() - 10, backgroundCloseOrigin.getGreen() - 10, backgroundCloseOrigin.getBlue() - 10);
				closePressed = true;
				for (FlatTitleBarListener l : listeners) {
					l.onCloseButtonPressed();
				}
			} else {
				backgroundClose = new Color(backgroundCloseOrigin.getRed(), backgroundCloseOrigin.getGreen(), backgroundCloseOrigin.getBlue());
				for (FlatTitleBarListener l : listeners) {
					l.onCloseButtonMouseMove();
				}
			}
			if (black) {
				backgroundClose = background;
				if (closePressed && !mouseDown) {
					for (FlatTitleBarListener l : listeners) {
						l.onCloseButtonReleased();
					}
					closePressed = false;
				}

			}
			backgroundMax = background;
			backgroundMin = background;
			break;
		case 2:
			if (press) {
				backgroundMax = new Color(backgroundMaxOrigin.getRed() - 10, backgroundMaxOrigin.getGreen() - 10, backgroundMaxOrigin.getBlue() - 10);
				maxPressed = true;
				for (FlatTitleBarListener l : listeners) {
					l.onMaximizeButtonPressed();
				}
			} else {
				backgroundMax = new Color(backgroundMaxOrigin.getRed(), backgroundMaxOrigin.getGreen(), backgroundMaxOrigin.getBlue());
				for (FlatTitleBarListener l : listeners) {
					l.onMaximizeButtonMouseMove();
				}

			}
			if (black) {
				backgroundMax = background;
				if (maxPressed && !mouseDown) {
					for (FlatTitleBarListener l : listeners) {
						l.onMaximizeButtonReleased();
					}
					maxPressed = false;
				}
			}
			backgroundMin = background;
			backgroundClose = background;
			break;
		case 1:
			if (press) {
				backgroundMin = new Color(backgroundMinOrigin.getRed() - 10, backgroundMinOrigin.getGreen() - 10, backgroundMinOrigin.getBlue() - 10);
				for (FlatTitleBarListener l : listeners) {
					minPressed = true;
					l.onMinimizeButtonPressed();
				}
			} else {
				backgroundMin = new Color(backgroundMinOrigin.getRed(), backgroundMinOrigin.getGreen(), backgroundMinOrigin.getBlue());
				for (FlatTitleBarListener l : listeners) {
					l.onMinimizeButtonMouseMove();
				}
			}
			if (black) {
				backgroundMin = background;
				if (minPressed && !mouseDown) {
					for (FlatTitleBarListener l : listeners) {
						l.onMinimizeButtonReleased();
					}
					minPressed = false;
				}

			}
			backgroundMax = background;
			backgroundClose = background;
			break;
		case 4:
			// if (mouseDown && !(maxPressed || minPressed || closePressed)) {
			// frame.setLocation(onScreen.x - lastMousePoint.x, onScreen.y -
			// lastMousePoint.y);
			// }
			if (optionMenuToggleEnabled) {
				if (press) {
					optPressed = true;
					if (!optDisplayed) {
						showMenu();
					} else {
						hideMenu();
					}
				} else {

				}
			}

			// Option pane
			// if(getContextMenuOptions() != null && mouseDown){
			// getContextMenuOptions().showMenu();
			// }
			break;
		default:
			backgroundMax = background;
			backgroundClose = background;
			backgroundMin = background;
			if (mouseDown && !(maxPressed || minPressed || closePressed || optPressed) && moveable) {
				frame.setLocation(onScreen.x - lastMousePoint.x, onScreen.y - lastMousePoint.y);
			}
			break;
		}

		repaint();
	}

	/**
	 * Adds a new title bar listener. HINT: The DefaultFlatTitleBarListener does
	 * the default interaction with your buttons!
	 * 
	 * @param listener
	 */
	public void addFlatTitleBarListener(FlatTitleBarListener listener) {
		listeners.add(listener);
	}

	/**
	 * Returns the window text.
	 * 
	 * @return Window text
	 */
	public String getText() {
		return text;
	}

	/**
	 * Sets the window text.
	 * 
	 * @param text
	 */
	public void setText(String text) {
		this.text = text;
		repaint();
	}

	/**
	 * Is the window closeable?
	 * 
	 * @return
	 */
	public boolean isCloseable() {
		return closeable;
	}

	/**
	 * Sets the bool if the window is closeable.
	 * 
	 * @param closeable
	 */
	public void setCloseable(boolean closeable) {
		this.closeable = closeable;
		repaint();
	}

	/**
	 * Is the window maximizable?
	 * 
	 * @return
	 */
	public boolean isMaximizable() {
		return maximizable;
	}

	/**
	 * Sets the bool if the window is maximizable.
	 * 
	 * @param maximizable
	 */
	public void setMaximizable(boolean maximizable) {
		this.maximizable = maximizable;
		repaint();
	}

	/**
	 * Is the window hideable?
	 * 
	 * @return
	 */
	public boolean isMinimizable() {
		return hideable;
	}

	/**
	 * Sets the bool if the window is hideable.
	 * 
	 * @param hideable
	 */
	public void setMinimizable(boolean hideable) {
		this.hideable = hideable;
		repaint();
	}

	public Color getForeground() {
		return foreground;
	}

	public void setForeground(Color foreground) {
		this.foreground = foreground;
	}

	public Color getBackgroundClose() {
		return backgroundCloseOrigin;
	}

	public void setBackgroundClose(Color backgroundClose) {
		this.backgroundCloseOrigin = backgroundClose;
	}

	public Color getBackgroundMax() {
		return backgroundMaxOrigin;
	}

	public void setBackgroundMax(Color backgroundMax) {
		this.backgroundMaxOrigin = backgroundMax;
	}

	public Color getBackgroundMin() {
		return backgroundMinOrigin;
	}

	public void setBackgroundMin(Color backgroundMin) {
		this.backgroundMinOrigin = backgroundMin;
	}

	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		this.font = font;
	}

	public void setBackground(Color background) {
		super.setBackground(background);
		if (background.equals(Color.black)) {
			this.backgroundOptions = FlatColors.BACKGROUND.darker();
			if (optionMenu != null) {
				optionMenu.setBackground(FlatColors.BACKGROUND.darker());
			}
		} else {
			this.backgroundOptions = background.darker();
			if (optionMenu != null) {
				optionMenu.setBackground(background.darker());
			}
		}

		this.backgroundClose = background;
		this.backgroundMax = background;
		this.backgroundMin = background;

		repaint();
	}

	public FlatMenu getOptionMenu() {
		return optionMenu;
	}

	public void setOptionMenu(FlatMenu optionMenu) {
		this.optionMenu = optionMenu;
		optionMenu.setBounds(getX(), getY() + getHeight(), getWidth(), 30);
		optionMenu.setVisible(optionMenuDefaultDisplayed);
		optionMenu.setBackground(backgroundOptions);
		int count = 0;
		FlatMenuPoint p;
		try {
			while ((p = optionMenu.getMenuPoint(count)) != null) {
				p.setVisible(optionMenuDefaultDisplayed);
				p.setBackground(backgroundOptions);
				count++;
			}
		} catch (Exception ex) {

		}

		// this.man.getContentPane().add(this.optionMenu);
	}

	public void showMenu() {
		if (optionMenu != null && !this.optDisplayed && optionMenu.getMenuPoints().size() > 0) {
			this.optDisplayed = true;
			JPanel cp = man.getContentPane();
			if(cp != null){
				for (Component c : cp.getComponents()) {
					if ((c instanceof FlatMenu) || (c instanceof FlatMenuPoint) || (c instanceof FlatTitleBarWin10)) {
						if (c instanceof FlatMenu) {
							FlatMenu m = (FlatMenu) c;
							m.setVisible(true);
							// displayComponent(m);

							FlatMenuPoint p;
							int count = 0;
							try {
								while ((p = m.getMenuPoint(count)) != null) {
									p.setVisible(true);
									// displayComponent(p);

									count++;
								}
							} catch (Exception ex) {

							}

						}
					} else {
						if (!optionMenuOverlay) {
							c.setLocation(c.getLocation().x, c.getLocation().y + 30);
						}

					}
				}
			}
			

		}
	}

	public void hideMenu() {
		if (optionMenu != null && this.optDisplayed) {
			this.optDisplayed = false;
			JPanel cp = man.getContentPane();
			if (cp != null) {
				for (Component c : cp.getComponents()) {
					if ((c instanceof FlatMenu) || (c instanceof FlatMenuPoint) || (c instanceof FlatTitleBarWin10)) {
						if (c instanceof FlatMenu) {
							FlatMenu m = (FlatMenu) c;
							m.setVisible(false);
							FlatMenuPoint p;
							int count = 0;
							try {
								while ((p = m.getMenuPoint(count)) != null) {
									p.setVisible(false);
									count++;
								}
							} catch (Exception ex) {

							}
						}
					} else {
						if (!optionMenuOverlay) {
							c.setLocation(c.getLocation().x, c.getLocation().y - 30);
						}

					}
				}
			}

		}
	}

	public boolean isOptionMenuDefaultDisplayed() {
		return optionMenuDefaultDisplayed;
	}

	public void setOptionMenuDefaultDisplayed(boolean optionMenuDefaultDisplayed) {
		this.optionMenuDefaultDisplayed = optionMenuDefaultDisplayed;
		this.optDisplayed = optionMenuDefaultDisplayed;
	}

	public boolean isOptionMenuToggleEnabled() {
		return optionMenuToggleEnabled;
	}

	public void setOptionMenuToggleEnabled(boolean optionMenuToggleEnabled) {
		this.optionMenuToggleEnabled = optionMenuToggleEnabled;
	}

	public boolean isOptionMenuOverlay() {
		return optionMenuOverlay;
	}

	public void setOptionMenuOverlay(boolean optionMenuOverlay) {
		this.optionMenuOverlay = optionMenuOverlay;
	}

	public boolean isMoveable() {
		return moveable;
	}

	public void setMoveable(boolean moveable) {
		this.moveable = moveable;
	}

//	private void displayComponent(final JComponent component) {
//		component.setOpaque(true);
//		new Thread(new Runnable() {
//
//			@Override
//			public void run() {
//				Color cB = null;
//				Color cF = null;
//				int alpha = 0;
//				for (int i = alpha; i < 256; i += 8) {
//					cB = new Color(component.getBackground().getRed(), component.getBackground().getGreen(), component.getBackground().getBlue(), i);
//					cF = new Color(component.getForeground().getRed(), component.getForeground().getGreen(), component.getForeground().getBlue(), i);
//					component.setBackground(cB);
//					component.setForeground(cF);
//					// System.out.println("alpha set to: " + i);
//					component.repaint();
//					try {
//						Thread.sleep(10);
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//				}
//
//			}
//		}).start();
//	}

}
