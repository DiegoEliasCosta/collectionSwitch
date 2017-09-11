package de.heidelberg.pvs.diego.collections_online_adapter.context.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.List;

import de.heidelberg.pvs.diego.collections_online_adapter.context.CollectionTypeEnum;
import de.heidelberg.pvs.diego.collections_online_adapter.context.ListAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.context.ListAllocationContextInfo;
import de.heidelberg.pvs.diego.collections_online_adapter.context.ListCollectionType;

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

	public <E> List<E> createList() {
		
		count++;
		if(count % FREQUENCY == 0) {
			writer.println(String.format("Created %d lists \n\t-- initialCapacity (analyzed=%d || described=10)  ", count, this.context.getAnalyzedInitialCapacity() ));
			writer.flush();
		}
	
		
		return context.createList();
	}
	
	public void noCollectionTypeConvergence(int mode, int medianInitialCapacity) {
		
	}

	public <E> List<E> createList(int initialCapacity) {
		
		count++;
		if(count % FREQUENCY == 0) {
			writer.println(String.format("Created %d lists \n\t-- initialCapacity (analyzed=%d || described=%d)  ", count, this.context.getAnalyzedInitialCapacity(), initialCapacity));
			writer.flush();
		}
		
		return context.createList(initialCapacity);
	}

	public <E> List<E> createList(Collection<? extends E> c) {
		
		count++;
		if(count % FREQUENCY == 0) {
			writer.println(String.format("Copied %d lists \n\t-- initialCapacity (analyzed=%d || described=%d)  ", count, this.context.getAnalyzedInitialCapacity(), c.size()));
			writer.flush();
		}
		
		return context.createList(c);
	}


	@Override
	public void updateCollectionType(ListCollectionType type) {
		String beforeState = context.getCurrentCollectionType();
		context.updateCollectionType(type);
		String afterState = context.getCurrentCollectionType();
		
		writer.println("Type updated from " + beforeState + " -- to --" + afterState);
		writer.println("New Initial Capacity = " + context.getAnalyzedInitialCapacity());
		writer.flush();
		
	}

	

	
	

}
