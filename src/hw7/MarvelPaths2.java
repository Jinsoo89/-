package hw7;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;
import java.util.Comparator;

import hw5.Edge;
import hw5.Graph;
import hw6.MarvelParser;
import hw6.MarvelParser.MalformedDataException;

/**
 * MarvelPaths2 class contains a method that builds
 * a graph with loaded data from file, and a method 
 * that finds the minimum cost path between two nodes
 * in the graph.
 */
public class MarvelPaths2 {

    /**
     * Reads data from file and builds a graph with the data
     * 
     * @param filename name of file to read
     * @throws IllegalArgumentException if filename == null
     * @throws MalformedDataException if data format is 
     * invalid to expected format
     */
    public static Graph<String, Double> buildGraph(String fileName) {
        if (fileName == null) {
            throw new IllegalArgumentException("Invalid file name");
        }

        Graph<String, Double> graph = new Graph<String, Double>();
        Map<String, List<String>> books = new HashMap<String, List<String>>();
        Set<String> characters = new HashSet<String>();

        try {
            MarvelParser.parseData(fileName, characters, books);
        } catch (MalformedDataException e) {
            e.printStackTrace();
        }

        for (String character : characters) {
            graph.addNode(character);
        }
        
        Map<Edge<String, Double>, Integer> weightedEdges = 
                new HashMap<Edge<String, Double>, Integer>();

        // iterate through each book
        for (String book : books.keySet()) {
            List<String> chars = books.get(book);

            for (String parent : chars) {
                for (String child : chars) {
                    Edge<String, Double> edge = 
                            new Edge<String, Double>(parent, child, 0.0);

                    if (!weightedEdges.containsKey(edge)) {
                        weightedEdges.put(edge, 0);
                    }

                    if (!edge.getParent().equals(edge.getChild())) {
                        weightedEdges.put(edge, weightedEdges.get(edge) + 1);
                    }
                }
            }
        }
        // connect characters each other with every books
        for (Edge<String, Double> edge : weightedEdges.keySet()) {
            double weight = weightedEdges.get(edge);

            if (Double.compare(weight, 0.0) != 0) {
                weight = 1 / weight;
            }
            graph.addEdge(edge.getParent(), edge.getChild(), weight);

        }

        return graph;
    }
    
