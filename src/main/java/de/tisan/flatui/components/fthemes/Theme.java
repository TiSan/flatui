package de.tisan.flatui.components.fthemes;

import java.awt.Color;

import de.tisan.flatui.components.fcommons.FlatComponent;

public abstract class Theme {
	private Color background;
	private Color foreground;

	protected Theme(Color foreground, Color background) {
		this.background = background;
		this.foreground = foreground;
	}

	public Color getBackground() {
		return background;
	}

	public Color getForeground() {
		return foreground;
	}

	public abstract void configureComponent(FlatComponent component);
}
