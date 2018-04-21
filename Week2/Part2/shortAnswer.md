# Part2
1. What is the role of the instance variable sideLength?
It indicates the number of steps on each side of the trace.
```java
// @file: projects/boxBug/boxBug.java
// @line: 45~49
if (steps < sideLength && canMove())
{
    move();
    steps++;
}
```
2. What is the role of the instance variable steps?
It records the number of steps that the bug has moved on current side.
```java
// @file: projects/boxBug/BoxBug.java
// @line: 45~49
if (steps < sideLength && canMove())
{
    move();
    steps++;
}
```
3. Why is the turn method called twice when steps becomes equal to sideLength?
Because the box bug needs to turn 90° when steps becomes equal to sideLength, which means that the bug goes to next box side. But a turn method is defined to turn only 45° per turn. So it should be called twice.
```java
// @file: info/gridworld/actor/Bug.java
// @line: 62~65
public void turn()
{
    setDirection(getDirection() + Location.HALF_RIGHT);
}
```
4. Why can the move method be called in the BoxBug class when there is no move method in the BoxBug code?
Because the BoxBug extends Bug, and the move method is defined in Bug.java
```java
// @file: projects/boxBug/BoxBug.java
// @line: 25
public class BoxBug extends Bug

// @file: info/gridworld/actor/Bug.java
// @line: 71~84
public void move()
{
    Grid<Actor> gr = getGrid();
    if (gr == null)
        return;
    Location loc = getLocation();
    Location next = loc.getAdjacentLocation(getDirection());
    if (gr.isValid(next))
        moveTo(next);
    else
        removeSelfFromGrid();
    Flower flower = new Flower(getColor());
    flower.putSelfInGrid(gr, loc);
}
```
5. After a BoxBug is constructed, will the size of its square pattern always be the same? Why or why not?
Yes, it will always be the same. When it's constructed, the sideLength is determined by length.
```java
// @file: projects/boxBug/BoxBug.java
// @line: 34~38
public BoxBug(int length)
{
    steps = 0;
    sideLength = length;
}
```
6. Can the path a BoxBug travels ever change? Why or why not?
Yes. When a rock or an edge or other actor is in front of the BoxBug, it will turn its direction and maybe then the path will change. 
```java
// @file: projects/boxBug/BoxBug.java
// @line: 50~55
// include the case: !canMove() && steps < sideLength, so it will turn even not finishing the current side
else
{
    turn();
    turn();
    steps = 0;
}
```
7. When will the value of steps be zero?
It's when the bug has just travelled to a new side.
```java
// @file: projects/boxBug/BoxBug.java
// @line: 50~55
// include the case: steps >= sideLength, which means the bug should change side
else
{
    turn();
    turn();
    steps = 0;
}
```