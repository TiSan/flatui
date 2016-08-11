package de.tisan.flatui.atest;

import java.awt.EventQueue;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

import de.tisan.flatui.components.fcommons.FlatColors;
import de.tisan.flatui.components.floading.FlatCircleLoading;
import de.tisan.flatui.components.floading.FlatCircleLoading.Dot;

public class TestLoading extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestLoading frame = new TestLoading();
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
	public TestLoading() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(null);
		contentPane.setLayout(null);
		contentPane.setBackground(FlatColors.BACKGROUND);
		setContentPane(contentPane);
		FlatCircleLoading p = new FlatCircleLoading(10, null);
		p.startAnimation();
		p.setBounds(0, 0, getWidth(), getHeight());
		contentPane.add(p);
		addComponentListener(new ComponentListener() {

			@Override
			public void componentResized(ComponentEvent e) {
				p.setBounds(0, 0, getWidth(), getHeight());
			}

			@Override
			public void componentMoved(ComponentEvent e) {

			}

			@Override
			public void componentHidden(ComponentEvent e) {

			}

			@Override
			public void componentShown(ComponentEvent e) {

			}
		});
		p.setShowPercent(true);
		new Thread(new Runnable() {

			@Override
			public void run() {
				Random rnd = new Random();
				for (int i = 0; i <= 100; i++) {
					//p.setPercent(rnd.nextInt(100));
					p.setPercent(i);
					for(Dot d : p.getDots()){
						d.setColor(FlatColors.colors.get(rnd.nextInt(FlatColors.colors.size())));
					}
					try {
						Thread.sleep(100 + rnd.nextInt(300));
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

}
