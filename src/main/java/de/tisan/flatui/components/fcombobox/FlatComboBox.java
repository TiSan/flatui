package de.tisan.flatui.components.fcombobox;

import de.tisan.flatui.components.fcommons.FlatComponent;
import de.tisan.flatui.components.fcommons.FlatLayoutManager;

/**
 * Dummy, not coded yet.
 * 
 * @author Hendrik
 * 
 */
class FlatComboBox extends FlatComponent {
	protected FlatComboBox(FlatLayoutManager man) {
		super(man);
		// TODO Auto-generated constructor stub
	}

	//
	private static final long serialVersionUID = 1200471938453536412L;
	// private String text;
	// private ArrayList<String> values;
	// private Font font;
	// private boolean blinky;
	// private boolean requestBlinky;
	//
	// private FlatComboBox(FlatLayoutManager man) {
	// man.addComponent(this);
	// this.values = new ArrayList<String>();
	// this.background = new Color(0,0,0);
	// this.backOrigin = new Color(background.getRed(), background.getGreen(),
	// background.getBlue());
	// this.font = FlatFont.getInstance();
	// this.text = "";
	// this.blinky = true;
	// addKeyListener(new KeyListener() {
	//
	// @Override
	// public void keyPressed(KeyEvent arg0) {
	// }
	//
	// @Override
	// public void keyReleased(KeyEvent arg0) {
	// }
	//
	// @Override
	// public void keyTyped(KeyEvent arg0) {
	// int temp = (int) arg0.getKeyChar();
	//
	// if (temp == 8) {
	// if (text.length() > 1) {
	// text = text.substring(0, text.length() - 1);
	// } else if (text.length() <= 1) {
	// text = "";
	// }
	//
	// } else {
	// if (temp > 20) {
	// text += arg0.getKeyChar();
	// }
	//
	// }
	// repaint();
	// }
	//
	// });
	// new Thread(new Runnable() {
	//
	// @Override
	// public void run() {
	// while (true) {
	// if (hasFocus() || requestBlinky) {
	// requestBlinky = true;
	// try {
	// Thread.sleep(500);
	// } catch (InterruptedException e) {
	// e.printStackTrace();
	// }
	// blinky = !blinky;
	// repaint();
	//
	// }
	// }
	//
	// }
	//
	// }).start();
	// }
	//
	// public String getText() {
	// return text;
	// }
	//
	// public void setText(String text) {
	// this.text = text;
	// }
	//
	// @Override
	// public void setFont(Font font) {
	// this.font = font;
	// }
	//
	// @Override
	// public Font getFont() {
	// return this.font;
	// }
	//
	// @Override
	// public void paintComponent(Graphics g) {
	// super.paintComponent(g);
	// g.setColor(FlatColors.basicHighlightBackground);
	// g.fillRect(0, 0, getBounds().getSize().width,
	// getBounds().getSize().height);
	// g.setColor(FlatColors.basicWhite);
	// g.setFont(font);
	// FontMetrics fm = g.getFontMetrics(font);
	// java.awt.geom.Rectangle2D rect = fm.getStringBounds(text, g);
	// int textHeight = (int) (rect.getHeight());
	// int textWidth = (int) (rect.getWidth());
	// int panelHeight = this.getHeight();
	// int panelWidth = this.getWidth();
	// int x = 10;
	// boolean extra = false;
	// if (textWidth >= panelWidth - 15) {
	// x = panelWidth - textWidth - 10;
	// extra = true;
	// }
	// int y = (panelHeight - textHeight) / 2 + fm.getAscent() - 3;
	// g.drawString(text, x, y);
	// if (blinky) {
	//
	// g.setColor(FlatColors.basicWhite);
	// if (!extra) {
	// g.drawRect(textWidth + 12, (panelHeight - textHeight) / 2, 1,
	// (textHeight));
	// } else {
	// g.drawRect(panelWidth - 5, (panelHeight - textHeight) / 2 + 2,
	// 1, (textHeight));
	// }
	//
	// }
	//
	// }
	//
	// @Override
	// public Color getBackground() {
	// // TODO Auto-generated method stub
	// return null;
	// }
	//
	// @Override
	// public void setBackground(Color background) {
	// // TODO Auto-generated method stub
	//
	// }

}
