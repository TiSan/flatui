package de.tisan.flatui.components.fmenu;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import de.tisan.flatui.components.fcommons.FlatColors;
import de.tisan.flatui.components.fcommons.FlatComponent;
import de.tisan.flatui.components.fcommons.FlatUI;
import de.tisan.flatui.components.ffont.FlatFont;
import de.tisan.flatui.components.ficon.FlatIcon;
import de.tisan.flatui.components.ficon.FlatIconFont;

/**
 * A FlatMenu item.
 * 
 * @author TiSan
 * 
 */
public class FlatMenuPoint extends FlatComponent {
	private String text;
	private Color foreground;
	private Font font;
	private FlatIcon icon_value;

	public FlatMenuPoint(String text) {
		super(null);
		this.text = text;
		this.foreground = FlatColors.LIGHTWHITE;
		this.font = FlatFont.getInstance();
	}

	public FlatMenuPoint(String text, FlatIcon icon) {
		super(null);
		this.text = text;
		this.foreground = FlatColors.LIGHTWHITE;
		this.icon_value = icon;
		this.font = FlatFont.getInstance();
	}

	public Color getForeground() {
		return this.foreground;
	}

	public void setForeground(Color color) {
		this.foreground = color;
	}

	public Font getFont() {
		return this.font;
	}

	public void setFont(Font font) {
		this.font = font;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	private static final long serialVersionUID = -5802070208807073765L;

	public void paintComponent(Graphics gr) {
		Graphics2D g = (Graphics2D) gr;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, !FlatUI.isFastRendering() ? RenderingHints.VALUE_ANTIALIAS_ON : RenderingHints.VALUE_ANTIALIAS_OFF);
		g.setColor(background);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
//		g.setColor(background.brighter());
//		g.fillRect(0, 0, this.getWidth(), 5);
//		g.setColor(background.darker());
//		g.fillRect(0, this.getHeight() - 5, this.getWidth(), 5);
		int amplX = 0;
		if (icon_value != null) {
			amplX = 40;
			g.setColor(this.getForeground());
			g.setFont(FlatIconFont.getInstance());
			FontMetrics fm = g.getFontMetrics(FlatIconFont.getInstance());
			java.awt.geom.Rectangle2D rect = fm.getStringBounds(icon_value.getValue(), g);
			int textHeight = (int) (rect.getHeight());
			int panelHeight = this.getHeight();
			int x = 10;
			int y = (panelHeight - textHeight) / 2 + fm.getAscent();
			g.drawString(icon_value.getValue(), x, y);
		}
		g.setColor(this.getForeground());
		g.setFont(this.getFont());
		FontMetrics fm = g.getFontMetrics(this.getFont());
		java.awt.geom.Rectangle2D rect = fm.getStringBounds(this.getText(), g);
		int textHeight = (int) (rect.getHeight());
		int textWidth = (int) (rect.getWidth());
		int panelHeight = this.getHeight();
		int panelWidth = this.getWidth();
		int x = 0;
		if (icon_value != null) {
			x = amplX;
		} else {
			x = (panelWidth - textWidth) / 2;
		}
		int y = (panelHeight - textHeight) / 2 + fm.getAscent();
		g.drawString(this.getText(), x, y);
	}
}
