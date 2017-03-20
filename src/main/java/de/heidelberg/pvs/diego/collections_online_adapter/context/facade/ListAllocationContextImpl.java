package de.heidelberg.pvs.diego.collections_online_adapter.context.facade;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import de.heidelberg.pvs.diego.collections_online_adapter.context.CollectionTypeEnum;
import de.heidelberg.pvs.diego.collections_online_adapter.context.ListAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.context.ListAllocationContextUpdatable;
import de.heidelberg.pvs.diego.collections_online_adapter.context.ListAllocationOptimizer;
import de.heidelberg.pvs.diego.collections_online_adapter.context.impl.FirstSamplesListMemporyOptimizer;
import de.heidelberg.pvs.diego.collections_online_adapter.custom.HashArrayList;
import de.heidelberg.pvs.diego.collections_online_adapter.instrumenters.lists.ArrayListSizeMonitor;
import de.heidelberg.pvs.diego.collections_online_adapter.instrumenters.lists.LinkedListSizeMonitor;
import de.heidelberg.pvs.diego.collections_online_adapter.instrumenters.lists.ListSizeMonitor;

/**
 * Facade created add flexibility to the {@link ListAllocationContext} creation.
 * In this way we can change only the facade instantiation to inject a new
 * context behavior without exposing this change to the app client.
 * 
 * @author Diego
 *
 * @param <E>
 */
public class ListAllocationContextImpl<E> implements ListAllocationContext<E>, ListAllocationContextUpdatable {

	ListAllocationOptimizer<E> optimizer;

	CollectionTypeEnum championCollectionType;

	int analyzedInitialCapacity = 10;

	public ListAllocationContextImpl(CollectionTypeEnum collectionType) {
		super();
		this.optimizer = new FirstSamplesListMemporyOptimizer<>(this, collectionType);
		this.championCollectionType = collectionType;
	}

	public List<E> createList() {
		return this.createList(this.analyzedInitialCapacity);
	}

	@Override
	public List<E> createList(int initialCapacity) {

		if (optimizer.isOnline()) {
			return optimizer.createListMonitor(initialCapacity);
			
		} else {
			switch (championCollectionType) {
			case ARRAY:
				return new ArrayList<>(initialCapacity);
			case LINKED:
				return new LinkedList<>();
			case HASH:
				return new HashArrayList<>(initialCapacity);
			default:
				break;
			}
		}

		return null;

	}

	@Override
	public List<E> createList(Collection<? extends E> c) {

		if (optimizer.isOnline()) {

			switch (championCollectionType) {
			case ARRAY:
				return new ArrayListSizeMonitor<>(c, this);
			case LINKED:
				return new LinkedListSizeMonitor<>(this);
			case HASH:
				return new ListSizeMonitor<>(new HashArrayList<>(c), this);
			default:
				break;
			}

		} else {
			switch (championCollectionType) {
			case ARRAY:
				return new ArrayList<>(c);
			case LINKED:
				return new LinkedList<>();
			case HASH:
				return new HashArrayList<>(c);
			default:
				break;
			}
		}

		return null;
	}

	public void updateOperationsAndSize(int indexOp, int midListOp, int containsOp, int size) {
		optimizer.updateOperationsAndSize(indexOp, midListOp, containsOp, size);
	}

	public void updateSize(int size) {
		optimizer.updateSize(size);
	}

	@Override
	public void updateCollectionType(CollectionTypeEnum collectionTypeEnum) {
		this.championCollectionType = collectionTypeEnum;

	}

	@Override
	public void updateInitialCapacity(int analyzedInitialCapacity) {
		this.analyzedInitialCapacity = analyzedInitialCapacity;

	}

}
