package de.heidelberg.pvs.diego.collectionswitch.context;

import java.util.Collection;
import java.util.Set;

public interface SetAllocationContext extends AllocationContextUpdatable<SetCollectionType> {
	
	public <E> Set<E> createSet();

	public <E> Set<E> createSet(int initialCapacity);

	public <E> Set<E> createSet(Collection<? extends E> set);
	
	
}
