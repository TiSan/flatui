package de.tisan.flatui.components.fsidemenu;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JPanel;

import de.tisan.flatui.components.fcommons.FlatColors;
import de.tisan.flatui.components.ficon.FlatIcon;
import de.tisan.flatui.components.ficon.FlatIconFont;

public class FlatSideMenuEntry {

	private String name;
	private Color foreground;
	private Color highlightForeground;
	private Color background;
	private ArrayList<FlatSideMenuEntry> subEntries;
	private JPanel contentPane;
	private ArrayList<FlatSideMenuListener> listeners;
	private FlatSideMenu menu;
	private boolean selected;
	private FlatIcon icon;
	private FlatSideMenuEntry upperMenu;

	public FlatSideMenuEntry(String name, JPanel contentPane, FlatSideMenu menu) {
		this.name = name;
		this.menu = menu;
		this.contentPane = contentPane;
		this.foreground = FlatColors.WHITE;
		this.highlightForeground = FlatColors.BLUE;
		this.background = FlatColors.BACKGROUND.brighter().brighter();
		this.subEntries = new ArrayList<FlatSideMenuEntry>();
		this.listeners = new ArrayList<FlatSideMenuListener>();
		addListener(new FlatSideMenuListener() {

			@Override
			public void onClick() {
				menu.goIntoEntry(FlatSideMenuEntry.this, false);
			}
		});
	}

	public boolean isSelected() {
		return selected;
	}

	public FlatIcon getIcon() {
		return icon;
	}

	public FlatSideMenuEntry getUpperMenu() {
		return upperMenu;
	}

	public void setUpperMenu(FlatSideMenuEntry upperMenu) {
		this.upperMenu = upperMenu;
	}

	public void setIcon(FlatIcon icon) {
		this.icon = icon;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public FlatSideMenu getMenu() {
		return menu;
	}

	public Color getBackground() {
		return background;
	}

	public void setBackground(Color background) {
		this.background = background;
	}

	public void addEntry(FlatSideMenuEntry entry) {
		if (subEntries.size() == 0) {
			FlatSideMenuEntry gotoUpper = new FlatSideMenuEntry("", null, menu);
			gotoUpper.setIcon(FlatIconFont.ANGLE_LEFT);
			subEntries.add(gotoUpper);
			gotoUpper.addListener(new FlatSideMenuListener() {

				@Override
				public void onClick() {
					menu.goIntoEntry(FlatSideMenuEntry.this, true);
				}
			});
		}
		this.subEntries.add(entry);
		entry.setUpperMenu(this);

	}

	public ArrayList<FlatSideMenuEntry> getSubEntries() {
		return subEntries;
	}

	public JPanel getContentPane() {
		return contentPane;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Color getForeground() {
		return foreground;
	}

	public void setForeground(Color foreground) {
		this.foreground = foreground;
	}

	public Color getHighlightForeground() {
		return highlightForeground;
	}

	public void setHighlightForeground(Color highlightForeground) {
		this.highlightForeground = highlightForeground;
	}

	public void addListener(FlatSideMenuListener listener) {
		this.listeners.add(listener);
	}

	@SuppressWarnings("unchecked")
	public ArrayList<FlatSideMenuListener> getListeners() {
		return (ArrayList<FlatSideMenuListener>) listeners.clone();
	}

	public void setContentPane(JPanel contentPane) {
		this.contentPane = contentPane;
	}

}
