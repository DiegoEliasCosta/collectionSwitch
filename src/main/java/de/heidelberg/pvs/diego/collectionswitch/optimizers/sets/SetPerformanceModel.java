package de.heidelberg.pvs.diego.collectionswitch.optimizers.sets;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;

import de.heidelberg.pvs.diego.collectionswitch.context.SetCollectionType;
import de.heidelberg.pvs.diego.collectionswitch.monitors.sets.SetMetrics;

public class SetPerformanceModel  {
	
	private final SetCollectionType type;
	
	private final UnivariateFunction contains;
	private final UnivariateFunction populate;
	private final UnivariateFunction iterate;
	
	public SetPerformanceModel(SetCollectionType type, double[] populate, double[] contains, double[] iterate) {
		super();
		this.type = type;
		this.contains = new PolynomialFunction(contains);
		this.populate = new PolynomialFunction(populate);
		this.iterate = new PolynomialFunction(iterate);
	}
	
	public double calculatePerformance(SetMetrics state) {
		
		int size = state.getMaxSize();
		
		return 1 * populate.value(size) + 
				state.getContainsOp() * contains.value(size) +
				state.getIterationOp() * iterate.value(size);
	}
	
	public SetCollectionType getType() {
		return type;
	}
	
}
