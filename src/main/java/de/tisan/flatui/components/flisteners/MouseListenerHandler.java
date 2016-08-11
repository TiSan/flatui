package de.tisan.flatui.components.flisteners;

import java.awt.event.MouseEvent;

public class MouseListenerHandler extends ListenerHandler {
	private MouseEvent event;

	public MouseListenerHandler(MouseEvent event) {
		this.event = event;
	}

	public MouseEvent getEvent() {
		return event;
	}

}
