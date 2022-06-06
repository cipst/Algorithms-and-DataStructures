package graph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Before;
import org.junit.Test;

public class GraphTestsDirected {
    private Graph<Integer, Character> graph = null;
    private int lb1, lb2, lb3;
    private char c1, c2, c3;

    @Before
    public void createGraph() {
        graph = new Graph<>(GraphType.DIRECTED);
        lb1 = 5;
        lb2 = 14;
        lb3 = -7;
        c1 = 'A';
        c2 = 'B';
        c3 = 'C';
    }

    @Test
    public void testAddOneVertex() {
        assertTrue(graph.addVertex(lb1));
    }

    @Test(expected = NullPointerException.class)
    public void testAddNullVertex() {
        graph.addVertex(null);
    }

    @Test
    public void testAddTwoVertex_True() {
        boolean ris = graph.addVertex(lb1);
        ris = ris && graph.addVertex(lb2);
        assertTrue(ris);
    }

    @Test
    public void testAddTwoVertex_False() {
        boolean ris = graph.addVertex(lb1);
        ris = ris && graph.addVertex(lb1);
        assertFalse(ris);
    }

    @Test
    public void testIsDirected() {
        assertTrue(graph.isDirected());
    }

    @Test
    public void testGetType() {
        assertThat(graph.getType(), is(GraphType.DIRECTED));
    }

    @Test
    public void testGetNumberVertices_Empty() {
        assertEquals(graph.getNumberVertices(), 0);
    }

    @Test
    public void testGetNumberVertices_OneVertex() {
        graph.addVertex(lb1);
        assertEquals(graph.getNumberVertices(), 1);
    }

    @Test
    public void testGetNumberVertices_TwoVertices() {
        graph.addVertex(lb1);
        graph.addVertex(lb2);
        assertEquals(graph.getNumberVertices(), 2);
    }

    @Test
    public void testContains_True() {
        graph.addVertex(lb1);
        assertTrue(graph.containsVertex(lb1));
    }

    @Test
    public void testContains_False() {
        assertFalse(graph.containsVertex(lb1));
    }

    @Test
    public void testGetVerticesLabel_Empty() {
        Set<Integer> ris = graph.getVerticesLabel();
        assertThat(ris, is(new HashSet<>()));
    }

    @Test
    public void testGetVerticesLabel_OneVertex() {
        graph.addVertex(lb1);
        Set<Integer> exp = new HashSet<>();
        exp.add(lb1);
        assertThat(graph.getVerticesLabel(), is(exp));
    }

    @Test
    public void testGetVertices_TwoVertices() {
        graph.addVertex(lb1);
        graph.addVertex(lb2);
        Set<Integer> exp = new HashSet<>();
        exp.add(lb1);
        exp.add(lb2);
        assertThat(graph.getVerticesLabel(), is(exp));
    }

    @Test(expected = NullPointerException.class)
    public void testRemoveVertex_Null() {
        graph.removeVertex(null);
    }

    @Test(expected = GraphException.class)
    public void testRemoveVertex_NotInGraph() {
        graph.removeVertex(lb1);
    }

    @Test
    public void testRemoveVertex_True() {
        graph.addVertex(lb1);
        assertThat(graph.removeVertex(lb1), is(new Vertex<>(lb1)));
    }

    @Test(expected = NullPointerException.class)
    public void testGetAdjacentVertices_Null() {
        graph.getAdjacentVertices(null);
    }

    @Test
    public void testGetAdjacentVertices_Empty() {
        assertNull(graph.getAdjacentVertices(lb1));
    }

    @Test
    public void testAddEdge_False() {
        graph.addVertex(lb1);
        assertFalse(graph.addEdge(lb1, lb2, c1));
    }

    // @Test
    // public void testAddThreeVerticesDirected() {
    // directedGraph.addVertex(lb1);
    // directedGraph.addVertex(lb2);
    // directedGraph.addVertex(lb3);

    // System.out.println(directedGraph);

    // directedGraph.addEdge(lb1, lb2, 'B');
    // directedGraph.addEdge(lb2, lb1, 'A');
    // directedGraph.addEdge(lb3, lb1, 'C');
    // directedGraph.addEdge(lb1, lb3, 'D');

    // System.out.println(directedGraph);

    // System.out.println(directedGraph.getNumberVertices());

    // System.out.println("DIRECTED DEGREE: " + directedGraph.getVertexDegree(lb1));

    // directedGraph.removeVertex(lb2);

    // System.out.println(directedGraph.getNumberVertices());

    // System.out.println(directedGraph);

    // Character label = directedGraph.getEdgeLabel(lb3, lb1);

    // System.out.println(label);

    // assertThat(directedGraph.getVertex(lb3), not((Vertex<Integer, Double>)
    // null));
    // }

    // @Test
    // public void testAddThreeVerticesUndirected() {
    // undirectedGraph.addVertex(lb1);
    // undirectedGraph.addVertex(lb2);
    // undirectedGraph.addVertex(lb3);

    // System.out.println(undirectedGraph);

    // undirectedGraph.addEdge(lb1, lb2, 'B');
    // undirectedGraph.addEdge(lb3, lb1, 'C');

    // System.out.println(undirectedGraph);

    // System.out.println(undirectedGraph.getNumberVertices());

    // System.out.println("UNDIRECTED DEGREE: " +
    // undirectedGraph.getVertexDegree(lb1));

    // undirectedGraph.removeVertex(lb2);

    // System.out.println(undirectedGraph.getNumberVertices());

    // System.out.println(undirectedGraph);

    // Character label = undirectedGraph.getEdgeLabel(lb3, lb1);

    // System.out.println(label);

    // assertThat(undirectedGraph.getVertex(lb3), not((Vertex<Integer, Double>)
    // null));
    // }
}
