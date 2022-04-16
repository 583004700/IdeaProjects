package chapter7;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 图、常用表示方式，邻接矩阵和邻接表
 */
public class GraphBFS {
    // 广度优先搜索遍历图(邻接矩阵存储)
    public void BFS1() {
        // 顶点 1，2，3，4，5，6
        int[][] graph = new int[7][7];
        graph[1][2] = 1;
        graph[1][3] = 1;
        graph[2][4] = 1;
        graph[3][2] = 1;
        graph[3][5] = 1;
        graph[4][3] = 1;
        graph[4][6] = 1;
        graph[5][4] = 1;
        graph[5][6] = 1;
        int[] visit = new int[7]; // 保存访问过的顶点
        Queue<Integer> queue = new LinkedList<Integer>();
        // 从顶点1开始访问
        visit[1] = 1;
        queue.offer(1);
        Integer dot = 0;
        while ((dot = queue.poll()) != null) {
            System.out.println(dot);
            for (int i = 1; i < graph[dot].length; i++) {
                if (graph[dot][i] == 1 && visit[i] == 0) {
                    visit[i] = 1;
                    queue.offer(i);
                }
            }
        }
    }

    // 广度优先搜索遍历图(邻接表存储)
    public void BFS2() {
        LinkedList<Integer>[] graph = new LinkedList[7];
        LinkedList<Integer> l1 = new LinkedList<Integer>();
        l1.offer(2);
        l1.offer(3);
        graph[1] = l1;

        LinkedList<Integer> l2 = new LinkedList<Integer>();
        l2.offer(4);
        graph[2] = l2;

        LinkedList<Integer> l3 = new LinkedList<Integer>();
        l3.offer(2);
        l3.offer(5);
        graph[3] = l3;

        LinkedList<Integer> l4 = new LinkedList<Integer>();
        l4.offer(3);
        l4.offer(6);
        graph[4] = l4;

        LinkedList<Integer> l5 = new LinkedList<Integer>();
        l5.offer(4);
        l5.offer(6);
        graph[5] = l5;

        int[] visit = new int[7];
        LinkedList<Integer> n1 = graph[1];
        visit[1] = 0;
        LinkedList<Integer> queue = new LinkedList<Integer>();
        queue.offer(1);
        Integer i = null;
        while((i = queue.poll()) != null){
            if(visit[i] == 0) {
                System.out.println(i);
                visit[i] = 1;
                LinkedList<Integer> is = graph[i];
                if(is != null) {
                    queue.addAll(is);
                }
            }
        }
    }

    public static void main(String[] args) {
        GraphBFS graph = new GraphBFS();
        graph.BFS1();
        graph.BFS2();
    }

}
