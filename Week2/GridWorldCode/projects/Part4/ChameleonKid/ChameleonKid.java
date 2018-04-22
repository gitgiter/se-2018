import info.gridworld.actor.Actor;
import info.gridworld.actor.Critter;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

import java.awt.*;
import java.util.ArrayList;

/**
 * A <code>ChameleonKid</code> takes on the color of neighboring actors as it
 * moves through the grid. <br />
 * The implementation of this class is testable on the AP CS A and AB exams.
 */
public class ChameleonKid extends ModifiedChameleonCritter {

    /**
    * A ChameleonKid gets the actors in the locations 
    * immediately front or behind
    *
    * @return a list of actors with two location elements
    */
    public ArrayList<Actor> getActors() {
        ArrayList<Actor> actors = new ArrayList<Actor>();
        int[] dirs = {Location.AHEAD, Location.HALF_CIRCLE};
        for (Location loc : getLocationsInDirections(dirs)) {
            Actor a = getGrid().get(loc);
            if (a != null) {
                actors.add(a);
            }
        }
        return actors;
    }

    public ArrayList<Location> getLocationsInDirections(int[] directions) {
        ArrayList<Location> locs = new ArrayList<Location>();
        Grid gr = getGrid();
        Location loc = getLocation();

        for (int d : directions) {
            Location neighborLoc = loc.getAdjacentLocation(getDirection() + d);
            if (gr.isValid(neighborLoc)) {
                locs.add(neighborLoc);
            }
        }
        return locs;
    }
}