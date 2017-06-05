package de.heidelberg.pvs.diego.collections_online_adapter.factories;

import de.heidelberg.pvs.diego.collections_online_adapter.context.CollectionTypeEnum;
import de.heidelberg.pvs.diego.collections_online_adapter.context.ListAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.context.MapAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.context.MapAllocationContextInfo;
import de.heidelberg.pvs.diego.collections_online_adapter.context.SetAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.context.SetAllocationContextInfo;
import de.heidelberg.pvs.diego.collections_online_adapter.context.impl.LogMapAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.context.impl.LogSetAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.context.impl.MapAllocationContextImpl;
import de.heidelberg.pvs.diego.collections_online_adapter.context.impl.SetAllocationContextImpl;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.AllocationOptimizer;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.PassiveOptimizer;

public class AllocationContextFactory {
	
	private static final int SAMPLES = 50;
	private static final int WINDOW_SIZE = 10;
	
	
	
	public static class AllocationContextBuilder {

		private final CollectionTypeEnum type;
		private final String identifier;

		private AllocationContextAlgorithm algorithm;

		private boolean hasLog;
		private String logFile;

		private int windowSize = WINDOW_SIZE;
		private int samples = SAMPLES;

		public enum AllocationContextAlgorithm {

			ADAPTIVE;

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
			this.samples  = samples;
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
	 * SETS
	 */
	public static <E> SetAllocationContext buildSetContext(CollectionTypeEnum type, String identifier) {
		
		// Parse command line
		AllocationContextBuilder builder = parseCommandLine(type, identifier);
		
		// Build the context + optimizer
		AllocationOptimizer optimizer = new PassiveOptimizer(builder.windowSize);
		SetAllocationContextInfo context = new SetAllocationContextImpl(optimizer);

		// Print the log of the changes
		if (builder.hasLog) {
			SetAllocationContext logContext = new LogSetAllocationContext(context, identifier, builder.logFile);
			optimizer.setContext(logContext);
			return logContext;

		}

		return context;
	}
	
	public static <E> SetAllocationContext buildSetContext(int sample, int windowSize) {
		AllocationOptimizer optimizer = new PassiveOptimizer(windowSize);
		SetAllocationContext context = new SetAllocationContextImpl(optimizer, sample);
		optimizer.setContext(context);
		return context;
	}

	/*
	 * MAPS
	 */

	public static <K, V> MapAllocationContext buildMapContext(CollectionTypeEnum type, String identifier) {
		// Parse command line
		AllocationContextBuilder builder = parseCommandLine(type, identifier);
		
		// Build the context + optimizer
		AllocationOptimizer optimizer = new PassiveOptimizer(builder.windowSize);
		MapAllocationContextInfo context = new MapAllocationContextImpl(optimizer);

		// Print the log of the changes
		if (builder.hasLog) {
			MapAllocationContext logContext = new LogMapAllocationContext(context, identifier, builder.logFile);
			optimizer.setContext(logContext);
			return logContext;

		}

		return context;

	}

	public static <K, V> MapAllocationContext buildMapContext(int sample, int windowSize) {
		AllocationOptimizer optimizer = new PassiveOptimizer(windowSize);
		MapAllocationContext context = new MapAllocationContextImpl(optimizer, sample);
		optimizer.setContext(context);
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
		if(sampleStr != null) {
			builder.samples(Integer.parseInt(sampleStr));
		}

		String logFile = System.getProperty("log");
		if(logFile != null) {
			builder.withLog(logFile);
		}
		
		return builder;

	}
	

	
}
