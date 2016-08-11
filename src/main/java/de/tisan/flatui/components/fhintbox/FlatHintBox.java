package de.tisan.flatui.components.fhintbox;

import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Area;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import de.tisan.flatui.components.fbutton.FlatButton;
import de.tisan.flatui.components.fcommons.FlatColors;
import de.tisan.flatui.components.fcommons.FlatHintProgressBar;
import de.tisan.flatui.components.fcommons.FlatLayoutManager;
import de.tisan.flatui.components.ffont.FlatFont;
import de.tisan.flatui.components.flisteners.MouseClickedHandler;
import de.tisan.flatui.components.flisteners.MouseDragHandler;
import de.tisan.flatui.components.flisteners.MouseEnterHandler;
import de.tisan.flatui.components.flisteners.MouseLeaveHandler;
import de.tisan.flatui.components.flisteners.MouseMoveHandler;
import de.tisan.flatui.components.flisteners.MousePressHandler;
import de.tisan.flatui.components.flisteners.MouseReleaseHandler;
import de.tisan.flatui.components.flisteners.Priority;

/**
 * A Hintbox on the right down corner of your screen to notify the user.
 * 
 * @author TiSan
 * 
 */
public class FlatHintBox extends JFrame {

	private static final long serialVersionUID = 4435888747006229980L;
	private JPanel contentPane;
	private Point to;
	private FlatHintBoxOrientation orientation;
	private FlatHintProgressBar progressBar;
	private boolean showing;
	protected boolean counterAlreadyRunning;
	protected boolean confirmed;
	private FlatButton btn;
	private JLabel lblHeader;
	private JLabel lblMessage;

