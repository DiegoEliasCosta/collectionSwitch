package de.heidelberg.pvs.diego.collections_online_adapter.automata;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.heidelberg.pvs.diego.collections_online_adapter.context.AllocationContextState;
import de.heidelberg.pvs.diego.collections_online_adapter.context.impl.AbstractAdaptiveAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.factories.AllocationContextFactory;
import jlibs.core.lang.RuntimeUtil;

public abstract class AbstractAutomataStateTest<K> {

	private static final int SLEEPING_TIME = 200;

	protected abstract AbstractAdaptiveAllocationContext buildContext();

	protected abstract K createCollection(AbstractAdaptiveAllocationContext context);

	// SIZE < ALPHA && CONVERGENCE > BETA
	protected abstract List<K> createSmallConvergentCollections(List<K> collections);

	// SIZE < ALPHA && CONVERGENCE < BETA
	protected abstract List<K> createSmallDivergentCollections(List<K> collections)
			throws OperationNotSupportedException;

	// SIZE > ALPHA && CONVERGENCE > BETA
	protected abstract List<K> createLargeConvergentCollections(List<K> collections);

	// SIZE > ALPHA && CONVERGENCE < BETA
	protected abstract List<K> createLargeDivergentCollections(List<K> collections);

	protected abstract void specificSetup();

	AbstractAdaptiveAllocationContext context;

	@Before
	public void setup() {
		context = buildContext();
		specificSetup();
	}

	// 1. A) ACTIVE_MEMORY -> ACTIVE_FULL (CONVERGENT)
	@Test
	public void activeMemoryToActiveFull_Convergent() throws Exception {

		context.setAllocationContextState(AllocationContextState.ACTIVE_MEMORY);

		List<K> collections = new ArrayList();
		for (int i = 0; i < AllocationContextFactory.WINDOW_SIZE; i++) {
			collections.add(createCollection(context));
		}

		// SIZE > ALPHA and CONVERGENVE > BETA
		collections = this.createLargeConvergentCollections(collections);

		collections = null;
		this.disposeCollections(collections);

		// Triggers the optimization
		createCollection(context);

		Assert.assertEquals(AllocationContextState.ACTIVE_FULL, context.getAllocationContextState());
	}

	// 1. B) ACTIVE_MEMORY -> ACTIVE_FULL (DIVERGENT)
	@Test
	public void activeMemoryToActiveFull_Divergent() throws Exception {

		context.setAllocationContextState(AllocationContextState.ACTIVE_MEMORY);

		List<K> collections = new ArrayList();
		for (int i = 0; i < AllocationContextFactory.WINDOW_SIZE; i++) {
			collections.add(createCollection(context));
		}

		// SIZE > ALPHA and CONVERGENCE > BETA
		collections = this.createLargeDivergentCollections(collections);
		collections = null;

		this.disposeCollections(collections);

		// Triggers the optimization
		createCollection(context);

		Assert.assertEquals(AllocationContextState.ACTIVE_FULL, context.getAllocationContextState());
	}

	// 2A ATIVE_FULL -> ATIVE_MEMORY
	@Test
	public void activeFullToActiveMemory_Convergent() throws Exception {

		context.setAllocationContextState(AllocationContextState.ACTIVE_FULL);

		List<K> collections = new ArrayList();
		for (int i = 0; i < AllocationContextFactory.WINDOW_SIZE; i++) {
			collections.add(createCollection(context));
		}

		// SIZE < ALPHA
		collections = this.createSmallConvergentCollections(collections);

		collections = null;
		this.disposeCollections(collections);

		// Triggers the optimization
		createCollection(context);

		Assert.assertEquals(AllocationContextState.ACTIVE_MEMORY, context.getAllocationContextState());
	}

	// 2B ATIVE_FULL -> ATIVE_MEMORY
	@Test
	public void activeFullToActiveMemory_Divergent() throws Exception {

		context.setAllocationContextState(AllocationContextState.ACTIVE_FULL);

		List<K> collections = new ArrayList();
		for (int i = 0; i < AllocationContextFactory.WINDOW_SIZE; i++) {
			collections.add(createCollection(context));
		}

		try {
			// SIZE < ALPHA
			collections = this.createSmallDivergentCollections(collections);

			collections = null;
			this.disposeCollections(collections);

			Assert.assertEquals(AllocationContextState.ACTIVE_MEMORY, context.getAllocationContextState());

		} catch (OperationNotSupportedException e) {
			assertTrue(true);

		}
	}

