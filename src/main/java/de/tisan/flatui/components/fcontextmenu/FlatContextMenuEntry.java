package de.tisan.flatui.components.fcontextmenu;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Arrays;

import de.tisan.flatui.components.ficon.FlatIcon;
/**
 * Displays an entry of the context menu
 * @author TiSan
 *
 */
public class FlatContextMenuEntry {
	private String name;
	private FlatContextMenuEntryType type;
	private ArrayList<FlatContextMenuEntry> subEntries;
	private ArrayList<FlatContextMenuEntryListener> listeners;
	private Image icon;
	private FlatIcon flatIcon;
	private boolean checked;

	public FlatContextMenuEntry(String name, FlatContextMenuEntryType type, FlatContextMenuEntry... subEntries) {
		this.type = type;
		this.name = name;
		this.subEntries = new ArrayList<FlatContextMenuEntry>(Arrays.asList(subEntries));
		this.listeners = new ArrayList<FlatContextMenuEntryListener>();
		this.checked = false;
	}

	public FlatContextMenuEntry(String name, FlatContextMenuEntryType type, Image image, FlatContextMenuEntry... subEntries) {
		this(name, type, subEntries);
		this.icon = image;
	}

	public FlatContextMenuEntry(String name, FlatContextMenuEntryType type, FlatIcon icon, FlatContextMenuEntry... subEntries) {
		this(name, type, subEntries);
		this.flatIcon = icon;
	}

	public String getName() {
		return name;
	}

	public FlatContextMenuEntryType getType() {
		return type;
	}

	public ArrayList<FlatContextMenuEntry> getSubEntries() {
		return subEntries;
	}

	public void addSubEntry(FlatContextMenuEntry entry) {
		this.subEntries.add(entry);
	}

	public boolean containsSubEntries() {
		return (this.subEntries != null && this.subEntries.size() > 0 ? true : false);
	}

	public void addListener(FlatContextMenuEntryListener listener) {
		this.listeners.add(listener);
	}

	protected ArrayList<FlatContextMenuEntryListener> getListeners() {
		return this.listeners;
	}

	public Image getIcon() {
		return icon;
	}

	public FlatIcon getFlatIcon() {
		return flatIcon;
	}

	public boolean isChecked() {
		return checked;
	}

	protected void setChecked(boolean checked){
		this.checked = checked;
	}
}