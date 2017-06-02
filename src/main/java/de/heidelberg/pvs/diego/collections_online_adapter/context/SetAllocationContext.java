package de.heidelberg.pvs.diego.collections_online_adapter.context;

import java.util.Collection;
import java.util.Set;

public interface SetAllocationContext extends AllocationContextUpdatable {
	
	public <E> Set<E> createSet();

	public <E> Set<E> createSet(int initialCapacity);

	public <E> Set<E> createSet(Collection<? extends E> set);

	public AllocationContextState getAllocationContextState();

	
}
