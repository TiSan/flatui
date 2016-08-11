package de.tisan.flatui.components.ftitlebar;

import java.awt.Frame;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
/**
 * This class do the default interaction event handlers for the FlatTitleBar. Use this to avid stressful recoding of the buttons. 
 * @author TiSan
 *
 */
public class DefaultFlatTitleBarListener implements FlatTitleBarListener {
	private JFrame frame;

	public DefaultFlatTitleBarListener(JFrame frame) {
		this.frame = frame;
	}

	@Override
	public void onWindowDragged() {
		
	}

	@Override
	public void onMinimizeButtonReleased() {
		frame.setExtendedState(Frame.ICONIFIED);
	}

	@Override
	public void onMinimizeButtonPressed() {

	}

	@Override
	public void onMinimizeButtonMouseMove() {

	}

	@Override
	public void onMaximizeButtonReleased() {
		if (frame.getExtendedState() == Frame.MAXIMIZED_BOTH) {
			frame.setExtendedState(Frame.NORMAL);
		} else {
			frame.setExtendedState(Frame.MAXIMIZED_BOTH);
		}
	}

	@Override
	public void onMaximizeButtonPressed() {

	}

	@Override
	public void onMaximizeButtonMouseMove() {

	}

	@Override
	public void onImageClicked() {

	}

	@Override
	public void onCloseButtonReleased() {
		frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
	}

	@Override
	public void onCloseButtonPressed() {

	}

	@Override
	public void onCloseButtonMouseMove() {

	}
}
