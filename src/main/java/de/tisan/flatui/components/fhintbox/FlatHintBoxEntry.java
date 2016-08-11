
package de.tisan.flatui.components.fhintbox;

/**
 * An entry into the FlatHintBox.
 * 
 * @author TiSan
 * 
 */
public class FlatHintBoxEntry {

	private int priority;
	private int timeout;
	private String header;
	private String message;
	private boolean progressBarVisible;
	private boolean timeoutt;

	/**
	 * Creates an entry with a progress bar timer.
	 * 
	 * @param title
	 *            Title of box
	 * @param message
	 *            Message of box
	 * @param timeout
	 *            timeout in sec. (After how many seconds should the message
	 *            close?)
	 */
	public FlatHintBoxEntry(String title, String message, int timeout) {
		// this.priority = priority;
		this.timeout = timeout;
		this.timeoutt = true;
		this.progressBarVisible = true;
		this.header = title;
		this.message = message;
	}

	/**
	 * Creates an entry with a button and without a timer.
	 * 
	 * @param title
	 *            Title of box
	 * @param message
	 *            Message of box
	 */
	public FlatHintBoxEntry(String title, String message) {
		// this.priority = priority;
		this.timeout = -1;
		this.timeoutt = false;
		this.progressBarVisible = false;
		this.header = title;
		this.message = message;
	}

	protected boolean hasTimeout() {
		return timeoutt;
	}

	protected int getPriority() {
		return priority;
	}

	/**
	 * Returns the timeout which is set.
	 * 
	 * @return Timeout
	 */
	public int getTimeout() {
		return timeout;
	}

	/**
	 * Returns the title of the box
	 * 
	 * @return Title
	 */
	public String getTitle() {
		return header;
	}

	/**
	 * Returns the message of the box
	 * 
	 * @return Message
	 */
	public String getMessage() {
		return message;
	}

	protected boolean isProgressBar() {
		return this.progressBarVisible;
	}

}
