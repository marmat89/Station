package it.unibo.system;

import it.unibo.debugger.OnlineDebugger;
import it.unibo.util.Configurator;

/**
 * A machine may function as a debugger service, this class allows to implement
 * a debugger that runs an online control, the timing that is given to the
 * machine is usually 1 hour.
 * 
 * @author matteo.mariani11@studio.unibo.it
 * @version 1.0.0
 * @since 06/feb/2015 01:36:19
 *
 */
public class OnlineDebuggerTH implements Runnable {
	OnlineDebugger deb;
 int UpdeteTime;
	public void ThreadCreation() {
		Configurator conf = new Configurator("conf.xml", "configuration");
		 UpdeteTime=(conf.getTagValueINT("DEBUG")*1000);
		deb = new OnlineDebugger();
		// THREAD finder Start
		Thread thDebug = new Thread(this);
		thDebug.start();
	}

	/**
	 * start error check thread
	 */
	@Override
	public void run() {
		while (true) {
			deb.checkMachineConnection();
			deb.checkReliabilitySensor();
			try {
				Thread.sleep(UpdeteTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		OnlineDebuggerTH deb = new OnlineDebuggerTH();
		deb.ThreadCreation();
	}

}
