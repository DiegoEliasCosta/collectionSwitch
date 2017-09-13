package de.heidelberg.pvs.diego.collections_online_adapter.factories;

import de.heidelberg.pvs.diego.collections_online_adapter.context.ListAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.context.ListAllocationContextInfo;
import de.heidelberg.pvs.diego.collections_online_adapter.context.ListCollectionType;
import de.heidelberg.pvs.diego.collections_online_adapter.context.MapAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.context.MapAllocationContextInfo;
import de.heidelberg.pvs.diego.collections_online_adapter.context.MapCollectionType;
import de.heidelberg.pvs.diego.collections_online_adapter.context.SetAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.context.SetAllocationContextInfo;
import de.heidelberg.pvs.diego.collections_online_adapter.context.SetCollectionType;
import de.heidelberg.pvs.diego.collections_online_adapter.context.impl.InitialCapacityListAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.context.impl.InitialCapacityMapAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.context.impl.InitialCapacitySetAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.context.impl.LogListAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.context.impl.LogMapAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.context.impl.LogSetAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.manager.PerformanceGoal;
import de.heidelberg.pvs.diego.collections_online_adapter.manager.PerformanceGoal.PerformanceDimension;
import de.heidelberg.pvs.diego.collections_online_adapter.manager.SwitchManager;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.lists.ListActiveOptimizer;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.lists.ListAllocationOptimizer;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.lists.ListEmpiricalOptimizer;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.lists.ListEmpiricalPerformanceEvaluator;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.maps.MapActiveOptimizer;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.maps.MapAllocationOptimizer;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.maps.MapEmpiricalPerformanceEvaluator;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.sets.SetActiveOptimizer;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.sets.SetAllocationOptimizer;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.sets.SetEmpiricalPerformanceEvaluator;

public class AllocationContextFactory {

	private static final double FINISHED_RATIO = 1;
	private static final int SAMPLES = 50;
	private static final int WINDOW_SIZE = 10;
	private static final int DELAY = 1000;
	private static final int INITIAL_DELAY = 1000;
	private static final double DEFAULT_MIN_IMPROVEMENT = 1.2;
	private static final double DEFAULT_MAX_PENALTY = 0.7;
	private static final double DEFAULT_FINISHED_RATIO = 0.8;
	private static final int DEFAULT_THREADS_NUMBER = 1;

	protected static SwitchManager manager = new SwitchManager();
	private static PerformanceGoal goal;
	private static boolean init;
	private static AllocationContextBuilder builder;

	private static ListEmpiricalPerformanceEvaluator listEvaluator;
	private static SetEmpiricalPerformanceEvaluator setEvaluator;
	private static MapEmpiricalPerformanceEvaluator mapEvaluator;

	public static class AllocationContextBuilder {

		// Default: EMPIRICAL
		private AllocationContextAlgorithm algorithm = AllocationContextAlgorithm.EMPIRICAL;

		private boolean hasLog;
		private String logFile;

		private int windowSize = WINDOW_SIZE;
		private int samples = SAMPLES;
		private int initialDelay = INITIAL_DELAY;
		private int delay = DELAY;
		private int threadsNumber = DEFAULT_THREADS_NUMBER;

		private double finishedRatio = DEFAULT_FINISHED_RATIO;
		private PerformanceDimension majorDimension = PerformanceDimension.TIME;
		private PerformanceDimension minorDimension = PerformanceDimension.ALLOCATION;
		private double maxPenalty = DEFAULT_MAX_PENALTY;
		private double minImprovement = DEFAULT_MIN_IMPROVEMENT;

		public enum AllocationContextAlgorithm {
			INITIAL_CAPACITY, EMPIRICAL;
		}

		public AllocationContextBuilder() {
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

		public AllocationContextBuilder withThreadsNumber(int parseInt) {
			this.threadsNumber = parseInt;
			return this;

		}

		public AllocationContextBuilder withFinishedRatio(double parseDouble) {
			this.finishedRatio = parseDouble;
			return this;

		}

		public AllocationContextBuilder withMajorDimension(PerformanceDimension parse) {
			this.majorDimension = parse;
			return this;
		}

		public AllocationContextBuilder withMinorDimension(PerformanceDimension parse) {
			this.minorDimension = parse;
			return this;

		}

		public void withMinImprovement(double parseDouble) {
			this.minImprovement = parseDouble;

		}

		public void withMaxPenalty(double parseDouble) {
			this.maxPenalty = parseDouble;

		}

	}

	/*
	 * LISTS
	 */
	public static ListAllocationContext buildListContext(ListCollectionType type, String identifier) {

		if (!init) {
			bootstrap();
		}

		return buildListContext(type, builder, identifier);
	}

