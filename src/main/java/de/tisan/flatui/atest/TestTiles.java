package de.tisan.flatui.atest;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import de.tisan.flatui.components.fcheckbox.FlatCheckBox;
import de.tisan.flatui.components.fcommons.FlatColors;
import de.tisan.flatui.components.fcommons.FlatLayoutManager;
import de.tisan.flatui.components.fframe.FlatFrame;
import de.tisan.flatui.components.ficon.FlatIconFont;
import de.tisan.flatui.components.flatoptionpanes.FlatOptionPane;
import de.tisan.flatui.components.flatoptionpanes.FlatOptionPaneButton;
import de.tisan.flatui.components.flatoptionpanes.FlatOptionPaneTemplates;
import de.tisan.flatui.components.flisteners.MouseClickedHandler;
import de.tisan.flatui.components.flisteners.MouseDragHandler;
import de.tisan.flatui.components.flisteners.MouseEnterHandler;
import de.tisan.flatui.components.flisteners.MouseLeaveHandler;
import de.tisan.flatui.components.flisteners.MouseListener;
import de.tisan.flatui.components.flisteners.MouseMoveHandler;
import de.tisan.flatui.components.flisteners.MousePressHandler;
import de.tisan.flatui.components.flisteners.MouseReleaseHandler;
import de.tisan.flatui.components.flisteners.Priority;
import de.tisan.flatui.components.ftilemenu.FlatTile;
import de.tisan.flatui.components.ftilemenu.FlatTileListener;
import de.tisan.flatui.components.ftilemenu.FlatTileMenu;
import de.tisan.flatui.components.ftilemenu.FlatTileMenuGroup;
import de.tisan.flatui.components.ftilemenu.FlatTileSize;

public class TestTiles extends FlatFrame {

	private JPanel contentPane;

	public static void main(String[] args) throws IOException {
		new TestTiles().setVisible(true);
	}
	public TestTiles() throws IOException {
		super("");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1600, 500);
		setAlwaysOnTop(true);
		setBackgroundBlur(false);
		removeBackground();
		FlatLayoutManager man = FlatLayoutManager.get(this);
		man.setResizable(true);
		FlatTileMenu menu = new FlatTileMenu(man);
		menu.setBounds(0, 35, getWidth() - 10, getHeight() - 45);
		FlatTileMenuGroup g = new FlatTileMenuGroup("Allgemein");
		FlatTile t1 = new FlatTile();
		t1.setIcon(FlatIconFont.USERS);
		FlatTile t11 = new FlatTile();
		t11.setIcon(FlatIconFont.CALENDAR);
		t11.setBackground(FlatColors.PURPLE);
		FlatTile t2 = new FlatTile();
		t2.setSize(FlatTileSize.BIG);
		t2.setIcon(FlatIconFont.SUN_O);
		t2.setBackground(FlatColors.GREEN);
		FlatTile t3 = new FlatTile();
		t3.setSize(FlatTileSize.WIDE);
		t3.setIcon(FlatIconFont.ENVELOPE);
		t3.setBackground(FlatColors.BLUE);
		t1.setName("Kontakte");
		t11.setName("Kalender");
		t2.setName("Wetter");
		t3.setName("Mail");
		g.addTile(t1);
		g.addTile(t11);
		g.addTile(t2);
		g.addTile(t3);
		menu.addFlatTileMenuGroup(g);

		FlatTileMenuGroup gg = new FlatTileMenuGroup("Erweiterte Funktionen");
		FlatTile tt1 = new FlatTile();
		tt1.setBackground(FlatColors.DARKTURQUOISE);
		FlatTile tt11 = new FlatTile();
		tt11.setBackground(FlatColors.LIGHTBLUE);
		tt11.setIcon(FlatIconFont.FACEBOOK);
		tt11.addListener(new FlatTileListener() {
			
			@Override
			public void onDrag() {
				
			}
			
			@Override
			public void onClick() {
				try {
					new ProcessBuilder("C:\\Program Files\\Internet Explorer\\iexplore.exe","http://facebook.com").start();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		FlatTile tt12 = new FlatTile();
		FlatTile tt2 = new FlatTile();
		tt2.setSize(FlatTileSize.BIG);
		tt2.setBackground(FlatColors.CARROT);
		tt2.setIcon(FlatIconFont.REBEL);
		tt2.addListener(new FlatTileListener() {
			
			@Override
			public void onDrag() {
				
			}
			
			@Override
			public void onClick() {
				FlatOptionPaneButton btn1 = new FlatOptionPaneButton("Ja", null);
				FlatOptionPane p = FlatOptionPane.getMessageDialog("Festplatte wirklich formatieren?", "Möchten Sie wirklich alle Dateien auf Ihrem Computer und Ihrem Facebook-Account löschen?", FlatOptionPaneTemplates.createYesButton("Ja"), FlatOptionPaneTemplates.createYesButton("Ja"));
				p.showDialog();
				p.setAlwaysOnTop(true);
			}
		});
		//tt2.setImage(ImageIO.read(TestTiles.class.getResourceAsStream("/de/tisan/resources/windows.png")));
		FlatTile tt3 = new FlatTile();
		tt3.setIcon(FlatIconFont.WINDOWS);
		tt3.setSize(FlatTileSize.WIDE);
		tt3.setBackground(FlatColors.BLUE);
		tt1.setName("Spiele");
		tt1.setIcon(FlatIconFont.GAMEPAD);
		tt11.setName("Facebook");
		tt12.setName("Skype");
		tt12.setIcon(FlatIconFont.SKYPE);
		tt2.setName("Explorer");
		tt3.setName("Windows 10");
		gg.addTile(tt1);
		gg.addTile(tt11);
		gg.addTile(tt12);

		gg.addTile(tt2);
		gg.addTile(tt3);

		menu.addFlatTileMenuGroup(gg);
		addComponentListener(new ComponentListener() {

			@Override
			public void componentShown(ComponentEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void componentResized(ComponentEvent e) {
				menu.setBounds(0, 35, getWidth() - 10, getHeight() - 45);
			}

			@Override
			public void componentMoved(ComponentEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void componentHidden(ComponentEvent e) {
				// TODO Auto-generated method stub

			}
		});
		
		
		FlatCheckBox box = new FlatCheckBox("Verschieben", man);
		box.addListener(Priority.NORMAL, new MouseListener() {
			
			@Override
			public void onMouseRelease(MouseReleaseHandler handler) {
				menu.setEditable(!box.isChecked());
				System.out.println("hi " + menu.isEditable());
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
		box.setBounds(400,30,120,20);
//		contentPane.remove(box);
		//contentPane.add(box, 0);
	}

}
