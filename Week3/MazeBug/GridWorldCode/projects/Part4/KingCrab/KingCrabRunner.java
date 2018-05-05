import info.gridworld.actor.ActorWorld;
import info.gridworld.actor.Bug;
import info.gridworld.actor.Flower;
import info.gridworld.actor.Rock;
import info.gridworld.grid.Location;

/**
 * This class runs a world that contains KingCrab critters. <br />
 * This class is not tested on the AP CS A and AB exams.
 */
public final class KingCrabRunner {
    private KingCrabRunner() {
    }

    public static void main(String[] args) {
        ActorWorld world = new ActorWorld();
        world.add(new Location(7, 5), new Rock());
        world.add(new Location(7, 3), new Rock());
        world.add(new Location(7, 8), new Flower());
        world.add(new Location(3, 8), new Flower());
        world.add(new Location(5, 3), new Bug());
        world.add(new Location(7, 4), new KingCrab());
        world.show();
    }
}