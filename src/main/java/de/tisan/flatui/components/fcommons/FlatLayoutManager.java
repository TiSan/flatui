package de.tisan.flatui.components.fcommons;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;

import de.tisan.flatui.components.fthemes.Theme;
import de.tisan.flatui.components.fthemes.Themes;
import de.tisan.flatui.components.fupdate.FlatUpdate;
import de.tisan.flatui.components.fupdate.FlatUpdateManager;

/**
 * The FlatLayoutManager manages the layout of the several components on the
 * screen, which are created by FlatUI.
 * 
 * @author TiSan
 * 
 */
public class FlatLayoutManager {
	private ArrayList<FlatComponent> components;
	private JFrame frame;
	private static HashMap<JFrame, FlatLayoutManager> instances = new HashMap<JFrame, FlatLayoutManager>();
	private boolean alreadyCalced;
	protected boolean resizeRight;
	protected boolean resizeLeft;
	protected boolean resizeDown;
	protected boolean resizeBoth;
	private boolean resizable = true;
	private Color tintColor;
	private int tintIntensity;
	private Dimension maximumWindowSize;
	private static boolean checkedUpdates;
	private static FlatLayoutManager defI;
	private Theme currentTheme;
	public static boolean showCreditFrame = true;

	/**
	 * Use this if you dont have a JFrame (in case of JavaFX). ATTENTION: Some
	 * components such as the FlatMenu dont work because they require a JFrame
	 * with a contentpane. I dont guarantee any work of FlatUI out of the SWING
	 * environment, but you are welcome to post bugs in the github project, so I
	 * can fix that if I can.
	 * 
	 * @return
	 */
	public static FlatLayoutManager getDefault() {
		return (defI == null ? (defI = new FlatLayoutManager(null)) : defI);
	}

	/**
	 * Returns the FlatLayoutManager of your JFrame. If no FlatLayoutManager
	 * found, a new one will be created and returned.
	 * 
	 * @param frame
	 * @return
	 */
	public static FlatLayoutManager get(JFrame frame) {
		FlatLayoutManager man = instances.get(frame);
		if (man == null) {
			man = new FlatLayoutManager(frame);
			instances.put(frame, man);
		}
		return man;
	}

