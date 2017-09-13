package de.heidelberg.pvs.diego.collections_online_adapter.context;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	InitialCapacityListAllocationContextTest.class,
	InitialCapacityMapAllocationContextTest.class,
	InitialCapacitySetAllocationContextTest.class,
	ListEmpiricalAllocationContextTest.class,
	SetEmpiricalAllocationContextTest.class,
	MapEmpiricalAllocationContextTest.class,
	ListCollectionTypeTest.class,
	SetCollectionTypeTest.class,
	MapCollectionTypeTest.class
})
public class ContextTestSuite {

}
