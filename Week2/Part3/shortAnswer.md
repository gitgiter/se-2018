## Set 3  
Assume the following statements when answering the following questions.
```java
Location loc1 = new Location(4, 3); 
Location loc2 = new Location(3, 4);
```
1. How would you access the row value for loc1?
```java
// @file: info/gridworld/grid/Location.java
// @line: 110~113
public int getRow()
{
    return row;
}
```
So my method is:
```java
int row = loc1.getRow();
```

2. What is the value of b after the following statement is executed?
```java
boolean b = loc1.equals(loc2);
```
According to the definition of Location.equals(Location), it will return true when the two locations are the same as each other.  
So the value of b will be false.
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
3. What is the value of loc3 after the following statement is executed?
```java
Location loc3 = loc2.getAdjacentLocaton(Location.SOUTH);
```
According to the definition of the getAdjacentLocaton() function, it will return a location which is adjacent with current location, with a parameters specifying the direction.
So, the loc3 will be (4, 4), 
```java 
// @file: info/gridworld/grid/Location.java
// @line: 130~169
public Location getAdjacentLocation(int direction)
{
    ...
}
```
4. What is the value of dir after the following statement is executed?
```java
int dir = loc1.getDirectionToward(new Location(6, 5));
```
According to the definition of the getAdjacentLocaton() function, it will return a location which is adjacent with current location, with a parameters specifying the destination.
So, the value of dir will be 135, because the dx and dy is both 2, which means the direction of southeast. 
```java 
// @file: info/gridworld/grid/Location.java
// @line: 178~195
public int getDirectionToward(Location target)
{
    int dx = target.getCol() - getCol();
    int dy = target.getRow() - getRow();
    // y axis points opposite to mathematical orientation
    int angle = (int) Math.toDegrees(Math.atan2(-dy, dx));

    // mathematical angle is counterclockwise from x-axis,
    // compass angle is clockwise from y-axis
    int compassAngle = RIGHT - angle;
    // prepare for truncating division by 45 degrees
    compassAngle += HALF_RIGHT / 2;
    // wrap negative angles
    if (compassAngle < 0)
        compassAngle += FULL_CIRCLE;
    // round to nearest multiple of 45
    return (compassAngle / HALF_RIGHT) * HALF_RIGHT;
}
```
5. How does the getAdjacentLocation method know which adjacent location to return?
By passing a parameter which specifies a direction of adjacent grid. For example, when the value of the parameter is Location.SOUTH, it means return the location of the grid which is right under the current location.
```java 
// @file: info/gridworld/grid/Location.java
// @line: 130~169
public Location getAdjacentLocation(int direction)
{
    ...
}
```


## Set 4  
6. How can you obtain a count of the objects in a grid? How can you obtain a count of the empty locations in a bounded grid?
```java
// @file: info/gridworld/grid/Grid.java
// @line: 85
ArrayList<Location> getOccupiedLocations();
```
The function getOccupiedLocations() gets the locations in this grid that contain objects. And return an array list of all occupied locations in this grid.
So the count of the objects is:
```java
// where gr is an instance of grid
int count = gr.getOccupiedLocations().size();
```
And the numbers of the empty locations is
```java
// where gr is an instance of grid
gr.getNumRows()*gr.getNumCols() - gr.getOccupiedLocations().size();
```
7. How can you check if location (10,10) is in a grid?
```java
// @file: info/gridworld/grid/Grid.java
// @line: 50
boolean isValid(Location loc);
```
The function isValid(Location) accepts a parameter of the location to be checked, and return true if is valid in this grid.
So I can check it by calling this function like:
```java
// where gr is an instance of grid
gr.isValid(new Location(10,10));
```

8. Grid contains method declarations, but no code is supplied in the methods. Why? Where can you find the implementations of these methods?
Grid is an interface. The functions of an interface are implemented in the classes which extend from the interface.  
In this question, we can find those classes in AbstractGrid and the BoundedGrid and UnboundedGrid.
For some examples:
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
```java
// @file: info/gridworld/grid/BoundedGrid.java
// @line: 60~64
public boolean isValid(Location loc)
{
    return 0 <= loc.getRow() && loc.getRow() < getNumRows()
            && 0 <= loc.getCol() && loc.getCol() < getNumCols();
}
```
```java
// @file: info/gridworld/grid/UnboundedGrid.java
// @line: 53~56
public boolean isValid(Location loc)
{
    return true;
}
```
9. All methods that return multiple objects return them in an ArrayList. Do you think it would be a better design to return the objects in an array? Explain your answer.
I think both have their own advantages. On one hand, the ArrayList may easily figure out the numbers of the elements in the List, but with a more expensive space cost. On the other hand, the array may cost fewer than ArrayList, but it must always specify the size of the array before it return, which may be inconvenient.
- ArrayList
```java
ArrayList<Location> getOccupiedLocations();
```
- array
```java
Location[] getOccupiedLocations()
{
    ...
    Location[] arr = new Location[size];
    ...
    return arr;
}
```


Set 5  
10. Name three properties of every actor.
- location
- direction
- color
```java
// @file: info/gridworld/actor/Actor.java
// @line: 32~34
private Location location;
private int direction;
private Color color;
```
11. When an actor is constructed, what is its direction and color?
- The direction is north, which is 0°.  
- The color is blud.
```java
// @file: info/gridworld/actor/Actor.java
// @line: 41~42
color = Color.BLUE;
direction = Location.NORTH;
```
12. Why do you think that the Actor class was created as a class 
According to the meaning of the name itself, an Actor is more real and more natural than a Grid, with many properties and behaviors. An interface is not allow to have property members and behaviors which are implemented.

