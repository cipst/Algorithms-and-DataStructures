#include <stdlib.h>

#include "../../Resources/C/Unity/unity.h"
#include "sorting.h"

// Data elements that are initialized before each test
static int i1, i2, i3;
static GenericArray* generic_array_int;

// Function called on test to initialize the environment
void setUp(void) {
    i1 = 9;
    i2 = -3;
    i3 = 1;
    generic_array_int = generic_array_create();
}

// Function called on test to clear the environment
void tearDown(void) {
    generic_array_destroy(generic_array_int);
}

/* TEST FUNCTIONS */
static void test_swap_two_el(void) {
    int* exp_arr[] = {&i3, &i1};
    int** act_arr = malloc(2 * sizeof(int*));
    generic_array_insert(generic_array_int, &i1);
    generic_array_insert(generic_array_int, &i3);
    swap(generic_array_int, 0, 1);
    act_arr[0] = (int*)generic_array_get(generic_array_int, 0);
    act_arr[1] = (int*)generic_array_get(generic_array_int, 1);
    TEST_ASSERT_EQUAL_PTR_ARRAY(act_arr, exp_arr, 2);
}

int main(void) {
    UNITY_BEGIN();

    RUN_TEST(test_swap_two_el);

    return UNITY_END();
}