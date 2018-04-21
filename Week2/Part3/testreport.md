## Test Report
1. Test1: Test whether the function act() is correct
    - The code to test act
    ```java
    private Jumper jumper = new Jumper();
    private ActorWorld world = new ActorWorld();
    ```
    ```java
    @Test
    public void testAct() {
        world.add(new Location(2, 0), jumper);
        jumper.act();
        assertEquals(new Location(0, 0), jumper.getLocation());
        jumper.act();
        assertEquals(new Location(0, 0), jumper.getLocation());
        jumper.act();
        assertEquals(new Location(0, 0), jumper.getLocation());
        jumper.act();
        assertEquals(new Location(0, 2), jumper.getLocation());
    }
    ```
    - Check condition
        - Act to jump  
        The fisrt assertEquals statement. To check whether the jumper jump correctly.
        ```java
        assertEquals(new Location(0, 0), jumper.getLocation());
        ```
        The last assertEquals statement. To check whether the jumper also jump correctly in another direction.
        ```java
        assertEquals(new Location(0, 2), jumper.getLocation());
        ```
        - Act to turn  
        The two statements between the first and the last is used to check whether the bug does the correct turns, and whether it does not move when it is turning.
        ```java
        assertEquals(new Location(0, 2), jumper.getLocation());
        ```
    - Analysis
        - The first act  
        jump from (2, 0) to (0, 0)
        - The second act  
        turn 45°, and still in (0, 0)
        - The third act  
        turn another 45°, and still in (0, 0)
        - The fourth act  
        jump from (0, 0) to (0, 2)

2. Test2: Test whether the color is constructed correctly.
    - The code to test constructors
    ```java
    private Jumper jumper = new Jumper();
    private Jumper jumper2 = new Jumper(Color.black);
    ```
    ```java
    @Test
    public void testJumperColor() {
        assertEquals(Color.red, jumper.getColor());
        assertEquals(Color.black, jumper2.getColor());
    }
    ```
    - Check condition
        - The jumper's color
        The fisrt assertEquals statement. To check whether the first jumper's color is correctly.
        ```java
        assertEquals(Color.red, jumper.getColor());
        ```    
        - The jumper2's color  
        The second assertEquals statement. To check whether the jumper2's color also correctly.
        ```java
        assertEquals(Color.black, jumper2.getColor());
        ```
    - Analysis
        - The first jumper  
        It should have the default color---red
        - The second jumper  
        It should have the user defined color---black or others.

3. Test3: Test whether the bug turns correctly.
    - The code to test turn
    ```java
    private Jumper jumper = new Jumper();
    private ActorWorld world = new ActorWorld();
    ```
    ```java
    @Test
    public void testTurn() {
        world.add(new Location(2, 2), jumper);
        assertEquals(0, jumper.getDirection());
        jumper.turn();
        assertEquals(45, jumper.getDirection());
    }
    ```
    - Check condition
        - The direction before turning
        The fisrt assertEquals statement. To ensure the jumper's direction before turning.
        ```java
        assertEquals(0, jumper.getDirection());
        ```    
        - The direction after turning 
        The second assertEquals statement. To check whether the jumper's direction is correctly after turning.
        ```java
        assertEquals(45, jumper.getDirection());
        ```
    - Analysis
        - The direction before turning
        It should be the default direction---NORTH---0°
        - The direction after turning
        It should hbe 45° to the right of the default direction

4. Test4: Test whether the bug jump correctly.
    - The code to test jump
    ```java
    private Jumper jumper = new Jumper();
    private ActorWorld world = new ActorWorld();
    ```
    ```java
    @Test
    public void testJump() {
        world.add(new Location(4, 4), jumper);
        assertEquals(new Location(4, 4), jumper.getLocation());
        jumper.jump();
        assertEquals(new Location(2, 4), jumper.getLocation());
    }
    ```
    - Check condition
        - The Location before jumping
        The fisrt assertEquals statement. To ensure the jumper's location before jumping.
        ```java
        assertEquals(new Location(4, 4), jumper.getLocation());
        ```    
        - The Location after jumping 
        The second assertEquals statement. To check whether the jumper's location is correctly after jumping.
        ```java
        assertEquals(new Location(2, 4), jumper.getLocation());
        ```
    - Analysis
        - The Location before jumping  
        It should be at (4, 4)
        - The Location after jumping  
        It should be at (2, 4), because (2, 4) is empty so it can jump.

5. Test5: Test whether the canJump method return right boolean.
    - The code to test canJump
    ```java
    private Jumper jumper = new Jumper();
    private ActorWorld world = new ActorWorld();
    ```
    ```java
    @Test
    public void testCanJump() {
        world.add(new Location(2, 2), jumper);
        world.add(new Location(0, 2), rock);
        assertEquals(false, jumper.canJump());
        jumper.turn();
        world.add(new Location(4, 0), flower);
        assertEquals(true, jumper.canJump());
        jumper.jump();
        assertEquals(false, jumper.canJump());
        jumper.turn();
        jumper.turn();
        jumper.turn();
        world.add(new Location(4, 2), jumper2);
        assertEquals(true, jumper.canJump());
    }
    ```
    - Check condition
        - Rock
        The fisrt assertEquals statement. To check whether the jumper judge correctly while jumping onto a rock.
        ```java
        assertEquals(false, jumper.canJump());
        ```    
        - Flower
        The second assertEquals statement. To check whether the jumper judge correctly while jumping onto a flower.
        ```java
        assertEquals(true, jumper.canJump());
        ```
        - Edge
        The third assertEquals statement. To check whether the jumper judge correctly while jumping out of grid.
        ```java
        assertEquals(false, jumper.canJump());
        ```
        - Another jumper
        The fourth assertEquals statement. To check whether the jumper judge correctly while jumping towards the other jumper.
        ```java
        assertEquals(true, jumper.canJump());
        ```
    - Analysis
        - Rock  
        It cannot jump onto a rock so it should be false.
        - Flower  
        It can jump onto a flower so it should be true.
        - Edge
        It cannot jump out of grid so it should be false.
        - Another jumper  
        It can jump towards to the other jumper because the other jumper will jump away in next step.
