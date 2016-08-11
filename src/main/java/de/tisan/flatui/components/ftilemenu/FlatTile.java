package de.tisan.flatui.components.ftilemenu;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import de.tisan.flatui.components.fcommons.FlatColors;
import de.tisan.flatui.components.ffont.FlatFont;
import de.tisan.flatui.components.ficon.FlatIcon;

public class FlatTile {
	private Color background;
	private String name;
	private FlatIcon icon;
	private FlatTileSize size;
	private BufferedImage image;
	private int iconSize;
	private Color foreground;
	private Font font;
	protected boolean pressed;
	protected boolean pressedLeft;
	protected boolean pressedRight;
	protected boolean pressedUp;
	protected boolean pressedDown;
	protected boolean pressedCenter;
	protected boolean dragged;
	protected int internalX;
	protected int internalY;
	private ArrayList<FlatTileListener> listeners;

	public FlatTile() {
		this.listeners = new ArrayList<FlatTileListener>();
		this.background = FlatColors.ALIZARINRED;
		this.name = "" + System.currentTimeMillis();
		this.icon = null;
		this.size = FlatTileSize.MIDDLE;
		this.image = null;
		this.font = FlatFont.getInstance(11, Font.PLAIN);
		this.iconSize = 64;
		this.foreground = FlatColors.WHITE;

	}

	public void addListener(FlatTileListener l) {
		this.listeners.add(l);
	}

	protected void pushClick() {
		for (FlatTileListener l : listeners) {
			l.onClick();
		}
	}

	protected void pushDrag() {
		for (FlatTileListener l : listeners) {
			l.onDrag();
		}
	}

	public Color getBackground() {
		return background;
	}

	public void setBackground(Color background) {
		this.background = background;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public FlatIcon getIcon() {
		return icon;
	}

	public void setIcon(FlatIcon icon) {
		this.icon = icon;
	}

	public FlatTileSize getSize() {
		return size;
	}

	public void setSize(FlatTileSize size) {
		this.size = size;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public int getIconSize() {
		return iconSize;
	}

	public void setIconSize(int iconSize) {
		this.iconSize = iconSize;
	}

	public Color getForeground() {
		return foreground;
	}

	public void setForeground(Color foreground) {
		this.foreground = foreground;
	}

	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		this.font = font;
	}

}
