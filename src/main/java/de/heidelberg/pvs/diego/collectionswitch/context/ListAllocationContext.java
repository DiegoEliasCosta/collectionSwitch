package de.heidelberg.pvs.diego.collectionswitch.context;

import java.util.Collection;
import java.util.List;

public interface ListAllocationContext extends AllocationContextUpdatable<ListCollectionType> {
	
	public <E> List<E> createList();
	
	public <E> List<E> createList(int initialCapacity);
	
	public <E> List<E> createList(Collection<? extends E> c);
	
}
