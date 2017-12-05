package de.heidelberg.pvs.diego.collectionswitch.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import de.heidelberg.pvs.diego.collectionswitch.optimizers.AllocationOptimizer;

public class SwitchManager {

	List<AllocationOptimizer> optimizers;

	private ScheduledExecutorService scheduler;

	public SwitchManager() {
		super();
		this.optimizers = new ArrayList<AllocationOptimizer>();

	}

	protected void analyzeAllOptimizers() {
		for (AllocationOptimizer optimizer : optimizers) {
			optimizer.analyzeAndOptimize();
		}

	}

	public void addOptimizer(AllocationOptimizer optimizer) {
		this.optimizers.add(optimizer);

	}

	public void configureAndScheduleManager(int threadsNumber, int initialDelay, int delay2) {

		// FIXME: Use a better singleton implementation
		if (scheduler == null) {
			// Schedule the Online Adapter Thread
			scheduler = Executors.newScheduledThreadPool(threadsNumber);
			scheduler.scheduleAtFixedRate(new Runnable() {
				@Override
				public void run() {
					analyzeAllOptimizers();
				}
			}, initialDelay, delay2, TimeUnit.MILLISECONDS);
		}

	}

	// Only for tests 
	// FIXME: Should be removed
	public void configureAndScheduleManagerOnce(int threadsNumber, int initialDelay) {

		// FIXME: Use a better singleton implementation
		if (scheduler == null) {
			// Schedule the Online Adapter Thread
			scheduler = Executors.newScheduledThreadPool(threadsNumber);
			scheduler.schedule(new Runnable() {
				@Override
				public void run() {
					analyzeAllOptimizers();
					scheduler.shutdown();
				}
			}, initialDelay, TimeUnit.MILLISECONDS);
		}

	}

}
