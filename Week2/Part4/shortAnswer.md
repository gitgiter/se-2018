## Set 7 The source code for the Critter class is in the critters directory

1. What methods are implemented in Critter?
- act  
```java
// @file: info/gridworld/actor/Critter.java
// @line: 38~47
public void act()
{
    if (getGrid() == null)
        return;
    ArrayList<Actor> actors = getActors();
    processActors(actors);
    ArrayList<Location> moveLocs = getMoveLocations();
    Location loc = selectMoveLocation(moveLocs);
    makeMove(loc);
}
```
- getActors  
```java
// @file: info/gridworld/actor/Critter.java
// @line: 56~59
public ArrayList<Actor> getActors()
{
    return getGrid().getNeighbors(getLocation());
}
```
- processActors  
```java
// @file: info/gridworld/actor/Critter.java
// @line: 71~78
public void processActors(ArrayList<Actor> actors)
{
    for (Actor a : actors)
    {
        if (!(a instanceof Rock) && !(a instanceof Critter))
            a.removeSelfFromGrid();
    }
}
```
- getMoveLocations  
```java
// @file: info/gridworld/actor/Critter.java
// @line: 88~91
public ArrayList<Location> getMoveLocations()
{
    return getGrid().getEmptyAdjacentLocations(getLocation());
}
```
- selectMoveLocation  
```java
// @file: info/gridworld/actor/Critter.java
// @line: 104~111
public Location selectMoveLocation(ArrayList<Location> locs)
{
    int n = locs.size();
    if (n == 0)
        return getLocation();
    int r = (int) (Math.random() * n);
    return locs.get(r);
}
```
- makeMove  
```java
// @file: info/gridworld/actor/Critter.java
// @line: 125~131
public void makeMove(Location loc)
{
    if (loc == null)
        removeSelfFromGrid();
    else
        moveTo(loc);
}
```
2. What are the five basic actions common to all critters when they act?
code is shown in the No.1 question
- getActors  
- processActors  
- getMoveLocations  
- selectMoveLocation  
- makeMove  

3. Should subclasses of Critter override the getActors method? Explain.
Yes. According to the requirements, those subclass may select different actors and different locations from the Critter does. So to achieve this requires the override.
4. Describe the way that a critter could process actors.
It can eat all those actors, or change their color, or make them turn or move. In Critter class is implement as "eatting all" by remove all actors from the grid.
```java
// @file: info/gridworld/actor/Critter.java
// @line: 71~78
public void processActors(ArrayList<Actor> actors)
{
    for (Actor a : actors)
    {
        if (!(a instanceof Rock) && !(a instanceof Critter))
            a.removeSelfFromGrid();
    }
}
```
5. What three methods must be invoked to make a critter move? Explain each of these methods.
The act should calls those three methods one by one to make a critter move. Firstly, it calls **getMoveLocations** to get an ArrayList of the empty locations around current locations. Secondly, it pass this ArrayList to **selectMoveLocations**, in order to select a location randomly from the list. Thirdly, it calls makeMove to finally make the critter move.
- getMoveLocations
```java
// @file: info/gridworld/actor/Critter.java
// @line: 88~91
public ArrayList<Location> getMoveLocations()
{
    return getGrid().getEmptyAdjacentLocations(getLocation());
}
```
- selectMoveLocations
```java
// @file: info/gridworld/actor/Critter.java
// @line: 104~111
public Location selectMoveLocation(ArrayList<Location> locs)
{
    int n = locs.size();
    if (n == 0)
        return getLocation();
    int r = (int) (Math.random() * n);
    return locs.get(r);
}
```
- makeMove
```java
// @file: info/gridworld/actor/Critter.java
// @line: 125~131
public void makeMove(Location loc)
{
    if (loc == null)
        removeSelfFromGrid();
    else
        moveTo(loc);
}
```

6. Why is there no Critter constructor?
Because it has a default constructor, which is implicit. This constructor default to call the default constructor in Actor, which constructs a blue critter that faces north, since Critter extends from Actors.
```java
// @file: info/gridworld/actor/Critter.java
// @line: 31
public class Critter extends Actor
```
```java
// @file: info/gridworld/actor/Actor.java
// @line: 39~45
public Actor()
{
    color = Color.BLUE;
    direction = Location.NORTH;
    grid = null;
    location = null;
}
```

