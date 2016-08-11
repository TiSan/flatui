package de.tisan.flatui.components.fcommons;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JFrame;
import javax.swing.JPanel;

import de.tisan.flatui.components.ffont.FlatFont;

/**
 * Dummy, not implemented yet.
 * 
 * @author TiSan
 * 
 */
public class FlatMessageBox extends JFrame {

	private static final long serialVersionUID = -5055642729013298558L;
	private boolean show;
	private String message;

	private FlatMessageBoxType messageType;
	private JPanel contentPane;
	private FlatLayoutManager man;
	private Painter painter;
	private JFrame parentFrame;
	
	public FlatMessageBox(final JFrame frame) {
		this.show = false;
		this.parentFrame = frame;
		this.messageType = FlatMessageBoxType.INFO;
		this.message = "";
		this.setBounds(500, 200, (frame.getSize().width / 2) - (this.getSize().width / 2), (frame.getSize().height / 2) - (this.getSize().height / 2));
		setUndecorated(true);
		setAlwaysOnTop(true);
		setOpacity(0.95F);
		contentPane = new JPanel();
		contentPane.setBorder(null);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setFocusable(true);
		contentPane.setBackground(FlatColors.BACKGROUND);
		man = new FlatLayoutManager(this);
		painter = new Painter();
		painter.setBounds(0, 0, getWidth(), getHeight());
		contentPane.add(painter);

	}

	class Painter extends JPanel {

		private static final long serialVersionUID = -1443570819483420437L;

		private Painter() {

		}

		@Override
		public void paintComponent(Graphics g2) {
			Graphics2D g = (Graphics2D) g2;
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, !FlatUI.isFastRendering() ? RenderingHints.VALUE_ANTIALIAS_ON : RenderingHints.VALUE_ANTIALIAS_OFF);

			if (messageType == FlatMessageBoxType.ALERT) {

			} else if (messageType == FlatMessageBoxType.INFO) {
				g.setColor(FlatColors.BLUE);
			} else if (messageType == FlatMessageBoxType.WARNING) {

			}
			g.fillRect(0, 20, getSize().width, getSize().height);
			g.setColor(g.getColor().darker());
			g.fillRect(0, 0, getSize().width, 20);
			g.setColor(FlatColors.WHITE);
			g.setFont(FlatFont.getInstance());
			g.drawString(message, 5, 16);
		}
	}

	public void show(String title, String message) {
		this.message = title;
		this.message = message;
		this.show = true;
		this.setSize(300, 150);
		this.setLocation(((parentFrame.getSize().width / 2) - (this.getSize().width / 2)), (parentFrame.getSize().height / 2) - (this.getSize().height / 2));
		painter.setBounds(0, 0, getWidth(), getHeight());
		setVisible(true);
	}

	public void hide() {
		setVisible(false);
	}
}
