package de.tisan.flatui.atest;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import de.tisan.flatui.components.fbutton.FlatButton;
import de.tisan.flatui.components.fcheckbox.FlatCheckBox;
import de.tisan.flatui.components.fcommons.Anchor;
import de.tisan.flatui.components.fcommons.FlatColors;
import de.tisan.flatui.components.fcommons.FlatUI;
import de.tisan.flatui.components.fcontextmenu.FlatContextMenu;
import de.tisan.flatui.components.fcontextmenu.FlatContextMenuEntry;
import de.tisan.flatui.components.fcontextmenu.FlatContextMenuEntryListenerGlobal;
import de.tisan.flatui.components.fcontextmenu.FlatContextMenuEntryType;
import de.tisan.flatui.components.ffont.FlatFont;
import de.tisan.flatui.components.fframe.FlatFrame;
import de.tisan.flatui.components.fhintbox.FlatHintBox;
import de.tisan.flatui.components.fhintbox.FlatHintBoxEntry;
import de.tisan.flatui.components.fhintbox.FlatHintBoxListener;
import de.tisan.flatui.components.fhintbox.FlatHintBoxManager;
import de.tisan.flatui.components.ficon.FlatIconFont;
import de.tisan.flatui.components.flisteners.MouseClickedHandler;
import de.tisan.flatui.components.flisteners.MouseDragHandler;
import de.tisan.flatui.components.flisteners.MouseEnterHandler;
import de.tisan.flatui.components.flisteners.MouseLeaveHandler;
import de.tisan.flatui.components.flisteners.MouseListener;
import de.tisan.flatui.components.flisteners.MouseMoveHandler;
import de.tisan.flatui.components.flisteners.MousePressHandler;
import de.tisan.flatui.components.flisteners.MouseReleaseHandler;
import de.tisan.flatui.components.flisteners.Priority;
import de.tisan.flatui.components.fmenu.FlatMenu;
import de.tisan.flatui.components.fmenu.FlatMenuActionListener;
import de.tisan.flatui.components.fmenu.FlatMenuPoint;
import de.tisan.flatui.components.fprogressbar.FlatProgressBar;
import de.tisan.flatui.components.fradiobutton.FlatRadioButton;
import de.tisan.flatui.components.fradiobutton.FlatRadioButtonGroup;
import de.tisan.flatui.components.fslider.FlatSlider;
import de.tisan.flatui.components.fspinner.FlatSpinner;
import de.tisan.flatui.components.ftextbox.FlatTextBox;
import de.tisan.flatui.components.ftogglebtn.FlatToggleButton;
import de.tisan.flatui.helpers.fadehelper.FadeHelper;