13. Can an actor put itself into a grid twice without first removing itself? Can an actor remove itself from a grid twice? Can an actor be placed into a grid, remove itself, and then put itself back? Try it out. What happens?
- No. Must remove itself first before putting in again, because it will throw an IllegalStateException.
```java
// @file: info/gridworld/actor/Actor.java
// @line: 117~119
if (grid != null)
    throw new IllegalStateException(
        "This actor is already contained in a grid.");
```
- No. When the cell is empty, it can't remove anything.
```java
// @file: info/gridworld/actor/Actor.java
// @line: 135~137
if (grid == null)
    throw new IllegalStateException(
        "This actor is not contained in a grid.");
```
- Yes. Of course we can put in when empty and remove when not empty.
```java
// @file: info/gridworld/actor/Actor.java
// @line: 121~126
Actor actor = gr.get(loc);
if (actor != null)
    actor.removeSelfFromGrid();
gr.put(loc, this);
grid = gr;
location = loc;
```
```java
// @file: info/gridworld/actor/Actor.java
// @line: 143~145
grid.remove(location);
grid = null;
location = null;
```
14. How can an actor turn 90 degrees to the right?
```java
// @file: info/gridworld/actor/Actor.java
// @line: 69~72
public int getDirection()
{
    return direction;
}
// @file: info/gridworld/actor/Actor.java
// @line: 80~85
public void setDirection(int newDirection)
{
    direction = newDirection % Location.FULL_CIRCLE;
    if (direction < 0)
        direction += Location.FULL_CIRCLE;
}
```
According to the definition of getDirection() and setDirection(int), we can turn 90° by:
```java
setDirection(getDirection() + 90);
```

## Set 6  
15. Which statement(s) in the canMove method ensures that a bug does not try to move out of its grid?
These statements ensure the bug perform a valid move.
```java
// @file: info/gridworld/actor/Bug.java
// @line: 98~99
if (!gr.isValid(next))
    return false;
```
16. Which statement(s) in the canMove method determines that a bug will not walk into a rock?
This statement ensures returning false when a rock is next to the bug.
```java
// @file: info/gridworld/actor/Bug.java
// @line: 100~101
Actor neighbor = gr.get(next);
return (neighbor == null) || (neighbor instanceof Flower);
```
17. Which methods of the Grid interface are invoked by the canMove method and why?
The isValid(Location) and get(Location). The isValidn method is used to ensure the next cell is valid. And the get method is used to get the Actor in the location, in order to provide support next statement.
```java
// @file: info/gridworld/actor/Bug.java
// @line: 98
if (!gr.isValid(next))
// @file: info/gridworld/actor/Bug.java
// @line: 98
Actor neighbor = gr.get(next);
```
18. Which method of the Location class is invoked by the canMove method and why?
The getAdjacentLocation(int) method. It's used to find next possible adjacent location from current location, deciding whether the bug can move or not.
```java
// @file: info/gridworld/actor/Bug.java
// @line: 97
Location next = loc.getAdjacentLocation(getDirection());
```
19. Which methods inherited from the Actor class are invoked in the canMove method?
- getGrid()
```java
// @file: info/gridworld/actor/Bug.java
// @line: 93
Grid<Actor> gr = getGrid();
```
- getLocation()
```java
// @file: info/gridworld/actor/Bug.java
// @line: 96
Location loc = getLocation();
```
- getDirection()
```java
// @file: info/gridworld/actor/Bug.java
// @line: 97
Location next = loc.getAdjacentLocation(getDirection());
```

20. What happens in the move method when the location immediately in front of the bug is out of the grid?
It will remove itself from the grid, and will leave a flower behind.
```java
// @file: info/gridworld/actor/Bug.java
// @line: 80~81
else
    removeSelfFromGrid();
```
21. Is the variable loc needed in the move method, or could it be avoided by calling getLocation() multiple times?
Yes. The loc is needed to store the location before the bug moves, in order to put a flower in the old location.
```java
// @file: info/gridworld/actor/Bug.java
// @line: 82~83
Flower flower = new Flower(getColor());
flower.putSelfInGrid(gr, loc);
```
22. Why do you think the flowers that are dropped by a bug have the same color as the bug?
Because it initiate a new flower by setting the color of current bug. 
```java
// @file: info/gridworld/actor/Bug.java
// @line: 82
Flower flower = new Flower(getColor());
```
23. When a bug removes itself from the grid, will it place a flower into its previous location?
Yes. As long as the bug removes from the old location, it will leave a flower behind.
```java
// @file: info/gridworld/actor/Bug.java
// @line: 82~83
Flower flower = new Flower(getColor());
flower.putSelfInGrid(gr, loc);
```
24. Which statement(s) in the move method places the flower into the grid at the bug's previous location?
```java
// @file: info/gridworld/actor/Bug.java
// @line: 82~83
Flower flower = new Flower(getColor());
flower.putSelfInGrid(gr, loc);
```
25. If a bug needs to turn 180 degrees, how many times should it call the turn method?
It would be four times. Because it only turns 45° per turn.
```java
// @file: info/gridworld/actor/Bug.java
// @line: 62~65
public void turn()
{
    setDirection(getDirection() + Location.HALF_RIGHT);
}
```