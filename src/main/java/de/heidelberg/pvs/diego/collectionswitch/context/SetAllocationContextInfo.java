package de.heidelberg.pvs.diego.collectionswitch.context;

public interface SetAllocationContextInfo extends SetAllocationContext {

	int getAnalyzedInitialCapacity();
	
	String getCurrentCollectionType();

}
