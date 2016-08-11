package de.tisan.flatui.components.fsidemenu;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import de.tisan.flatui.components.fcommons.FlatColors;
import de.tisan.flatui.components.fcommons.FlatLayoutManager;
import de.tisan.flatui.components.ftogglebtn.FlatToggleButton;

public class GUIMenuTest extends JFrame {

	private static final long serialVersionUID = 5148872514636332613L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUIMenuTest frame = new GUIMenuTest();
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
	public GUIMenuTest() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 700);
		contentPane = new JPanel();
		contentPane.setBorder(null);
		contentPane.setLayout(null);
		contentPane.setBackground(FlatColors.WHITE);
		setContentPane(contentPane);
		// setUndecorated(true);
		FlatLayoutManager man = FlatLayoutManager.get(this);
		FlatSideMenu menu = new FlatSideMenu(man);
		menu.setBounds(0, 0, getWidth(), getHeight());
		contentPane.add(menu);
		FlatSideMenuEntry e = new FlatSideMenuEntry("Test1", null, menu);
		menu.addRootEntry(e);
		FlatSideMenuEntry e2 = new FlatSideMenuEntry("Test2", null, menu);
		menu.addRootEntry(e2);
		// e2.setIcon(FlatIconFont.ALIGN_CENTER);
		FlatSideMenuEntry e3 = new FlatSideMenuEntry("Test3", null, menu);
		menu.addRootEntry(e3);
		JPanel pnl = new JPanel();
		FlatToggleButton btn = new FlatToggleButton(man);
		btn.setLocked(false);
		btn.setOn(true);
		contentPane.remove(btn);
		btn.setBounds(50, 50, 100, 50);
		pnl.add(btn);
		pnl.setLayout(null);
		pnl.setBounds(0, 0, getWidth(), getHeight());
		pnl.setBackground(FlatColors.BACKGROUND);
		// contentPane.add(pnl, 0);
		// contentPane.remove(menu);
		// btn.setBounds(0, 0, 50, 50);
		e3.setContentPane(pnl);
		FlatSideMenuEntry e31 = new FlatSideMenuEntry("Test5", null, menu);
		e3.addEntry(e31);

		FlatSideMenuEntry e4 = new FlatSideMenuEntry("Test4", null, menu);
		menu.addRootEntry(e4);
	}

}
