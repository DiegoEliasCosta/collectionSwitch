package de.heidelberg.pvs.diego.collections_online_adapter.phantom;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.google.common.base.FinalizablePhantomReference;
import com.google.common.base.FinalizableReferenceQueue;

import jlibs.core.lang.RuntimeUtil;

public class PhantomReferenceTest {
	
	private static final int _10000 = 10000;

	public class PrintPhantomReference extends FinalizablePhantomReference<List<?>> {

		
		protected PrintPhantomReference(List<?> referent, FinalizableReferenceQueue queue) {
			super(referent, queue);
		}

		@Override
		public void finalizeReferent() {
			System.out.println(String.format("LIST FINALIZED"));
			
		}
		
	}
	
	@Test
	public void testSimpleFinalization() throws InterruptedException {
		List<Integer> one = new ArrayList<Integer>(_10000);
		
		FinalizableReferenceQueue rq = new FinalizableReferenceQueue();
		PrintPhantomReference printPhantomReference = new PrintPhantomReference(one, rq);
		
		for(int i = 0; i < _10000; i++) {
			one.add(i);
		}
		
		one = null;
		one = null;
		RuntimeUtil.gc();
		Thread.sleep(10000);
		
		

	}

}
