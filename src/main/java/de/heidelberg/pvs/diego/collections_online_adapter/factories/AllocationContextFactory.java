package de.heidelberg.pvs.diego.collections_online_adapter.factories;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import de.heidelberg.pvs.diego.collections_online_adapter.context.CollectionTypeEnum;
import de.heidelberg.pvs.diego.collections_online_adapter.context.InactiveMapAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.context.InactiveSetAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.context.ListAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.context.MapAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.context.SetAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.context.impl.AdaptiveListAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.context.impl.AdaptiveMapAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.context.impl.AdaptiveSetAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.context.impl.InactiveListAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.context.impl.LogListAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.context.impl.LogMapAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.context.impl.LogSetAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.context.impl.ProactiveListAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.context.impl.ProactiveMapAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.context.impl.ProactiveSetAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.factories.AllocationContextFactory.AllocationContextBuilder.AllocationContextAlgorithm;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.lists.ListAllocationOptimizer;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.lists.ProactiveRuleBasedListOptimizer;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.lists.RuleBasedListOptimizer;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.lists.STRuleBasedListOptimizer;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.maps.MapAllocationOptimizer;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.maps.ProactiveRuleBasedMapOptimizer;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.maps.RuleBasedMapOptimizer;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.maps.STRuleBasedMapOptimizer;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.sets.ProactiveRuleBasedSetOptimizer;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.sets.RuleBasedSetOptimizer;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.sets.STRuleBasedSetOptimizer;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.sets.SetAllocationOptimizer;

public class AllocationContextFactory {
	
	// TODO: HUGE REFACTOR -- We should be able to use the same context for all collections

	private static final int THREADS_NUMBER = 2;
	// Default Values
	public static final int WINDOW_SIZE = 10;
	public static final int CONVERGENCE_RATE = 7;
	public static final int FULL_ANALYSIS_THRESHOLD = 100;
	public static final int SLEEPING_FREQUENCY = 100;
	public static final int DIVERGENCE_ROUNDS_THRESHOLD = 2;

	private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(THREADS_NUMBER);

	public static class AllocationContextBuilder {

		private final CollectionTypeEnum type;
		private final String identifier;

		private boolean singleThread;

		private AllocationContextAlgorithm algorithm;

		private boolean hasLog;
		private String logFile;

		private int windowSize = WINDOW_SIZE;
		private int convergenceRate = CONVERGENCE_RATE;
		private int fullAnalysisThr = FULL_ANALYSIS_THRESHOLD;
		private int sleepingFrequency = SLEEPING_FREQUENCY;
		private int divergenceRounds = DIVERGENCE_ROUNDS_THRESHOLD;

		public enum AllocationContextAlgorithm {

			ADAPTIVE, INACTIVE, PROACTIVE;

		}

		public AllocationContextBuilder(CollectionTypeEnum type, String identifier) {
			super();
			this.type = type;
			this.identifier = identifier;
			this.algorithm = AllocationContextAlgorithm.PROACTIVE; // DEFAULT
		}

		public AllocationContextBuilder withAlgorithm(AllocationContextAlgorithm algorithm) {
			this.algorithm = algorithm;
			return this;
		}

		public AllocationContextBuilder singleThread(boolean singleThread) {
			this.singleThread = singleThread;
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

		
		public AllocationContextBuilder sleepingFrequency(int sleepingFrequency) {
			this.sleepingFrequency = sleepingFrequency;
			return this;
		}
		
		public AllocationContextBuilder fullAnalysisThr(int fullAnalysisThr) {
			this.fullAnalysisThr = fullAnalysisThr;
			return this;
		}

		public AllocationContextBuilder divergenceRounds(int divergenceRounds) {
			this.divergenceRounds = divergenceRounds;
			return this;
		}

		public AllocationContextBuilder convergenceRate(int convergenceRate) {
			this.convergenceRate = convergenceRate;
			return this;
		}

		public ListAllocationContext buildListAllocationContext() {
			return buildListContext(this);
		}

		public SetAllocationContext buildSetAllocationContext() {
			return buildSetContext(this);
		}

		public MapAllocationContext buildMapAllocationContext() {
			return buildMapContext(this);
		}

	}

