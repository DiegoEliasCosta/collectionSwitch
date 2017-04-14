package de.heidelberg.pvs.diego.collections_online_adapter.context.impl;

import java.util.Collection;
import java.util.List;

import de.heidelberg.pvs.diego.collections_online_adapter.context.CollectionTypeEnum;
import de.heidelberg.pvs.diego.collections_online_adapter.context.ListAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.factories.ListsFactory;

public class SimplifiedAdaptiveListAllocationContext extends AbstractAdaptiveAllocationContext implements ListAllocationContext {

	public SimplifiedAdaptiveListAllocationContext(CollectionTypeEnum collectionType, int windowSize,
			int fullAnalysisThreshold, int sleepingFrequency, int convergencyRate, int divergentRoundsThreshold) {
		super(collectionType, windowSize, fullAnalysisThreshold, sleepingFrequency, convergencyRate, divergentRoundsThreshold);
		// TODO Auto-generated constructor stub
	}

	@Override
	public <E> List<E> createList() {
		//TODO
		return null;
	}

	@Override
	public <E> List<E> createList(int initialCapacity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <E> List<E> createList(Collection<? extends E> c) {
		// TODO Auto-generated method stub
		return null;
	}

}
