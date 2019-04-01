package hw8.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import hw8.Coordinate;

/**
 * CoordinateTest contains test cases to test Coordinate class
 */
public class CoordinateTest {
    private Coordinate cord;
    
    @Before
    public void setUp() {
        cord = new Coordinate(0.01, 1.01);
    }
    
    @Test
    public void testConstructorWithValidValue() {
        new Coordinate(1, 1);
    }
    
    // test getter methods in Coordinate class
    
    @Test
    public void testGetterX() {
        assertTrue(cord.getX() == 0.01);
    }
    
    @Test
    public void testGetterXwithNull() {
        assertTrue(cord.getX() != null);
    }
    
    @Test
    public void testGetterY() {
        assertTrue(cord.getY() == 1.01);
    }
    
    @Test
    public void testGetterYwithNull() {
        assertTrue(cord.getY() != null);
    }
    
    // test equals method
    
    @Test
    public void testEqualsWithSameCoordinate() {
        Coordinate same = new Coordinate(0.01, 1.01);
        assertTrue(cord.equals(same));
    }
    
    @Test
    public void testEqualsWithNotSameCoordinate() {
        Coordinate same = new Coordinate(1.01, 0.01);
        assertFalse(cord.equals(same));
    }
    
    @Test
    public void testEqualsWithNull() {
        assertFalse(cord.equals(null));
    }
    
    // test hashCode
    
    @Test
    public void testHashCode() {
        Coordinate same = new Coordinate(0.01, 1.01);
        assertEquals(cord.hashCode(), same.hashCode());
    }
    
    @Test
    public void testHashCodeWithDifferentCoordinateButSameValues() {
        Coordinate same = new Coordinate(1.01, 0.01);
        assertNotEquals(cord.hashCode(), same.hashCode());
    }
}