	// 3. ACTIVE_MEMORY -> SLEEPING MEMORY
	@Test
	public void activeMemoryToSleepingMemoryTest() throws Exception {

		context.setAllocationContextState(AllocationContextState.ACTIVE_MEMORY);

		List<K> collections = new ArrayList();
		for (int i = 0; i < AllocationContextFactory.WINDOW_SIZE; i++) {
			collections.add(createCollection(context));
		}

		// SIZE < ALPHA
		collections = this.createSmallConvergentCollections(collections);

		collections = null;
		this.disposeCollections(collections);

		// Triggers the optimization
		createCollection(context);

		Assert.assertEquals(AllocationContextState.SLEEPING_MEMORY, context.getAllocationContextState());

	}

	// 4. SLEEPING_MEMORY -> ACTIVE_MEMORY
	// TODO: Implement this when this becomes possible for every single
	// collection (NOT POSSIBLE FOR LISTS)

	// 5. ACTIVE_MEMORY -> ACTIVE_MEMORY

	// 6. ACTIVE_FULL -> SLEEPING_FULL
	@Test
	public void activeFullToSleepingFullTest() throws Exception {

		context.setAllocationContextState(AllocationContextState.ACTIVE_FULL);

		List<K> collections = new ArrayList();
		for (int i = 0; i < AllocationContextFactory.WINDOW_SIZE; i++) {
			collections.add(createCollection(context));
		}

		// SIZE > ALPHA ALPHA
		// CONVERGENCE > BETA
		collections = this.createLargeConvergentCollections(collections);

		// Dispose
		collections = null;
		this.disposeCollections(collections);

		// Triggers the optimization
		createCollection(context);

		Assert.assertEquals(AllocationContextState.SLEEPING_FULL, context.getAllocationContextState());

	}

	// 7. SLEEPING_FULL -> ACTIVE_FULL
	@Test
	public void sleepingFullToActiveFullTest() throws Exception {

		context.setAllocationContextState(AllocationContextState.SLEEPING_FULL);

		List<K> collections = new ArrayList();
		for (int i = 0; i < AllocationContextFactory.WINDOW_SIZE * AllocationContextFactory.SLEEPING_FREQUENCY; i++) {
			collections.add(createCollection(context));
		}

		// SIZE > ALPHA ALPHA
		// CONVERGENCE < BETA
		collections = this.createLargeDivergentCollections(collections);

		// Dispose
		collections = null;
		this.disposeCollections(collections);

		// Triggers the optimization
		createCollection(context);

		Assert.assertEquals(AllocationContextState.ACTIVE_FULL, context.getAllocationContextState());

	}

	private void disposeCollections(List<K> collections) throws InterruptedException {
		collections = null;
		System.gc();
		RuntimeUtil.gc();
		Thread.sleep(SLEEPING_TIME);

	}

	// 8. ACTIVE_FULL -> ACTIVE_FULL
	@Test
	public void activeFullToActiveFullTest() throws Exception {

		context.setAllocationContextState(AllocationContextState.ACTIVE_FULL);

		List<K> collections = new ArrayList();
		for (int i = 0; i < AllocationContextFactory.WINDOW_SIZE; i++) {
			collections.add(createCollection(context));
		}

		// SIZE > ALPHA ALPHA
		// CONVERGENCE < BETA
		collections = this.createLargeDivergentCollections(collections);

		// Dispose
		collections = null;
		this.disposeCollections(collections);

		// Triggers the optimization
		createCollection(context);

		Assert.assertEquals(AllocationContextState.ACTIVE_FULL, context.getAllocationContextState());

	}

	// 9. SLEEPING_MEMORY -> ACTIVE_FULL
	@Test
	public void sleepingMemoryToActiveFull_Convergent() throws Exception {

		context.setAllocationContextState(AllocationContextState.SLEEPING_MEMORY);

		List<K> collections = new ArrayList();
		int sleepAmount = AllocationContextFactory.WINDOW_SIZE * AllocationContextFactory.SLEEPING_FREQUENCY;
		for (int i = 0; i < sleepAmount; i++) {
			collections.add(createCollection(context));
		}

		// SIZE > ALPHA ALPHA
		// CONVERGENCE > BETA
		collections = this.createLargeConvergentCollections(collections);

		// Dispose
		collections = null;
		this.disposeCollections(collections);

		// Triggers the optimization
		//createCollection(context);

		Assert.assertEquals(AllocationContextState.ACTIVE_FULL, context.getAllocationContextState());

	}

