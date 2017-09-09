package de.heidelberg.pvs.diego.collections_online_adapter.optimizers.lists;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import de.heidelberg.pvs.diego.collections_online_adapter.context.CollectionTypeEnum;
import de.heidelberg.pvs.diego.collections_online_adapter.context.ListAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.monitors.lists.ListActiveFullMonitor;
import de.heidelberg.pvs.diego.collections_online_adapter.monitors.lists.ListMetrics;

public class ListEmpiricalOptimizer implements ListAllocationOptimizer {
	
	
	private List<ListMetrics> collectionsState;
	
	private ListAllocationContext context;
	private ListEmpiricalPerformanceEvaluator evaluator;
	private CollectionTypeEnum type;

	private int ratio;
	
	public ListEmpiricalOptimizer(int windowSize, double finishedRatio, CollectionTypeEnum defaultType) {
		this.collectionsState = new ArrayList<ListMetrics>(windowSize);
		this.ratio = (int) (collectionsState.size() / finishedRatio);
		this.evaluator = new ListEmpiricalPerformanceEvaluator(4);
		this.type = defaultType;		
	}
	
	@Override
	public <E> List<E> createMonitor(List<E> list) {

		ListMetrics state = new ListMetrics(new WeakReference<List<E>>(list));
		collectionsState.add(state);
		return new ListActiveFullMonitor<E>(list, state);
	}

	@Override
	public void analyzeAndOptimize() {
		
		int amountFinishedCollections = 0;
		for(ListMetrics metric : collectionsState) {
			if(metric.hasCollectionFinished()) amountFinishedCollections++;
		}
		
		// Only analyze it when a ratio of collections have been finished already
		if(amountFinishedCollections >= ratio) {
			
			//CollectionTypeEnum bestType = evaluator.evaluatePerformance(collectionsState);
			// TODO: TO implement
		}
		

		
	}

	@Override
	public void setContext(ListAllocationContext context) {
		this.context = context;
		
	}
	
}
