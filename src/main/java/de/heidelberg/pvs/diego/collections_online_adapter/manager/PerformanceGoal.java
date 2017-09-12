package de.heidelberg.pvs.diego.collections_online_adapter.manager;

public class PerformanceGoal {
	
	public final PerformanceDimension majorDimension;
	public final PerformanceDimension minorDimension;
	public final double maxPenalty;
	public final double minImprovement;
	
	public enum PerformanceDimension {
		TIME,
		ALLOCATION,
		MEMORY_FOOTPRINT;
	}
	
	public PerformanceGoal(PerformanceDimension major, PerformanceDimension minor, double minInprovement, double maxPenalty) {
		this.majorDimension = major;
		this.minorDimension = minor;
		this.minImprovement = minInprovement;
		this.maxPenalty = maxPenalty;
	}
	
	public PerformanceGoal() {
		majorDimension = PerformanceDimension.TIME;
		minorDimension = PerformanceDimension.ALLOCATION;
		maxPenalty = 0.7;
		minImprovement = 1.2;
	}

	
	
}
