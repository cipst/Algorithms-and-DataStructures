/**
 * @file sorting.h
 * @author Stefano Cipolletta
 * @version v0.1
 * */

#ifndef SORTING_H_adsnoiqpfmoa
#define SORTING_H_adsnoiqpfmoa

#include "generic_array.h"

/**
 * Orders the array using the quick sort algorithm
 * @param unsorted_array unsorted array
 * @param precedence pointer to a function which determines the precedence relation between the array elements.
 * It returns 1 iff the first element is greater than the second.
 * It returns -1 iff the first element is smaller than the second.
 * It return 0 iff the first and the second elements are equal.
 * @pre GenericArray cannot be NULL
 * @pre *precedence must be a reference to a valid function
 * @author Stefano Cipolletta
 * */
void quick_sort(GenericArray* unsorted_array, int (*precedence)(void*, void*));

/**
 * Hearth of the QuickSort algorithm
 * @param unsorted_array unsorted array
 * @param precedence pointer to a function which determines the precedence relation between the array elements.
 * It returns 1 iff the first element is greater than the second.
 * It returns -1 iff the first element is smaller than the second.
 * It return 0 iff the first and the second elements are equal.
 * @param first index of the first element in the range to partition
 * @param last index of the last element in the range to partition
 * @author Stefano Cipolletta
 * */
unsigned long partition(GenericArray* unsorted_array, int (*precedence)(void*, void*), unsigned long first, unsigned long last);

void swap(GenericArray* unsorted_array, unsigned long index1, unsigned long index2);

#endif /*SORTING_H_adsnoiqpfmoa*/