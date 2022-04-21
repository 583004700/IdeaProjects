package chapter7;

import java.util.LinkedList;

/**
 *  各顶点之间最短路径的实现
 */
public class Floyd {
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

        for (int i = 1; i < graph.length; i++) {
            for (int j = 1; j < graph[i].length; j++) {
                for (int k = 1; k < graph.length; k++) {
                    if(graph[k][i]+graph[i][j] < graph[k][j]){
                        graph[k][j] = graph[k][i]+graph[i][j];
                    }
                }
            }
        }

        for (int i = 1; i < graph.length; i++) {
            for (int j = 0; j < graph[i].length; j++) {
                System.out.print(graph[i][j]+",");
            }
            System.out.println();
        }
    }
}