	@Test
	public void sleepingMemoryToActiveFull_Divergent() throws Exception {

		context.setAllocationContextState(AllocationContextState.SLEEPING_MEMORY);

		List<K> collections = new ArrayList();
		for (int i = 0; i < AllocationContextFactory.WINDOW_SIZE * AllocationContextFactory.SLEEPING_FREQUENCY; i++) {
			collections.add(createCollection(context));
		}

		// SIZE > ALPHA
		// CONVERGENCE < BETA
		collections = this.createLargeDivergentCollections(collections);

		// Dispose
		collections = null;
		this.disposeCollections(collections);

		// Triggers the optimization
		createCollection(context);

		Assert.assertEquals(AllocationContextState.ACTIVE_FULL, context.getAllocationContextState());

	}

	// 10. SLEEPING_FULL -> ACTIVE_MEMORY
	@Test
	public void sleepingFullToActiveMemory_Convergent() throws Exception {

		context.setAllocationContextState(AllocationContextState.SLEEPING_FULL);

		List<K> collections = new ArrayList();
		for (int i = 0; i < AllocationContextFactory.WINDOW_SIZE * AllocationContextFactory.SLEEPING_FREQUENCY
				+ 1; i++) {
			collections.add(createCollection(context));
		}

		// SIZE < ALPHA
		// CONVERGENCE > BETA
		collections = this.createSmallConvergentCollections(collections);

		// Dispose
		collections = null;
		this.disposeCollections(collections);

		Assert.assertEquals(AllocationContextState.ACTIVE_MEMORY, context.getAllocationContextState());

	}

	// 10. SLEEPING_FULL -> ACTIVE_MEMORY
	@Test
	public void sleepingFullToActiveMemoryTest() throws Exception {

		context.setAllocationContextState(AllocationContextState.SLEEPING_FULL);

		List<K> collections = new ArrayList();
		for (int i = 0; i < AllocationContextFactory.WINDOW_SIZE * AllocationContextFactory.SLEEPING_FREQUENCY
				+ 1; i++) {
			collections.add(createCollection(context));
		}

		// SIZE < ALPHA ALPHA
		// CONVERGENCE < BETA
		try {
			collections = this.createSmallDivergentCollections(collections);

			// Dispose
			collections = null;
			this.disposeCollections(collections);

			Assert.assertEquals(AllocationContextState.ACTIVE_MEMORY, context.getAllocationContextState());

		} catch (OperationNotSupportedException e) {
			assertTrue(true);
		}

	}

	// 11. INACTIVE -> INACTIVE (A)
	@Test
	public void inactiveToInactive_SmallConvergent() throws Exception {

		context.setAllocationContextState(AllocationContextState.INACTIVE);

		List<K> collections = new ArrayList();
		for (int i = 0; i < AllocationContextFactory.WINDOW_SIZE; i++) {
			collections.add(createCollection(context));
		}

		// SIZE < ALPHA ALPHA
		// CONVERGENCE < BETA
		collections = this.createSmallConvergentCollections(collections);

		// Dispose
		collections = null;
		this.disposeCollections(collections);

		Assert.assertEquals(AllocationContextState.INACTIVE, context.getAllocationContextState());

	}

	// 11. INACTIVE -> INACTIVE (B)
	@Test
	public void inactiveToInactive_SmallDivergent() throws Exception {

		context.setAllocationContextState(AllocationContextState.INACTIVE);

		List<K> collections = new ArrayList();
		for (int i = 0; i < AllocationContextFactory.WINDOW_SIZE; i++) {
			collections.add(createCollection(context));
		}

		// SIZE < ALPHA ALPHA
		// CONVERGENCE < BETA
		try {

			collections = this.createSmallDivergentCollections(collections);

			// Dispose
			collections = null;
			this.disposeCollections(collections);

			Assert.assertEquals(AllocationContextState.INACTIVE, context.getAllocationContextState());

		} catch (OperationNotSupportedException e) {
			assertTrue(true);

		}

	}

	// 11. INACTIVE -> INACTIVE (C)
	@Test
	public void inactiveToInactive_LargeConvergent() throws Exception {

		context.setAllocationContextState(AllocationContextState.INACTIVE);

		List<K> collections = new ArrayList();
		for (int i = 0; i < AllocationContextFactory.WINDOW_SIZE; i++) {
			collections.add(createCollection(context));
		}

		// SIZE < ALPHA ALPHA
		// CONVERGENCE < BETA
		collections = this.createLargeConvergentCollections(collections);

		// Dispose
		collections = null;
		this.disposeCollections(collections);

		Assert.assertEquals(AllocationContextState.INACTIVE, context.getAllocationContextState());

	}

