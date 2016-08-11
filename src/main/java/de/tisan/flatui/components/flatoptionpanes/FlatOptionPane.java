package de.tisan.flatui.components.flatoptionpanes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import de.tisan.flatui.components.fbutton.FlatButton;
import de.tisan.flatui.components.fcommons.Anchor;
import de.tisan.flatui.components.fcommons.FlatColors;
import de.tisan.flatui.components.fcommons.FlatLayoutManager;
import de.tisan.flatui.components.ffont.FlatFont;
import de.tisan.flatui.components.ficon.FlatIconFont;
import de.tisan.flatui.components.flisteners.ActionListener;
import de.tisan.flatui.components.flisteners.FlatButtonSizeChangeListener;
import de.tisan.flatui.components.flisteners.MouseClickedHandler;
import de.tisan.flatui.components.flisteners.Priority;
import de.tisan.flatui.helpers.resizehelpers.SwingResizeHelper;

public class FlatOptionPane extends JFrame {

	private static final long serialVersionUID = 8687960699103945262L;
	private JPanel contentPane;
	private FlatOptionPaneButton[] btns;
	private Font titleFont;
	private Font messageFont;
	private JLabel lblTitle;
	private JPanel highlight;
	private JLabel lblText;
	protected Point lastMousePoint;
	private FlatButton btnClose;

	public static void main(String[] args) {
		FlatOptionPane p = FlatOptionPane.getYesNoDialog("Test", "Dies ist ein Test");
		p.setMessageFont(FlatFont.getInstance(18, Font.PLAIN));
		p.setAccentColor(FlatColors.RED);
		p.setTextColor(FlatColors.BACKGROUND);
		p.showDialog();
	}

	public static FlatOptionPane getYesNoDialog(String title, String message) {
		FlatOptionPaneButton btnYes = FlatOptionPaneTemplates.createYesButton("Yes");
		FlatOptionPaneButton btnNo = FlatOptionPaneTemplates.createYesButton("No");
		return getMessageDialog(title, message, FlatColors.BACKGROUND, FlatColors.WHITE, false, false, true, 550, 200, btnYes, btnNo);
	}

	public static FlatOptionPane getYesNoCancelDialog(String title, String message) {
		FlatOptionPaneButton btnYes = FlatOptionPaneTemplates.createYesButton("Yes");
		FlatOptionPaneButton btnNo = FlatOptionPaneTemplates.createYesButton("No");
		FlatOptionPaneButton btnCancel = FlatOptionPaneTemplates.createYesButton("Cancel");
		return getMessageDialog(title, message, btnYes, btnNo, btnCancel);
	}

	public static FlatOptionPane getYesDialog(String title, String message) {
		FlatOptionPaneButton btnYes = FlatOptionPaneTemplates.createYesButton("Yes");
		return getMessageDialog(title, message, btnYes);
	}

	public static FlatOptionPane getMessageDialog(String title, String message, FlatOptionPaneButton... btns) {
		return getMessageDialog(title, message, FlatColors.BACKGROUND, FlatColors.WHITE, false, false, true, 550, 200, btns);
	}

	public static FlatOptionPane getMessageDialog(String title, String message, Color accentColor, Color textColor, boolean alwaysOnTop, boolean resizable, boolean showXButton, int width, int height, FlatOptionPaneButton... btns) {
		// 550x200
		FlatOptionPane pane = new FlatOptionPane(accentColor, textColor, alwaysOnTop, resizable, showXButton, width, height, title, message, btns);
		// pane.setVisible(true);
		return pane;
	}

