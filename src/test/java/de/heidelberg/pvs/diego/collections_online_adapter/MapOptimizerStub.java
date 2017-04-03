package de.heidelberg.pvs.diego.collections_online_adapter;

import de.heidelberg.pvs.diego.collections_online_adapter.context.AllocationContextUpdatable;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.maps.MapAllocationOptimizer;

public class MapOptimizerStub implements MapAllocationOptimizer {

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
	public void setContext(AllocationContextUpdatable context) {
		// TODO Auto-generated method stub
		
	}

}
