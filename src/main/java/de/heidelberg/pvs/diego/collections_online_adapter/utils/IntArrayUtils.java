package de.heidelberg.pvs.diego.collections_online_adapter.utils;

import java.util.Arrays;

public class IntArrayUtils {
	
	public static int calculateMedian(int[] array) {
		
		Arrays.sort(array);
		return array[array.length / 2];
		
	}
	
	public static double calculateMean(int[] array) {
		
		int summed = 0;
		for (int i = 0; i < array.length; i++) {
			summed += array[i];
		}
		return summed / (double) array.length;
		
	}

	public static double calculateStandardDeviation(int[] array) {
		
		double mean = calculateMean(array);
		
		double sd = 0;
		for (int i = 0; i < array.length; i++) {
		    sd = (sd + Math.pow(array[i] - mean, 2));
		}
		
		return sd;
		
	}
	
	public static int mode(final int[] array) {
		
	    int maxKey = 0;
	    int maxCounts = 0;

	    int[] counts = new int[array.length];

	    for (int i=0; i < array.length; i++) {
	        counts[array[i]]++;
	        if (maxCounts < counts[array[i]]) {
	            maxCounts = counts[array[i]];
	            maxKey = array[i];
	        }
	    }
	    return maxKey;
	}
	
}
