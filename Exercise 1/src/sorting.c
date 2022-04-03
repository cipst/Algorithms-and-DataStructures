/**
 * @file sorting.c
 * @author Stefano Cipolletta
 * @version v0.1
 * */

#include "sorting.h"

#include <stdio.h>
#include <stdlib.h>

/**
 * Wrapper function for quick_sort().
 * It helps to pass start and finish index of the unsorted_array to be sort
 * @param unsorted_array unsorted array
 * @param compare pointer to a function which determines the precedence relation between the array elements.
 * It returns 1 iff the first element is greater than the second.
 * It returns -1 iff the first element is smaller than the second.
 * It returns 0 iff the first and the second elements are equal.
 * @param first index of the first element in the unsorted array
 * @param last index of the last element in the unsorted array
 * @author Stefano Cipolletta
 * */
static void quick_sort_wrap(GenericArray* unsorted_arrat, int (*compare)(void*, void*), unsigned long first, unsigned long last);

/**
 * Partition the unsorted_array.
 * Move all the elements less than the pivot on the left of the pivot and all the elements greater than the pivot on the right.
 * Then return the pivot index;
 * @param unsorted_array unsorted array
 * @param compare pointer to a function which determines the precedence relation between the array elements.
 * It returns 1 iff the first element is greater than the second.
 * It returns -1 iff the first element is smaller than the second.
 * It returns 0 iff the first and the second elements are equal.
 * @param first index of the first element in the range to partition
 * @param last index of the last element in the range to partition
 * @author Stefano Cipolletta
 * */
static unsigned long partition(GenericArray* unsorted_array, int (*compare)(void*, void*), unsigned long first, unsigned long last);

static unsigned long partition(GenericArray* unsorted_array, int (*compare)(void*, void*), unsigned long first, unsigned long last) {
    unsigned long i = first + 1;
    unsigned long j = (last - first) + 1;

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

void swap(GenericArray* unsorted_array, unsigned long index1, unsigned long index2) {
    if (unsorted_array == NULL) {
        fprintf(stderr, "quick_sort(): unsorted_array parameter is NULL.\n");
        exit(EXIT_FAILURE);
    }
    if (index1 >= generic_array_size(unsorted_array)) {
        fprintf(stderr, "swap(%lu): index out of bound.\n", index1);
        exit(EXIT_FAILURE);
    }
    if (index2 >= generic_array_size(unsorted_array)) {
        fprintf(stderr, "swap(%lu): index out of bound.\n", index2);
        exit(EXIT_FAILURE);
    }
    void* aux = generic_array_get(unsorted_array, index1);
    generic_array_update_at(unsorted_array, generic_array_get(unsorted_array, index2), index1);
    generic_array_update_at(unsorted_array, aux, index2);
}