	// 11. INACTIVE -> INACTIVE (D)
	@Test
	public void inactiveToInactive_LargeDivergent() throws Exception {

		context.setAllocationContextState(AllocationContextState.INACTIVE);

		List<K> collections = new ArrayList();
		for (int i = 0; i < AllocationContextFactory.WINDOW_SIZE; i++) {
			collections.add(createCollection(context));
		}

		// SIZE < ALPHA ALPHA
		// CONVERGENCE < BETA
		collections = this.createLargeDivergentCollections(collections);

		// Dispose
		collections = null;
		this.disposeCollections(collections);

		Assert.assertEquals(AllocationContextState.INACTIVE, context.getAllocationContextState());
	}

	// 12. ACTIVE_FULL -> INACTIVE
	@Test
	public void activeFullToInactive() throws Exception {

		context.setAllocationContextState(AllocationContextState.ACTIVE_FULL);

		for (int j = 0; j < AllocationContextFactory.DIVERGENCE_ROUNDS_THRESHOLD; j++) {
			List<K> collections = new ArrayList();

			for (int i = 0; i < AllocationContextFactory.WINDOW_SIZE; i++) {
				collections.add(createCollection(context));
			}

			// SIZE < ALPHA ALPHA
			// CONVERGENCE < BETA
			collections = this.createLargeDivergentCollections(collections);

			// Dispose
			collections = null;
			this.disposeCollections(collections);

			// Triggers the optimization
			createCollection(context);
		}

		Assert.assertEquals(AllocationContextState.INACTIVE, context.getAllocationContextState());
	}

	// 13. ACTIVE_MEMORY -> INACTiVE
	@Test
	public void activeMemoryToInactive() throws Exception {

		try {
			context.setAllocationContextState(AllocationContextState.ACTIVE_MEMORY);

			for (int j = 0; j < AllocationContextFactory.DIVERGENCE_ROUNDS_THRESHOLD; j++) {

				List<K> collections = new ArrayList();
				for (int i = 0; i < AllocationContextFactory.WINDOW_SIZE; i++) {
					collections.add(createCollection(context));
				}

				// SIZE < ALPHA ALPHA
				// CONVERGENCE < BETA
				collections = this.createSmallDivergentCollections(collections);

				// Dispose
				collections = null;
				this.disposeCollections(collections);
			}

			Assert.assertEquals(AllocationContextState.INACTIVE, context.getAllocationContextState());

		} catch (OperationNotSupportedException e) {
			assertTrue(true);

		}

	}

	// 14. SLEEPING_MEMORY -> SLEEPING_MEMORY
	@Test
	public void sleepingMemoryToSleepingMemory() throws Exception {

		context.setAllocationContextState(AllocationContextState.SLEEPING_MEMORY);

		for (int j = 0; j < AllocationContextFactory.DIVERGENCE_ROUNDS_THRESHOLD; j++) {

			List<K> collections = new ArrayList();
			for (int i = 0; i < AllocationContextFactory.WINDOW_SIZE; i++) {
				collections.add(createCollection(context));
			}

			// SIZE < ALPHA ALPHA
			// CONVERGENCE < BETA
			collections = this.createSmallConvergentCollections(collections);

			// Dispose
			collections = null;
			this.disposeCollections(collections);
		}

		Assert.assertEquals(AllocationContextState.SLEEPING_MEMORY, context.getAllocationContextState());

	}

	// 13. ACTIVE_MEMORY -> INACTiVE
	@Test
	public void sleepingFullToSleepingFull() throws Exception {

		context.setAllocationContextState(AllocationContextState.SLEEPING_FULL);

		for (int j = 0; j < AllocationContextFactory.DIVERGENCE_ROUNDS_THRESHOLD; j++) {

			List<K> collections = new ArrayList();
			for (int i = 0; i < AllocationContextFactory.WINDOW_SIZE; i++) {
				collections.add(createCollection(context));
			}

			// SIZE < ALPHA ALPHA
			// CONVERGENCE < BETA
			collections = this.createLargeConvergentCollections(collections);

			// Dispose
			collections = null;
			this.disposeCollections(collections);
			

		}

		// Triggers the optimization
		createCollection(context);
		Assert.assertEquals(AllocationContextState.SLEEPING_FULL, context.getAllocationContextState());

	}

}
