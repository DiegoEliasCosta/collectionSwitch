package de.heidelberg.pvs.diego.collections_online_adapter.factories;

import java.util.List;

import de.heidelberg.pvs.diego.collections_online_adapter.context.ListCollectionType;
import de.heidelberg.pvs.diego.collections_online_adapter.context.MapCollectionType;
import de.heidelberg.pvs.diego.collections_online_adapter.context.SetCollectionType;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.lists.ListPerformanceModel;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.maps.MapPerformanceModel;
import de.heidelberg.pvs.diego.collections_online_adapter.optimizers.sets.SetPerformanceModel;

public class PerformanceModelFactory {

	// TODO: Hard-coded performance model factory
	// This should be implemented as an input

	public static List<ListPerformanceModel> buildListPerformanceModelsTime() {

		// FIXME: Bad implementation - this should return a list of performance models NOT add them into the evaluator
		List<ListPerformanceModel> performanceModel = new java.util.ArrayList<ListPerformanceModel>();

		// AUTOMATICALLY GENERATED - VARIABLE TIME
		ListPerformanceModel ArrayListTime = new ListPerformanceModel(ListCollectionType.JDK_ARRAYLIST,
				new double[] { -68.142119, 7.590974, -0.005205 }, new double[] { 29.950540, 0.518341, -0.000141 },
				new double[] { 5.550586, 4.244929, -0.000151 });
		performanceModel.add(ArrayListTime);
		// AUTOMATICALLY GENERATED - VARIABLE TIME
		ListPerformanceModel LinkedListTime = new ListPerformanceModel(ListCollectionType.JDK_LINKEDLIST,
				new double[] { 41.580798, 5.373206, 0.001918 }, new double[] { 29.367605, 0.924321, -0.000051 },
				new double[] { -2.464303, 4.757063, -0.000942 });
		performanceModel.add(LinkedListTime);
		// AUTOMATICALLY GENERATED - VARIABLE TIME
		ListPerformanceModel AdaptiveListTime = new ListPerformanceModel(ListCollectionType.ONLINEADAPTER_ADAPTIVELIST,
				new double[] { -749.209976, 46.435289, -0.011771 }, new double[] { 38.666151, 0.055588, -0.000107 },
				new double[] { 6.983691, 4.373944, 0.000872 });
		performanceModel.add(AdaptiveListTime);
		// AUTOMATICALLY GENERATED - VARIABLE TIME
		ListPerformanceModel HashArrayListTime = new ListPerformanceModel(
				ListCollectionType.ONLINEADAPTER_HASHARRAYLIST, new double[] { -95.816045, 42.094208, -0.011714 },
				new double[] { 29.264840, 0.094781, -0.000152 }, new double[] { 2.657610, 4.177008, 0.000677 });
		performanceModel.add(HashArrayListTime);

		return performanceModel;
		
	}

	public static List<ListPerformanceModel> buildListPerformanceModelsAllocation() {

		List<ListPerformanceModel> performanceModel = new java.util.ArrayList<ListPerformanceModel>();
		// AUTOMATICALLY GENERATED - VARIABLE ·GC.ALLOC.RATE.NORM
		ListPerformanceModel ArrayListgcallocratenorm = new ListPerformanceModel(ListCollectionType.JDK_ARRAYLIST,
				new double[] { -119.004257, 19.299468, -0.009556 }, new double[] { -0.856593, 0.029089, -0.000005 },
				new double[] { 0.000006, 0.000006, -0.000000 });
		performanceModel.add(ArrayListgcallocratenorm);
		// AUTOMATICALLY GENERATED - VARIABLE ·GC.ALLOC.RATE.NORM
		ListPerformanceModel LinkedListgcallocratenorm = new ListPerformanceModel(ListCollectionType.JDK_LINKEDLIST,
				new double[] { 32.000062, 24.000007, 0.000000 }, new double[] { -0.856598, 0.029089, -0.000005 },
				new double[] { -0.000006, 0.000006, -0.000000 });
		performanceModel.add(LinkedListgcallocratenorm);
		// AUTOMATICALLY GENERATED - VARIABLE ·GC.ALLOC.RATE.NORM
		ListPerformanceModel AdaptiveListgcallocratenorm = new ListPerformanceModel(
				ListCollectionType.ONLINEADAPTER_ADAPTIVELIST, new double[] { -1228.127328, 77.053782, -0.048796 },
				new double[] { -0.856823, 0.029098, -0.000005 }, new double[] { -0.648953, 0.001387, 0.000146 });
		performanceModel.add(AdaptiveListgcallocratenorm);
		// AUTOMATICALLY GENERATED - VARIABLE ·GC.ALLOC.RATE.NORM
		ListPerformanceModel HashArrayListgcallocratenorm = new ListPerformanceModel(
				ListCollectionType.ONLINEADAPTER_HASHARRAYLIST, new double[] { -232.205056, 71.000311, -0.043266 },
				new double[] { -0.856819, 0.029097, -0.000005 }, new double[] { -0.000016, 0.000006, -0.000000 });
		performanceModel.add(HashArrayListgcallocratenorm);

		return performanceModel;
		
	}

