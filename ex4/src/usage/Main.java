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

        /* ADD ALL VERTICES */
        try (BufferedReader fileInputReader = Files.newBufferedReader(inputFilePath, ENCODING)) {
            String line = null;
            while ((line = fileInputReader.readLine()) != null) {
                String[] lineElements = line.split(",");

                graph.addVertex(lineElements[0]);
                graph.addVertex(lineElements[1]);
            }
        }

        /* ADD ALL EDGES + WEIGHTS */
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

            String srcLabel = "torino";
            String dstLabel = "catania";

            long nanoTime1 = System.nanoTime();
            Set<Vertex<String, String>> ris = Dijkstra.dijkstra(graph, srcLabel);
            long nanoTime2 = System.nanoTime();

            long runTimeInNanoSeconds = (nanoTime2 - nanoTime1);
            System.out
                    .format("Time taken by Dijkstra: %.2f milliseconds\n\n", (double) (runTimeInNanoSeconds) / 1000000);

            // printDijkstraResult(ris);

            findDistance(ris, srcLabel, dstLabel);
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    private static void findDistance(Set<Vertex<String, String>> ris, String srcLabel, String dstLabel) {
        Iterator<Vertex<String, String>> i = ris.iterator();
        while (i.hasNext()) {
            Vertex<String, String> v = i.next();

            if (v.getLabel().equals(dstLabel)) {
                System.out.format("Distance between %s and %s : ~%.2f Km\n\n", srcLabel, dstLabel,
                        (v.getDistance() / 1000));
                return;
            }
        }

        System.out.format("Distance between %s and %s : %f Km\n\n", srcLabel, dstLabel, Double.POSITIVE_INFINITY);
    }

    private static void printDijkstraResult(Set<Vertex<String, String>> ris) {
        System.out.println("DIJKSTRA RESULT: ");
        for (Vertex<String, String> v : ris) {
            System.out.println(v.getLabel() + ": " + v.getDistance());
        }
    }

    private static void printPath(Vertex<String, String> v) {
        if (v != null) {
            printPath(v.getPi());
            System.out.print(v + " ");
        }
    }
}
