package de.heidelberg.pvs.diego.collections_online_adapter.context;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.collections.impl.set.mutable.UnifiedSet;

import de.heidelberg.pvs.diego.collections_online_adapter.adaptive.AdaptiveSet;
import edu.stanford.nlp.util.ArraySet;
import gnu.trove.set.hash.THashSet;
import net.openhft.koloboke.collect.set.hash.HashObjSets;

public enum SetCollectionType {

	JDK_HASHSET, JDK_LINKEDHASHSET,

	FASTUTILS_HASHSET, GSCOLLECTIONS_UNIFIEDSET, KOLOBOKE_HASHSET,

	NLP_ARRAYSET,
	// ONLINEADAPTER_ARRAYSET,
	ONLINEADAPTER_ADAPTIVESET;

	public <E> Set<E> createSet(int initialCapacity) {

		Set<E> set;

		switch (this) {

		case JDK_HASHSET:
			set = new HashSet<E>(initialCapacity);

		case KOLOBOKE_HASHSET:
			set = HashObjSets.newMutableSet(initialCapacity);

		case JDK_LINKEDHASHSET:
			set = new LinkedHashSet<E>(initialCapacity);

		case FASTUTILS_HASHSET:
			set = new THashSet<E>(initialCapacity);

		case GSCOLLECTIONS_UNIFIEDSET:
			set = new UnifiedSet<E>(initialCapacity);

		case NLP_ARRAYSET:
			set = new ArraySet<E>(initialCapacity);

			// case ONLINEADAPTER_ARRAYSET:
			// set = new ArraySet_Naive(initialCapacity);

		case ONLINEADAPTER_ADAPTIVESET:
			set = new AdaptiveSet<E>(initialCapacity);

		default:
			set = new HashSet<>(initialCapacity);
		}

		return set;
	}

	public <E> Set<E> createSet() {

		Set<E> set;

		switch (this) {

		case JDK_HASHSET:
			set = new HashSet<E>();

		case KOLOBOKE_HASHSET:
			set = HashObjSets.newMutableSet();

		case JDK_LINKEDHASHSET:
			set = new LinkedHashSet<E>();

		case FASTUTILS_HASHSET:
			set = new THashSet<E>();

		case GSCOLLECTIONS_UNIFIEDSET:
			set = new UnifiedSet<E>();

		case NLP_ARRAYSET:
			set = new ArraySet<E>();

			// case ONLINEADAPTER_ARRAYSET:
			// set = new ArraySet_Naive(initialCapacity);

		case ONLINEADAPTER_ADAPTIVESET:
			set = new AdaptiveSet<E>();

		default:
			set = new HashSet<>();
		}

		return set;
	}

	
	public <E> Set<E> createSet(Collection<? extends E> setToCopy) {

		Set<E> set;

		switch (this) {

		case JDK_HASHSET:
			set = new HashSet<E>(setToCopy);

		case KOLOBOKE_HASHSET:
			set = HashObjSets.newMutableSet(setToCopy);

		case JDK_LINKEDHASHSET:
			set = new LinkedHashSet<E>(setToCopy);

		case FASTUTILS_HASHSET:
			set = new THashSet<E>(setToCopy);

		case GSCOLLECTIONS_UNIFIEDSET:
			set = new UnifiedSet<E>(setToCopy);

		case NLP_ARRAYSET:
			set = new ArraySet<E>();

			// case ONLINEADAPTER_ARRAYSET:
			// set = new ArraySet_Naive(initialCapacity);

		case ONLINEADAPTER_ADAPTIVESET:
			set = new AdaptiveSet<E>(setToCopy);

		default:
			set = new HashSet<>(setToCopy);
		}

		return set;
	}
}
