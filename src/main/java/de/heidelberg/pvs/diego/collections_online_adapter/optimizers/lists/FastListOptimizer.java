package de.heidelberg.pvs.diego.collections_online_adapter.optimizers.lists;

import java.util.List;

import de.heidelberg.pvs.diego.collections_online_adapter.context.AllocationContextState;
import de.heidelberg.pvs.diego.collections_online_adapter.context.CollectionTypeEnum;
import de.heidelberg.pvs.diego.collections_online_adapter.context.ListAllocationContext;

public class FastListOptimizer implements ListAllocationOptimizer {

	private static final float ALPHA = 0.9f;
	private ListAllocationContext context;
	private int finalizedCount;
	private int analyzedInitialCapacity;
	private int analyzedContains;
	
	private CollectionTypeEnum championCollection;
	

	@Override
	public void updateOperationsAndSize(int index, int indexOp, int midListOp, int contiansOp, int size) {
		
		int finalizedCopy = finalizedCount;
		
		if (finalizedCopy == 0) {
			analyzedInitialCapacity = size;
			analyzedContains = contiansOp;
			finalizedCount++;

		} else {
			analyzedInitialCapacity = (int) (analyzedInitialCapacity * ALPHA + (1 - ALPHA) * size);
			analyzedContains = (int) (analyzedContains * ALPHA + (1 - ALPHA) * size);
			
			if(analyzedContains > 20) {
				this.championCollection = CollectionTypeEnum.HASH;
			}
			
			finalizedCount++;
			
		}
		
		if(finalizedCopy == 10) {
			this.context.setAllocationContextState(AllocationContextState.ACTIVE);
		}
		
	}

	@Override
	public void updateSize(int index, int size) {
		
		int finalizedCopy = finalizedCount;
		
		if (finalizedCopy == 0) {
			analyzedInitialCapacity = size;
			finalizedCount++;

		} else {
			analyzedInitialCapacity = (int) (analyzedInitialCapacity * ALPHA + (1 - ALPHA) * size);
			finalizedCount++;
			
		}
		
		if(finalizedCopy == 10) {
			this.context.setAllocationContextState(AllocationContextState.ACTIVE);
		}	
		
	}

	@Override
	public int getMonitoringIndex() {
		// IXME
		return 1;
	}

	@Override
	public void setContext(ListAllocationContext context) {
		this.context = context;
		
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
