package graph;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

class Vertex<T, S> {
    private T label = null;
    private Hashtable<T, S> adjacentList = null;

    /**
     * Create a new {@code Vertex} with the given {@code label}
     * 
     * @param label name of the new {@code Vertex}
     * @throws NullPointerException iff {@code label} is {@code null}
     */
    public Vertex(T label) throws NullPointerException {
        if (label == null)
            throw new NullPointerException();

        this.label = label;
        this.adjacentList = new Hashtable<>();
    }

    /**
     * @return the {@code label} of the {@code Vertex}
     */
    public T getVertexLabel() {
        return label;
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
     * @return a new {@link Set} of type {@code S} containing all the edges
     */
    public Set<S> getEdgeLabels() {
        Set<S> ris = new HashSet<>();
        Enumeration<S> edge = adjacentList.elements();

        while (edge.hasMoreElements()) {
            S e = edge.nextElement();
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
    public boolean hasEdge(T vertexLabel) throws NullPointerException {
        if (vertexLabel == null)
            throw new NullPointerException();

        return adjacentList.containsKey(vertexLabel);
    }

    /**
     * @param adjacent of the vertex adjacent
     * @param label    of the edge
     * @return {@code TRUE} iff added successfully, {@code FALSE} otherwise
     * @throws NullPointerException iff {@code adjacent} OR {@code label} are
     *                              {@code null}
     */
    public boolean addAdjacent(T adjacent, S label) throws NullPointerException {
        if (adjacent == null || label == null)
            throw new NullPointerException();

        return (adjacentList.putIfAbsent(adjacent, label) == null);
    }

    /**
     * @param adjacent
     * @return the edge on success, {@code null} otherwise
     * @throws NullPointerException iff {@code adjacent} is {@code null}
     */
    public S getEdgeLabel(T adjacent) throws NullPointerException {
        if (adjacent == null)
            throw new NullPointerException();

        return adjacentList.get(adjacent);
    }

    /**
     * @param adjacent the adjacent to remove
     * @return the edge of the adjacent removed on success, {@code null} otherwise
     * @throws NullPointerException iff {@code adjacent} is {@code null}
     */
    public S removeEdge(T adjacent) throws NullPointerException {
        if (adjacent == null)
            throw new NullPointerException();

        return adjacentList.remove(adjacent);
    }

    /**
     * @param adjacent
     * @param newLabel
     * @return
     * @throws NullPointerException iff {@code adjacent} OR {@code newLabel} are
     *                              {@code null}
     */
    public S replaceEdgeLabel(T adjacent, S newLabel) throws NullPointerException {
        if (adjacent == null || newLabel == null)
            throw new NullPointerException();

        return adjacentList.replace(adjacent, newLabel);
    }

    /**
     * Remove all the adjacents vertices
     */
    public void clearEdges() {
        adjacentList.clear();
    }

    @Override
    public String toString() {
        return adjacentList.toString();
    }
}
