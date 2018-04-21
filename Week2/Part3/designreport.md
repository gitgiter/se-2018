# design report

a. What will a jumper do if the location in front of it is empty, but the location two cells in front contains a flower or a rock?
It will jump when there is a flower in the cell(valid and empty), otherwise, it turns.
```java
// @file: projects/Part3/Jumper.java
// @line: 31~35
// in function act
if (canJump()) {
    jump();
} else {
    turn();
}
// @file: projects/Part3/Jumper.java
// @line: 82~85
// in function canJump
Actor neighbor = gr.get(twoNext);
return (neighbor == null) || (neighbor instanceof Flower);
// ok to move into empty location or onto flower
// not ok to move onto any other actor
```

b. What will a jumper do if the location two cells in front of the jumper is out of the grid?
It will turn its direction until it can jump.
```java
// @file: projects/Part3/Jumper.java
// @line: 31~35
// in function act
if (canJump()) {
    jump();
} else {
    turn();
}
// @file: projects/Part3/Jumper.java
// @line: 79~81
// in function canJump
if (!gr.isValid(twoNext)) {
    return false;
}
```

c. What will a jumper do if it is facing an edge of the grid?
It will turn its direction until it can jump, maybe turn twice or more.
```java
// @file: projects/Part3/Jumper.java
// @line: 31~35
// in function act
if (canJump()) {
    jump();
} else {
    turn();
}
// @file: projects/Part3/Jumper.java
// @line: 79~81
// in function canJump
if (!gr.isValid(twoNext)) {
    return false;
}
```

d. What will a jumper do if another actor (not a flower or a rock) is in the cell that is two cells in front of the jumper?
It will turn its direction if the actor doesn't move away in next step, until it can jump.
```java
// @file: projects/Part3/Jumper.java
// @line: 31~35
// in function act
if (canJump()) {
    jump();
} else {
    turn();
}
// @file: projects/Part3/Jumper.java
// @line: 82~85
// in function canJump
Actor neighbor = gr.get(twoNext);
return (neighbor == null) || (neighbor instanceof Flower);
// ok to move into empty location or onto flower
// not ok to move onto any other actor
```

e. What will a jumper do if it encounters another jumper in its path?
It will turn its direction if the other jumper doesn't move away in next step, until it can jump.
```java
// @file: projects/Part3/Jumper.java
// @line: 31~35
// in function act
if (canJump()) {
    jump();
} else {
    turn();
}
// @file: projects/Part3/Jumper.java
// @line: 82~85
// in function canJump
Actor neighbor = gr.get(twoNext);
return (neighbor == null) || (neighbor instanceof Flower);
// ok to move into empty location or onto flower
// not ok to move onto any other actor
```

f. Are there any other tests the jumper needs to make?
Almost tested.  
But if we want to increase the difficulty of test.   
We can:
- surronded by a circle of rocks, or flowers, or jumpers, or other actors.
- surronded by two circle of rocks, or flowers, or jumpers, or other actors.
- facing the corner, with one edge on the top, and another edge on the right.
- facing the same corner mentioned above, but with a distance of one grid.