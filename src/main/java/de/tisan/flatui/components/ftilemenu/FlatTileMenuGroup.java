package de.tisan.flatui.components.ftilemenu;

import java.util.ArrayList;

public class FlatTileMenuGroup {
	private ArrayList<FlatTile> tiles;
	private String name;

	public FlatTileMenuGroup(String name) {
		this.name = name;
		this.tiles = new ArrayList<>();
	}

	public ArrayList<FlatTile> getTiles() {
		return tiles;
	}

	public void addTile(FlatTile tile) {
		this.tiles.add(tile);
	}

	public void setTiles(ArrayList<FlatTile> tiles) {
		this.tiles = tiles;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
