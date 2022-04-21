package chapter7;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 最小生成树的一种算法实现
 */
public class Kruskal {
    @Setter
    @Getter
    public static class B{
        private int source;
        private int dist;
        private int weight;
    }

    public static void main(String[] args) {
        int maxValue = 999999;
        int graph[][] = new int[8][8];
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph[i].length; j++) {
                graph[i][j] = maxValue;
            }
        }
        graph[1][2] = 23;
        graph[2][1] = 23;
        graph[1][7] = 36;
        graph[7][1] = 36;
        graph[1][6] = 28;
        graph[6][1] = 28;
        graph[2][3] = 20;
        graph[3][2] = 20;
        graph[2][7] = 1;
        graph[7][2] = 1;
        graph[3][7] = 4;
        graph[7][3] = 4;
        graph[3][4] = 15;
        graph[4][3] = 15;
        graph[4][7] = 9;
        graph[7][4] = 9;
        graph[4][5] = 3;
        graph[5][4] = 3;
        graph[5][7] = 16;
        graph[7][5] = 16;
        graph[5][6] = 17;
        graph[6][5] = 17;
        graph[6][7] = 25;
        graph[7][6] = 25;

        List<B> bs = new ArrayList<B>();
        int[] q = new int[]{0,1,2,3,4,5,6,7,8}; // 存每个顶点所属的集合的上一个
        for (int i = 1; i < graph.length; i++) {
            for (int j = i; j < graph[i].length; j++) {
                if(graph[i][j] < maxValue){
                    B b = new B();
                    b.setSource(i);
                    b.setDist(j);
                    b.setWeight(graph[i][j]);
                    bs.add(b);
                }
            }
        }
        Collections.sort(bs,(Comparator.comparingInt(B::getWeight)));
        List<B> selected = new ArrayList<B>();
        for (int i = 0; i < bs.size(); i++) {
            B b = bs.get(i);

            // 判断两个顶点是否是同一个集合（并查集）

            int s = q[b.getSource()];
            while(s != q[s]){
                s = q[s];
            }
            int d = q[b.getDist()];
            while(d != q[d]){
                d = q[d];
            }
            if(s != d) {
                // 将集合连通
                if (s < d) {
                    q[d] = s;
                } else {
                    q[s] = d;
                }
                selected.add(b);
            }
        }
        for (int i = 0; i < selected.size(); i++) {
            System.out.println(selected.get(i).getSource()+"-"+selected.get(i).getDist()+"-"+ selected.get(i).getWeight());
        }
    }
}
