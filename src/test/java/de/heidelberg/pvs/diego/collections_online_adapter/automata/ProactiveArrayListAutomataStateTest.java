package de.heidelberg.pvs.diego.collections_online_adapter.automata;

import java.util.ArrayList;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import de.heidelberg.pvs.diego.collections_online_adapter.context.CollectionTypeEnum;
import de.heidelberg.pvs.diego.collections_online_adapter.context.ListAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.context.impl.AbstractAdaptiveAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.factories.AllocationContextFactory;
import de.heidelberg.pvs.diego.collections_online_adapter.factories.AllocationContextFactory.AllocationContextBuilder;
import de.heidelberg.pvs.diego.collections_online_adapter.factories.AllocationContextFactory.AllocationContextBuilder.AllocationContextAlgorithm;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.lists.RuleBasedListOptimizer;

public class ProactiveArrayListAutomataStateTest extends AbstractAutomataStateTest<List<Integer>> {

	List<Integer> fullList;

	@Override
	protected void specificSetup() {
		fullList = new ArrayList();

		for (int i = 0; i < AllocationContextFactory.FULL_ANALYSIS_THRESHOLD + 1; i++) {
			fullList.add(i);
		}
		// Extra
		fullList.add(10);
		fullList.add(10);

	}

	@Override
	protected AbstractAdaptiveAllocationContext buildContext() {
		AllocationContextBuilder builder = new AllocationContextBuilder(CollectionTypeEnum.ARRAY, "ProactiveArrayList_Test");
		builder.withAlgorithm(AllocationContextAlgorithm.PROACTIVE);
		return (AbstractAdaptiveAllocationContext) builder.buildListAllocationContext();
	}

	protected List<Integer> createCollection(AbstractAdaptiveAllocationContext context) {
		return ((ListAllocationContext) context).createList();
	}

	protected List<List<Integer>> fillCollectionsAboveSizeLimit(List<List<Integer>> collectionCreated) {

		for (List<Integer> list : collectionCreated) {
			list.addAll(fullList);
		}
		return collectionCreated;
	}

	protected List<List<Integer>> fillCollectionsBelowSizeLimit(List<List<Integer>> collectionCreated) {

		for (List<Integer> list : collectionCreated) {
			list.add(1);
			list.add(2);
		}
		return collectionCreated;

	}

	protected List<List<Integer>> convergeListCollection(List<List<Integer>> collections) {

		// ARRAY LIST is the DEFAULT choice
		return collections;
	}

	protected List<List<Integer>> disperseListCollection(List<List<Integer>> collections) {

		for (int i = 0; i < collections.size(); i++) {
			List<Integer> list = collections.get(i);

			if (i % 3 == 0) {
				// ARRAY -> Default - nothing needs to be done here
				continue;
			} else if (i % 3 == 1) {
				// LINKED -> MidList operations
				for (int j = 0; j < RuleBasedListOptimizer.MIDLIST_LINKED_THRESHOLD * 3; j++) {
					list.add(0, j);
				}

			} else {
				// HASH -> Contains
				for (int j = 0; j < RuleBasedListOptimizer.CONTAINS_HASH_THRESHOLD * 3; j++) {
					list.contains(j);
				}
			}

		}

		return collections;
	}

	@Override
	protected List<List<Integer>> createSmallConvergentCollections(List<List<Integer>> collections) {

		for (List<Integer> list : collections) {
			list.add(1);
			list.add(2);
		}
		return collections;
		
	}

	@Override
	protected List<List<Integer>> createSmallDivergentCollections(List<List<Integer>> collections) throws OperationNotSupportedException {
		throw new OperationNotSupportedException();
	}

	@Override
	protected List<List<Integer>> createLargeConvergentCollections(List<List<Integer>> collections) {

		for (List<Integer> list : collections) {
			list.addAll(fullList);
			list.add(1);
		}
		
		return collections;
	}

	@Override
	protected List<List<Integer>> createLargeDivergentCollections(List<List<Integer>> collections) {

		for (int i = 0; i < collections.size(); i++) {
			List<Integer> list = collections.get(i);
			// LARGE
			list.addAll(fullList);

			if (i % 3 == 0) {
				// ARRAY -> Default - nothing needs to be done here
				continue;
			} else if (i % 3 == 1) {
				// LINKED -> MidList operations
				for (int j = 0; j < RuleBasedListOptimizer.MIDLIST_LINKED_THRESHOLD * 3; j++) {
					list.add(0, j);
				}

			} else {
				// HASH -> Contains
				for (int j = 0; j < RuleBasedListOptimizer.CONTAINS_HASH_THRESHOLD * 3; j++) {
					list.contains(j);
				}
			}

		}
		
		return collections;
	}

}
