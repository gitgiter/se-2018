# SYSU SE-2018 中级实训
---

- 学号：16340255 姓名：谢涛

---

- **本篇自学报告说明**：*出于想记录学习历程和记录一些关键的Linux命令，除了回答要求的Vi,Java,Ant,Junit的自学部分，我会将本次阶段一所遇到的较有价值的问题和对应解决方案记录下来，以及其他题目的完成过程。ta可直接只查看标有检查部分的内容*  

---

- 如果markdown渲染效果不佳，欢迎访问本人github博客---[传送门](https://gitgiter.github.io/2018/04/15/SYSU-SE-2018-Part1/)

---

## 学习Vi/Vim编辑器的使用（检查部分）
Vim是Vi的升级版，Vim基本上兼容所有的Vi指令，然后也扩展了一些新特性。所以这里就主要讲一下自己对Vi的一些尝试。Vi有3个模式：命令模式（command mode）、插入模式（Insert mode）和底行模式（last line mode）。用vi打开文件后，是处于命令行模式，命令行模式一般是用于控制屏幕光标的移动，字符、字或行的删除，复制和切换模式；在命令行模式下按一下字母[i]就可以进入插入模式，切换到插入模式才能够输入文字，插入模式中按[Esc]可以回到命令模式；在命令模式下按一下[:]可以进入底行模式。说一下第一次使用这种”强大的文本编辑器“的感受吧，就是到处碰壁，然后查命令，一不小心就在插入模式下使用了方向键和退格键。  
记录一下我在编写Java的helloworld程序时尝试过的几个基本命令（均在虚拟机上测试过）。 
- vi helloworld.java 在本目录下创建或打开该文件
- 插入模式，输入文本，方向键不能使用，退格只有\b的作用没有删除作用
- 命令模式
    - i进入插入模式，从光标当前位置开始输入文件
    - a进入插入模式，从光标下个位置开始输入文件
    - o进入插入模式，插入新的一行，从行首开始输入文字
    - 删除、复制、更改等结合数字#可以指定操作多少个行或字或字符
    - 移动光标
        - 方向键或hjkl可以上下左右移动
        - 数字0可以移到行开头（Wiki的教程上写的是文章开头，我觉得写错了）
        - gg可以移到文章开头
        - G可以移到文章结尾
        - $、^可以分别移到行尾和行首
        - w移到下个字头
        - e移到下个字尾
        - b移到上个字头
    - 删除
        - x、delete都可以删光标后的一个字符(如果在插入模式下，delete可以返回命令模式并删除后一个字符，x不可以)
        - X可以删前一个字符
        - dd删一行
    - 复制粘贴
        - yw，复制光标到字尾
        - yy，复制光标行
        - p，粘贴到光标处
    - cw，更改光标到字尾，相当于windows里的鼠标刷一段字之后修改
    - u，撤销一次操作，只有一次！！
    - r，R可以替换字符
- 底行模式
    - w，保存
    - q!，退出
    - wq!，保存并退出(Wiki上的教程写成qw)
    - set nu，显示行号
    - set nonu，取消行号显示

## 熟悉JDK的环境并学习JAVA语言，完成HelloWorld的编译运行 
- 环境配置  
首先是在Linux上配置jdk环境，根据实训Wiki上的指示，可以在官网上下载SunJDK，然后解压，手动逐条命令进行安装，最主要的是讲一下环境变量的配置，跟Windows一样，下了JDK都需要配置PATH，JAVA_HOME，CLASSPATH，这三个变量分别指定Java命令的搜索路径，Java的安装目录，Java的执行环境所需要的类或包所在的路径。明确了这三者的意义之后，我们大概就可以明白进行环境变量配置的意义了，结合Wiki上的教程，可以这么理解，配置完Java环境后，是更方便Java的运行，最直观的作用就是可以在任意目录下输入Java或Javac命令，系统都可以识别得到，在没有配置Java环境之前系统是识别不到的。Linux可以通过一下几个变量进行手动的环境变量配置配置完Java环境变量后，在终端输入Java或Javac就能看到系统对该指令的识别了。
    ```  
    export JAVA_HOME=/opt/jdk1.6.0_16
    export PATH=$JAVA_HOME/bin:$PATH
    export CLASSPATH=.$JAVA_HOME/lib/dt.jar: $JAVA_HOME/lib/tools.jar
    ```
    这里要提一下的是，个人认为建议学习这些手动安装的命令是为了理解这些操作的意义和更加了解环境变量配置的意义。在对这个意义有大概地一个了解的情况下，还是建议使用Linux提供的包管理器实现环境的配置为好，可以避免或减少一些错误的出现，比如路径不小心配置错误等。所以我选用了sudo apt-get openjdk-9-jdk的方式。但是出乎意料地还是出现了错误，先是报了不能下载其中某些包的错误，然后系统建议使用sudo apt-get update解决这个问题，然后尝试了一下，但是update失败了，报错是“E: Problem executing scripts APT::Update::Post-Invoke-Success 'if /usr/bin/test -w /var/cache/app-info -a -e /usr/bin/appstreamcli; then appstreamcli refresh > /dev/null; fi'”，后来在网上查到解决方案是依次输入这三条命令（不是很理解为什么，但是确实可以）
    ```
    sudo pkill -KILL appstreamcli
    wget -P /tmp https://launchpad.net/ubuntu/+archive/primary/+files/appstream_0.9.4-1ubuntu1_amd64.deb https://launchpad.net/ubuntu/+archive/primary/+files/libappstream3_0.9.4-1ubuntu1_amd64.deb
    sudo dpkg -i /tmp/appstream_0.9.4-1ubuntu1_amd64.deb /tmp/libappstream3_0.9.4-1ubuntu1_amd64.deb
    ```
    接着update成功之后，再次install就没有出现包无法下载的错误了，但是还有一个“正试图覆盖 /usr/lib/jvm/java-9-openjdk-amd64/include/linux/jawt_md.h，它同时被包含于软件包 openjdk-9-jdk-headless:amd64 9~b114-0ubuntu1”的错误，大概可能是之前安装到一半，有一些东西可能需要覆盖一遍，于是使用sudo apt-get -o Dpkg::Options::="--force-overwrite" install openjdk-9-jdk强制覆盖安装。最后终于在自己的虚拟机上配置Java环境成功。
    检查配置成功的方法
    ```
    java -version
    出现以下反馈则配置成功
    openjdk version "9-internal"
    OpenJDK Runtime Environment (build 9-internal+0-2016-04-14-195246.buildd.src)
    OpenJDK 64-Bit Server VM (build 9-internal+0-2016-04-14-195246.buildd.src, mixed mode)

    javac -version
    出现：javac 9-internal 则配置成功
    ```  
    ps：出于发现手动安装和包管理安装的JDK不一样，查阅资料后，可以简单归纳为：openjdk是jdk的开放原始码版本，基本上只包含最精简的JDK，sun jdk7是在openjdk7的基础上发布的，其大部分原始码都相同，只有少部分原始码被替换掉。
- helloworld的编译运行  
    通过vi/vim编辑器(学习目的)在helloworld.java文件中输入好以下信息并保存后
    ``` java
    public class helloworld
    {
        public static void main(String[] args)
        {
            System.out.println("Hello World!");
        }
    }        
    ```
    在对应目录的终端下面，依次输出编译、运行的命令，就可以在终端输出"Hello World!"了
    ```
    javac helloworld.java
    java helloworld
    ```
## 熟悉Ant的环境并学习Ant，利用Ant实现HelloWorld的自动编译（检查部分）
- 环境配置
    因为配置ant需要先配置好JDK环境，于是前面已经先把Java环境配置好了。接下来ant环境的配置和前面提到的JDK配置的步骤其实差不多。也是可以通过手动安装和包管理器安装两种方式。同样的，手动安装也是需要设置ant的环境变量。
    ```
    export ANT_HOME=/usr/apache-ant-1.9.6
    export PATH=$PATH:$ANT_HOME/bin
    ```
    包管理器安装
    ```
    sudo apt install ant
    ```
    检查配置成功的方法
    ```
    ant -version
    出现：Apache Ant(TM) version 1.9.6 compiled on July 8 2015 则配置成功
    ```
    庆幸的是，ant的环境配置没有遇到什么其他问题。
- 自学ant
    - 简单了解ant  
    Ant是一种基于Java的build工具，类似Linux下c/c++的make，但是比make要好。ant虽然不是最好的，一些成熟的IDE也可以做到这些自动化程序，但是由于ant的轻量级，在许多场合下ant具有独特的优势，尤其是服务器上环境的搭建，不可能在每个服务器都搞一个庞大的IDE，这时候就是ant大显身手的时候。从一台服务器上移植到另一台服务器，有可能只是需要简单修改一下build.xml，然后就可以告诉甚至不懂程序的操作员，"你只要输入ant xxx"，就可以完成一系列部署。  
    那什么时候要用到ant？
    > 当一个代码项目大了以后，每次重新编译，打包，测试等都会变得非常复杂而且重复，因此c语言中有make脚本来帮助这些工作的批量完成。在Java 中应用是平台无关性的，当然不会用平台相关的make脚本来完成这些批处理任务了，ANT本身就是这样一个流程脚本引擎，用于自动化调用程序完成项目的编译，打包，测试等。除了基于JAVA是平台无关的外，脚本的格式是基于XML的，比make脚本来说还要好维护一些
    - 构建文件
        - project元素的属性

        |属性|描述|
        |:---:|:---:|
        |name|表示project的名称（可选）|
        |default|表示构建脚本默认运行的目标，即制定默认的 target。一个project 可以包含多个target（必须）|
        |basedir|表示当该属性没有指定时，使用 Ant 的构件文件的附目录作为基准目录（可选）|  

        - target元素的属性  

        |属性|描述|
        |:---:|:---:|
        |name|表示target的名称（必须）|
        |depends|用于描述 target 之间的依赖关系，若与多个 target 存在依赖关系时，需要以“,”间隔。Ant 会依照 depends 属性中 target 出现的顺序依次执行每个 target。被依赖的 target 会先执行（可选）|
        |decription|关于 target 功能的简单描述（可选）|  
        |if|用于验证指定的属性是否存在，若不存在，所在 target 将不会被执行（可选）|
        |unless|该属性的功能与 if 属性的功能正好相反，它也用于验证指定的属性是否存在，若不存在，所在 target 将会被执行（可选）|

    - 其他元素
        - fileset，用于定制文件集，可以用通配符过滤或选取某些模式的文件
        - property，属性设置，一般在这声明路径变量(使程序更简洁)
        - mkdir，创建文件夹(如果不存在)
        - javac，编译java源程序
        - java，运行java程序
        - classpath，指定java类文件的路径
- helloworld的编译运行
## 学习Java语言，并编写Java小程序，完成要求请参考Java小程序完成要求（检查部分）
- Java自学
    - 几个重要特性
        - 跨平台(可移植)  
        主要原因是：我们编写的Java源码，编译后会生成一种 .class 文件，称为字节码文件。JVM就是负责将字节码文件翻译成特定平台下的机器码然后运行。但是值得注意的是JVM不是跨平台的，JVM翻译出来的机器码也不是跨平台的。因此，跨平台的是Java程序，不是JVM，在不同的平台需要安装对应的JVM。
        - 面向对象  
        Java面向对象的三大特征：封装，继承，多态。封装可以起到很好的信息隐藏作用，隐藏实现细节。继承则可以使子类具有父类的一些基本成员和方法，也可以在父类的基础上扩展出一些新的功能。多态是*允许将父对象设置成为和一个或更多的他的子对象相等的技术*，简单来说就是允许父类指针指向子类，实现方法一般可以通过子类重写父类的一些方法。一开始我对继承和多态有些混淆，感觉差不多。虽然的确挺相似，但是还是有许多区别。最主要的应该是两者的侧重不同，继承主要是为了继承父类的方法而实现代码重用，多态则更多是为了重写父类的接口或方法，多态顾名思义就是”多种形态“，”多种实现方法“，”多种表现形式“
        - 安全性
        Java语言本身有提供很多安全性措施的支持。最主要的一个Java里面没有用指针，而且有内存回收机制，因此可以大大减少内存访问越界以及内存泄露的问题，还有万恶的野指针。另外因为Java支持网络编程，因此网络安全保护机制在Java中也是需要被重视的，Java也有一套独立的网络安全防范机制，尤其是针对有下载任务的时候。
        - 多线程  
        Java多线程可以使得同一程序中有多个线程在同时地并行执行，最直观的作用是可以加快程序运行速度。比如我有一个有1000000个数的数组，要对他进行求和，这时多线程的威力就很好地体现出来。我可以一下开10个线程，每个线程负责把相隔10的数加起来，最后统一一下各线程的计算结果(各线程几乎是同时结束)，就能很快地得出最终结果，相比单线程得出结果的时间，可以接近快10倍。  
        线程的生命周期：新建状态，就绪状态，运行状态，阻塞状态，死亡状态。其中，新建状态执行start()后就你进入就绪状态；就绪状态执行run()方法后等到系统调度，获取到CPU资源之后进入到运行状态；运行状态可以通过sleep或yield让出CPU资源进入阻塞状态；阻塞状态会回到就绪状态，再开始一个循环；运行状态也可以一直运行到程序死亡或者通过stop与destroy函数强制终止进程进入死亡状态；  
        > 在Java语言中，线程是一种特殊的对象，它必须由Thread类或其子（孙）类来创建。通常有两种方法来创建线程：其一，使用型构为Thread(Runnable)的构造子将一个实现了Runnable接口的对象包装成一个线程，其二，从Thread类派生出子类并重写run方法，使用该子类创建的对象即为线程。值得注意的是Thread类已经实现了Runnable接口，因此，任何一个线程均有它的run方法，而run方法中包含了线程所要运行的代码。线程的活动由一组方法来控制。Java语言支持多个线程的同时执行，并提供多线程之间的同步机制（关键字为synchronized）。  
       
        几个重要方法  
        ``` 
            start(): 使该线程开始执行
            sleep(): 在指定的毫秒数内让当前正在执行的线程休眠
            yield(): 暂停当前正在执行的线程对象，并执行其他线程
            isAlive(): 判断线程是否处于活动状态。 
            join(): 等待线程终止。 
            activeCount(): 程序中活跃的线程数。 
            currentThread(): 返回对当前正在执行的线程对象的引用
            wait(): 强迫一个线程等待。 
            notify(): 通知一个线程继续运行。 
            setPriority(): 设置一个线程的优先级。
        ```
        - 分布式(支持网络编程)  
        > Java语言支持Internet应用的开发，在基本的Java应用编程接口中有一个网络应用编程接口（java net），它提供了用于网络应用编程的类库，包括URL、URLConnection、Socket、ServerSocket等。Java的RMI（远程方法激活）机制也是开发分布式应用的重要手段。
    - 基本的语法  
    对于Java的语法，我是觉得跟c和c++很像，尤其是面向对象的c++。感觉基本数据类型也基本上差不多，只不过是有些换了个名字。然后就是一些逻辑函数的语法，像if，for，where等等，用法可以说和c++就没什么太大区别。再有就是类的实现和定义和c++稍有不同，但也只是定义的句式上的不同，本质上还是一样的。
    - 基本的输入输出
        - 输入  
        一般通过Scanner来输入，需要import java.util.Scanner，然后创建一个Scanner的实例，用这个实例来进行输入，Scanner可以分很多类型来读取。以下是我尝试的一些常见类型。
        ``` java
        ...
        Scanner in = new Scanner(System.in);
        num = in.nextInt(); // read a integer
        str = in.nextLine(); // read a line
        str = in.next(); // read a string
        byt = in.nextByte(); // read a byte
        in.close(); // close Scanner
        ...
        ```
        - 输出
        一般通过System.out来输出，和输入一样，输出也可也分很多模式输出，以下是我尝试的一些常用的输出模式
        ``` java
        // print should add a \n to be same as the println
        System.out.println("hello world");
        System.out.print("hello world\n"); 
        // printf can specify the output format like in C
        System.out.printf("%2s %d", ' ', 3);
        ```
    - GUI界面  
        -  大致结构
        Java的GUI界面讲究框架、容器跟组件。通常都是先建立一个框架，把在这个框架上再放几个容器，然后往容器里面塞各种组件，这是目前我对Java GUI的理解。也就是说我要创建一个图形界面类，首先这个类就得继承一个最基本的框架JFrame，在JFrame上添加一些Pannel设定布局，再然后在实例化一些GUI组件添加到容器中。其中Frame是带标题和边框的最顶层窗体；Panel是个最简单的容器类，它提供空间让程序放其它组件，包括其它Panel。添加到容器中的组件放在一个列表中。列表的顺序将定义组件在容器内的正向堆栈顺序。如果将组件添加到容器中时未指定索引，则该索引将被添加到列表尾部（此后它位于堆栈顺序的底部）。
        - 常用事件接口  
        GUI图像界面的交互，最重要的莫过于就是对事件的处理了。
            -  点击事件接口
            继承ActionListener监听接口，并实现其核心函数actionPerformed。其中函数参数ActionEvent可以获得事件发出者的有关信息，比如最重要的事件来源，可以通过getSource()方法获得，进而做一些逻辑判断，以下是计算器小程序中实现actionPerformed的代码
            ``` java
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                if (e.getSource() == buttons[0][0]) {
                    funcOperator("+");	
                }
                else if (e.getSource() == buttons[0][1]) {
                    funcOperator("-");	
                }
                else if (e.getSource() == buttons[0][2]) {
                    funcOperator("*");		
                }
                else if (e.getSource() == buttons[0][3]) {
                    funcOperator("/");		
                }
                else if (e.getSource() == buttons[0][4]) {
                    funcOk();
                }
                else if (e.getSource() == help) {
                    funcHelp();
                }
            }
            ```
            -  键盘事件接口
            继承KeyListener监听接口，KeyListener监听接口有四个必须需要实现的函数，这里仅展示实现keyPressed的实现，其他函数若不实现必须定义一个空实现。其中函数参数KeyEvent可以获得事件发出者的有关信息，事件来源可以通过getKeyCode()这类方法获得，进而做一些逻辑判断，以下是计算器小程序中实现keyPressed的代码
            ``` java
            @Override
            public void keyPressed(KeyEvent e) {
                // TODO Auto-generated method stub
                if(e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_ADD) {
                    funcOperator("+");
                }
                else if(e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_MINUS) {
                    funcOperator("-");		
                }
                else if(e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_MULTIPLY) {
                    funcOperator("*");		
                }
                else if(e.getKeyCode() == KeyEvent.VK_F || e.getKeyCode() == KeyEvent.VK_DIVIDE) {
                    funcOperator("/");		
                }
                else if(e.getKeyCode() == KeyEvent.VK_G || e.getKeyCode() == KeyEvent.VK_EQUALS) {
                    funcOk();
                }
            }
            ```
- 小程序
    - 基本功能如要求，不再赘述
    - 拓展
        - 菜单栏
        - 快捷键
        - 支持键盘输入
## 学习Junit，利用Ant、Junit测试通过HelloWorld（检查部分）
- 自学junit  
JUnit 是一个 Java 编程语言的单元测试框架，被开发者用于实施对应用程序的单元测试，加快程序编制速度，同时提高编码的质量，增加了程序员的产量和程序的稳定性，可以减少程序员的压力和花费在排错上的时间。  
    - JUnit 测试框架具有以下重要特性：
        - 测试工具  
        测试工具是一整套固定的工具用于基线测试。测试工具的目的是为了确保测试能够在共享且固定的环境中运行，因此保证测试结果的可重复性。
            - 在所有测试调用指令发起前的 setUp() 方法。
            - 在测试方法运行后的 tearDown() 方法。
        - 测试套件  
        测试套件意味捆绑几个测试案例并且同时运行。在 JUnit 中，@RunWith 和 @Suite 都被用作运行测试套件。
        - 测试运行器  
        测试运行器 用于执行测试案例。
        - 测试分类  
        测试分类是在编写和测试 JUnit 的重要分类。几种重要的分类如下：
            - 包含一套断言方法的测试断言
            - 包含规定运行多重测试工具的测试用例
            - 包含收集执行测试用例结果的方法的测试结果
    - 基本用法
        - 创建并编写好待测试类及其相关方法
        - 创建测试类
        - 创建用来执行测试类的运行类（可选）
- ant编译运行helloworld
    - 文件结构部署（在同一目录下）
        - 创建src文件夹，将编写好的helloworld.java放入src文件夹
        - 再创建一个build文件夹
        - 创建一个build.xml文件，待写入ant执行task树
    - build.xml编辑        
        - 几个要注意的地方
            - project元素的basedir属性指定基准文件目录，"."为build.xml所在目录   
            - project元素的default指定默认执行的target，如果只想编译而不运行，可以设置成default="compile"(以我的代码为例)
            - property元素中可以定义一些常用的变量，比如路径，这里将src文件夹路径赋给src.dir，build文件夹路径赋给build.dir
            - target名字是自定义的，所以套用模板时记得更改
            - target:clean可以每次ant之前必须把之前历史生成的.class文件清空
            - target:compile进行编译，其中的javac元素要用src属性指定java源文件所在路径，用destdir属性指定.class文件输出的路径
            - target:run运行生成的.class文件，同理其中的java也要指定类文件的路径以及类名
            - target的depends属性用来指定依赖关系，决定各target的执行顺序
        -  模板大致如下面代码代码
    ``` xml
    <?xml version="1.0"?>
    <project name="helloworld" basedir="." default="run">
    <property name="src.dir" value="src"/>
    <property name="build.dir" value="build"/>
    <property name="name" value="helloworld"/>

    <path id="master-classpath">
    <fileset dir="${build.dir}">
        <include name="*.class"/>
    </fileset>
    <pathelement path="${build.dir}"/>
    </path>  

    <target name="clean" description="Clean output directories">
    <delete>
        <fileset dir="${build.dir}">
        <include name="**/*.class"/>
        </fileset>
    </delete>
    </target>

    <target name="compile" depends="clean" description="Compile source java files">
    <mkdir dir="${build.dir}"/>
    <javac destdir="${build.dir}" includeAntRuntime="false">
        <src path="${src.dir}"/>
    </javac>
    </target>

    <target name="run" depends="compile" description="Run java program">
    <java classname="helloworld">
        <classpath refid="master-classpath"/>
    </java>
    </target>
    </project>
    ```
- junit测试通过  
这里我是使用了eclipse进行junit测试，这里简单记录一下操作过程  
    - 首先在eclipse中导入Junit，右击eclipse的项目名(这里是helloworld)->Java Build Path->Add Library->Junit->next->选择Junit4->finish
    - 然后会警告xxx版本不能低于1.5，因此需要打开xxx更改其默认版本，
    - 新建测试类，右击待测试的文件(这里是helloworld.java)->new->Junit Test Case->选择创建setUp方法(后面解释)->next->勾选helloworld中要测试的函数(这里是main)->finish
    - 编写测试类  
        - 遇到的困难：通过代码可见完成测试最核心的函数就是assertEquals(arg1, arg2)，通常arg2是通过调用待测试的函数来获取返回值以比较是否达到期望，但是由于这里测试的是无返回值的main方法，且该main方法是helloworld类的一个静态方法，可以不通过创建实例便能直接用类名调用，造成获得arg2进行比较。
        - 解决方案：
            - 这个方法比较简单，但是通用性不强。可以将helloworld的main方法改成普通函数，且有一个String的返回值，便可以直接调用该方法获得arg2
            - 这个方法看起来比较复杂，但是通用性比较强。就是通过流获取到控制台的输出，将该流转成arg2以进行比较。这类方法可以测试所有有控制台输出的void方法
            - 当然，还有其他测试void函数的方法，因为毕竟一个函数运行了，程序的状态，一些变量的值都会发生改变，可以通过检测这些状态来进行测试，但是由于时间问题这里就没有尝试
        - 代码如下

        ``` java
        package junit_test;

        import static org.junit.Assert.*;
        import junit_test.helloworld;
        import org.junit.*;
        import java.io.*;

        public class helloworldTest {
            
            ByteArrayOutputStream mybyte = null;
            PrintStream console = null;
            
            // something need to be prepared before run test
            @Before
            public void setUp()
            {
                // to get the output on the console by stream
                mybyte = new ByteArrayOutputStream();
                console = System.out;
                System.setOut(new PrintStream(mybyte));
            }
            
            // test case
            @Test
            public void test() {
                // pass an empty argument
                String[] str = new String[1];
                // run static method
                helloworld.main(str);
                
                // compare the expected output and the console output
                assertEquals("Hello World!\n", mybyte.toString());
            }
        }

        ```
    - 运行测试类
        - 我的文件结构
            - junit_test
                - helloworld.java
                - helloworldTest.java
        - 步骤：右击待测试类所在项目名(这里是junit_test)->Run As->JUnit Test，如果没有failure出现则说明通过，如果有failure出现，可以双击failure查看错误对比
## 学习并配置SonarQube，利用SonarQube测试自己昨天写的Java小程序
- 配置SonarQube
    - 获取安装包  
    在自己虚拟机上可以通过sudo获取，但是由于云桌面没有权限所以直接给了安装包，在云桌面/opt/resources目录下，将该目录下的sonar-3.7.4.zip和sonar-runner-dist-2.4.zip移到桌面上(方便起见我是移到桌面，当然也可以是其他目录，但是环境变量设置的时候就要对应相应的目录)，并解压
    - 配置环境变量  
    配置Sonar环境变量的核心就是将这下面几行代码加入系统环境变量集中。至于如何添加，有以下三种方法
        ```
        export SONAR_HOME=/home/Desktop/sonar-3.7.4/bin/linux-x86-32
        export SONAR_RUNNER_HOME=/home/Desktop/sonar-runner-2.4
        export PATH=$SONAR_RUNNER_HOME/bin:$PATH
        ``` 
        
        - 修改/etc/profile文件(推荐)  
        修改方法就类似Java环境配置，但是修改profile文件需要sudo权限，由于云平台没有sudo权限，所以该方法在云平台无效，可以在自己的虚拟机上测试该方法
        - 修改.bashrc文件(个人用户有效)---采用
        这种方法只对个人用户有效，但是已足够。.bashrc文件在~目录下，~等价于/home/username/，由于.bashrc是隐藏文件，所以在该目录下ls是不可见的，la才可以。然后可以通过vi/vim打开该文件往其中追加上面提到的代码。保存并退回shell界面的时候，键入以下命令，可以重新加载当前用户的环境变量源
        ```
        souce ~/.bashrc
        ```
        - 直接在shell修改(只对当前Shell有效)
        这种方法比较局限，只能在当前的shell界面有效，关闭当前shell之后就丢失了。虽然配置很简单，直接在shell中键入上面提到的三行代码即可
- 启动Sonar服务  
    - 进入到sonar.sh所在的文件夹
    ```
    cd $SONAR_HOME
    or
    cd /home/Desktop/sonar-3.7.4/bin/linux-x86-32
    ```
    - 在该目录下执行相关命令
    每次使用完Sonar要记得stop
    ```
    ./sonar.sh start 启动服务
    ./sonar.sh stop 终止服务 
    ./sonar.sh restart 重启服务
    ```
    - 编写sonar-project.properties配置文件  
    配置文件基本按照这个模板，每次要改的一般就是projeckkey，projectName，projectBaseDir。分别前两个是该项目的唯一标识，第三个是源文件所在文件夹的路径（项目文件结构更改的时候要记得修改这个）
    ``` 
    sonar.projectKey = Calculator
    sonar.projectName = Calculator
    sonar.projectVersion = 1.0

    sonar.sourceEncoding = UTF-8
    sonar.modules = java-module

    java-module.sonar.projectName = Java Module
    java-module.sonar.language = java

    java-module.sonar.sources = .
    java-module.sonar.projectBaseDir = Part1/Calculator/src
    ```
    - 运行sonar-runner  
    文件结构和配置文件部署好之后，在配置文件所在目录(该目录必须有sonar-runner相关的包，所以这里我的是Desktop)键入以下命令
    ```
    sonar-runner
    ```
    - 查看项目评测情况   
    打开浏览器访问http://localhost:9000即可，综合评测结果可以通过RCI的值来判断(0~100)越高越好。具体的错误和建议也可以看得到。

## 完成GridWorld的环境配置，学习gridworld.jar的引用，编译运行BugRunner
- 获取安装包  
在自己虚拟机上可以通过sudo获取，但是由于云桌面没有权限所以直接给了安装包，在云桌面/opt/resources目录下，将该目录下的grid-world.zip移到桌面上(方便起见我是移到桌面，当然也可以是其他目录，但是环境变量设置的时候就要对应相应的目录)，并解压
- 运行BugRunner  
找到gridworld.jar所在文件目录，键入以下代码添加引用并编译运行
```
javac -classpath .:gridworld.jar/projects/fisrtProject/BugRunner.java
java -classpath .:gridworld.jar:projects/firsrProject BugRunner
```
- gridworld.jar引用  
jar包就是别人已经写好的一些类，然后将这些类进行打包，你可以将这些jar包引入你的项目中，然后就可以直接使用这些jar包中的类和属性了。所以引用gridworld.jar，简单来讲就是像c/c++里的include，java里面的import也是一个道理，在BugRunner里面import了一下gridworld.jar里面的库，就必须把gridworld.jar引用进来，否则就像c/c++里面用了某些头文件里面的函数却没有包含那个头文件，就会报函数未定义或者引用错误之类的信息。
## My Short Answer on Matrix

---

### Step1 Running the Demo
1. Does the bug always move to a new location? Explain.  
Not always. The bug will only move to a new location when the location in front of it is empty and exists, whether there is a flower in the cell or not.   
2. In which direction does the bug move?  
The bug will move to the cell in front of it, only when the bug can move, so there maybe four directions. However, the bug will just turn 45° clockwise per step when it can't move.
3. What does the bug do if it does not move?  
The bug will just turn 45° clockwise per step when it can't move, until it can move.
4. What does a bug leave behind when it moves?  
The bug will leave a flower behind, and the flower's color is the same as the bug's.
5. What happens when the bug is at an edge of the grid? (Consider whether the bug is facing the edge as well as whether the bug is facing some other direction when answering this question.)  
The bug will just turn 45° clockwise per step when it can't move(facing an edge or facing a rock), and it will continue to turn the direction until it can move.
6. What happens when a bug has a rock in the location immediately in front of it?  
The bug will act like when it facing an edge, turning 45° clockwise per step util it can move.
7. Does a flower move?  
No. Flower can't move.
8. What behavior does a flower have?  
The flower has fading behavior. It starts fading when it's produced, and its color is getting dark. And it cannot stop fading until it's covered by a new flower.
9. Does a rock move or have any other behavior?  
No. The rock can not move, and it doesn't seems like having any other behavior.
10. Can more than one actor (bug, flower, rock) be in the same location in the grid at the same time?  
No. The grid cell can only contain an actor at a time.

---

### Step2 Exploring Actor State and Behavior
1. Test the setDirection method with the following inputs and complete the table, giving the compass direction each input represents.  

    |Degrees|Compass Direction|
    |:---:|:---:|
    |0|North|
    |45|North East|
    |90|East|
    |135|South East|
    |180|South|
    |225|South West|
    |270|West|
    |315|North West|
    |360|North|

2. Move a bug to a different location using the moveTo method. In which directions can you move it? How far can you move it? What happens if you try to move the bug outside the grid?
    - The bug can move to any valid cell in the gridworld, but without changing the direction;
    - The diagonal line is the farthest;
    - The program will throw an IllegalArgumentException exception.
3. Change the color of a bug, a flower, and a rock. Which method did you use?  
The setColor method.
4. Move a rock on top of a bug and then move the rock again. What happened to the bug?  
When a rock move to the location of a bug, the bug will be covered by the rock, and will disappear when the rock moves away.