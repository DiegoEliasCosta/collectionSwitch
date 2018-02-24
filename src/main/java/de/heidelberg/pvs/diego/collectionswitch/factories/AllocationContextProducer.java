package de.heidelberg.pvs.diego.collectionswitch.factories;

import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;

import de.heidelberg.pvs.diego.collectionswitch.annotations.Switch;
import de.heidelberg.pvs.diego.collectionswitch.manager.ContextManager;

@SessionScoped
public class AllocationContextProducer {

	private Switch name;
	
	@Produces
	@SessionScoped
	public ListSwitch producesList() {
		return ContextManager.createList(name);
	}

}
