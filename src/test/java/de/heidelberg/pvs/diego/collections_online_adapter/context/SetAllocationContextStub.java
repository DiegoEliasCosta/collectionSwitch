package de.heidelberg.pvs.diego.collections_online_adapter.context;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

public class SetAllocationContextStub<E> implements SetAllocationContext<E> {

	@Override
	public Set<E> createSet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateSize(int size) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateOperationsAndSize(int iterations, int contains, int size) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Set<E> createSet(int initialCapacity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<E> createSet(Collection<? extends E> set) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isOnline() {
		// TODO Auto-generated method stub
		return false;
	}
	

}