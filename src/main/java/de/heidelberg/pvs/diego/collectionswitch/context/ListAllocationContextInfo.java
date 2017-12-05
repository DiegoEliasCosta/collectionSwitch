package de.heidelberg.pvs.diego.collectionswitch.context;

public interface ListAllocationContextInfo extends ListAllocationContext {
	
	public ListCollectionType getCurrentCollectionType();
	

	public int getAnalyzedInitialCapacity();

}
