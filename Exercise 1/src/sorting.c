/**
 * @file sorting.c
 * @author Stefano Cipolletta
 * @version v0.1
 * */

#include "sorting.h"

#include <stdio.h>
#include <stdlib.h>

/**
 * quick_sort() core function.
 * It helps to pass start and finish index of the unsorted_array to be sort
 * @param unsorted_array unsorted array
 * @param compare pointer to a function which determines the precedence relation between the array elements.
 * It returns 1 iff the first element is greater than the second.
 * It returns -1 iff the first element is smaller than the second.
 * It returns 0 iff the first and the second elements are equal.
 * @param first index of the first element in the unsorted array
 * @param last index of the last element in the unsorted array
 * @return 1 on success, -1 otherwise
 * @author Stefano Cipolletta
 * */
static void quick_sort_core(GenericArray* unsorted_arrat, int (*compare)(void*, void*), unsigned long first, unsigned long last);

/**
 * Partition the unsorted_array.
 * Move all the elements less than the pivot on the left of the pivot and all the elements greater than the pivot on the right.
 * @param unsorted_array unsorted array
 * @param compare pointer to a function which determines the precedence relation between the array elements.
 * It returns 1 iff the first element is greater than the second.
 * It returns -1 iff the first element is smaller than the second.
 * It returns 0 iff the first and the second elements are equal.
 * @param first index of the first element in the range to partition
 * @param last index of the last element in the range to partition
 * @return the pivot index
 * @author Stefano Cipolletta
 * */
static unsigned long partition(GenericArray* unsorted_array, int (*compare)(void*, void*), unsigned long first, unsigned long last);

void quick_sort(GenericArray* unsorted_array, int (*compare)(void*, void*)) {
    if (unsorted_array == NULL) {
        fprintf(stderr, "quick_sort(): unsorted_array parameter is NULL.\n");
        exit(EXIT_FAILURE);
    }
    if (compare == NULL) {
        fprintf(stderr, "quick_sort(): compare function is NULL.\n");
        exit(EXIT_FAILURE);
    }
    unsigned long n = generic_array_size(unsorted_array);
    if (n > 1)
        quick_sort_core(unsorted_array, compare, 0, n - 1);
}

static void quick_sort_core(GenericArray* unsorted_array, int (*compare)(void*, void*), unsigned long first, unsigned long last) {
    if ((last - first) > 1) {  // at least 2 elements in the unsorted array (with 1 element the array is ordered)

        unsigned long p = partition(unsorted_array, compare, first, last);

        if (p > first + 1)  // at least 2 elements before the pivot
            quick_sort_core(unsorted_array, compare, first, p - 1);

        if (p < last - 1)  // at least 2 elements after the pivot
            quick_sort_core(unsorted_array, compare, p + 1, last);
    }
}

static unsigned long partition(GenericArray* unsorted_array, int (*compare)(void*, void*), unsigned long first, unsigned long last) {
    unsigned long i = first + 1;
    unsigned long j = (last - first);

    while (i <= j) {
        if ((*compare)(generic_array_get(unsorted_array, i), generic_array_get(unsorted_array, first)) <= 0) {  // unsorted_array[i] <= unsorted_array[p]
            ++i;
        } else if ((*compare)(generic_array_get(unsorted_array, j), generic_array_get(unsorted_array, first)) > 0) {  // unsorted_array[j] > unsorted_array[p]
            --j;
        } else {
            swap(unsorted_array, i, j);
            ++i;
            --j;
        }
    }
    swap(unsorted_array, first, j);
    return j;
}

int swap(GenericArray* unsorted_array, unsigned long index1, unsigned long index2) {
    if (unsorted_array == NULL) {
        // fprintf(stderr, "quick_sort(): unsorted_array parameter is NULL.\n");
        return -1;
    }
    if (index1 >= generic_array_size(unsorted_array)) {
        // fprintf(stderr, "swap(%lu): index out of bound.\n", index1);
        return -1;
    }
    if (index2 >= generic_array_size(unsorted_array)) {
        // fprintf(stderr, "swap(%lu): index out of bound.\n", index2);
        return -1;
    }
    void* aux = generic_array_get(unsorted_array, index1);
    generic_array_update_at(unsorted_array, generic_array_get(unsorted_array, index2), index1);
    generic_array_update_at(unsorted_array, aux, index2);
    return 1;
}