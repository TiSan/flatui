package de.tisan.flatui.components.ftilemenu;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import de.tisan.flatui.components.fcommons.FlatColors;
import de.tisan.flatui.components.fcommons.FlatComponent;
import de.tisan.flatui.components.fcommons.FlatLayoutManager;
import de.tisan.flatui.components.ffont.FlatFont;
import de.tisan.flatui.components.ficon.FlatIconFont;

public class FlatTileMenu extends FlatComponent {

	private static final long serialVersionUID = -8122979376536563765L;
	private ArrayList<FlatTileMenuGroup> groups;
	private boolean editable;
	private FlatTile pressedTile;
	private Point pressedPoint;
	private int transformSize = 0;
	private int transformAmpl;
	private boolean effectsStop;

	public FlatTileMenu(FlatLayoutManager man) {
		super(man);
		man.addComponent(this);
		if (man.getContentPane() != null)
			man.getContentPane().add(this, 0);
		effects = false;
		this.transformAmpl = 4;
		editable = false;
		setForeground(FlatColors.WHITE);
		setOpaque(false);
		setBackground(new Color(0, 0, 0, 0));
		this.groups = new ArrayList<FlatTileMenuGroup>();
		MouseListener l = new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				if (pressedTile != null) {
					if (e.getPoint().x >= pressedTile.internalX && pressedTile.internalX + pressedTile.getSize().getWidth() >= e.getPoint().x && e.getPoint().y >= pressedTile.internalY
							&& pressedTile.internalY + pressedTile.getSize().getHeight() >= e.getPoint().y) {
						pressedTile.pushClick();
					}
					new Thread(new Runnable() {

						@Override
						public void run() {
							FlatTile tile = pressedTile;
							effectsStop = true;
							while (transformSize >= 0) {
								transformSize--;
								try {
									Thread.sleep(20);
								} catch (InterruptedException e) {
								}
								repaint();
							}
							tile.pressed = false;
							tile.dragged = false;
							tile.pressedCenter = false;
							tile.pressedDown = false;
							tile.pressedLeft = false;
							tile.pressedRight = false;
							tile.pressedUp = false;
							pressedTile = null;
							effectsStop = false;
						}
					}).start();

				}

				repaint();
			}

			@Override
			public void mousePressed(MouseEvent e) {
				FlatTile tile = getClickedTile(e.getPoint());
				if (tile != null) {

					pressedTile = tile;
					pressedPoint = new Point(e.getPoint().x - tile.internalX, e.getPoint().y - tile.internalY);
					pressedTile.pressed = true;
					int percX = (pressedPoint.x * 100) / pressedTile.getSize().getWidth();
					int percY = (pressedPoint.y * 100) / pressedTile.getSize().getHeight();

					if (percY > 80) {
						pressedTile.pressedDown = true;
					} else if (percY < 20) {
						pressedTile.pressedUp = true;
					} else {
						if (percX < 40) {
							pressedTile.pressedLeft = true;
						} else if (percX > 60) {
							pressedTile.pressedRight = true;
						} else if (percX >= 40 && percX <= 60) {
							pressedTile.pressedCenter = true;
						}
					}
					new Thread(new Runnable() {

						@Override
						public void run() {
							FlatTile tile = pressedTile;
							while (transformSize >= -1 && transformSize <= transformAmpl && !effectsStop) {
								if (pressedTile != tile || effectsStop) {
									break;
								} else {
									transformSize += 1;
								}
								try {
									Thread.sleep(20);
								} catch (InterruptedException e) {
								}
								repaint();
							}
							if (pressedTile != tile) {
								transformSize = -1;
							} else if (!effectsStop) {
								transformSize = transformAmpl;
							}
						}
					}).start();
				}

				repaint();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

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
		addMouseListener(l);
		addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent e) {

			}

