package edu.kit.wavelength.client.model.reduction;

import java.util.Arrays;
import java.util.List;

/**
 * Static class giving access to all {@link ReductionOrder}s known to the model.
 *
 */
public final class ReductionOrders {
	
	/**
	 * Returns an unmodifiable list of all {@link ReductionOrder}s known to the model.
	 * @return An unmodifiable list containing exactly one instance of all
	 * {@link ReductionOrder}s known to the model
	 */
	public static List<ReductionOrder> all() {
		return Arrays.asList(new NormalOrder(), new CallByName(), new CallByValue(), new ApplicativeOrder());
	}
	
	/**
	 * Returns the {@link ReductionOrder} represented by a given string.
	 * @param serialized A serialized reduction order
	 * @return The {@link ReductionOrder} the given string refers to, if known to the model
	 */
	public static ReductionOrder deserialize(String serialized) {
		return null;
	}
	
	private ReductionOrders() {
	}
}
