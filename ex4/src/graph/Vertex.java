package graph;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

public class Vertex<T, S> {
    private T label = null;
    private Hashtable<T, Edge<S>> adjacentList = null;
    private double distance = 0;
    private Vertex<T, S> pi = null;

    /**
     * Create a new {@code Vertex}
     */
    public Vertex(T label) {
        this.adjacentList = new Hashtable<>();
        this.label = label;
    }

    public T getLabel() {
        return this.label;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getDistance() {
        return this.distance;
    }

    public void setPi(Vertex<T, S> pi) {
        this.pi = pi;
    }

    public Vertex<T, S> getPi() {
        return pi;
    }

    /**
     * @return the total number of {@code edge} outgoing
     */
    public int getOutDegree() {
        return adjacentList.size();
    }

    /**
     * @return a new {@link Set} of type {@code T} containing all the vertices
     *         adjacents
     */
    public Set<T> getAdjacentVertices() {
        Set<T> adjacent = new HashSet<>();

        for (T a : adjacentList.keySet()) {
            adjacent.add(a);
        }
        return adjacent;
    }

    /**
     * @return a new {@link Set} of type {@code Edge<S>} containing all the edges
     */
    public Set<Edge<S>> getEdgeLabels() {
        Set<Edge<S>> ris = new HashSet<>();
        Enumeration<Edge<S>> edge = adjacentList.elements();

        while (edge.hasMoreElements()) {
            Edge<S> e = edge.nextElement();
            ris.add(e);
        }

        return ris;
    }

    /**
     * Check if the given {@code vertexLabel} is an adjacent of the current
     * {@code Vertex}
     * 
     * @param vertexLabel label of the vertex to check if is adjacent
     * @return {@code TRUE} iff {@code vertexLabel} is an adjacent of the current
     *         {@code Vertex}, {@code FALSE} otherwise
     * @throws NullPointerException iff {@code vertexLabel} is {@code null}
     */
    public boolean hasAdjacent(T vertexLabel) throws NullPointerException {
        if (vertexLabel == null)
            throw new NullPointerException();

        return adjacentList.containsKey(vertexLabel);
    }

    public boolean hasEdge(Edge<S> edge) throws NullPointerException {
        if (edge == null)
            throw new NullPointerException();

        return adjacentList.contains(edge);
    }

    /**
     * @param adjacent of the vertex adjacent
     * @param label    of the edge
     * @return {@code TRUE} iff added successfully, {@code FALSE} otherwise
     * @throws NullPointerException iff {@code adjacent} OR {@code label} are
     *                              {@code null}
     */
    public boolean addAdjacent(T adjacent, Edge<S> label) throws NullPointerException {
        if (adjacent == null || label == null)
            throw new NullPointerException();

        return (adjacentList.putIfAbsent(adjacent, label) == null);
    }

    /**
     * @param adjacent
     * @return the edge on success, {@code null} otherwise
     * @throws NullPointerException iff {@code adjacent} is {@code null}
     */
    // public Edge<S> getEdgeLabel(T adjacent) throws NullPointerException {
    // if (adjacent == null)
    // throw new NullPointerException();

    // return adjacentList.get(adjacent);
    // }

    public Edge<S> getEdge(T adjacent) throws NullPointerException {
        if (adjacent == null)
            throw new NullPointerException();

        return adjacentList.get(adjacent);
    }

    public double getEdgeWeight(T adjacent) throws NullPointerException {
        if (adjacent == null)
            throw new NullPointerException();

        Edge<S> edge = getEdge(adjacent);

        if (edge != null)
            return edge.getWeight();
        else
            return -1;
    }

    /**
     * @param adjacent the adjacent to remove
     * @return the edge of the adjacent removed on success, {@code null} otherwise
     * @throws NullPointerException iff {@code adjacent} is {@code null}
     */
    public Edge<S> removeEdge(T adjacent) throws NullPointerException {
        if (adjacent == null)
            throw new NullPointerException();

        return adjacentList.remove(adjacent);
    }

    // /**
    // * @param adjacent
    // * @param newLabel
    // * @return
    // * @throws NullPointerException iff {@code adjacent} OR {@code newLabel} are
    // * {@code null}
    // */
    // public S replaceEdgeLabel(T adjacent, S newLabel) throws NullPointerException
    // {
    // if (adjacent == null || newLabel == null)
    // throw new NullPointerException();

    // return adjacentList.replace(adjacent, newLabel);
    // }

    /**
     * Remove all the adjacents vertices
     */
    public void clearEdges() {
        adjacentList.clear();
    }

    @Override
    public String toString() {
        return "{" + this.label + "}";
    }

    public String adjacentsToString() {
        return adjacentList.toString();
    }
}
