package mysources.recursion;

public class MiGong {
    public static int[][] redirects = new int[4][2];
    //创建一个二维数组，模拟迷宫
    public static int[][] map = new int[8][7];

    public static int count = 0;

    static{
        //上右下左四个方向

        redirects[0][0] = -1;
        redirects[0][1] = 0;

        redirects[1][0] = 0;
        redirects[1][1] = 1;

        redirects[2][0] = 1;
        redirects[2][1] = 0;

        redirects[3][0] = 0;
        redirects[3][1] = -1;


        //使用-1 表示墙
        //上下全部置为-1， 表示墙
        for(int i=0;i<7;i++){
            map[0][i] = -1;
            map[7][i] = -1;
        }
        for(int i=0;i<8;i++){
            map[i][0] = -1;
            map[i][6] = -1;
        }

        map[3][1] = -1;
        map[3][2] = -1;
    }

    public static void main(String[] args) {
        printMap(map);
        setWay(map,1,1,1);
        System.out.println("共有"+count+"种方式到达目标");
    }

    public static void printMap(int[][] map){
        //输出地图
        System.out.println("地图的情况");
        for(int i=0;i<8;i++){
            for(int j=0;j<7;j++){
                if(map[i][j] > 9 || map[i][j] < 0){
                    System.out.print(map[i][j]+"  ");
                }else{
                    System.out.print(map[i][j]+"   ");
                }
            }
            System.out.println();
        }
    }

    //如果小球能到6，5位置，则说明找到
    //如果map[i][j]为0表示该点没有走过 当为-1表示墙,>0代表已经走过
    /**
     *
     * @param map 表示地图
     * @param i 从哪个位置开始找
     * @param j
     * @return  如果找到通路，则返回true,否则返回false
     */
    public static boolean setWay(int[][] map,int i,int j,int step){

        if(i == 6 && j == 5){
            map[i][j] = step;
            printMap(map);
            count++;
            return true;
        }else if(map[i][j] == 0){
            map[i][j] = step;
        }else{
            return false;
        }


        for(int r=0;r<redirects.length;r++){
            int [] redirect = redirects[r];
            boolean res = setWay(map,i+redirect[0],j+redirect[1],step+1);
            if(res){
                map[i+redirect[0]][j+redirect[1]] = 0;
            }
        }

        return true;
    }
}
