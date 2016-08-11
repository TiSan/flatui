package de.tisan.flatui.components.fcommons;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Comparator;

import javax.swing.JPanel;

import de.tisan.flatui.components.fcheckbox.FlatCheckBox;
import de.tisan.flatui.components.fcontextmenu.FlatContextMenu;
import de.tisan.flatui.components.flisteners.ActionListener;
import de.tisan.flatui.components.flisteners.FlatListener;
import de.tisan.flatui.components.flisteners.KeyListener;
import de.tisan.flatui.components.flisteners.KeyPressHandler;
import de.tisan.flatui.components.flisteners.KeyReleaseHandler;
import de.tisan.flatui.components.flisteners.ListenerPriorityCombiner;
import de.tisan.flatui.components.flisteners.MouseClickedHandler;
import de.tisan.flatui.components.flisteners.MouseDragHandler;
import de.tisan.flatui.components.flisteners.MouseEnterHandler;
import de.tisan.flatui.components.flisteners.MouseLeaveHandler;
import de.tisan.flatui.components.flisteners.MouseListener;
import de.tisan.flatui.components.flisteners.MouseMoveHandler;
import de.tisan.flatui.components.flisteners.MousePressHandler;
import de.tisan.flatui.components.flisteners.MouseReleaseHandler;
import de.tisan.flatui.components.flisteners.Priority;
import de.tisan.flatui.components.fradiobutton.FlatRadioButton;
import de.tisan.flatui.components.fslider.FlatSlider;

/**
 * Abstract class of the flat components. It is not important for you :)
 * 
 * @author TiSan
 * 
 */
public abstract class FlatComponent extends JPanel {

	private static final long serialVersionUID = -1180747487135124395L;
	protected int click;
	public Color background;
	protected Color backOrigin;
	protected boolean focused;
	protected boolean effects;
	boolean effectStop;
	int effectSteps;
	public boolean mouseEntered;
	private FlatContextMenu contextMenu;
	private ArrayList<ListenerPriorityCombiner> listeners;
	private ArrayList<Anchor> anchors;
	private FlatLayoutManager man;
	private double percentWidth;
	private int rootWidth;
	private double percentHeight;
	private int rootHeight;
	private int rootX;
	private int rootY;
	private int cornerLeft;
	private int cornerRight;
	private int cornerUp;
	private int cornerDown;
	private Dimension calculatedSize;

	/**
	 * Returns true if this component has the focus.
	 */
	@Override
	public boolean hasFocus() {
		return this.focused;
	}

	/**
	 * Returns the current background color of the pane.
	 */
	public Color getBackground() {
		return this.background;
	}

	/**
	 * Enables the mouse hover effects.
	 */
	public void enableEffects() {
		this.effects = true;
	}

	/**
	 * Disables the mouse hover effects.
	 */
	public void disableEffects() {
		this.effects = false;
	}

	/**
	 * Changes the background.
	 */
	public void setBackground(final Color background) {
		FlatComponent.this.background = background;
		FlatComponent.this.backOrigin = new Color(background.getRed(), background.getGreen(), background.getBlue());
		repaint();
		if (background.getRed() != -1) {
			return;
		}
		if (background.getRed() == 240 && background.getGreen() == 240 && background.getBlue() == 240) {
			return;
		}

		new Thread(new Runnable() {
			// TODO Work not perfect, not all colors are available on start??
			@Override
			public void run() {
				final Color oldColour = new Color(FlatComponent.this.background.getRed(), FlatComponent.this.background.getGreen(), FlatComponent.this.background.getBlue());
				// System.out.println("old:{" +
				// FlatComponent.this.background.toString() + "} new {" +
				// background + "}");

				final int dRed = background.getRed() - oldColour.getRed();
				final int dGreen = background.getGreen() - oldColour.getGreen();
				final int dBlue = background.getBlue() - oldColour.getBlue();
				// No point if no difference.
				if (dRed != 0 || dGreen != 0 || dBlue != 0) {
					// Do it in n steps.
					for (int i = 0; i <= 25; i++) {
						final Color c = new Color(oldColour.getRed() + ((dRed * i) / 25), oldColour.getGreen() + ((dGreen * i) / 25), oldColour.getBlue() + ((dBlue * i) / 25));
						FlatComponent.this.background = c;
						repaint();
						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {

						}
					}
				}
				FlatComponent.this.background = background;
				FlatComponent.this.backOrigin = new Color(background.getRed(), background.getGreen(), background.getBlue());

				// repaint();
			}

		}).start();
	}

