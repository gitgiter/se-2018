package solution;

import jigsaw.Jigsaw;
import jigsaw.JigsawNode;

import java.util.Comparator;
import java.util.Queue;
import java.util.Set;
import java.util.PriorityQueue;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

/**
 * 在此类中填充算法，完成重拼图游戏（N-数码问题）
 */
public class Solution extends Jigsaw {

    private Queue<JigsawNode> openList;  // 用以保存已发现但未访问的节点
    private Set<JigsawNode> closeList;    // 用以保存已发现的节点

    /**
     * 拼图构造函数
     */
    public Solution() {
    }

    /**
     * 拼图构造函数
     * @param bNode - 初始状态节点
     * @param eNode - 目标状态节点
     */
    public Solution(JigsawNode bNode, JigsawNode eNode) {
        super(bNode, eNode);
    }

    /**
     *（实验一）广度优先搜索算法，求指定5*5拼图（24-数码问题）的最优解
     * 填充此函数，可在Solution类中添加其他函数，属性
     * @param bNode - 初始状态节点
     * @param eNode - 目标状态节点
     * @return 搜索成功时为true,失败为false
     */
    public boolean BFSearch(JigsawNode bNode, JigsawNode eNode) {
        // reset all the variables
        // this.openList = new Queue<JigsawNode>();
        // this.closeList = new Set<JigsawNode>();
        this.beginJNode = new JigsawNode(bNode);
        this.endJNode = new JigsawNode(eNode);
        this.currentJNode = null;

        this.closeList = new HashSet<>(1000);
        this.openList = new PriorityQueue<>(500, new Comparator<JigsawNode>() {
            @Override
            public int compare(JigsawNode a, JigsawNode b) {
                if (a.getEstimatedValue() < b.getEstimatedValue()) {
                    return -1;
                } else if (a.getEstimatedValue() > b.getEstimatedValue()) {
                    return 1;
                } else if (a.getNodeDepth() < b.getNodeDepth()) {
                    return -1;
                } else if (a.getNodeDepth() > b.getNodeDepth()) {
                    return 1;
                }
                return 0;
            }
        });
        
        // reset solution flags
        //this.searchedNodesNum = 0;
        //this.solutionPath = null;
        //super.searchedNodesNum = 0;
        super.reset();

        // regards as searching fail,
        // when searchedNodesNum larger than 29000
        final int MAX_NODE_NUM = 29000;
        final int DIRS = 4;
        
        // (1)Add begin node to openList.
        openList.add(this.beginJNode);

        // (2)If openList is empty, fail to start searching
        // else start searching
        while (super.getSearchedNodesNum() < MAX_NODE_NUM && !openList.isEmpty()) {
            // (2-1)Access the first node in openList
            this.currentJNode = openList.peek();
            // if the node is the target node, search end successfully
            if (this.currentJNode.equals(eNode)) {
                this.getPath();
                break;
            }
            
            // (2-2)Remove the first node from openList
            // and add the node into the closeList
            this.openList.remove();
            this.closeList.add(this.currentJNode);
            //this.searchedNodeNum++;
            //super.increaseSearchedNodesNum();
            
            // (2-3)Add all adjancent nodes of current node into openList,
            // which have not been visited yet
            JigsawNode[] adjancentNodes = new JigsawNode[]{
                new JigsawNode(this.currentJNode), new JigsawNode(this.currentJNode),
                new JigsawNode(this.currentJNode), new JigsawNode(this.currentJNode)
            };
            for (int i = 0; i < DIRS; ++i) {
                // if the adjancent nodes can move to directions i
                // and the node is not in openList
                // and the node has not been visited
                if (adjancentNodes[i].move(i) &&
                !this.openList.contains(adjancentNodes[i]) &&
                !this.closeList.contains(adjancentNodes[i])) {
                    // then add this node into openList
                    openList.add(adjancentNodes[i]);
                }
            }
        }
        
        System.out.println("Jigsaw BF Search Result:");
        System.out.println("Begin state:" + this.getBeginJNode().toString());
        System.out.println("End state:" + this.getEndJNode().toString());
        //System.out.println("Solution Path: ");
        //System.out.println(this.getSolutionPath());
        System.out.println("Total number of searched nodes:" + this.getSearchedNodesNum());
        System.out.println("Depth of the current node is:" + this.getCurrentJNode().getNodeDepth());
        return this.isCompleted();
    }


    /**
     *（Demo+实验二）计算并修改状态节点jNode的代价估计值:f(n)
     * 如 f(n) = s(n). s(n)代表后续节点不正确的数码个数
     * 此函数会改变该节点的estimatedValue属性值
     * 修改此函数，可在Solution类中添加其他函数，属性
     * @param jNode - 要计算代价估计值的节点
     */
    public void estimateValue(JigsawNode jNode) {               
        int dimension = JigsawNode.getDimension();

        int count = 0; //后续节点不正确的数码个数 
        for (int index = 1; index < dimension * dimension; index++) {
            if (jNode.getNodesState()[index] + 1 != jNode.getNodesState()[index + 1]) {
                count++;
            }
        }

        int ManHattan = 0; //ManHattan distance
        int Euclid = 0; //Euclidean distance

        // Compute Manhattan distance and 
        // the square of eculidean distance of all nodes
        for (int i = 1; i <= dimension * dimension; ++i) {
            // the node cannot be empty
            if (jNode.getNodesState()[i] != 0) {
                for (int j = 1; j <= dimension * dimension; ++j) {
                    if (jNode.getNodesState()[i] == this.endJNode.getNodesState()[j]) {
                        int startX = (i - 1) / dimension;
                        int startY = (i - 1) % dimension;
                        int endX = (j - 1) / dimension;
                        int endY = (j - 1) % dimension;
                        ManHattan += Math.abs(startX - endX) + Math.abs(startY - endY);
                        Euclid += Math.pow(startX - endX, 2) + Math.pow(startY - endY, 2);
                        break;
                    }
                }
            }
        }

        // Set weight of the cost
        jNode.setEstimatedValue(7 * count + 6 * ManHattan + 3 * Euclid);
    }
}
