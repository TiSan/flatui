package de.tisan.flatui.components.ftilemenu;

public enum FlatTileSize {
	MIDDLE(120, 120), WIDE(250, 120), BIG(250, 250);

	private int width;
	private int height;

	private FlatTileSize(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

}
