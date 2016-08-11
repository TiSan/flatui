package de.tisan.flatui.components.flisteners;

public class Priority {
	private String name;
	private int weight;

	private Priority(String name, int weight) {
		this.name = name;
		this.weight = weight;
	}

	public String getName() {
		return name;
	}

	public int getWeight() {
		return weight;
	}

	public final static Priority LOW = new Priority("LOW", 1);
	public final static Priority NORMAL = new Priority("NORMAL", 2);
	public final static Priority HIGH = new Priority("HIGH", 3);

}
