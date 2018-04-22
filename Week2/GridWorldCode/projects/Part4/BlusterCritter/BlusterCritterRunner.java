import info.gridworld.actor.ActorWorld;
import info.gridworld.actor.Rock;
import info.gridworld.grid.Location;

/**
 * This class runs a world that contains bluster critters. <br />
 * This class is not tested on the AP CS A and AB exams.
 */
public final class BlusterCritterRunner {
    public BlusterCritterRunner() {
    }

    public static void main(String[] args) {
        ActorWorld world = new ActorWorld();
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 4; col++) {
                world.add(new Location(row, col), new Rock());
            }
        }
        for (int row = 1; row < 4; row++) {
            for (int col = 1; col < 3; col++) {
                world.add(new Location(row, col), new BlusterCritter(2 * row + col));
            }
        }
        world.show();
    }
}