package de.heidelberg.pvs.diego.collections_online_adapter.optimizers.sets;

import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;

import de.heidelberg.pvs.diego.collections_online_adapter.context.SetCollectionType;
import de.heidelberg.pvs.diego.collections_online_adapter.monitors.sets.SetMetrics;

public class SetPerformanceModel {
	
	private final SetCollectionType type;
	
	private final PolynomialFunction contains;
	private final PolynomialFunction populate;
	private final PolynomialFunction iterate;
	
	public SetPerformanceModel(SetCollectionType type, double[] contains, double[] populate, double[] iterate) {
		super();
		this.type = type;
		this.contains = new PolynomialFunction(contains);
		this.populate = new PolynomialFunction(populate);
		this.iterate = new PolynomialFunction(iterate);
	}
	
	public double calculatePerformance(int size, int nPopulate, int nContains, int cIterate) {
		
		return nPopulate * populate.value(size) + 
				nContains * contains.value(size) +
				cIterate * iterate.value(size);
	}
	
	public double calculatePerformance(SetMetrics state) {
		
		int size = state.getSize();
		
		return 1 * populate.value(size) + 
				state.getContainsOp() * contains.value(size) +
				state.getIterationOp() * iterate.value(size);
	}
	
	public SetCollectionType getType() {
		return type;
	}
	
}
