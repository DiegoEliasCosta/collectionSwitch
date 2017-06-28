package de.heidelberg.pvs.diego.collections_online_adapter.context;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
	MapActiveAllocationContextImplTest.class,
	MapPassiveAllocationContextImplTest.class,
	SetActiveAllocationContextImplTest.class,
	SetPassiveAllocationContextImplTest.class
	
})
public class ContextTestSuite {

}
