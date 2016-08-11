package de.tisan.flatui.components.ffont;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;

/**
 * Returns an instance of the flatFont.
 * 
 * @author TiSan
 * 
 */
public class FlatFont extends Font {
	private static final long serialVersionUID = 4199006941865732937L;
	private static HashMap<String, Font> fonts = new HashMap<>();

	private FlatFont(File file) throws FontFormatException, IOException {

		super(Font.createFont(Font.TRUETYPE_FONT, file));
		// performCleanUp();
		GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(this);
	}

	/**
	 * Returns the current instance of FlatFont.
	 * 
	 * @return current font instance
	 */
	public static Font getInstance() {
		// return new Font("Arial", 12, Font.PLAIN);
		return getInstance(17, Font.PLAIN);
	}

	/**
	 * Returns the current instance of FlatFont and sets a specific font size.
	 * 
	 * @param size
	 *            font size
	 * @return current font instance
	 */
	public static Font getInstance(int size, int style) {
		// return new Font("Arial", 12, Font.PLAIN);

		try {
			String s = size + "";
			if (fonts.containsKey(s)) {
				return fonts.get(s);
			}
			String tmpPath = System.getProperty("java.io.tmpdir");
			if(tmpPath.length() > 0){
				tmpPath += "/";
			}
			File f = new File(tmpPath + "FlatUI/");
			if (!f.exists()) {
				f.mkdirs();
			}
			File fi = new File(tmpPath + "FlatUI/osl.ttf");
			if (!fi.exists()) {
				Files.copy(FlatFont.class.getResourceAsStream("/de/tisan/resources/osl.ttf"), fi.toPath());
			}
			Font ff = new FlatFont(fi);
			Font fon = ff.deriveFont(style, size);
			fonts.put(s, fon);
			return fon;
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
