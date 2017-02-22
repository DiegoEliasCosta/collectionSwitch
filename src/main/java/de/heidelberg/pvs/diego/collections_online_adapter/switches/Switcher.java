package de.heidelberg.pvs.diego.collections_online_adapter.switches;

import java.util.List;

import de.heidelberg.pvs.diego.collections_online_adapter.context.ListAllocationContext;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

public class Switcher {
	
	private static final int EXPECTED_ALLOCATION_SITES = 100;
	
	public static Int2ObjectOpenHashMap<ListAllocationContext<?>> listContext = new Int2ObjectOpenHashMap<ListAllocationContext<?>>(EXPECTED_ALLOCATION_SITES); 
	
	public static List<?> createList(int codeID){
		return listContext.get(codeID).createList();
	}

}
