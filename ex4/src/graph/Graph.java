package graph;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

public class Graph<T, S> {
    private Hashtable<T, Vertex<T, S>> vertices = null;

    private GraphType type = GraphType.DIRECTED;

    /**
     * Init a {@code Directed} {@code Graph}
     */
    public Graph() {
        this.vertices = new Hashtable<>();
        // this.edges = new ArrayList<>();
    }

    /**
     * Init a {@code Graph} of given {@ode type}
     * 
     * @param type of the new {@code Graph}
     * @see {@link GraphType}
     */
    public Graph(GraphType type) {
        this.vertices = new Hashtable<>();
        // this.edges = new ArrayList<>();
        this.type = type;
    }

    /**
     * @return the {@code type} of the {@code Graph}
     */
    public GraphType getType() {
        return this.type;
    }

    /**
     * @return {@code TRUE} iff the {@code Graph} is {@code  Directed},
     *         {@code FALSE} otherwise
     */
    public boolean isDirected() {
        return this.type == GraphType.DIRECTED;
    }

    /**
     * @return the number of vertices
     */
    public int getNumberVertices() {
        return this.vertices.size();
    }

    public int getNumberEdges() {
        int sum = 0;

        for (T a : vertices.keySet()) {
            sum += vertices.get(a).getOutDegree();
        }

        return (isDirected() ? sum : sum / 2);
    }

    /**
     * Add a {@code Vertex} with the given {@code label} into the {@code Graph}
     * 
     * @param vertexLabel
     * @return {@code TRUE} iff added successfully, {@code FALSE} otherwise
     * @throws NullPointerException iff {@code vertexLabel} is {@code null}
     * @see {@link Vertex}
     */
    public boolean addVertex(T vertexLabel) throws NullPointerException {
        if (vertexLabel == null)
            throw new NullPointerException();

        Vertex<T, S> newVertex = new Vertex<>();

        return (vertices.putIfAbsent(vertexLabel, newVertex) == null);
    }

    /**
     * Check if the vertex {@code label} is contained in the {@code Graph}
     * 
     * @param vertexLabel
     * @return {@code TRUE} iff {@code label} is contained in the {@code Graph},
     *         {@code FALSE} otherwise
     * @throws NullPointerException iff {@code vertexLabel} is {@code null}
     */
    public boolean containsVertex(T vertexLabel) throws NullPointerException {
        if (vertexLabel == null)
            throw new NullPointerException();

        return vertices.containsKey(vertexLabel);
    }

    public boolean containsEdge(T vertexA, T vertexB) throws NullPointerException {
        if (vertexA == null || vertexB == null)
            throw new NullPointerException();

        Vertex<T, S> firstVertex = vertices.get(vertexA);
        Vertex<T, S> secondVertex = vertices.get(vertexB);

        return firstVertex.hasAdjacent(vertexB) || secondVertex.hasAdjacent(vertexB);
    }

    /**
     * Get all the vertices in the {@code Graph}
     * 
     * @return a new {@code Set} of vertices
     * @see {@link java.util.Set}
     */
    public Set<T> getVertices() {
        Set<T> verts = new HashSet<>();
        for (T a : vertices.keySet()) {
            verts.add(a);
        }
        return verts;
    }

    public Set<S> getEdges() {
        Set<S> ris = new HashSet<>();

        for (T a : vertices.keySet()) {
            for (S b : vertices.get(a).getEdgeLabels()) {
                ris.add(b);
            }
        }

        return ris;
    }

    /**
     * @param vertexLabel to remove
     * @return {@code TRUE} iff removed successfully, {@code FALSE} otherwise
     * @throws NullPointerException iff {@code vertexLabel} is {@code null}
     */
    public boolean removeVertex(T vertexLabel) throws NullPointerException {
        if (vertexLabel == null)
            throw new NullPointerException();

        // Vertex<T, S> ris = vertices.remove(vertexLabel);

        if (vertices.remove(vertexLabel) == null)
            return false;

        Set<T> all = this.getVertices();

        for (T a : all) {
            Vertex<T, S> curr = this.vertices.get(a);
            if (curr.hasAdjacent(vertexLabel)) {
                curr.removeEdge(vertexLabel);
            }
        }

        return true;
    }

    /**
     * @param vertexLabel vertex whose adjacents you want to know
     * @return a {@link Set} of adjacents of the given {@code vertexLabel},
     *         {@code null} otherwise
     * @throws NullPointerException iff {@code vertexLabel} is {@code null}
     */
    public Set<T> getAdjacentVertices(T vertexLabel) throws NullPointerException {
        if (vertexLabel == null)
            throw new NullPointerException();

        if (!vertices.contains(vertexLabel))
            return null;

        Vertex<T, S> vertex = vertices.get(vertexLabel);

        return vertex.getAdjacentVertices();
    }

