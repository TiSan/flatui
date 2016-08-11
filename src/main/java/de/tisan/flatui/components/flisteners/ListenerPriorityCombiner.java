package de.tisan.flatui.components.flisteners;

public class ListenerPriorityCombiner {
	private Priority priority;
	private FlatListener listener;
	
	public ListenerPriorityCombiner(Priority prio, FlatListener listener){
		this.priority = prio;
		this.listener = listener;
	}

	public Priority getPriority() {
		return priority;
	}

	public FlatListener getListener() {
		return listener;
	}
	
}
