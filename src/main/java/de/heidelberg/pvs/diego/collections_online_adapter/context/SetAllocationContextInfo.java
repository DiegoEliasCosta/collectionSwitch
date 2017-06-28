package de.heidelberg.pvs.diego.collections_online_adapter.context;

public interface SetAllocationContextInfo extends SetAllocationContext {

	AllocationContextState getAllocationContextState();

	int getAnalyzedSize();

}
