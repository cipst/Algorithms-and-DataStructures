/**
 * @file   ex1.c
 * @author Stefano Cipolletta
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "sorting.h"

// It should be invoked with one parameter specifying the path of the data file
int main(int argc, char const *argv[]) {
    if (argc < 2) {
        fprintf(stderr, "Error in ex1: missing <file_name> path.\n");
        exit(EXIT_FAILURE);
    }

    return EXIT_SUCCESS;
}