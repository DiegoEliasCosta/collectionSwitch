package de.heidelberg.pvs.diego.collections_online_adapter.context.facade;

import java.util.Collections;
import java.util.Set;

import de.heidelberg.pvs.diego.collections_online_adapter.context.CollectionTypeEnum;
import de.heidelberg.pvs.diego.collections_online_adapter.context.SetAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.context.impl.FirstSamplesSetAllocationContext;

public class SetAllocationContextFacade<E> implements SetAllocationContext<E> {
	
	private SetAllocationContext<E> context;
	
	public SetAllocationContextFacade(CollectionTypeEnum collectionType) {
		super();
		this.context = new FirstSamplesSetAllocationContext<E>(collectionType);
	}

	public Set<E> createSet() {
		return context.createSet();
	}

	public void updateSize(int size) {
		context.updateSize(size);
	}

	public void updateOperationsAndSize(int iterations, int contains, int size) {
		context.updateOperationsAndSize(iterations, contains, size);
	}

	public Set<E> createSet(int initialCapacity) {
		return context.createSet(initialCapacity);
	}

	public Set<E> createSet(Collections collections) {
		return context.createSet(collections);
	}
	
	

}
