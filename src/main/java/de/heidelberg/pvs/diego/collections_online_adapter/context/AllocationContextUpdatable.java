package de.heidelberg.pvs.diego.collections_online_adapter.context;

public interface AllocationContextUpdatable {
	
	void updateCollectionSize(int size);
	
	void noSizeConvergence();

}
