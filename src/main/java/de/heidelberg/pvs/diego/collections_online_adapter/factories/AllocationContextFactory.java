package de.heidelberg.pvs.diego.collections_online_adapter.factories;

import java.util.concurrent.ScheduledExecutorService;

import de.heidelberg.pvs.diego.collections_online_adapter.context.CollectionTypeEnum;
import de.heidelberg.pvs.diego.collections_online_adapter.context.ListAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.context.MapAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.context.SetAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.factories.AllocationContextFactory.AllocationContextBuilder.AllocationContextAlgorithm;

public class AllocationContextFactory {

	private static final int SAMPLES = 50;
	private static final int WINDOW_SIZE = 10;

	private static ScheduledExecutorService scheduler;

	public static class AllocationContextBuilder {

		private final CollectionTypeEnum type;
		private final String identifier;

		// Default: PASSIVE
		private AllocationContextAlgorithm algorithm = AllocationContextAlgorithm.PASSIVE;

		private boolean hasLog;
		private String logFile;

		private int windowSize = WINDOW_SIZE;
		private int samples = SAMPLES;
		private int initialDelay = 1000;
		private int delay = 1000;

		public enum AllocationContextAlgorithm {
			PASSIVE, ACTIVE;
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

		return null;
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

//		final SetAllocationOptimizer optimizer;
//		SetAllocationContextInfo context;
//
//		// Build the optimizer
//		switch (builder.algorithm) {
//
//		case ACTIVE:
//			optimizer = new SetActiveOptimizer(builder.windowSize);
//
//			// Schedule the Online Adapter Thread
//			scheduler = Executors.newScheduledThreadPool(1);
//			scheduler.scheduleAtFixedRate(new Runnable() {
//				@Override
//				public void run() {
//					((SetActiveOptimizer) optimizer).checkFinalizedAnalysis();
//				}
//			}, builder.initialDelay, builder.delay, TimeUnit.MILLISECONDS);
//
//			break;
//		case PASSIVE:
//			optimizer = new SetPassiveOptimizer(builder.windowSize);
//			break;
//		default:
//			optimizer = new SetPassiveOptimizer(builder.windowSize);
//			break;
//		}
//
//		// Build the context
//		context = new SetAllocationContextImpl(optimizer, builder.samples);
//
//		// Print the log of the changes
//		if (builder.hasLog) {
//			SetAllocationContext logContext = new LogSetAllocationContext(context, builder.identifier, builder.logFile);
//			optimizer.setContext(logContext);
//			return logContext;
//
//		} else {
//			optimizer.setContext(context);
//		}
//
//		return context;
		
		return null;

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
		
//		// Build the context + optimizer
//		final MapAllocationOptimizer optimizer;
//
//		// Build the optimizer
//		switch (builder.algorithm) {
//
//		case ACTIVE:
//			optimizer = new MapActiveOptimizer(builder.windowSize);
//
//			// Schedule the Online Adapter Thread
//			scheduler = Executors.newScheduledThreadPool(1);
//			scheduler.scheduleAtFixedRate(new Runnable() {
//				@Override
//				public void run() {
//					((MapActiveOptimizer) optimizer).checkFinalizedAnalysis();
//				}
//			}, builder.initialDelay, builder.delay, TimeUnit.MILLISECONDS);
//
//			break;
//		case PASSIVE:
//			optimizer = new MapPassiveOptimizer(builder.windowSize);
//			break;
//		default:
//			optimizer = new MapPassiveOptimizer(builder.windowSize);
//			break;
//		}
//
//		MapAllocationContextInfo context = new MapAllocationContextImpl(optimizer, builder.samples);
//
//		// Print the log of the changes
//		if (builder.hasLog) {
//			MapAllocationContext logContext = new LogMapAllocationContext(context, builder.identifier, builder.logFile);
//			optimizer.setContext(logContext);
//			return logContext;
//
//		}
//
//		return context;
		
		return null;
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

		String active = System.getProperty("active");
		if (active != null) {
			builder.withAlgorithm(AllocationContextAlgorithm.ACTIVE);
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
