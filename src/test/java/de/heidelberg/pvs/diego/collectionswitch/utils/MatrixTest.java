package de.heidelberg.pvs.diego.collectionswitch.utils;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.junit.Test;

public class MatrixTest {

	@Test
	public void testSimpleMultiplication() throws Exception {
		
		
		double[][] a = {{1, 1, 4, 5}};
		double[][] b = {{1, 2, 3}, {1,1,1}, {1,1,1}, {1,1,1}};
		double[][] c = {{1,1,1}}; 

		RealMatrix matrixA = MatrixUtils.createRealMatrix(a);
		RealMatrix matrixB = MatrixUtils.createRealMatrix(b);
		RealMatrix matrixC = MatrixUtils.createRealMatrix(c);
		
		RealMatrix result = matrixA.multiply(matrixB).add(matrixC);
		
		System.out.println(result);
		
	}

}
