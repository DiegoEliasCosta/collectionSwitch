package de.heidelberg.pvs.diego.collections_online_adapter.monitors.lists;

import java.lang.ref.PhantomReference;

public class ListState {

	private int size;
	private int maxSize;
	private int containsOp;
	private int indexOp;
	private int iterationOp;

	private PhantomReference<?> list;

	public boolean hasCollectionFinished() {
		return list == null;
	}

	public int getSize() {
		return size;
	}

	public int getMaxSize() {
		return maxSize;
	}

	public int getContainsOp() {
		return containsOp;
	}

	public int getIndexOp() {
		return indexOp;
	}

	public int getIterationOp() {
		return iterationOp;
	}

	public void updateIteratorOp(int delta) {
		iterationOp += delta;
	}

	public void updateSize(int delta) {
		size += delta;
	}

	public void updateIndexOp(int delta) {
		indexOp += delta;
	}

	public void updateContainsOp(int delta) {
		containsOp += delta;
	}

}
