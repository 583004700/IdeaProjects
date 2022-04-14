package chapter5.datastructure;

/**
 * 对称矩阵(沿对角线对称)
 */
public class SymmetryArray {
    public static void main(String[] args) {
        int[][] arr = new int[4][4];
        arr[0][0] = 1;
        arr[0][1] = 2;
        arr[0][2] = 3;
        arr[0][3] = 4;

        arr[1][0] = 2;
        arr[1][1] = 5;
        arr[1][2] = 6;
        arr[1][3] = 7;

        arr[2][0] = 3;
        arr[2][1] = 6;
        arr[2][2] = 8;
        arr[2][3] = 9;

        arr[3][0] = 4;
        arr[3][1] = 7;
        arr[3][2] = 9;
        arr[3][3] = 6;

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println("-----------------------");

        int[] arr2 = new int[10];
        arr2[0] = 1;
        arr2[1] = 2;
        arr2[2] = 5;
        arr2[3] = 3;
        arr2[4] = 6;
        arr2[5] = 8;
        arr2[6] = 4;
        arr2[7] = 7;
        arr2[8] = 9;
        arr2[9] = 6;

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                System.out.print(getElement(arr2,i,j) + " ");
            }
            System.out.println();
        }
    }

    /**
     *
     * @param arr 对称矩阵的数组存储
     * @param i 行
     * @param j 列
     * @return 返回元素值
     */
    public static int getElement(int[] arr,int i,int j){
        int index = 0;
        if (i >= j) {
            index = (i + 1) * i / 2 + j;
        } else {
            index = (j + 1) * j / 2 + i;
        }
        return arr[index];
    }
}
