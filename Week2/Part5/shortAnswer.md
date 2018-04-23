## Set 10

The source code for the AbstractGrid class is in Appendix D.

1. Where is the isValid method specified? Which classes provide an implementation of this method?
The isValid method is specified in Grid interface. And it's implemented in BoundedGrid and UnboundedGrid.
```java
// @file: info/gridworld/grid/Grid.java
// @line: 50
boolean isValid(Location loc);

// @file: info/gridworld/grid/BoundedGrid.java
// @line: 60~64
public boolean isValid(Location loc)
{
    return 0 <= loc.getRow() && loc.getRow() < getNumRows()
            && 0 <= loc.getCol() && loc.getCol() < getNumCols();
}

// @file: info/gridworld/grid/UnboundedGrid.java
// @line: 53~56
public boolean isValid(Location loc)
{
    return true;
}
```
2. Which AbstractGrid methods call the isValid method? Why don't the other methods need to call it?
The **getValidAdjacentLocations** method. This function is specailly used to get the valid adjacent locations from current locations, as its function name suggested. Therefore, it should call the **isValid** method in order to validate a certain location. However, the other method doesn't directly call the **isValid** method. They call **getValidAjacentLocations** function instead. An advantage of this way is that, the code can be managered better than they call directly, such as, being easily modified.
```java
// @file: info/gridworld/grid/UnboundedGrid.java
// @line: 36~49
public ArrayList<Location> getValidAdjacentLocations(Location loc)
{
    ...
    if (isValid(neighborLoc))
        locs.add(neighborLoc);
    ...
}
```

3. Which methods of the Grid interface are called in the getNeighbors method? Which classes provide implementations of these methods?
```java
// @file: info/gridworld/grid/AbstractGrid.java
// @line: 28~34
public ArrayList<E> getNeighbors(Location loc)
{
    ArrayList<E> neighbors = new ArrayList<E>();
    for (Location neighborLoc : getOccupiedAdjacentLocations(loc))
        neighbors.add(get(neighborLoc));
    return neighbors;
}
```
- These two interfaces in grid is call.
    - getOccupiedAdjacentLocations  
    This interface is implemented in Abstract class.
    ```java
    // @file: info/gridworld/grid/AbstractGrid.java
    // @line: 62~71
    public ArrayList<Location> getOccupiedAdjacentLocations(Location loc)
    {
        ArrayList<Location> locs = new ArrayList<Location>();
        for (Location neighborLoc : getValidAdjacentLocations(loc))
        {
            if (get(neighborLoc) != null)
                locs.add(neighborLoc);
        }
        return locs;
    }
    ```
    - get  
    This interface is implemented by BoundedGrid and UnboundedGrid.
    ```java
    // @file: info/gridworld/grid/BoundedGrid.java
    // @line: 85~91
    public E get(Location loc)
    {
        if (!isValid(loc))
            throw new IllegalArgumentException("Location " + loc
                    + " is not valid");
        return (E) occupantArray[loc.getRow()][loc.getCol()]; // unavoidable warning
    }
    ```
    ```java
    // @file: info/gridworld/grid/UnboundedGrid.java
    // @line: 66~71
    public E get(Location loc)
    {
        if (loc == null)
            throw new NullPointerException("loc == null");
        return occupantMap.get(loc);
    }
    ```


4. Why must the get method, which returns an object of type E, be used in the getEmptyAdjacentLocations method when this method returns locations, not objects of type E?
Because the **get** method is used in **getEmptyAdjacentLocations** to judge whether the locations is empty or not. And maybe this is the only way. The **get** method return a object in the specified location or null if it's empty. Therefore, the **getEmptyAdjacentLocations** can call the **get** method to dicide whether it should return that specified locaion or not.
```java
// @file: info/gridworld/grid/AbstractGrid.java
// @line: 51~60
public ArrayList<Location> getEmptyAdjacentLocations(Location loc)
{
    ArrayList<Location> locs = new ArrayList<Location>();
    for (Location neighborLoc : getValidAdjacentLocations(loc))
    {
        if (get(neighborLoc) == null)
            locs.add(neighborLoc);
    }
    return locs;
}
```
5. What would be the effect of replacing the constant Location.HALF_RIGHT with Location.RIGHT in the two places where it occurs in the getValidAdjacentLocations method?
Then the **getValidAdjacentLocations** will be only to get the four adjacent locations instead of the eight before replacing. These adjacent locations may be north, east, south, west.
```java
// @file: info/gridworld/grid/AbstractGrid.java
// @line: 41~47
for (int i = 0; i < Location.FULL_CIRCLE / Location.HALF_RIGHT; i++)
{
    Location neighborLoc = loc.getAdjacentLocation(d);
    if (isValid(neighborLoc))
        locs.add(neighborLoc);
    d = d + Location.HALF_RIGHT;
}
```

## Set 11

