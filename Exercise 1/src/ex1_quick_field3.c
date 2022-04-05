/**
 * @file   ex1.c
 * @author Stefano Cipolletta
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

#include "sorting.h"

typedef struct _Record {
    int id;
    char *field1;
    int field2;
    float field3;
} Record;

static void load_array(const char *file_name, GenericArray *array) {
    char *read_line_p;
    char buffer[1024];
    int buf_size = 1024;
    FILE *fp;

    printf("\nLoading data from file...\n");

    fp = fopen(file_name, "r");

    if (fp == NULL) {
        fprintf(stderr, "main: unable to open the file");
        exit(EXIT_FAILURE);
    }

    while (fgets(buffer, buf_size, fp) != NULL) {
        read_line_p = malloc((strlen(buffer) + 1) * sizeof(char));
        if (read_line_p == NULL) {
            fprintf(stderr, "main: unable to allocate memory for the read line");
            exit(EXIT_FAILURE);
        }

        strcpy(read_line_p, buffer);

        char *id_in_read_line_p = strtok(read_line_p, ",");
        char *field1_in_read_line_p = strtok(NULL, ",");
        char *field2_in_read_line_p = strtok(NULL, ",");
        char *field3_in_read_line_p = strtok(NULL, ",");

        int id = atoi(id_in_read_line_p);
        char *field1 = malloc((strlen(field1_in_read_line_p) + 1) * sizeof(char));
        if (field1 == NULL) {
            fprintf(stderr, "main: unable to allocate memory for the string field of the read record");
            exit(EXIT_FAILURE);
        }
        strcpy(field1, field1_in_read_line_p);
        int field2 = atoi(field2_in_read_line_p);
        float field3 = (float)atof(field3_in_read_line_p);

        Record *record_p = malloc(sizeof(Record));
        if (record_p == NULL) {
            fprintf(stderr, "main: unable to allocate memory for the read record");
            exit(EXIT_FAILURE);
        }
        record_p->id = id;
        record_p->field1 = field1;
        record_p->field2 = field2;
        record_p->field3 = field3;

        generic_array_insert(array, (void *)record_p);
        free(read_line_p);
    }

    fclose(fp);
    printf("\nData loaded\n");
}

static void print_array(GenericArray *array) {
    unsigned long el_num = generic_array_size(array);

    Record *element;

    printf("\nGENERIC ARRAY OF RECORDS\n");

    for (unsigned long i = 0; i < el_num; i++) {
        element = (Record *)generic_array_get(array, i);
        printf("<%d, %s, %d, %f>\n", element->id, element->field1, element->field2, element->field3);
        fflush(stdout);
    }
}

static void free_array_content(GenericArray *array_to_free) {
    for (unsigned long i = 0; i < generic_array_size(array_to_free); i++) {
        free(generic_array_get(array_to_free, i));
    }
}

static int compare_record_float_field(void *r1_p, void *r2_p) {
    if (r1_p == NULL) {
        fprintf(stderr, "compare_record_float_field: the first parameter is a null pointer");
        exit(EXIT_FAILURE);
    }
    if (r2_p == NULL) {
        fprintf(stderr, "compare_record_float_field: the second parameter is a null pointer");
        exit(EXIT_FAILURE);
    }
    Record *rec1_p = (Record *)r1_p;
    Record *rec2_p = (Record *)r2_p;

    if (rec1_p->field3 > rec2_p->field3)
        return (1);
    else if (rec1_p->field3 < rec2_p->field3)
        return (-1);
    else
        return (0);
}

// It should be invoked with one parameter specifying the path of the data file
int main(int argc, char const *argv[]) {
    if (argc < 2) {
        fprintf(stderr, "Usage: ./ex1 <file_name>\n");
        exit(EXIT_FAILURE);
    }

    GenericArray *array = generic_array_create();

    load_array(argv[1], array);

    time_t seconds;
    struct tm *timeStruct;

    seconds = time(NULL);
    timeStruct = localtime(&seconds);
    printf("\n\e[1;31mStarting QuickSort... [%d:%d:%d]\n\e[0m", timeStruct->tm_hour, timeStruct->tm_min, timeStruct->tm_sec);

    quick_sort(array, compare_record_float_field);

    seconds = time(NULL);
    timeStruct = localtime(&seconds);
    printf("\n\e[1;31mQuickSort Ended [%d:%d:%d]\n\e[0m", timeStruct->tm_hour, timeStruct->tm_min, timeStruct->tm_sec);

    // print_array(array);
    free_array_content(array);
    generic_array_destroy(array);

    return EXIT_SUCCESS;
}