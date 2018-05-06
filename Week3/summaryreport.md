# se-2018 中级实训总结
---
- **本篇总结报告说明**：由于第一阶段的自学报告写得非常详细，所以本次总结着重介绍和记录后两阶段的学习内容、遇到的问题及其解决方案

---
## 第一阶段
本次实训第一阶段自学了Vi,Java,Ant,Junit的相关知识。熟悉了Vi的三种开发模式和基本的使用方法，初步了解和认识Java的语法，实现简单的helloworld和图形计算器，掌握利用Ant编译和运行项目的方法，利用Junit实现对类方法的测试。同时也更加熟悉了Linux命令行的开发环境。  
相关的详细学习过程请见第一阶段的学习报告（真的非常详细Orz）。
[传送门](https://gitgiter.github.io/2018/04/15/SYSU-SE-2018-Part1/)
## 第二阶段
本阶段实训主要完成Part2、Part3、Part4和Part5的任务要求以及大量的简答题。具体过程大致如下。
- Part2
    - CircleBug  
    CircleBug继承Bug，实现该类的目的是使虫子能按类似圆圈的路径运动，其实就是一个多边形，在原来BoxBug的基础上将每次要转向时转两次改成转一次，就能将其运动轨迹改为多边形。
    ```java
    public void act() {
        if (steps < sideLength && canMove()) {
            move();
            steps++;
        } else {
            turn();
            steps = 0;
        }
    }
    ```
    - SpiralBug  
    SpiralBug继承Bug，实现该类的目的是使虫子能按不断螺旋状扩散的路线运动，只需要在原来BoxBug的基础上，每次转向的时候增加下一条要走的边，就能将其运动轨迹的边长不断扩增，最后形成一个螺旋状的路线。
    ```java
    public void act()
    {
        if (steps < sideLength && canMove())
        {
            move();
            steps++;
        }
        else
        {
            turn();
            turn();
            steps = 0;
            sideLength++;
        }
    }
    ```
    - ZBug  
    ZBug继承Bug，实现该类的目的是使虫子能走Z字型路线，这个实现比之前两个就要稍微多一些判断，主要是起点和终点的判断以及判断是在Z字的第几个拐点（因为在第一个拐点和第二个拐点的转向次数不一样，一个是3一个是5）。  
    核心代码如下，其中turnTimes函数是为了简化转向次数而抽象出来的函数，参数是几就是转几下。比如一开始的时候要让ZBug面朝东边，就需要在构造函数里调用turnTimes(2)。
    ```java
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
    ```
    - DancingBug  
    DancingBug继承Bug，相比于前面几个类型的Bug，DancingBug相对改动比较多一些。主要是处理构造时传进来的一个数组，数组中的元素的值代表DancingBug每次要转向的时候按照数组中规定的值来决定转多少下，数组中的值依次且循环使用。
    ```java
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
    ```
- Part3
    - Jumper类  
    Jumper继承Actor，主要实现以下两个方法，jump()和canJump()。jump()是move()的改写版，区别就是move()移动一个格子，jump()移动两格。为此要沿着当前Jumper面朝的方向连续搜索两格。canJump()则是负责判断当前Jumper所处状态是否允许jump，要考虑的状态主要是将要跳向的格子里是否有其他Actor或者越界，如果是Actor，则再区分石头和花以及其他Bug或Jumper本身。
    ```java
    /**
    * check the second cell that in front of the jumper,
    * if can jump, then moveTo the cell and remove itself,
    * and not to put a flower in old cell.
    */
    public void jump() {
        Grid<Actor> gr = getGrid();
        if (gr == null) {
            return;
        }
        Location loc = getLocation();
        Location oneNext = loc.getAdjacentLocation(getDirection());
        Location twoNext = oneNext.getAdjacentLocation(getDirection());
        if (gr.isValid(twoNext)) {
            moveTo(twoNext);
        } else {
            removeSelfFromGrid();
        }
    }

    /**
    * Tests whether this jumper can jump forward into a location that is empty or
    * contains a flower.
    *
    * @return true if the jumper can jump.
    */
    public boolean canJump() {
        Grid<Actor> gr = getGrid();
        if (gr == null) {
            return false;
        }
        Location loc = getLocation();
        Location oneNext = loc.getAdjacentLocation(getDirection());
        Location twoNext = oneNext.getAdjacentLocation(getDirection());
        if (!gr.isValid(twoNext)) {
            return false;
        }
        Actor neighbor = gr.get(twoNext);
        return (neighbor == null) || (neighbor instanceof Flower);
        // ok to move into empty location or onto flower
        // not ok to move onto any other actor
    }
    ```
    - Junit测试  
    Junit测试主要是将各个函数的执行结果测试一遍，以及碰见各种Actor后的canJump()做出的判断是否正确。  
    下面以测试canJump函数为例，依次测试了Jumper将要跳向的格子里面有rock，flower，越界，其他jumper，其他bug的情况，对应的canJump应该只有是跳向flower的时候才返回true
    ```java
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
    ```
- Part4
    - ModifiedChameleonCritter  
    ModifiedChameleonCritter继承Critter，在和ChameleonCritter相同功能的基础上，再实现一个没吃到东西时会变暗的效果。
    ```java
    public void processActors(ArrayList<Actor> actors) {
		int n = actors.size();
		if (n == 0) {
			darken();
			return;
		}

		int r = (int) (Math.random() * n);
		setColor(actors.get(r).getColor());
	}

    private void darken() {
		Color color = getColor();
		setColor(color.darker());
	}
    ```
    - ChameleonKid  
    题目要求是ChameleonKid继承ChameleonCritter，但是由于ChameleonKid一样也要实现变暗效果，于是我干脆让ChameleonKid继承有变暗效果的ModifiedChameleonCritter，在其基础上，再实现一个只在碰见前后有其他Actor的时候才会变成与其同色的效果，因为原来的ModifiedChameleonCritter也有会变色的效果，所以只需要改getActor函数，使其只获得前后的Actor。
    ```java
    public ArrayList<Actor> getActors() {
        ArrayList<Actor> actors = new ArrayList<Actor>();
        int[] dirs = {Location.AHEAD, Location.HALF_CIRCLE};
        for (Location loc : getLocationsInDirections(dirs)) {
            Actor a = getGrid().get(loc);
            if (a != null) {
                actors.add(a);
            }
        }
        return actors;
    }
    ```
    - RockHound  
    RockHound继承Critter，目的是要实现一个能把其相邻格子的石块去掉的功能。
    ```java
    public void processActors(ArrayList<Actor> actors) {
        for (Actor a : actors) {
            if (a instanceof Rock) {
                a.removeSelfFromGrid();
            }
        }
    }
    ```
    - BlusterCritter  
    BlusterCritter继承Critter，主要是实现一个courage的维护和管理。计算其相邻的两层格子内的所有其他critter，如果数量超过BlusterCritter自身的勇气值courage，BlusterCritter的颜色就会渐渐变暗，否则越来越亮。
    ```java
    public void processActors(ArrayList<Actor> actors) {
        int count = 0;
        for (Actor a : actors) {
            if (a instanceof Critter) {
                count++;
            }
        }
        if (count < courage) {
            colorChange(1);
            return;
        } 
        else {
            colorChange(0);
            return;
        }
    }

    private void colorChange(int choice) {
        Color color = getColor();
        if (choice == 0) {
            setColor(color.darker());
        }
        else {
            setColor(color.brighter());
        }
    }
    ```
    - QuickCrab  
    QuickCrab继承CrabCritter，处理Actor的方法一致，只是在其基础上实现移动时随机向左或右一次性移动两步。
    ```java
    public ArrayList<Location> getMoveLocations() {
        ArrayList<Location> locs = new ArrayList<Location>();
        int[] dirs = {Location.LEFT, Location.RIGHT};
        getMoveLocationsInDirection(locs, getDirection() + dirs[0]);
        getMoveLocationsInDirection(locs, getDirection() + dirs[1]);

        if (locs.size() == 0) {
            return super.getMoveLocations();
        }
        return locs;
    }

    public void getMoveLocationsInDirection(ArrayList<Location> locs, int direction) {
        Grid gr = getGrid();
        Location currentLoc = getLocation();

        Location neighbor1 = currentLoc.getAdjacentLocation(direction);
        if (gr.isValid(neighbor1) && gr.get(neighbor1) == null) {
            Location neighbor2 = neighbor1.getAdjacentLocation(direction);
            if (gr.isValid(neighbor2) && gr.get(neighbor2) == null) {
                locs.add(neighbor2);
            }
        }
    }
    ```
    - KingCrab  
    KingCrab继承CrabCritter，主要实现是使其可以推动在其前面的石头。
    ```java
    public void processActors(ArrayList<Actor> actors) {
        Grid gr = getGrid();
        int direction = getDirection();
        for (Actor a : actors) {
            Location temp = a.getLocation().getAdjacentLocation(direction + Location.AHEAD);
            if (gr.isValid(temp)) {
                a.moveTo(temp);
            } 
            else {
                a.removeSelfFromGrid();
            }
        }
    }
    ```
- Part5
    - SparseBoundedGrid1  
    用链表实现一个有界gridworld。因为当gridworld中的元素是稀疏的时候，采用原来的数组的实现方式很浪费空间，查找起来也比较费劲。采用链表的方式，可以将每个行视为一个链表，每个链表连接该行上的已存在（占用了格子）的元素。这样一个gridworld就可以用一个大小为行数的链表数组表示了，相对节省空间且查找效率高。
    ```java
    public E get(Location loc) {
    	// get the node related to the loc from the list
        SparseGridNode node = getNode(loc);
        if (node != null) {
            return (E) node.getOccupant();
        } 
        else {
            return null;
        }
    }

    public E put(Location loc, E obj) {
        if (!isValid(loc)) {
            throw new IllegalArgumentException("Location " + loc + " is not valid");
        }
        if (obj == null) {
            throw new NullPointerException("obj == null");
        }

        // Add the object to the grid. 
        // Remove the old object from the grid.
        // Push the new occupant into the list.
        E oldOccupant = remove(loc);
        SparseGridNode oldHead = occupantArray[loc.getRow()];
        SparseGridNode newHead = new SparseGridNode(obj, loc.getCol(), null, oldHead);
        if (oldHead != null) {
            oldHead.setPre(newHead);
        }
        occupantArray[loc.getRow()] = newHead;
        return oldOccupant;
    }

    public E remove(Location loc) {
	    if (!isValid(loc)) {
	        throw new IllegalArgumentException("Location " + loc + " is not valid");
	    }

	    // Remove the object from the grid. 
	    // if no found return null
	    SparseGridNode removeNode = getNode(loc);
	    if (removeNode == null) {
	        return null;
	    }

	    // cast the type to E in order to return
	    E ENode = get(removeNode);

	    // modify the list
	    SparseGridNode preNode = removeNode.getPre();
	    SparseGridNode nextNode = removeNode.getNext();
	    if (preNode != null) {
	    	// link the preNode to the nextNode
	    	preNode.setNext(nextNode);
	    } 
	    else if (occupantArray[loc.getRow()] != null) {
	        // reset the head to nextNode because the removeNode is the head
	        occupantArray[loc.getRow()] = nextNode;
        }
        // link the nextNode to the preNode
        if (nextNode != null) {
            nextNode.setPre(preNode);
        }

    	return ENode;
    }
    ```
    - SparseBoundedGrid2  
    用map实现一个有界gridworld。目的同样是解决原来数组记录的弊端。这里是利用Hashmap来记录对应的location和对象模板E，location为键值，对应的E则为value，每在gridworld中新增或删除一个元素时，只需要在Hashmap中插入和移除对应的键值对即可。相比于上一个链表的解决方案，使用Hashmap的方法总体效率还要更高些，许多访问操作的复杂度通常情况都在o(1)左右。
    ```java
    public E get(Location loc) {
        if (!isValid(loc)) {
            throw new IllegalArgumentException("Location " + loc + " is not valid");
        }
        return occupantMap.get(loc);
    }

    public E put(Location loc, E obj) {
        if (!isValid(loc)) {
            throw new IllegalArgumentException("Location " + loc + " is not valid");
        }
        if (obj == null) {
             throw new NullPointerException("obj == null");
        }           
        return occupantMap.put(loc, obj);
    }

    public E remove(Location loc) {
        if (!isValid(loc)) {
            throw new IllegalArgumentException("Location " + loc + " is not valid");
        }
        return occupantMap.remove(loc);
    }
    ```
    - UnboundedGrid2  
    用数组实现一个无界gridworld。实现方式类似原来的有界gridworld的实现。主要的区别是无界的数组可以扩展。当一个新插入的元素超出原来的数组记录范围的时候，数组的大小会一直倍增直至数组能记录该越界的元素。
    ```java
    private void resize(Location loc) {
        // double the size until it is greater than needed
        int size = dimension;
        while (loc.getRow() >= size || loc.getCol() >= size) {
            size *= 2;
        }

        // create a new resized array
        Object[][] temp = new Object[size][size];

        // move the old array to the new array
        for (int r = 0; r < dimension; r++) {
            for (int c = 0; c < dimension; c++) {
                temp[r][c] = occupantArray[r][c];
            }
        }
            
        // reset the properties
        occupantArray = temp;
        dimension = size;
    }
    ```

## 第三阶段
- ImageReader
    - ImplementImageIO
        - 实现IImageIO的myRead接口  
        使用二进制读取图片文件的方式，解析读到的位图文件的位图头、位图信息。主要是对读到的位图信息的一个解析，要将每四个字节代表的数字转成Java数字变量，以进行后面的运算和判断。转化的方法就是对这四个字节代表的数字赋予不同的位权，也就是移位，有了位权之后再将四个字节组合起来看就可以得到这四个字节代表的数字。另外一个重点是，RGB像素的理解。这里用的是24位彩图，每个像素是3个字节，分别对应红绿蓝三种颜色。除了24位图还有的就是，1位（八个像素一个字节），4位（两个像素一个字节），8位（一个像素一个字节），这三种都需要用调色板来对自己表示的颜色进行编号，24位则不用。需要注意的是，Java里面的Image是需要每个像素四个字节的，其中第四个字节是指图片的透明度。因为保存到图片文件里面的时候是不记录透明度的，所以在用从文件里读取和解析出来的RGB像素信息构造Java的Image时需要对每个像素再加一个字节，代表透明度（默认不透明）。
        - 实现IImageIO的myWrite接口（使用ImageIO.write()）
    - ImplementImageProcessor  
    实现IImageProcessor的四个色彩通道筛选接口，红绿蓝三种色彩通道的实现方法是类似的，都可以通过只提取RGB三色中对应的一种颜色来完成颜色筛选，而灰色稍有不同，灰色的RGB需要由计算得出，因此用到一条灰度公式 I = 0.299 * R + 0.587 * G + 0.114 * B。通过这条公式可以将原来的彩色图像的RGB转化为灰色图像的RGB，这条公式的大致原理就是使RGB三色的值大致趋同，三色对应的RGB值越接近，就越接近灰色。
    ```java
    public int filterRGB(int x, int y, int rgb){   
        if(colorNum==1){  
            //red
            return ( rgb & 0xffff0000 );  
        }
        else if(colorNum==2){  
            //green
            return ( rgb & 0xff00ff00 );  
        }
        else if(colorNum==3){  
            //blue
            return ( rgb & 0xff0000ff );  
        }
        else{  
            //gray, I = 0.299 * R + 0.587 * G + 0.114 *B
            int g = (int)( ((rgb & 0x00ff0000) >> 16)*0.299 + ((rgb & 0x0000ff00) >> 8 )*0.587  
                    + ((rgb & 0x000000ff))*0.114 );  
            return (rgb & 0xff000000) + (g << 16) + (g << 8) + g;  
        }  
    }
    ```
    - ImageProcessorTest  
    一共进行了八个测试，基本上涵盖了所有该测试的功能。分别测试了1.bmp的四个色彩通道提取和2.bmp的四个色彩通道的提取，在测试色彩通道提取的过程中也顺带测试了图片的读取。因为图片的输出是调用的ImageIO.write()所以没有测试。
    ```java
    @Test
    public void testmyBlue1() throws IOException {
        testOne("1", "blue");
    }

    @Test
    public void testmyBlue2() throws IOException {
        testOne("2", "blue");
    }

    @Test
    public void testGreen1() throws IOException {
        testOne("1", "green");
    }

    @Test
    public void testGreen2() throws IOException {
        testOne("2", "green");
    }

    @Test
    public void testRed1() throws IOException {
        testOne("1", "red");
    }

    @Test
    public void testRed2() throws IOException {
        testOne("2", "red");
    }

    @Test
    public void testGray1() throws IOException {
        testOne("1", "gray");
    }

    @Test
    public void testGray2() throws IOException {
        testOne("1", "gray");
    }
    ```
    这里提一下图片对比的思路，要求是比较自己处理的图片和目标图片的位图宽度、位图高度以及像素值，像素值其实也就是RGB的值。而由前面位图信息的知识可以得知，下面这个是为了减少代码重复度抽取出来的一个函数，可以根据传入的参数决定测试哪幅图和对应的哪种色彩通道测试。
    ```java
    public void testOne(String which, String color) throws IOException {

        ...//省略图片的读取实现

        switch (color)
        {
            case "blue":
                myProcessedImage = processor.showChanelB(image);
                break;
            case "green":
                myProcessedImage = processor.showChanelG(image);
                break;
            case "red":
                myProcessedImage = processor.showChanelR(image);
                break;
            case "gray":
                myProcessedImage = processor.showGray(image);
                break;
        }

       ... //省略图片的处理
        //比较颜色值
        for (int i = 0; i < goalImage.getWidth(null); i++) {
            for (int j = 0; j < goalImage.getHeight(null); j++) {
                assertEquals(goalImage.getRGB(i, j), myBufferedImage.getRGB(i, j));
            }                
        }            
        //比较宽高
        assertEquals(myProcessedImage.getWidth(null), goalImage.getWidth(null));
        assertEquals(myProcessedImage.getHeight(null), goalImage.getHeight(null));
    }
    ```
- MazeBug
    - 基础---八方向->四方向  
    这里只需要修改提供的MazeBug.java里面的getValid函数，使其在原来八个方向都valid的情况下，只挑出四个方向valid即上下左右，即返回的ArrayList<Location>最多只有四个元素。
    ```java
    //the four valid locations
    int[] dirs =
        { Location.AHEAD, Location.RIGHT, Location.HALF_CIRCLE, Location.LEFT };

    for (int d : dirs) {
        //test each directions
        Location neighborLoc = loc.getAdjacentLocation(getDirection() + d);
        
        ...

    }
    ```
    - 提高---dfs  
        - 深度优先搜索算法的基本思想。是沿着树的深度遍历树的节点，尽可能深地搜索树的分支，换句话说就是一条路走到黑。深度优先算法将不停地一直搜索，直至搜索到想要的节点或者所有节点都已被访问过（即没搜到）。所以将深度优先算法应用于走迷宫是个很合适的选择，前提是要解的迷宫是无环的，否则会导致深度优先算法无法遍历所有节点，可能会出现节点存在但搜不到的情况。
        - 深度优先搜索算法的基本步骤。
            - 先将树的所有节点标记为”未访问”状态。
            - 输出起始节点，将起始节点标记为”已访问”状态。
            - 将起始节点入栈。
            - 当栈非空时重复执行以下步骤：
                1. 取当前栈顶节点。
                2. 如果当前栈顶节点是结束节点（迷宫出口），输出该节点，结束搜索。
                3. 如果当前栈顶节点存在”未访问”状态的邻接节点，则选择一个未访问节点，置为”已访问”状态，并将它入栈，继续步骤1。
                4. 如果当前栈顶节点不存在”未访问”状态的邻接节点，则将栈顶节点出栈，继续步骤1。            
    - 进阶---方向概率选择
        - 原理。五个评估成绩的迷宫都有一定的方向偏向性，如图四就有向上和向左的偏向性。在行走正确路径时，对四个方向的选择次数进行统计，从而控制随机选择时选择某个方向的概率。增加方向的概率估计后能有效地提高走迷宫的效率。
        - 方向次数数组的维护。四个方向选择次数默认都是1，如果第一个节点选择向左，则向左次数加1，同理，其他方向和其他节点，选什么方向就什么方向次数加1。而减少的时候是在搜索到一条死路准备回退时，回退的时候要注意的是，每回退一格，就要将该回退方向的反方向的次数减1（一不注意可能忘记取反方向了）。
        ```java
        //the posibility in this direction increases
        int dir = getLocation().getDirectionToward(next);
        estimate[dir / 90]++;
        ```
        ```java
        //the posibility in this direction decreases
        int dir = getLocation().getDirectionToward(next);
        estimate[((dir + 180) % 360) / 90]--;
        ```
        - 概率的选择。在每步进行决策的时候，根据当前的方向次数数组的值中每个方向的步数，来进行概率计算，这里做的一点优化就是，概率的分母只取有效的方向的总和，效果出奇得好。比如如果此时方向数组的值是 {3，4，5，6}，如果四个方向都能走，则第一个方向的概率都是 3/(3+4+5+6)，如果只有前两个方向能走，则第一个方向的概率是 3/(3+4)，这样就能防止在决策的时候被其他无效方向的概率影响。
        ```java
        //compute the total turns that is valid
        int totalValidTurns = 0;
        for (int i = 0; i < validLocsNum; i++) {
            int dir = getLocation().getDirectionToward(validLocs.get(i));
            totalValidTurns += estimate[dir / 90];
        }
        //get random num among the total valid turns
        int random = (int)(Math.random() * totalValidTurns);
        //to find which dir we get, by using posibility
        int compare = 0;
        for (int i = 0; i < validLocsNum; i++) {
            int dir = getLocation().getDirectionToward(validLocs.get(i));
            compare += estimate[dir / 90];
            if (random < compare) {
                random = i;
                break;
            }
        }
        ```
- N-puzzle
    - bfs
        - 广度优先搜索算法的基本思想。
        - 广度优先搜索算法的基本步骤。
            - 将起始节点放入一个open队列中。
            - 如果open队列为空，则搜索失败，问题无解；否则重复以下步骤：
                1. 访问open队列中的第一个节点v，若v为目标节点，则搜索成功，退出。
                2. 从open队列中删除节点v，放入close集合中。
                3. 将所有与v邻接且未曾被访问的节点放入open队列中
        - **优化**
            - 问题的发现。在运行main测试样例的时候，发现总是要卡个近十秒才出结果。虽然后面结果出来的时候总是显示运行平均时间才50来毫秒，但是一想这似乎不匹配啊，然后仔细看了main函数的测试方法后才发现原来真正耗时的是前面的bfs，显示的平均运行时间仅仅是A*算法的时间。这样一来，转念一想就知道bfs的查询部分是最耗时的了，每步广度优先搜索都有一个要判断是否在当前查询节点是否在队列中的操作，而队列是个线性表，广搜涉及的节点非常之多，所以在一个超长队列里进行查询是非常不妥的。因此用来查询的应该必须是像close那样，是一个集合，集合的查询是非常快的，尤其是哈希集合。我的close一开始就是用的哈希集合，所以自然而然地想到了这一点。
            - 问题的解决。因为集合里的元素是无序的，所以集合没有像队列一样的操作，然而广搜是需要通过队列来实现的，所以一个折中的方案就是，仅仅查询的时候是用集合来查询，算法的正常运作还是得靠队列来维护。因此我新建了一个和open队列保持一样元素的哈希集合，这个集合在队列add的时候同时add，remove的时候同时remove，始终保持一致。集合的作用就仅仅是用来执行查询。虽然听起来有点浪费空间，但这在时间上快得还真不止一点点，和之前的运行时间完全不是一个量级，所以我认为这点空间冗余完全是值得的，改进之后的bfs，几乎都是秒出结果。
    - estimate
        - 估价函数。主要作用是用来估计节点n的重要性，表示为：从起始节点，经过节点n，到达目标节点的代价。代价越小，节点就越优良，在搜索的时候就应该更优先搜索，这就是所谓的启发式搜索。我的估价函数主要是用了三个评估方法，分别是：
            - 后续节点不正确的个数。当前位置的节点后面的节点不是本来应该在其后面的节点。将有这种情况的节点都记个数统计起来，某种意义上当成是一种代价。
            - 曼哈顿距离。曼哈顿距离通俗地说其实就是折线距离。计算当前节点所在位置到其本来应该在的位置的曼哈顿距离，充当一种代价。
            - 欧几里得距离。欧几里得距离是平常所说的直线距离。计算当前节点所在位置到其本来应该在的位置的欧几里得距离，充当一种代价。
        - 权值。既然三种评估方法都各有其代价，而且代价的衡量尺度也无法从某种意义上找到联系。就只能人为地给每种评估方式的代价加上一个权重，代表该种评估方法的重要性，以实现将这三种不相关的估计方式联系起来。权重的参数是需要慢慢调整的，所以真正花时间的是调参。

## 总结
这次实训还是相当充实的，和大一实训相比，本次实训应用性更强，所以也比较有意思，而且没有蛋疼的机器测试，自由度比较大。很喜欢gridworld的架构，可扩展性很强。这次实训最大的收获就是锻炼了强大的快速学习的能力，能够熟练地进行Linux命令行Java编译、引用、运行和Jdb调试（因为不习惯用eclipse），更有Ant、Junit、Sonar等集成编译、运行、测试和代码规范检查的黑科技。还有就是对待Linux的报错的态度也前后发生了360°变化，从一开始的小心翼翼不想碰到任何一个报错，一碰到就gg的心态，变成了尽管来，报错了我乖乖查的心态，可以说是很强大了。