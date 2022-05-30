package chapter7;

import java.util.LinkedList;

/**
 * 单源最短路径的算法实现（从某个点到其它各点最短路径）
 * 和 Floyd 算法思想非常像，数据结构的表示和松驰次数有点不同
 */
public class BellmanFord {

    public static void main(String[] args) {
        int maxValue = 99999;

        int[] u = new int[]{2,1,1,4,3};
        int[] v = new int[]{3,2,5,5,4};
        int[] w = new int[]{2,-3,5,2,3};

        // 求顶点1到其它各顶点最短路径
        int[] dist = new int[]{-1,0,maxValue,maxValue,maxValue,maxValue};
        // 经过的路线
        int[] line = new int[]{-1,-1,-1,-1,-1,-1};

        for (int i = 0; i < 5; i++) {
            // 这层循环代表通过某一条边使路径变短，需要外面那层是因为可以通过多条边
            for (int j = 0; j < 5; j++) {
                if(dist[u[j]]+w[j] < dist[v[j]]){
                    dist[v[j]] = dist[u[j]]+w[j];
                    line[v[j]] = u[j];
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
        while(line[l] != -1){
            linkedList.addFirst(l);
            l = line[l];
        }
        linkedList.addFirst(l);
        System.out.println(linkedList);
    }

}
