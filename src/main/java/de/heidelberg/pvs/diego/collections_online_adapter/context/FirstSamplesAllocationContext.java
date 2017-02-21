package de.heidelberg.pvs.diego.collections_online_adapter.context;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import de.heidelberg.pvs.diego.collections_online_adapter.instrumenters.ArrayListMonitor;
import de.heidelberg.pvs.diego.collections_online_adapter.instrumenters.LinkedListMonitor;

public class FirstSamplesAllocationContext implements ListAllocationContext{

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

	@SuppressWarnings("rawtypes")
	public List<?> createList() {
		
		switch(collectionType){
		case ARRAY:
			return isOnline() ? new ArrayListMonitor(initialCapacity, this) : new ArrayList(initialCapacity);
		case LINKED:
			return isOnline()? new LinkedListMonitor(this) : new LinkedList();
		case HASH:
			// FIXME: Not available at the moment
			return null;
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
	
	

}
