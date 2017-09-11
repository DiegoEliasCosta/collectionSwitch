package de.heidelberg.pvs.diego.collections_online_adapter.context;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.collections.impl.set.mutable.UnifiedSet;

import gnu.trove.set.hash.THashSet;

public enum SetCollectionType {

	FASTUTILS_HASHSET, 
	GSCOLLECTIONS_UNIFIEDSET, 
	JDK_HASHSET, 
	JDK_LINKEDHASHSET, 
	KOLOBOKE_HASHSET, 
	NLP_ARRAYSET, 
	ONLINEADAPTER_ARRAYSET, 
	ONLINEADAPTER_BINARYARRAYSET;
	
	public <E> Set<E> createSet(int initialCapacity) {
		
		Set<E> set;
		
		switch(this) {
		
			case JDK_HASHSET:
				set = new HashSet<>(initialCapacity);
			
			case FASTUTILS_HASHSET:
				set = new THashSet<>(initialCapacity);
				
			case GSCOLLECTIONS_UNIFIEDSET:
				set = new UnifiedSet<>(initialCapacity);
		
			default:
				set = new HashSet<>(initialCapacity);
		}
		
		return set;
	}
	
	public <E> Set<E> createSet() {
		
		Set<E> set;
		
		switch(this) {
		
			case JDK_HASHSET:
				set = new HashSet<>();
			
			case FASTUTILS_HASHSET:
				set = new THashSet<>();
				
			case GSCOLLECTIONS_UNIFIEDSET:
				set = new UnifiedSet<>();
		
			default:
				set = new HashSet<>();
		}
		
		return set;
	}

}