	public static List<SetPerformanceModel> buildSetsPerformanceModelTime() {

		List<SetPerformanceModel> performanceModel = new java.util.ArrayList<SetPerformanceModel>();

		// AUTOMATICALLY GENERATED - VARIABLE TIME
		SetPerformanceModel FastUtilHashSetTime = new SetPerformanceModel(SetCollectionType.FASTUTILS_HASHSET,
				new double[] { -316.145596, 25.689791, -0.006822 }, new double[] { 27.394889, 0.073665, -0.000111 },
				new double[] { -14.111811, 5.793781, -0.001714 });
		performanceModel.add(FastUtilHashSetTime);
		// AUTOMATICALLY GENERATED - VARIABLE TIME
		SetPerformanceModel UnifiedSetTime = new SetPerformanceModel(SetCollectionType.GSCOLLECTIONS_UNIFIEDSET,
				new double[] { -549.835979, 41.040946, -0.022664 }, new double[] { 33.591044, 0.050408, -0.000069 },
				new double[] { -75.086755, 11.573696, -0.007554 });
		performanceModel.add(UnifiedSetTime);
		// AUTOMATICALLY GENERATED - VARIABLE TIME
		SetPerformanceModel JDKHashSetTime = new SetPerformanceModel(SetCollectionType.JDK_HASHSET,
				new double[] { -147.563815, 25.882185, -0.003529 }, new double[] { 29.817089, 0.066021, -0.000100 },
				new double[] { -11.468034, 5.131184, 0.007571 });
		performanceModel.add(JDKHashSetTime);
		// AUTOMATICALLY GENERATED - VARIABLE TIME
		SetPerformanceModel LinkedHashSetTime = new SetPerformanceModel(SetCollectionType.JDK_LINKEDHASHSET,
				new double[] { -40.506715, 23.358312, 0.003296 }, new double[] { 29.814675, 0.071508, -0.000115 },
				new double[] { 8.394560, 3.864150, 0.000808 });
		performanceModel.add(LinkedHashSetTime);
		// AUTOMATICALLY GENERATED - VARIABLE TIME
		SetPerformanceModel HashSetTime = new SetPerformanceModel(SetCollectionType.KOLOBOKE_HASHSET,
				new double[] { -341.262428, 33.228792, -0.024395 }, new double[] { 29.005397, 0.049525, -0.000070 },
				new double[] { -23.884863, 6.504564, -0.001198 });
		performanceModel.add(HashSetTime);
		// AUTOMATICALLY GENERATED - VARIABLE TIME
		SetPerformanceModel ArraySetTime = new SetPerformanceModel(SetCollectionType.NLP_ARRAYSET,
				new double[] { 141.540041, 6.883808, 0.444936 }, new double[] { 30.044110, 0.555977, -0.000226 },
				new double[] { -9.160581, 4.783920, 0.000294 });
		performanceModel.add(ArraySetTime);
		// AUTOMATICALLY GENERATED - VARIABLE TIME
		SetPerformanceModel AdaptiveSetTime = new SetPerformanceModel(SetCollectionType.ONLINEADAPTER_ADAPTIVESET,
				new double[] { -96.650564, 38.827774, -0.035870 }, new double[] { 37.416124, 0.012403, -0.000022 },
				new double[] { -32.038533, 6.133033, 0.002870 });
		performanceModel.add(AdaptiveSetTime);

		return performanceModel;
		
	}

