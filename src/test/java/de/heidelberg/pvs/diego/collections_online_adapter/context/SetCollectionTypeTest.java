package de.heidelberg.pvs.diego.collections_online_adapter.context;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.collections.impl.set.mutable.UnifiedSet;
import org.junit.Assert;
import org.junit.Test;

import de.heidelberg.pvs.diego.collections_online_adapter.adaptive.AdaptiveSet;
import gnu.trove.set.hash.THashSet;
import net.openhft.koloboke.collect.set.hash.HashObjSet;

public class SetCollectionTypeTest {
	
	@Test
	public void testSimpleCreation() throws Exception {
		
		
		SetCollectionType type = SetCollectionType.FASTUTILS_HASHSET;
		Set<?> Set = type.createSet();
		Assert.assertTrue(Set instanceof THashSet);
		
		type = SetCollectionType.GSCOLLECTIONS_UNIFIEDSET;
		Set = type.createSet();
		Assert.assertTrue(Set instanceof UnifiedSet);
		
		type = SetCollectionType.JDK_HASHSET;
		Set = type.createSet();
		Assert.assertTrue(Set instanceof HashSet);
		
		type = SetCollectionType.JDK_LINKEDHASHSET;
		Set = type.createSet();
		Assert.assertTrue(Set instanceof LinkedHashSet);
		
		type = SetCollectionType.KOLOBOKE_HASHSET;
		Set = type.createSet();
		Assert.assertTrue(Set instanceof HashObjSet);
		
		type = SetCollectionType.NLP_ARRAYSET;
		Set = type.createSet();
		Assert.assertTrue(Set instanceof edu.stanford.nlp.util.ArraySet);
		
		type = SetCollectionType.ONLINEADAPTER_ADAPTIVESET;
		Set = type.createSet();
		Assert.assertTrue(Set instanceof AdaptiveSet);
		
	}

}
