package de.heidelberg.pvs.diego.collections_online_adapter.context;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.collections.impl.map.mutable.UnifiedMap;
import org.junit.Assert;
import org.junit.Test;

import com.google.api.client.util.ArrayMap;

import de.heidelberg.pvs.diego.collections_online_adapter.adaptive.AdaptiveMap;
import gnu.trove.map.hash.THashMap;
import net.openhft.koloboke.collect.map.hash.HashObjObjMap;
import vlsi.utils.CompactHashMap;

public class MapCollectionTypeTest {
	
	@Test
	public void testSimpleCreation() throws Exception {
		
		
		MapCollectionType type = MapCollectionType.FASTUTILS_HASHMAP;
		Map<?,?> map = type.createMap();
		Assert.assertTrue(map instanceof THashMap);
		
		type = MapCollectionType.GOOGLE_ARRAYMAP;
		map = type.createMap();
		Assert.assertTrue(map instanceof ArrayMap);
		
		type = MapCollectionType.GSCOLLECTIONS_UNIFIEDMAP;
		map = type.createMap();
		Assert.assertTrue(map instanceof UnifiedMap);
		
		type = MapCollectionType.JDK_HASHMAP;
		map = type.createMap();
		Assert.assertTrue(map instanceof HashMap);
		
		type = MapCollectionType.JDK_LINKEDHASHMAP;
		map = type.createMap();
		Assert.assertTrue(map instanceof LinkedHashMap);
		
		type = MapCollectionType.KOLOBOKE_HASHMAP;
		map = type.createMap();
		Assert.assertTrue(map instanceof HashObjObjMap);
		
		type = MapCollectionType.NAYUKI_COMPACTHASHMAP;
		map = type.createMap();
		Assert.assertTrue(map instanceof CompactHashMap);
		
		type = MapCollectionType.NLP_ARRAYMAP;
		map = type.createMap();
		Assert.assertTrue(map instanceof edu.stanford.nlp.util.ArrayMap);
		
		type = MapCollectionType.ONLINEADAPTER_ADAPTIVEMAP;
		map = type.createMap();
		Assert.assertTrue(map instanceof AdaptiveMap);
		
	}

}
