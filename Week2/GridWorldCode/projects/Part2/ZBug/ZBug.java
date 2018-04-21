import info.gridworld.actor.Bug;

/**
 * A <code>SpiralBug</code> traces out a shape of "Z" of a given size. 
 */
public class ZBug extends Bug
{
    private int steps;
    private int sideLength;
    private int turns;
    private boolean end;

    /**
     * Constructs a box bug that traces a shape of "Z" of a given side length
     * @param length the side length
     */
    public ZBug(int length)
    {
        steps = 0;
        sideLength = length;
        turns = 0;
        end = false;
        turnTimes(2);
    }

    /**
     * Moves to the next location of the Z.
     */
    public void act()
    {        
        // if end
        if (end) 
        {
            return;     
        }

        if (steps < sideLength && canMove())
        {
            move();
            steps++;

            // if the bug has turned twice
            if (turns >= 2 && steps == sideLength) 
            {
                end = true;   
            }
        }
        else
        {
            if (steps >= sideLength)
            {
                if (turns == 0)
                {
                    turnTimes(3);
                }                    
                else {
                    turnTimes(5);
                }                
                //turns == 0 ? turnTimes(3) : turnTimes(5);
                steps = 0;
                turns++;
            }
            else 
            {
                end = true;
            }
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
