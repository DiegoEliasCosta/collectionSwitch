package de.heidelberg.pvs.diego.collections_online_adapter;

import static org.junit.Assert.*;

import org.junit.Test;

import de.heidelberg.pvs.diego.collections_online_adapter.utils.IntArrayUtils;

public class ArrayUtilsTest {
	
	@Test
	public void testModeThreshold() throws Exception {
		
		int[] array = new int[10];
		
		array[0] = 15;
		array[1] = 15;
		array[2] = 15;
		array[3] = 15;
		array[4] = 15;
		array[5] = 15;
		array[6] = 15;
		array[7] = 15;
		//
		array[8] = 14;
		array[9] = 14;
		
		int calculateModeWithThrehsold = IntArrayUtils.calculateModeWithThrehsold(array, 7);
		assertEquals(15, calculateModeWithThrehsold);
		
		calculateModeWithThrehsold = IntArrayUtils.calculateModeWithThrehsold(array, 8);
		assertEquals(15, calculateModeWithThrehsold);
		
		calculateModeWithThrehsold = IntArrayUtils.calculateModeWithThrehsold(array, 9);
		assertEquals(-1, calculateModeWithThrehsold);
		
		
	}

}
