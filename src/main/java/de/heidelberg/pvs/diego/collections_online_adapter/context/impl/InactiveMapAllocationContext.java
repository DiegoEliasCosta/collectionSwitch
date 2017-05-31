package de.heidelberg.pvs.diego.collections_online_adapter.context.impl;

import java.util.Map;

import de.heidelberg.pvs.diego.collections_online_adapter.context.AllocationContextState;
import de.heidelberg.pvs.diego.collections_online_adapter.context.CollectionTypeEnum;
import de.heidelberg.pvs.diego.collections_online_adapter.context.MapAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.factories.MapFactory;

public class InactiveMapAllocationContext implements MapAllocationContext {

	
	private CollectionTypeEnum type;
	

	public InactiveMapAllocationContext(CollectionTypeEnum type) {
		super();
		this.type = type;
	}

	@Override
	public void optimizeCollectionType(CollectionTypeEnum collecton, int mode, int median) {
		// TODO Auto-generated method stub

	}

	@Override
	public void noCollectionTypeConvergence(int mode, int medianInitialCapacity) {
		// TODO Auto-generated method stub

	}

	@Override
	public <K, V> Map<K, V> createMap() {
		return MapFactory.createNormalMap(type, 10, 0.75f);
	}

	@Override
	public <K, V> Map<K, V> createMap(int initialCapacity) {
		return MapFactory.createNormalMap(type, initialCapacity, 0.75f);
	}

	@Override
	public <K, V> Map<K, V> createMap(Map<K, V> map) {
		return MapFactory.createNormalMap(type, map);
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

	@Override
	public <K, V> Map<K, V> createMap(int initialCapacity, float loadFactor) {
		return MapFactory.createNormalMap(type, initialCapacity, 0.75f);
	}

	@Override
	public int getInitialCapacity() {
		// TODO Auto-generated method stub
		return 0;
	}

}
