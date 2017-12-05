package de.heidelberg.pvs.diego.collectionswitch.manager;

public class PerformanceGoal {
	
	public final PerformanceDimension majorDimension;
	public final PerformanceDimension minorDimension;
	public final double maxPenalty;
	public final double minImprovement;
	
	public enum PerformanceDimension {
		TIME,
		ALLOCATION,
		MEMORY_FOOTPRINT;

		public static PerformanceDimension parse(String majorDimension) {
			
			if(majorDimension.equalsIgnoreCase("time")){
				return TIME;
			}
			
			if(majorDimension.equalsIgnoreCase("allocation")){
				return ALLOCATION;
			}
			
			return null;
		}
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
