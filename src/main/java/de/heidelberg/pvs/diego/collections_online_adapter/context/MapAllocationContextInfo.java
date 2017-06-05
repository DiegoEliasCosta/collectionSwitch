package de.heidelberg.pvs.diego.collections_online_adapter.context;

public interface MapAllocationContextInfo extends MapAllocationContext {

	AllocationContextState getAllocationContextState();

	int getInitialCapacity();

}
