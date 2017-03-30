package de.heidelberg.pvs.diego.collections_online_adapter.automata;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.heidelberg.pvs.diego.collections_online_adapter.context.CollectionTypeEnum;
import de.heidelberg.pvs.diego.collections_online_adapter.context.impl.AbstractAdaptiveAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.context.impl.AdaptiveSetAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.factories.AllocationContextFactory;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.sets.RuleBasedSetOptimizer;

public class AdaptiveHashSetAutomataStateTest extends AbstractAutomataStateTest<Set<Integer>> {

	Set<Integer> fullSet;
	Set<Integer> hashSetSize;
	Set<Integer> unifiedSetSize;
	Set<Integer> arraySetSize;

	@Override
	protected void specificSetup() {
		fullSet = new HashSet<>();
		arraySetSize = new HashSet<>();
		unifiedSetSize = new HashSet<>();
		hashSetSize = new HashSet<>();

		for (int i = 0; i < AllocationContextFactory.FULL_ANALYSIS_THRESHOLD + 1; i++) {
			fullSet.add(i);
		}

		for (int i = 0; i < RuleBasedSetOptimizer.ARRAY_THRESHOLD; i++) {
			arraySetSize.add(i);
		}
		
		for (int i = 0; i < RuleBasedSetOptimizer.UNIFIED_THRESHOLD; i++) {
			unifiedSetSize.add(i);
		}
		
		for (int i = 0; i < RuleBasedSetOptimizer.UNIFIED_THRESHOLD + 1; i++) {
			hashSetSize.add(i);
		}
		
		
	}

	@Override
	protected AbstractAdaptiveAllocationContext buildContext() {
		return (AbstractAdaptiveAllocationContext) AllocationContextFactory.buildSetContext(CollectionTypeEnum.HASH);
	}

	@Override
	protected Set<Integer> createCollection(AbstractAdaptiveAllocationContext context) {
		return ((AdaptiveSetAllocationContext) context).createSet();
	}

	@Override
	protected List<Set<Integer>> createSmallConvergentCollections(List<Set<Integer>> collections) {
		for (Set<Integer> list : collections) {
			list.add(1);
			list.add(2);
		}
		return collections;
	}

	@Override
	protected List<Set<Integer>> createSmallDivergentCollections(List<Set<Integer>> collections) {

		for (int i = 0; i < collections.size(); i++) {
			
			Set<Integer> set = collections.get(i);
			
			if(i % 2 == 0) {
				// ARRAY
				set.addAll(arraySetSize);
			} else {
				// UNIFIED
				set.addAll(arraySetSize);
				set.add(1000);
				set.add(1001);
			}
			
		}
		return collections;

	}

	@Override
	protected List<Set<Integer>> createLargeConvergentCollections(List<Set<Integer>> collections) {

		for (Set<Integer> list : collections) {
			list.addAll(fullSet);
		}
		return collections;

	}

	@Override
	protected List<Set<Integer>> createLargeDivergentCollections(List<Set<Integer>> collections) {

		for (int i = 0; i < collections.size(); i++) {
			
			Set<Integer> set = collections.get(i);
			
			if(i % 3 == 0) {
				// HASH
				set.addAll(hashSetSize);
				set.add(1000000);
			} else if(i % 3 == 1) {
				// UNIFIED
				set.addAll(fullSet);
			} else {
				// ARRAY
				set.addAll(arraySetSize);
			}
			
		}
		return collections;
	}

}
