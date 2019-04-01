package hw6;

import java.util.*;

import hw6.MarvelParser.MalformedDataException;

import hw5.*;

/**
 * MarvelPaths class contains a method that builds
 * a graph with loaded data from file, and a method 
 * that finds the shortest path between two nodes
 * in the graph.
 */
public class MarvelPaths {

    /**
     * Reads data from file and builds a graph with the data
     * 
     * @param filename name of file to read
     * @throws IllegalArgumentException if filename == null
     * @throws MalformedDataException if data format is 
     * invalid to expected format
     */
    public static Graph<String, String> buildGraph(String filename) {
        if (filename == null) {
            throw new IllegalArgumentException("Invalid filename");
        }

        Graph<String, String> graph = new Graph<String, String>();
        Map<String, List<String>> books = new HashMap<String, List<String>>();
        Set<String> characters = new HashSet<String>();

        try {
            MarvelParser.parseData(filename, characters, books);
        } catch (MalformedDataException e) {
            e.printStackTrace();
        }

        // add character nodes to graph
        for (String character : characters) {
            graph.addNode(character);
        }

        // connect characters each other with every books
        for (String book : books.keySet()) {
            List<String> chars = books.get(book);
            int subIndex = 1;
            
            // creating edges with directed graph behavior
            for (String char1 : chars) {
                List<String> subChars = chars.subList(subIndex, chars.size());

                for (String char2 : subChars) {
                    // prevent self-connecting
                    if (!char1.equals(char2)) {
                        graph.addEdge(char1, char2, book);
                        graph.addEdge(char2, char1, book);
                    }
                }
                subIndex++;
            }
        }

        return graph;

    }

    /**
     * Finds the shortest path of given node(from) to node(to)
     * 
     * @param graph the graph that is used to find the shortest path
     * @param from Start node to search path
     * @param to Target node to search path
     * @requires graph != null && from != null && to != null
     * @return the shortest path between from-node and to-node, or null
     * if there is no path between them
     * @throws IllegalArgumentException if graph is null, or one of nodes is null,
     * or one of nodes is not in the graph
     */
    public static List<Edge<String, String>> findPath(
            Graph<String, String> graph, String from, String to) {

        if (graph == null) {
            throw new IllegalArgumentException("graph cannot be null");
        }
        if (from == null) {
            throw new IllegalArgumentException("'from' node cannot be null");
        }
        if (to == null) {
            throw new IllegalArgumentException("'to' node cannot be null");
        }
        if (!graph.containsNode(from)) {
            throw new IllegalArgumentException("'from' node : " + from + 
                    " is not in the graph (book)");
        }
        if (!graph.containsNode(to)) {
            throw new IllegalArgumentException("'to' node : " + to + 
                    " is not in the graph (book)");
        }
        // codes below are converted from provided Pseudocode (BFS algorithm)
        // stores nodes that are visited
        Queue<String> visited = new LinkedList<String>();
        
        // stores nodes as keys and paths of each node as values
        Map<String, List<Edge<String, String>>> paths = 
                new HashMap<String, List<Edge<String, String>>>();

        visited.add(from);
        paths.put(from, new ArrayList<Edge<String, String>>());

        while (!visited.isEmpty()) {
            String n = visited.poll();

            if (n.equals(to)) {
                return new ArrayList<Edge<String, String>>(paths.get(n));
            }

            // use TreeSet to construct with comparator of edge
            // this code is from hw5/test/HW5TestDriver.java
            Set<Edge<String, String>> edges = new TreeSet<Edge<String, String>>(
                    new Comparator<Edge<String, String>>() {
                public int compare(Edge<String, String> edge1, Edge<String, String> edge2) {
                    if (!(edge1.getChild().equals(edge2.getChild()))) {
                        return edge1.getChild().compareTo(edge2.getChild());
                    }
                    if (!edge1.getLabel().equals(edge2.getLabel())) {
                        return edge1.getLabel().compareTo(edge2.getLabel());
                    }
                    return 0;
                }
            });

            edges.addAll(graph.getEdges(n));
            for (Edge<String, String> edge : edges) {
                // if node is not visited, then store this node, and
                // paths in paths(Map), and visited(Queue)
                if (!paths.containsKey(edge.getChild())) {
                    List<Edge<String, String>> path1 = paths.get(n);
                    List<Edge<String, String>> path2 = new ArrayList<Edge<String, String>>(path1);

                    path2.add(edge);
                    paths.put(edge.getChild(), path2);
                    visited.add(edge.getChild());
                }
            }
        }
        return null;
    }

    /**
     * Main method to run MarvelPaths, this program takes user inputs
     * and shows results on the inputs.
     * 
     * @param args
     */
    public static void main(String[] args) {
        Graph<String, String> graph = MarvelPaths.buildGraph("src/hw6/data/marvel.tsv");

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
                List<Edge<String, String>> paths = MarvelPaths.findPath(graph, from, to);

                String str = "path from " + from + " to " + to + ":";
                String curFrom = from;

                if (paths == null) {
                    str += "\n" + "no path found";
                } else {
                    for (Edge<String, String> edge : paths) {
                        str += "\n" + curFrom + " to " + edge.getChild() + 
                                " via " + edge.getLabel();
                        curFrom = edge.getChild();
                    }
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