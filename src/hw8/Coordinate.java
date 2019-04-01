package hw8;

/**
 * <b>Coordinate</b> is an <b>immutable</b> class that represents
 * a pair of Cartesian coordinate.
 */
public class Coordinate {
    
    // Representation Invariant:
    //  x != null
    //  y != null
    
    // Abstract Function:
    //  AF(this) = this.x represents x coordinate point of this, ex: (0.0)
    //             this.y represents y coordinate point of this, ex: (1.1)
    //             then this = (0.0, 1.1)
    
    private final Double x, y;
    
    /*
     * constant debugging variable for checkRep()
     */
    private static final boolean CHECK_DEBUG = false;
    
    /**
     * construct coordinate
     * 
     * @param x x coordinate point
     * @param y y coordinate point
     */
    public Coordinate(double x, double y) {
        this.x = x;
        this.y = y;
        checkRep();
    }
    
    /**
     * returns x coordinate of this coordinate
     * 
     * @return x x coordinate of this coordinate
     */
    public Double getX() {
        checkRep();
        return x;
    }
    
    /**
     * returns y coordinate of this coordinate
     * 
     * @return y y coordinate of this coordinate
     */
    public Double getY() {
        checkRep();
        return y;
    }
    
    /**
     * Check to see if two objects are equal
     * 
     * @param o Object to be compared with this coordinate
     * @return true if o is same coordinate
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Coordinate)) {
//            checkRep();
            return false;
        }
        Coordinate c = (Coordinate) o;
        
        checkRep();
        return this.getX().equals(c.getX()) && this.getY().equals(c.getY()); 
    }
    
    /**
     * Returns a hash code of this coordinate
     * 
     * @return hash code of this coordinate
     */
    @Override
    public int hashCode() {
        checkRep();
        return x.hashCode() * 37 + y.hashCode() * 31;
    }
    
    /**
     * Check representation invariant holds
     */
    private void checkRep() {
        if (CHECK_DEBUG) {
            assert (x != null) : "x cannot be null";
            assert (y != null) : "y cannot be null";
            assert (x >= 0) : "x cannot be negative";
            assert (y >= 0) : "y cannot be negative";
        }
    }
}
