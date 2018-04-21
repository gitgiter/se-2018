import static org.junit.Assert.*;
import org.junit.*;
import java.io.*;

import info.gridworld.actor.Actor;
import info.gridworld.actor.ActorWorld;
import info.gridworld.actor.Bug;
import info.gridworld.actor.Rock;
import info.gridworld.actor.Flower;
import info.gridworld.grid.Location;

import java.awt.*;

public class JumperTest {
    private Jumper jumper = new Jumper();
    private Jumper jumper2 = new Jumper(Color.black);
    private ActorWorld world = new ActorWorld();
    private Rock rock = new Rock();
    private Flower flower = new Flower();
    private Bug bug = new Bug();

    @Before
    public void setUp() throws Exception {
        int row = 0;
        int col = 0;
        col = world.getGrid().getNumCols();
        row = world.getGrid().getNumRows();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                world.remove(new Location(i, j));
            }
        }
    }

    /**
     * check the act method.
     */
    @Test
    public void testAct() {
        world.add(new Location(2, 0), jumper);
        jumper.act();
        jumper.act();
        assertEquals(new Location(0, 0), jumper.getLocation());
        jumper.act();
        jumper.act();
        assertEquals(new Location(0, 2), jumper.getLocation());
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
        assertEquals(0, jumper.getDirection());
        jumper.turn();
        assertEquals(45, jumper.getDirection());
    }

    /**
     * check the jump method.
     */
    @Test
    public void testJump() {
        world.add(new Location(4, 4), jumper);
        assertEquals(new Location(4, 4), jumper.getLocation());
        jumper.jump();
        assertEquals(new Location(2, 4), jumper.getLocation());
    }

    /**
     * check the canJump method.
     */
    @Test
    public void testCanJump() {
        world.add(new Location(2, 2), jumper);
        world.add(new Location(0, 2), rock);
        assertEquals(false, jumper.canJump());
        jumper.turn();
        world.add(new Location(0, 4), flower);
        assertEquals(true, jumper.canJump());
        jumper.jump();
        assertEquals(false, jumper.canJump());
        jumper.turn();
        jumper.turn();
        jumper.turn();
        world.add(new Location(2, 4), jumper2);        
        assertEquals(false, jumper.canJump());
        jumper.turn();
        world.add(new Location(2, 2), bug);
        assertEquals(false, jumper.canJump());
    }
}