package de.heidelberg.pvs.diego.collections_online_adapter.optimizers.lists;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import de.heidelberg.pvs.diego.collections_online_adapter.context.ListAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.monitors.lists.ListActiveFullMonitor;
import de.heidelberg.pvs.diego.collections_online_adapter.monitors.lists.ListState;
import de.heidelberg.pvs.diego.collections_online_adapter.utils.IntArrayUtils;

public class ListActiveOptimizer implements ListOptimizer {
	
	
	private List<ListState> collectionsState;
	
	private ListAllocationContext context;
	
	
	public ListActiveOptimizer(ListAllocationContext context, int windowSize) {
		this.context = context;
		this.collectionsState = new ArrayList<ListState>(collectionsState);
	}
	
	@Override
	public <E> List<E> createMonitor(List<E> list) {

		ListState state = new ListState(new WeakReference<List<E>>(list));
		collectionsState.add(state);
		return new ListActiveFullMonitor<E>(list, state);
	}

	@Override
	public void analyzeAndOptimizeContext() {
		
		int[] sizes = new int[collectionsState.size()];
		
		for(int i = 0; i < collectionsState.size(); i++) {
			sizes[i] = collectionsState.get(i).getSize();
			
		}
		
		double mean = IntArrayUtils.calculateMean(sizes);
		double std = IntArrayUtils.calculateStandardDeviation(sizes);
		
		int newInitialCapacity = (int) (mean + 2 * std);
		
		context.updateCollectionInitialCapacity(newInitialCapacity);
		
	}
	

}
