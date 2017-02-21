package de.heidelberg.pvs.diego.collections_online_adapter.context;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MovingAverageListAnalyzer {

	private static final double LINKED_THRESHOLD = 2;
	private static final int SAMPLES = 10;
	private int rollingAvgInitialCapacity = 10;
	private double linkedListRatio;
	
	public List<?> createList() {
		
		if(linkedListRatio > LINKED_THRESHOLD) {
			return new LinkedList();
		}
		
		return new ArrayList(rollingAvgInitialCapacity);
	}
	
	/**
	 * Update InitialCapacity through moving avg
	 * @param initialCapacity
	 */
	public void updateInitialCapacity(int initialCapacity) {
		
		rollingAvgInitialCapacity -= rollingAvgInitialCapacity / SAMPLES;
		rollingAvgInitialCapacity += initialCapacity / SAMPLES;
		
	}
	
	public void updateOperations(int indexOp, int midListOp, int size) {
		// FIXME: Include the size
		indexOp++; midListOp++; // Awful method of Avoiding zero divisions 
		linkedListRatio -= linkedListRatio / SAMPLES;
		rollingAvgInitialCapacity += (midListOp / indexOp) / SAMPLES;
		
	}

}
