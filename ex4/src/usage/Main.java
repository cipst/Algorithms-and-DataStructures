package usage;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Set;

import dijkstra.Dijkstra;
import graph.Graph;
import graph.GraphType;
import graph.Vertex;

public class Main {

    private static final Charset ENCODING = StandardCharsets.UTF_8;

    private static void loadGraph(String filepath, Graph<String, String> graph)
            throws IOException {
        System.out.println("\nLoading data from file...");

        Path inputFilePath = Paths.get(filepath);
        try (BufferedReader fileInputReader = Files.newBufferedReader(inputFilePath, ENCODING)) {
            String line = null;
            while ((line = fileInputReader.readLine()) != null) {
                String[] lineElements = line.split(",");

                graph.addVertex(lineElements[0]);
                graph.addVertex(lineElements[1]);
            }
        }
        try (BufferedReader fileInputReader = Files.newBufferedReader(inputFilePath, ENCODING)) {
            String line = null;
            while ((line = fileInputReader.readLine()) != null) {
                String[] lineElements = line.split(",");

                graph.addEdge(lineElements[0], lineElements[1], lineElements[0] + "-" + lineElements[1]);

                // if (!graph.addEdge(lineElements[1], lineElements[0], lineElements[1] + "-" +
                // lineElements[0]))
                // System.out.println("ERROR ADD EDGE: " + lineElements[1] + "-" +
                // lineElements[0]);
                // else
                // System.out.println("SUCCESS ADD EDGE: " + lineElements[1] + "-" +
                // lineElements[0]);

                graph.getEdge(lineElements[0], lineElements[1]).setWeight(Double.parseDouble(lineElements[2]));

                // graph.getEdge(lineElements[1],
                // lineElements[0]).setWeight(Double.parseDouble(lineElements[2]));
            }
        }
        System.out.println("Data loaded\n");
    }

    public static void main(String[] args) {
        Graph<String, String> graph = new Graph<>();
        try {
            // System.out.println(graph);
            loadGraph(args[0], graph);
            // System.out.println(graph);

            System.out.println(graph.getNumberEdges());

            Set<Vertex<String, String>> ris = Dijkstra.dijkstra(graph, "catania");

            printDijkstraResult(ris);
            System.out.println("\nDistance between torino and catania: " + findDistance(ris, "torino"));
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        // Graph<String, String> graph = new Graph<>();
        // init(graph);
        // System.out.println(graph);
        // try {
        // System.out.println("RUNNING DIJKSTRA...");
        // Set<Vertex<String, String>> ris = Dijkstra.dijkstra(graph, "0");
        // printDijkstraResult(ris);
        // System.out.println("\nDistance between 0 and 4: " + findDistance(ris, "4"));
        // } catch (Exception e) {
        // e.printStackTrace(System.out);
        // }
    }

    private static double findDistance(Set<Vertex<String, String>> ris, String label) {
        Iterator<Vertex<String, String>> i = ris.iterator();
        while (i.hasNext()) {
            Vertex<String, String> v = i.next();
            if (v.getLabel().equals(label))
                return v.getDistance();
        }
        return Double.MAX_VALUE;
    }

    private static void printDijkstraResult(Set<Vertex<String, String>> ris) {
        System.out.println("DIJKSTRA RESULT: ");
        for (Vertex<String, String> v : ris) {
            // if (v.getLabel().equals("catania"))
            // System.out.println(v.getLabel() + " | " + v.getDistance() + " | " +
            // v.getPi());
            System.out.print(v.getLabel() + ": " + v.getDistance());
            // printPath(v);
            System.out.println();
        }
    }

    private static void printPath(Vertex<String, String> v) {
        if (v != null) {
            printPath(v.getPi());
            System.out.print(v + " ");
        } else {
            // System.out.print(v);
        }
    }

    private static void init(Graph<String, String> g) {
        System.out.println("INIT GRAPH...");
        g.addVertex("0");
        g.addVertex("1");
        g.addVertex("2");
        g.addVertex("3");
        g.addVertex("4");
        g.addVertex("5");
        g.addVertex("6");
        g.addVertex("7");

        g.addEdge("0", "1", "0-1");
        g.addEdge("0", "3", "0-3");
        g.addEdge("1", "6", "1-6");
        g.addEdge("2", "1", "2-1");
        g.addEdge("2", "5", "2-5");
        g.addEdge("3", "5", "3-5");
        g.addEdge("3", "7", "3-7");
        g.addEdge("4", "6", "4-6");
        g.addEdge("5", "3", "5-3");
        g.addEdge("5", "7", "5-7");
        g.addEdge("6", "4", "6-4");
        g.addEdge("6", "5", "6-5");

        g.getEdge("0", "1").setWeight(3);
        g.getEdge("0", "3").setWeight(9);
        g.getEdge("1", "6").setWeight(5);
        g.getEdge("2", "1").setWeight(5);
        g.getEdge("2", "5").setWeight(7);
        g.getEdge("3", "5").setWeight(7);
        g.getEdge("3", "7").setWeight(5);
        g.getEdge("4", "6").setWeight(2);
        g.getEdge("5", "3").setWeight(4);
        g.getEdge("5", "7").setWeight(5);
        g.getEdge("6", "4").setWeight(9);
        g.getEdge("6", "5").setWeight(3);
    }

}