	/**
	 * Sets the resizing anchors of the component. See Anchor.class for detailed
	 * information.
	 * 
	 * @param anchors
	 */
	public void setAnchor(Anchor... anchors) {
		this.anchors.clear();
		if (anchors != null) {
			for (Anchor c : anchors) {
				this.anchors.add(c);
			}
		}
	}

	/**
	 * Gets all resizing anchors of the component. See Anchor.class for detailed
	 * information.
	 * 
	 * @return
	 */
	public ArrayList<Anchor> getAnchors() {
		return this.anchors;
	}

	/**
	 * Dont initialize a flatcomponent by yourself. ;)
	 * 
	 * @param man
	 */
	protected FlatComponent(FlatLayoutManager man) {
		this.man = man;
		this.focused = false;
		this.setFocusable(true);
		this.click = 0;
		this.effects = true;
		this.effectStop = false;
		this.effectSteps = 0;
		// this.responsive = false;
		this.background = FlatColors.BACKGROUND;
		this.backOrigin = new Color(background.getRed(), background.getGreen(), background.getBlue());
		this.listeners = new ArrayList<ListenerPriorityCombiner>();
		this.anchors = new ArrayList<Anchor>();
		addMouseListener(new java.awt.event.MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				for (FlatListener l : getListenersSorted()) {
					if (l instanceof MouseListener) {
						MouseListener m = (MouseListener) l;
						MouseClickedHandler h = new MouseClickedHandler(arg0);
						m.onMouseClicked(h);
						if (h.isCancel()) {
							break;
						}
					}
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				for (FlatListener l : getListenersSorted()) {
					if (l instanceof MouseListener) {
						MouseListener m = (MouseListener) l;
						MouseEnterHandler h = new MouseEnterHandler(e);
						m.onMouseEnter(h);
						if (h.isCancel()) {
							break;
						}
					}
				}
				if (effects) {
					mouseEntered = true;
					new Thread(new Runnable() {

						@Override
						public void run() {

							for (int i = 0; i < 21; i++) {
								if (!effectStop) {
									effectSteps = i;
									background = new Color(changeValue(backOrigin.getRed(), i), changeValue(backOrigin.getGreen(), i), changeValue(backOrigin.getBlue(), i));
									repaint();
									try {
										Thread.sleep(10);
									} catch (InterruptedException e1) {

									}
								} else {
									break;
								}

							}
						}

					}).start();

				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				for (FlatListener l : getListenersSorted()) {
					if (l instanceof MouseListener) {
						MouseListener m = (MouseListener) l;
						MouseLeaveHandler h = new MouseLeaveHandler(e);
						m.onMouseLeave(h);
						if (h.isCancel()) {
							break;
						}
					}
				}
				if (effects) {
					effectStop = true;
					new Thread(new Runnable() {

						@Override
						public void run() {
							for (int i = effectSteps; i >= 0; i--) {
								if (effectStop) {
									background = new Color(changeValue(backOrigin.getRed(), i), changeValue(backOrigin.getGreen(), i), changeValue(backOrigin.getBlue(), i));
									repaint();
									try {
										Thread.sleep(10);
									} catch (InterruptedException e1) {

									}
								} else {
									break;
								}

							}
							effectStop = false;
							effectSteps = 0;
							background = new Color(backOrigin.getRed(), backOrigin.getGreen(), backOrigin.getBlue());
							mouseEntered = false;
						}

					}).start();
					// background = new Color(backOrigin.getRed(),
					// backOrigin.getGreen(), backOrigin.getBlue());
					// repaint();
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				for (FlatListener l : getListenersSorted()) {
					if (l instanceof MouseListener) {
						MouseListener m = (MouseListener) l;
						MousePressHandler h = new MousePressHandler(e);
						m.onMousePress(h);
						if (h.isCancel()) {
							break;
						}
					}
				}
				if (effects) {
					effectStop = false;
					new Thread(new Runnable() {

						@Override
						public void run() {
							for (int i = effectSteps; i >= -10; i--) {
								if (!effectStop) {
									effectSteps = i;
									background = new Color(changeValue(backOrigin.getRed(), i), changeValue(backOrigin.getGreen(), i), changeValue(backOrigin.getBlue(), i));
									repaint();
									try {
										Thread.sleep(5);
									} catch (InterruptedException e1) {

									}
								} else {
									break;
								}

							}
						}

					}).start();

					// effectStop = true;
					// background = new Color(changeValue(backOrigin.getRed(),
					// -10), changeValue(backOrigin.getGreen(), -10),
					// changeValue(backOrigin.getBlue(), -10));
					// repaint();
					// effectStop = false;
				}
				if (FlatComponent.this instanceof FlatSlider) {
					((FlatSlider) FlatComponent.this).mouseDown = true;
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				for (FlatListener l : getListenersSorted()) {
					if (l instanceof MouseListener) {
						MouseListener m = (MouseListener) l;
						MouseReleaseHandler h = new MouseReleaseHandler(e);
						m.onMouseRelease(h);

						if (h.isCancel()) {
							break;
						}
					}
				}

				if (effects) {
					// background = new Color(backOrigin.getRed(),
					// backOrigin.getGreen(), backOrigin.getBlue());
					effectStop = true;
					new Thread(new Runnable() {

						@Override
						public void run() {
							for (int i = effectSteps; i < (mouseEntered ? 20 : 0); i++) {
								if (effectStop) {
									effectSteps = i;
									background = new Color(changeValue(backOrigin.getRed(), i), changeValue(backOrigin.getGreen(), i), changeValue(backOrigin.getBlue(), i));
									repaint();
									try {
										Thread.sleep(5);
									} catch (InterruptedException e1) {

									}
								} else {
									effectSteps = (mouseEntered ? 20 : 0);
									break;
								}
							}
							effectStop = false;
							if (mouseEntered) {
								background = new Color(backOrigin.getRed(), backOrigin.getGreen(), backOrigin.getBlue());
							} else {
								background = new Color(changeValue(backOrigin.getRed(), 20), changeValue(backOrigin.getGreen(), 20), changeValue(backOrigin.getBlue(), 20));

							}

						}

					}).start();

					// background = new Color(changeValue(backOrigin.getRed(),
					// effectSteps), changeValue(backOrigin.getGreen(),
					// effectSteps), changeValue(backOrigin.getBlue(),
					// effectSteps));

					// repaint();
					if (man != null) {
						man.requestFocus(FlatComponent.this);

					}

					if (FlatComponent.this instanceof FlatCheckBox) {
						((FlatCheckBox) FlatComponent.this).setChecked(!((FlatCheckBox) FlatComponent.this).isChecked());
					} else if (FlatComponent.this instanceof FlatRadioButton) {
						((FlatRadioButton) FlatComponent.this).group.uncheckAll(((FlatRadioButton) FlatComponent.this));
						((FlatRadioButton) FlatComponent.this).checked = true;
					}
				}
				if (FlatComponent.this instanceof FlatSlider) {
					((FlatSlider) FlatComponent.this).mouseDown = false;
				}

			}
		});
		addKeyListener(new java.awt.event.KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent e) {
				for (FlatListener l : getListenersSorted()) {
					if (l instanceof KeyListener) {
						KeyListener m = (KeyListener) l;
						KeyReleaseHandler h = new KeyReleaseHandler(e);
						m.onKeyRelease(h);
						if (h.isCancel()) {
							break;
						}
					}
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
				for (FlatListener l : getListenersSorted()) {
					if (l instanceof KeyListener) {
						KeyListener m = (KeyListener) l;
						KeyPressHandler h = new KeyPressHandler(e);
						m.onKeyPress(h);
						if (h.isCancel()) {
							break;
						}
					}
				}
			}
		});
		addMouseListener(Priority.HIGH, new MouseListener() {

			@Override
			public void onMouseRelease(MouseReleaseHandler handler) {
				if (handler.getEvent().getButton() == MouseEvent.BUTTON3 && FlatComponent.this.contextMenu != null) {
					FlatComponent.this.contextMenu.showMenu();
					handler.setCancel(true);
				}
			}

			@Override
			public void onMousePress(MousePressHandler handler) {
			}

			@Override
			public void onMouseMove(MouseMoveHandler handler) {
			}

			@Override
			public void onMouseLeave(MouseLeaveHandler handler) {
			}

			@Override
			public void onMouseEnter(MouseEnterHandler handler) {
			}

			@Override
			public void onMouseDrag(MouseDragHandler handler) {
			}

			@Override
			public void onMouseClicked(MouseClickedHandler handler) {
			}
		});
		new Thread(new Runnable() {

			@Override
			public void run() {
				// Component painter
				boolean oldState = false;
				boolean isInAnimation = false;

				while (effects) {
					if (mouseEntered != oldState && !effectStop) {
						if (mouseEntered) {

						}
					} else if (isInAnimation && !effectStop) {

					} else {
						// Keine Änderung, sleep.
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
						}
					}
				}

			}
		});
		// TODO Start Thread!
	}

