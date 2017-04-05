package de.heidelberg.pvs.diego.collections_online_adapter.context;

public interface AllocationContextUpdatable {
	
	void optimizeCollectionType(CollectionTypeEnum collecton, int mode, int median);
	
	void noCollectionTypeConvergence(int mode, int medianInitialCapacity);

}
