import info.gridworld.actor.Actor;
import info.gridworld.actor.Critter;
import info.gridworld.grid.Location;

import java.awt.*;
import java.util.ArrayList;

/**
 * A <code>ModifiedChameleonCritter</code> takes on the color of neighboring
 * actors as it moves through the grid. <br />
 * The implementation of this class is testable on the AP CS A and AB exams.
 */
public class ModifiedChameleonCritter extends Critter {

	/**
	* Randomly selects a neighbor and changes this critter's color to be the same
	* as that neighbor's. If there are no neighbors, its color will darken.
	*/
	public void processActors(ArrayList<Actor> actors) {
		int n = actors.size();
		if (n == 0) {
			darken();
			return;
		}

		int r = (int) (Math.random() * n);
		setColor(actors.get(r).getColor());
	}

	/**
	* Turns towards the new location as it moves.
	*/
	public void makeMove(Location loc) {
		setDirection(getLocation().getDirectionToward(loc));
		super.makeMove(loc);
	}

	private void darken() {
		Color color = getColor();
		setColor(color.darker());
	}
}