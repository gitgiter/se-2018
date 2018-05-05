## File Structure
Those files are all in a same directory. But I submit those codes dividing in to three folders. If you want to compile and run those codes, you are supposed to put them together.

## Code Implementation
Most of the code and logic can be referenced to the BoundedGrid.java which is located at info/gridworld/grid/. Most of the code in it can be reused. Therefore, I implement those following three kinds of grid by modifying the BoundedGrid class.
### SparseBoundedGrid1.java (with SparseGridNode.java)
- SparseGridNode.java
SparseGridNode is a class which I define the structure of list node in. It has many extended methods about maintaining the node list, such as **setPre**, **setNext**, **getOccupant** and other useful get/set methods. I use this kind of node in a list in SparseBoundedGrid1.java as a *“sparse array”*, which is a locations linked list in order to track the locations of the occupants.
- SparseBoundedGrid1.java
SparseBoundedGrid1 is one of those classes which implement the grid. This grid maintains a occupantArray to record which locations in this grid are occupied. And this array is implement by linked list.  
Every row in the array is a linked list, linking all the occupied columns on this row. When I want to add a occupant into this grid, I just need to get the row of this occupant's location, and push in into the related **"row list"**. What's more, I can just traverse this row list to find the occupant, when I want to remove or do some other operations it.

### SparseBoundedGrid2.java
SparseBoundedGrid2 is also one of those classes which implement the grid. This kind of grid is different from the SparseBoundedGrid1, since it's implement by the way of HashMap. HashMap is a helpful class to help us define many relations between two objects. In SparseBoundedGrid2, I use HashMap to make a connection between locations and occupants as key and value. One occupants one key. It's convenient to maintain the occupants by just simply inserting or removing them from the map, as long as the keys are different from each other.

### UnboundedGrid2.java
UnboundedGrid2 is also one of those classes which implement the grid. This kind of grid is different from the two mentioned above. It's a extended-grid which is implement by an extended-size array, a two dimension array. The way of maintaining the array is the same as the way implement in BoundedGrid.java. The only difference between them is UnboundedGrid2 is unbouned while the other is not. When a occupant is out of the current grid, we just need to keep doubling the array size until the array size is larger than needed.