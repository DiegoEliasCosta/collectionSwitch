package de.heidelberg.pvs.diego.collections_online_adapter.automata;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
	AdaptiveArrayListAutomataStateTest.class,
	ProactiveArrayListAutomataStateTest.class,
	AdaptiveHashMapAutomataStateTest.class,
	ProactiveHashMapAutomataStateTest.class,
	AdaptiveHashSetAutomataStateTest.class
})
public class AutomataTestSuite {

}
