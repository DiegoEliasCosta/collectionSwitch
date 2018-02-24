package de.heidelberg.pvs.diego.collectionswitch.annotations;

import java.util.List;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;

public class InjectionTest {
	
	@Inject 
	@Switch(name = "list")
	Instance<List<Integer>> myList;

	
	@Test
	public void simpleInjectionTest() {
		
		Assert.assertNotNull(myList);
		
	}
	
}
