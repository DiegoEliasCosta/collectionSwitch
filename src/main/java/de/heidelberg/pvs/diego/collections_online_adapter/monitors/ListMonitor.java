package de.heidelberg.pvs.diego.collections_online_adapter.monitors;

public interface ListMonitor<E> {
	
	public int getSize();
	
	public int getContainsOP();
	
	public int getIterateOP();
	
	public int getMidListOP();
	
	
}
