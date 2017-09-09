package de.heidelberg.pvs.diego.collections_online_adapter.context;

import de.heidelberg.pvs.diego.collections_online_adapter.context.impl.SetCollectionType;

public interface SetAllocationContextUpdatable {
	
	public void updateCollectionInitialCapacity(int analyzedInitialCapacity);
	
	public void updateCollectionType(SetCollectionType type);

}
