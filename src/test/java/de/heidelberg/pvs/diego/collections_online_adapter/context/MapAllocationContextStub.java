package de.heidelberg.pvs.diego.collections_online_adapter.context;

import java.util.Map;

public class MapAllocationContextStub<K, V> implements MapAllocationContext<K, V> {

	@Override
	public Map<K, V> createMap() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<K, V> createMap(int initialCapacity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<K, V> createMap(Map<K, V> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateSize(int size) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateOperationsAndSize(int containsOp, int iterationOp, int size) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void optimizeInitialCapacity(int analyzedInitialCapacity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void noInitialCapacityConvergence() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void optimizeCollectionType(CollectionTypeEnum collecton) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void noCollectionTypeConvergence() {
		// TODO Auto-generated method stub
		
	}

	
	

}