	public static List<SetPerformanceModel> buildSetsPerformanceModelAllocation() {

		List<SetPerformanceModel> performanceModel = new java.util.ArrayList<SetPerformanceModel>();
		
		// AUTOMATICALLY GENERATED - VARIABLE ·GC.ALLOC.RATE.NORM
		SetPerformanceModel FastUtilHashSetgcallocratenorm = new SetPerformanceModel(SetCollectionType.FASTUTILS_HASHSET, new double[]{-227.244994,26.432850,-0.017358}, new double[]{-0.856861,0.029099,-0.000005}, new double[]{-0.000023,0.000008,-0.000000});
		performanceModel.add(FastUtilHashSetgcallocratenorm);
		// AUTOMATICALLY GENERATED - VARIABLE ·GC.ALLOC.RATE.NORM
		SetPerformanceModel UnifiedSetgcallocratenorm = new SetPerformanceModel(SetCollectionType.GSCOLLECTIONS_UNIFIEDSET, new double[]{-381.574355,48.384927,-0.024525}, new double[]{-0.856836,0.029098,-0.000005}, new double[]{-3.986774,0.174788,-0.000351});
		performanceModel.add(UnifiedSetgcallocratenorm);
		// AUTOMATICALLY GENERATED - VARIABLE ·GC.ALLOC.RATE.NORM
		SetPerformanceModel HashSetgcallocratenorm = new SetPerformanceModel(SetCollectionType.JDK_HASHSET, new double[]{-219.090428,59.013734,-0.018427}, new double[]{-0.856848,0.029098,-0.000005}, new double[]{-0.811162,0.001731,0.000183});
		performanceModel.add(HashSetgcallocratenorm);
		// AUTOMATICALLY GENERATED - VARIABLE ·GC.ALLOC.RATE.NORM
		SetPerformanceModel LinkedHashSetgcallocratenorm = new SetPerformanceModel(SetCollectionType.JDK_LINKEDHASHSET, new double[]{-211.090115,67.013724,-0.018427}, new double[]{-0.856840,0.029098,-0.000005}, new double[]{-0.648969,0.001387,0.000146});
		performanceModel.add(LinkedHashSetgcallocratenorm);
		// AUTOMATICALLY GENERATED - VARIABLE ·GC.ALLOC.RATE.NORM
		SetPerformanceModel KolobokeHashSetgcallocratenorm = new SetPerformanceModel(SetCollectionType.KOLOBOKE_HASHSET, new double[]{-251.090774,27.013745,-0.018427}, new double[]{-0.856848,0.029098,-0.000005}, new double[]{-0.000003,0.000008,0.000000});
		performanceModel.add(KolobokeHashSetgcallocratenorm);
		// AUTOMATICALLY GENERATED - VARIABLE ·GC.ALLOC.RATE.NORM
		SetPerformanceModel ArraySetgcallocratenorm = new SetPerformanceModel(SetCollectionType.NLP_ARRAYSET, new double[]{-103.003898,19.299470,-0.009555}, new double[]{-0.854475,0.029076,-0.000005}, new double[]{-0.648959,0.001387,0.000146});
		performanceModel.add(ArraySetgcallocratenorm);
		// AUTOMATICALLY GENERATED - VARIABLE ·GC.ALLOC.RATE.NORM
		SetPerformanceModel AdaptiveSetgcallocratenorm = new SetPerformanceModel(SetCollectionType.ONLINEADAPTER_ADAPTIVESET, new double[]{-193.679351,30.844353,-0.024542}, new double[]{-0.856838,0.029098,-0.000005}, new double[]{-0.811248,0.001734,0.000183});
		performanceModel.add(AdaptiveSetgcallocratenorm);

		return performanceModel;
		
	}

	public static List<MapPerformanceModel> buildMapsPerformanceModelTime() {

		List<MapPerformanceModel> performanceModel = new java.util.ArrayList<MapPerformanceModel>();

		// AUTOMATICALLY GENERATED - VARIABLE TIME
		MapPerformanceModel UnifiedMapsTime = new MapPerformanceModel(MapCollectionType.GSCOLLECTIONS_UNIFIEDMAP, new double[]{-541.264229,54.608505,-0.018659}, new double[]{32.262985,0.020520,-0.000026}, new double[]{-36.663724,13.466403,-0.001900});
		performanceModel.add(UnifiedMapsTime);
		// AUTOMATICALLY GENERATED - VARIABLE TIME
		MapPerformanceModel ArrayMapTime = new MapPerformanceModel(MapCollectionType.GOOGLE_ARRAYMAP, new double[]{-1734.855889,68.318187,0.354878}, new double[]{30.340651,0.466380,-0.000087}, new double[]{-3.737298,6.321758,-0.000628});
		performanceModel.add(ArrayMapTime);
		// AUTOMATICALLY GENERATED - VARIABLE TIME
		MapPerformanceModel HashMapTime = new MapPerformanceModel(MapCollectionType.JDK_HASHMAP, new double[]{-159.526456,27.831369,0.000357}, new double[]{29.878743,-0.005077,0.000019}, new double[]{26.746037,5.452778,0.001511});
		performanceModel.add(HashMapTime);
		// AUTOMATICALLY GENERATED - VARIABLE TIME
		MapPerformanceModel LinkedHashMapTime = new MapPerformanceModel(MapCollectionType.JDK_LINKEDHASHMAP, new double[]{-188.400441,31.811119,-0.007272}, new double[]{29.628054,0.004791,-0.000006}, new double[]{-12.794264,4.445188,-0.000366});
		performanceModel.add(LinkedHashMapTime);
		// AUTOMATICALLY GENERATED - VARIABLE TIME
		MapPerformanceModel KolobokeHashMapTime = new MapPerformanceModel(MapCollectionType.KOLOBOKE_HASHMAP, new double[]{-236.858702,33.706754,-0.008767}, new double[]{26.827767,0.024344,-0.000035}, new double[]{-121.625078,20.183441,-0.004244});
		performanceModel.add(KolobokeHashMapTime);
		// AUTOMATICALLY GENERATED - VARIABLE TIME
		MapPerformanceModel NayukiCompactHashMapTime = new MapPerformanceModel(MapCollectionType.NAYUKI_COMPACTHASHMAP, new double[]{19.138060,78.018373,0.018487}, new double[]{44.214786,0.085242,-0.000085}, new double[]{125.838428,32.534349,-0.005407});
		performanceModel.add(NayukiCompactHashMapTime);
		
		return performanceModel;
		

	}

