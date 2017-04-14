package de.heidelberg.pvs.diego.collections_online_adapter.context;

import java.util.Collection;
import java.util.List;

public interface ListAllocationContext extends AllocationContextUpdatable {
	
	public <E> List<E> createList();
	
	public <E> List<E> createList(int initialCapacity);
	
	public <E> List<E> createList(Collection<? extends E> c);
	
	// Used for checking
	
	public AllocationContextState getAllocationContextState();
	
	public CollectionTypeEnum getChampion();

	public void setAllocationContextState(AllocationContextState inactive);

	public int getInitialCapacity();
	
}