	FlatLayoutManager(JFrame frame) {
		this.components = new ArrayList<FlatComponent>();
		this.frame = frame;
		this.tintColor = Color.BLUE;
		this.tintIntensity = 50;
		this.currentTheme = Themes.getDarkTheme();
		this.maximumWindowSize = new Dimension(400, 200);
		if (showCreditFrame)
			new Thread(new Runnable() {

				@Override
				public void run() {
					GUIPoweredBy f = new GUIPoweredBy();
					f.display();

				}
			}).start();
		if (!checkedUpdates)
			new Thread(new Runnable() {

				@Override
				public void run() {
					checkedUpdates = true;
					FlatUpdate upd = FlatUpdateManager.checkUpdate();
					if (upd != null) {
						System.out.println("[FlatUI] A new version of FlatUI is now available! Check it out!");
						System.out.println("[FlatUI] Your version: '" + FlatUI.getVersion() + "', new version: '" + upd.getVersion() + " >" + upd.getVersionState() + "<'!");
						System.out.println("[FlatUI] Download-Link: " + upd.getPath());
						System.out.println("[FlatUI] Things changed in new version (Changelog):");
						for (String tmp : upd.getChangelog()) {
							System.out.println("[FlatUI] [Changelog] " + tmp);
						}
						System.out.println("[FlatUI] You don't need to update, but it is highly recommended.");

					} else {
						System.out.println("[FlatUI] Version " + FlatUI.version + " initialized!");
						System.out.println("[FlatUI] Licence: Non Profit Licence activated.");
						System.out.println("[FlatUI] FlatUI. A simple UI collection by TiSan.de");

					}
					
				}

			}).start();

		ComponentListener l1 = new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				if (resizable) {
					for (FlatComponent f : components) {
						if (!alreadyCalced) {
							if ((f.getCalculatedSize() != null && !f.getCalculatedSize().equals(f.getSize()))) {
								f.updatePerc();
							} else {
								f.calcPerc();
							}

						}
						if (f.getAnchors() != null) {
							boolean center = f.getAnchors().contains(Anchor.CENTER);
							boolean down = f.getAnchors().contains(Anchor.DOWN);
							boolean left = f.getAnchors().contains(Anchor.LEFT);
							boolean right = f.getAnchors().contains(Anchor.RIGHT);
							if (down && !center && !left && !right) {
								// Only down
								f.setSize(f.getWidth(), (int) (e.getComponent().getHeight() * (f.getPercentHeight() / 100)));
							} else if (!down && !center && !left && right) {
								// Only right
								f.setLocation(e.getComponent().getWidth() - f.getWidth() - f.getCornerRight(), f.getLocation().y);
							} else if (down && !center && !left && right) {
								// Right and down
								f.setLocation(e.getComponent().getWidth() - f.getWidth() - f.getCornerRight(), e.getComponent().getHeight() - f.getHeight() - f.getCornerDown());
							} else if (!down && !center && left && right) {

								// Only left and right
								f.setSize((int) (e.getComponent().getWidth() * (f.getPercentWidth() / 100)), f.getHeight());
							} else if (center) {
								// all directions
								f.setSize((int) (e.getComponent().getWidth() * (f.getPercentWidth() / 100)), (int) (e.getComponent().getHeight() * (f.getPercentHeight() / 100)));
							}
						}
					}
					alreadyCalced = true;
				}

			}
		};
		if (frame != null) {
			frame.addComponentListener(l1);
		}
		MouseListener l2 = new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		};
		if (frame != null) {
			frame.addMouseListener(l2);
		}
		MouseMotionListener l3 = new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent arg0) {
				if (resizable) {
					if (arg0.getX() >= frame.getWidth() - 15 && arg0.getY() >= frame.getHeight() - 15) {
						frame.setCursor(Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR));
						resizeBoth = true;
						resizeDown = false;
						resizeLeft = false;
						resizeRight = false;
					} else if (arg0.getX() >= frame.getWidth() - 15) {
						frame.setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
						resizeRight = true;
						resizeDown = false;
						resizeLeft = false;
						resizeBoth = false;
					} else if (arg0.getY() >= frame.getHeight() - 15) {
						frame.setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
						resizeDown = true;
						resizeBoth = false;
						resizeLeft = false;
						resizeRight = false;
					} else {
						frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
						resizeDown = false;
						resizeBoth = false;
						resizeLeft = false;
						resizeRight = false;
					}
				}

			}

			@Override
			public void mouseDragged(MouseEvent arg0) {
				if (resizable) {
					if (arg0.getX() >= maximumWindowSize.width && arg0.getY() >= maximumWindowSize.height) {
						if (resizeRight) {
							frame.setSize(arg0.getX(), frame.getHeight());
						} else if (resizeDown) {
							frame.setSize(frame.getWidth(), arg0.getY());
						} else if (resizeBoth) {
							frame.setSize(arg0.getX(), arg0.getY());
						}
					} else {
						if (resizeBoth || resizeDown || resizeLeft || resizeRight) {
							if (arg0.getX() <= maximumWindowSize.width) {
								frame.setSize(maximumWindowSize.width, (int) frame.getSize().getHeight());
							} else {
								frame.setSize(arg0.getX(), (int) frame.getSize().getHeight());
							}
							if (arg0.getY() <= maximumWindowSize.height) {
								frame.setSize((int) frame.getSize().getWidth(), maximumWindowSize.height);
							} else {
								frame.setSize((int) frame.getSize().getWidth(), arg0.getY());
							}
						}

						// frame.setSize((int)(frame.getSize().getWidth() >= 400
						// ? frame.getSize().getWidth() : 400),(int)(
						// frame.getSize().getHeight() >= 200 ?
						// frame.getSize().getHeight() : 200));
					}
				}

			}
		};
		if (frame != null) {
			frame.addMouseMotionListener(l3);
		}
	}

	/**
	 * Adds a new component to the layoutmanager.
	 * 
	 * @param com
	 */
	public void addComponent(FlatComponent com) {
		components.add(com);
	}

	public void requestFocus(FlatComponent com) {

		for (int i = 0; i < components.size(); i++) {
			if (components.get(i).equals(com)) {
				com.setFocusable(true);
				com.requestFocus();
				com.focused = true;
			} else {
				components.get(i).focused = false;
			}
		}
	}

	public JPanel getContentPane() {
		if (frame != null) {
			return (JPanel) frame.getContentPane();
		} else {
			return null;
		}

	}

	public JFrame getFrame() {
		return frame;
	}

	public void enableAllEffects() {
		for (FlatComponent c : components) {
			c.enableEffects();
		}
	}

	public void disableAllEffects() {
		for (FlatComponent c : components) {
			c.disableEffects();
		}
	}

	public boolean isResizable() {
		return resizable;
	}

	public void setResizable(boolean resizable) {
		this.resizable = resizable;
	}

	public Color getTintColor() {
		return tintColor;
	}

	public void setTintColor(Color tintColor) {
		this.tintColor = tintColor;
	}

	public int getTintIntensity() {
		return tintIntensity;
	}

	public void setTintIntensity(int tintPercent) {
		this.tintIntensity = tintPercent;
	}

	public Theme getCurrentTheme() {
		return currentTheme;
	}

	public void setCurrentTheme(Theme currentTheme) {
		this.currentTheme = currentTheme;
		if (currentTheme != null) {
			for (FlatComponent c : components) {
				currentTheme.configureComponent(c);
			}
		}
	}

	public int[] tint(Color c) {
		int red = (int) ((c.getRed() + getTintColor().getRed() / ((100 - getTintIntensity()) * 0.16)) / 2);
		int green = (int) ((c.getGreen() + getTintColor().getGreen() / ((100 - getTintIntensity()) * 0.16)) / 2);
		int blue = (int) ((c.getBlue() + getTintColor().getBlue() / ((100 - getTintIntensity()) * 0.16)) / 2);
		if (red > 255) {
			red = 255;
		} else if (red < 0) {
			red = 0;
		}
		if (green > 255) {
			green = 255;
		} else if (green < 0) {
			green = 0;
		}
		if (blue > 255) {
			blue = 255;
		} else if (blue < 0) {
			blue = 0;
		}

		return new int[] { red, green, blue };
	}
}
