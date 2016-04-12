package it.unibo.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.util.Enumeration;
import java.util.Observable;

/**
 * 
 * @author matteo.mariani11@studio.unibo.it
 * @version 1.0.0
 * @since 05/feb/2015 22:18:50
 *
 */
public class SerialCom extends Observable implements SerialPortEventListener {
	public SerialCom() {
		super();
		Configurator conf = new Configurator("conf.xml", "configuration");

		MY_PORT = conf.getTagValueSTR("SERIAL_PORT");
		DATA_RATE = conf.getTagValueINT("DATA_RATE");
	}

	SerialPort serialPort;
	/** The port we're normally going to use. */
	private static final String PORT_NAMES[] = { "/dev/tty.usbmodemfd131","/dev/tty.usbmodemfd121",  // Mac
																			// OS
																			// X
			"/dev/ttyACM0", // Raspberry Pi
			"/dev/ttyUSB0", // Linux
			"COM4", // Windows
	};
	/**
	 * A BufferedReader which will be fed by a InputStreamReader converting the
	 * bytes into characters making the displayed results codepage independent
	 */
	private BufferedReader input;
	/** The output stream to the port */
	private OutputStream output;
	/** Milliseconds to block while waiting for port open */
	private static final int TIME_OUT = 2000;
	/** Default bits per second for COM port. */
	private int DATA_RATE;
	private String MY_PORT;
	private String someVariable = "";

	/**
	 * 
	 * @return
	 */
	public synchronized String getSomeVariable() {
		return someVariable;
	}

	public Boolean initialize() {
		/**
		 * the next line is for Raspberry Pi and // gets us into the while loop
		 * and was suggested here was suggested
		 * http://www.raspberrypi.org/phpBB3/viewtopic.php?f=81&t=32186 //
		 * System.setProperty("gnu.io.rxtx.SerialPorts", "/dev/ttyACM0");
		 */
		CommPortIdentifier portId = null;
		Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

		/**
		 * // First, Find an instance of serial port as set in PORT_NAMES.
		 */
		while (portEnum.hasMoreElements()) {
			CommPortIdentifier currPortId = (CommPortIdentifier) portEnum
					.nextElement();
			for (String portName : PORT_NAMES) {
				if (currPortId.getName().equals(portName)) {
					portId = currPortId;
					break;

				}
			}
		}
		portEnum = CommPortIdentifier.getPortIdentifiers();
		if (portId == null) 
		while (portEnum.hasMoreElements()) {
			CommPortIdentifier currPortId = (CommPortIdentifier) portEnum
					.nextElement();

			if (currPortId.getName().equals(MY_PORT)) {
				portId = currPortId;
				break;
			}
		}
		if (portId == null) {
			System.out.println("Could not find COM port.");
			return false;
		}

		try {
			/**
			 * open serial port, and use class name for the appName.
			 */
			serialPort = (SerialPort) portId.open(this.getClass().getName(),
					TIME_OUT);

			/**
			 * set port parameters
			 */
			serialPort.setSerialPortParams(DATA_RATE, SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
			/**
			 * Give the Arduino some time
			 */
			try {
				Thread.sleep(2000);
			} catch (InterruptedException ie) {
			}

			/**
			 * open the streams
			 */
			input = new BufferedReader(new InputStreamReader(
					serialPort.getInputStream()));
			output = serialPort.getOutputStream();

			/**
			 * add event listeners
			 */
			serialPort.addEventListener(this);
			serialPort.notifyOnDataAvailable(true);

		} catch (Exception e) {
			System.err.println(e.toString());
			return false;
		}
		return true;
	}

	/**
	 * Request Metod used for send measure request to Arduino
	 * 
	 * @param data
	 *            Request string, need to terminate with "="
	 */
	public void sendData(String data) {
		try {
			System.out.println("Sending data: '" + data + "'");
			output = serialPort.getOutputStream();
			output.write(data.getBytes());
		} catch (Exception e) {
			System.err.println(e.toString());
			System.exit(0);
		}
	}

	/**
	 * This should be called when you stop using the port. This will prevent
	 * port locking on platforms like Linux.
	 */
	public synchronized void close() {
		System.out.println("PortClosed");
		/**
		 * Give the Arduino some time
		 */
		try {
			Thread.sleep(2000);
		} catch (InterruptedException ie) {
		}
		if (serialPort != null) {
			serialPort.removeEventListener();
			serialPort.close();
		}
	}

	/**
	 * Handle an event on the serial port. Read the data and print it.
	 */
	public synchronized void serialEvent(SerialPortEvent oEvent) {
		if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
			try {
				String inputLine = input.readLine();
				System.out.println(inputLine);
				setSomeVariable(inputLine);
			} catch (Exception e) {
				System.err.println(e.toString());
			}
		}
		// Ignore all the other eventTypes, but you should consider the other
		// ones.
	}

	/**
	 * 
	 * @param inputLine
	 */
	public void setSomeVariable(String inputLine) {
		synchronized (this) {
			this.someVariable = inputLine;
		}
		setChanged();
		notifyObservers();
	}

	public static void main(String[] args) throws Exception {
		SerialCom main = new SerialCom();
		main.initialize();
		main.sendData("getTemp=");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException ie) {
		}
		main.sendData("getSpeed=");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException ie) {
		}
		main.sendData("getHumidity=");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException ie) {
		}
		main.sendData("getRain=");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException ie) {
		}
		main.sendData("getLight=");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException ie) {
		}
		main.close();

		// Wait 5 seconds then shutdown
		try {
			Thread.sleep(2000);
		} catch (InterruptedException ie) {
		}
		Thread t = new Thread() {
			public void run() {
				// the following line will keep this app alive for 1000 seconds,
				// waiting for events to occur and responding to them (printing
				// incoming messages to console).
				try {
					Thread.sleep(1000000);
				} catch (InterruptedException ie) {
				}
			}
		};
		t.start();
		System.out.println("Started");
	}
}