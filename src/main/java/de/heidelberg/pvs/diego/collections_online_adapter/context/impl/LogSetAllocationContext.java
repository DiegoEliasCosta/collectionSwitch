package de.heidelberg.pvs.diego.collections_online_adapter.context.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Set;

import de.heidelberg.pvs.diego.collections_online_adapter.context.SetAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.context.SetAllocationContextInfo;

public class LogSetAllocationContext implements SetAllocationContext {
	
	private static final int FREQUENCY = 10;

	SetAllocationContextInfo context;
	
	PrintWriter writer;
	
	int count = 0;
	
	public LogSetAllocationContext(SetAllocationContextInfo context, String identifier, String dir) {
		super();
		this.context = context;
		long currentTimeMillis = System.currentTimeMillis();
		
		try{
			writer = new PrintWriter(dir + "/" + identifier + "__-__" + currentTimeMillis + ".txt", "UTF-8");
		    writer.println("Context initialized");
		    writer.println("Collecton Type: " + this.context.getCurrentCollectionType());
		    writer.flush();
		} catch (IOException e) {
			if(writer != null) {
				writer.close();
			}
		   // do something
		}
	}

	
	@Override
	public void updateCollectionInitialCapacity(int size) {
		int prevInitial = context.getAnalyzedInitialCapacity();
		context.updateCollectionInitialCapacity(size);
		int updatedInitialCapacity = context.getAnalyzedInitialCapacity();
		
		writer.println("Initial Capacity updated from " + prevInitial + " -- to --" + updatedInitialCapacity);
		writer.println("New Initial Capacity = " + context.getAnalyzedInitialCapacity());
		writer.flush();
		
	}

	
	@Override
	public <E> Set<E> createSet() {
		count++;
		if(count % FREQUENCY == 0) {
			writer.println(String.format("Created %d sets \n\t-- initialCapacity (analyzed=%d || described=10)  ", count, this.context.getAnalyzedInitialCapacity() ));
			writer.flush();
		}
		return context.createSet();
	}


	@Override
	public <E> Set<E> createSet(int initialCapacity) {
		count++;
		if(count % FREQUENCY == 0) {
			writer.println(String.format("Created %d sets \n\t-- initialCapacity (analyzed=%d || described=%s)  ", count, this.context.getAnalyzedInitialCapacity(), initialCapacity));
			writer.flush();
		}
		return context.createSet(initialCapacity);
	}


	@Override
	public <E> Set<E> createSet(Collection<? extends E> set) {
		count++;
		if(count % FREQUENCY == 0) {
			writer.println(String.format("Copied %d sets \n\t-- initialCapacity (analyzed=%d || described=%s)  ", count, this.context.getAnalyzedInitialCapacity(), set.size()));
			writer.flush();
		}
		return context.createSet(set);
	}


	@Override
	public void updateCollectionType(SetCollectionType type) {
		String beforeState = context.getCurrentCollectionType();
		context.updateCollectionType(type);
		String afterState = context.getCurrentCollectionType();
		
		writer.println("Type updated from " + beforeState + " -- to --" + afterState);
		writer.println("New Initial Capacity = " + context.getAnalyzedInitialCapacity());
		writer.flush();		
	}

	

	
	

}
