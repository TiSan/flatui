package de.tisan.flatui.components.ftextbox;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import de.tisan.flatui.components.fcommons.FlatColors;
import de.tisan.flatui.components.fcommons.FlatComponent;
import de.tisan.flatui.components.fcommons.FlatLayoutManager;
import de.tisan.flatui.components.fcommons.FlatUI;
import de.tisan.flatui.components.fcontextmenu.FlatContextMenu;
import de.tisan.flatui.components.fcontextmenu.FlatContextMenuEntry;
import de.tisan.flatui.components.fcontextmenu.FlatContextMenuEntryListener;
import de.tisan.flatui.components.fcontextmenu.FlatContextMenuEntryType;
import de.tisan.flatui.components.ffont.FlatFont;
import de.tisan.flatui.components.ficon.FlatIconFont;

/**
 * A flat Text box.
 * 
 * @author TiSan
 * 
 */
public class FlatTextBox extends FlatComponent {

	private static final long serialVersionUID = 1200471938453536412L;
	private String text;
	private Font font;
	private boolean blinky;
	private Color foreground;
	private Color foreground_blinky;
	private int position;
	private boolean password;
	private boolean editable;

	/**
	 * Creates a new FlatTextBox whithout password possibility
	 * 
	 * @param man
	 *            an instance of FlatLayoutManager
	 */
	public FlatTextBox(FlatLayoutManager man) {
		super(man);
		man.addComponent(this);
		if(man.getContentPane() != null)	
		man.getContentPane().add(this, 0);
		this.font = FlatFont.getInstance();
		this.text = "";
		this.blinky = false;
		this.editable = false;
		this.position = 0;
		this.background = FlatColors.HIGHLIGHTBACKGROUND;
		this.backOrigin = new Color(background.getRed(), background.getGreen(), background.getBlue());
		this.foreground = FlatColors.WHITE;
		this.foreground_blinky = FlatColors.WHITE;
		this.editable = true;
		addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent arg0) {
				int temp = (int) arg0.getKeyCode();
				// System.out.println(temp);
				if (temp == 37) {
					// Links

					if (position > 0) {
						position--;
					}

					// position = (position > 0 ? position-- : 0);

					repaint();
				} else if (temp == 39) {
					// Rechts

					if (position != text.length()) {
						position++;
					}
					// position = (position < text.length() - 1 ? position++ :
					// text.length());

					repaint();
				}
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				if (editable) {
					int temp = (int) arg0.getKeyChar();
					if (temp == 8) {
						if (text.length() > 1) {
							// text = text.substring(0, text.length() - 1);
							if (position > 0) {
								text = text.substring(0, position - 1) + text.substring(position);
								position--;
							}
						} else if (text.length() <= 1) {
							text = "";
						}

					} else {
						if (temp > 20 && temp != 127) {
							String tmp = text;
							if (!tmp.equals("")) {
								text = tmp.substring(0, position) + arg0.getKeyChar() + ((tmp.length() == position) ? "" : tmp.substring(position));
								// text += arg0.getKeyChar();
								if (text.length() != position) {
									position++;
								}
							} else {
								text = "" + arg0.getKeyChar();
								if (text.length() != position) {
									position++;
								}
							}

						}

					}
				}

				// if(position != text.length()){
				// if(position > 0){
				// position = (text.length() > 0) ? text.length() : 0;
				// }
				//
				// }

				// System.out.println(position);
				repaint();
			}

		});
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					if (hasFocus()) {
						try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
							// e.printStackTrace();
						}
						blinky = !blinky;
						repaint();
					} else {
						blinky = false;
						repaint();
					}
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						// e.printStackTrace();
					}
				}

			}

		});
		t.setPriority(Thread.MAX_PRIORITY);
		t.start();
		StringSelection s = new StringSelection("");
		FlatContextMenuEntry e1 = new FlatContextMenuEntry("Kopieren",  FlatContextMenuEntryType.NORMAL, FlatIconFont.FILES_O);
		e1.addListener(new FlatContextMenuEntryListener() {

			@Override
			public void onClick() {
				Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(getText()), s);
				
			}
		});
		
		FlatContextMenuEntry e2 = new FlatContextMenuEntry("Einfügen", FlatContextMenuEntryType.NORMAL, FlatIconFont.CLIPBOARD);
		e2.addListener(new FlatContextMenuEntryListener() {

			@Override
			public void onClick() {
				Transferable t = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
				if(t.isDataFlavorSupported(DataFlavor.stringFlavor)){
					try {
						String s = (String) t.getTransferData(DataFlavor.stringFlavor);
						setText(getText() + s);
					} catch (UnsupportedFlavorException | IOException e) {
						e.printStackTrace();
					}
					
				}
			}
		});
		FlatContextMenuEntry e3 = new FlatContextMenuEntry("Ersetzen", FlatContextMenuEntryType.NORMAL, FlatIconFont.RANDOM);
		e3.addListener(new FlatContextMenuEntryListener() {

			@Override
			public void onClick() {
				Transferable t = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
				if(t.isDataFlavorSupported(DataFlavor.stringFlavor)){
					try {
						String s = (String) t.getTransferData(DataFlavor.stringFlavor);
						setText(s);
						
					} catch (UnsupportedFlavorException | IOException e) {
						e.printStackTrace();
					}
					
				}
			}
		});
		FlatContextMenuEntry e4 = new FlatContextMenuEntry("Löschen", FlatContextMenuEntryType.NORMAL, FlatIconFont.ERASER);
		e4.addListener(new FlatContextMenuEntryListener() {

			@Override
			public void onClick() {
				setText("");
				
			}
		});
		FlatContextMenu menu = new FlatContextMenu(e1, e2,e3, e4);
		setContextMenu(menu);
		
	}

	/**
	 * Creates a new FlatTextBox with password possibility
	 * 
	 * @param man
	 *            an instance of FlatLayoutManager
	 */
	public FlatTextBox(FlatLayoutManager man, boolean password) {
		this(man);
		this.password = password;
	}

	/**
	 * Returns the current text
	 * 
	 * @return current text
	 */
	public String getText() {
		return text;
	}

	/**
	 * Changes the text
	 * 
	 * @param text
	 */
	public void setText(String text) {
		this.text = text;
		this.position = text.length();
		repaint();
	}

	@Override
	public void setFont(Font font) {
		this.font = font;
	}

	@Override
	public Font getFont() {
		return this.font;
	}

	@Override
	public void paintComponent(Graphics gr) {
		super.paintComponent(gr);
		Graphics2D g = (Graphics2D) gr;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, !FlatUI.isFastRendering() ? RenderingHints.VALUE_ANTIALIAS_ON : RenderingHints.VALUE_ANTIALIAS_OFF);
		g.setColor(background);
		g.fillRect(0, 0, getBounds().getSize().width, getBounds().getSize().height);
		g.setColor(foreground);
		if (password) {
			g.setFont(FlatFont.getInstance(22, Font.PLAIN));
		} else {
			g.setFont(font);
		}

		FontMetrics fm = g.getFontMetrics((password ? FlatFont.getInstance(22, Font.PLAIN) : font));
		String passwdRepl = "";
		for (int i = 0; i < text.length(); i++) {
			passwdRepl += "\u00b7";
		}
		java.awt.geom.Rectangle2D rect = fm.getStringBounds((!password ? text : passwdRepl), g);
		int textHeight = (int) (rect.getHeight());
		int textWidth = (int) (rect.getWidth());
		int panelHeight = this.getHeight();
		int panelWidth = this.getWidth();
		int x = 10;
		// boolean extra = false;
		if (textWidth >= panelWidth - 15) {
			x = panelWidth - textWidth - 10;
			// extra = true;
		}
		int y = (panelHeight - textHeight) / 2 + fm.getAscent() - 3;

		g.drawString((!password ? text : passwdRepl), x, y);

		if (blinky && editable) {
			int xBlinky;
			if (position > 0) {
				if (!text.equals("")) {
					java.awt.geom.Rectangle2D rect2 = fm.getStringBounds((password ? passwdRepl.substring(0, position) : text.substring(0, position)), g);
					//System.out.println("pos=" + position + ", XBlinky="  + (rect2.getWidth() - 1));
					xBlinky = (int) rect2.getWidth() - 1;
					if(xBlinky > panelWidth - 20){
						xBlinky = panelWidth - 23;
						//System.out.println("Cutting xBlinky");
					}
				} else {
					xBlinky = -2;
				}
			} else if (position == 0 && text.equals("")) {
				xBlinky = textWidth;
			} else {
				xBlinky = -2;
			}

			g.setColor(foreground_blinky);
			g.drawRect(xBlinky + 12, (panelHeight - textHeight) / 2, 1, (textHeight));
			// if (!extra) {
			// g.drawRect(textWidth + 12, (panelHeight - textHeight) / 2, 1,
			// (textHeight));
			// } else {
			// g.drawRect(panelWidth - 5, (panelHeight - textHeight) / 2, 1,
			// (textHeight));
			// }

		}

	}

	@Override
	public Color getForeground() {
		return this.foreground;
	}

	@Override
	public void setForeground(Color foreground) {
		this.foreground = foreground;
	}

	/**
	 * Returns the color of the blinky element.
	 * 
	 * @return
	 */
	public Color getForegroundBlinky() {
		return this.foreground_blinky;
	}

	/**
	 * Sets the color of the blinky element.
	 * 
	 * @param foregroundBlinky
	 */
	public void setForegroundBlinky(Color foregroundBlinky) {
		this.foreground_blinky = foregroundBlinky;
	}

	public boolean isPasswordVisible() {
		return password;
	}

	public void setPasswordVisible(boolean password) {
		this.password = password;
		repaint();
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

}
