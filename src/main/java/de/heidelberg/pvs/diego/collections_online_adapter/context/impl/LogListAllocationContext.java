package de.heidelberg.pvs.diego.collections_online_adapter.context.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.List;

import de.heidelberg.pvs.diego.collections_online_adapter.context.AllocationContextState;
import de.heidelberg.pvs.diego.collections_online_adapter.context.ListAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.context.ListAllocationContextInfo;

public class LogListAllocationContext implements ListAllocationContext {
	
	private static final int FREQUENCY = 10;

	ListAllocationContextInfo context;
	
	PrintWriter writer;
	
	int count = 0;
	
	public LogListAllocationContext(ListAllocationContextInfo context, String identifier, String dir) {
		super();
		this.context = context;
		
		long currentTimeMillis = System.currentTimeMillis();
		
		try{
		    writer = new PrintWriter(dir + "/" + identifier + "__-__" + currentTimeMillis + ".txt", "UTF-8");
		    writer.println("Context initialized");
		    writer.println("First Status: " + this.context.getAllocationContextState());
		    writer.flush();
		} catch (IOException e) {
			if(writer != null) {
				writer.close();
			}
		   // do something
		}
	}

	
	@Override
	public void updateCollectionSize(int size) {
		AllocationContextState beforeState = context.getAllocationContextState();
		context.updateCollectionSize(size);
		AllocationContextState afterState = context.getAllocationContextState();
		
		writer.println("State updated from " + beforeState + " -- to --" + afterState);
		writer.println("New Initial Capacity = " + context.getInitialCapacity());
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
	
	@Override
	public void noSizeConvergence() {
		writer.println("No size convergence");
		writer.flush();
		context.noSizeConvergence();
		
	}

	public void noCollectionTypeConvergence(int mode, int medianInitialCapacity) {
		
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

	

	
	

}