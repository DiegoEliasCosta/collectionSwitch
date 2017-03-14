package de.heidelberg.pvs.diego.collections_online_adapter.context.impl;

import java.util.Collections;
import java.util.Set;

import de.heidelberg.pvs.diego.collections_online_adapter.context.CollectionTypeEnum;
import de.heidelberg.pvs.diego.collections_online_adapter.context.SetAllocationContext;

public class FirstSamplesSetAllocationContext<E> implements SetAllocationContext<E> {

	private int initialCapacity = 10;
	private static int SAMPLES = 10;
	
	// Monitored data
	private int[] size = new int[SAMPLES];
	private int[] contains = new int[SAMPLES];
	private int[] interations = new int[SAMPLES];
	
	private volatile int count = 0;
	
	private CollectionTypeEnum collectionType;
	
	public FirstSamplesSetAllocationContext(CollectionTypeEnum collectionType) {
		super();
		this.collectionType = collectionType;
	}

	public boolean isOnline() {
		return count < SAMPLES;
	}

	@Override
	public Set<E> createSet() {

//		switch(collectionType){
//		case ARRAY:
//			return isOnline() ? new ArrayListOperationsMonitor<E>(initialCapacity, this) : new ArrayList<E>(initialCapacity);
//		case LINKED:
//			return isOnline()? new LinkedListOperationsMonitor<E>(this) : new LinkedList<E>();
//		case HASH:
//			// At this point the algorithm won't be online anymore
//			return new HashArrayList<E>(initialCapacity); 
//		}
//			 
//		return null;
//		
		return null;
	}
	

	@Override
	public Set<E> createSet(int initialCapacity) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Set<E> createSet(Collections collections) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateSize(int size) {
		// FIXME: This needs to be thread-safe
		int copyCount = count++;
		this.size[copyCount] = size;
		
	}

	@Override
	public void updateOperationsAndSize(int iterations, int contains, int size) {
		// FIXME: This needs to be thread-safe
		int copyCount = count++;
		this.interations[copyCount] = iterations;
		this.contains[copyCount] = contains;
		
		
		
	}


}
