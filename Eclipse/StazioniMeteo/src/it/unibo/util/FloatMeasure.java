package it.unibo.util;

/**
 * 
 * @author matteo.mariani11@studio.unibo.it
 * @version 1.0.0
 * @since 05/feb/2015 22:17:19
 *
 */
public class FloatMeasure extends Measure {
	private float value;

	/**
	 * 
	 * @param simulated
	 * @param value
	 */
	public FloatMeasure(boolean simulated, float value) {
		super(simulated);
		this.value = value;
	}

	/**
 * 
 */
	public Object getValue() {
		return value;
	}
}
