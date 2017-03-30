package de.heidelberg.pvs.diego.collections_online_adapter.automata;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.heidelberg.pvs.diego.collections_online_adapter.context.AllocationContextState;
import de.heidelberg.pvs.diego.collections_online_adapter.context.impl.AbstractAdaptiveAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.context.impl.AdaptiveListAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.factories.AllocationContextFactory;

public abstract class AbstractAutomataStateTest<K> {

	protected abstract AbstractAdaptiveAllocationContext buildContext();

	protected abstract K createCollection(AbstractAdaptiveAllocationContext context);

	// SIZE < ALPHA && CONVERGENCE > BETA
	protected abstract List<K> createSmallConvergentCollections(List<K> collections);

	// SIZE < ALPHA && CONVERGENCE < BETA
	protected abstract List<K> createSmallDivergentCollections(List<K> collections);

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

		List<K> collections = new ArrayList<>();
		for (int i = 0; i < AllocationContextFactory.WINDOW_SIZE; i++) {
			collections.add(createCollection(context));
		}

		// SIZE > ALPHA and CONVERGENVE > BETA
		collections = this.createLargeConvergentCollections(collections);
		collections = null;

		System.gc();
		Thread.sleep(10);

		Assert.assertEquals(AllocationContextState.ACTIVE_FULL, context.getAllocationContextState());
	}

	// 1. B) ACTIVE_MEMORY -> ACTIVE_FULL (DIVERGENT)
	@Test
	public void activeMemoryToActiveFull_Divergent() throws Exception {

		context.setAllocationContextState(AllocationContextState.ACTIVE_MEMORY);

		List<K> collections = new ArrayList<>();
		for (int i = 0; i < AllocationContextFactory.WINDOW_SIZE; i++) {
			collections.add(createCollection(context));
		}

		// SIZE > ALPHA and CONVERGENCE > BETA
		collections = this.createLargeDivergentCollections(collections);
		collections = null;

		System.gc();
		Thread.sleep(10);

		Assert.assertEquals(AllocationContextState.ACTIVE_FULL, context.getAllocationContextState());
	}

	// 2A ATIVE_FULL -> ATIVE_MEMORY
	@Test
	public void activeFullToActiveMemory_Convergent() throws Exception {

		context.setAllocationContextState(AllocationContextState.ACTIVE_FULL);

		List<K> collections = new ArrayList<>();
		for (int i = 0; i < AllocationContextFactory.WINDOW_SIZE; i++) {
			collections.add(createCollection(context));
		}

		// SIZE < ALPHA
		collections = this.createSmallConvergentCollections(collections);
		collections = null;

		System.gc();
		Thread.sleep(10);

		Assert.assertEquals(AllocationContextState.ACTIVE_MEMORY, context.getAllocationContextState());
	}

	// 2B ATIVE_FULL -> ATIVE_MEMORY
	@Test
	public void activeFullToActiveMemory_Divergent() throws Exception {

		context.setAllocationContextState(AllocationContextState.ACTIVE_FULL);

		List<K> collections = new ArrayList<>();
		for (int i = 0; i < AllocationContextFactory.WINDOW_SIZE; i++) {
			collections.add(createCollection(context));
		}

		// SIZE < ALPHA
		collections = this.createSmallDivergentCollections(collections);
		collections = null;

		System.gc();
		Thread.sleep(10);

		Assert.assertEquals(AllocationContextState.ACTIVE_MEMORY, context.getAllocationContextState());
	}

	// 3. ACTIVE_MEMORY -> SLEEPING MEMORY
	@Test
	public void activeMemoryToSleepingMemoryTest() throws Exception {

		context.setAllocationContextState(AllocationContextState.ACTIVE_MEMORY);

		List<K> collections = new ArrayList<>();
		for (int i = 0; i < AllocationContextFactory.WINDOW_SIZE; i++) {
			collections.add(createCollection(context));
		}

		// SIZE < ALPHA
		collections = this.createSmallConvergentCollections(collections);
		collections = null;

		System.gc();
		Thread.sleep(10);

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

		List<K> collections = new ArrayList<>();
		for (int i = 0; i < AllocationContextFactory.WINDOW_SIZE; i++) {
			collections.add(createCollection(context));
		}

		// SIZE > ALPHA ALPHA
		// CONVERGENCE > BETA
		collections = this.createLargeConvergentCollections(collections);

		// Dispose
		collections = null;
		System.gc();
		Thread.sleep(10);

		Assert.assertEquals(AllocationContextState.SLEEPING_FULL, context.getAllocationContextState());

	}

	// 7. SLEEPING_FULL -> ACTIVE_FULL
	@Test
	public void sleepingFullToActiveFullTest() throws Exception {

		context.setAllocationContextState(AllocationContextState.SLEEPING_FULL);

		List<K> collections = new ArrayList<>();
		for (int i = 0; i < AllocationContextFactory.WINDOW_SIZE * AllocationContextFactory.SLEEPING_FREQUENCY + 1; i++) {
			collections.add(createCollection(context));
		}

		// SIZE > ALPHA ALPHA
		// CONVERGENCE < BETA
		collections = this.createLargeDivergentCollections(collections);

		// Dispose
		collections = null;
		System.gc();
		Thread.sleep(10);

		Assert.assertEquals(AllocationContextState.ACTIVE_FULL, context.getAllocationContextState());

	}

	// 8. ACTIVE_FULL -> ACTIVE_FULL
	@Test
	public void activeFullToActiveFullTest() throws Exception {

		context.setAllocationContextState(AllocationContextState.ACTIVE_FULL);

		List<K> collections = new ArrayList<>();
		for (int i = 0; i < AllocationContextFactory.WINDOW_SIZE * 3; i++) {
			collections.add(createCollection(context));
		}

		// SIZE > ALPHA ALPHA
		// CONVERGENCE < BETA
		collections = this.createLargeDivergentCollections(collections);

		// Dispose
		collections = null;
		System.gc();
		Thread.sleep(10);

		Assert.assertEquals(AllocationContextState.ACTIVE_FULL, context.getAllocationContextState());

	}

	// 9. SLEEPING_MEMORY -> ACTIVE_FULL
	@Test
	public void sleepingMemoryToActiveFull_Convergent() throws Exception {

		context.setAllocationContextState(AllocationContextState.SLEEPING_MEMORY);

		List<K> collections = new ArrayList<>();
		for (int i = 0; i < AllocationContextFactory.WINDOW_SIZE * AllocationContextFactory.SLEEPING_FREQUENCY; i++) {
			collections.add(createCollection(context));
		}

		// SIZE > ALPHA ALPHA
		// CONVERGENCE > BETA
		collections = this.createLargeConvergentCollections(collections);

		// Dispose
		collections = null;
		System.gc();
		Thread.sleep(10);

		Assert.assertEquals(AllocationContextState.ACTIVE_FULL, context.getAllocationContextState());

	}

	@Test
	public void sleepingMemoryToActiveFull_Divergent() throws Exception {

		context.setAllocationContextState(AllocationContextState.SLEEPING_MEMORY);

		List<K> collections = new ArrayList<>();
		for (int i = 0; i < AllocationContextFactory.WINDOW_SIZE * AllocationContextFactory.SLEEPING_FREQUENCY
				+ 1; i++) {
			collections.add(createCollection(context));
		}

		// SIZE > ALPHA
		// CONVERGENCE < BETA
		collections = this.createLargeDivergentCollections(collections);

		// Dispose
		collections = null;
		System.gc();
		Thread.sleep(10);

		Assert.assertEquals(AllocationContextState.ACTIVE_FULL, context.getAllocationContextState());

	}

	// 10. SLEEPING_FULL -> ACTIVE_MEMORY
	@Test
	public void sleepingFullToActiveMemory_Convergent() throws Exception {

		context.setAllocationContextState(AllocationContextState.SLEEPING_FULL);

		List<K> collections = new ArrayList<>();
		for (int i = 0; i < AllocationContextFactory.WINDOW_SIZE * AllocationContextFactory.SLEEPING_FREQUENCY
				+ 1; i++) {
			collections.add(createCollection(context));
		}

		// SIZE < ALPHA
		// CONVERGENCE > BETA
		collections = this.createSmallConvergentCollections(collections);

		// Dispose
		collections = null;
		System.gc();
		Thread.sleep(10);

		Assert.assertEquals(AllocationContextState.ACTIVE_MEMORY, context.getAllocationContextState());

	}

	// 10. SLEEPING_FULL -> ACTIVE_MEMORY
	@Test
	public void sleepingFullToActiveMemoryTest() throws Exception {
		
		context.setAllocationContextState(AllocationContextState.SLEEPING_FULL);

		List<K> collections = new ArrayList<>();
		for (int i = 0; i < AllocationContextFactory.WINDOW_SIZE * AllocationContextFactory.SLEEPING_FREQUENCY
				+ 1; i++) {
			collections.add(createCollection(context));
		}

		// SIZE < ALPHA ALPHA
		// CONVERGENCE < BETA
		collections = this.createSmallDivergentCollections(collections);

		// Dispose
		collections = null;
		System.gc();
		Thread.sleep(10);

		Assert.assertEquals(AllocationContextState.ACTIVE_MEMORY, context.getAllocationContextState());

	}

}
