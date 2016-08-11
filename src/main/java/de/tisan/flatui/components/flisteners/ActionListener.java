package de.tisan.flatui.components.flisteners;

public interface ActionListener extends MouseListener {

	@Override
	public default void onMouseClicked(MouseClickedHandler handler) {
		onAction(handler);
	}

	public void onAction(MouseClickedHandler handler);

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

	@Override
	public default void onMousePress(MousePressHandler handler) {

	}

	@Override
	public default void onMouseRelease(MouseReleaseHandler handler) {

	}
}
