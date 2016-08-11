package de.tisan.flatui.components.fthemes;

import de.tisan.flatui.components.fcommons.FlatColors;
import de.tisan.flatui.components.fcommons.FlatComponent;

public class DarkTheme extends Theme {
	
	protected DarkTheme() {
		super(FlatColors.TEXT, FlatColors.BACKGROUND);
	}


	@Override
	public void configureComponent(FlatComponent component) {
		component.setBackground(getBackground());
		component.setForeground(getForeground());
	//	if (component instanceof FlatButton) {
	//		FlatButton btn = (FlatButton) component;
	//		
	//	}
	}

}