	/*
	 * LISTS
	 */
	public static ListAllocationContext buildListContext(CollectionTypeEnum type) {
		return buildListContext(type, "");
	}

	public static ListAllocationContext buildListContext(AllocationContextBuilder builder) {
		return buildListContext(builder.type, builder.identifier, builder.algorithm, builder.windowSize,
				builder.convergenceRate, builder.sleepingFrequency, builder.fullAnalysisThr, builder.divergenceRounds,
				builder.hasLog, builder.logFile, builder.singleThread);
	}
	
	public static ListAllocationContext buildListContext(CollectionTypeEnum type, String identifier,
			AllocationContextAlgorithm algorithm, int windowSize, int convergenceRate, int sleepingFrequency,
			int fullAnalysisThr, int divergenceRounds, boolean withLog, String logDir, boolean singleThreaded) {

		ListAllocationContext context;
		final ListAllocationOptimizer optimizer;

		switch (algorithm) {

		case INACTIVE:
			optimizer = null;
			return new InactiveListAllocationContext(type);

		case ADAPTIVE:
			if (singleThreaded) {
				optimizer = new STRuleBasedListOptimizer(windowSize, convergenceRate);
			} else {
				optimizer = new RuleBasedListOptimizer(windowSize, convergenceRate);
			}

			context = new AdaptiveListAllocationContext(type, optimizer, windowSize, fullAnalysisThr, sleepingFrequency,
					convergenceRate, divergenceRounds);
			optimizer.setContext(context);
			break;

		case PROACTIVE:

			optimizer = new ProactiveRuleBasedListOptimizer(windowSize, convergenceRate);
			context = new ProactiveListAllocationContext(type, optimizer, windowSize, fullAnalysisThr,
					sleepingFrequency, convergenceRate, divergenceRounds);

			int period = 100;
			int initialDelay = 100;
			
			String delayStr = System.getProperty("periodDelay");
			if(delayStr != null) {
				period = Integer.parseInt(delayStr);
			}
			
			scheduler.scheduleAtFixedRate(new Runnable() {
				@Override
				public void run() {
					optimizer.checkFinalizedAnalysis();
				}
			}, initialDelay, period, TimeUnit.MILLISECONDS);
			optimizer.setContext(context);
			break;

		default:
			context = null;
			optimizer = null;
			break;

		}

		// Print the log of the changes
		if (withLog) {

			ListAllocationContext logContext = new LogListAllocationContext(context, identifier, logDir);
			optimizer.setContext(logContext);
			return logContext;

		}

		return context;

	}

	
	public static SetAllocationContext buildSetContext(AllocationContextBuilder builder) {
		return buildSetContext(builder.type, builder.identifier, builder.algorithm, builder.windowSize,
				builder.convergenceRate, builder.sleepingFrequency, builder.fullAnalysisThr, builder.divergenceRounds,
				builder.hasLog, builder.logFile, builder.singleThread);
	}
	
	public static SetAllocationContext buildSetContext(CollectionTypeEnum type, String identifier,
			AllocationContextAlgorithm algorithm, int windowSize, int convergenceRate, int sleepingFrequency,
			int fullAnalysisThr, int divergenceRounds, boolean withLog, String logDir, boolean singleThreaded) {

		SetAllocationContext context;
		final SetAllocationOptimizer optimizer;

		switch (algorithm) {

		case INACTIVE:
			optimizer = null;
			return new InactiveSetAllocationContext(type);

		case ADAPTIVE:
			if (singleThreaded) {
				optimizer = new STRuleBasedSetOptimizer(windowSize, convergenceRate);
			} else {
				optimizer = new RuleBasedSetOptimizer(windowSize, convergenceRate);
			}

			context = new AdaptiveSetAllocationContext(type, optimizer, windowSize, fullAnalysisThr, sleepingFrequency,
					convergenceRate, divergenceRounds);
			optimizer.setContext(context);
			break;

		case PROACTIVE:
			
			int period = 100;
			int initialDelay = 100;
			
			String delayStr = System.getProperty("periodDelay");
			if(delayStr != null) {
				period = Integer.parseInt(delayStr);
			}

			optimizer = new ProactiveRuleBasedSetOptimizer(windowSize, convergenceRate);
			context = new ProactiveSetAllocationContext(type, optimizer, windowSize, fullAnalysisThr,
					sleepingFrequency, convergenceRate, divergenceRounds);

			scheduler.scheduleAtFixedRate(new Runnable() {
				@Override
				public void run() {
					optimizer.checkFinalizedAnalysis();
				}
			}, initialDelay, period, TimeUnit.MILLISECONDS);
			optimizer.setContext(context);
			break;

		default:
			context = null;
			optimizer = null;
			break;

		}

		// Print the log of the changes
		if (withLog) {

			SetAllocationContext logContext = new LogSetAllocationContext(context, identifier, logDir);
			optimizer.setContext(logContext);
			return logContext;

		}

		return context;

	}
	
	
	public static MapAllocationContext buildMapContext(AllocationContextBuilder builder) {
		return buildMapContext(builder.type, builder.identifier, builder.algorithm, builder.windowSize,
				builder.convergenceRate, builder.sleepingFrequency, builder.fullAnalysisThr, builder.divergenceRounds,
				builder.hasLog, builder.logFile, builder.singleThread);
	}
	