The source code for the BoundedGrid class is in Appendix D.

6. What ensures that a grid has at least one valid location?
In the constructor of the BoundedGrid class, it requires the rows and cols to be positive. Otherwise, it will throw an exception.
```java
// @file: info/gridworld/grid/BoundedGrid.java
// @line: 39~46
public BoundedGrid(int rows, int cols)
{
    if (rows <= 0)
        throw new IllegalArgumentException("rows <= 0");
    if (cols <= 0)
        throw new IllegalArgumentException("cols <= 0");
    occupantArray = new Object[rows][cols];
}
```
7. How is the number of columns in the grid determined by the getNumCols method? What assumption about the grid makes this possible?
It is determined by the length of occupiedArray[0]. The assumption about this is that, the grid has at least one row, as it's required in the constructor.
```java
// @file: info/gridworld/grid/BoundedGrid.java
// @line: 53~58
public int getNumCols()
{
    // Note: according to the constructor precondition, numRows() > 0, so
    // theGrid[0] is non-null.
    return occupantArray[0].length;
}
```
8. What are the requirements for a Location to be valid in a BoundedGrid? In the next four questions, let r = number of rows, c = number of columns, and n = number of occupied locations.
It requires the row of the location is in the range of [0, r) and the column of the location is in the range of [0, c).
```java
// @file: info/gridworld/grid/BoundedGrid.java
// @line: 60~64
public boolean isValid(Location loc)
{
    return 0 <= loc.getRow() && loc.getRow() < getNumRows()
            && 0 <= loc.getCol() && loc.getCol() < getNumCols();
}
```
9. What type is returned by the getOccupiedLocations method? What is the time complexity (Big-Oh) for this method?
The **getOccupiedLocations** method returns a type of ArrayList<Location>. The two for loops will result in O(r * c). And the add operation on ArrayList will cost O(1). Therefore, the time complexity of this method is O(r * c + 1).
```java
// @file: info/gridworld/grid/BoundedGrid.java
// @line: 71~80
for (int r = 0; r < getNumRows(); r++)
{
    for (int c = 0; c < getNumCols(); c++)
    {
        // If there's an object at this location, put it in the array.
        Location loc = new Location(r, c);
        if (get(loc) != null)
            theLocations.add(loc);
    }
}
```
10. What type is returned by the get method? What parameter is needed? What is the time complexity (Big-Oh) for this method?
The get method returns an E type object. The function needs a location as a parameter. The time complexity of this method is O(1), which is caused by the accessing of a linear array.
```java
// @file: info/gridworld/grid/BoundedGrid.java
// @line: 85~91
public E get(Location loc)
{
    ...
    return (E) occupantArray[loc.getRow()][loc.getCol()];
}
```
11. What conditions may cause an exception to be thrown by the put method? What is the time complexity (Big-Oh) for this method?
This method will throw an IllegalArgumentException if the location is invalid and throw a NullPointerException if the object is null. The time complexity is O(1), caused by the linear array accessing.
```java
// @file: info/gridworld/grid/BoundedGrid.java
// @line: 93~105
public E put(Location loc, E obj)
{
    if (!isValid(loc))
        throw new IllegalArgumentException("Location " + loc
                + " is not valid");
    if (obj == null)
        throw new NullPointerException("obj == null");

    // Add the object to the grid.
    E oldOccupant = get(loc);
    occupantArray[loc.getRow()][loc.getCol()] = obj;
    return oldOccupant;
}
```
12. What type is returned by the remove method? What happens when an attempt is made to remove an item from an empty location? What is the time complexity (Big-Oh) for this method?
The remove method return an E type object. It's not an exception when remove item from an empty location. Because it will just make the null element in occupiedArray to be null again. The time complexity of this method is O(1), caused by the linear array accessing.
```java
// @file: info/gridworld/grid/BoundedGrid.java
// @line: 107~117
public E remove(Location loc)
{
    if (!isValid(loc))
        throw new IllegalArgumentException("Location " + loc
                + " is not valid");
    
    // Remove the object from the grid.
    E r = get(loc);
    occupantArray[loc.getRow()][loc.getCol()] = null;
    return r;
}
```
13. Based on the answers to questions 4, 5, 6, and 7, would you consider this an efficient implementation? Justify your answer.
Yes, it's likely to be an efficient implementation. Because the only largest time complexity is O(r * c), while others are O(1).

## Set 12 

The source code for the UnboundedGrid class is in Appendix D.

