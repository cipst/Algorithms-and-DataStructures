#include <stdio.h>
#include <stdlib.h>

#include "../../Resources/C/Unity/unity.h"
#include "generic_array.h"

// Data elements that are initialized before each test
static int i1, i2, i3;
static GenericArray* generic_array_int;

// Function called on test to initialize the environment
void setUp(void) {
    i1 = -12;
    i2 = 0;
    i3 = 4;
    generic_array_int = generic_array_create();
}

// Function called on test to clear the environment
void tearDown(void) {
    generic_array_destroy(generic_array_int);
}

/* TEST FUNCTIONS */
static void test_generic_array_size_zero_el(void) {
    TEST_ASSERT_EQUAL_INT(0, generic_array_size(generic_array_int));
}

static void test_generic_array_insert_one_el(void) {
    TEST_ASSERT_EQUAL_PTR(&i1, generic_array_insert(generic_array_int, &i1));
}

static void test_generic_array_size_one_el(void) {
    generic_array_insert(generic_array_int, &i1);
    TEST_ASSERT_EQUAL_INT(1, generic_array_size(generic_array_int));
}

static void test_generic_array_insert_two_el(void) {
    int* exp_arr[] = {&i1, &i2};
    int** act_arr = malloc(2 * sizeof(int*));
    act_arr[0] = (int*)generic_array_insert(generic_array_int, &i1);
    act_arr[1] = (int*)generic_array_insert(generic_array_int, &i2);
    TEST_ASSERT_EQUAL_PTR_ARRAY(act_arr, exp_arr, 2);
}

static void test_generic_array_size_two_el(void) {
    generic_array_insert(generic_array_int, &i1);
    generic_array_insert(generic_array_int, &i3);
    TEST_ASSERT_EQUAL_INT(2, generic_array_size(generic_array_int));
}

int main(void) {
    UNITY_BEGIN();

    RUN_TEST(test_generic_array_size_zero_el);
    RUN_TEST(test_generic_array_insert_one_el);
    RUN_TEST(test_generic_array_size_one_el);
    RUN_TEST(test_generic_array_insert_two_el);
    RUN_TEST(test_generic_array_size_two_el);

    return UNITY_END();
}