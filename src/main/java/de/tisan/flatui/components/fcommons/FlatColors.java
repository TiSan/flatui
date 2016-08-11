package de.tisan.flatui.components.fcommons;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * A collection of different flat colors.
 * 
 * @author TiSan
 * 
 */
public interface FlatColors {
	public static final Color WHITE = new Color(236, 240, 241);
	// public static final Color BLUE = new Color(41, 128, 185);
	public static final Color BLACK = new Color(0, 0, 0);
	public static final Color BLUE = new Color(0, 122, 204);
	public static final Color LIGHTBLUE = new Color(52, 152, 219);
	public static final Color YELLOW = new Color(241, 196, 15);
	public static final Color ORANGE = new Color(243, 156, 18);
	public static final Color CARROT = new Color(230, 126, 34);
	public static final Color PUMPKIN = new Color(211, 84, 0);
	public static final Color ALIZARINRED = new Color(231, 76, 60);
	public static final Color RED = new Color(192, 57, 43);
	public static final Color SILVER = new Color(189, 195, 199);
	public static final Color GRAY = new Color(149, 165, 166);
	public static final Color DARKERGRAY = new Color(127, 140, 141);
	public static final Color GREEN = new Color(39, 174, 96);
	public static final Color LIME = new Color(46, 204, 113);
	public static final Color TURQUOISE = new Color(26, 188, 156);
	public static final Color DARKTURQUOISE = new Color(22, 160, 133);
	public static final Color PURPLE = new Color(142, 68, 173);
	public static final Color LIGHTPURPLE = new Color(155, 89, 182);
	public static final Color WETASPHALT = new Color(52, 73, 94);
	public static final Color MIDNIGHTBLUE = new Color(44, 62, 80);
	public static final Color MSGBOXBLUETRANSPARENT = new Color(51, 181, 229, 200);
	public static final Color HIGHLIGHTBACKGROUND_OLD = (FlatUI.isDarkTheme() ? new Color(47, 79, 79) : WETASPHALT);
	public static final Color HIGHLIGHTBACKGROUND = (FlatUI.isDarkTheme() ? new Color(47, 47, 50) : WETASPHALT);
	public static final Color FLATUI_ACCENT = new Color(0, 122, 204);
	public static final Color TEXT = (FlatUI.isDarkTheme() ? SILVER : new Color(10, 10, 10));
	public static final Color LIGHTWHITE = new Color(255, 255, 255);
	public static final Color BACKGROUND_OLD = (FlatUI.isDarkTheme() ? new Color(27, 59, 59) : LIGHTWHITE);
	public static final Color BACKGROUND = (FlatUI.isDarkTheme() ? new Color(40, 40, 43) : LIGHTWHITE);

	public static final ArrayList<Color> colors = new ArrayList<Color>(Arrays.asList(WHITE, BACKGROUND, BACKGROUND_OLD, BLUE, LIGHTBLUE, YELLOW, ORANGE, CARROT, PUMPKIN, ALIZARINRED, RED, SILVER, GRAY, DARKERGRAY, GREEN, LIME, TURQUOISE,
			DARKTURQUOISE, PURPLE, LIGHTPURPLE, WETASPHALT, MIDNIGHTBLUE, MSGBOXBLUETRANSPARENT, HIGHLIGHTBACKGROUND, HIGHLIGHTBACKGROUND_OLD, LIGHTWHITE

	));

}
