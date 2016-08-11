package de.tisan.flatui.components.fradiobutton;

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
 * A radio button. A FLAT radio button.
 * 
 * @author TiSan
 * 
 */
public class FlatRadioButton extends FlatComponent {

	public boolean checked;
	private static final long serialVersionUID = 7675448035737919900L;
	private String text;
	private Font font;

	private Color backgroundCheck;
	public final FlatRadioButtonGroup group;

	/**
	 * Creates a new FlatRadioButton
	 * 
	 * @param text
	 *            The text.
	 * @param group
	 *            An instance of a existing FlatRadioButtonGroup
	 * @param man
	 *            An instance of the FlatLayoutManager
	 */
	public FlatRadioButton(String text, FlatRadioButtonGroup group, FlatLayoutManager man) {
		super(man);
		man.addComponent(this);
		if(man.getContentPane() != null)
		man.getContentPane().add(this, 0);
		this.checked = false;
		this.text = text;
		this.group = group;
		this.group.addButton(this);
		this.font = FlatFont.getInstance();
		this.background = FlatColors.HIGHLIGHTBACKGROUND;
		this.backOrigin = new Color(background.getRed(), background.getGreen(), background.getBlue());
		this.backgroundCheck = FlatColors.HIGHLIGHTBACKGROUND.brighter();

	}

	/**
	 * Returns the used radio button group.
	 * 
	 * @return
	 */
	public FlatRadioButtonGroup getFlatRadioButtonGroup() {
		return this.group;
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
			g2.fillOval(7, 7, 7, 7);
		}
		g2.setColor(FlatColors.WHITE);
		g2.setFont(font);
		FontMetrics fm = g2.getFontMetrics(font);
		int x = 22;
		int y = fm.getAscent() - 2;
		g2.drawString(text, x, y);

	}

	/**
	 * Sets this radio button as the checked one.
	 * 
	 * @param checked
	 */
	public void setChecked(boolean checked) {
		group.uncheckAll(this);
		this.checked = checked;
		repaint();
	}

	/**
	 * Is this button checked?
	 * 
	 * @return
	 */
	public boolean isChecked() {
		return this.checked;
	}

	/**
	 * Returns the text
	 * 
	 * @return Text
	 */
	public String getText() {
		return this.text;

	}

	/**
	 * Changes the text
	 * 
	 * @param text
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Changes background of square check
	 * 
	 * @param color
	 */
	public void setBackgroundCheck(Color color) {
		this.backgroundCheck = color;
	}

	/**
	 * Returns background of square check
	 * 
	 * @param color
	 */
	public Color getBackgroundCheck() {
		return this.backgroundCheck;
	}

	@Override
	public void setFont(Font font) {
		this.font = font;
	}

	@Override
	public Font getFont() {
		return this.font;
	}
}
