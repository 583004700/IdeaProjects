package chapter7;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * 拓扑排序(解决是否存在循环依赖)
 */
public class Topological {
    public static void main(String[] args) {
        LinkedList<Integer>[] graph = new LinkedList[6];
        List<Integer> topo = new ArrayList<Integer>();
        int[] come = new int[6];
        Stack<Integer> s = new Stack<Integer>();
        LinkedList<Integer> c0 = new LinkedList<Integer>();
        c0.add(3);
        c0.add(2);
        c0.add(1);
        graph[0] = c0;

        LinkedList<Integer> c2 = new LinkedList<Integer>();
        c2.add(4);
        c2.add(1);
        graph[2] = c2;

        LinkedList<Integer> c3 = new LinkedList<Integer>();
        c3.add(4);
        graph[3] = c3;

        LinkedList<Integer> c5 = new LinkedList<Integer>();
        c5.add(4);
        c5.add(3);
        graph[5] = c5;

        for (int i = 0; i < graph.length; i++) {
            LinkedList<Integer> l = graph[i];
            if(l != null){
                for (Integer dot : l) {
                    come[dot] = come[dot] + 1;
                }
            }
        }

        for (int i = 0; i < come.length; i++) {
            if(come[i] == 0){
                s.push(i);
            }
        }

        Integer dot = null;
        while(!s.isEmpty()){
            dot = s.pop();
            topo.add(dot);
            LinkedList<Integer> linkedList = graph[dot];
            if(linkedList != null) {
                for (Integer integer : linkedList) {
                    come[integer] = come[integer] - 1;
                    if (come[integer] == 0) {
                        s.push(integer);
                    }
                }
            }
        }
        System.out.println(topo);
    }
}
