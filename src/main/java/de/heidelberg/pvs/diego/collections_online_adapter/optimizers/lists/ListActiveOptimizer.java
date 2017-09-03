package de.heidelberg.pvs.diego.collections_online_adapter.optimizers.lists;

import java.util.ArrayList;
import java.util.List;

import de.heidelberg.pvs.diego.collections_online_adapter.context.ListAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.monitors.lists.ListActiveFullMonitor;
import de.heidelberg.pvs.diego.collections_online_adapter.monitors.lists.ListState;

public class ListActiveOptimizer implements ListOptimizer {
	
	
	private List<ListState> collectionsState;
	
	private ListAllocationContext context;
	
	
	public ListActiveOptimizer(ListAllocationContext context, int windowSize) {
		this.context = context;
		this.collectionsState = new ArrayList<ListState>(collectionsState);
	}
	
	@Override
	public List<?> createMonitor(List<?> list) {

		ListState newState = new ListState();
		collectionsState.add(newState);
		List<?> monitor = new ListActiveFullMonitor<>(list, newState);
		return monitor;
	}

	@Override
	public void analyzeAndOptimizeContext() {
		// TODO To be implemented
		
	}
	

}
