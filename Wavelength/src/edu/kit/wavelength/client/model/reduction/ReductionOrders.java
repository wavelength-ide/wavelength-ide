package edu.kit.wavelength.client.model.reduction;

import java.util.List;

/**
 * Static class giving access to all reduction orders known to the model.
 *
 */
public final class ReductionOrders {
	
	/**
	 * Returns an unmodifiable list of all reduction orders known to the model.
	 * @return An unmodifiable list containing exactly one instance of all
	 * reduction orders known to the model
	 */
	public static List<ReductionOrder> all() {
		return null;
	}
	
	/**
	 * Returns the reduction order represented by a given string.
	 * @param serialized A serialized reduction order
	 * @return The reduction order the given string refers to, if known to the model
	 */
	public static ReductionOrder deserialize(String serialized) {
		return null;
	}
	
	private ReductionOrders() {
	}
}
