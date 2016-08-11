package de.tisan.flatui.components.flisteners;

public interface KeyListener extends FlatListener{
	
	public void onKeyPress(KeyPressHandler handler);
	public void onKeyRelease(KeyReleaseHandler handler);
}
