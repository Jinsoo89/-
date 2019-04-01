package hw5.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import hw5.Edge;



/**
 * This class contains a set of test cases that can be used to test the
 * implementation of the Graph class.
 * 
 * Some Test Methods are relatively longer in order to simulate proper stack functionality
 */
public class EdgeTest {

    private final String parent = "A";
    private final String child = "B";
    private final String label = "A to B";
    
    // test constructor
    
    @Test(expected = IllegalArgumentException.class)
    public void testConstructAnEdgeWithNullParent() {
        Edge<String, String> testEdge = new Edge<String, String>(null, child, label);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testConstructAnEdgeWithNullChild() {
        Edge<String, String> testEdge = new Edge<String, String>(parent, null, label);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testConstructAnEdgeWithNullNodes() {
        Edge<String, String> testEdge = new Edge<String, String>(null, null, label);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testConstructAnEdgeWithNullLabel() {
        Edge<String, String> testEdge = new Edge<String, String>(parent, child, null);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testConstructAnEdgeWithNullNodesAndLabel() {
        Edge<String, String> testEdge = new Edge<String, String>(null, null, null);
    }
    
    @Test
    public void testGetParent() {
        Edge<String, String> edge = new Edge<String, String>(parent, child, label);
        
        assertEquals(edge.getParent(), parent);
    }
    
    @Test
    public void testGetChild() {
        Edge<String, String> edge = new Edge<String, String>(parent, child, label);
        
        assertEquals(edge.getChild(), child);
    }
    
    @Test
    public void testGetLabel() {
        Edge<String, String> edge = new Edge<String, String>(parent, child, label);
        
        assertEquals(edge.getLabel(), label);
    }
    
    @Test
    public void testEqualNodes() {
        Edge<String, String> edge1 = new Edge<String, String>(parent, child, label);
        Edge<String, String> edge2 = new Edge<String, String>(parent, child, label);
        
        assertTrue(edge1.equals(edge2));
    }
    
    @Test
    public void testNoNEqualNodesOneWayEdge() {
        Edge<String, String> edge1 = new Edge<String, String>(parent, child, label);
        Edge<String, String> edge2 = new Edge<String, String>(child, parent, label);
        
        assertFalse(edge1.equals(edge2));
    }
    
    @Test
    public void testHashCodeSameEdge() {
        Edge<String, String> edge1 = new Edge<String, String>(parent, child, label);
        Edge<String, String> edge2 = new Edge<String, String>(parent, child, label);
        
        assertEquals(edge1.hashCode(), edge2.hashCode());
    }
    
    @Test
    public void testHashCodeDifferentEdgesPointsToEachOther() {
        Edge<String, String> edge1 = new Edge<String, String>(parent, child, label);
        Edge<String, String> edge2 = new Edge<String, String>(child, parent, label);
        
        assertNotEquals(edge1.hashCode(), edge2.hashCode());
    }
    
    @Test
    public void testToString() {
        Edge<String, String> edge1 = new Edge<String, String>(parent, child, label);
        
        assertEquals(edge1.toString(), "B(A to B)");
    }
}
