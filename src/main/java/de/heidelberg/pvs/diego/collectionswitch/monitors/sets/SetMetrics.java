package de.heidelberg.pvs.diego.collectionswitch.monitors.sets;

import java.lang.ref.WeakReference;

public class SetMetrics {
	
	private int size;
	private int maxSize;
	private int containsOp;
	private int iterationOp;
	
	private WeakReference<?> setReference;
	
	public SetMetrics(WeakReference<?> map) {
		super();
		this.setReference = map;
	}

	public boolean hasCollectionFinished() {
		return setReference.get() == null;
	}
	
	public int getLastSize() {
		return size;
	}

	public int getMaxSize() {
		return maxSize;
	}
	
	public int getContainsOp() {
		return containsOp;
	}

	public int getIterationOp() {
		return iterationOp;
	}
	
	public void updateSize(int delta) {
		size += delta;
		if(size > maxSize) {
			maxSize = size;
		}
	}
	
	public void updateContainsOp(int delta) {
		containsOp += delta;
	}
	
	public void updateIteration(int delta) {
		iterationOp += delta;
	}

}