	/**
	 * Returns an arrayList of all registered listeners ordered by its priority.
	 * 
	 * @return
	 */
	public ArrayList<FlatListener> getListenersSorted() {
		ArrayList<FlatListener> l = new ArrayList<FlatListener>();
		ArrayList<ListenerPriorityCombiner> tmp = listeners;
		tmp.sort(new Comparator<ListenerPriorityCombiner>() {

			@Override
			public int compare(ListenerPriorityCombiner o1, ListenerPriorityCombiner o2) {
				if (o1.getPriority().equals(Priority.LOW) && (o2.getPriority().equals(Priority.NORMAL) || o2.getPriority().equals(Priority.HIGH))) {
					return -1;
				} else if (o2.getPriority().equals(o1.getPriority())) {
					return 0;
				} else {
					return 1;
				}
			}
		});
		for (ListenerPriorityCombiner t : tmp) {
			l.add(t.getListener());
		}
		return l;
	}

	/**
	 * Adds a listener
	 * 
	 * @param prio
	 *            The Priority of the Listener (Normal ist the default one, HIGH
	 *            should be used only internal)
	 * @param l
	 *            The Listener
	 */
	public void addListener(Priority prio, FlatListener l) {
		this.listeners.add(new ListenerPriorityCombiner(prio, l));
	}

