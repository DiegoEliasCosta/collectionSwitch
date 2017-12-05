package de.heidelberg.pvs.diego.collectionswitch.context;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	ListEmpiricalAllocationContextTest.class,
	SetEmpiricalAllocationContextTest.class,
	MapEmpiricalAllocationContextTest.class,
	ListCollectionTypeTest.class,
	SetCollectionTypeTest.class,
	MapCollectionTypeTest.class
})
public class ContextTestSuite {

}