	public static List<MapPerformanceModel> buildMapsPerformanceModelAllocation() {

		List<MapPerformanceModel> performanceModel = new java.util.ArrayList<MapPerformanceModel>();

		// AUTOMATICALLY GENERATED - VARIABLE ·GC.ALLOC.RATE.NORM
		MapPerformanceModel UnifiedMapsgcallocratenorm = new MapPerformanceModel(MapCollectionType.GSCOLLECTIONS_UNIFIEDMAP, new double[]{-843.394386,83.392457,-0.048719}, new double[]{0.000043,0.000000,-0.000000}, new double[]{31.999958,24.000018,-0.000000});
		performanceModel.add(UnifiedMapsgcallocratenorm);
		// AUTOMATICALLY GENERATED - VARIABLE ·GC.ALLOC.RATE.NORM
		MapPerformanceModel ArrayMapgcallocratenorm = new MapPerformanceModel(MapCollectionType.GOOGLE_ARRAYMAP, new double[]{354.889708,18.467617,0.028073}, new double[]{0.000042,0.000001,-0.000000}, new double[]{-0.000035,24.000009,-0.000000});
		performanceModel.add(ArrayMapgcallocratenorm);
		// AUTOMATICALLY GENERATED - VARIABLE ·GC.ALLOC.RATE.NORM
		MapPerformanceModel HashMapgcallocratenorm = new MapPerformanceModel(MapCollectionType.JDK_HASHMAP, new double[]{-235.090512,59.013739,-0.018427}, new double[]{0.000039,-0.000000,0.000000}, new double[]{0.000025,0.000008,0.000000});
		performanceModel.add(HashMapgcallocratenorm);
		// AUTOMATICALLY GENERATED - VARIABLE ·GC.ALLOC.RATE.NORM
		MapPerformanceModel LinkedHashMapgcallocratenorm = new MapPerformanceModel(MapCollectionType.JDK_LINKEDHASHMAP, new double[]{-227.090512,67.013745,-0.018427}, new double[]{0.000040,0.000000,-0.000000}, new double[]{0.000004,0.000006,0.000000});
		performanceModel.add(LinkedHashMapgcallocratenorm);
		// AUTOMATICALLY GENERATED - VARIABLE ·GC.ALLOC.RATE.NORM
		MapPerformanceModel KolobokeHashMapgcallocratenorm = new MapPerformanceModel(MapCollectionType.KOLOBOKE_HASHMAP, new double[]{-556.696152,53.470694,-0.036075}, new double[]{0.000037,0.000000,-0.000000}, new double[]{39.999850,40.000027,-0.000000});
		performanceModel.add(KolobokeHashMapgcallocratenorm);
		// AUTOMATICALLY GENERATED - VARIABLE ·GC.ALLOC.RATE.NORM
		MapPerformanceModel NayukiCompactHashMapgcallocratenorm = new MapPerformanceModel(MapCollectionType.NAYUKI_COMPACTHASHMAP, new double[]{5.655551,55.839487,-0.003252}, new double[]{22.640998,0.100559,-0.000174}, new double[]{407.283902,53.565156,0.000359});
		performanceModel.add(NayukiCompactHashMapgcallocratenorm);
		
		return performanceModel;
		
	}

}
