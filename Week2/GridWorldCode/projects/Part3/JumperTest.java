import static org.junit.Assert.*;
import org.junit.*;
import java.io.*;

import info.gridworld.actor.Actor;
import info.gridworld.actor.ActorWorld;
import info.gridworld.actor.Bug;
import info.gridworld.actor.Rock;
import info.gridworld.grid.Location;

import java.awt.*;

public class JumperTest {
  private Jumper jumper = new Jumper();
  private Jumper jumper2 = new Jumper(Color.black);
  private ActorWorld world = new ActorWorld();
  private Rock rock = new Rock();

  @Before
  public void setUp() throws Exception {
    int row = 0;
    int col = 0;
    col = world.getGrid().getNumCols();
    row = world.getGrid().getNumRows();
    int i = 0;
    int j = 0;
    while (i < row) {
      j = 0;
      while (j < col) {
        world.remove(new Location(i, j));
        j++;
      }
      i++;
    }
  }

  /**
   * check the act method.
   */
  @Test
  public void testAct() {
    world.add(new Location(2, 2), jumper);
    jumper.act();
    jumper.act();
    jumper.act();
    jumper.act();
    assertEquals(new Location(0, 4), jumper.getLocation());
  }

  /**
   * check the constructors.
   */
  @Test
  public void testJumperColor() {
    assertEquals(Color.red, jumper.getColor());
    assertEquals(Color.black, jumper2.getColor());
  }

  /**
   * check the turn method.
   */
  @Test
  public void testTurn() {
    world.add(new Location(2, 2), jumper);
    jumper.turn();
    assertEquals(45, jumper.getDirection());
  }

  /**
   * check the jump method.
   */
  @Test
  public void testJump() {
    world.add(new Location(2, 2), jumper);
    jumper.jump();
    assertEquals(new Location(0, 2), jumper.getLocation());
  }

  /**
   * check the canJump method.
   */
  @Test
  public void testCanJump() {
    world.add(new Location(2, 2), jumper);
    world.add(new Location(0, 2), rock);
    assertEquals(false, jumper.canJump());
  }

}