	public static MapAllocationContext buildMapContext(CollectionTypeEnum type, String identifier,
			AllocationContextAlgorithm algorithm, int windowSize, int convergenceRate, int sleepingFrequency,
			int fullAnalysisThr, int divergenceRounds, boolean withLog, String logDir, boolean singleThreaded) {

		MapAllocationContext context;
		final MapAllocationOptimizer optimizer;

		switch (algorithm) {

		case INACTIVE:
			optimizer = null;
			return new InactiveMapAllocationContext(type);

		case ADAPTIVE:
			if (singleThreaded) {
				optimizer = new STRuleBasedMapOptimizer(windowSize, convergenceRate);
			} else {
				optimizer = new RuleBasedMapOptimizer(windowSize, convergenceRate);
			}

			context = new AdaptiveMapAllocationContext(type, optimizer, windowSize, fullAnalysisThr, sleepingFrequency,
					convergenceRate, divergenceRounds);
			optimizer.setContext(context);
			break;

		case PROACTIVE:
			
			int period = 100;
			int initialDelay = 100;
			String delayStr = System.getProperty("periodDelay");
			if(delayStr != null) {
				period = Integer.parseInt(delayStr);
			}
			
			optimizer = new ProactiveRuleBasedMapOptimizer(windowSize, convergenceRate);
			context = new ProactiveMapAllocationContext(type, optimizer, windowSize, fullAnalysisThr,
					sleepingFrequency, convergenceRate, divergenceRounds);

			scheduler.scheduleAtFixedRate(new Runnable() {
				@Override
				public void run() {
					optimizer.checkFinalizedAnalysis();
				}
			}, initialDelay, period, TimeUnit.MILLISECONDS);
			optimizer.setContext(context);
			break;

		default:
			context = null;
			optimizer = null;
			break;

		}

		// Print the log of the changes
		if (withLog) {

			MapAllocationContext logContext = new LogMapAllocationContext(context, identifier, logDir);
			optimizer.setContext(logContext);
			return logContext;

		}

		return context;

	}

	
	public static AllocationContextBuilder parseCommandLine(CollectionTypeEnum type, String identifier) {

		AllocationContextBuilder builder = new AllocationContextBuilder(type, identifier);

		String windowSizeStr = System.getProperty("windowSize");
		if (windowSizeStr != null) {
			builder.windowSize(Integer.parseInt(windowSizeStr));
		}

		String convergencyRateStr = System.getProperty("convergenceRate");
		if (convergencyRateStr != null) {
			builder.convergenceRate(Integer.parseInt(convergencyRateStr));
		}

		String sleepingFrequencyStr = System.getProperty("sleepingFrequency");
		if (sleepingFrequencyStr != null) {
			builder.sleepingFrequency(Integer.parseInt(sleepingFrequencyStr));
		}

		String fullAnalysysStr = System.getProperty("fullAnalysis");
		if (fullAnalysysStr != null) {
			builder.fullAnalysisThr(Integer.parseInt(fullAnalysysStr));
		}

		builder.singleThread(System.getProperty("single-thread") != null);

		// DEFAULT
		builder.withAlgorithm(AllocationContextAlgorithm.PROACTIVE);
		if (System.getProperty("adaptive") != null) {
			builder.withAlgorithm(AllocationContextAlgorithm.ADAPTIVE);
		}

		String logFile = System.getProperty("log");
		if(logFile != null) {
			builder.withLog(logFile);
		}
		
		return builder;

	}

