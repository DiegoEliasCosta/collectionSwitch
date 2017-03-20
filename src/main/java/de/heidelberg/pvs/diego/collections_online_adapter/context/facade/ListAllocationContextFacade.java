package de.heidelberg.pvs.diego.collections_online_adapter.context.facade;

import java.util.Collection;
import java.util.List;

import de.heidelberg.pvs.diego.collections_online_adapter.context.CollectionTypeEnum;
import de.heidelberg.pvs.diego.collections_online_adapter.context.ListAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.context.impl.FirstSamplesListMemporyOptimizer;

/**
 * Facade created add flexibility to the {@link ListAllocationContext} creation.
 * In this way we can change only the facade instantiation to inject a new context
 * behavior without exposing this change to the app client.
 * 
 * @author Diego
 *
 * @param <E>
 */
public class ListAllocationContextFacade<E>  implements ListAllocationContext<E> {
	
	FirstSamplesListMemporyOptimizer<E> context;
	
	public ListAllocationContextFacade(CollectionTypeEnum collectionType) {
		super();
		this.context = new FirstSamplesListMemporyOptimizer<>(collectionType);
	}


	public List<E> createList() {
		return context.createList();
	}

	public int hashCode() {
		return context.hashCode();
	}

	public List<E> createList(int initialCapacity) {
		return context.createList(initialCapacity);
	}

	public List<E> createList(Collection<? extends E> c) {
		return context.createList(c);
	}

	public void updateOperationsAndSize(int indexOp, int midListOp, int containsOp, int size) {
		context.updateOperationsAndSize(indexOp, midListOp, containsOp, size);
	}

	public boolean isOnline() {
		return context.isOnline();
	}

	public void updateSize(int size) {
		context.updateSize(size);
	}

	public boolean equals(Object obj) {
		return context.equals(obj);
	}

	public String toString() {
		return context.toString();
	}
	
	
	
	

}
