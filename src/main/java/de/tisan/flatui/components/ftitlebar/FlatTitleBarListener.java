package de.tisan.flatui.components.ftitlebar;
/**
 * Hosts several method listeners for the FlatTitleBar interaction with the user.
 * @author TiSan
 *
 */
public interface FlatTitleBarListener {
	/**
	 * If close button is pressed
	 */
	public void onCloseButtonPressed();
	/**
	 * If Maximize button is pressed
	 */
	public void onMaximizeButtonPressed();
	/**
	 * If minimize button is pressed
	 */
	public void onMinimizeButtonPressed();
	/**
	 * If minimize button is released
	 */
	public void onCloseButtonReleased();
	/**
	 * If Maximize button is released
	 */
	public void onMaximizeButtonReleased();
	/**
	 * If Minimize button is released
	 */
	public void onMinimizeButtonReleased();
	/**
	 * If on close button is mouse moved
	 */
	public void onCloseButtonMouseMove();
	/**
	 * If on maximize button is mouse moved
	 */
	public void onMaximizeButtonMouseMove();
	/**
	 * If on minimize button is mouse moved
	 */
	public void onMinimizeButtonMouseMove();
	/**
	 * DEPRECATED
	 */
	@Deprecated
	public void onWindowDragged();
	/**
	 * DEPRECATED
	 */
	@Deprecated
	public void onImageClicked();

}
