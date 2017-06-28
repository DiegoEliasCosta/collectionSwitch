package de.heidelberg.pvs.diego.collections_online_adapter.context;

import static org.junit.Assert.assertNotNull;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.heidelberg.pvs.diego.collections_online_adapter.context.impl.MapAllocationContextImpl;
import de.heidelberg.pvs.diego.collections_online_adapter.context.impl.SetAllocationContextImpl;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.AllocationOptimizer;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.PassiveOptimizer;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.sets.SetActiveOptimizer;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.sets.SetAllocationOptimizer;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.sets.SetPassiveOptimizer;
import jlibs.core.lang.RuntimeUtil;

public class SetActiveAllocationContextImplTest {
	
	private static final int SAMPLE = 1;
	private static final int WINDOW_SIZE = 10;
	private SetActiveOptimizer optimizer;
	private SetAllocationContextImpl context;
	
	private static ScheduledExecutorService scheduler;

	@Before
	public void setup() {
		
		optimizer = new SetActiveOptimizer(WINDOW_SIZE);
		assertNotNull(optimizer);
		
		context = new SetAllocationContextImpl(optimizer, SAMPLE);
		assertNotNull(context);
		
		optimizer.setContext(context);
		
		scheduler = Executors.newScheduledThreadPool(1);
		
		scheduler.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				optimizer.checkFinalizedAnalysis();
			}
		}, 0, 100, TimeUnit.MILLISECONDS);

		
	}
	
	
	@Test
	public void sanityTest() throws Exception {

		// Optimizer
		SetAllocationOptimizer optimizer = new SetActiveOptimizer(10);
		
		// Context
		SetAllocationContext context = new SetAllocationContextImpl(optimizer);
		Assert.assertNotNull(context);
		
		optimizer.setContext(context);
		
		Set<Integer> set = context.createSet();
		Assert.assertNotNull(set);
		
	}
	
	@Test
	public void testPassiveWarmupToAdaptive() throws Exception {
		
		int n = 7;
		for(int i = 0; i < WINDOW_SIZE * SAMPLE; i++) {
			
			Set<Integer> set = context.createSet();
			
			for(int j = 0; j < n; j++) {
				set.add(j);
			}
			
			set = null;
		}
		
		RuntimeUtil.gc();
		Thread.sleep(400);
		
		AllocationContextState state = context.getAllocationContextState();
		Assert.assertEquals(AllocationContextState.ADAPTIVE, state);

		int analyzedSize = context.getAnalyzedSize();
		Assert.assertEquals(n, analyzedSize);
		
	}
	

}
