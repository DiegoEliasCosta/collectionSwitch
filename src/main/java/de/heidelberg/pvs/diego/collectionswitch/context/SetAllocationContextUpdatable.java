package de.heidelberg.pvs.diego.collectionswitch.context;

public interface SetAllocationContextUpdatable {
	
	public void updateCollectionInitialCapacity(int analyzedInitialCapacity);
	
	public void updateCollectionType(SetCollectionType type);

}
