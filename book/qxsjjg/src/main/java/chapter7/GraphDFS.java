package chapter7;

public class GraphDFS {
    // 顶点 1，2，3，4，5，6
    int[][] graph = new int[7][7];
    int visit[] = new int[7];
    {
        graph[1][2] = 1;
        graph[1][3] = 1;
        graph[2][4] = 1;
        graph[3][2] = 1;
        graph[3][5] = 1;
        graph[4][3] = 1;
        graph[4][6] = 1;
        graph[5][4] = 1;
        graph[5][6] = 1;
    }

    public void DFS1(int dot) {
        if(visit[dot] == 0) {
            System.out.println(dot);
            visit[dot] = 1;
            for (int i = graph[dot].length-1; i > 0; i--) {
                if (graph[dot][i] == 1) {
                    DFS1(i);
                }
            }
        }
    }

    public static void main(String[] args) {
        GraphDFS graph = new GraphDFS();
        graph.DFS1(1);
    }

}
