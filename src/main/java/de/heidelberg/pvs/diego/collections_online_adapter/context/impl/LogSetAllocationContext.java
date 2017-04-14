package de.heidelberg.pvs.diego.collections_online_adapter.context.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Set;

import de.heidelberg.pvs.diego.collections_online_adapter.context.AllocationContextState;
import de.heidelberg.pvs.diego.collections_online_adapter.context.CollectionTypeEnum;
import de.heidelberg.pvs.diego.collections_online_adapter.context.SetAllocationContext;

public class LogSetAllocationContext implements SetAllocationContext {
	
	SetAllocationContext context;
	
	PrintWriter writer;
	
	int count = 0;
	
	public LogSetAllocationContext(SetAllocationContext context, String identifier, String dir) {
		super();
		this.context = context;
		
		long currentTimeMillis = System.currentTimeMillis();
		
		try{
		    writer = new PrintWriter(dir + "/" +identifier + "__-__" + currentTimeMillis + ".txt", "UTF-8");
		    writer.println("Context initialized");
		    writer.println("First Status: " + this.context.getAllocationContextState());
		    writer.println("Specified collection: " + this.context.getChampion());
		    writer.flush();
		} catch (IOException e) {
			if(writer != null) {
				writer.close();
			}
		   // do something
		}
	}

	public void optimizeCollectionType(CollectionTypeEnum collecton, int mode, int medianInitialCapacity) {
		
		AllocationContextState beforeState = context.getAllocationContextState();
		context.optimizeCollectionType(collecton, mode,  medianInitialCapacity);
		AllocationContextState afterState = context.getAllocationContextState();
		
		writer.println("State updated from " + beforeState + " -- to --" + afterState);
		writer.println("Champion choosed: " + context.getChampion());
		writer.println("Mode Initial Capacity = " + mode);
		writer.println("Median Initial Capacity = " + medianInitialCapacity);
		writer.flush();
	}

	public <E> Set<E> createSet() {
		
		count++;
		if(count % 100 == 0) {
			writer.println("Created " + count + " sets");
			writer.flush();
		}
	
		
		return context.createSet();
	}

	public void noCollectionTypeConvergence(int mode, int medianInitialCapacity) {
		writer.println("No convergence");
		writer.println("Mode Initial Capacity = " + mode);
		writer.println("Median Initial Capacity = " + medianInitialCapacity);
		writer.flush();
		context.noCollectionTypeConvergence(mode, medianInitialCapacity);
	}

	public <E> Set<E> createSet(int initialCapacity) {
		
		count++;
		if(count % 100 == 0) {
			writer.println("Created " + count + " sets");
			writer.flush();
		}
		
		return context.createSet(initialCapacity);
	}

	public <E> Set<E> createSet(Collection<? extends E> c) {
		
		count++;
		if(count % 100 == 0) {
			writer.println("Created " + count + " sets");
			writer.flush();
		}
		
		return context.createSet(c);
	}

	@Override
	public AllocationContextState getAllocationContextState() {
		return this.context.getAllocationContextState();
	}

	@Override
	public CollectionTypeEnum getChampion() {
		return this.context.getChampion();
	}

	
	

}
