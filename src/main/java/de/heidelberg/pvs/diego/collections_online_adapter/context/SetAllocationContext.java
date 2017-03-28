package de.heidelberg.pvs.diego.collections_online_adapter.context;

import java.util.Collection;
import java.util.Set;

public interface SetAllocationContext<E> extends SetAllocationContextBase<E>, AllocationContextUpdatable {
	
	public Set<E> createSet();

	public Set<E> createSet(int initialCapacity);

	public Set<E> createSet(Collection<? extends E> set);
	
	
}
