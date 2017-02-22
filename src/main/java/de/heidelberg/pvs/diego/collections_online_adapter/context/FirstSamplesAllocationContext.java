package de.heidelberg.pvs.diego.collections_online_adapter.context;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import de.heidelberg.pvs.diego.collections_online_adapter.custom.HashArrayList;
import de.heidelberg.pvs.diego.collections_online_adapter.instrumenters.ArrayListMonitor;
import de.heidelberg.pvs.diego.collections_online_adapter.instrumenters.LinkedListMonitor;

public class FirstSamplesAllocationContext<E> implements ListAllocationContext<E> {

	private int initialCapacity = 10;
	private static int SAMPLES = 10;
	
	// Monitored data
	private int[] indexOp = new int[SAMPLES];
	private int[] midListOp = new int[SAMPLES];
	private int[] size = new int[SAMPLES];
	
	private int count = 0;
	
	private CollectionTypeEnum collectionType;
	
	public FirstSamplesAllocationContext(CollectionTypeEnum collectionType, int specifiedInitialCapacity) {
		super();
		this.collectionType = collectionType;
		this.initialCapacity = specifiedInitialCapacity;
	}

	public List<E> createList() {
		
		switch(collectionType){
		case ARRAY:
			return isOnline() ? new ArrayListMonitor<E>(initialCapacity, this) : new ArrayList<E>(initialCapacity);
		case LINKED:
			return isOnline()? new LinkedListMonitor<E>(this) : new LinkedList<E>();
		case HASH:
			// At this point the algorithm won't be online anymore
			return new HashArrayList<E>(initialCapacity); 
		}
			 
		return null;
	}

	public void updateOperations(int indexOp, int midListOp, int size) {
		
		this.indexOp[count] = indexOp;
		this.midListOp[count] = midListOp;
		this.size[count] = size;
		count++;
		
		if(count == SAMPLES) {
			int summedSize = 0;
			for(int i = 0; i < SAMPLES; i++) {
				summedSize += this.size[i];
			}
			// This needs to be synchronized?
			this.initialCapacity = summedSize / SAMPLES;
		}
	}

	public boolean isOnline() {
		return count < SAMPLES;
	}

	public void updateOperations(int getOp, int containsOp, int removeOp, int size) {
		// TODO: To be implemented
		
	}

}
