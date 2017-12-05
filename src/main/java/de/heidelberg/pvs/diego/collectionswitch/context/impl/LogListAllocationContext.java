package de.heidelberg.pvs.diego.collectionswitch.context.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.List;

import de.heidelberg.pvs.diego.collectionswitch.context.CollectionTypeEnum;
import de.heidelberg.pvs.diego.collectionswitch.context.ListAllocationContext;
import de.heidelberg.pvs.diego.collectionswitch.context.ListAllocationContextInfo;
import de.heidelberg.pvs.diego.collectionswitch.context.ListCollectionType;

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
			// FIXME: This should be temporary
			System.out.println(e);
			if(writer != null) {
				writer.close();
			}
		   // do something
		}
	}

	
	private void logListCreation() {
		count++;
		if(count % FREQUENCY == 0) {
			writer.println(String.format("Created %d lists", count));
			writer.flush();
		}
	}
	

	public <E> List<E> createList() {
		logListCreation();
		return context.createList();
	}
	
	public <E> List<E> createList(int initialCapacity) {
		logListCreation();
		return context.createList(initialCapacity);
	}

	public <E> List<E> createList(Collection<? extends E> c) {
		logListCreation();
		return context.createList(c);
	}


	@Override
	public void updateCollectionType(ListCollectionType type) {
		ListCollectionType beforeState = context.getCurrentCollectionType();
		context.updateCollectionType(type);
		ListCollectionType afterState = context.getCurrentCollectionType();
		
		writer.println("Type updated from " + beforeState + " -- to --" + afterState);
		writer.flush();
		
	}

	

	
	

}
