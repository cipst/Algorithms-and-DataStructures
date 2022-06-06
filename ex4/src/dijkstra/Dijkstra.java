package dijkstra;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import graph.Graph;
import graph.Vertex;
import graph.Edge;
import graph.GraphException;
import minimumheap.MinimumHeap;
import minimumheap.MinimumHeapException;

public class Dijkstra {

    private static <T, S> void initSingleSource(Graph<T, S> G, Vertex<T, S> src) {
        for (Vertex<T, S> v : G.getVertices()) {
            v.setDistance(Double.POSITIVE_INFINITY);
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

    public static <T, S> Set<Vertex<T, S>> dijkstra(Graph<T, S> graph, T srcLabel)
            throws MinimumHeapException, GraphException, Exception {
        GenericComparator<T, S> comparator = new GenericComparator<>();

        Vertex<T, S> src = graph.getVertex(srcLabel);

        src.setDistance(0);

        MinimumHeap<Vertex<T, S>> Q = new MinimumHeap<>(comparator);
        Set<Vertex<T, S>> ris = new HashSet<>();

        for (Vertex<T, S> v : graph.getVertices()) {
            if (!v.equals(src)) {
                v.setDistance(Double.POSITIVE_INFINITY);
                v.setPi(null);
            }
            Q.add(v);
        }

        ris.add(src);

        while (Q.size() != 0) {
            Vertex<T, S> u = Q.remove();
            ris.add(u);
            for (Vertex<T, S> v : graph.getAdjacentVertices(u.getLabel())) {
                Vertex<T, S> tmp = v;
                Double alt = u.getDistance() + graph.getEdge(u.getLabel(), v.getLabel()).getWeight();
                if (alt < v.getDistance() && u.getDistance() != Double.POSITIVE_INFINITY) {
                    v.setDistance(alt);
                    v.setPi(u);
                    Q.decrease(tmp, v);
                }
            }
        }

        return ris;
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