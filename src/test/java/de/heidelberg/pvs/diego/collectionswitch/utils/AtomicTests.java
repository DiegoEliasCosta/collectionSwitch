package de.heidelberg.pvs.diego.collectionswitch.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class AtomicTests {
	
	@Test
	public void testIncrementAssignment() throws Exception {
		
		int i = 0;
		int j = i++;
		
		// J is assigned before i increments
		assertEquals(0, j);
		assertEquals(1, i);
		
	}

}
