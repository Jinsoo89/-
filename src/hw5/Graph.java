package hw5;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * </b>Graph</b> represents a <b>mutable</b>, and directed graph, which is a
 * collection of nodes and edges in generic to implement various types of graph with
 * nodes, and edges. In directed graph, each edge connects two
 * nodes in one way. Edge a = <A, B> indicates that B (children) is directly
 * connected from A (parent), the opposite is Edge b = <B, A>. Graph would have
 * edges a, b at the same time.
 * 
 * @param <T1> the type of nodes
 * @param <T2> the type of the label of edge, which this graph contains
 */
public class Graph<T1, T2> {

    // Representation Invariant:
    // graph != null (this.graph is non-null) is equivalent to nodes != null
    // All nodes in graph are not null
    // All edges in graph are not null
    // each node in graph has non-null collection of edges.

    // Abstract Function:
    // AF(this) = directed graph g that represents
    // if g is an empty graph, g = {}
    // if n is node without edges in g.nodes, {n=[], ... }
    // otherwise, g = {n=[e1, e2], .... } for e1, e2 are edges from n (parent node).

    private Map<T1, HashSet<Edge<T1, T2>>> nodes;

    /*
     * constant debugging variable for checkRep()
     */
    private final static boolean CHECK_DEBUG = false;

    /**
     * Constructs new graph with empty nodes
     */
    public Graph() {
        nodes = new HashMap<T1, HashSet<Edge<T1, T2>>>();
        checkRep();
    }

    /**
     * Add node to the graph
     * 
     * @param node
     * @modifies this graph has an additional node
     * @effects node is added to the graph
     * @throws IllegalArgumentException
     *             if node is null
     */
    public void addNode(T1 node) {
        checkRep();

        if (node == null) {
            throw new IllegalArgumentException("node cannot be a null");
        }

        if (!containsNode(node)) {
            nodes.put(node, new HashSet<Edge<T1, T2>>());
        }

        checkRep();
    }

    /**
     * Add edge to the graph
     * 
     * @param parent parent (from) of new edge
     * @param child child (to | destination) of new edge
     * @param label label of new edge
     * @modifies this graph has an additional edge, and nodes if parent and child
     *           are in the graph
     * @effects adds new edge with parent, child nodes if they are in the graph
     *          currently
     * @throws IllegalArgumentException if parent or child or label is null
     */
    public void addEdge(T1 parent, T1 child, T2 label) {
        checkRep();

        if (parent == null || child == null) {
            throw new IllegalArgumentException("node cannot be a null");
        }

        if (label == null) {
            throw new IllegalArgumentException("label cannot be a null");
        }

        if (!nodes.containsKey(parent)) {
            throw new IllegalArgumentException("parent node is not in this graph");
        }

        if (!nodes.containsKey(child)) {
            throw new IllegalArgumentException("child node is not in this graph");
        }

        Edge<T1, T2> newEdge = new Edge<T1, T2>(parent, child, label);
        
        if (!containsEdge(newEdge)) {
            nodes.get(parent).add(newEdge);
        }

        checkRep();
    }

    /**
     * Check if node is currently in the graph
     * 
     * @param node a node to check
     * @requires node != null
     * @return true if the node is in the graph, false if it is not
     * @throws IllegalArgumentException
     *             if checking node is null
     */
    public boolean containsNode(T1 node) {
        checkRep();

        if (node == null) {
            throw new IllegalArgumentException("Checking node cannot be null");
        }

        checkRep();
        return nodes.containsKey(node);
    }

    /**
     * Get a set of all current nodes in the graph
     * 
     * @return a set of all current nodes in the graph as an unmodifiable set
     */
    public Set<T1> getNodes() {
        checkRep();
        return Collections.unmodifiableSet(nodes.keySet());
    }
    
    /**
     * Check if this graph contains the edge
     * 
     * @param edge edge to check existence
     * @return true if the specified edge is in the graph
     * @throws IllegalArgumentException if edge is null
     */
    public boolean containsEdge(Edge<T1, T2> edge) {
        checkRep();

        if (edge == null) {
            throw new IllegalArgumentException("edge to be checked cannot be null");
        }

        checkRep();
        return nodes.get(edge.getParent()).contains(edge);
    }

    /**
     * Get a set of edges of specific node (parent node)
     * 
     * @param node a node, which is parent node of the edges
     * @return a set of all edges from the node.
     * @throws IllegalArgumentException if passing node is null
     */
    public Set<Edge<T1, T2>> getEdges(T1 node) {
        checkRep();

        if (node == null) {
            throw new IllegalArgumentException("cannot check null node");
        }

        checkRep();
        return Collections.unmodifiableSet(nodes.get(node));
    }

    /**
     * Get a size of this graph
     * 
     * @return number of nodes in this graph
     */
    public int getSize() {
        checkRep();
        return nodes.size();
    }

    /**
     * Check if this graph is empty
     * 
     * @return true if the graph is empty, false if it is not
     */
    public boolean isEmpty() {
        checkRep();
        return nodes.isEmpty();
    }

    /**
     * String expression of this graph
     * 
     * @return a string expression of this graph
     */
    public String toString() {
        checkRep();
        return nodes.toString();
    }

    /**
     * Check if the representation invariant holds
     * 
     */
    private void checkRep() {
        // Representation Invariant:
        // graph != null (this.graph is non-null)
        if (CHECK_DEBUG) {
            assert (nodes != null) : "the graph cannot be null";

            Set<T1> tempNodes = nodes.keySet();
            // All nodes in graph are not null
            for (T1 node : tempNodes) {
                assert (node != null) : "node cannot be null";
                
                HashSet<Edge<T1, T2>> edges = nodes.get(node);
                assert (edges != null) : "each node in graph has non-null collection of edges";
                
                // All edges in graph are not null
                for (Edge<T1, T2> edge : edges) {
                    assert (edge != null) : "null edge cannot be in the graph";
                }
            }
        }
    }
}
