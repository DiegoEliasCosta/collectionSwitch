package de.heidelberg.pvs.diego.collections_online_adapter.monitors.lists;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import de.heidelberg.pvs.diego.collections_online_adapter.context.CollectionTypeEnum;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.lists.ListAllocationOptimizer;

public class ListProactiveFullMonitor<E> implements List<E> {

	private List<E> list;
	private ListAllocationOptimizer optimizer;
	private final int monitorIndex;

	private int containsOp;
	private int midListOp;
	private int indexOp;

	public ListProactiveFullMonitor(List<E> list, ListAllocationOptimizer optimizer, int index) {
		super();
		this.list = list;
		this.optimizer = optimizer;
		this.monitorIndex = index;
		this.optimizer.updateSize(index, size());
	}

	@Override
	public E get(int index) {
		indexOp++;
		return list.get(index);
	}

	@Override
	public E remove(int index) {
		midListOp++;
		// FIXME: MidList op analysis
		return list.remove(index);
	}

	@Override
	public void add(int index, E element) {
		indexOp++;
		list.add(index, element);

	}
	
	public boolean add(E e) {
		boolean add = list.add(e);
		this.optimizer.updateSize(monitorIndex, size());
		return add;
	}
	
	public boolean addAll(Collection<? extends E> c) {
		boolean addAll = list.addAll(c);
		this.optimizer.updateSize(monitorIndex, size());
		return addAll;
	}
	
	public boolean addAll(int index, Collection<? extends E> c) {
		boolean addAll = list.addAll(index, c);
		this.optimizer.updateSize(monitorIndex, size());
		return addAll;
	}

	@Override
	public boolean contains(Object o) {
		containsOp++;
		if(containsOp >= 20){
			this.optimizer.updateVote(monitorIndex, CollectionTypeEnum.HASH);
		}
		
		return list.contains(o);
	}
	
	public boolean containsAll(Collection<?> c) {
		containsOp += c.size();
		if(containsOp >= 20){
			this.optimizer.updateVote(monitorIndex, CollectionTypeEnum.HASH);
		}
		return list.containsAll(c);
	}

	public int size() {
		return list.size();
	}

	public boolean isEmpty() {
		return list.isEmpty();
	}

	public Iterator<E> iterator() {
		return list.iterator();
	}

	public Object[] toArray() {
		return list.toArray();
	}

	public <T> T[] toArray(T[] a) {
		return list.toArray(a);
	}


	public boolean remove(Object o) {
		return list.remove(o);
	}




	public boolean removeAll(Collection<?> c) {
		return list.removeAll(c);
	}

	public boolean retainAll(Collection<?> c) {
		return list.retainAll(c);
	}

	public void clear() {
		list.clear();
	}

	public boolean equals(Object o) {
		return list.equals(o);
	}

	public int hashCode() {
		return list.hashCode();
	}

	public E set(int index, E element) {
		return list.set(index, element);
	}

	public int indexOf(Object o) {
		return list.indexOf(o);
	}

	public int lastIndexOf(Object o) {
		return list.lastIndexOf(o);
	}

	public ListIterator<E> listIterator() {
		return list.listIterator();
	}

	public ListIterator<E> listIterator(int index) {
		return list.listIterator(index);
	}

	public List<E> subList(int fromIndex, int toIndex) {
		return list.subList(fromIndex, toIndex);
	}

}
