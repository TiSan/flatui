package de.tisan.flatui.components.flisteners;

public class ListenerHandler {
	private boolean cancel;

	public ListenerHandler() {
		this.cancel = false;
	}

	public boolean isCancel() {
		return cancel;
	}

	public void setCancel(boolean cancel) {
		this.cancel = cancel;
	}

}
