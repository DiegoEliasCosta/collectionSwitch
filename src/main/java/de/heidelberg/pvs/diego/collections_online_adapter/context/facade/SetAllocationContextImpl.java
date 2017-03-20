package de.heidelberg.pvs.diego.collections_online_adapter.context.facade;

import java.util.Collection;
import java.util.Set;

import de.heidelberg.pvs.diego.collections_online_adapter.context.CollectionTypeEnum;
import de.heidelberg.pvs.diego.collections_online_adapter.context.SetAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.context.impl.FirstSamplesSetMemoryOptimizer;

public class SetAllocationContextImpl<E> implements SetAllocationContext<E> {
	
	private SetAllocationContext<E> context;
	
	public SetAllocationContextImpl(CollectionTypeEnum collectionType) {
		super();
		this.context = new FirstSamplesSetMemoryOptimizer<E>(collectionType);
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

	public Set<E> createSet(Collection<? extends E> collections) {
		return context.createSet(collections);
	}

	@Override
	public boolean isOnline() {
		return context.isOnline();
	}
	
	

}
