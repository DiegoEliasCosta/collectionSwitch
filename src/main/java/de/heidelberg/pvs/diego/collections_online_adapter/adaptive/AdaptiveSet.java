package de.heidelberg.pvs.diego.collections_online_adapter.adaptive;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.collections.impl.set.mutable.UnifiedSet;

import edu.stanford.nlp.util.ArraySet;
import net.openhft.koloboke.collect.set.hash.HashObjSets;

public class AdaptiveSet<E> implements Set<E> {

	private static final int TURNING_POINT = 40;

	Set<E> set;

	private boolean transformed;

	public AdaptiveSet() {
		super();
		set = new ArraySet<E>();
	}

	public AdaptiveSet(int capacity) {
		super();
		if (capacity < TURNING_POINT) {
			set = new ArraySet<E>(capacity);
		} else  {
			set = HashObjSets.newMutableSet(capacity);
			transformed = true;
		} 
	}

	public AdaptiveSet(int capacity, float loadFactor) {
		super();
		
		if (capacity < TURNING_POINT) {
			set = new ArraySet<E>(capacity);
		} else  {
			set = HashObjSets.newMutableSet(capacity);
			transformed = true;
		} 
	}

	public AdaptiveSet(Collection<? extends E> set) {

		if (set.size() < TURNING_POINT) {
			this.set = new ArraySet<E>();
			this.set.addAll(set);
		} else  {
			set = HashObjSets.newMutableSet(set);
			transformed = true;
		} 

	}

	private void manageImplementation(int delta) {
		int newSize = size() + delta;
		
		// Double-check on transformed
		if (newSize > TURNING_POINT && !transformed) {
			set = HashObjSets.newMutableSet(set);
			transformed = true;
		} 
		
	}

	public boolean add(E e) {
		if(!transformed) {
			manageImplementation(1);
		}
		return set.add(e);
	}

	public boolean addAll(Collection<? extends E> c) {
		if(!transformed) {
			manageImplementation(c.size());
		}
		manageImplementation(c.size());
		return set.addAll(c);
	}

	public void clear() {
		set.clear();
	}

	public boolean contains(Object o) {
		return set.contains(o);
	}

	public boolean containsAll(Collection<?> c) {
		return set.containsAll(c);
	}

	public boolean equals(Object o) {
		return set.equals(o);
	}

	public int hashCode() {
		return set.hashCode();
	}

	public boolean isEmpty() {
		return set.isEmpty();
	}

	public Iterator<E> iterator() {
		return set.iterator();
	}

	public boolean remove(Object o) {
		return set.remove(o);
	}

	public boolean removeAll(Collection<?> c) {
		return set.removeAll(c);
	}

	public boolean retainAll(Collection<?> c) {
		return set.retainAll(c);
	}

	public int size() {
		return set.size();
	}

	public Object[] toArray() {
		return set.toArray();
	}

	public Object[] toArray(Object[] a) {
		return set.toArray(a);
	}

}
