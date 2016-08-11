package de.tisan.flatui.components.flisteners;

public interface MouseMotionListener extends MouseListener {
	@Override
	public default void onMouseClicked(MouseClickedHandler handler) {

	}

	@Override
	public default void onMousePress(MousePressHandler handler) {

	}

	@Override
	public default void onMouseRelease(MouseReleaseHandler handler) {

	}

	@Override
	public void onMouseDrag(MouseDragHandler handler);

	@Override
	public void onMouseEnter(MouseEnterHandler handler);

	@Override
	public void onMouseLeave(MouseLeaveHandler handler);

	@Override
	public void onMouseMove(MouseMoveHandler handler);
}
