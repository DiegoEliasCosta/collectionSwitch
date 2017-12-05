package de.heidelberg.pvs.diego.collections_online_adapter.optimizers.maps;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;

import de.heidelberg.pvs.diego.collections_online_adapter.context.MapCollectionType;
import de.heidelberg.pvs.diego.collections_online_adapter.monitors.maps.MapMetrics;

public class MapPerformanceModel {

	private MapCollectionType type;

	private UnivariateFunction contains;
	private UnivariateFunction populate;
	private UnivariateFunction iterate;

	public MapPerformanceModel(MapCollectionType type, double[] populate, double[] contains, double[] iterate) {
		super();
		this.type = type;
		this.contains = new PolynomialFunction(contains);
		this.populate = new PolynomialFunction(populate);
		this.iterate = new PolynomialFunction(iterate);
	}

	public MapPerformanceModel(MapCollectionType type) {
		super();
		this.type = type;
	}

	public void setContains(double[] contains) {
		this.contains = new PolynomialFunction(contains);
	}

	public void setPopulate(double[] populate) {
		this.populate = new PolynomialFunction(populate);
	}

	public void setIterate(double[] iterate) {
		this.iterate = new PolynomialFunction(iterate);
	}

	public double calculatePerformance(MapMetrics state) {
		// FIXME: Flexibilize here
		int size = state.getMaxSize();

		return 1 * populate.value(size) 
				+ state.getContainsOp() * contains.value(size)
				+ state.getIterationOp() * iterate.value(size);
	}

	public MapCollectionType getType() {
		return type;
	}

}
