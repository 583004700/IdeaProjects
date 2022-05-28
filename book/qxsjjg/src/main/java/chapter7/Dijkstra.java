package chapter7;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class Dijkstra {
    static int maxValue = 99999;

    public static void main(String[] args) {
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

        int distV = 1;
        // 求顶点1到其它各顶点最短路径
        int[] dist = new int[]{-1, 0, 2, 5, maxValue, maxValue};
        // 经过的路线
        int[] line = new int[]{-1, 1, 2, 3, -1, -1};
        Set<Integer> excludeIndex = new HashSet<Integer>();
        for (int i = 1; i < dist.length; i++) {
            // 每次都找距离最近的点
            Integer minIndex = getMinIndex(dist, excludeIndex);
            if(minIndex != null) {
                excludeIndex.add(minIndex);
                for (int j = 0; j < graph[distV].length; j++) {
                    int v = dist[minIndex] + graph[minIndex][j];
                    if (v < dist[j]) {
                        dist[j] = v;
                        line[j] = minIndex;
                    }
                }
            }
        }

        for (int i : dist) {
            System.out.println(i);
        }
        System.out.println("-------------------");
        for (int i = 0; i < line.length; i++) {
            System.out.println(line[i]);
        }
        System.out.println("--------------------");
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

    private static Integer getMinIndex(int[] arr, Set<Integer> excludeIndex) {
        Integer result = null;
        int min = maxValue + 1;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < min && arr[i] > 0 && !excludeIndex.contains(i)) {
                min = arr[i];
                result = i;
            }
        }
        return result;
    }
}
