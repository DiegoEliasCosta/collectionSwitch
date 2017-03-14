package de.heidelberg.pvs.diego.collections_online_adapter.context.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import de.heidelberg.pvs.diego.collections_online_adapter.context.CollectionTypeEnum;
import de.heidelberg.pvs.diego.collections_online_adapter.context.ListAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.custom.HashArrayList;
import de.heidelberg.pvs.diego.collections_online_adapter.instrumenters.lists.ArrayListOperationsMonitor;
import de.heidelberg.pvs.diego.collections_online_adapter.instrumenters.lists.LinkedListOperationsMonitor;

public class FirstSamplesListAllocationContext<E> implements ListAllocationContext<E> {

	private int initialCapacity = 10;
	private static int SAMPLES = 10;
	
	// Monitored data
	private int[] indexOp = new int[SAMPLES];
	private int[] midListOp = new int[SAMPLES];
	private int[] size = new int[SAMPLES];
	private int[] containsOp = new int[SAMPLES];
	
	private volatile int count = 0;
	
	private CollectionTypeEnum collectionType;
	
	public FirstSamplesListAllocationContext(CollectionTypeEnum collectionType) {
		super();
		this.collectionType = collectionType;
	}

	public List<E> createList() {
		
		switch(collectionType){
		case ARRAY:
			return isOnline() ? new ArrayListOperationsMonitor<E>(initialCapacity, this) : new ArrayList<E>(initialCapacity);
		case LINKED:
			return isOnline()? new LinkedListOperationsMonitor<E>(this) : new LinkedList<E>();
		case HASH:
			// At this point the algorithm won't be online anymore
			return new HashArrayList<E>(initialCapacity); 
		}
			 
		return null;
	}
	
	@Override
	public List<E> createList(int initialCapacity) {
		this.initialCapacity = initialCapacity;
		return createList();
		
	}

	@Override
	public List<E> createList(Collection<? extends E> c) {
		
		switch(collectionType){
		case ARRAY:
			return isOnline() ? new ArrayListOperationsMonitor<E>(c, this) : new ArrayList<E>(c);
		case LINKED:
			return isOnline()? new LinkedListOperationsMonitor<E>(c, this) : new LinkedList<E>();
		case HASH:
			// At this point the algorithm won't be online anymore
			return new HashArrayList<E>(c); 
		}
		return null;
	}

	public void updateOperationsAndSize(int indexOp, int midListOp, int containsOp, int size) {
		
		// FIXME: This needs to be thread-safe
		int copyCount = count++;
		
		this.indexOp[copyCount] = indexOp;
		this.midListOp[copyCount] = midListOp;
		this.containsOp[copyCount] = containsOp;
		this.size[copyCount] = size;
		
		if(copyCount == SAMPLES) {
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

	@Override
	public void updateSize(int size) {
		
		// FIXME: This needs to be thread-safe
		int copyCount = count++;
		this.size[copyCount] = size;
		
	}


}
