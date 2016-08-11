package de.tisan.flatui.components.flatoptionpanes;

import javax.swing.ImageIcon;

import de.tisan.flatui.components.fbutton.FlatButton;
import de.tisan.flatui.components.fcommons.FlatLayoutManager;
import de.tisan.flatui.components.ficon.FlatIcon;

public class FlatOptionPaneButton extends FlatButton{

	private static final long serialVersionUID = 4388577991622673756L;
	public FlatOptionPaneButton(String text, ImageIcon imgIcon, FlatLayoutManager man) {
		super(text, imgIcon, man);
	}

	public FlatOptionPaneButton(String text, FlatLayoutManager man) {
		super(text, man);
	}
	public FlatOptionPaneButton(String text, FlatIcon icon, FlatLayoutManager man) {
		super(text, icon, man);
	}
	

}
