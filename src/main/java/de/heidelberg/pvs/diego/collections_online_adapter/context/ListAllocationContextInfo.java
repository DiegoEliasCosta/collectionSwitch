package de.heidelberg.pvs.diego.collections_online_adapter.context;

public interface ListAllocationContextInfo extends ListAllocationContext {
	
	public String getCurrentCollectionType();

	public int getInitialCapacity();

}