    /**
     * Finds the minimum cost path of given node(from) to node(to)
     * 
     * @param graph the graph that is used to find the shortest path
     * @param from Start node to search path
     * @param to Target node to search path
     * @return the minimum cost path between from-node and to-node, or null
     * if there is no path between them
     * @throws IllegalArgumentException if graph is null, or one of nodes is null,
     * or one of nodes is not in the graph
     */
    public static <T> List<Edge<T, Double>> findMinCostPath(
            Graph<T, Double> graph, T from, T to) {
        
        if (graph == null) {
            throw new IllegalArgumentException("graph cannot be null");
        }
        if (from == null || to == null) {
            throw new IllegalArgumentException("from or to cannot be null");
        }
        if (!graph.containsNode(from)) {
            throw new IllegalArgumentException("from node is not in graph");
        }
        if (!graph.containsNode(to)) {
            throw new IllegalArgumentException("to node is not in graph");
        }
        
        // follows provided Pseudocode for Dijkstra's algorithm
        // uses PriorityQueue from java.util with comparator that
        // compares labels (weight of edge) of edges
        PriorityQueue<ArrayList<Edge<T, Double>>> active = new PriorityQueue<ArrayList<
                Edge<T, Double>>>(20, new Comparator<ArrayList<Edge<T, Double>>>() {
                            public int compare(ArrayList<Edge<T, Double>> p1, 
                                    ArrayList<Edge<T, Double>> p2) {
                                Edge<T, Double> t1 = p1.get(p1.size() - 1);
                                Edge<T, Double> t2 = p2.get(p2.size() - 1);
                                
                                if (!t1.getLabel().equals(t2.getLabel())) {
                                    return t1.getLabel().compareTo(t2.getLabel());
                                } else {
                                    return p1.size() - p2.size();
                                }
                            }
                        });
        Set<T> finished = new HashSet<T>();
        
        ArrayList<Edge<T, Double>> start = new ArrayList<Edge<T, Double>>();
        start.add(new Edge<T, Double>(from, from, 0.0));
        active.add(start);
        
        while (!active.isEmpty()) {
            // minPath is the lowest-cost path in active and is the
            // minimum cost path to some node
            ArrayList<Edge<T, Double>> minPath = active.poll();
            // minDest is destination of minPath
            T minDest = minPath.get(minPath.size() - 1).getChild();
            // minCost is cost (weight) of minPath
            Double minCost = minPath.get(minPath.size() - 1).getLabel();
            
            // the destination of minPath is equal to entered destination
            if (minDest.equals(to)) {
                return minPath;
            }
            // minDest is already known, skip this iteration
            if (finished.contains(minDest)) {
                continue;
            }
            for (Edge<T, Double> edge : graph.getEdges(minDest)) {
                // if we don't know the minimum cost path from start to child, examine
                // the path we have just found
                if (!finished.contains(edge.getChild())) {
                    Double cost = minCost + edge.getLabel();
                    ArrayList<Edge<T, Double>> updatedPath = 
                            new ArrayList<Edge<T, Double>>(minPath);
                    
                    updatedPath.add(new Edge<T, Double>(
                            edge.getParent(), edge.getChild(), cost));
                    active.add(updatedPath);
                }
            }
            finished.add(minDest);
        }
        return null;
    }
    
    /**
     * Main method to run MarvelPaths2, this program takes user inputs
     * and shows results on the inputs.
     * 
     * @param args
     */
    public static void main(String[] args) {
        Graph<String, Double> graph = MarvelPaths2.buildGraph("src/hw7/data/soccerPlayers.tsv");

        System.out.println("----------- Welcome to Marvel World!! -----------");
        System.out.println("-------------------------------------------------");

        System.out.println("---- You can find relationship paths between ----");
        System.out.println("---- Two characters in Marvel World          ----");

        String from, to;
        Scanner user_input = new Scanner(System.in);

        while (true) {
            System.out.print("\n" + "Enter a character: ");
            from = user_input.nextLine();

            System.out.print("Enter another character: ");
            to = user_input.nextLine();

            if ((!graph.containsNode(from)) && (!graph.containsNode(to))) {
                System.out.println("unknown character " + from);
                System.out.println("unknown character " + to);
            } else if (!graph.containsNode(from)) {
                System.out.println("unknown character " + from);
            } else if (!graph.containsNode(to)) {
                System.out.println("unknown character " + to);
            } else {
                List<Edge<String, Double>> paths = findMinCostPath(graph, from, to);

                String str = "path from " + from + " to " + to + ":";
                String curFrom = from;

                if (paths == null) {
                    str += "\n" + "no path found";
                } else {
                    Double cost = 0.0;
                    paths = paths.subList(1, paths.size());
                    
                    for (Edge<String, Double> edge : paths) {
                        str += "\n" + curFrom + " to " + edge.getChild() + 
                                " with weight " + String.format("%.3f", edge.getLabel() - cost);
                        cost = edge.getLabel();
                        curFrom = edge.getChild();
                    }
                    str += "\n" + "total cost: " + String.format("%.3f", cost);
                }

                System.out.println(str);
            }
            
            // ask user to re-attempt
            System.out.print("\n" + "Another try? (Only enter 'y' to continue): ");
            String entered = user_input.nextLine();

            if (!(entered.equals("y") || entered.equals("Y"))) {
                break;
            }
        }

        user_input.close();
        System.out.println("\n" + "----------- Good Bye!! Marvel World!! -----------");
        System.out.println("-------------------------------------------------");

    }
}
