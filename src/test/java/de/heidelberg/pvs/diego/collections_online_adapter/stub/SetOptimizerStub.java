package de.heidelberg.pvs.diego.collections_online_adapter.stub;

import java.util.Set;

import de.heidelberg.pvs.diego.collections_online_adapter.context.SetAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.sets.SetAllocationOptimizer;

public class SetOptimizerStub implements SetAllocationOptimizer {


	@Override
	public void updateOperationsAndSize(int index, int containsOp, int iterationOp, int size) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateSize(int index, int size) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getMonitoringIndex() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setContext(SetAllocationContext context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addReference(Set<?> set) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void checkFinalizedAnalysis() {
		// TODO Auto-generated method stub
		
	}

}
