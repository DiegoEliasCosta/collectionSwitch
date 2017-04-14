package de.heidelberg.pvs.diego.collections_online_adapter.automata;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.heidelberg.pvs.diego.collections_online_adapter.context.CollectionTypeEnum;
import de.heidelberg.pvs.diego.collections_online_adapter.context.impl.AbstractAdaptiveAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.context.impl.AdaptiveMapAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.factories.AllocationContextFactory;
import de.heidelberg.pvs.diego.collections_online_adapter.factories.AllocationContextFactory.AllocationContextBuilder;
import de.heidelberg.pvs.diego.collections_online_adapter.factories.AllocationContextFactory.AllocationContextBuilder.AllocationContextAlgorithm;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.maps.RuleBasedMapOptimizer;

public class AdaptiveHashMapAutomataStateTest extends AbstractAutomataStateTest<Map<Integer, Integer>> {

	Map<Integer, Integer> hashMapSize;
	Map<Integer, Integer> unifiedMapSize;

	@Override
	protected void specificSetup() {

		hashMapSize = new HashMap();
		unifiedMapSize = new HashMap();

		for (int i = 0; i < RuleBasedMapOptimizer.RULE_ARRAY_SIZE + 1; i++) {
			unifiedMapSize.put(i, i);
		}

		for (int i = 0; i < RuleBasedMapOptimizer.RULE_UNIFIED_SIZE; i++) {
			hashMapSize.put(i, i);
		}
		// Extra
		hashMapSize.put(100000, 10000);
		hashMapSize.put(1000001, 100001);

	}

	@Override
	protected AbstractAdaptiveAllocationContext buildContext() {
		AllocationContextBuilder builder = new AllocationContextBuilder(CollectionTypeEnum.HASH, "AdaptiveHashMapAutomata_TEST");
		builder.withAlgorithm(AllocationContextAlgorithm.ADAPTIVE);
		return (AbstractAdaptiveAllocationContext) builder.buildMapAllocationContext();
		
	}

	@Override
	protected Map<Integer, Integer> createCollection(AbstractAdaptiveAllocationContext context) {
		return ((AdaptiveMapAllocationContext) context).createMap();
	}

	@Override
	protected List<Map<Integer, Integer>> createSmallConvergentCollections(List<Map<Integer, Integer>> collections) {

		for (Map<Integer, Integer> map : collections) {
			map.put(1, 1);
		}

		return collections;
	}

	@Override
	protected List<Map<Integer, Integer>> createSmallDivergentCollections(List<Map<Integer, Integer>> collections) {

		// CONVERT TO HASH
		for (int i = 0; i < collections.size(); i++) {

			Map<Integer, Integer> map = collections.get(i);
			
			if(i % 2 == 0) {
				// ARRAY
				map.put(1, 1);
				
			} else {
				// UNIFIED
				map.putAll(unifiedMapSize);
			}
			
		}

		return null;
	}

	@Override
	protected List<Map<Integer, Integer>> createLargeConvergentCollections(List<Map<Integer, Integer>> collections) {

		// CONVERT TO HASH
		for (Map<Integer, Integer> map : collections) {
			map.putAll(hashMapSize);
		}

		return collections;
	}

	@Override
	protected List<Map<Integer, Integer>> createLargeDivergentCollections(List<Map<Integer, Integer>> collections) {

		// CONVERT TO HASH
		for (int i = 0; i < collections.size(); i++) {
			Map<Integer, Integer> map = collections.get(i);

			if (i % 3 == 0) {
				// HASH
				map.putAll(hashMapSize);
			} else if (i % 3 == 1) {
				// LINKED
				map.putAll(hashMapSize);

				// ITERATE
				for (int j = 0; j < RuleBasedMapOptimizer.RULE_LINKED_ITERATIONS; j++) {
					Set<Integer> keySet = map.keySet();
					for (Integer integer : keySet) {
						integer.intValue();
					}
				}

			} else {
				// UNIFIED
				map.putAll(unifiedMapSize);
			}
		}

		return collections;
	}

}