			@Override
			public void mouseDragged(MouseEvent e) {
				if (pressedTile != null) {
					if (pressedTile.pressed && !pressedTile.dragged && editable) {
						Point tmp = new Point(e.getPoint().x - pressedTile.internalX, e.getPoint().y - pressedTile.internalY);
						if (Math.abs(tmp.x - pressedPoint.x) > 20 || Math.abs(tmp.y - pressedPoint.y) > 20) {
							pressedTile.dragged = true;
							pressedTile.pressed = false;
						}
					}
					if (pressedTile.dragged) {
						pressedTile.pushDrag();
						pressedTile.internalX = e.getPoint().x - pressedPoint.x;
						pressedTile.internalY = e.getPoint().y - pressedPoint.y;
						repaint();
					}

				}
			}
		});
	}

	public void addFlatTileMenuGroup(FlatTileMenuGroup group) {
		this.groups.add(group);
	}

	@Override
	protected void paintComponent(Graphics g2) {
		super.paintComponent(g2);
		Graphics2D g = (Graphics2D) g2;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		int x = 10;
		int y = 30;
		g.setColor(getBackground());
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(getForeground());
		g.setFont(FlatFont.getInstance(20, Font.PLAIN));
		if (groups != null && groups.size() > 0) {
			// Iteriere alle Groups durch.
			
			for (FlatTileMenuGroup group : groups) {
				// Zeichne den Titel einer Group.
				g.drawString(group.getName(), x, y);
				y += 30;
				if (group.getTiles() != null && group.getTiles().size() > 0) {
					int xSize = 0;
					int ySize = 0;
					int xStart = x;
					int yStart = y;
					FlatTile last = null;
					for (FlatTile tile : group.getTiles()) {
						// System.out.println("Drawing " + tile.getName() +
						// ", ySize is " + ySize);
						if (xSize + tile.getSize().getWidth() > 250 && last != null) {
							// Wenn nach rechts in einer Reihe kein Platz mehr
							// ist, dann wird ein Zeilenumbruch gemacht.
							y += last.getSize().getHeight() + 10;
							x = xStart;
							xSize = 0;
						} else {
							if (last != null) {
								ySize -= last.getSize().getHeight() + 20;

							}

						}
						if (ySize + tile.getSize().getHeight() >= getHeight() - 40 && last != null) {
							// Wenn nach unten in einer Reihe kein Platz mehr
							// ist, wird ein Spaltenumbruch gemacht.
							x += 260 - (x - xStart);
							xStart = x;
							y = yStart;
							ySize = 0;
						}
						// Füge eine temporäre Variable für Information der
						// Spalten- und Zellengröße hinzu.
						xSize += tile.getSize().getWidth();
						// if(!newLine){

						ySize += tile.getSize().getHeight() + 20;

						// }
						// Zeichne das Feld.
						g.setColor(tile.getBackground());
						int xDraw = x;
						int yDraw = y;
						if (tile.dragged) {
							xDraw = tile.internalX;
							yDraw = tile.internalY;
						} else {
							tile.internalX = x;
							tile.internalY = y;

						}
						int amplX = 0;
						int amplY = 0;
						if (tile.pressed) {
							// Wenn wir gedrückt haben, dann soll die
							// "Eindrück-Animation" kommen.
							if (tile.pressedCenter) {
								g.fillRect(xDraw + transformSize, yDraw + transformSize, tile.getSize().getWidth() - transformSize * 2, tile.getSize().getHeight() - transformSize * 2);

							} else if (tile.pressedDown) {
								Polygon p = new Polygon();
								p.addPoint(xDraw, yDraw);
								p.addPoint(xDraw + tile.getSize().getWidth(), yDraw);
								p.addPoint(xDraw + tile.getSize().getWidth() - transformSize, yDraw + tile.getSize().getHeight() - transformSize * 2);
								p.addPoint(xDraw + transformSize, yDraw + tile.getSize().getHeight() - transformSize * 2);
								g.fillPolygon(p);
								amplY = -transformSize;
								amplX = (transformSize * 2) / transformAmpl;
							} else if (tile.pressedUp) {
								Polygon p = new Polygon();
								p.addPoint(xDraw + transformSize, yDraw + transformSize * 2);
								p.addPoint(xDraw + tile.getSize().getWidth() - transformSize, yDraw + transformSize * 2);
								p.addPoint(xDraw + tile.getSize().getWidth(), yDraw + tile.getSize().getHeight());
								p.addPoint(xDraw, yDraw + tile.getSize().getHeight());
								g.fillPolygon(p);
							} else if (tile.pressedLeft) {
								Polygon p = new Polygon();
								p.addPoint(xDraw + transformSize * 2, yDraw + transformSize);
								p.addPoint(xDraw + tile.getSize().getWidth(), yDraw);
								p.addPoint(xDraw + tile.getSize().getWidth(), yDraw + tile.getSize().getHeight());
								p.addPoint(xDraw + transformSize * 2, yDraw + tile.getSize().getHeight() - transformSize);
								g.fillPolygon(p);
								amplX = transformSize;
								amplY = -(transformSize * 2) / transformAmpl;
							} else if (tile.pressedRight) {
								Polygon p = new Polygon();
								p.addPoint(xDraw, yDraw);
								p.addPoint(xDraw + tile.getSize().getWidth() - transformSize * 2, yDraw + transformSize);
								p.addPoint(xDraw + tile.getSize().getWidth() - transformSize * 2, yDraw + tile.getSize().getHeight() - transformSize);
								p.addPoint(xDraw, yDraw + tile.getSize().getHeight());
								g.fillPolygon(p);
							}
						} else {
							g.fillRect(xDraw, yDraw, tile.getSize().getWidth(), tile.getSize().getHeight());

						}
						g.setColor(tile.getForeground());
						g.setFont(tile.getFont());
						g.drawString(tile.getName(), xDraw + 10 + amplX, yDraw + tile.getSize().getHeight() - 10 + amplY);
						if (tile.getIcon() != null) {
							g.setColor(this.getForeground());
							//System.out.println(tile.getIconSize());
							Font ifont = FlatIconFont.getInstance(tile.getIconSize(), Font.PLAIN);
							g.setFont(ifont);
							FontMetrics fm1 = g.getFontMetrics(ifont);
							java.awt.geom.Rectangle2D rect1 = fm1.getStringBounds(tile.getIcon().getValue(), g);
							int textHeight1 = (int) (rect1.getHeight());
							int textWidth1 = (int) (rect1.getWidth());
							int panelHeight1 = tile.getSize().getHeight();
							int panelWidth1 = tile.getSize().getWidth();
							g.drawString(tile.getIcon().getValue(), xDraw + ((panelWidth1 / 2) - textWidth1 / 2), yDraw + ((panelHeight1 / 2) - textHeight1 / 2) + fm1.getAscent() - 5);
						} else if (tile.getImage() != null) {

							g.drawImage(tile.getImage(), (xDraw + (tile.getSize().getWidth() / 2) - tile.getImage().getWidth() / 2), yDraw + ((tile.getSize().getHeight() / 2) - tile.getImage().getHeight() / 2), null);
						}

						// Füge die Größe zur x-Variable hinzu und speichere das
						// aktuelle Element.
						x += tile.getSize().getWidth() + 10;
						last = tile;

					}
					// Alle Tiles einer Group wurden gezeichnet. Neue Group
					// beginnt.
					y = 30;
					x = xStart + 280;
					g.setColor(getForeground());
					g.setFont(FlatFont.getInstance(20, Font.PLAIN));

				}
			}
		}
	}

	private FlatTile getClickedTile(Point p) {
		for (FlatTileMenuGroup g : groups) {
			for (FlatTile t : g.getTiles()) {
				if (t.internalX <= p.getX() && p.getX() <= t.internalX + t.getSize().getWidth()) {
					if (t.internalY <= p.getY() && p.getY() <= t.internalY + t.getSize().getHeight()) {
						return t;
					}
				}
			}
		}
		return null;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

}
