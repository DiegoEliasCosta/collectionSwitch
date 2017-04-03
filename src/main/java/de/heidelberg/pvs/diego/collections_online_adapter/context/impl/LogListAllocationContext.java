package de.heidelberg.pvs.diego.collections_online_adapter.context.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.List;

import de.heidelberg.pvs.diego.collections_online_adapter.context.AllocationContextState;
import de.heidelberg.pvs.diego.collections_online_adapter.context.CollectionTypeEnum;
import de.heidelberg.pvs.diego.collections_online_adapter.context.ListAllocationContext;

public class LogListAllocationContext implements ListAllocationContext {
	
	ListAllocationContext context;
	
	PrintWriter writer;
	
	int count = 0;
	
	public LogListAllocationContext(ListAllocationContext context, String identifier) {
		super();
		this.context = context;
		
		long currentTimeMillis = System.currentTimeMillis();
		
		try{
		    writer = new PrintWriter(identifier + "__-__" + currentTimeMillis + ".txt", "UTF-8");
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

	public void optimizeCollectionType(CollectionTypeEnum collecton, int medianInitialCapacity) {
		
		AllocationContextState beforeState = context.getAllocationContextState();
		context.optimizeCollectionType(collecton, medianInitialCapacity);
		AllocationContextState afterState = context.getAllocationContextState();
		
		writer.println("State updated from " + beforeState + " -- to --" + afterState);
		writer.println("Champion choosed: " + context.getChampion());
		writer.println("Median Initial Capacity = " + medianInitialCapacity);
		writer.flush();
	}

	public <E> List<E> createList() {
		
		count++;
		if(count % 100 == 0) {
			writer.println("Created " + count + " lists");
			writer.flush();
		}
	
		
		return context.createList();
	}

	public void noCollectionTypeConvergence(int medianInitialCapacity) {
		writer.println("No convergence");
		writer.println("Median Initial Capacity = " + medianInitialCapacity);
		writer.flush();
		context.noCollectionTypeConvergence(medianInitialCapacity);
	}

	public <E> List<E> createList(int initialCapacity) {
		return context.createList(initialCapacity);
	}

	public <E> List<E> createList(Collection<? extends E> c) {
		return context.createList(c);
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
