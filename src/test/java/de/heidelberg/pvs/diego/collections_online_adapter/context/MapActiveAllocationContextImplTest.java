package de.heidelberg.pvs.diego.collections_online_adapter.context;

import static org.junit.Assert.assertNotNull;

import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.heidelberg.pvs.diego.collections_online_adapter.context.impl.MapAllocationContextImpl;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.maps.MapActiveOptimizer;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.maps.MapAllocationOptimizer;
import jlibs.core.lang.RuntimeUtil;

public class MapActiveAllocationContextImplTest {

	private static final int SAMPLE = 1;
	private static final int WINDOW_SIZE = 10;
	private MapActiveOptimizer optimizer;
	private MapAllocationContextImpl context;
	private ScheduledExecutorService scheduler;

	@Before
	public void setup() {
		optimizer = new MapActiveOptimizer(WINDOW_SIZE);
		assertNotNull(optimizer);

		context = new MapAllocationContextImpl(optimizer, SAMPLE);
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

		MapAllocationOptimizer optimizer = new MapActiveOptimizer(WINDOW_SIZE);
		assertNotNull(optimizer);

		MapAllocationContext context = new MapAllocationContextImpl(optimizer);
		assertNotNull(context);

		optimizer.setContext(context);

		Map<Object, Object> createMap = context.createMap();
		assertNotNull(createMap);

	}

	@Test
	public void testWarmupToAdaptive() throws Exception {

		int n = 7;
		for (int i = 0; i < WINDOW_SIZE * SAMPLE; i++) {

			Map<Integer, Integer> map = context.createMap();

			for (int j = 0; j < n; j++) {
				map.put(j, j);
			}

			map = null;
		}

		RuntimeUtil.gc();
		RuntimeUtil.gc();
		Thread.sleep(1000);

		AllocationContextState state = context.getAllocationContextState();
		Assert.assertEquals(AllocationContextState.ADAPTIVE, state);

		int analyzedSize = context.getAnalyzedSize();
		Assert.assertEquals(n, analyzedSize);

	}

}
