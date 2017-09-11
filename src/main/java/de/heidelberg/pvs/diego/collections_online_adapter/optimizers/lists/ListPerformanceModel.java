package de.heidelberg.pvs.diego.collections_online_adapter.optimizers.lists;

import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;

import de.heidelberg.pvs.diego.collections_online_adapter.context.ListCollectionType;
import de.heidelberg.pvs.diego.collections_online_adapter.monitors.lists.ListMetrics;

public class ListPerformanceModel {

	private final ListCollectionType type;

	private final PolynomialFunction contains;
	private final PolynomialFunction populate;
	private final PolynomialFunction iterate;
	private final PolynomialFunction index;

	public ListPerformanceModel(ListCollectionType type, double[] populate, double[] contains, 
			double[] iterate, double[] index) {
		super();
		this.type = type;
		this.contains = new PolynomialFunction(contains);
		this.populate = new PolynomialFunction(populate);
		this.iterate = new PolynomialFunction(iterate);
		this.index = new PolynomialFunction(index);
	}

	public double calculatePerformance(ListMetrics state) {

		int size = state.getSize();

		return populate.value(size) 
				+ state.getContainsOp() * contains.value(size)
				+ state.getIterationOp() * iterate.value(size)
				+ state.getIndexOp() * index.value(size);
	}

	public ListCollectionType getType() {
		return type;
	}

}
