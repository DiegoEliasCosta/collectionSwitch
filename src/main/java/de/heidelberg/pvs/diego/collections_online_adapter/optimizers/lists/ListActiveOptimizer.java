package de.heidelberg.pvs.diego.collections_online_adapter.optimizers.lists;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import de.heidelberg.pvs.diego.collections_online_adapter.context.ListAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.monitors.lists.ListActiveFullMonitor;
import de.heidelberg.pvs.diego.collections_online_adapter.monitors.lists.ListMetrics;
import de.heidelberg.pvs.diego.collections_online_adapter.utils.IntArrayUtils;

public class ListActiveOptimizer implements ListAllocationOptimizer {
	
	
	private List<ListMetrics> collectionsState;
	
	private ListAllocationContext context;

	private double finishedRatio;
	
	
	public ListActiveOptimizer(int windowSize, double finishedRatio) {
		this.collectionsState = new ArrayList<ListMetrics>(windowSize);
		this.finishedRatio = finishedRatio;
	}
	
	@Override
	public <E> List<E> createMonitor(List<E> list) {

		ListMetrics state = new ListMetrics(new WeakReference<List<E>>(list));
		collectionsState.add(state);
		return new ListActiveFullMonitor<E>(list, state);
	}

	@Override
	public void analyzeAndOptimize() {
		
		int n = collectionsState.size();
		int[] sizes = new int[n];
		int amountFinishedCollections = 0;
		
		for(int i = 0; i < n; i++) {
			ListMetrics state = collectionsState.get(i);
			
			if(state.hasCollectionFinished()) {
				amountFinishedCollections++;
				sizes[i] = state.getMaxSize();
			} else {
				// TODO: IMPLEMENT THIS
			}
			
		}
		
		// Only analyze it when 
		if(amountFinishedCollections >= n / finishedRatio) {
			
			double mean = IntArrayUtils.calculateMean(sizes);
			double std = IntArrayUtils.calculateStandardDeviation(sizes);
			
			// 
			int newInitialCapacity = (int) (mean + 2 * std);
			
			context.updateCollectionInitialCapacity(newInitialCapacity);
		}
		

		
	}

	@Override
	public void setContext(ListAllocationContext context) {
		this.context = context;
		
	}
	

}
