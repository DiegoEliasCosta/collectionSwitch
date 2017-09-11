package de.heidelberg.pvs.diego.collections_online_adapter.optimizers;

public interface PerformanceModel<M, T> {
	
	public double calculatePerformance(M metrics);
	
	public T getType();

}