    /**
     * Return the total degree of the given {@code vertexLabel}
     * 
     * @param vertexLabel
     * @return the total degree on success, -1 otherwise
     * @throws NullPointerException iff {@code vertexLabel} is {@code null}
     */
    public int getVertexDegree(T vertexLabel) throws NullPointerException {
        if (vertexLabel == null)
            throw new NullPointerException();

        Vertex<T, S> vertex = this.vertices.get(vertexLabel);

        if (vertex == null)
            return -1;

        int outdegree = vertex.getOutDegree();

        if (!isDirected())
            return outdegree;

        int indegree = 0;

        Enumeration<Vertex<T, S>> enumerationVertices = vertices.elements();
        while (enumerationVertices.hasMoreElements()) {
            vertex = enumerationVertices.nextElement();
            if (vertex.hasAdjacent(vertexLabel))
                indegree++;
        }

        return (outdegree + indegree);
    }

    /**
     * Check if the two vertices are adjacents
     * 
     * @param vertexFrom
     * @param vertexTo
     * @return {@code TRUE} iff the two vertices are adjacents, {@code FALSE}
     *         otherwise
     * @throws NullPointerException iff {@code vertexA} OR {@code vertexB} are
     *                              {@code null}
     */
    public boolean areAdjacents(T vertexFrom, T vertexTo) throws NullPointerException {
        if (vertexFrom == null || vertexTo == null)
            throw new NullPointerException();

        Vertex<T, S> firstVertex = vertices.get(vertexFrom);

        // if (this.type == GraphType.UNDIRECTED)
        return firstVertex.hasAdjacent(vertexTo);

        // Vertex<T, S> secondVertex = vertices.get(vertexB);

        // return (firstVertex.hasAdjacent(vertexB) &&
        // secondVertex.hasAdjacent(vertexA));
    }

    public boolean areCompleteAdjacents(T vertexA, T vertexB) {
        return this.areAdjacents(vertexA, vertexB) && this.areAdjacents(vertexB, vertexA);
    }

    /**
     * @param vertexFrom
     * @param vertexTo
     * @return the edge that connect {@code vertexFrom} with {@code vertexTo} on
     *         success, {@code null} otherwise
     * @throws NullPointerException iff {@code vertexFrom} OR {@code vertexTo} are
     *                              {@code null}
     */
    public S getEdge(T vertexFrom, T vertexTo) throws NullPointerException {
        if (vertexFrom == null || vertexTo == null)
            throw new NullPointerException();

        Vertex<T, S> vertex = vertices.get(vertexFrom);

        return vertex.getEdgeLabel(vertexTo);
    }

    /**
     * @param vertexA
     * @param vertexB
     * @param label
     * @return {@code TRUE} on success, {@code FALSE} otherwise
     * @throws NullPointerException iff {@code vertexFrom} OR {@code vertexTo} OR
     *                              {@code label} are {@code null}
     */
    public boolean addEdge(T vertexA, T vertexB, S label) throws NullPointerException {
        if (vertexA == null || vertexB == null || label == null)
            throw new NullPointerException();

        if (!vertices.containsKey(vertexA))
            return false;
        if (!vertices.containsKey(vertexB))
            return false;

        Vertex<T, S> firstVertex = vertices.get(vertexA);

        if (!firstVertex.addAdjacent(vertexB, label))
            return false;

        if (!isDirected()) {
            Vertex<T, S> secondVertex = vertices.get(vertexB);

            if (!secondVertex.addAdjacent(vertexA, label))
                return false;
        }

        return true;
    }

    /**
     * @param vertexA
     * @param vertexB
     * @return {@code TRUE} on success, {@code FALSE} otherwise
     * @throws NullPointerException iff {@code vertexA} OR {@code vertexB} are
     *                              {@code null}
     */
    public boolean removeEdge(T vertexA, T vertexB) throws NullPointerException {
        if (vertexA == null || vertexB == null)
            throw new NullPointerException();

        if (!vertices.containsKey(vertexA))
            return false;
        if (!vertices.containsKey(vertexB))
            return false;

        Vertex<T, S> firstVertex = vertices.get(vertexA);

        if (firstVertex.removeEdge(vertexB) == null)
            return false;

        if (!isDirected()) {
            Vertex<T, S> secondVertex = vertices.get(vertexB);

            if (secondVertex.removeEdge(vertexA) == null)
                return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return vertices.toString();
    }
}
