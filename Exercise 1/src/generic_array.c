/**
 * @file generic_array.c
 * @author Stefano Cipolletta
 * @version v0.1
 * */

#include "generic_array.h"

#include <stdio.h>
#include <stdlib.h>

#ifndef INITIAL_ARRAY_SIZE
#define INITIAL_ARRAY_SIZE 10
#endif

struct _GenericArray {
    void** array;
    unsigned long num_el;
    unsigned long size;
};

GenericArray* generic_array_create() {
    GenericArray* array_ptr = (GenericArray*)malloc(sizeof(GenericArray));
    if (array_ptr == NULL) {
        fprintf(stderr, "generic_array_create(): Unable to allocate memory for the new array.\n");
        exit(EXIT_FAILURE);
    }

    array_ptr->array = malloc(INITIAL_ARRAY_SIZE * sizeof(void*));
    if (array_ptr->array == NULL) {
        fprintf(stderr, "generic_array_create(): Unable to allocate memory for the new array.\n");
        exit(EXIT_FAILURE);
    }

    for (unsigned long i = 0; i < INITIAL_ARRAY_SIZE; ++i) {
        array_ptr->array[i] = NULL;
    }

    array_ptr->num_el = 0;
    array_ptr->size = INITIAL_ARRAY_SIZE;

    return (array_ptr);
}

unsigned long generic_array_size(GenericArray* array_ptr) {
    if (array_ptr == NULL) {
        fprintf(stderr, "generic_array_size(): array_ptr parameter is NULL.\n");
        exit(EXIT_FAILURE);
    }
    return (array_ptr->num_el);
}

void* generic_array_get(GenericArray* array_ptr, unsigned long index) {
    if (array_ptr == NULL) {
        fprintf(stderr, "generic_array_get(): array_ptr parameter is NULL.\n");
        exit(EXIT_FAILURE);
    }
    if (index >= array_ptr->num_el) {
        fprintf(stderr, "generic_array_get(%lu): index out of bound.\n", index);
        exit(EXIT_FAILURE);
    }
    return (array_ptr->array)[index];
}