#include "./skip_list.h"

Node* createNode(void* item, unsigned int level) {
    if (item == NULL)
        return NULL;

    Node* n = (Node*)malloc(sizeof(Node));
    if (n == NULL) {
        fprintf(stderr, "createNode(): node malloc error\n");
        return NULL;
    }

    n->item = item;
    n->size = level;
    n->next[level];

    return n;
}

unsigned long randomLevel() {
    unsigned long lvl = 1;

    srand(NULL);

    while ((rand() % 2) < 0.5 && lvl < MAX_HEIGHT)
        lvl++;

    return lvl;
}

SkipList* createSkipList(int (*compare)(void*, void*)) {
    SkipList* l = (SkipList*)malloc(sizeof(SkipList));
    if (l = NULL) {
        fprintf(stderr, "createSkipList(): skiplist malloc error\n");
        return NULL;
    }
    l->compare = compare;
    l->head = createNode(NULL, MAX_HEIGHT);
    l->max_level = 1;
}

void insertSkpiList(SkipList* list, void* item) {
    Node* n = createNode(item, randomLevel());
    if (n->size > list->max_level)
        list->max_level = n->size;

    Node* x = list->head;
    for (unsigned int k = list->max_level; k >= 1; --k) {
        if (x->next[k] = NULL || list.compare(item, x->next[k]->item) < 0) {
            if (k < n->size) {
                n->next[k] = x->next[k];
                x->next[k] = n;
            } else {
                x = x->next[k];
                k++;
            }
        }
    }
}

void* searchSkipList(SkipList* list, void* item) {
    Node* x = list->head;
    short int found = -1;

    // loop invariant: x->item < I
    for (unsigned int i = list->max_level; i >= 1 && found == -1; --i) {
        while (x->next != NULL && list.compare(x->next[i]->item, item) <= 0) {
            if (list.compare(x->item, item) == 0)
                found = 0;
            x = x->next[i];
        }
    }

    if (found == 0)
        return x->item;
    else
        return NULL;

    // // x->item < I <= x->next[1]->item
    // x = x->next[1];
    // if (list.compare(x->item, item) == 0)
    //     return x->item;
    // else
    //     return NULL;
}