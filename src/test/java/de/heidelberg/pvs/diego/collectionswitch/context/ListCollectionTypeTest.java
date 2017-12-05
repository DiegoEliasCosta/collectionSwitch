package de.heidelberg.pvs.diego.collectionswitch.context;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import de.heidelberg.pvs.diego.collectionswitch.adaptive.AdaptiveList;
import de.heidelberg.pvs.diego.collectionswitch.context.ListCollectionType;
import de.heidelberg.pvs.diego.collectionswitch.custom.lists.HashArrayList;

public class ListCollectionTypeTest {

	@Test
	public void testSimpleType() throws Exception {

		ListCollectionType type = ListCollectionType.JDK_ARRAYLIST;
		List list = type.createList();
		Assert.assertTrue(list instanceof ArrayList);

		type = ListCollectionType.JDK_LINKEDLIST;
		list = type.createList();
		Assert.assertTrue(list instanceof LinkedList);

		type = ListCollectionType.ONLINEADAPTER_ADAPTIVELIST;
		list = type.createList();
		Assert.assertTrue(list instanceof AdaptiveList);

		type = ListCollectionType.ONLINEADAPTER_HASHARRAYLIST;
		list = type.createList();
		Assert.assertTrue(list instanceof HashArrayList);

	}

}
