package de.heidelberg.pvs.diego.collections_online_adapter.factories;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import de.heidelberg.pvs.diego.collections_online_adapter.context.CollectionTypeEnum;
import de.heidelberg.pvs.diego.collections_online_adapter.context.ListAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.context.ListAllocationContextInfo;
import de.heidelberg.pvs.diego.collections_online_adapter.context.MapAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.context.MapAllocationContextInfo;
import de.heidelberg.pvs.diego.collections_online_adapter.context.SetAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.context.SetAllocationContextInfo;
import de.heidelberg.pvs.diego.collections_online_adapter.context.impl.InitialCapacityListAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.context.impl.InitialCapacityMapAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.context.impl.InitialCapacitySetAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.context.impl.LogListAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.context.impl.LogMapAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.context.impl.LogSetAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.lists.ListActiveOptimizer;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.lists.ListAllocationOptimizer;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.maps.MapActiveOptimizer;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.maps.MapAllocationOptimizer;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.sets.SetActiveOptimizer;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.sets.SetAllocationOptimizer;

public class AllocationContextFactory {

	private static final double FINISHED_RATIO = 1;
	private static final int SAMPLES = 50;
	private static final int WINDOW_SIZE = 10;
	private static final int DELAY = 1000;
	private static final int INITIAL_DELAY = 1000;

	private static ScheduledExecutorService scheduler;

	public static class AllocationContextBuilder {

		private final CollectionTypeEnum type;
		private final String identifier;

		// Default: PASSIVE
		private AllocationContextAlgorithm algorithm = AllocationContextAlgorithm.INITIAL_CAPACITY;

		private boolean hasLog;
		private String logFile;

		private int windowSize = WINDOW_SIZE;
		private int samples = SAMPLES;
		private int initialDelay = INITIAL_DELAY;
		private int delay = DELAY;

		public enum AllocationContextAlgorithm {
			INITIAL_CAPACITY;
		}

		public AllocationContextBuilder(CollectionTypeEnum type, String identifier) {
			super();
			this.type = type;
			this.identifier = identifier;
		}

		public AllocationContextBuilder withAlgorithm(AllocationContextAlgorithm algorithm) {
			this.algorithm = algorithm;
			return this;
		}

		public AllocationContextBuilder withLog(String logFile) {
			this.hasLog = true;
			this.logFile = logFile;
			return this;
		}

		public AllocationContextBuilder windowSize(int windowSize) {
			this.windowSize = windowSize;
			return this;
		}

		public AllocationContextBuilder samples(int samples) {
			this.samples = samples;
			return this;
		}

		public AllocationContextBuilder withInitialDelay(int parseInt) {
			this.initialDelay = parseInt;
			return this;

		}

		public AllocationContextBuilder withDelay(int parseInt) {
			this.delay = parseInt;
			return this;

		}

	}

	/*
	 * LISTS
	 */
	public static ListAllocationContext buildListContext(CollectionTypeEnum type, String identifier) {

		// Parse command line
		AllocationContextBuilder builder = parseCommandLine(type, identifier);

		return buildListContext(builder);
	}

	private static ListAllocationContext buildListContext(AllocationContextBuilder builder) {

		final ListAllocationOptimizer optimizer;
		ListAllocationContextInfo context = null;

		// Build the optimizer
		switch (builder.algorithm) {

		case INITIAL_CAPACITY:
		default:
			// TODO: Put this into the Switch Thread Manager
			optimizer = new ListActiveOptimizer(builder.windowSize, FINISHED_RATIO);

			// Schedule the Online Adapter Thread
			scheduler = Executors.newScheduledThreadPool(1);
			scheduler.scheduleAtFixedRate(new Runnable() {
				@Override
				public void run() {
					((ListActiveOptimizer) optimizer).analyzeAndOptimize();
				}
			}, builder.initialDelay, builder.delay, TimeUnit.MILLISECONDS);

			context = new InitialCapacityListAllocationContext(optimizer, builder.windowSize, builder.samples);
			break;

		}

		// Print the log of the changes
		if (builder.hasLog) {
			ListAllocationContext logContext = new LogListAllocationContext(context, builder.identifier,
					builder.logFile);
			optimizer.setContext(logContext);
			return logContext;

		} else {
			optimizer.setContext(context);
		}

		return context;
	}

