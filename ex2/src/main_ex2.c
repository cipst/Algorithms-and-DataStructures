#include <ctype.h>
#include <string.h>

#include "skip_list.h"

#define IS_LETTER(x) (((x) >= 'a' && (x) <= 'z') || ((x) >= 'A' && (x) <= 'Z'))

void print_skip_list(SkipList* list) {
    Node* x = list->head;
    for (int i = 0; i < list->max_level; i++) {
        while (x != NULL && x->next[i] != NULL) {
            printf("[%s]->", (char*)(x->next[i]->item));
            x = x->next[i];
        }
        printf("NIL\n");
    }
}

int compare(void* v1, void* v2) {
    if (v1 == NULL) {
        fprintf(stderr, "compare: v1 must not be null\n");
        exit(EXIT_FAILURE);
    }
    if (v2 == NULL) {
        fprintf(stderr, "compare: v1 must not be null\n");
        exit(EXIT_FAILURE);
    }
    return strcmp((const char*)v1, (const char*)v2);
}

void load_dictionary(SkipList* list, const char* file_name) {
    char* string;
    char buffer[1024];
    int buf_size = 1024;
    FILE* fp;

    fp = fopen(file_name, "r");
    if (fp == NULL) {
        fprintf(stderr, "main(load_dictionary): unable to open the file\n");
        exit(EXIT_FAILURE);
    }

    printf("\nLoading Dictionary...\n");

    // int count = 0;
    while (fgets(buffer, buf_size, fp) != NULL) {
        string = malloc((strlen(buffer)) * sizeof(char));
        if (string == NULL) {
            fprintf(stderr, "main(load_dictionary): unable to allocate memory for the string");
            exit(EXIT_FAILURE);
        }
        strncpy(string, buffer, strlen(buffer) - 1);
        // printf("%s", string);
        insertSkpiList(list, string);
        // count++;
    }

    fclose(fp);
    printf("\nDictionary Loaded Successfully!\n");
    fflush(stdout);
}

void check_correctme(SkipList* list, const char* file_name) {
    char c;
    char string[35] = "";
    FILE* fp;

    fp = fopen(file_name, "r");
    if (fp == NULL) {
        fprintf(stderr, "main(load_dictionary): unable to open the file\n");
        exit(EXIT_FAILURE);
    }

    printf("\nWords to correct:\n");
    fflush(stdout);

    while ((c = (char)fgetc(fp)) != EOF) {
        if (IS_LETTER(c)) {
            c = (char)tolower(c);
            strncat(string, &c, 1);
        } else if (c == ' ') {
            if (searchSkipList(list, string) == -1) {
                printf("%s\n", string);
                fflush(stdout);
            }
            strcpy(string, "");
        }
    }

    if (searchSkipList(list, string) == -1) {
        printf("%s\n", string);
        fflush(stdout);
    }
    strcpy(string, "");

    fclose(fp);
}

void test_SkipList(const char* path1, const char* path2) {
    SkipList* list;
    if ((list = createSkipList(compare)) == NULL) {
        fprintf(stderr, "test_SkipList: error while creating SkipList\n");
        exit(EXIT_FAILURE);
    }

    load_dictionary(list, path1);

    // print_skip_list(list);

    check_correctme(list, path2);

    freeSkipList(list);
}

int main(int argc, char** argv) {
    if (argc < 3) {
        fprintf(stderr, "Usage: ./main_ex2 <dictionary.txt> <correctme.txt>\n");
        exit(EXIT_FAILURE);
    }

    test_SkipList(argv[1], argv[2]);

    return EXIT_SUCCESS;
}