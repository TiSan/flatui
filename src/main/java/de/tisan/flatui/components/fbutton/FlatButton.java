package de.tisan.flatui.components.fbutton;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import de.tisan.flatui.components.fcommons.FlatColors;
import de.tisan.flatui.components.fcommons.FlatComponent;
import de.tisan.flatui.components.fcommons.FlatLayoutManager;
import de.tisan.flatui.components.fcommons.FlatUI;
import de.tisan.flatui.components.ffont.FlatFont;
import de.tisan.flatui.components.ficon.FlatIcon;
import de.tisan.flatui.components.ficon.FlatIconFont;
import de.tisan.flatui.components.flisteners.FlatButtonSizeChangeListener;

/**
 * A stylistic button.
 * 
 * @author TiSan
 * 
 */
public class FlatButton extends FlatComponent {

	private static final long serialVersionUID = -2500141177947420554L;
	private String text;
	private Color foreground;
	private Font font;
	private FlatIcon icon;
	private ImageIcon imgIcon;
	private Font iconFont;
	private boolean autoSize;
	private ArrayList<FlatButtonSizeChangeListener> listeners;

	/**
	 * Creates a button with a text.
	 * 
	 * @param text
	 *            Text that should show onto the button, "" if empty
	 */
	public FlatButton(String text, FlatLayoutManager man) {
		super(man);
		if (man != null) {
			man.addComponent(this);
			if (man.getContentPane() != null)
				man.getContentPane().add(this, 0);
		}
		setSize(1, 1);
		this.text = text;
		this.foreground = FlatColors.WHITE;
		this.background = FlatColors.BLUE;
		this.backOrigin = new Color(background.getRed(), background.getGreen(), background.getBlue());
		this.font = FlatFont.getInstance();
		this.iconFont = FlatIconFont.getInstance();
		this.listeners = new ArrayList<FlatButtonSizeChangeListener>();

	}

	public FlatButton(String text, FlatIcon icon, FlatLayoutManager man) {
		this(text, man);
		this.icon = icon;
	}

	public FlatButton(String text, ImageIcon imgIcon, FlatLayoutManager man) {
		this(text, man);
		this.imgIcon = imgIcon;
	}

	@Override
	public void paintComponent(Graphics gr) {
		super.paintComponent(gr);
		Graphics2D g = (Graphics2D) gr;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, !FlatUI.isFastRendering() ? RenderingHints.VALUE_ANTIALIAS_ON : RenderingHints.VALUE_ANTIALIAS_OFF);
		g.setColor(background);
		g.fillRect(0, 0, (int) getBounds().getSize().getWidth(), (int) getBounds().getSize().getHeight());
		g.setColor(foreground);
		g.setFont(font);
		int amplX = 0;
		int width = 5;
		int height = 5;
		if (icon != null) {
			amplX = 10;
			g.setColor(this.getForeground());
			g.setFont(iconFont);
			FontMetrics fm1 = g.getFontMetrics(iconFont);
			java.awt.geom.Rectangle2D rect1 = fm1.getStringBounds(icon.getValue(), g);
			int textHeight1 = (int) (rect1.getHeight());
			int panelHeight1 = this.getHeight();
			if (textHeight1 > height) {
				height = textHeight1;
			}
			width += rect1.getWidth();
			int x1 = 18;
			if (text == null) {
				x1 = (this.getWidth() / 2 - (int) rect1.getWidth() / 2) + fm1.getLeading();
			}
			int y1 = (panelHeight1 - textHeight1) / 2 + fm1.getAscent();
			g.drawString(icon.getValue(), x1, y1);
		} else if (imgIcon != null) {
			amplX = 10;
			g.drawImage(imgIcon.getImage(), amplX + (imgIcon.getIconWidth() / 4), ((getHeight() / 2) - imgIcon.getIconHeight() / 2), null);

			if (imgIcon.getIconHeight() > height) {
				height = imgIcon.getIconHeight();
			}
			width += imgIcon.getIconWidth();
		}
		if (text != null) {

			FontMetrics fm = g.getFontMetrics(font);
			java.awt.geom.Rectangle2D rect = fm.getStringBounds(text, g);
			int textHeight = (int) (rect.getHeight());
			int textWidth = (int) (rect.getWidth());
			int panelHeight = this.getHeight();
			int panelWidth = this.getWidth();
			if (textHeight > height) {
				height = textHeight;
			}
			width += textWidth + amplX;
			int x = (panelWidth - textWidth) / 2;
			int y = (panelHeight - textHeight) / 2 + fm.getAscent();
			x = (panelWidth - textWidth) / 2 + amplX;

			g.setFont(this.getFont());
			g.drawString(text, x, y);
		}
		if (autoSize) {
			if (getSize().width != width || getSize().height != height) {
				setSize(width + 20, height + 5);
				for (FlatButtonSizeChangeListener l : listeners) {
					l.onSizeChange();
				}

			}

		}
	}

	/**
	 * Returns the current text of the button
	 * 
	 * @return Label of Button
	 */
	public String getText() {
		return this.text;
	}

	/**
	 * Changes the text of the button
	 * 
	 * @param txt
	 *            New text of the button
	 */
	public void setText(String txt) {
		this.text = txt;
		repaint();
	}

	/**
	 * Sets the foreground color of this object.
	 * 
	 * @param color
	 *            A normal object of a Color (look at FlatColors for cool
	 *            colors)
	 */
	@Override
	public void setForeground(Color color) {
		this.foreground = color;
	}

	/**
	 * Gets the current Foreground color
	 */
	@Override
	public Color getForeground() {
		return this.foreground;
	}

	/**
	 * Updates the Font of this object.
	 * 
	 * @param font
	 *            The new Font object
	 */
	@Override
	public void setFont(Font font) {
		this.font = font;
	}

	/**
	 * Gets the current Font of this object
	 */
	@Override
	public Font getFont() {
		return this.font;
	}

	/**
	 * Adds a new FlatButtonListener
	 * 
	 * @param listener
	 */
	public void addFlatButtonListener(FlatButtonListener listener) {
		addMouseListener(listener);
	}

	/**
	 * Allows to add a layout manager after creation of the button (some
	 * szenarios does need that)
	 * 
	 * @param man
	 */
	public void implementLayoutManager(FlatLayoutManager man) {
		man.addComponent(this);
	}

	/**
	 * Returns the current used icon font
	 * 
	 * @return
	 */
	public Font getIconFont() {
		return iconFont;
	}

	/**
	 * Sets the icon font
	 * 
	 * @param iconFont
	 */
	public void setIconFont(Font iconFont) {
		this.iconFont = iconFont;
	}

	/**
	 * Should the FlatButton autosize itself?
	 * 
	 * @param autoSize
	 */
	public void setAutoSize(boolean autoSize) {
		this.autoSize = autoSize;

	}

	/**
	 * Is the button autoresizing itself?
	 * 
	 * @return
	 */
	public boolean isAutoSize() {
		return autoSize;
	}

	/**
	 * Adds a listener that would be invoked if the button resizes itself
	 * 
	 * @param l
	 */
	public void addListener(FlatButtonSizeChangeListener l) {
		listeners.add(l);
	}
}
