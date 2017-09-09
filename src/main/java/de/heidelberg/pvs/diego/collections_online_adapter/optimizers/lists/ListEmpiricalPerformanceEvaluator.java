package de.heidelberg.pvs.diego.collections_online_adapter.optimizers.lists;

import java.util.List;

import de.heidelberg.pvs.diego.collections_online_adapter.context.CollectionTypeEnum;

public class ListEmpiricalPerformanceEvaluator {

	private int nTypes;
	
	// TODO: Add the support of passing this as a parameter
	private final static double[][] array_coef = {};
	private final static double[][] link_coef = {};
	private final static double[][] hash_coef = {};
	private final static double[][] adap_coef = {};
	
	public ListEmpiricalPerformanceEvaluator(int typesConsidered) {
		
		this.nTypes = typesConsidered;
		
	}

	public EmpiricalPerformance evaluatePerformance(List<CollectionTypeEnum> metrics) {
		
		for(CollectionTypeEnum metric : metrics) {
			
			
			
		}
		
		return null;
	}
	
	public class EmpiricalPerformance {
		
		
		
	}

}
