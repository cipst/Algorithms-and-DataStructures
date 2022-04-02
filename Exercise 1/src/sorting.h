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
 * @param compare pointer to a function which determines the precedence relation between the array elements.
 * It returns 1 iff the first element is greater than the second.
 * It returns -1 iff the first element is smaller than the second.
 * It return 0 iff the first and the second elements are equal.
 * @pre GenericArray cannot be NULL
 * @pre *compare must be a reference to a valid function
 * @author Stefano Cipolletta
 * */
void quick_sort(GenericArray* unsorted_array, int (*compare)(void*, void*));

#endif /*SORTING_H_adsnoiqpfmoa*/