public class TestUI extends FlatFrame {
	private static final long serialVersionUID = -2643370602022963704L;
	protected boolean responsive;
	private FlatMenu menu;
	private FlatButton btnProgressBar;
	private FlatProgressBar pro;
	private FlatTextBox txt;
	private FlatProgressBar pro2;
	private FlatProgressBar pro3;
	private FlatProgressBar pro4;
	private FlatProgressBar pro5;
	private FlatProgressBar pro6;
	private FlatCheckBox chk;
	private FlatRadioButtonGroup group;
	private FlatRadioButton rad;
	private FlatRadioButton rad1;
	private FlatSpinner spi;
	private FlatSlider sli;
	private FlatButton btnHintBox;
	private FlatHintBoxManager boxMan;
	private FlatHintBoxEntry entry;
	private FlatHintBoxEntry entry2;
	private FlatMenuPoint p1;
	private FlatMenuPoint p3;
	private FlatToggleButton btnTgl;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestUI frame = new TestUI();
					frame.setVisible(true);

				} catch (Exception e) {

					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @throws UnsupportedLookAndFeelException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws FontFormatException
	 */
	public TestUI() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException, FontFormatException, IOException {
		super("FlatUI demo presentation tool v" + FlatUI.getVersion());
		System.out.println();
		setTitle("FlatUI demo presentation tool v" + FlatUI.getVersion());
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 530, 598);
		setUndecorated(true);
		setBackgroundBlur(false);
		removeBackground();
		//refreshBackground();
		final FlatMenu menu = new FlatMenu(getLayoutManager());
		menu.setBounds(0, bar.getHeight(), getWidth(), 30);
		menu.setAnchor(Anchor.LEFT, Anchor.RIGHT);
		bar.setOptionMenuOverlay(true);
		p1 = menu.addMenuPoint("Toggle fastRendering", FlatIconFont.FAST_FORWARD, new FlatMenuActionListener() {

			@Override
			public void mouseReleased(MouseEvent arg0) {
				FlatUI.setFastRendering(!FlatUI.isFastRendering());
				refreshBackground();
				if (FlatUI.isFastRendering()) {
					p1.setBackground(FlatColors.GREEN);

				} else {
					p1.setBackground(FlatColors.ALIZARINRED);
				}
				repaint();
			}

		});
		p3 = menu.addMenuPoint("Open Tile Window", FlatIconFont.WINDOWS, new FlatMenuActionListener() {

			@Override
			public void mouseReleased(MouseEvent arg0) {
				TestTiles frame;
				try {
					frame = new TestTiles();
					frame.setVisible(true);
				} catch (IOException e) {
					e.printStackTrace();
				}

			}

		});

		FlatMenuPoint p2 = menu.addMenuPoint("Exit", FlatIconFont.POWER_OFF, new FlatMenuActionListener() {

			@Override
			public void mouseReleased(MouseEvent arg0) {
				System.exit(0);
			}

		});
		p2.setBackground(FlatColors.ALIZARINRED);
		p2.repaint();
		bar.setOptionMenu(menu);
		contentPane.add(menu);

		JLabel lblWatermark = new JLabel("<html>FlatUI v" + FlatUI.getVersion() + " | <b>Demo presentation tool</b><br>(C) 2014 by TiSan. Testing purposes only!</html>");
		lblWatermark.setVerticalAlignment(SwingConstants.TOP);
		lblWatermark.setHorizontalAlignment(SwingConstants.RIGHT);
		lblWatermark.setFont(FlatFont.getInstance(11, Font.PLAIN));
		lblWatermark.setBounds(10, 98, 492, 34);
		contentPane.add(lblWatermark);
		lblWatermark.setForeground(FlatColors.TEXT);
		// menu.setLocation(0, 44);
		// contentPane.add(menu);

		btnProgressBar = new FlatButton("Press me", getLayoutManager());
		btnProgressBar.setText("Example Button");
		btnProgressBar.setAnchor(Anchor.DOWN);
		btnProgressBar.setBackground(FlatColors.GREEN);
		pro = new FlatProgressBar(true, getLayoutManager());
		txt = new FlatTextBox(getLayoutManager(), false);
		btnProgressBar.setBounds(88, 147, 199, 53);
		pro.setBounds(88, 211, 414, 23);
		txt.setBounds(88, 415, 414, 43);
		// setResizable(false);
		pro2 = new FlatProgressBar(true, getLayoutManager());
		pro2.setBounds(88, 245, 414, 23);
		pro3 = new FlatProgressBar(true, getLayoutManager());
		pro3.setBounds(88, 279, 414, 23);
		pro4 = new FlatProgressBar(true, getLayoutManager());
		pro4.setBounds(88, 313, 414, 23);
		pro5 = new FlatProgressBar(true, getLayoutManager());
		pro5.setBounds(88, 347, 414, 23);
		pro6 = new FlatProgressBar(false, getLayoutManager());
		pro6.setBounds(88, 381, 414, 23);
		pro2.setForeground(FlatColors.ALIZARINRED);
		pro3.setForeground(FlatColors.DARKERGRAY);
		pro4.setForeground(FlatColors.LIGHTBLUE);
		pro5.setForeground(FlatColors.YELLOW);
		pro6.setForeground(FlatColors.LIME);
		JLabel lblNewLabel = new JLabel("Button");
		lblNewLabel.setForeground(FlatColors.TEXT);
		lblNewLabel.setFont(FlatFont.getInstance(13, Font.PLAIN));
		lblNewLabel.setBounds(10, 158, 82, 31);
		contentPane.add(lblNewLabel);
		JLabel lblProgressbar = new JLabel("ProgressBar");
		lblProgressbar.setForeground(FlatColors.TEXT);
		lblProgressbar.setFont(FlatFont.getInstance(13, Font.PLAIN));
		lblProgressbar.setBounds(10, 211, 82, 23);
		contentPane.add(lblProgressbar);
		JLabel label = new JLabel("ProgressBar");
		label.setForeground(FlatColors.TEXT);
		label.setFont(FlatFont.getInstance(13, Font.PLAIN));
		label.setBounds(10, 245, 82, 23);
		contentPane.add(label);
		JLabel label_1 = new JLabel("ProgressBar");
		label_1.setForeground(FlatColors.TEXT);
		label_1.setFont(FlatFont.getInstance(13, Font.PLAIN));
		label_1.setBounds(10, 279, 82, 23);
		contentPane.add(label_1);
		JLabel label_2 = new JLabel("ProgressBar");
		label_2.setForeground(FlatColors.TEXT);
		label_2.setFont(FlatFont.getInstance(13, Font.PLAIN));
		label_2.setBounds(10, 313, 82, 23);
		contentPane.add(label_2);
		JLabel label_3 = new JLabel("ProgressBar");
		label_3.setForeground(FlatColors.TEXT);
		label_3.setFont(FlatFont.getInstance(13, Font.PLAIN));
		label_3.setBounds(10, 347, 82, 23);
		contentPane.add(label_3);
		JLabel lblProgressbars = new JLabel("ProgressBar(S)");
		lblProgressbars.setForeground(FlatColors.TEXT);
		lblProgressbars.setFont(FlatFont.getInstance(13, Font.PLAIN));
		lblProgressbars.setBounds(10, 381, 82, 23);
		contentPane.add(lblProgressbars);
		JLabel lblTextbox = new JLabel("TextBox");
		lblTextbox.setForeground(FlatColors.TEXT);
		lblTextbox.setFont(FlatFont.getInstance(13, Font.PLAIN));
		lblTextbox.setBounds(10, 428, 82, 18);
		contentPane.add(lblTextbox);
		chk = new FlatCheckBox("CheckBox", getLayoutManager());
		chk.setBounds(10, 469, 109, 20);
		group = new FlatRadioButtonGroup();
		rad = new FlatRadioButton("Radio Button 1", group, getLayoutManager());
		rad.setBounds(129, 469, 139, 20);
		rad1 = new FlatRadioButton("Radio Button 2", group, getLayoutManager());
		rad1.setBounds(278, 469, 139, 20);
		spi = new FlatSpinner(getLayoutManager());
		spi.setBounds(427, 469, 75, 20);
		spi.addValue("1");
		spi.addValue("2");
		spi.addValue("3");
		spi.addValue("4");
		spi.addValue("5");
		sli = new FlatSlider(getLayoutManager());
		sli.setBounds(10, 540, 492, 58);
		btnTgl = new FlatToggleButton(getLayoutManager());
		btnTgl.setBounds(10, 500, 50, 30);
		// contentPane.add(btnTgl, 0);
		btnHintBox = new FlatButton("Open HintBox", FlatIconFont.BELL, getLayoutManager());
		btnHintBox.setBounds(303, 147, 199, 53);
		btnHintBox.setAnchor(Anchor.RIGHT, Anchor.DOWN);
		btnHintBox.setContextMenu(new FlatContextMenu(new FlatContextMenuEntry("Testeintrag eines", FlatContextMenuEntryType.NORMAL), new FlatContextMenuEntry("l�ngeren Tests mit", FlatContextMenuEntryType.NORMAL, FlatIconFont.ADJUST),
				new FlatContextMenuEntry("mehreren Eintr�gen", FlatContextMenuEntryType.NORMAL, FlatIconFont.FACEBOOK_SQUARE), new FlatContextMenuEntry("in einer riiiiesengro�en Box.", FlatContextMenuEntryType.CHECKBOX))
				.addGlobalListener(new FlatContextMenuEntryListenerGlobal() {

					@Override
					public void onClick(FlatContextMenuEntry entry) {
						System.out.println("Click on " + entry.getName());
					}
				}));
		boxMan = new FlatHintBoxManager();
		boxMan.addFlatHintBoxListener(new FlatHintBoxListener() {

			@Override
			public void onClick(FlatHintBoxEntry entry, FlatHintBox box) {

			}
		});
		entry = new FlatHintBoxEntry("Much time", "Test box with a timer! <b><i>Such wow!</i></b>", 5);
		entry2 = new FlatHintBoxEntry("Such interaction", "Test box with a button instead of a timer to get the <b>attention of the user</b>.");
		btnProgressBar.addMouseListener(Priority.NORMAL, new MouseListener() {

			@Override
			public void onMousePress(MousePressHandler handler) {
				
			}

			@Override
			public void onMouseRelease(MouseReleaseHandler handler) {
				if (pro.getValue() < 100) {
					pro.setValue(pro.getValue() + 10);
					pro2.setValue(pro.getValue() + 10);
					pro3.setValue(pro.getValue() + 20);
					pro4.setValue(pro.getValue() + 30);
					pro5.setValue(pro.getValue() + 40);
					pro6.setValue(pro.getValue());
				} else {
					pro.setValue(0);
					pro2.setValue(0);
					pro3.setValue(0);
					pro4.setValue(0);
					pro5.setValue(0);
					pro6.setValue(0);
				}
			}

			@Override
			public void onMouseMove(MouseMoveHandler handler) {

			}

			@Override
			public void onMouseDrag(MouseDragHandler handler) {

			}

			@Override
			public void onMouseEnter(MouseEnterHandler handler) {

			}

			@Override
			public void onMouseLeave(MouseLeaveHandler handler) {

			}

			@Override
			public void onMouseClicked(MouseClickedHandler handler) {

			}
		});
		btnHintBox.addMouseListener(Priority.NORMAL, new MouseListener() {

			@Override
			public void onMouseRelease(MouseReleaseHandler handler) {
				FadeHelper.fadeOut(btnHintBox);
				//boxMan.showHintBox(entry);
				//boxMan.showHintBox(entry2);
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						FadeHelper.fadeIn(btnHintBox);
					}
				}).start();
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
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent arg0) {
				if (responsive) {
					pro.setSize(getSize().width - 110, pro.getSize().height);
					pro2.setSize(getSize().width - 110, pro2.getSize().height);
					pro3.setSize(getSize().width - 110, pro3.getSize().height);
					pro4.setSize(getSize().width - 110, pro4.getSize().height);
					pro5.setSize(getSize().width - 110, pro5.getSize().height);
					pro6.setSize(getSize().width - 110, pro5.getSize().height);
					btnProgressBar.setSize(getSize().width - btnHintBox.getSize().width - 120, btnProgressBar.getSize().height);
					// flatButton_1.setSize(getSize().width - 110,
					// flatButton_1.getSize().height);
					txt.setSize(getSize().width - 110, txt.getSize().height);
					sli.setSize(getSize().width - 33, sli.getSize().height);
					menu.setSize(getSize().width, menu.getSize().height);
				}
			}
		});

	}

}