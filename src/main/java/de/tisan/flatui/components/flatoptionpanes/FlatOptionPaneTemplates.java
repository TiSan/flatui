package de.tisan.flatui.components.flatoptionpanes;

import de.tisan.flatui.components.ficon.FlatIcon;

public class FlatOptionPaneTemplates {

	public static FlatOptionPaneButton createYesButton() {
		return createYesButton("Yes");
	}

	public static FlatOptionPaneButton createYesButton(String label) {
		return createYesButton(label, null);
	}

	public static FlatOptionPaneButton createYesButton(String label, FlatIcon icon) {
		FlatOptionPaneButton btn = new FlatOptionPaneButton(label, icon, null);
		btn.setAutoSize(true);
		return btn;
	}

	public static FlatOptionPaneButton createNoButton() {
		return createNoButton("No");
	}

	public static FlatOptionPaneButton createNoButton(String label) {
		return createNoButton(label, null);
	}

	public static FlatOptionPaneButton createNoButton(String label, FlatIcon icon) {
		FlatOptionPaneButton btn = new FlatOptionPaneButton(label, icon, null);
		btn.setAutoSize(true);
		return btn;
	}

	public static FlatOptionPaneButton createCancelButton() {
		return createCancelButton("Cancel");
	}

	public static FlatOptionPaneButton createCancelButton(String label) {
		return createCancelButton(label, null);
	}

	public static FlatOptionPaneButton createCancelButton(String label, FlatIcon icon) {
		FlatOptionPaneButton btn = new FlatOptionPaneButton(label, icon, null);
		btn.setAutoSize(true);
		return btn;
	}
}
