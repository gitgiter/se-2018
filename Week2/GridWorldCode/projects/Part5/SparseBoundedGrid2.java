import info.gridworld.grid.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A <code>BoundedGrid</code> is a rectangular grid with a finite number of
 * rows and columns. <br />
 * The implementation of this class is testable on the AP CS AB exam.
 */
public class SparseBoundedGrid2<E> extends AbstractGrid<E> {
    private Map<Location, E> occupantMap;
    private int rows;
    private int columns;

    /**
    * Constructs an empty bounded grid with the given dimensions. (Precondition:
    * <code>rowNum > 0</code> and <code>colNum > 0</code>.)
    *
    * @param rowNum number of rows in BoundedGrid
    * @param colNum number of columns in BoundedGrid
    */
    public SparseBoundedGrid2(int rowNum, int colNum) {
        if (rowNum <= 0) {
            throw new IllegalArgumentException("rowNum <= 0");
        }
        if (colNum <= 0) {
            throw new IllegalArgumentException("colNum <= 0");
        }

        rows = rowNum;
        columns = colNum;
        occupantMap = new HashMap<Location, E>();
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
        ArrayList<Location> a = new ArrayList<Location>();
        for (Location loc : occupantMap.keySet()) {
            a.add(loc);
        }        
        return a;
    }

    public E get(Location loc) {
        if (!isValid(loc)) {
            throw new IllegalArgumentException("Location " + loc + " is not valid");
        }
        return occupantMap.get(loc);
    }

    public E put(Location loc, E obj) {
        if (!isValid(loc)) {
            throw new IllegalArgumentException("Location " + loc + " is not valid");
        }
        if (obj == null) {
             throw new NullPointerException("obj == null");
        }           
        return occupantMap.put(loc, obj);
    }

    public E remove(Location loc) {
        if (!isValid(loc)) {
            throw new IllegalArgumentException("Location " + loc + " is not valid");
        }
        return occupantMap.remove(loc);
    }
}