package de.heidelberg.pvs.diego.collections_online_adapter.adaptive;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.collections.impl.set.mutable.UnifiedSet;

import de.heidelberg.pvs.diego.collections_online_adapter.context.CollectionTypeEnum;
import edu.stanford.nlp.util.ArraySet;

public class AdaptiveSet<E> implements Set<E> {

	private static final int OPENHASH_HIGH_BOUND = 10000;
	private static final int ARRAY_HIGH_BOUND = 50;

	private static final int SAMPLE = 50;

	Set<E> set;

	CollectionTypeEnum type;

	private int count;

	public AdaptiveSet() {
		super();
		set = new ArraySet<E>();
		type = CollectionTypeEnum.ARRAY;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public AdaptiveSet(int capacity) {
		super();
		if (capacity < ARRAY_HIGH_BOUND) {
			set = new ArraySet(capacity);
			type = CollectionTypeEnum.ARRAY;
		} else if (capacity < OPENHASH_HIGH_BOUND) {
			set = new HashSet<E>(capacity);
			type = CollectionTypeEnum.HASH;
		} else {
			set = new UnifiedSet<E>(capacity);
			type = CollectionTypeEnum.OPEN_HASH;

		}
	}

	public AdaptiveSet(int capacity, float loadFactor) {
		super();
		
		/*
		 * if (capacity < ARRAY_HIGH_BOUND) { set = new ArraySet(capacity,
		 * loadFactor); type = CollectionTypeEnum.ARRAY; } else
		 */

		if (capacity < OPENHASH_HIGH_BOUND) {
			set = new HashSet<E>(capacity, loadFactor);
			type = CollectionTypeEnum.HASH;

		} else {
			set = new UnifiedSet<E>(capacity, loadFactor);
			type = CollectionTypeEnum.OPEN_HASH;

		}
	}

	public AdaptiveSet(Collection<E> set) {

		// if (set.size() < ARRAY_HIGH_BOUND) {
		// set = new ArraySet<E>(capacity, loadFactor);
		// type = CollectionTypeEnum.ARRAY;
		// }

		if (set.size() > OPENHASH_HIGH_BOUND) {
			this.set = new HashSet<E>(set);
			type = CollectionTypeEnum.HASH;
		}

		else {
			set = new UnifiedSet<E>(set);
			type = CollectionTypeEnum.OPEN_HASH;
		}

	}

	private void manageImplementation(int elementsAdded) {
		int newSize = size() + elementsAdded;
		switch (type) {
		case ARRAY:
			if (newSize > ARRAY_HIGH_BOUND) {
				set = new UnifiedSet<E>(set);
				type = CollectionTypeEnum.OPEN_HASH;
			}
			break;

		case OPEN_HASH:
			if (newSize > OPENHASH_HIGH_BOUND) {
				set = new HashSet<E>(set);
				type = CollectionTypeEnum.HASH;
			}
			break;
		default:
			break;

		}
	}

	public boolean add(E e) {
		if (count++ % SAMPLE == 0)
			manageImplementation(1);
		return set.add(e);
	}

	public boolean addAll(Collection<? extends E> c) {
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
