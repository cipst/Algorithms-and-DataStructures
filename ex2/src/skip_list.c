#include "./skip_list.h"

Node* createNode(void* item, int level) {
    if (item == NULL)
        return NULL;

    Node* n = (Node*)malloc(sizeof(Node));
    if (n == NULL) {
        fprintf(stderr, "createNode(): node malloc error\n");
        return NULL;
    }

    n->item = item;
    n->size = level;
    n->next = (Node**)malloc(level * sizeof(Node*));

    return n;
}

int randomLevel() {
    int lvl = 1;

    srand((unsigned)time(NULL));

    while ((rand() % 2) < 0.5 && lvl < MAX_HEIGHT)
        lvl++;

    return lvl;
}

SkipList* createSkipList(int (*compare)(void*, void*)) {
    SkipList* l = (SkipList*)malloc(sizeof(SkipList));
    if (l == NULL) {
        fprintf(stderr, "createSkipList(): skiplist malloc error\n");
        return NULL;
    }
    l->head = createNode(NULL, MAX_HEIGHT);
    l->max_level = 1;
    l->compare = compare;
}

void insertSkpiList(SkipList* list, void* item) {
    Node* n = createNode(item, randomLevel());
    if (n->size > list->max_level)
        list->max_level = n->size;

    Node* x = list->head;
    for (int k = list->max_level - 1; k > 0; --k) {
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

    // loop invariant: x->item < I
    for (int i = list->max_level - 1; i > 0; --i) {
        while (x->next[i] != NULL && list.compare(x->next[i]->item, item) <= 0) {
            x = x->next[i];
        }
    }

    // x->item < I <= x->next[1]->item
    x = x->next[1];
    if (list.compare(x->item, item) == 0)
        return 1;
    else
        return -1;
}