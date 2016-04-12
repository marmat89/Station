package it.unibo.util;

/**
 * 
 * @author matteo.mariani11@studio.unibo.it
 * @version 1.0.0
 * @since 05/feb/2015 22:16:01
 *
 */
public class BooleanMeasure extends Measure {
	private Boolean isChange;

	/**
	 * 
	 * @param simulated
	 * @param isChange
	 */
	public BooleanMeasure(boolean simulated, Boolean isChange) {
		super(simulated);
		this.isChange = isChange;
	}

	/**
	 * 
	 * @return
	 */
	public Boolean isChanged() {
		return isChange;
	}

	/**
 * 
 */
	@Override
	public Object getValue() {
		if (isChange)
			return 1;
		else
			return 0;

	}

}
