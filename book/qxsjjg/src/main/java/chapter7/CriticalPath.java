package chapter7;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * 关键路径（先拓扑排序）
 */
public class CriticalPath {

    @Setter
    @Getter
    @Accessors(chain = true)
    public static class B {
        private int source;
        private int dist;
        private int weight;
    }

    public static void main(String[] args) {
        List<B> graph = new ArrayList<B>();
        B a0 = new B().setSource(0).setDist(1).setWeight(2);
        B a1 = new B().setSource(0).setDist(2).setWeight(15);
        B a2 = new B().setSource(2).setDist(1).setWeight(4);
        B a3 = new B().setSource(1).setDist(3).setWeight(10);
        B a4 = new B().setSource(1).setDist(4).setWeight(19);
        B a5 = new B().setSource(2).setDist(4).setWeight(11);
        B a6 = new B().setSource(3).setDist(5).setWeight(6);
        B a7 = new B().setSource(4).setDist(5).setWeight(5);
        graph.add(a0);
        graph.add(a1);
        graph.add(a2);
        graph.add(a3);
        graph.add(a4);
        graph.add(a5);
        graph.add(a6);
        graph.add(a7);
        // 每个顶点的入边
        List<B>[] from = new List[6];
        List<B>[] fromFinal = new List[6];
        // 每个顶点的出边
        List<B>[] to = new List[6];
        // 拓扑排序的结果
        List<Integer> topo = new LinkedList<Integer>();
        // 临时保存入边为零的顶点
        Stack<Integer> s = new Stack<Integer>();
        for (int i = 0; i < graph.size(); i++) {
            B b = graph.get(i);
            if (to[b.getSource()] == null) {
                to[b.getSource()] = new ArrayList<B>();
            }
            to[b.getSource()].add(b);
            if (from[b.getDist()] == null) {
                from[b.getDist()] = new ArrayList<B>();
                fromFinal[b.getDist()] = new ArrayList<B>();
            }
            from[b.getDist()].add(b);
            fromFinal[b.getDist()].add(b);
        }
        for (int i = 0; i < from.length; i++) {
            if (from[i] == null || from[i].size() == 0) {
                s.push(i);
            }
        }
        Integer r = null;
        while (!s.isEmpty()) {
            r = s.pop();
            topo.add(r);
            List<B> toList = to[r];
            if (toList != null) {
                for (B b : toList) {
                    if (from[b.getDist()] != null) {
                        from[b.getDist()].remove(b);
                        if (from[b.getDist()].size() == 0) {
                            s.push(b.getDist());
                        }
                    }
                }
            }
        }
        //topo = Arrays.asList(0,2,1,3,4,5);
        System.out.println(topo);
        // 事件最早发生时间
        int[] ve = new int[6];
        for (int i = 0; i < topo.size(); i++) {
            if (fromFinal[topo.get(i)] != null && fromFinal[topo.get(i)].size() > 0) {
                B maxVb = fromFinal[topo.get(i)].get(0);
                int maxValue = ve[maxVb.getSource()] + maxVb.getWeight();
                for (B b : fromFinal[topo.get(i)]) {
                    if(ve[b.getSource()] + b.getWeight()>maxValue) {
                        maxValue = ve[b.getSource()] + b.getWeight();
                    }
                }
                ve[topo.get(i)] = maxValue;
            }
        }
        // 事件最迟发生时间
        int[] vl = new int[6];
        vl[vl.length-1] = ve[ve.length-1];
        for (int i = topo.size() - 1; i >= 0; i--) {
            if(to[topo.get(i)] != null && to[topo.get(i)].size() > 0){
                B minVb = to[topo.get(i)].get(0);
                int minValue = vl[minVb.getDist()] - minVb.getWeight();
                for (B b : to[topo.get(i)]) {
                    if(vl[b.getDist()] - b.getWeight()<minValue) {
                        minValue = vl[b.getDist()] - b.getWeight();
                    }
                }
                vl[topo.get(i)] = minValue;
            }
        }
        for (int i : ve) {
            System.out.println(i);
        }
        System.out.println("------------------");
        for (int i : vl) {
            System.out.println(i);
        }

        // 活动最早开始时间和最迟开始时间
        int[] e = new int[8];
        int[] l = new int[8];
        for (int i = 0; i < graph.size(); i++) {
            B b = graph.get(i);
            e[i] = ve[b.getSource()];
            l[i] = vl[b.getDist()] - b.getWeight();
        }
        System.out.println("----------------------------");
        for (int i = 0; i < e.length; i++) {
            System.out.print(i+":"+"e"+e[i]+"    ");
            System.out.println("l"+l[i]);
        }
    }
}
