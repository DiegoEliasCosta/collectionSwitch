package de.heidelberg.pvs.diego.collections_online_adapter.context.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import de.heidelberg.pvs.diego.collections_online_adapter.context.AllocationContextState;
import de.heidelberg.pvs.diego.collections_online_adapter.context.CollectionTypeEnum;
import de.heidelberg.pvs.diego.collections_online_adapter.context.MapAllocationContext;

public class LogMapAllocationContext implements MapAllocationContext {

	private static final int FREQUENCY = 10;

	MapAllocationContext context;

	PrintWriter writer;

	int count = 0;

	public LogMapAllocationContext(MapAllocationContext context, String identifier, String dir) {
		super();
		this.context = context;

		long currentTimeMillis = System.currentTimeMillis();

		try {
			writer = new PrintWriter(dir + "/" + identifier + "__-__" + currentTimeMillis + ".txt", "UTF-8");
			writer.println("Context initialized");
			writer.println("First Status: " + this.context.getAllocationContextState());
			writer.println("Specified collection: " + this.context.getChampion());
			writer.flush();
		} catch (IOException e) {
			if (writer != null) {
				writer.close();
			}
			// do something
		}
	}

	public void optimizeCollectionType(CollectionTypeEnum collecton, int mode, int medianInitialCapacity) {

		AllocationContextState beforeState = context.getAllocationContextState();
		context.optimizeCollectionType(collecton, mode, medianInitialCapacity);
		AllocationContextState afterState = context.getAllocationContextState();

		writer.println("State updated from " + beforeState + " -- to --" + afterState);
		writer.println("Champion choosed: " + context.getChampion());
		writer.println("Mode Initial Capacity = " + mode);
		writer.println("Median Initial Capacity = " + medianInitialCapacity);
		writer.flush();
	}

	public void noCollectionTypeConvergence(int mode, int medianInitialCapacity) {
		writer.println("No convergence");
		writer.println("Mode Initial Capacity = " + mode);
		writer.println("Median Initial Capacity = " + medianInitialCapacity);
		writer.flush();
		context.noCollectionTypeConvergence(mode, medianInitialCapacity);
	}

	@Override
	public AllocationContextState getAllocationContextState() {
		return this.context.getAllocationContextState();
	}

	@Override
	public CollectionTypeEnum getChampion() {
		return this.context.getChampion();
	}

	@Override
	public <K, V> Map<K, V> createMap() {

		count++;
		if (count % FREQUENCY == 0) {
			writer.println(String.format("Created %d maps \n\t-- initialCapacity (analyzed=%d || described=DEFAULT)  ", count, this.context.getInitialCapacity()));
			writer.flush();
		}

		return this.context.createMap();
	}

	@Override
	public <K, V> Map<K, V> createMap(int initialCapacity) {

		count++;
		if (count % FREQUENCY == 0) {
			writer.println(String.format("Created %d maps \n\t-- initialCapacity (analyzed=%d || described=%d)  ", count, this.context.getInitialCapacity(), initialCapacity));
			
			writer.flush();
		}

		return this.context.createMap(initialCapacity);
	}

	@Override
	public <K, V> Map<K, V> createMap(Map<K, V> map) {

		count++;
		if (count % FREQUENCY == 0) {
			writer.println(String.format("Copied %d maps \n\t-- initialCapacity (analyzed=%d || described=%d)  ", count, this.context.getInitialCapacity(), map.size()));
			writer.flush();
		}

		return this.context.createMap(map);
	}
	
	@Override
	public <K, V> Map<K, V> createMap(int initialCapacity, float loadFactor) {
		
		count++;
		if (count % FREQUENCY == 0) {
			writer.println(String.format("Copied %d maps \n\t-- initialCapacity (analyzed=%d || described=%d) \n\t-- (loadFactor=%d)  ", 
					count, this.context.getInitialCapacity(), initialCapacity, loadFactor));
			writer.flush();
		}
		
		return this.context.createMap(initialCapacity, loadFactor);
	}

	@Override
	public int getInitialCapacity() {
		return context.getInitialCapacity();
	}


}
