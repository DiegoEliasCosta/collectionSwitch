package de.heidelberg.pvs.diego.collections_online_adapter.optimizers.maps;

import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.collections.impl.map.mutable.primitive.ObjectIntHashMap;

import de.heidelberg.pvs.diego.collections_online_adapter.context.AllocationContextUpdatable;
import de.heidelberg.pvs.diego.collections_online_adapter.context.CollectionTypeEnum;

public class AdaptiveMapOptimizer<K, V> implements MapAllocationOptimizer<K, V> {

	private int sizes[] = new int[WINDOW_SIZE];

	private ObjectIntHashMap<CollectionTypeEnum> votedCollection;

	private AllocationContextUpdatable context;

	private AtomicInteger indexManager;

	public AdaptiveMapOptimizer(AllocationContextUpdatable context) {
		super();
		this.context = context;
		this.indexManager = new AtomicInteger(0);
		votedCollection = ObjectIntHashMap.newWithKeysValues(CollectionTypeEnum.ARRAY, 0, CollectionTypeEnum.ARRAY_HASH,
				0, CollectionTypeEnum.HASH, 0, CollectionTypeEnum.LINKED, 0);
	}

	@Override
	public void updateSize(int size) {

		int myIndex = indexManager.getAndIncrement();

		if (myIndex < WINDOW_SIZE) {

			sizes[myIndex] = size;

			if (size < 10) {
				this.votedCollection.addToValue(CollectionTypeEnum.ARRAY, 1);
			} 
			
			else if (size < 500) {
				this.votedCollection.addToValue(CollectionTypeEnum.ARRAY_HASH, 1);
			} 
			
			else {
				this.votedCollection.addToValue(CollectionTypeEnum.HASH, 1);
			}
			// Last update
			if (myIndex == WINDOW_SIZE - 1) {
				updateContext();
			}

		}

	}

	@Override
	public void updateOperationsAndSize(int containsOp, int iterationOp, int size) {

		int myIndex = indexManager.getAndIncrement();

		if (myIndex < WINDOW_SIZE) {

			sizes[myIndex] = size;

			if (size < 10) {
				this.votedCollection.addToValue(CollectionTypeEnum.ARRAY, 1);
			} 
			
			else if (size < 500) {
				this.votedCollection.addToValue(CollectionTypeEnum.ARRAY_HASH, 1);
			} 
			
			else if(iterationOp > 100) {
				this.votedCollection.addToValue(CollectionTypeEnum.LINKED, 1);
			}
			
			else {
				this.votedCollection.addToValue(CollectionTypeEnum.HASH, 1);
			}

			// Last update
			if (myIndex == WINDOW_SIZE - 1) {
				updateContext();
			}

		}

	}

	private void updateContext() {
		
		
	}

}
