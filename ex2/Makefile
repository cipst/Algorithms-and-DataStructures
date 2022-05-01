CC = gcc

# all: tests ex2
all: ex2

# tests: bin/generic_array_tests bin/sorting_tests
# 	bin/generic_array_tests
# 	bin/sorting_tests

ex2: build/main_ex2
	build/main_ex2 "../Resources/Datasets/dictionary.txt" "../Resources/Datasets/correctme.txt" 

CFLAGS = -g -Wall -Wextra -Wpedantic -Wconversion

INCLUDES = src/*.h

COMMON_DEPS = $(INCLUDES) Makefile

obj/unity.o: ../Resources/C/Unity/unity.c ../Resources/C/Unity/unity.h ../Resources/C/Unity/unity_internals.h
	$(CC) $(CFLAGS) -c ../Resources/C/Unity/unity.c -o obj/unity.o

obj/%.o: src/%.c $(COMMON_DEPS)
	$(CC) $(CFLAGS) -c $< -o $@

# bin/generic_array_tests: build/unity.o build/generic_array_tests.o build/generic_array.o $(COMMON_DEPS)
# 	$(CC) -o bin/generic_array_tests build/generic_array_tests.o build/generic_array.o build/unity.o

# bin/sorting_tests: build/unity.o build/sorting_tests.o build/sorting.o build/generic_array.o $(COMMON_DEPS)
# 	$(CC) -o bin/sorting_tests build/sorting_tests.o build/sorting.o build/unity.o build/generic_array.o

build/main_ex2: obj/main_ex2.o obj/skip_list.o $(COMMON_DEPS)
	$(CC) -o build/main_ex2 obj/main_ex2.o obj/skip_list.o

clean:
	rm -f obj/* build/*