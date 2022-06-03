package dijkstra;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

import graph.Graph;
import graph.Vertex;
import graph.Edge;
import minimumheap.MinimumHeap;
import minimumheap.MinimumHeapException;

public class Dijkstra {

    private static <T, S> void initSingleSource(Graph<T, S> G, Vertex<T, S> src) {
        for (Vertex<T, S> v : G.getVerticesObj()) {
            v.setDistance(Double.MAX_VALUE);
            v.setPi(null);
        }
        src.setDistance(0);
    }

    private static <T, S> void relax(Vertex<T, S> u, Vertex<T, S> v) {
        double w = u.getEdgeWeight(v.getLabel());
        if (v.getDistance() > (u.getDistance() + w)) {
            v.setDistance(u.getDistance() + w);
            v.setPi(u);
        }
    }

    public static <T, S> Set<Vertex<T, S>> dijkstra(Graph<T, S> G, T vertexLabel)
            throws MinimumHeapException {

        if (!G.isDirected())
            throw new NullPointerException();

        Vertex<T, S> src = G.getVertex(vertexLabel);

        initSingleSource(G, src);

        Set<Vertex<T, S>> S = new HashSet<>(); // set of vertices whose final shortest-path weights from the source src
                                               // have already been determined

        GenericComparator<T, S> comparator = new GenericComparator<>();

        MinimumHeap<Vertex<T, S>> Q = new MinimumHeap<>(comparator);

        for (Vertex<T, S> v : G.getVerticesObj()) {
            Q.add(v);
        }

        while (Q.size() != 0) {
            Vertex<T, S> u = Q.remove();
            S.add(u);
            for (Vertex<T, S> v : G.getAdjacentVerticesObj(u)) {
                relax(u, v);
            }
        }

        return S;
    }
}

class GenericComparator<T, S> implements Comparator<Vertex<T, S>> {
    @Override
    public int compare(Vertex<T, S> o1, Vertex<T, S> o2) {
        if (o1.getDistance() > o2.getDistance()) {
            return 1;
        } else if (o1.getDistance() < o2.getDistance()) {
            return -1;
        } else {
            return 0;
        }
    }
}