package de.tisan.flatui.components.flisteners;

public interface MouseListener extends FlatListener {
	public void onMousePress(MousePressHandler handler);

	public void onMouseRelease(MouseReleaseHandler handler);

	public void onMouseMove(MouseMoveHandler handler);

	public void onMouseDrag(MouseDragHandler handler);

	public void onMouseEnter(MouseEnterHandler handler);

	public void onMouseLeave(MouseLeaveHandler handler);
	
	public void onMouseClicked(MouseClickedHandler handler);
}
