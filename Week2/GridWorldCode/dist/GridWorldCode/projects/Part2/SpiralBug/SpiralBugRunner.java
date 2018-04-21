import info.gridworld.actor.ActorWorld;
import info.gridworld.grid.Location;

import java.awt.Color;

/**
 * This class runs a world that contains spiral bugs.
 */
public class SpiralBugRunner
{
    public static void main(String[] args)
    {
        ActorWorld world = new ActorWorld();
        SpiralBug alice = new SpiralBug(5);
        alice.setColor(Color.ORANGE);
        SpiralBug bob = new SpiralBug(2);
        world.add(new Location(9, 9), alice);
        world.add(new Location(0, 0), bob);
        world.show();
    }
}