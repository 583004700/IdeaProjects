package chapter7;

import java.util.LinkedList;

/**
 * 单源最短路径的算法实现（从某个点到其它各点最短路径）
 * 和 bellman-ford 算法思想非常像，数据结构的表示和松驰次数有点不同
 */
public class BellmanFord {

    public static void main(String[] args) {
        int maxValue = 99999;
        
        int[][] graph = new int[6][6];

        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph[i].length; j++) {
                graph[i][j] = maxValue;
            }
        }

        graph[1][2] = 2;
        graph[1][3] = 5;
        graph[2][3] = 2;
        graph[2][4] = 6;
        graph[3][4] = 7;
        graph[3][5] = 1;
        graph[4][3] = 2;
        graph[4][5] = 4;

        // 求顶点1到其它各顶点最短路径
        int[] dist = new int[]{-1,0,2,5,maxValue,maxValue};
        // 经过的路线
        int[] line = new int[]{-1,1,2,3,-1,-1};
        for (int i = 1; i < graph.length; i++) {
            for (int j = 1; j < graph[i].length; j++) {
                if(dist[i]+graph[i][j] < dist[j]){
                    dist[j] = dist[i]+graph[i][j];
                    line[j] = i;
                }
            }
        }

        for (int i = 0; i < dist.length; i++) {
            System.out.println(dist[i]);
        }
        System.out.println("----------------------------");

        for (int i = 0; i < line.length; i++) {
            System.out.println(line[i]);
        }

        System.out.println("----------------------------");

        // 求1到5最短的路径
        LinkedList<Integer> linkedList = new LinkedList<Integer>();
        int l = 5;
        while(line[l] != l){
            linkedList.addFirst(l);
            l = line[l];
        }
        linkedList.addFirst(l);
        System.out.println(linkedList);
    }

}
