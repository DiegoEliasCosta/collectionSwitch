package de.heidelberg.pvs.diego.collections_online_adapter.monitors.maps;

import java.lang.ref.WeakReference;

public class MapMetrics {
	
	private int size;
	private int maxSize;
	private int containsOp;
	private int iterationOp;
	
	private WeakReference<?> mapReference;
	
	public MapMetrics(WeakReference<?> map) {
		super();
		this.mapReference = map;
	}

	public boolean hasCollectionFinished() {
		return mapReference.get() == null;
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
