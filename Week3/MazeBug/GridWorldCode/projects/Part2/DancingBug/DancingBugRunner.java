import info.gridworld.actor.ActorWorld;
import info.gridworld.grid.Location;

import java.awt.*;

/**
 * This class runs a world that contains dancing bugs.
 */
public final class DancingBugRunner {
  /**
   * constructs a new runner instance doing nothings
   */
  private DancingBugRunner() {
  }

  public static void main(String[] args) {
    int[] arr = {4, 3, 2, 1};
    ActorWorld world = new ActorWorld();
    DancingBug alice = new DancingBug(arr);
    alice.setColor(Color.ORANGE);
    world.add(new Location(7, 8), alice);
    world.show();
  }
}