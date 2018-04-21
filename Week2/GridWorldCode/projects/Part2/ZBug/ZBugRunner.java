import info.gridworld.actor.ActorWorld;
import info.gridworld.grid.Location;

import java.awt.Color;

/**
 * This class runs a world that contains z bugs.
 */
public class ZBugRunner
{
    public static void main(String[] args)
    {
        ActorWorld world = new ActorWorld();
        //ZBug alice = new SpiralBug(5);
        //alice.setColor(Color.ORANGE);
        ZBug bob = new ZBug(4);
        //world.add(new Location(9, 9), alice);
        world.add(new Location(0, 0), bob);
        world.show();
    }
}