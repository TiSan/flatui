package de.tisan.flatui.components.fcommons;

/**
 * Main class of the FlatUI components.
 * 
 * @author TiSan
 * 
 */
public class FlatUI {
	public static final String version = "1.0";
	private static boolean dark = true;
	private static boolean fastRendering;
	/**
	 * Returns the current version of flatUI.
	 * 
	 * @return
	 */
	
	public static String getVersion() {
		return version;
	}

	public static boolean isDarkTheme() {
		return dark;
	}

	protected static void setDarkTheme(boolean darkActive) {
		dark = darkActive;
	}
	/**
	 * Is the fast rendering mode of flatUI activated?
	 * @return boolean
	 */
	public static boolean isFastRendering() {
		return fastRendering;
	}
	/**
	 * You can enable the fast rendering mode of flatUI. This mode is disabled by default. 
	 * Moreover it is not recommended to choose this one, but can make a better experience especially 
	 * on slower computers with an older graphics card. Normally the flatUI components are 
	 * rendered with the antialising mode, which slows down the process but increases the smoothing.
	 * Its your decision to enable the rendering mode, but I will give no guarantee for the perfect
	 * functionality. 
	 * @param fastRendering See above.
	 */
	public static void setFastRendering(boolean fastRender) {
		fastRendering = fastRender;
	}
	
	
}
