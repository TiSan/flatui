package de.tisan.flatui.components.fcommons;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import de.tisan.flatui.components.ffont.FlatFont;
/**
 * Displays a small credit frame on the screen.
 * Its cool if you are let it on, otherwise you can disable it with 'FlatLayoutManager.showCreditFrame = false'
 * @author TiSan
 *
 */
public class GUIPoweredBy extends JFrame {

	private static final long serialVersionUID = 897350133622696946L;
	private JPanel contentPane;

	
	public GUIPoweredBy() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setUndecorated(true);
		try {
			setOpacity(0F);
		} catch (Exception ex) {
			System.err.println("[FlatUI] Your operation system does not support opaque frames. :(");
		}

		setAlwaysOnTop(true);
		setBounds((int) dim.getWidth() - 300, (int) dim.getHeight() - 40, 300, 40);
		// setExtendedState(JFrame.MAXIMIZED_BOTH);
		contentPane = new JPanel();
		contentPane.setBorder(null);
		contentPane.setLayout(null);
		contentPane.setBackground(FlatColors.BACKGROUND);
		JLabel lbl1 = new JLabel("Powered by");
		lbl1.setBounds(5, -5, 100, 40);
		lbl1.setFont(FlatFont.getInstance(12, Font.PLAIN));
		lbl1.setForeground(FlatColors.WHITE);
		contentPane.add(lbl1);
		JLabel lbl2 = new JLabel("FlatUI!");
		lbl2.setBounds(120, -1, 100, 40);
		lbl2.setFont(FlatFont.getInstance(24, Font.PLAIN));
		lbl2.setForeground(FlatColors.WHITE);
		contentPane.add(lbl2);
		JLabel lbl3 = new JLabel("Version " + FlatUI.version);
		lbl3.setBounds(190, 7, 100, 40);
		lbl3.setFont(FlatFont.getInstance(10, Font.PLAIN));
		lbl3.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl3.setForeground(FlatColors.WHITE);
		contentPane.add(lbl3);
		setContentPane(contentPane);
	}

	public void display() {
		setVisible(true);
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
					int z = 0;
					int time = 1;
					while (true) {
						z++;
						if (z == 40) {
							break;
						}
						setBounds((int) dim.getWidth() - 300, (int) dim.getHeight() - z, 300, 40);
						float f = (float) z / 40;
						// System.out.println(f);
						setOpacity(f);
						if (f > 0.5) {
							time += 1;
						}
						try {
							Thread.sleep(time);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				} catch (Exception ex) {
					//System.err.println("[FlatUI] Your operation system does not support opaque frames. :(");
				} finally {
					setOpacity(1F);
				}

				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				hideWindow();
			}
		}).start();

	}

	public void hideWindow() {

		new Thread(new Runnable() {

			@Override
			public void run() {
				try{
					Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
					int z = 40;
					int time = 1;
					while (true) {
						z--;
						if (z == 0) {
							break;
						}
						setBounds((int) dim.getWidth() - 300, (int) dim.getHeight() - z, 300, 40);
						float f = (float) z / 40;
						// System.out.println(f);
						setOpacity(f);
						if (f < 0.5) {
							time += 1;
						}
						try {
							Thread.sleep(time);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				} catch(Exception ex){
					//System.out.println("[FlatUI] Your operation system does not support opaque frames. :(");
				} finally {
					setOpacity(0F);
					
				}
				
				setVisible(false);
				dispose();
			}
		}).start();

	}
}
