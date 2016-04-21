package it.unibo.util;

import it.unibo.debugger.OnBoardDebugger;
import it.unibo.interfaces.Sensor;
import it.unibo.interfaces.SensorArduino;
import it.unibo.interfaces.StationRPI;
import it.unibo.station.impl.AirMonitor;

/**
 * 
 * @author matteo.mariani11@studio.unibo.it
 * 
 *         Sensor need attend a serial replay, thread permits attending for 5
 *         second serial COM port replay
 * @version 1.0.0
 * @since 05/feb/2015 21:26:17
 *
 */
public class AttendRispThread implements Runnable {
	SensorArduino sen;
	OnBoardDebugger deb;

	/**
	 * Thread attending repaly creation
	 * 
	 * @param deb
	 *            Same Sensor have an on-board Debugger that update on the DB
	 */
	public void ThreadCreation(OnBoardDebugger deb, Sensor sen) {
		this.sen = (SensorArduino) sen;
		this.deb = deb;
		// THREAD finder Start
		Thread findTh = new Thread(this);
		findTh.start();
	}

	public void run() {

		try {
			int i = 0;
			while (i < 1) {
				Thread.sleep(2000);
				i++;
			}
			if (sen.value == "") {
				System.out.println("Thread Request OUT OF TIME");
				deb.SerialReplyProblem(sen);
				synchronized (sen) {
					sen.notify();
				}
			}
		} catch (InterruptedException exc) {
		}

	}
}