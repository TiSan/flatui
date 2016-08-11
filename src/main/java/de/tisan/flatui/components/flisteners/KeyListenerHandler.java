package de.tisan.flatui.components.flisteners;

import java.awt.event.KeyEvent;

public class KeyListenerHandler extends ListenerHandler {
	private KeyEvent event;

	public KeyListenerHandler(KeyEvent event) {
		this.event = event;
	}

	public KeyEvent getEvent() {
		return event;
	}

}
