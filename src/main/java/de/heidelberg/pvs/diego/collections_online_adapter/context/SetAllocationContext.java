package de.heidelberg.pvs.diego.collections_online_adapter.context;

import java.util.Collection;
import java.util.Set;

public interface SetAllocationContext<E> {
	
	public Set<E> createSet();

	public void updateSize(int size);
	
	public void updateOperationsAndSize(int iterations, int contains, int size);

	public Set<E> createSet(int initialCapacity);

	public Set<E> createSet(Collection<? extends E> set);
	
	public boolean isOnline();
	
}