	/*
	 * ------------------------------- SETS -------------------------------
	 */
	public static <E> SetAllocationContext buildSetContext(CollectionTypeEnum type, String identifier) {

		// Parse command line
		AllocationContextBuilder builder = parseCommandLine(type, identifier);

		return buildSetContext(builder);

	}

	public static <E> SetAllocationContext buildSetContext(AllocationContextBuilder builder) {

		final SetAllocationOptimizer optimizer;
		SetAllocationContextInfo context = null;

		// Build the optimizer
		switch (builder.algorithm) {

		case INITIAL_CAPACITY:
		default:
			// TODO: Put this into the Switch Thread Manager
			optimizer = new SetActiveOptimizer(builder.windowSize, FINISHED_RATIO);

			// Schedule the Online Adapter Thread
			scheduler = Executors.newScheduledThreadPool(1);
			scheduler.scheduleAtFixedRate(new Runnable() {
				@Override
				public void run() {
					((SetActiveOptimizer) optimizer).analyzeAndOptimize();
				}
			}, builder.initialDelay, builder.delay, TimeUnit.MILLISECONDS);

			context = new InitialCapacitySetAllocationContext(optimizer, builder.windowSize);
			break;

		}

		// Print the log of the changes
		if (builder.hasLog) {
			SetAllocationContext logContext = new LogSetAllocationContext(context, builder.identifier, builder.logFile);
			optimizer.setContext(logContext);
			return logContext;

		} else {
			optimizer.setContext(context);
		}

		return context;

	}

	/*
	 * ------------------------------- MAPS -------------------------------
	 */
	public static <K, V> MapAllocationContext buildMapContext(CollectionTypeEnum type, String identifier) {
		// Parse command line
		AllocationContextBuilder builder = parseCommandLine(type, identifier);

		return buildMapContext(builder);

	}

	public static MapAllocationContext buildMapContext(AllocationContextBuilder builder) {

		// Build the context + optimizer
		final MapAllocationOptimizer optimizer;

		// Build the optimizer
		switch (builder.algorithm) {

		case INITIAL_CAPACITY:
		default:
			optimizer = new MapActiveOptimizer(builder.windowSize, FINISHED_RATIO);

			// Schedule the Online Adapter Thread
			scheduler = Executors.newScheduledThreadPool(1);
			scheduler.scheduleAtFixedRate(new Runnable() {
				@Override
				public void run() {
					((MapActiveOptimizer) optimizer).analyzeAndOptimizeContext();
				}
			}, builder.initialDelay, builder.delay, TimeUnit.MILLISECONDS);

			break;
		}

		MapAllocationContextInfo context = new InitialCapacityMapAllocationContext(optimizer, builder.samples);

		// Print the log of the changes
		if (builder.hasLog) {
			MapAllocationContext logContext = new LogMapAllocationContext(context, builder.identifier, builder.logFile);
			optimizer.setContext(logContext);
			return logContext;

		}

		return context;
	}

	/*
	 * COMMAND LINE
	 */
	public static AllocationContextBuilder parseCommandLine(CollectionTypeEnum type, String identifier) {

		AllocationContextBuilder builder = new AllocationContextBuilder(type, identifier);

		String windowSizeStr = System.getProperty("windowSize");
		if (windowSizeStr != null) {
			builder.windowSize(Integer.parseInt(windowSizeStr));
		}

		String sampleStr = System.getProperty("samples");
		if (sampleStr != null) {
			builder.samples(Integer.parseInt(sampleStr));
		}

		String logFile = System.getProperty("log");
		if (logFile != null) {
			builder.withLog(logFile);
		}

		logFile = System.getProperty("logOutput");
		if (logFile != null) {
			builder.withLog(logFile);
		}

		String initialDelay = System.getProperty("initialDelay");
		if (initialDelay != null) {
			builder.withInitialDelay(Integer.parseInt(initialDelay));
		}

		String delay = System.getProperty("delay");
		if (delay != null) {
			builder.withDelay(Integer.parseInt(delay));
		}

		return builder;

	}

}