14. Which method must the Location class implement so that an instance of HashMap can be used for the map? What would be required of the Location class if a TreeMap were used instead? Does Location satisfy these requirements? 
The location class should implement the **equals** method and **hashCode** mehthod, to make sure that the **equals** return true and hashCode return the same code when the two locations are the same.
```java
// @file: info/gridworld/grid/Location.java
// @line: 205~212
public boolean equals(Object other)
{
    if (!(other instanceof Location))
        return false;

    Location otherLoc = (Location) other;
    return getRow() == otherLoc.getRow() && getCol() == otherLoc.getCol();
}
```
```java
// @file: info/gridworld/grid/Location.java
// @line: 218~221
public int hashCode()
{
    return getRow() * 3737 + getCol();
}
```
And it the TreeMap were used instead, then the **compareTo** method would be required, to make sure that the **compareTo** method return 0 when two locations equals.
```java
// @file: info/gridworld/grid/Location.java
// @line: 234~246
public int compareTo(Object other)
{
    Location otherLoc = (Location) other;
    if (getRow() < otherLoc.getRow())
        return -1;
    if (getRow() > otherLoc.getRow())
        return 1;
    if (getCol() < otherLoc.getCol())
        return -1;
    if (getCol() > otherLoc.getCol())
        return 1;
    return 0;
}
```
The Location class satisfies all these requirements.

15. Why are the checks for null included in the get, put, and remove methods? Why are no such checks included in the corresponding methods for the BoundedGrid?
Because a null object is not supposed to be put into the map, so it's also cannot be **get** or **remove** from the map. However, the BoundedGrid class is implemented by an two-dimensions array of objects, whose element is allowed to be null. When a null object is going to be **put** into the array, it just makes the null to null operation, which is not an error.
```java
// @file: info/gridworld/grid/UnboundedGrid.java
// @line: 33
private Map<Location, E> occupantMap;
```
```java
// @file: info/gridworld/grid/BoundedGrid.java
// @line: 31
private Object[][] occupantArray;
```

16. What is the average time complexity (Big-Oh) for the three methods: get, put, and remove? What would it be if a TreeMap were used instead of a HashMap?
The time complexity of **get**, **put**, **remove** are all O(1), since they just need to access element in HashMap, which is the map constructed by some kind of hash function. However, when a TreeMap replaces the HashMap, the time complexity will be O(log n), according to the data structure of tree.
17. How would the behavior of this class differ, aside from time complexity, if a TreeMap were used instead of a HashMap?
They may differ in **getOccupiedLocations** method aside from time complexity. More specifically, it will differ in the order of getting or accessing the occupant stored in map. According to the implement of HashMap and the meaning of hash function, the HashMap stored its elements randomly depending on the hash function. Therefore, when getting the occpants in HashMap, it always returns the occupants in random order by traversing the hash list. On the contrary, the TreeMap will traverse a balanced binary search tree to get the occupants, which is an inorder traversed. Every time the **getOccupiedLocations** method is called, it will do this again. So the TreeMap always returns the occupants list in an inorder traversed order.
18. Could a map implementation be used for a bounded grid? What advantage, if any, would the two-dimensional array implementation that is used by the BoundedGrid class have over a map implementation?
Yes, a map can be used for a bounded grid's implementation.  
The advantege is clear that, the map costs more space, especially when the grid is almost full. The map will then be storing too many object type items as the occupants. However, the two-dimensional array still just needs to store only two integer type indices. It can get the corresponding occupants by easily calculating the row and column indices. 

## [Coding Exercises] 
19. Consider using a HashMap or TreeMap to implement the SparseBoundedGrid. How could you use the UnboundedGrid class to accomplish this task? Which methods of UnboundedGrid could be used without change?

Fill in the following chart to compare the expected Big-Oh efficiencies for each implementation of the SparseBoundedGrid.

Let r = number of rows, c = number of columns, and n = number of occupied locations

|Methods|SparseGridNode version|LinkedList<OccupantInCol> version|HashMap version|TreeMap version|
|:---:|:---:|:---:|:---:|:---:|:---:|
|getNeighbors|O(c)|O(c)|O(1)|O(log n)|
|getEmptyAdjacentLocations|O(c)|O(c)|O(1)|O(log n)|
|getOccupiedAdjacentLocations|O(c)|O(c)|O(1)|O(log n)|
|getOccupiedLocations|O(r + n)|O(r + n)|O(n)|O(n)|
|get|O(c)|O(c)|O(1)|O(log n)|
|put|O(c)|O(c)|O(1)|O(log n)|
|remove|O(c)|O(c)|O(1)|O(log n)|

20. Consider an implementation of an unbounded grid in which all valid locations have non-negative row and column values. The constructor allocates a 16 x 16 array. When a call is made to the put method with a row or column index that is outside the current array bounds, double both array bounds until they are large enough, construct a new square array with those bounds, and place the existing occupants into the new array.

Implement the methods specified by the Grid interface using this data structure. What is the Big-Oh efficiency of the get method? What is the efficiency of the put method when the row and column index values are within the current array bounds? What is the efficiency when the array needs to be resized?

The time complexity of get method is O(1)
- within bounds  
The time complexity of put method is O(1)
- out of bounds  
The time complexity of put method is O([old dimension]^2 + (log times))
