package info.gridworld.maze;

import info.gridworld.actor.Actor;
import info.gridworld.actor.Bug;
import info.gridworld.actor.Flower;
import info.gridworld.actor.Rock;
import info.gridworld.grid.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

import javax.swing.JOptionPane;

/**
 * A <code>MazeBug</code> can find its way in a maze. <br />
 * The implementation of this class is testable on the AP CS A and AB exams.
 */
public class MazeBug extends Bug {
	public Location next;
	public Location last;
	public boolean isEnd = false;
	public Stack<ArrayList<Location>> crossLocation = new Stack<ArrayList<Location>>();
	public Integer stepCount = 0;
	//final message has been shown
	boolean hasShown = false;
	//store the visited location
	public boolean isVisit[][];
	//
	ArrayList<Location> branch;

	/**
	 * Constructs a box bug that traces a square of a given side length
	 * 
	 * @param length
	 *            the side length
	 */
	public MazeBug() {
		setColor(Color.GREEN);
		last = new Location(0, 0);

		int size = 100;
		isVisit = new boolean[size][size];
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                isVisit[i][j] = false;

		Location loc = getLocation();
        branch = new ArrayList<Location>();
        branch.add(loc);
	}

	/**
	 * Moves to the next location of the square.
	 */
	public void act() {
		boolean willMove = canMove();
		if (isEnd == true) {
		//to show step count when reach the goal		
			if (hasShown == false) {
				String msg = stepCount.toString() + " steps";
				JOptionPane.showMessageDialog(null, msg);
				hasShown = true;
			}
		} 
		else if (willMove) {
			//move when can move
			move();
			//increase step count when move 
			stepCount++;
			//set the location moving to visited
			isVisit[next.getRow()][next.getCol()] = true;
		} 
		else {
			//go back when cannot move
			goBack();
			//increase step count when go back
			stepCount++;
		}
	}

	/**
	 * Find all positions that can be move to.
	 * 
	 * @param loc
	 *            the location to detect.
	 * @return List of positions.
	 */
	public ArrayList<Location> getValid(Location loc) {
		Grid<Actor> gr = getGrid();
		if (gr == null) {
			return null;
		}
		//to save the valid locations and return
		ArrayList<Location> valid = new ArrayList<Location>();
				
		//the four valid locations
        int[] dirs =
            { Location.AHEAD, Location.RIGHT, Location.HALF_CIRCLE, Location.LEFT };

        for (int d : dirs) {
        	//test each directions
            Location neighborLoc = loc.getAdjacentLocation(getDirection() + d);
            int row = neighborLoc.getRow();
            int col = neighborLoc.getCol();
            //test whether the location is out of grid
            if (gr.isValid(neighborLoc)) {
            	//get the actors in the grid
                Actor a = gr.get(neighborLoc);
                //is valid if the grid is empty or with a flower
                //and it must have not been visited
                if ((a == null || a instanceof Flower) && !isVisit[row][col]) {
                	//add the valid location to the list
                    valid.add(neighborLoc);
                } 
                //test whether the bug reach the end of maze, which is a red rock
                else if (a instanceof Rock && a.getColor().equals(Color.RED)) {
                    isEnd = true;
                }
            }
        }

		return valid;
	}

	/**
	 * Tests whether this bug can move forward into a location that is empty or
	 * contains a flower.
	 * 
	 * @return true if this bug can move.
	 */
	public boolean canMove() {
		//get the valid location, which is at most four
		ArrayList<Location> loc = getValid(getLocation());        

        //if there is not valid location adjacent to current location
        if (loc.isEmpty()) {
            return false;
        }
        //has valid location(s)
        else {
        	//add current location to the path
            branch.add(getLocation());

            //if there are more than 1 valid location
            int locSize = loc.size();
            if (locSize >= 2) {
                crossLocation.push(branch);
                branch = new ArrayList<Location>();
            }
            //we have to choose randomly
            int romdom = (int)(Math.random() * (locSize - 1));
            next = loc.get(romdom);
            last = getLocation();
        }

        return true;
	}
	/**
	 * Moves the bug forward, putting a flower into the location it previously
	 * occupied.
	 */
	public void move() {
		Grid<Actor> gr = getGrid();
		if (gr == null) {
			return;
		}
		//get the current location
		Location loc = getLocation();
		//if the current location is in the grid
		if (gr.isValid(next)) {
			//
			setDirection(getLocation().getDirectionToward(next));
			moveTo(next);
		}
		//if the current location is out of grid 
		else {
			//remove itself from the grid
			removeSelfFromGrid();
		}			
		//left a flower behind
		Flower flower = new Flower(getColor());
		flower.putSelfInGrid(gr, loc);
	}
	/**
	 * Moves the bug backward, 
	 * eating the flower left in the location it previously occupied.
	 */
	public void goBack() {
        if (branch.isEmpty())
            branch = crossLocation.pop();
        int branchSize = branch.size();
        next = branch.remove(branchSize - 1);
        move();
    }
}
