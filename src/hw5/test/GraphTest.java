package hw5.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import hw5.Edge;
import hw5.Graph;

/**
 * This class contains a set of test cases that can be used to test the
 * implementation of the Graph class.
 * 
 * Some Test Methods are relatively longer in order to simulate proper stack
 * functionality
 */
public class GraphTest {

    private final String node1 = "A";
    private final String node2 = "B";
    private final String node3 = "C";
    private final Edge<String, String> edge1 = new Edge<String, String>(node1, node1, "A to A");
    private final Edge<String, String> edge2 = new Edge<String, String>(node1, node2, "A to B");
    private final Edge<String, String> edge3 = new Edge<String, String>(node2, node3, "B to C");
    private final Edge<String, String> edge4 = new Edge<String, String>(node3, node1, "C to A");

    private Graph<String, String> graph;
    private Set<String> nodes;
    private Set<Edge<String, String>> edges;

    @Before
    public void setTest() {
        graph = new Graph<String, String>();
        nodes = new HashSet<String>();
        edges = new HashSet<Edge<String, String>>();
    }

    // construction

    @Test
    public void testSizeZeroAfterConstructed() {
        assertEquals(graph.getSize(), 0);
    }

    @Test
    public void testEmptyGraphAfterConstructed() {
        assertTrue(graph.isEmpty());
    }

    @Test
    public void testNodesAfterConstructed() {
        assertEquals(graph.getNodes(), nodes);
    }

    @Test
    public void testToStringAfterConstructed() {
        assertEquals("{}", graph.toString());
    }

    // adding expected IllegalArgumentException

    @Test(expected = IllegalArgumentException.class)
    public void testAddNullNodeToGraph() {
        graph.addNode(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddNEdgeWithullParentEdgeToGraph() {
        graph.addNode(node1);
        graph.addEdge(null, node1, "test");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddEdgeWithNullChildNodeToGraph() {
        graph.addNode(node1);
        graph.addEdge(node1, null, "test");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddEdgeWithNullNodesToGraph() {
        graph.addEdge(null, null, "test");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddEdgeWithNullLabelToGraph() {
        graph.addNode(node1);
        graph.addNode(node2);
        graph.addEdge(node1, node2, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddEdgeWithNonExistsNodeToGraph() {
        graph.addNode(node1);
        graph.addNode(node2);
        graph.addEdge(node1, node3, "test");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddEdgeWithNullNodesAndLabelToGraph() {
        graph.addEdge(null, null, null);
    }

    // test contains

    @Test
    public void testContainNode() {
        graph.addNode(node1);

        assertTrue(graph.containsNode(node1));
    }

    @Test
    public void testContainNodeWithNodeNotInGraph() {
        graph.addNode(node1);

        assertFalse(graph.containsNode(node2));
    }

    @Test
    public void testContainEdge() {
        graph.addNode(node1);
        graph.addNode(node2);
        graph.addEdge(node1, node2, "A to B");

        assertTrue(graph.containsEdge(edge2));
    }

    @Test
    public void testContainEdgeWithNonExistEdge() {
        graph.addNode(node1);
        graph.addNode(node2);
        graph.addEdge(node1, node2, "A to B");

        assertFalse(graph.containsEdge(edge3));
    }

    // test adding in success

    @Test
    public void testAddNode() {
        graph.addNode(node1);

        assertTrue(graph.containsNode(node1));
    }

    @Test
    public void testAddTwoNodes() {
        graph.addNode(node1);
        graph.addNode(node2);

        assertTrue(graph.containsNode(node1));
        assertTrue(graph.containsNode(node2));
    }

    @Test
    public void testAddSingleNodeAndEdgetoConnectItSelf() {
        graph.addNode(node1);
        graph.addEdge(node1, node1, "A to A");

        assertTrue(graph.containsEdge(edge1));
    }

    @Test
    public void testAddTwoDifferentNodesAndAnEdge() {
        graph.addNode(node1);
        graph.addNode(node2);
        graph.addEdge(node1, node2, "A to B");

        assertTrue(graph.containsEdge(edge2));
    }

    @Test
    public void testAddThreeDifferentNodesAndTwoEdges() {
        graph.addNode(node1);
        graph.addNode(node2);
        graph.addNode(node3);
        graph.addEdge(node1, node2, "A to B");
        graph.addEdge(node2, node3, "B to C");
        graph.addEdge(node3, node1, "C to A");

        assertTrue(graph.containsEdge(edge2));
        assertTrue(graph.containsEdge(edge3));
        assertTrue(graph.containsEdge(edge4));
    }

    @Test
    public void testSizeAfterAddNode() {
        graph.addNode(node1);

        assertEquals(graph.getSize(), 1);
    }

    @Test
    public void testSizeAfterAddSameNodeTwice() {
        graph.addNode(node1);
        graph.addNode(node1);

        assertEquals(graph.getSize(), 1);
    }

    @Test
    public void testSizeAfterAddmultipleDifferentNodes() {
        graph.addNode(node1);
        graph.addNode(node2);

        assertEquals(graph.getSize(), 2);
    }

    @Test
    public void testIsEmptyAfterAddNode() {
        graph.addNode(node1);

        assertFalse(graph.isEmpty());
    }

    // test getMethods (getNodes, getEdges)

    @Test
    public void testGetNodesNothingIsAdded() {
        assertEquals(graph.getNodes(), nodes);
    }

    @Test
    public void testGetNodesAfterAddOneNode() {
        graph.addNode(node1);
        nodes.add(node1);

        assertEquals(graph.getNodes(), nodes);
    }

    @Test
    public void testGetNodesAfterAddMultipleNodes() {
        graph.addNode(node1);
        graph.addNode(node2);
        graph.addNode(node3);
        nodes.add(node3);
        nodes.add(node2);
        nodes.add(node1);

        assertEquals(graph.getNodes(), nodes);
    }

    @Test
    public void testGetEdgesNothingIsAdded() {
        graph.addNode(node1);

        assertEquals(graph.getEdges(node1), edges);
    }

    @Test
    public void testGetEdgesAfterAddOne() {
        graph.addNode(node1);
        graph.addEdge(node1, node1, "A to A");
        edges.add(edge1);

        assertEquals(graph.getEdges(node1), edges);
    }

    @Test
    public void testGetEdgesAfterAddMultiple() {
        graph.addNode(node1);
        graph.addNode(node2);
        graph.addEdge(node1, node1, "A to A");
        graph.addEdge(node1, node2, "A to B");
        edges.add(edge1);
        edges.add(edge2);

        assertEquals(graph.getEdges(node1), edges);
    }

    // test string expression

    @Test
    public void testToStringAfterAddOneNodeAndEdge() {
        graph.addNode(node1);
        graph.addEdge(node1, node1, "A to A");

        assertEquals(graph.toString(), "{A=[A(A to A)]}");
    }
}