## Set 8 The source code for the ChameleonCritter class is in the critters directory

7. Why does act cause a ChameleonCritter to act differently from a Critter even though ChameleonCritter does not override act?
Because the ChameleonCritter class has overrided the processActors method and makeMove method, which will be called in act method. And since these two override methods are different from the Critter's. So ChameleonCritter's act method must different from Critter.
```java
// @file: projects/critters/ChameleonCritter.java
// @line: 36~45
public void processActors(ArrayList<Actor> actors)
{
    int n = actors.size();
    if (n == 0)
        return;
    int r = (int) (Math.random() * n);

    Actor other = actors.get(r);
    setColor(other.getColor());
}
```
```java
// @file: projects/critters/ChameleonCritter.java
// @line: 50~54
public void makeMove(Location loc)
{
    setDirection(getLocation().getDirectionToward(loc));
    super.makeMove(loc);
}
```
8. Why does the makeMove method of ChameleonCritter call super.makeMove?
The ChameleonCritter firstly changes the direction of the critter to face its new location. And then it wants to move like Critter, so it can just call the parent class method by **super.makeMove(loc)**, to reduce code repetition rate and reuse codes. And also, that's why it's defined to extend from Critter, rather than other classes.
```java
// @file: projects/critters/ChameleonCritter.java
// @line: 50~54
public void makeMove(Location loc)
{
    setDirection(getLocation().getDirectionToward(loc));
    super.makeMove(loc);
}
```
9. How would you make the ChameleonCritter drop flowers in its old location when it moves?
I can just need to modify the makeMove method in ChameleonCritter by adding some codes about creating a flower. Of course, I should firstly store the old location before the ChameleonCritter moves. And I should 
For example:
```java
public void makeMove(Location loc) { 
    Location oldLoc = getLocation(); 
    setDirection(getLocation().getDirectionToward(loc));
    super.makeMove(loc); 
    if(!oldLoc.equals(loc)) {         
        Flower flower = new Flower(getColor());flower.putSelfInGrid(getGrid(), oldLoc);
    }
 }
```
10. Why doesn't ChameleonCritter override the getActors method?
Because it does the same thing as its base class Critter's does. There is no new behavior in ChameleonCritter's getActors method. Therefore, it doesn't need to override.
11. Which class contains the getLocation method?
The Actor class and all its subclass.

12. How can a Critter access its own grid?
Call the getGrid() method, which inherits from Actor class.

## Set 9 The source code for the CrabCritter class is reproduced at the end of this part of GridWorld.

13. Why doesn't CrabCritter override the processActors method?
Because it does the same thing as its base class Critter's does. There is no new behavior in CrabCritter's processActors method. Therefore, it's no need to override.
14. Describe the process a CrabCritter uses to find and eat other actors. Does it always eat all neighboring actors? Explain.
No. The CrabCritter only eats those other actors that are in the locations in front or front-right or front-left of it. The rest of the neighboring actors will be "safe".
15. Why is the getLocationsInDirections method used in CrabCritter?
Because it's needed to finds the valid adjacent locations of this CrabCritter in different directions, according to the behaviors that are designed for CrabCritter class. The function takes a parameter with an array of directions, which are relative to the current direction. And return a set of valid locations that are neighbors of the current location in the given directions.
16. If a CrabCritter has location (3, 4) and faces south, what are the possible locations for actors that are returned by a call to the getActors method?
(4, 3), (4, 4) and (4, 5), according to the definition of getActors(). It can return at most three valid locations of the front, front-left, front-right neighboring actors.
17. What are the similarities and differences between the movements of a CrabCritter and a Critter?
- similarities  
They both randomly choose a valid location in their valid locations list to move to. And they don't turn in the direction that they are moving.
- differences  
The Critter can randomly select all the neighboring valid locations to move to, but the CrabCritter only moves to the locations that on its left or right. Besides, when they can't move, the CrabCritter randomly turns left or right, while the Critter does not turn.
18. How does a CrabCritter determine when it turns instead of moving?
In its makeMove(Location) method, if the parameter is passed as the same location as the current locations, it turns instead of moving, which means that there isn't any valid location to move to. 
19. Why don't the CrabCritter objects eat each other?
This is about how it process the actors they encounter to. So the processActors from the its super class Crittrt is the key. This method only removes actors that are not rocks and not critters. So the CrabCritter doesn't eat each other since it's a critter.