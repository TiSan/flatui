package de.tisan.flatui.atest;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import de.tisan.flatui.components.fcommons.Anchor;
import de.tisan.flatui.components.flatoptionpanes.FlatOptionPane;
import de.tisan.flatui.helpers.resizehelpers.SwingResizeHelper;

public class OnlyForDaniel extends JFrame {

	private JPanel contentPane;

	public static void main(String[] args) {
		//FlatOptionPaneButton btn1 = new FlatOptionPaneButton("Test", null);
//		FlatOptionPaneButton btn1 = FlatOptionPaneTemplates.createYesButton("Ja");
//		FlatOptionPaneButton btn2 = FlatOptionPaneTemplates.createYesButton("Dieser Button hat einen längeren Text");
//		btn2.addActionListener(Priority.NORMAL, new ActionListener() {
//			
//			@Override
//			public void onAction(MouseClickedHandler handler) {
//				System.out.println("hi");
//			}
//		});
//		FlatOptionPane pane = FlatOptionPane.getMessageDialog("Hallo!", "Ich bin eine Box mit Aufmerksamkeitsdefizithypersyndrom (kurz ADHS)!", FlatColors.RED, FlatColors.TEXT, false, true, true, 550, 200, btn1, btn2);
//		
//		pane.showDialog();
		
		FlatOptionPane.getYesNoCancelDialog("TEst", "Lorem ipsum dolor sit amet").showDialog();
		EventQueue.invokeLater(new Runnable() {
			public void run() { 
				try { 
					OnlyForDaniel frame = new OnlyForDaniel();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public OnlyForDaniel() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(null);
		contentPane.setLayout(null);
		SwingResizeHelper helper = SwingResizeHelper.getResizeHelperForSwing(this);
		JLabel lbl = new JLabel("Dies ist ein Test");
		lbl.setBounds(20,20,50,50);
		lbl.setBackground(Color.gray);
		helper.addComponent(lbl, Anchor.RIGHT);
		contentPane.add(lbl);
		setContentPane(contentPane);
	}

}
