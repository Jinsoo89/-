package hw5;


/**
 * <b>Edge</b> represents an <b>immutable</b> edge between two nodes in directed graph.
 * Edge is a one way connection between parent node (from) to child node (to).
 * 
 * @param <T1> type of nodes of this edge
 * @param <T2> type of label of this edge
 */
public class Edge<T1, T2> {

    // Representation Invariant:
    //    parent != null
    //    child != null
    //    label != null
    
    // Abstract Function:
    //    AF(this) = An edge e with label l, parent p, and child c such that
    //               this.label = l
    //               this.parent = p
    //               this.child = c
    
    private final T1 parent;
    private final T1 child;
    private final T2 label;
    
    /*
     * constant debugging variable for checkRep()
     */
    private final static boolean CHECK_DEBUG = false;
    
    /**
     * Creates an edge with label
     * 
     * @param parent Parent node of this edge
     * @param child Child node of this edge
     * @param label label of this edge
     * @requires parent != null && child != null && label != null
     * @effects Constructs an edge with parent, child, label
     * @throws IllegalArgumentException if one of parent, child, and label is null
     */
    public Edge(T1 parent, T1 child, T2 label) {
        if (parent == null || child == null || label == null) {
            throw new IllegalArgumentException("Null arguments are now allowed");
        }
        
        this.parent = parent;
        this.child = child;
        this.label = label;
        
        // call checkRep()
        checkRep();
    }
    
    /**
     * Getter method of parent
     * 
     * @return the parent of this edge
     */
    public T1 getParent() {
        checkRep();
        return parent;
    }
    
    /**
     * Getter method of child
     * 
     * @return the child of this edge
     */
    public T1 getChild() {
        checkRep();
        return child;
    }
    
    /**
     * Getter method of label
     * 
     * @return the label of this edge
     */
    public T2 getLabel() {
        checkRep();
        return label;
    }
    
    /**
     * Compare equality with this edge
     * 
     * @param o object to compare
     * @return true if o represents this edge
     */
    @Override
    public boolean equals(Object o) {
        checkRep();
        if (! (o instanceof Edge)) {
            return false;
        }
        
        Edge<?, ?> e = (Edge<?, ?>) o;
        checkRep();
        
        return parent.equals(e.parent) && 
                child.equals(e.child) && 
                label.equals(e.label);
    }
    
    /**
     * Return hash code of this edge.
     * 
     * @return hash code of this edge
     */
    @Override
    public int hashCode() {
        checkRep();
        return parent.hashCode() * 7 + child.hashCode() * 29 + label.hashCode();
    }
    
    /**
     * String representation of this edge.
     * 
     * @return a string representation of this edge.
     */
    @Override
    public String toString() {
        checkRep();
        String str = child + "(" + label + ")";
        checkRep();
        return str;
    }
    
    /**
     * Chceks that representation invariant holds
     */
    private void checkRep() {
        if (CHECK_DEBUG) {
            assert (this.parent != null) : "Parent node cannot be null";
            assert (this.child != null) : "Child node cannot be null";
            assert (this.label != null) : "Label cannot be null";
        }
    }
    
}
