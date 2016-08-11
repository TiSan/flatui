package de.tisan.flatui.atest;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import de.tisan.flatui.components.fcommons.FlatColors;
import de.tisan.flatui.helpers.fadehelper.FadeHelper;

public class TestPanelHide extends JFrame {

	private JPanel contentPane;
	private JPanel pnlTarget;
	//private Painter pnlPainter;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestPanelHide frame = new TestPanelHide();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TestPanelHide() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 730, 600);
		contentPane = new JPanel();
		contentPane.setBorder(null);
		contentPane.setLayout(null);
		setContentPane(contentPane);
		pnlTarget = new JPanel();
		pnlTarget.setBounds(20, 20, 500, 500);
		pnlTarget.setBackground(FlatColors.BACKGROUND);
		JButton btn1 = new JButton("Hide");
		btn1.setBounds(590, 20, 100, 50);
		contentPane.add(btn1);
		JButton btn2 = new JButton("Show");
		btn2.setBounds(590, 150, 100, 50);
		contentPane.add(btn2);
		contentPane.add(pnlTarget);
//		pnlPainter = new Painter();
//		pnlPainter.setBounds(400, 20, 300, 300);
//		// pnlPainter.setBackground();
//		contentPane.add(pnlPainter);
		btn1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				FadeHelper.fadeOut(pnlTarget);		
			}
		});
		btn2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				FadeHelper.fadeIn(pnlTarget);		
			}
		});
	}

		
}
