package de.heidelberg.pvs.diego.collections_online_adapter.context;

public interface AllocationContextUpdatable<E> {
	
	void updateCollectionInitialCapacity(int size);
	
	void updateCollectionType(E type);
	
}
