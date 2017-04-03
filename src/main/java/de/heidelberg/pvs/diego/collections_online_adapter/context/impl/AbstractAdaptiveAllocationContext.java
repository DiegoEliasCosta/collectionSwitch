package de.heidelberg.pvs.diego.collections_online_adapter.context.impl;

import de.heidelberg.pvs.diego.collections_online_adapter.context.AllocationContextState;
import de.heidelberg.pvs.diego.collections_online_adapter.context.AllocationContextUpdatable;
import de.heidelberg.pvs.diego.collections_online_adapter.context.CollectionTypeEnum;

public abstract class AbstractAdaptiveAllocationContext implements AllocationContextUpdatable {

	protected AllocationContextState state;

	protected CollectionTypeEnum championCollectionType;
	protected CollectionTypeEnum defaultCollectionType;

	protected int analyzedInitialCapacity;

	protected int sleepingMonitoringCount;

	protected final int fullAnalysisThreshold;
	protected final int sleepingFrequency;
	private final int divergentRoundsThreshold;

	private int curentDivergenceCount;

	public AbstractAdaptiveAllocationContext(CollectionTypeEnum collectionType, int windowSize,
			int fullAnalysisThreshold, int sleepingFrequency, int convergencyRate, int divergentRoundsThreshold) {
		super();
		this.championCollectionType = collectionType;
		this.defaultCollectionType = collectionType;
		this.fullAnalysisThreshold = fullAnalysisThreshold;
		this.sleepingFrequency = sleepingFrequency;
		
		this.divergentRoundsThreshold = divergentRoundsThreshold;
		this.curentDivergenceCount = 0;

		// First State
		this.state = AllocationContextState.ACTIVE_MEMORY;
		analyzedInitialCapacity = 10;
	}

	protected boolean shouldMonitor() {
		return sleepingMonitoringCount++ % this.sleepingFrequency == 0;
	}
	

	@Override
	public void optimizeCollectionType(CollectionTypeEnum collection, int medianInitialCapacity) {
		this.championCollectionType = collection;
		this.analyzedInitialCapacity = medianInitialCapacity;
		
		switch (state) {
		
		case INACTIVE:
			break;

		case ACTIVE_FULL:
		case SLEEPING_FULL:
			if (this.analyzedInitialCapacity < fullAnalysisThreshold) {
				this.state = AllocationContextState.ACTIVE_MEMORY;
			} else {
				this.state = AllocationContextState.SLEEPING_FULL;
			}
			this.curentDivergenceCount = 0;
			break;

		case ACTIVE_MEMORY:
		case SLEEPING_MEMORY:
			if (this.analyzedInitialCapacity > fullAnalysisThreshold) {
				this.state = AllocationContextState.ACTIVE_FULL;
			} else {
				this.state = AllocationContextState.SLEEPING_MEMORY;
			}
			this.curentDivergenceCount = 0;
			break;
		default:
			break;
			
		}

	}

	@Override
	public void noCollectionTypeConvergence(int medianInitialCapacity) {

		this.analyzedInitialCapacity = medianInitialCapacity;
		
		if (this.analyzedInitialCapacity < fullAnalysisThreshold) {
			this.state = AllocationContextState.ACTIVE_MEMORY;
		} else {
			this.state = AllocationContextState.ACTIVE_FULL;
		}
		
		this.curentDivergenceCount++;
		
		if(curentDivergenceCount >= this.divergentRoundsThreshold) {
			this.state = AllocationContextState.INACTIVE;
		}

	}

	public AllocationContextState getAllocationContextState() {
		return this.state;
	}

	public void setAllocationContextState(AllocationContextState state) {
		this.state = state;
	}
	
	public CollectionTypeEnum getChampion() {
		return this.championCollectionType;
	}

}
