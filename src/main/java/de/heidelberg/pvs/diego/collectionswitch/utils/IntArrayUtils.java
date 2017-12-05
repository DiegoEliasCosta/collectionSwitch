package de.heidelberg.pvs.diego.collectionswitch.utils;

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

		return Math.sqrt(sd);

	}

	public static int calculateMode(final int[] a) {

		int maxValue = 0;
		int maxCount = 0;

		for (int i = 0; i < a.length; ++i) {
			int count = 0;
			for (int j = 0; j < a.length; ++j) {
				if (a[j] == a[i])
					++count;
			}
			if (count > maxCount) {
				maxCount = count;
				maxValue = a[i];
			}
		}

		return maxValue;
	}
	
	public static int calculateModeCategoryWithThreshold(final int[] a, int convergenceRate, int category) {
		
		int maxValue = 0;
		int maxCount = 0;

		for (int i = 0; i < a.length; ++i) {
			int count = 0;
			int bini = a[i] / category;
			for (int j = 0; j < a.length; ++j) {
				int bin = a[j] / category;
				if (bin == bini)
					++count;
			}
			if (count > maxCount) {
				maxCount = count;
				maxValue = bini;
			}
		}

		if(maxCount >= convergenceRate) {
			return maxValue * category + category;
		} else {
			return -1;
		}
		
	}

	/**
	 * 
	 * 
	 * @param array
	 * @param convergenceRate
	 * @return
	 */
	public static int calculateModeWithThrehsold(int[] array, int convergenceRate) {
		
		int maxValue = 0;
		int maxCount = 0;

		for (int i = 0; i < array.length; ++i) {
			int count = 0;
			for (int j = 0; j < array.length; ++j) {
				if (array[j] == array[i])
					++count;
			}
			if (count > maxCount) {
				maxCount = count;
				maxValue = array[i];
			}
		}

		return maxCount >= convergenceRate? maxValue : -1;
	}

}