	protected FlatHintBox() {
		setUndecorated(true);
		setAlwaysOnTop(true);
		contentPane = new JPanel();
		this.setContentPane(contentPane);
		this.setSize(400, 150);
		this.setOrientation(FlatHintBoxOrientation.DOWN);
		this.showing = false;
		this.confirmed = false;
		contentPane.setBackground(FlatColors.BACKGROUND);
		FlatLayoutManager man = FlatLayoutManager.get(this);
		contentPane.setLayout(null);
		progressBar = new FlatHintProgressBar(true, man);
		progressBar.setLocation(0, 0);
		progressBar.setSize(400, 10);
		this.counterAlreadyRunning = false;
		contentPane.add(progressBar);
		btn = new FlatButton("OK", man);
		btn.setLocation(0, 123);
		btn.setSize(400, 27);
		btn.addMouseListener(Priority.NORMAL, new de.tisan.flatui.components.flisteners.MouseListener() {

			@Override
			public void onMouseRelease(MouseReleaseHandler handler) {
				confirmed = true;
				// System.out.println("Pressed Button " + confirmed);
			}

			@Override
			public void onMousePress(MousePressHandler handler) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onMouseMove(MouseMoveHandler handler) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onMouseLeave(MouseLeaveHandler handler) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onMouseEnter(MouseEnterHandler handler) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onMouseDrag(MouseDragHandler handler) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onMouseClicked(MouseClickedHandler handler) {
				// TODO Auto-generated method stub

			}
		});
		contentPane.add(btn);

		lblHeader = new JLabel();
		lblHeader.setText("Header");
		lblHeader.setBounds(10, 21, 380, 27);
		lblHeader.setForeground(FlatColors.LIGHTBLUE);
		lblHeader.setFont(FlatFont.getInstance(32, Font.PLAIN));
		// lblHeader.setFont(FlatFont.getInstance());
		// System.out.println(lblHeader.getFont().getFontName());
		contentPane.add(lblHeader);

		lblMessage = new JLabel("<html>Message</html>");
		lblMessage.setVerticalAlignment(SwingConstants.TOP);
		lblMessage.setBounds(10, 59, 380, 53);
		lblMessage.setForeground(FlatColors.SILVER);
		lblMessage.setFont(FlatFont.getInstance());
		contentPane.add(lblMessage);

		// Dimension scrnSize = Toolkit.getDefaultToolkit().getScreenSize();
		// Rectangle winSize =
		// GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		Rectangle bounds = gd.getDefaultConfiguration().getBounds();
		Insets insets = Toolkit.getDefaultToolkit().getScreenInsets(gd.getDefaultConfiguration());
		Rectangle safeBounds = new Rectangle(bounds);
		safeBounds.x += insets.left;
		safeBounds.y += insets.top;
		safeBounds.width -= (insets.left + insets.right);
		safeBounds.height -= (insets.top + insets.bottom);
		Area area = new Area(bounds);
		area.subtract(new Area(safeBounds));
		setOrientation(null);
		if (area.getBounds().getX() > 0) {
			// Rechts
			to = new Point(Toolkit.getDefaultToolkit().getScreenSize().width - getSize().width - (int) area.getBounds().getWidth(), Toolkit.getDefaultToolkit().getScreenSize().height - getSize().height);
			setOrientation(FlatHintBoxOrientation.RIGHT);
		} else {
			// Links, Unten oder oben
			if (area.getBounds().getY() > 0) {
				// Unten
				to = new Point(Toolkit.getDefaultToolkit().getScreenSize().width - getSize().width, Toolkit.getDefaultToolkit().getScreenSize().height - getSize().height - (int) area.getBounds().height);
				setOrientation(FlatHintBoxOrientation.DOWN);
			} else {
				// Oben
				to = new Point(Toolkit.getDefaultToolkit().getScreenSize().width - getSize().width, Toolkit.getDefaultToolkit().getScreenSize().height - getSize().height);
				setOrientation(FlatHintBoxOrientation.UP);
			}
		}
		this.setLocation((int) to.getX(), (int) to.getY() + 50);
		this.setOpacity(0F);
		// setVisible(false);
		addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				confirmed = true;
				// System.out.println("Clicked, set confirmed to true");
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
		});
	}

	/**
	 * Shows the current instance of a flathintbox.
	 * 
	 * @param hasButton
	 */
	protected void showBox(boolean hasButton) {
		this.showing = true;
		this.confirmed = false;
		setVisible(true);
		if (hasButton) {
			progressBar.setVisible(false);
			btn.setVisible(true);
		} else {
			progressBar.setVisible(true);
			btn.setVisible(false);
		}
		new Thread(new Runnable() {
			@Override
			public void run() {
				setVisible(true);
				// Color bgColor = getBackground();
				float tmp = 0;
				int time = 1;
				for (int i = getLocation().y; i > to.y; i--) {
					setLocation(getLocation().x, i);
					// setOpacity(((getLocation().y) / i));
					if (tmp <= 1F && getOpacity() <= 1F) {
						setOpacity(tmp);
					} else {
						setOpacity(1F);
					}
					tmp += 0.02F;
					if (tmp > 0.5) {
						time += (tmp * 2) / 0.8;
					}
					try {
						Thread.sleep(time);
					} catch (InterruptedException e) {
					}
				}
				setOpacity(1F);

			}

		}).start();
	}

	/**
	 * Changes the text of the body of the hintbox.
	 * 
	 * @param body
	 */
	public void setText(String body) {
		this.lblMessage.setText("<html>" + body + "</html>");
	}

	/**
	 * Changes the title of the hintbox.
	 */
	@Override
	public void setTitle(String title) {
		this.lblHeader.setText("<html>" + title + "</html>");

	}

	protected void enableProgressBar(final int value, final int timeout, boolean reset) {
		this.progressBar.setVisible(true);
		this.btn.setVisible(false);
		// this.progressBar.setValue(this.value);
		if (reset) {
			counterAlreadyRunning = false;
		}
		new Thread(new Runnable() {

			@Override
			public void run() {
				if (!counterAlreadyRunning) {
					counterAlreadyRunning = true;
					for (int i = 0; i <= 100; i++) {
						progressBar.setValue(i);
						// System.out.println("hi");
						try {
							Thread.sleep((int) (timeout * 12));
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}

		}).start();

	}

	/**
	 * Returns true if box is actually showing.
	 * 
	 * @return
	 */
	public boolean isShowBox() {
		return this.showing;
	}

	public void hideBox() {
		this.counterAlreadyRunning = false;
		this.showing = false;
		this.confirmed = false;
		new Thread(new Runnable() {
			@Override
			public void run() {

				// Color bgColor = getBackground();
				float tmp = 1;
				int tmp1 = getLocation().y;
				int time = 1;
				for (int i = getLocation().y; i <= tmp1 + 50; i++) {
					setLocation(getLocation().x, i);
					// setOpacity(((getLocation().y) / i));
					if (tmp >= 0) {
						setOpacity(tmp);
					} else {
						setOpacity(0F);
					}
					tmp -= 0.02F;
					if (tmp <= 0.5) {
						time += 1;
					}
					try {
						Thread.sleep(time);
					} catch (InterruptedException e) {

					}
				}

				setVisible(false);
				setLocation((int) to.getX(), (int) to.getY() + 50);
			}

		}).start();

	}

	protected void enableButton() {
		progressBar.setVisible(false);
		btn.setVisible(true);
		this.confirmed = false;
		// System.out.println("Enabling button");
	}

	/**
	 * Where is the box?
	 * 
	 * @return
	 */
	public FlatHintBoxOrientation getOrientation() {
		return orientation;
	}

	private void setOrientation(FlatHintBoxOrientation orientation) {
		this.orientation = orientation;
	}

	public void setConfirmed(boolean b) {
		this.confirmed = b;
	}

}
