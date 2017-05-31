package de.heidelberg.pvs.diego.collections_online_adapter.context.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.List;

import de.heidelberg.pvs.diego.collections_online_adapter.context.AllocationContextState;
import de.heidelberg.pvs.diego.collections_online_adapter.context.CollectionTypeEnum;
import de.heidelberg.pvs.diego.collections_online_adapter.context.ListAllocationContext;

public class LogListAllocationContext implements ListAllocationContext {
	
	private static final int FREQUENCY = 10;

	ListAllocationContext context;
	
	PrintWriter writer;
	
	int count = 0;
	
	public LogListAllocationContext(ListAllocationContext context, String identifier, String dir) {
		super();
		this.context = context;
		
		long currentTimeMillis = System.currentTimeMillis();
		
		try{
		    writer = new PrintWriter(dir + "/" + identifier + "__-__" + currentTimeMillis + ".txt", "UTF-8");
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

	public <E> List<E> createList() {
		
		count++;
		if(count % FREQUENCY == 0) {
			writer.println(String.format("Created %d lists \n\t-- initialCapacity (analyzed=%d || described=10)  ", count, this.context.getInitialCapacity() ));
			writer.flush();
		}
	
		
		return context.createList();
	}

	public void noCollectionTypeConvergence(int mode, int medianInitialCapacity) {
		writer.println("No convergence");
		writer.println("Mode Initial Capacity = " + mode);
		writer.println("Median Initial Capacity = " + medianInitialCapacity);
		writer.flush();
		context.noCollectionTypeConvergence(mode, medianInitialCapacity);
	}

	public <E> List<E> createList(int initialCapacity) {
		
		count++;
		if(count % FREQUENCY == 0) {
			writer.println(String.format("Created %d lists \n\t-- initialCapacity (analyzed=%d || described=%d)  ", count, this.context.getInitialCapacity(), initialCapacity));
			writer.flush();
		}
		
		return context.createList(initialCapacity);
	}

	public <E> List<E> createList(Collection<? extends E> c) {
		
		count++;
		if(count % FREQUENCY == 0) {
			writer.println(String.format("Copied %d lists \n\t-- initialCapacity (analyzed=%d || described=%d)  ", count, this.context.getInitialCapacity(), c.size()));
			writer.flush();
		}
		
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

	@Override
	public void setAllocationContextState(AllocationContextState inactive) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getInitialCapacity() {
		return this.context.getInitialCapacity();
	}

	
	

}
