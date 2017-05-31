package de.heidelberg.pvs.diego.collections_online_adapter.adaptive;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.collections.impl.set.mutable.UnifiedSet;
import org.junit.Test;
import org.openjdk.jol.info.GraphLayout;

import de.heidelberg.pvs.diego.collections_online_adapter.custom.ArraySet;
import gnu.trove.set.hash.THashSet;

public class AdaptiveSetTest {
	
	@Test
	public void testTotalFootprint() throws Exception {
		
		Set<Integer> set = new AdaptiveSet<Integer>();
		Set<Integer> arraySet = new ArraySet();
		Set<Integer> arrayNLPSet = new edu.stanford.nlp.util.ArraySet<Integer>();
		Set<Integer> troveSet = new THashSet<Integer>();
		Set<Integer> unifiedSet = new UnifiedSet<Integer>();
		Set<Integer> hashSet = new HashSet<Integer>();
		
		String footprint;
		
		for (int i = 0; i < 10000; i++) {
			set.add(i);
			arraySet.add(i);
			troveSet.add(i);
			arrayNLPSet.add(i);
			unifiedSet.add(i);
			hashSet.add(i);

			if(i % 100 == 0) {
				footprint = String.format("AdaptiveSet %s=%s \t ArraySet %s=%s \t ArrayNLPSet %s=%s \t TroveSet %s=%s \t UnifiedSet %s=%s \t HashSet %s=%s", 
						i, GraphLayout.parseInstance(set).totalSize(),
						i, GraphLayout.parseInstance(arraySet).totalSize(),
						i, GraphLayout.parseInstance(arrayNLPSet).totalSize(),
						i, GraphLayout.parseInstance(troveSet).totalSize(),
						i, GraphLayout.parseInstance(unifiedSet).totalSize(),
						i, GraphLayout.parseInstance(hashSet).totalSize());
				
				System.out.println(String.format("%s", footprint));
			}
		}
		
	}
	
	@Test
	public void testMemoryFootprint() throws Exception {
		
		Set<Integer> set = new AdaptiveSet<Integer>();
		Set<Integer> unifiedSet = new UnifiedSet<Integer>();
		Set<Integer> troveSet = new THashSet<Integer>();
		Set<Integer> hashSet = new HashSet<Integer>();
		
		for(int i = 0; i < 10; i++) {
			set.add(i);
			unifiedSet.add(i);
			troveSet.add(i);
			hashSet.add(i);
		}
		
		String footprint = String.format("AdaptiveSet %s \n\n TroveSet %s \n\n UnifiedSet %s \n\n HashSet %s", 
				 GraphLayout.parseInstance(set).toFootprint(),
				 GraphLayout.parseInstance(troveSet).toFootprint(),
				 GraphLayout.parseInstance(unifiedSet).toFootprint(),
				 GraphLayout.parseInstance(hashSet).toFootprint());
		
		System.out.println(String.format("%s", footprint));
		
		
	}

}
