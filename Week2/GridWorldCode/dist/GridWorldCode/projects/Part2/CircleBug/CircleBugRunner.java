import info.gridworld.actor.ActorWorld;
import info.gridworld.grid.Location;

import java.awt.Color;

/**
 * This class runs a world that contains Circle bugs.
 */
public final class CircleBugRunner {
  /**
   * constructs a new runner instance doing nothings
   */
  private CircleBugRunner() {
  }

  public static void main(String[] args) {
    ActorWorld world = new ActorWorld();
    CircleBug alice = new CircleBug(2);
    alice.setColor(Color.ORANGE);
    CircleBug bob = new CircleBug(4);
    world.add(new Location(0, 0), alice);
    world.add(new Location(9, 9), bob);
    world.show();
  }
}
