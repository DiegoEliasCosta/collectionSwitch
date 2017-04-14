package de.heidelberg.pvs.diego.collections_online_adapter.optimizers.maps;

import java.util.Map;

import de.heidelberg.pvs.diego.collections_online_adapter.context.AllocationContextUpdatable;
import de.heidelberg.pvs.diego.collections_online_adapter.context.CollectionTypeEnum;

public interface MapAllocationOptimizer {

	void updateOperationsAndSize(int index, int containsOp, int iterationOp, int size);

	void updateSize(int index, int size);
	
	void updateVote(int index, CollectionTypeEnum vote);

	int getMonitoringIndex();

	void setContext(AllocationContextUpdatable context);
	
	void addReference(Map<?, ?> map);
	
	void checkFinalizedAnalysis();


}
