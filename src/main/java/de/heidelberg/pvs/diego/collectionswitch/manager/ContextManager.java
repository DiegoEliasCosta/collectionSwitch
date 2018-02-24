package de.heidelberg.pvs.diego.collectionswitch.manager;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.collections.impl.map.mutable.UnifiedMap;

import de.heidelberg.pvs.diego.collectionswitch.annotations.Switch;
import de.heidelberg.pvs.diego.collectionswitch.context.ListAllocationContext;
import de.heidelberg.pvs.diego.collectionswitch.context.ListCollectionType;
import de.heidelberg.pvs.diego.collectionswitch.context.MapAllocationContext;
import de.heidelberg.pvs.diego.collectionswitch.context.SetAllocationContext;
import de.heidelberg.pvs.diego.collectionswitch.factories.AllocationContextFactory;

public class ContextManager {
	
//	private static final int DEFAULT_AMOUNT_CONTEXTS = 16;
	
	private static AtomicInteger IdsGenerator = new AtomicInteger();
	
	private static UnifiedMap<String, ListAllocationContext> listContextsMap = new UnifiedMap<>();
	private static UnifiedMap<String, SetAllocationContext> setContextsMap = new UnifiedMap<>();
	private static UnifiedMap<String, MapAllocationContext> mapContextsMap = new UnifiedMap<>();
	
	
	public static int generateId() {
		return IdsGenerator.getAndIncrement();
	}
	
	public static List<?> createList(Switch contextName) {
		
		String contextID = contextName.name();
		return listContextsMap.getIfAbsentPut(contextID, AllocationContextFactory.
				buildListContext(ListCollectionType.JDK_ARRAYLIST, "" + contextID)).createList();
	}
	

}
