package com.test.fx.test.util;

public class QuickSort {
	public void quickSort(int low, int high, char[] characters) {
		int i = low, j = high;
		int pivot = characters[low + (high - low) / 2];
		while (i < j) {
			while (characters[i] < pivot) {
				i++;
			}
			while (characters[j] > pivot) {
				j--;
			}
			if (i <= j) {
				exchange(i, j, characters);
				i++;
				j--;
			}
		}
		if (low < j) {
			quickSort(low, j, characters);
		}
		if (i < high) {
			quickSort(i, high, characters);
		}
	}

	private void exchange(int i, int j, char[] characters) {
		char tmp = characters[i];
		characters[i] = characters[j];
		characters[j] = tmp;
	}

}
