package de.heidelberg.pvs.diego.collections_online_adapter.custom;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.collections.impl.set.mutable.UnifiedSet;
import org.junit.Test;
import org.openjdk.jol.info.GraphLayout;

public class CustomCollectionsTest {

	@Test
	public void testArraySet() throws Exception {

		// Write to the file
		ArraySet root = new ArraySet();
		String footprint = String.format("ArraySet\n%s", GraphLayout.parseInstance(root).toFootprint());

		System.out.println(String.format("%s", footprint));

		
		root.add(2);
		// Write to the file
		footprint = String.format("ArraySet (1)\n%s", GraphLayout.parseInstance(root).toFootprint());

		System.out.println(String.format("%s", footprint));

	}
	
	@Test
	public void testHashSet() throws Exception {

		// Write to the file
		Set<Integer> root = new HashSet();
		String footprint = String.format("HashSet\n%s", GraphLayout.parseInstance(root).toFootprint());

		System.out.println(String.format("%s", footprint));

		
		root.add(2);
		// Write to the file
		footprint = String.format("HashSet (1)\n%s", GraphLayout.parseInstance(root).toFootprint());

		System.out.println(String.format("%s", footprint));

	}

	@Test
	public void testUnifiedSet() throws Exception {

		// Write to the file
		Set<Integer> root = new UnifiedSet<Integer>();
		String footprint = String.format("Unified Set\n%s", GraphLayout.parseInstance(root).toFootprint());

		System.out.println(String.format("%s", footprint));

		
		root.add(2);
		// Write to the file
		footprint = String.format("Unified Set (1)\n%s", GraphLayout.parseInstance(root).toFootprint());

		System.out.println(String.format("%s", footprint));

	}
	
}