	private FlatOptionPane(Color accentColor, Color textColor, boolean alwaysOnTop, boolean resizable, boolean showXButton, int width, int height, String title, String message, FlatOptionPaneButton... btns) {
		this.btns = btns;
		setAlwaysOnTop(alwaysOnTop);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBorder(null);
		contentPane.setLayout(null);
		setContentPane(contentPane);
		setUndecorated(true);
		SwingResizeHelper h = SwingResizeHelper.getResizeHelperForSwing(this);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(width, height);
		setLocation(dim.width / 2 - (getSize().width / 2), dim.height / 2 - (getSize().height / 2));
		contentPane.setBackground(accentColor);
		highlight = new JPanel();
		highlight.setBackground(contentPane.getBackground().brighter());
		highlight.setBounds(0, 0, getWidth(), 40);
		lblTitle = new JLabel(title);
		h.addComponent(highlight, Anchor.LEFT, Anchor.RIGHT);
		lblTitle.setForeground(textColor);
		lblTitle.setBounds(10, -4, getWidth() - 50, 46);
		lblTitle.setVerticalAlignment(JLabel.CENTER);
		lblTitle.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {
				lastMousePoint = new Point(e.getLocationOnScreen().x - getLocation().x, e.getLocationOnScreen().y - getLocation().y);
			}

			@Override
			public void mouseExited(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseClicked(MouseEvent e) {

			}
		});
		lblTitle.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent e) {

			}

			@Override
			public void mouseDragged(MouseEvent e) {
				setLocation(e.getXOnScreen() - lastMousePoint.x, e.getYOnScreen() - lastMousePoint.y);
			}
		});
		titleFont = FlatFont.getInstance(18, Font.PLAIN);
		lblTitle.setFont(titleFont);
		h.addComponent(lblTitle, Anchor.LEFT, Anchor.RIGHT);
		contentPane.add(lblTitle);
		FlatLayoutManager man = FlatLayoutManager.get(this);
		man.setResizable(resizable);
		if (showXButton) {
			btnClose = new FlatButton(null, FlatIconFont.TIMES, man);
			btnClose.setAnchor(Anchor.RIGHT);
			btnClose.setBounds(getWidth() - 40, 0, 40, 40);
			btnClose.setBackground(highlight.getBackground());
			btnClose.addActionListener(Priority.NORMAL, new ActionListener() {

				@Override
				public void onAction(MouseClickedHandler handler) {
					FlatOptionPane.this.dispatchEvent(new WindowEvent(FlatOptionPane.this, WindowEvent.WINDOW_CLOSING));
				}
			});
			contentPane.add(btnClose);
		}

		lblText = new JLabel("<html>" + message + "</html>");
		lblText.setForeground(textColor);
		lblText.setBounds(10, 50, getWidth() - 50, getHeight() - 100);
		lblText.setFont(FlatFont.getInstance(12, Font.PLAIN));
		lblText.setVerticalAlignment(JLabel.TOP);
		h.addComponent(lblText, Anchor.CENTER);
		contentPane.add(lblText);

		contentPane.add(highlight);

		for (FlatOptionPaneButton btn : btns) {
			contentPane.add(btn);
			btn.addListener(new FlatButtonSizeChangeListener() {

				@Override
				public void onSizeChange() {
					orderButtons();
				}
			});

			btn.setBackground(accentColor.brighter());
		}
		h.useNowSize();
	}

	private void orderButtons() {
		if (btns != null) {
			int x = getWidth() - 10;
			for (FlatOptionPaneButton btn : btns) {
				btn.setLocation(x - btn.getSize().width, getHeight() - 10 - btn.getSize().height);
				x -= btn.getSize().width + 10;
			}
		}
	}

	public void showDialog() {
		setVisible(true);
	}

	public void hideDialog() {
		setVisible(false);
	}

	public Font getTitleFont() {
		return titleFont;
	}

	public void setTitleFont(Font titleFont) {
		this.titleFont = titleFont;
		lblTitle.setFont(titleFont);
	}

	public Font getMessageFont() {
		return messageFont;
	}

	public void setMessageFont(Font messageFont) {
		this.messageFont = messageFont;
		lblText.setFont(messageFont);
	}

	public void setAccentColor(Color accentColor) {
		highlight.setBackground(accentColor.brighter());
		contentPane.setBackground(accentColor);
		for (FlatOptionPaneButton btn : btns) {
			btn.setBackground(accentColor.brighter());
		}
		if (btnClose != null) {
			btnClose.setBackground(accentColor.brighter());
		}
	}

	public void setTextColor(Color textColor) {
		lblText.setForeground(textColor);
		lblTitle.setForeground(textColor);
		for (FlatOptionPaneButton btn : btns) {
			btn.setForeground(textColor);
		}
		if (btnClose != null) {
			btnClose.setForeground(textColor);
		}
	}
}
