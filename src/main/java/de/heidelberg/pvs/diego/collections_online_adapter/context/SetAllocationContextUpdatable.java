package de.heidelberg.pvs.diego.collections_online_adapter.context;

public interface SetAllocationContextUpdatable {
	
	public void updateCollectionInitialCapacity(int analyzedInitialCapacity);
	
	public void updateCollectionType(SetCollectionType type);

}
