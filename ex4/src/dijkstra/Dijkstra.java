package dijkstra;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

import graph.*;
import minimumheap.*;

public class Dijkstra {

    //
    // Dijkstra(G, s)
    // Q ← V
    // for ∀v ∈ V do v.d ← ∞, v.π ← nil
    // s.d ← 0
    // s.π ← nil
    // while Q != ∅ do
    // u ← togli nodo con d minimo da Q
    // for ∀v ∈ adj[u] do
    // if v ∈ Q e u.d + W(u, v) < v.d then
    // v.d ← u.d + W(u, v)
    // v.π ← u
    //

    class CompareDouble implements Comparator<Double> {
        @Override
        public int compare(Double o1, Double o2) {
            return o1.compareTo(o2);
        }
    }

    public static <T, S> void dijkstra(Graph<T, S> G, T s)
            throws MinimumHeapException {

        if (!G.isDirected())
            throw new NullPointerException();

        Set<T> S = new HashSet<>();
        GenericComparator<T, S> comparator = new GenericComparator<>();

        MinimumHeap<Vertex<T, S>> Q = new MinimumHeap<>(comparator);

        for (T v : G.getVertices()) {
            Q.add();
        }

        while (Q.size() != 0) {
            T u = Q.remove();
            S.add(u);
            for (T v : G.getAdjacentVertices(u)) {
                relax(u, v, w);
            }
        }
    }

    private static <T> void relax(T u, T v, Comparator<? super T> comparator) {

    }
}

class GenericComparator<T, S> implements Comparator<Vertex<T, S>> {
    @Override
    public int compare(Vertex<T, S> o1, Vertex<T, S> o2) {
        if (o1.getValue() > o2.getValue()) {
            return 1;
        } else if (o1.getValue() < o2.getValue()) {
            return -1;
        } else {
            return 0;
        }
    }
}