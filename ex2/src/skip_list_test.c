#include "skip_list.h"

#include <stdlib.h>

#include "../../Resources/C/Unity/unity.h"

// Data elements that are initialized before each test
static int i1, i2, i3, i4, i5;
static SkipList* skip_list_int;

int compare(void* v1, void* v2) {
    if (v1 == NULL) {
        printf("compare(v1, v2): v1 must not be NULL\n");
        exit(EXIT_FAILURE);
    }
    if (v2 == NULL) {
        printf("compare(v1, v2): v2 must not be NULL\n");
        exit(EXIT_FAILURE);
    }
    int i1 = *((int*)(v1));
    int i2 = *((int*)(v2));
    return i1 - i2;
}

// Function called on test to initialize the environment
void setUp(void) {
    i1 = 53;
    i2 = 47;
    i3 = 91;
    i4 = 14;
    i5 = 7;
    skip_list_int = createSkipList(compare);
}

// Function called on test to clear the environment
void tearDown(void) {
    freeSkipList(skip_list_int);
}

static void test_skip_list_insert_one(void) {
    insertSkpiList(skip_list_int, &i1);
}

int main(void) {
    UNITY_BEGIN();

    RUN_TEST(test_skip_list_insert_one);

    return UNITY_END();
}