package de.heidelberg.pvs.diego.collections_online_adapter.stub;

import java.util.List;

import de.heidelberg.pvs.diego.collections_online_adapter.context.CollectionTypeEnum;
import de.heidelberg.pvs.diego.collections_online_adapter.context.ListAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.lists.ListAllocationOptimizer;

public class ListOptmizerStub implements ListAllocationOptimizer {


	@Override
	public int getMonitoringIndex() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void updateOperationsAndSize(int index, int indexOp, int midListOp, int contiansOp, int size) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateSize(int index, int size) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setContext(ListAllocationContext context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void checkFinalizedAnalysis() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateVote(int index, CollectionTypeEnum vote) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addReference(List<?> list) {
		// TODO Auto-generated method stub
		
	}

}
