package chapter7;

/**
 * 最小生成树的一种算法实现（权值和最小）
 */
public class Prim {
    public static void main(String[] args) {
        int maxValue = 99999;
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

        // 已经选好的节点
        int[] selected = new int[8];
        selected[1] = 1;
        // 每个顶点距选好的节点集合（生成树）的最近距离
        int[] lowcost = new int[]{maxValue,0,23,maxValue,maxValue,maxValue,28,36};
        // 每个顶点距选好的节点的最近顶点,初始化时选好的节点只有1，所以都为1
        int[] closest = new int[]{-1,1,1,1,1,1,1,1};

        //从没有选中的里面找到最小的下标

        while(true) {
            int minVal = maxValue;
            int minIndex = 0;
            for (int i = 1; i < lowcost.length; i++) {
                if (selected[i] == 0) {
                    if (lowcost[i] < minVal) {
                        minVal = lowcost[i];
                        minIndex = i;
                    }
                }
            }
            if(minIndex == 0){
                break;
            }
            selected[minIndex] = 1;
            for (int i = 0; i < lowcost.length; i++) {
                if (selected[i] == 0) {
                    if (graph[minIndex][i] < lowcost[i]) {
                        lowcost[i] = graph[minIndex][i];
                        closest[i] = minIndex;
                    }
                }
            }
        }

        for (int i = 0; i < lowcost.length; i++) {
            System.out.println(lowcost[i]);
        }
        System.out.println("----------------------");
        for (int i = 0; i < closest.length; i++) {
            System.out.println(i+"-----"+closest[i]);
        }
    }
}
