package graph;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;
import java.util.Map.Entry;

public class Graph<T, S> {
    private Hashtable<T, Vertex<T, S>> vertices = null;
    private GraphType type = GraphType.DIRECTED;

    /**
     * Init a {@code Directed} {@code Graph}
     */
    public Graph() {
        this.vertices = new Hashtable<>();
    }

    /**
     * Init a {@code Graph} of given {@code type}
     * 
     * @param type of the new {@code Graph}
     * @see GraphType
     */
    public Graph(GraphType type) {
        this.vertices = new Hashtable<>();
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

    /**
     * @return the number of edges
     */
    public int getNumberEdges() {
        int sum = 0;

        for (T a : vertices.keySet()) {
            sum += vertices.get(a).getOutDegree();
        }

        return (isDirected() ? sum : sum / 2);
    }

    /**
     * Add a {@code Vertex} with the given {@code vertexLabel} into the
     * {@code Graph}
     * 
     * @param vertexLabel
     * @return {@code TRUE} iff added successfully, {@code FALSE} otherwise
     * @throws NullPointerException iff {@code vertexLabel} is {@code null}
     * @see Vertex
     */
    public boolean addVertex(T vertexLabel) throws NullPointerException {
        if (vertexLabel == null)
            throw new NullPointerException();

        Vertex<T, S> newVertex = new Vertex<>(vertexLabel);

        return (vertices.putIfAbsent(vertexLabel, newVertex) == null);
    }

    /**
     * @param vertexLabel
     * @return the {@code Vertex} with the given {@code vertexLabel}
     * @see Vertex
     */
    public Vertex<T, S> getVertex(T vertexLabel) {
        return vertices.get(vertexLabel);
    }

    /**
     * Check if the vertex {@code label} is contained in the {@code Graph}
     * 
     * @param vertexLabel
     * @return {@code TRUE} iff {@code vertexLabel} is contained in the
     *         {@code Graph}, {@code FALSE} otherwise
     * @throws NullPointerException iff {@code vertexLabel} is {@code null}
     */
    public boolean containsVertex(T vertexLabel) throws NullPointerException {
        if (vertexLabel == null)
            throw new NullPointerException();

        return vertices.containsKey(vertexLabel);
    }

    /**
     * @param vertexA
     * @param vertexB
     * @return {@code TRUE} iff there is an {@code edge} between {@code vertexA} and
     *         {@code vertexB}, {@code FALSE} otherwise
     * @throws NullPointerException iff {@code vertexA} OR {@code vertexB} are
     *                              {@code null}
     */
    public boolean containsEdge(T vertexA, T vertexB) throws NullPointerException {
        if (vertexA == null || vertexB == null)
            throw new NullPointerException();

        Vertex<T, S> firstVertex = vertices.get(vertexA);
        Vertex<T, S> secondVertex = vertices.get(vertexB);

        return firstVertex.hasAdjacent(vertexB) || secondVertex.hasAdjacent(vertexB);
    }

    /**
     * Get all the vertices label in the {@code Graph}
     * 
     * @return a new {@link Set} of vertices label
     */
    public Set<T> getVerticesLabel() {
        Set<T> verts = new HashSet<>();
        for (T a : vertices.keySet()) {
            verts.add(a);
        }
        return verts;
    }

    /**
     * Get all the vertices in the {@code Graph}
     * 
     * @return a new {@link Set} of {@code Vertex}
     * @see Vertex
     */
    public Set<Vertex<T, S>> getVertices() {
        Set<Vertex<T, S>> v = new HashSet<>();
        Set<T> s = getVerticesLabel();
        for (T x : s) {
            v.add(getVertex(x));
        }
        return v;
    }

    /**
     * Get all the edges in the {@code Graph}
     * 
     * @return a new {@code Set} of {@code Edge}
     * @see Edge
     */
    public Set<Edge<S>> getEdges() {
        Set<Edge<S>> ris = new HashSet<>();

        for (T a : vertices.keySet()) {
            for (Edge<S> b : vertices.get(a).getEdges()) {
                ris.add(b);
            }
        }

        return ris;
    }

    /**
     * @param vertexLabel to remove
     * @return the {@code Vertex} removed with the given label
     * @throws NullPointerException iff {@code vertexLabel} is {@code null}
     * @throws GraphException       iff {@code vertexLabel} is not in the
     *                              {@code Graph}
     */
    public Vertex<T, S> removeVertex(T vertexLabel) throws NullPointerException, GraphException {
        if (vertexLabel == null)
            throw new NullPointerException();

        Vertex<T, S> ris = vertices.remove(vertexLabel);

        if (ris == null)
            throw new GraphException("Vertex with the label: " + vertexLabel + " is not in the Graph");

        Set<T> all = this.getVerticesLabel();

        for (T a : all) {
            Vertex<T, S> curr = this.vertices.get(a);
            if (curr.hasAdjacent(vertexLabel)) {
                curr.removeEdge(vertexLabel);
            }
        }

        return ris;
    }

    /**
     * @param vertexLabel vertex whose adjacents label you want to know
     * @return a {@link Set} of adjacents label of the given {@code vertexLabel}
     * @throws NullPointerException iff {@code vertexLabel} is {@code null}
     * @throws GraphException       iff {@code vertexLabel} is not in the
     *                              {@code Graph}
     */
    public Set<T> getAdjacentVerticesLabel(T vertexLabel) throws NullPointerException, GraphException {
        if (vertexLabel == null)
            throw new NullPointerException();

        if (!vertices.containsKey(vertexLabel))
            throw new GraphException("Vertex with the label: " + vertexLabel + " is not in the Graph");

        Vertex<T, S> vertex = vertices.get(vertexLabel);

        return vertex.getAdjacentVerticesLabel();
    }

    /**
     * @param vertexLabel
     * @return a new {@link Set} of {@link Vertex} adjacents with the {@code vertex}
     *         with the given label
     * @throws NullPointerException iff {@code vertexLabel} is {@code null}
     * @throws GraphException       iff {@code vertexLabel} is not in the
     *                              {@code Graph}
     */
    public Set<Vertex<T, S>> getAdjacentVertices(T vertexLabel) throws NullPointerException, GraphException {
        if (vertexLabel == null)
            throw new NullPointerException();

        if (!vertices.containsKey(vertexLabel))
            throw new GraphException("Vertex: " + vertexLabel + " is not in the Graph");

        Set<T> adj = getAdjacentVerticesLabel(vertexLabel);

        Set<Vertex<T, S>> ris = new HashSet<>();
        for (T a : adj) {
            ris.add(getVertex(a));
        }
        return ris;
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

        return firstVertex.hasAdjacent(vertexTo);
    }

    /**
     * Check if the two vertices are complete adjacents:
     * <ul>
     * <li>{@code vertexA} is adjacent of {@code vertexB}</li>
     * <li>{@code vertexB} is adjacent of {@code vertexA}</li>
     * </ul>
     * For {@code UNDIRECTED} {@code Graph} {@link #areAdjacents(Object, Object)}
     * and {@link #areCompleteAdjacents(Object, Object)} return the same result
     * 
     * @param vertexA
     * @param vertexB
     * @return {@code TRUE} iff the two vertices are adjacents, {@code FALSE}
     *         otherwise
     * @throws NullPointerException iff {@code vertexA} OR {@code vertexB} are
     *                              {@code null}
     */
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
    public Edge<S> getEdge(T vertexFrom, T vertexTo) throws NullPointerException {
        if (vertexFrom == null || vertexTo == null)
            throw new NullPointerException();

        Vertex<T, S> vertex = vertices.get(vertexFrom);

        return vertex.getEdge(vertexTo);
    }

    /**
     * @param vertexFrom
     * @param vertexTo
     * @param edgeLabel
     * @throws NullPointerException iff {@code vertexFrom} OR {@code vertexTo} OR
     *                              {@code edgeLabel} are {@code null}
     * @throws GraphException
     *                              <ul>
     *                              <li>if {@code vertexFrom} OR {@code vertexTo}
     *                              are not in the {@code Graph}</li>
     *                              <li>if there is already an {@code edge} between
     *                              the two vertices</li>
     *                              </ul>
     */
    public void addEdge(T vertexFrom, T vertexTo, S edgeLabel) throws NullPointerException, GraphException {
        if (vertexFrom == null || vertexTo == null || edgeLabel == null)
            throw new NullPointerException();

        if (!vertices.containsKey(vertexFrom))
            throw new GraphException("There is no vertexFrom:" + vertexFrom + " in the Graph");
        if (!vertices.containsKey(vertexTo))
            throw new GraphException("There is no vertexTo" + vertexTo + " in the Graph");

        Vertex<T, S> firstVertex = vertices.get(vertexFrom);

        if (!firstVertex.addAdjacent(vertexTo, edgeLabel))
            throw new GraphException("There is already an edgeLabel: " + edgeLabel + " between vertexFrom:" + vertexFrom
                    + " and vertexTo:" + vertexTo + " in the Graph");

        if (!isDirected()) {
            Vertex<T, S> secondVertex = vertices.get(vertexTo);

            if (!secondVertex.addAdjacent(vertexFrom, edgeLabel))
                throw new GraphException("There is already an edgeLabel: " + edgeLabel + " between vertexTo:" + vertexTo
                        + " and vertexFrom:" + vertexFrom + " in the Graph");
        }
    }

    /**
     * @param vertexFrom
     * @param vertexTo
     * @return the {@code Edge} removed
     * @throws NullPointerException iff {@code vertexA} OR {@code vertexB} are
     *                              {@code null}
     * @throws GraphException
     *                              <ul>
     *                              <li>if {@code vertexA} OR {@code vertexB} are
     *                              not in the {@code Graph}</li>
     *                              <li>if there is no {@code edge} between
     *                              the two vertices</li>
     *                              </ul>
     */
    public Edge<S> removeEdge(T vertexFrom, T vertexTo) throws NullPointerException, GraphException {
        if (vertexFrom == null || vertexTo == null)
            throw new NullPointerException();

        if (!vertices.containsKey(vertexFrom))
            throw new GraphException("There is no vertexA:" + vertexFrom + " in the Graph");
        if (!vertices.containsKey(vertexTo))
            throw new GraphException("There is no vertexB:" + vertexTo + " in the Graph");

        Vertex<T, S> firstVertex = vertices.get(vertexFrom);

        Edge<S> ris = firstVertex.removeEdge(vertexTo);

        if (ris == null)
            throw new GraphException(
                    "There is no edge between vertexA:" + vertexFrom + " and vertexB:" + vertexTo + " in the Graph");

        if (!isDirected()) {
            Vertex<T, S> secondVertex = vertices.get(vertexTo);

            if (secondVertex.removeEdge(vertexFrom) == null)
                throw new GraphException(
                        "There is no edge between vertexB:" + vertexTo + " and vertexA:" + vertexFrom
                                + " in the Graph");
        }

        return ris;
    }

    @Override
    public String toString() {
        String s = "{\n";
        for (Entry<T, Vertex<T, S>> e : vertices.entrySet()) {
            s += "  " + e.getKey() + ": " + e.getValue().adjacentsToString() + "\n";
        }
        s += "}";
        return s;
    }
}
