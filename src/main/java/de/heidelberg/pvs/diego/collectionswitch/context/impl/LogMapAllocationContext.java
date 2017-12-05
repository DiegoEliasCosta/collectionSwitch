package de.heidelberg.pvs.diego.collectionswitch.context.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import de.heidelberg.pvs.diego.collectionswitch.context.MapAllocationContext;
import de.heidelberg.pvs.diego.collectionswitch.context.MapAllocationContextInfo;
import de.heidelberg.pvs.diego.collectionswitch.context.MapCollectionType;

public class LogMapAllocationContext implements MapAllocationContext {
	
	private static final int FREQUENCY = 1000;
	static Format formatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss-SSS");

	MapAllocationContextInfo context;
	
	PrintWriter writer;
	
	int count = 0;
	
	public LogMapAllocationContext(MapAllocationContextInfo context, String identifier, String dir) {
		super();
		this.context = context;
		
		Date date = new Date(System.currentTimeMillis());
		
		try{
		    writer = new PrintWriter(dir + "/" + identifier + "__-__" +  formatter.format(date)   + ".txt", "UTF-8");
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
	
	private void logMapCreation() {
		count++;
		if(count % FREQUENCY == 0) {
			writer.println(String.format("Created %d maps", count));
			writer.flush();
		}
	}

	@Override
	public <K, V> Map<K, V> createMap() {
		logMapCreation();
		return context.createMap();
	}

	@Override
	public <K, V> Map<K, V> createMap(int initialCapacity) {
		logMapCreation();
		return context.createMap(initialCapacity);
	}


	@Override
	public <K, V> Map<K, V> createMap(int initialCapacity, float loadFactor) {
		logMapCreation();
		return context.createMap(initialCapacity, loadFactor);
	}


	@Override
	public <K, V> Map<K, V> createMap(Map<K, V> map) {
		logMapCreation();
		return context.createMap(map);
	}


	@Override
	public void updateCollectionType(MapCollectionType type) {
		MapCollectionType beforeState = context.getCurrentCollectionType();
		context.updateCollectionType(type);
		MapCollectionType afterState = context.getCurrentCollectionType();
		
		if(!beforeState.equals(afterState)) {
			writer.println(String.format("%d maps created so far.", count));
			writer.println("Type updated from " + beforeState + " -- to --" + afterState);
			writer.flush();
		}
		
	}

	

	
	

}
