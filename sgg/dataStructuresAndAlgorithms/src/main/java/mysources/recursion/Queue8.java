package mysources.recursion;

/**
 * 八皇后问题
 */
public class Queue8 {

    public static int[][] map = new int[8][8];

    //已经走过的设置为fin
    public static final int fin = 1;

    public static int count = 0;

    public static int checkCount = 0;

    public static void main(String[] args) {
        start(map,0);
        System.out.println("一共有"+count+"种摆法");
        System.out.println(checkCount);
    }

    public static void print(){
        System.out.println("---------------");
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print(map[i][j]+" ");
            }
            System.out.println();
        }
    }

    public static void start(int[][] map,int colIndex){
        if(colIndex == 8){
            count++;
            print();
            return;
        }
        for(int i=0;i<8;i++){
            if(!judge(map,i,colIndex)){
                map[i][colIndex] = fin;
                start(map,colIndex+1);
                map[i][colIndex] = 0;
            }
        }
    }

    /**
     * 判断当前位置和地图已存在的皇后是否冲突
     * @param map
     * @param rowIndex
     * @param colIndex
     * @return
     */
    public static boolean judge(int[][] map,int rowIndex,int colIndex){
        checkCount++;
        for(int i=0;i<8;i++){
            if(map[rowIndex][i] == fin){
                return true;
            }
            if(map[i][colIndex] == fin){
                return true;
            }
            if(rowIndex+i<8 && colIndex+i<8){
                if(map[rowIndex+i][colIndex+i] == fin){
                    return true;
                }
            }
            if(rowIndex+i<8 && colIndex-i<8 && colIndex-i>=0){
                if(map[rowIndex+i][colIndex-i] == fin){
                    return true;
                }
            }
            if(rowIndex-i<8 && rowIndex-i>=0 && colIndex-i<8 && colIndex-i>=0){
                if(map[rowIndex-i][colIndex-i] == fin){
                    return true;
                }
            }
            if(rowIndex-i<8 && rowIndex-i>=0 && colIndex+i<8){
                if(map[rowIndex-i][colIndex+i] == fin){
                    return true;
                }
            }
        }
        return false;
    }
}
