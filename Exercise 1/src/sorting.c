/**
 * @file sorting.c
 * @author Stefano Cipolletta
 * */

#include "sorting.h"

#include <stdio.h>
#include <stdlib.h>

static void quick_sort_wrap(GenericArray* unsorted_arrat, int (*compare)(void*, void*), unsigned long first, unsigned long last);

/**
 * Hearth of the QuickSort algorithm
 * @param unsorted_array unsorted array
 * @param compare pointer to a function which determines the precedence relation between the array elements.
 * It returns 1 iff the first element is greater than the second.
 * It returns -1 iff the first element is smaller than the second.
 * It return 0 iff the first and the second elements are equal.
 * @param first index of the first element in the range to partition
 * @param last index of the last element in the range to partition
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
        quick_sort_wrap(unsorted_array, compare, 0, n - 1);
}

static void quick_sort_wrap(GenericArray* unsorted_array, int (*compare)(void*, void*), unsigned long first, unsigned long last) {
    unsigned long p = partition(unsorted_array, compare, first, last);
    quick_sort_wrap(unsorted_array, compare, first, p - 1);
    quick_sort_wrap(unsorted_array, compare, p + 1, last);
}

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
