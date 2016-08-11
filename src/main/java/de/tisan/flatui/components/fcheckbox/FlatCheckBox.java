package de.tisan.flatui.components.fcheckbox;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import de.tisan.flatui.components.fcommons.FlatColors;
import de.tisan.flatui.components.fcommons.FlatComponent;
import de.tisan.flatui.components.fcommons.FlatLayoutManager;
import de.tisan.flatui.components.fcommons.FlatUI;
import de.tisan.flatui.components.ffont.FlatFont;

/**
 * A checkbox in flatUI style.
 * 
 * @author TiSan
 *
 */
public class FlatCheckBox extends FlatComponent {

	private boolean checked;
	private static final long serialVersionUID = 7675448035737919900L;
	private String text;
	private Font font;
	private Color backgroundCheck;

	/**
	 * Creates a FlatCheckBox
	 * 
	 * @param text
	 *            The Text of the CheckBox
	 */
	public FlatCheckBox(String text, FlatLayoutManager man) {
		super(man);
		man.addComponent(this);
		if (man.getContentPane() != null)
			man.getContentPane().add(this, 0);
		this.checked = false;
		this.text = text;
		this.font = FlatFont.getInstance();
		this.background = FlatColors.HIGHLIGHTBACKGROUND;
		this.backOrigin = new Color(background.getRed(), background.getGreen(), background.getBlue());
		this.backgroundCheck = FlatColors.HIGHLIGHTBACKGROUND.brighter();

	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(2));
		super.paintComponent(g2);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, !FlatUI.isFastRendering() ? RenderingHints.VALUE_ANTIALIAS_ON : RenderingHints.VALUE_ANTIALIAS_OFF);
		g2.setColor(background);

		g2.fillRect(0, 0, getBounds().getSize().width, getBounds().getSize().height);
		g2.setColor(background.brighter());
		g2.fillRect(0, 0, 20, 20);
		if (checked) {
			g2.setColor(FlatColors.GREEN);
			g2.drawLine(3, 9, 9, 16);
			g2.drawLine(9, 16, 16, 3);
		}
		g2.setColor(FlatColors.WHITE);
		g2.setFont(font);
		FontMetrics fm = g2.getFontMetrics(font);
		int x = 22;
		int y = fm.getAscent() - 2;
		g2.drawString(text, x, y);

	}

	/**
	 * Changes the Checkbox Checked-State
	 * 
	 * @param checked
	 */
	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	/**
	 * Is the checkbox checked?
	 * 
	 * @return
	 */
	public boolean isChecked() {
		return this.checked;
	}

	/**
	 * Returns the label text
	 * 
	 * @return
	 */
	public String getText() {
		return this.text;
	}

	/**
	 * Sets the label text
	 * 
	 * @param text
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Changes the background of the checkbox
	 * 
	 * @param color
	 */
	public void setBackgroundCheck(Color color) {
		this.backgroundCheck = color;
	}

	/**
	 * Returns the background of the checkbox
	 * 
	 * @return
	 */
	public Color getBackgroundCheck() {
		return this.backgroundCheck;
	}

	/**
	 * Sets the font of the checkbox
	 */
	@Override
	public void setFont(Font font) {
		this.font = font;
	}

	/**
	 * Returns the font of the checkbox
	 */
	@Override
	public Font getFont() {
		return this.font;
	}
}
