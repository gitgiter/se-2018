import info.gridworld.grid.*;
import info.gridworld.grid.AbstractGrid;
import info.gridworld.grid.Location;

import java.util.ArrayList;

/**
 * A <code>SparseBoundedGrid1</code> is a rectangular grid with a finite number of
 * rows and columns. <br />
 * The implementation of this class is testable on the AP CS AB exam.
 */
public class SparseBoundedGrid1<E> extends AbstractGrid<E> {
    private SparseGridNode[] occupantArray;
    private int rows;
    private int columns;

    /**
    * Constructs an empty bounded grid with the given dimensions. (Precondition:
    * <code>rowNum > 0</code> and <code>colNum > 0</code>.)
    *
    * @param rowNum number of rows in BoundedGrid
    * @param colNum number of columns in BoundedGrid
    */
    public SparseBoundedGrid1(int rowNum, int colNum) {
        if (rowNum <= 0) {
            throw new IllegalArgumentException("rowNum <= 0");
        }
        if (colNum <= 0) {
            throw new IllegalArgumentException("colNum <= 0");
        }

        rows = rowNum;
        columns = colNum;
        occupantArray = new SparseGridNode[rowNum];
    }

    public int getNumRows() {
        return rows;
    }

    public int getNumCols() {
        return columns;
    }

    public boolean isValid(Location loc) {
    	// check row and col
        return 0 <= loc.getRow() && loc.getRow() < getNumRows()
                && 0 <= loc.getCol() && loc.getCol() < getNumCols();
    }

    public ArrayList<Location> getOccupiedLocations() {
        ArrayList<Location> theLocations = new ArrayList<Location>();

        // Look at all grid locations.
        for (int r = 0; r < getNumRows(); r++)
        {
        	// to track a certain row node
        	SparseGridNode currentRow = occupantArray[r];
            while (currentRow != null) {
            	// loc is the location which is occupied
            	Location loc = new Location(r, currentRow.getCol());
            	// add loc to the occupied locations
            	theLocations.add(loc);
            	// get next element in the list
            	currentRow = currentRow.getNext();
            }
        }

        return theLocations;
    }

    /**
    * @param loc
    * @return
    */
    private SparseGridNode getNode(Location loc) {
    	// to track a certain row node
        SparseGridNode currentRow = occupantArray[loc.getRow()];
        // look for a location that matches loc
        while (currentRow != null) {
	        if (currentRow.getCol() == loc.getCol()) {
	            return currentRow;
	        }

	        // Traverse the next node in the row list
	        currentRow = currentRow.getNext();
        }
        return null;
    }

    // cast the node type to E
    private E get(SparseGridNode node) {
        if (node != null) {
            return (E) node.getOccupant();
        } 
        else {
            return null;
        }
    }

    public E get(Location loc) {
    	// get the node related to the loc from the list
        SparseGridNode node = getNode(loc);
        if (node != null) {
            return (E) node.getOccupant();
        } 
        else {
            return null;
        }
    }

    public E put(Location loc, E obj) {
        if (!isValid(loc)) {
            throw new IllegalArgumentException("Location " + loc + " is not valid");
        }
        if (obj == null) {
            throw new NullPointerException("obj == null");
        }

        // Add the object to the grid. 
        // Remove the old object from the grid.
        // Push the new occupant into the list.
        E oldOccupant = remove(loc);
        SparseGridNode oldHead = occupantArray[loc.getRow()];
        SparseGridNode newHead = new SparseGridNode(obj, loc.getCol(), null, oldHead);
        if (oldHead != null) {
            oldHead.setPre(newHead);
        }
        occupantArray[loc.getRow()] = newHead;
        return oldOccupant;
    }

    public E remove(Location loc) {
	    if (!isValid(loc)) {
	        throw new IllegalArgumentException("Location " + loc + " is not valid");
	    }

	    // Remove the object from the grid. 
	    // if no found return null
	    SparseGridNode removeNode = getNode(loc);
	    if (removeNode == null) {
	        return null;
	    }

	    // cast the type to E in order to return
	    E ENode = get(removeNode);

	    // modify the list
	    SparseGridNode preNode = removeNode.getPre();
	    SparseGridNode nextNode = removeNode.getNext();
	    if (preNode != null) {
	    	// link the preNode to the nextNode
	    	preNode.setNext(nextNode);
	    } 
	    else if (occupantArray[loc.getRow()] != null) {
	        // reset the head to nextNode because the removeNode is the head
	        occupantArray[loc.getRow()] = nextNode;
        }
        // link the nextNode to the preNode
        if (nextNode != null) {
            nextNode.setPre(preNode);
        }

    	return ENode;
    }            
}