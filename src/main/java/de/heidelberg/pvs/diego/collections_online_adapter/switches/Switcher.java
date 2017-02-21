package de.heidelberg.pvs.diego.collections_online_adapter.switches;

import java.util.List;

import de.heidelberg.pvs.diego.collections_online_adapter.context.MovingAverageListAnalyzer;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

public class Switcher {
	
	private static final int EXPECTED_ALLOCATION_SITES = 100;
	
	public static Int2ObjectOpenHashMap<MovingAverageListAnalyzer> listContext = new Int2ObjectOpenHashMap<MovingAverageListAnalyzer>(EXPECTED_ALLOCATION_SITES); 
	
	public static List<?> createList(int codeID){
		return listContext.get(codeID).createList();
	}

}
