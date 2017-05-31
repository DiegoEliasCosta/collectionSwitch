package de.heidelberg.pvs.diego.collections_online_adapter.context.impl;

import java.util.Collection;
import java.util.Set;

import de.heidelberg.pvs.diego.collections_online_adapter.context.AllocationContextState;
import de.heidelberg.pvs.diego.collections_online_adapter.context.CollectionTypeEnum;
import de.heidelberg.pvs.diego.collections_online_adapter.context.SetAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.factories.SetsFactory;

public class InactiveSetAllocationContext implements SetAllocationContext {

	private CollectionTypeEnum type;
	
	public InactiveSetAllocationContext(CollectionTypeEnum type) {
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
	public <E> Set<E> createSet() {
		return SetsFactory.createNormalSet(type, 10);
	}

	@Override
	public <E> Set<E> createSet(int initialCapacity) {
		return SetsFactory.createNormalSet(type, initialCapacity);
	}

	@Override
	public <E> Set<E> createSet(Collection<? extends E> set) {
		return SetsFactory.createNormalSet(type, set);
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
