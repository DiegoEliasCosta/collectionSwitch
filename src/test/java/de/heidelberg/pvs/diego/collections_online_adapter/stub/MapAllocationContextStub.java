package de.heidelberg.pvs.diego.collections_online_adapter.stub;

import java.util.Map;

import de.heidelberg.pvs.diego.collections_online_adapter.context.AllocationContextState;
import de.heidelberg.pvs.diego.collections_online_adapter.context.CollectionTypeEnum;
import de.heidelberg.pvs.diego.collections_online_adapter.context.MapAllocationContext;

public class MapAllocationContextStub implements MapAllocationContext {

	@Override
	public <K, V> Map<K, V> createMap() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Map<K, V> createMap(int initialCapacity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K, V> Map<K, V> createMap(Map<K, V> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void optimizeCollectionType(CollectionTypeEnum collecton, int medianInitialCapacity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void noCollectionTypeConvergence(int medianInitialCapacity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public AllocationContextState getAllocationContextState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CollectionTypeEnum getChampion() {
		// TODO Auto-generated method stub
		return null;
	}


}
