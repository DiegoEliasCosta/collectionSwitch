package de.heidelberg.pvs.diego.collectionswitch.context;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.collections.impl.set.mutable.UnifiedSet;

import de.heidelberg.pvs.diego.collectionswitch.adaptive.AdaptiveSet;
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

		switch (this) {

		case JDK_HASHSET:
			return new HashSet<E>(initialCapacity);

		case KOLOBOKE_HASHSET:
			return HashObjSets.newMutableSet(initialCapacity);

		case JDK_LINKEDHASHSET:
			return new LinkedHashSet<E>(initialCapacity);

		case FASTUTILS_HASHSET:
			return new THashSet<E>(initialCapacity);

		case GSCOLLECTIONS_UNIFIEDSET:
			return new UnifiedSet<E>(initialCapacity);

		case NLP_ARRAYSET:
			return new ArraySet<E>(initialCapacity);

			// case ONLINEADAPTER_ARRAYSET:
			// return new ArraySet_Naive(initialCapacity);

		case ONLINEADAPTER_ADAPTIVESET:
			return new AdaptiveSet<E>(initialCapacity);

		default:
			return new HashSet<E>(initialCapacity);
		}

	}

	public <E> Set<E> createSet() {


		switch (this) {

		case JDK_HASHSET:
			return new HashSet<E>();

		case KOLOBOKE_HASHSET:
			return HashObjSets.newMutableSet();

		case JDK_LINKEDHASHSET:
			return new LinkedHashSet<E>();

		case FASTUTILS_HASHSET:
			return new THashSet<E>();

		case GSCOLLECTIONS_UNIFIEDSET:
			return new UnifiedSet<E>();

		case NLP_ARRAYSET:
			return new ArraySet<E>();

			// case ONLINEADAPTER_ARRAYSET:
			// return new ArraySet_Naive(initialCapacity);

		case ONLINEADAPTER_ADAPTIVESET:
			return new AdaptiveSet<E>();

		default:
			return new HashSet<E>();
		}

	}

	
	public <E> Set<E> createSet(Collection<? extends E> setToCopy) {

		switch (this) {

		case JDK_HASHSET:
			return new HashSet<E>(setToCopy);

		case KOLOBOKE_HASHSET:
			return HashObjSets.newMutableSet(setToCopy);

		case JDK_LINKEDHASHSET:
			return new LinkedHashSet<E>(setToCopy);

		case FASTUTILS_HASHSET:
			return new THashSet<E>(setToCopy);

		case GSCOLLECTIONS_UNIFIEDSET:
			return new UnifiedSet<E>(setToCopy);

		case NLP_ARRAYSET:
			Set<E> set2 = new ArraySet<E>();
			set2.addAll(setToCopy);
			return set2;

			// case ONLINEADAPTER_ARRAYSET:
			// return new ArraySet_Naive(initialCapacity);

		case ONLINEADAPTER_ADAPTIVESET:
			return new AdaptiveSet<E>(setToCopy);

		default:
			return new HashSet<E>(setToCopy);
		}

	}
}
