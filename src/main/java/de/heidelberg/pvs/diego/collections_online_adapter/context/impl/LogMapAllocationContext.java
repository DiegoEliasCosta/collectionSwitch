package de.heidelberg.pvs.diego.collections_online_adapter.context.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import de.heidelberg.pvs.diego.collections_online_adapter.context.MapAllocationContext;
import de.heidelberg.pvs.diego.collections_online_adapter.context.MapAllocationContextInfo;

public class LogMapAllocationContext implements MapAllocationContext {
	
	private static final int FREQUENCY = 10;

	MapAllocationContextInfo context;
	
	PrintWriter writer;
	
	int count = 0;
	
	public LogMapAllocationContext(MapAllocationContextInfo context, String identifier, String dir) {
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
		String beforeState = context.getCurrentCollectionType();
		context.updateCollectionInitialCapacity(size);
		String afterState = context.getCurrentCollectionType();
		
		writer.println("State updated from " + beforeState + " -- to --" + afterState);
		writer.println("New Initial Capacity = " + context.getAnalyzedInitialCapacity());
		writer.flush();
		
	}

	
	public void noCollectionTypeConvergence(int mode, int medianInitialCapacity) {
		// TODO To implement
	}

	@Override
	public <K, V> Map<K, V> createMap() {
		count++;
		if(count % FREQUENCY == 0) {
			writer.println(String.format("Created %d maps \n\t-- initialCapacity (analyzed=%d || described=10)  ", count, this.context.getAnalyzedInitialCapacity() ));
			writer.flush();
		}
		
		return context.createMap();
	}


	@Override
	public <K, V> Map<K, V> createMap(int initialCapacity) {
		count++;
		if(count % FREQUENCY == 0) {
			writer.println(String.format("Created %d maps \n\t-- initialCapacity (analyzed=%d || described=10)  ", count, this.context.getAnalyzedInitialCapacity() ));
			writer.flush();
		}
		
		return context.createMap(initialCapacity);
	}


	@Override
	public <K, V> Map<K, V> createMap(int initialCapacity, float loadFactor) {
		count++;
		if(count % FREQUENCY == 0) {
			writer.println(String.format("Created %d maps \n\t-- initialCapacity (analyzed=%d || described=10)  ", count, this.context.getAnalyzedInitialCapacity() ));
			writer.flush();
		}
		
		return context.createMap(initialCapacity, loadFactor);
	}


	@Override
	public <K, V> Map<K, V> createMap(Map<K, V> map) {
		count++;
		if(count % FREQUENCY == 0) {
			writer.println(String.format("Created %d maps \n\t-- initialCapacity (analyzed=%d || described=10)  ", count, this.context.getAnalyzedInitialCapacity() ));
			writer.flush();
		}
		
		return context.createMap(map);
	}

	

	
	

}