	private synchronized static void bootstrap() {
		
		// FIXME: This has a big chance of concurrency issues - FIX THIS LATER
		init = true;
		
		parseCommandLine();

		listEvaluator = new ListEmpiricalPerformanceEvaluator();
		listEvaluator.addEmpiricalModel(PerformanceDimension.TIME,
				PerformanceModelFactory.buildListPerformanceModelsTime());
		listEvaluator.addEmpiricalModel(PerformanceDimension.ALLOCATION,
				PerformanceModelFactory.buildListPerformanceModelsAllocation());

		setEvaluator = new SetEmpiricalPerformanceEvaluator();
		setEvaluator.addEmpiricalModel(PerformanceDimension.TIME,
				PerformanceModelFactory.buildSetsPerformanceModelTime());
		setEvaluator.addEmpiricalModel(PerformanceDimension.ALLOCATION,
				PerformanceModelFactory.buildSetsPerformanceModelAllocation());

		mapEvaluator = new MapEmpiricalPerformanceEvaluator();
		mapEvaluator.addEmpiricalModel(PerformanceDimension.TIME,
				PerformanceModelFactory.buildMapsPerformanceModelTime());
		mapEvaluator.addEmpiricalModel(PerformanceDimension.ALLOCATION,
				PerformanceModelFactory.buildMapsPerformanceModelAllocation());

		goal = new PerformanceGoal(builder.majorDimension, builder.minorDimension, builder.minImprovement,
				builder.maxPenalty);

		manager.configureAndScheduleManager(builder.threadsNumber, builder.initialDelay, builder.delay);

	}

	private static ListAllocationContext buildListContext(ListCollectionType type, AllocationContextBuilder builder,
			String identifier) {

		final ListAllocationOptimizer optimizer;
		ListAllocationContextInfo context = null;

		// Build the optimizer
		switch (builder.algorithm) {

		case INITIAL_CAPACITY:
			// TODO: Put this into the Switch Thread Manager
			optimizer = new ListActiveOptimizer(builder.windowSize, FINISHED_RATIO);

			manager.addOptimizer(optimizer);
			context = new InitialCapacityListAllocationContext(optimizer, builder.windowSize, builder.samples);
			break;
		case EMPIRICAL:
		default:
			optimizer = new ListEmpiricalOptimizer(listEvaluator, type, goal, builder.windowSize,
					builder.finishedRatio);

		}

		// Print the log of the changes
		if (builder.hasLog) {
			ListAllocationContext logContext = new LogListAllocationContext(context, identifier, builder.logFile);
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
	public static <E> SetAllocationContext buildSetContext(SetCollectionType type, String identifier) {

		if (!init) {
			bootstrap();
		}
		return buildSetContext(type, builder, identifier);

	}

	public static <E> SetAllocationContext buildSetContext(SetCollectionType type, AllocationContextBuilder builder,
			String identifier) {

		final SetAllocationOptimizer optimizer;
		SetAllocationContextInfo context = null;

		// Build the optimizer
		switch (builder.algorithm) {

		case INITIAL_CAPACITY:
		default:
			// TODO: Put this into the Switch Thread Manager
			optimizer = new SetActiveOptimizer(builder.windowSize, FINISHED_RATIO);
			manager.addOptimizer(optimizer);

			context = new InitialCapacitySetAllocationContext(optimizer, builder.windowSize);
			break;

		}

		// Print the log of the changes
		if (builder.hasLog) {
			SetAllocationContext logContext = new LogSetAllocationContext(context, identifier, builder.logFile);
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
	public static <K, V> MapAllocationContext buildMapContext(MapCollectionType type, String identifier) {

		if (!init) {
			bootstrap();
		}

		return buildMapContext(type, builder, identifier);

	}

	public static MapAllocationContext buildMapContext(MapCollectionType type, AllocationContextBuilder builder,
			String identifier) {

		// Build the context + optimizer
		final MapAllocationOptimizer optimizer;

		// Build the optimizer
		switch (builder.algorithm) {

		case INITIAL_CAPACITY:
		default:
			optimizer = new MapActiveOptimizer(builder.windowSize, FINISHED_RATIO);
			manager.addOptimizer(optimizer);

			break;
		}

		MapAllocationContextInfo context = new InitialCapacityMapAllocationContext(optimizer, builder.samples);

		// Print the log of the changes
		if (builder.hasLog) {
			MapAllocationContext logContext = new LogMapAllocationContext(context, identifier, builder.logFile);
			optimizer.setContext(logContext);
			return logContext;

		}

		return context;
	}

	/*
	 * COMMAND LINE
	 */
	public static AllocationContextBuilder parseCommandLine() {

		AllocationContextBuilder builder = new AllocationContextBuilder();

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

		String threads = System.getProperty("threads");
		if (threads != null) {
			builder.withThreadsNumber(Integer.parseInt(threads));
		}

		String finished = System.getProperty("finishedRatio");
		if (threads != null) {
			builder.withFinishedRatio(Double.parseDouble(finished));
		}

		String majorDimension = System.getProperty("majorDimension");
		if (majorDimension != null) {
			builder.withMajorDimension(PerformanceGoal.PerformanceDimension.parse(majorDimension));
		}

		String minImprovement = System.getProperty("minImprovement");
		if (minImprovement != null) {
			builder.withMinImprovement(Double.parseDouble(minImprovement));
		}

		String minorDimension = System.getProperty("minorDimension");
		if (minorDimension != null) {
			builder.withMinorDimension(PerformanceGoal.PerformanceDimension.parse(minorDimension));
		}

		String maxPenalty = System.getProperty("maxPenalty");
		if (maxPenalty != null) {
			builder.withMaxPenalty(Double.parseDouble(maxPenalty));
		}

		return builder;

	}

}
