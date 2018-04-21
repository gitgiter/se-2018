import info.gridworld.actor.Bug;

/**
 * A <code>DancingBug</code> traces as if a bug is dancing.
 */
public class DancingBug extends Bug {
  private int steps;
  private int sideLength;
  private int count;
  private int[] array;

  /**
   * Constructs a dancing bug that makes different turns before each move.
   * @param arr an integer array as parameter. 
   *        The integer entries in the array represent how many times the bug turns before it moves.
   */
  public DancingBug(int[] arr) {
    steps = 0;
    count = 0;

    // initialize the array
    sideLength = arr.length;
    array = new int[sideLength];
    for (int i = 0; i < arr.length; i++)
    {
        array[i] = arr[i];
    }
  }

  /**
   * Moves to the next location of the trace. steps of a sideLength is a movement,
   * after a movement the bug turns times that are defined by array.
   */
  public void act() {
    if (steps < sideLength && canMove())
    {
        move();
        steps++;
    } 
    else 
    {
        turnTimes(array[count++]);
        if (count == sideLength) 
        {
            count = 0;
        }
        steps = 0;
    }
  }

  /**
   * a function that makes the bug turn continuously.
   * @param times indicates how many turns will the bug act
   */
  public void turnTimes(int times)
    {
        for (int i = 0; i < times; i++)
        {
          turn();
        }            
    }
}