package de.tisan.flatui.components.fradiobutton;

import java.util.ArrayList;

/**
 * A group for multiple radio buttons. Not visible on screen.
 * 
 * @author TiSan
 * 
 */
public class FlatRadioButtonGroup {
	private ArrayList<FlatRadioButton> buttons;

	/**
	 * Creates a FlatRadioButtonGroup
	 */
	public FlatRadioButtonGroup() {
		this.buttons = new ArrayList<FlatRadioButton>();
	}

	protected void addButton(FlatRadioButton button) {
		this.buttons.add(button);
	}

	public void uncheckAll(FlatRadioButton except) {
		for (int i = 0; i < buttons.size(); i++) {
			if (!buttons.get(i).equals(except)) {
				buttons.get(i).checked = false;
				buttons.get(i).repaint();
			}
		}
	}

	public void uncheckAll() {
		for (int i = 0; i < buttons.size(); i++) {
			buttons.get(i).checked = false;
			buttons.get(i).repaint();
		}
	}

	/**
	 * Returns the current checked FlatRadioButton
	 * 
	 * @return current checked FlatRadioButton
	 */
	public FlatRadioButton getCheckedRadioButton() {
		for (int i = 0; i < buttons.size(); i++) {
			if (buttons.get(i).isChecked()) {
				return buttons.get(i);
			}
		}
		return null;
	}
}
