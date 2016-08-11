package de.tisan.flatui.components.flisteners;

public interface MouseKeyListener extends MouseListener {
	@Override
	public void onMouseClicked(MouseClickedHandler handler);

	@Override
	public void onMousePress(MousePressHandler handler);

	@Override
	public void onMouseRelease(MouseReleaseHandler handler);

	@Override
	public default void onMouseDrag(MouseDragHandler handler) {

	}

	@Override
	public default void onMouseEnter(MouseEnterHandler handler) {

	}

	@Override
	public default void onMouseLeave(MouseLeaveHandler handler) {

	}

	@Override
	public default void onMouseMove(MouseMoveHandler handler) {

	}
}
