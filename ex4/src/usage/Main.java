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
import graph.Edge;
import graph.Graph;
import graph.GraphException;
import graph.GraphType;
import graph.Vertex;

public class Main {

    private static final Charset ENCODING = StandardCharsets.UTF_8;

    private static void loadGraph(String filepath, Graph<String, String> graph)
            throws IOException, GraphException {
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
        try (BufferedReader fileInputReader = Files.newBufferedReader(inputFilePath,
                ENCODING)) {
            String line = null;
            while ((line = fileInputReader.readLine()) != null) {
                String[] lineElements = line.split(",");

                try {
                    graph.addEdge(lineElements[0], lineElements[1], lineElements[0] + "-" +
                            lineElements[1]);
                    graph.addEdge(lineElements[1], lineElements[0], lineElements[1] + "-" +
                            lineElements[0]);

                    graph.getEdge(lineElements[0],
                            lineElements[1]).setWeight(Double.parseDouble(lineElements[2]));

                    graph.getEdge(lineElements[1],
                            lineElements[0]).setWeight(Double.parseDouble(lineElements[2]));
                } catch (Exception e) {
                    e.printStackTrace(System.err);
                }
            }
        }
        System.out.println("Data loaded\n");
    }

    public static void main(String[] args) {
        Graph<String, String> graph = new Graph<>();
        try {
            loadGraph(args[0], graph);

            Set<Vertex<String, String>> ris = Dijkstra.dijkstra(graph, "torino");

            // printDijkstraResult(ris);

            System.out.println("\nDistance between torino and catania: " +
                    (findDistance(ris, "catania") / 1000) + " Km");
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    private static double findDistance(Set<Vertex<String, String>> ris, String label) {
        Iterator<Vertex<String, String>> i = ris.iterator();
        while (i.hasNext()) {
            Vertex<String, String> v = i.next();

            if (v.getLabel().equals(label))
                return v.getDistance();
        }
        return 0;
    }

    private static void printDijkstraResult(Set<Vertex<String, String>> ris) {
        System.out.println("DIJKSTRA RESULT: ");
        for (Vertex<String, String> v : ris) {
            System.out.print(v.getLabel() + ": " + v.getDistance());
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
}
