package de.heidelberg.pvs.diego.collections_online_adapter.utils;

import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
import org.junit.Test;

import junit.framework.Assert;

public class PolynomialTest {

	@Test
	public void testSimplePolynomial() throws Exception {
		
		double[] coef = {2, 3, 1};
		PolynomialFunction function = new PolynomialFunction(coef);
		
		double value = function.value(10);
		Assert.assertEquals(132.0, value);
		
		
	}
	
}
 