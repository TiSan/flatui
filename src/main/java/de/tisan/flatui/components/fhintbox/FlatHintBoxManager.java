package de.tisan.flatui.components.fhintbox;

import java.util.ArrayList;

/**
 * Important Manager for the HintBoxes.
 * 
 * @author TiSan
 * 
 */
public class FlatHintBoxManager {
	ArrayList<FlatHintBoxEntry> boxes;
	private FlatHintBox box;
	private ArrayList<FlatHintBoxEntry> queue;
	private ArrayList<FlatHintBoxListener> listeners;

	/**
	 * Creates a new instance of FlatHintBoxManager. One Manager per application
	 * is enough.
	 */
	public FlatHintBoxManager() {
		this.boxes = new ArrayList<FlatHintBoxEntry>();
		this.queue = new ArrayList<FlatHintBoxEntry>();
		this.listeners = new ArrayList<FlatHintBoxListener>();
		this.box = new FlatHintBox();
		box.setAlwaysOnTop(true);
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				int temp = 0;
				boolean skip = false;
				while (true) {
					while (queue.size() != 0) {
						
						if (queue.get(0).hasTimeout()) {
							if (!skip && (temp/10) <= queue.get(0).getTimeout()) {
								if (!box.isShowBox()) {
									// System.out.println("Showing box");
									box.showBox(false);
								}
								box.setTitle(queue.get(0).getTitle());
								box.setText(queue.get(0).getMessage());
								if (queue.get(0).isProgressBar()) {
									if (temp == 0) {
										box.enableProgressBar(temp, queue.get(0).getTimeout(), true);
									} else {
										box.enableProgressBar(temp, queue.get(0).getTimeout(), false);
									}

								}
								
								temp++;
								if(box.confirmed){
									for(FlatHintBoxListener l : listeners){
										l.onClick(queue.get(0), box);
									}
									//temp = (queue.get(0).getTimeout()*10)+1;
									skip = true;
									temp = 0; 
								}
								try {
									Thread.sleep(100);
								} catch (InterruptedException e) {

								}
							} else {
								temp = 0;
								queue.remove(0);
								box.setConfirmed(false);
								skip = false;
								//System.out.println("set to false  1");
								if (queue.size() == 0) {
									box.hideBox();
								}
							}

						} else {

							box.setTitle(queue.get(0).getTitle());
							box.setText(queue.get(0).getMessage());
							box.enableButton();
							if (!box.isShowBox()) {
								// System.out.println("Showing box");
								box.showBox(true);
							}
							// System.out.println("Wait if confirmed");
							Thread t = new Thread(new Runnable() {

								@Override
								public void run() {
									// System.out.println("Waiting.");
									while (!box.confirmed) {
										System.out.print("");
										try {
											Thread.sleep(10);
										} catch (InterruptedException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}
									//System.out.println("Box confirmed, fire event");
									// System.out.println("Finish Waiting.");
									for(FlatHintBoxListener l : listeners){
										l.onClick(queue.get(0), box);
									}
								}
							});
							t.setPriority(Thread.MAX_PRIORITY);
							t.start();
							try {
								t.join();
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							// System.out.println("Confirmed");
							temp = 0;
							queue.remove(0);
							box.setConfirmed(false);
							skip = false;
							//System.out.println("set to false  2");
							if (queue.size() == 0) {
								// System.out.println("hide box");
								box.hideBox();
							}
						}
					}
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// System.out.println("Exit");
						break;
					}

				}
				// System.out.println("end");
			}

		});
		// t.setPriority(Thread.MAX_PRIORITY);
		t.start();
	}

	/**
	 * Shows the specified HintBox. If you want to show multiple boxes at the
	 * same time, the queue will be filled.
	 * 
	 * @param box
	 *            The box that should be viewed on the desktop
	 */
	public void showHintBox(FlatHintBoxEntry box) {
		queue.add(box);

		// TODO
		/*
		 * Logik, damit eine Box angezeigt wird Wenn mehrere Boxen offen sind,
		 * wird die neueste angezeigt, der Timer f�r die alten wird angehalten
		 * und nach den neuen wieder angezeigt. ProgressBar in HintBox
		 * einblenden: a) Zum Anzeigen der restlichen Wartezeit, bis Meldung
		 * verschwindet b) private static Font instance;Zum individuellen
		 * Einstellen eines Wertes der Progressbar c) �berhaupt keine
		 * ProgressBar. Blinken der Box bei neuer (oder aktualisierter) Message
		 */
	}

	public void addFlatHintBoxListener(FlatHintBoxListener l) {
		this.listeners.add(l);
	}

}
