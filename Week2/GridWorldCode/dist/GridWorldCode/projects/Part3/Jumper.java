import info.gridworld.actor.Actor;
import info.gridworld.actor.Flower;
import info.gridworld.actor.Rock;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

import java.awt.*;

public class Jumper extends Actor {

    /**
    * Construct a red jumper.
    */
    public Jumper() {
        setColor(Color.RED);
    }

    /**
    * Construct a jumper and set the color of jumper.
    * @param color the color for this jumper
    */
    public Jumper(Color color) {
        setColor(color);
    }

    /**
    * Jumps to the next location of the jump path.
    * Jumps if it can jump, turns otherwise.
    */
    public void act() {
        if (canJump()) {
            jump();
        } else {
            turn();
        }
    }

    /**
    * Turns 45 degrees to the right.
    */
    public void turn() {
        setDirection(getDirection() + Location.HALF_RIGHT);
    }

    /**
    * check the second cell that in front of the jumper,
    * if can jump, then moveTo the cell and remove itself,
    * and not to put a flower in old cell.
    */
    public void jump() {
        Grid<Actor> gr = getGrid();
        if (gr == null) {
            return;
        }
        Location loc = getLocation();
        Location oneNext = loc.getAdjacentLocation(getDirection());
        Location twoNext = oneNext.getAdjacentLocation(getDirection());
        if (gr.isValid(twoNext)) {
            moveTo(twoNext);
        } else {
            removeSelfFromGrid();
        }
    }

    /**
    * Tests whether this jumper can jump forward into a location that is empty or
    * contains a flower.
    *
    * @return true if the jumper can jump.
    */
    public boolean canJump() {
        Grid<Actor> gr = getGrid();
        if (gr == null) {
            return false;
        }
        Location loc = getLocation();
        Location oneNext = loc.getAdjacentLocation(getDirection());
        Location twoNext = oneNext.getAdjacentLocation(getDirection());
        if (!gr.isValid(twoNext)) {
            return false;
        }
        Actor neighbor = gr.get(twoNext);
        return (neighbor == null) || (neighbor instanceof Flower);
        // ok to move into empty location or onto flower
        // not ok to move onto any other actor
    }    

}