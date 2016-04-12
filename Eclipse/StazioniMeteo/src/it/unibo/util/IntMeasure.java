package it.unibo.util;

/**
 * 
 * @author matteo.mariani11@studio.unibo.it
 * @version 1.0.0
 * @since 05/feb/2015 22:32:16
 *
 */
public class IntMeasure extends Measure {
	private int percentage;

	/**
	 * 
	 * @param simulated
	 * @param percentage
	 */
	public IntMeasure(boolean simulated, int percentage) {
		super(simulated);
		this.percentage = percentage;
	}

	@Override
	/**
	 * 
	 */
	public Object getValue() {
		return percentage;
	}

}
