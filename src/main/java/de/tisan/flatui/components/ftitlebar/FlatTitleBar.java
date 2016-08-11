package de.tisan.flatui.components.ftitlebar;

import java.awt.Color;
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

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import de.tisan.flatui.components.fcommons.FlatColors;
import de.tisan.flatui.components.fcommons.FlatComponent;
import de.tisan.flatui.components.fcommons.FlatLayoutManager;
import de.tisan.flatui.components.fcommons.FlatUI;
import de.tisan.flatui.components.ffont.FlatFont;

/**
 * Paints a stylish title bar to your upper window corner. setUndecorated(true)
 * is recommended. Use a FlatTitleBarListener for interaction with buttons.
 * 
 * @author TiSan
 *
 */
public class FlatTitleBar extends FlatComponent {
	private static final long serialVersionUID = -8772478458235629389L;
	private ImageIcon icoImg;
	private String text;
	private boolean closeable = true;
	private boolean maximizable = true;
	private boolean hideable = true;
	private Color foreground;
	private Color backgroundClose;
	private Color backgroundMax;
	private Color backgroundMin;
	private Color backgroundCloseOrigin;
	private Color backgroundMaxOrigin;
	private Color backgroundMinOrigin;
	private boolean closePressed;
	private boolean maxPressed;
	private boolean minPressed;
	private ArrayList<FlatTitleBarListener> listeners = new ArrayList<FlatTitleBarListener>();
	private JFrame frame;
	private boolean mouseDown = false;
	private Point lastMousePoint = null;
	private Font font;
	/**
	 * Creates a titlebar without an icon.
	 * 
	 * @param man
	 *            FlatLayoutManager instance
	 * @param text
	 *            Window title text
	 */
	public FlatTitleBar(FlatLayoutManager man, String text) {
		super(man);
		frame = (JFrame) SwingUtilities.getWindowAncestor(this);
		man.addComponent(this);
		if(man.getContentPane() != null)
		man.getContentPane().add(this, 0);
		background = Color.black;
		effects = false;
		this.backOrigin = new Color(background.getRed(), background.getGreen(), background.getBlue());
		this.text = text;
		this.backgroundClose = Color.black;
		this.backgroundCloseOrigin = FlatColors.ALIZARINRED;
		this.backgroundMax = Color.black;
		this.backgroundMaxOrigin = FlatColors.LIGHTBLUE;
		this.backgroundMin = Color.black;
		this.backgroundMinOrigin = FlatColors.LIGHTBLUE;
		this.foreground = FlatColors.WHITE;
		this.font = FlatFont.getInstance(20, Font.PLAIN);
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
				frame = (JFrame) SwingUtilities.getWindowAncestor(FlatTitleBar.this);
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

	/**
	 * Creates a new title bar with an icon in the upper left corner.
	 * 
	 * @param man
	 *            FlatLayoutManager
	 * @param ico
	 *            The Image to show
	 * @param text
	 *            Window title
	 */
	public FlatTitleBar(FlatLayoutManager man, ImageIcon ico, String text) {
		this(man, text);
		this.icoImg = ico;
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
		int textWidth = (int) (rect.getWidth());
		int panelHeight = this.getHeight();
		int panelWidth = this.getWidth();
		int x = (panelWidth - textWidth) / 2;
		int y = (panelHeight - textHeight) / 2 + fm.getAscent();
		g.drawString(text, x, y);
		if (icoImg != null) {
			if (icoImg.getIconHeight() > 30 || icoImg.getIconWidth() > 30) {
				return;
			}
			int xImg = (30 - icoImg.getIconWidth()) / 2;
			int yImg = (30 - icoImg.getIconHeight()) / 2;

			g.drawImage(icoImg.getImage(), xImg, yImg, null);
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
				backgroundClose = new Color(0, 0, 0);
				if (closePressed && !mouseDown) {
					for (FlatTitleBarListener l : listeners) {
						l.onCloseButtonReleased();
					}
					closePressed = false;
				}

			}
			backgroundMax = new Color(0, 0, 0);
			backgroundMin = new Color(0, 0, 0);
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
				backgroundMax = new Color(0, 0, 0);
				if (maxPressed && !mouseDown) {
					for (FlatTitleBarListener l : listeners) {
						l.onMaximizeButtonReleased();
					}
					maxPressed = false;
				}
			}
			backgroundMin = new Color(0, 0, 0);
			backgroundClose = new Color(0, 0, 0);
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
				backgroundMin = new Color(0, 0, 0);
				if (minPressed && !mouseDown) {
					for (FlatTitleBarListener l : listeners) {
						l.onMinimizeButtonReleased();
					}
					minPressed = false;
				}

			}
			backgroundMax = new Color(0, 0, 0);
			backgroundClose = new Color(0, 0, 0);
			break;
		default:
			backgroundMax = new Color(0, 0, 0);
			backgroundClose = new Color(0, 0, 0);
			backgroundMin = new Color(0, 0, 0);
			if (mouseDown && !(maxPressed || minPressed || closePressed)) {
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
	 * Returns the icon in the upper right corner (can be null).
	 * 
	 * @return ImageIcon
	 */
	public ImageIcon getImageIcon() {
		return icoImg;
	}

	/**
	 * Replaces the existing image icon with a new one.
	 * 
	 * @param icoImg
	 */
	public void setImageIcon(ImageIcon icoImg) {
		this.icoImg = icoImg;
		repaint();
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

	public ImageIcon getIcoImg() {
		return icoImg;
	}

	public void setIcoImg(ImageIcon icoImg) {
		this.icoImg = icoImg;
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

}
