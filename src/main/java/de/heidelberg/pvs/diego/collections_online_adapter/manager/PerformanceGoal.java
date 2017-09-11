package de.heidelberg.pvs.diego.collections_online_adapter.manager;

public enum PerformanceGoal {
	
	INSTANCE;
	
	public enum PerformanceDimension {
		TIME,
		ALLOCATION,
		MEMORY_FOOTPRINT;
	}
	
	public void init(PerformanceDimension major, PerformanceDimension minor, double minInprovement, double maxPenalty) {
		this.majorDimension = major;
		this.minorDimension = minor;
		this.minImprovement = minInprovement;
		this.maxPenalty = maxPenalty;
	}

	public PerformanceDimension majorDimension = PerformanceDimension.TIME;
	public PerformanceDimension minorDimension = PerformanceDimension.ALLOCATION;
	public double maxPenalty = 0.7;
	public double minImprovement = 1.2;
	
}