	/**
	 * Adds a mouse listener
	 * 
	 * @param prio
	 *            The Priority of the Listener (Normal ist the default one, HIGH
	 *            should be used only internal)
	 * @param l
	 *            The Listener
	 */
	public void addMouseListener(Priority prio, MouseListener l) {
		addListener(prio, l);
	}

	/**
	 * Adds a action listener
	 * 
	 * @param prio
	 *            The Priority of the Listener (Normal ist the default one, HIGH
	 *            should be used only internal)
	 * @param l
	 *            The Listener
	 */
	public void addActionListener(Priority prio, ActionListener l) {
		addListener(prio, l);
	}

	/**
	 * Adds a key listener
	 * 
	 * @param prio
	 *            The Priority of the Listener (Normal ist the default one, HIGH
	 *            should be used only internal)
	 * @param l
	 *            The Listener
	 */
	public void addKeyListener(Priority prio, KeyListener l) {
		addListener(prio, l);
	}

	/**
	 * Sets the context menu of a component
	 * 
	 * @param menu
	 *            The ContextMenu object
	 */
	public void setContextMenu(FlatContextMenu menu) {
		this.contextMenu = menu;
	}

	protected int changeValue(int value1, int ampli) {
		if (value1 + ampli >= 255) {
			return 255;
		} else if (value1 + ampli <= 0) {
			return 0;
		} else {
			return value1 + ampli;
		}

	}

	@Override
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, width, height);
	}

	protected void calcPerc() {
		if (man != null) {
			calculatedSize = getSize();
			rootWidth = getWidth();
			rootHeight = getHeight();
			rootX = getX();
			rootY = getY();
			try {
				percentWidth = (rootWidth * 100) / man.getContentPane().getBounds().width;
				percentHeight = (rootHeight * 100) / man.getContentPane().getBounds().height;
				cornerLeft = man.getContentPane().getBounds().width - rootX;
				cornerRight = man.getContentPane().getBounds().width - rootWidth - rootX;
				cornerUp = man.getContentPane().getBounds().height - rootY;
				cornerDown = man.getContentPane().getBounds().height - rootHeight - rootY;
			} catch (Exception ex) {

			}

		}
	}

	protected void updatePerc() {
		if (man != null) {
			calculatedSize = getSize();

			rootWidth = getWidth();
			rootHeight = getHeight();
			percentWidth = (rootWidth * 100) / man.getContentPane().getBounds().width;
			percentHeight = (rootHeight * 100) / man.getContentPane().getBounds().height;

		}
	}

	protected double getPercentWidth() {
		return percentWidth;
	}

	protected int getRootWidth() {
		return rootWidth;
	}

	protected double getPercentHeight() {
		return percentHeight;
	}

	protected int getRootHeight() {
		return rootHeight;
	}

	protected int getRootX() {
		return rootX;
	}

	protected int getRootY() {
		return rootY;
	}

	protected int getCornerLeft() {
		return cornerLeft;
	}

	protected int getCornerRight() {
		return cornerRight;
	}

	protected int getCornerUp() {
		return cornerUp;
	}

	protected int getCornerDown() {
		return cornerDown;
	}

	protected Dimension getCalculatedSize() {
		return calculatedSize;
	}

}
