package de.heidelberg.pvs.diego.collections_online_adapter.instrumenters;

import java.util.AbstractList;
import java.util.List;

import de.heidelberg.pvs.diego.collections_online_adapter.context.ListAllocationContext;

public class ListMonitor<E> extends AbstractList<E>{

	List<E> list;
	
	private ListAllocationContext<E> context;
	private int getOp;
	private int removeOp;
	private int containsOp;
	
	public ListMonitor(List<E> list, ListAllocationContext<E> context) {
		super();
		this.list = list;
	}

	@Override
	public E remove(int index) {
		removeOp++;
		return list.remove(index);
	}
	
	@Override
	public boolean contains(Object o) {
		containsOp++;
		return list.contains(o);
	}
	
	@Override
	public E get(int index) {
		getOp++;
		return list.get(index);
	}

	@Override
	public int size() {
		return list.size();
	}
	
	@Override
	protected void finalize() throws Throwable {
		context.updateOperations(getOp, containsOp, removeOp, list.size());
	}

}
