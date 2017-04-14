package de.heidelberg.pvs.diego.collections_online_adapter.optimizers.lists;

import java.util.List;

import de.heidelberg.pvs.diego.collections_online_adapter.context.CollectionTypeEnum;
import de.heidelberg.pvs.diego.collections_online_adapter.context.ListAllocationContext;

public interface ListAllocationOptimizer {
	
	void updateOperationsAndSize(int index, int indexOp, int midListOp, int contiansOp, int size);

	void updateSize(int index, int size);

	int getMonitoringIndex();

	void setContext(ListAllocationContext context);
	
	void checkFinalizedAnalysis();

	void updateVote(int index, CollectionTypeEnum vote);
	
	void addReference(List<?> list);
	
}