	public static ListAllocationContext buildListContext(CollectionTypeEnum type, String identifier) {

		AllocationContextBuilder builder = parseCommandLine(type, identifier);
		
		if (System.getProperty("no-lists") != null) {
			builder.withAlgorithm(AllocationContextAlgorithm.INACTIVE);
		} 
		
		return builder.buildListAllocationContext();

	}

	/*
	 * SETS
	 */
	public static <E> SetAllocationContext buildSetContext(CollectionTypeEnum type) {

		SetAllocationOptimizer optimizer = new RuleBasedSetOptimizer(WINDOW_SIZE, CONVERGENCE_RATE);

		SetAllocationContext context = new AdaptiveSetAllocationContext(type, optimizer, WINDOW_SIZE,
				FULL_ANALYSIS_THRESHOLD, SLEEPING_FREQUENCY, CONVERGENCE_RATE, DIVERGENCE_ROUNDS_THRESHOLD);
		optimizer.setContext(context);

		return context;
	}

	public static <E> SetAllocationContext buildSetContext(CollectionTypeEnum type, String identifier) {

		AllocationContextBuilder builder = parseCommandLine(type, identifier);

		if (System.getProperty("no-sets") != null) {
			// No sets will be monitored
			return new InactiveSetAllocationContext(type);
		}

		
		return builder.buildSetAllocationContext();

	}

	/*
	 * MAPS
	 */
	public static <K, V> MapAllocationContext buildMapContext(CollectionTypeEnum type) {

		MapAllocationOptimizer optimizer = new RuleBasedMapOptimizer(WINDOW_SIZE, CONVERGENCE_RATE);

		MapAllocationContext context = new AdaptiveMapAllocationContext(type, optimizer, WINDOW_SIZE,
				FULL_ANALYSIS_THRESHOLD, SLEEPING_FREQUENCY, CONVERGENCE_RATE, DIVERGENCE_ROUNDS_THRESHOLD);

		optimizer.setContext(context);

		return context;
	}

	/*
	 * MAPS
	 */
	public static <K, V> MapAllocationContext buildMapContext(CollectionTypeEnum type, String identifier) {

		// No maps will be monitored
		if (System.getProperty("no-maps") != null) {
			return new InactiveMapAllocationContext(type);
		}

		String windowSizeStr = System.getProperty("windowSize");
		Integer windowSize = windowSizeStr != null ? Integer.parseInt(windowSizeStr) : WINDOW_SIZE;

		String convergencyRateStr = System.getProperty("convergencyRate");
		Integer convergencyRate = convergencyRateStr != null ? Integer.parseInt(convergencyRateStr) : CONVERGENCE_RATE;

		String sleepingFrequencyStr = System.getProperty("sleepingFrequency");
		Integer sleepingFrequency = sleepingFrequencyStr != null ? Integer.parseInt(sleepingFrequencyStr)
				: SLEEPING_FREQUENCY;

		String fullAnalysisStr = System.getProperty("fullAnalysis");
		Integer fullAnalysis = fullAnalysisStr != null ? Integer.parseInt(fullAnalysisStr) : FULL_ANALYSIS_THRESHOLD;

		MapAllocationOptimizer optimizer;
		if (System.getProperty("single-thread") != null) {
			optimizer = new STRuleBasedMapOptimizer(windowSize, convergencyRate);
		} else {
			optimizer = new RuleBasedMapOptimizer(windowSize, convergencyRate);

		}

		MapAllocationContext context = new AdaptiveMapAllocationContext(type, optimizer, windowSize, fullAnalysis,
				sleepingFrequency, convergencyRate, DIVERGENCE_ROUNDS_THRESHOLD);

		if (System.getProperty("log") != null) {
			String dir = System.getProperty("log");

			MapAllocationContext logcontext = new LogMapAllocationContext(context, identifier, dir);
			optimizer.setContext(logcontext);
			return logcontext;
		} else {
			optimizer.setContext(context);
			return context;
		}

	}

}
