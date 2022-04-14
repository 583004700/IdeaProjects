package chapter5.datastructure;

/**
 * 对角矩阵(以对角线为中心向两边辐射)，按对角线存储,和书上写法不同，可以更省空间
 */
public class OppositeAnglesArray {
    public static void main(String[] args) {
        int[][] arr = new int[6][6];
        arr[0][0] = 1;
        arr[0][1] = 3;
        arr[0][2] = 2;
        arr[0][3] = 0;
        arr[0][4] = 0;
        arr[0][5] = 0;

        arr[1][0] = 2;
        arr[1][1] = 4;
        arr[1][2] = 5;
        arr[1][3] = 7;
        arr[1][4] = 0;
        arr[1][5] = 0;

        arr[2][0] = 7;
        arr[2][1] = 9;
        arr[2][2] = 2;
        arr[2][3] = 6;
        arr[2][4] = 8;
        arr[2][5] = 0;

        arr[3][0] = 0;
        arr[3][1] = 2;
        arr[3][2] = 7;
        arr[3][3] = 3;
        arr[3][4] = 2;
        arr[3][5] = 5;

        arr[4][0] = 0;
        arr[4][1] = 0;
        arr[4][2] = 3;
        arr[4][3] = 9;
        arr[4][4] = 6;
        arr[4][5] = 4;

        arr[5][0] = 0;
        arr[5][1] = 0;
        arr[5][2] = 0;
        arr[5][3] = 4;
        arr[5][4] = 5;
        arr[5][5] = 1;

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println("-----------------------");

        int[] arr2 = new int[24];
        arr2[0] = 2;
        arr2[1] = 7;
        arr2[2] = 8;
        arr2[3] = 5;

        arr2[4] = 3;
        arr2[5] = 5;
        arr2[6] = 6;
        arr2[7] = 2;
        arr2[8] = 4;

        arr2[9] = 1;
        arr2[10] = 4;
        arr2[11] = 2;
        arr2[12] = 3;
        arr2[13] = 6;
        arr2[14] = 1;

        arr2[15] = 2;
        arr2[16] = 9;
        arr2[17] = 7;
        arr2[18] = 9;
        arr2[19] = 5;

        arr2[20] = 7;
        arr2[21] = 2;
        arr2[22] = 3;
        arr2[23] = 4;

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                System.out.print(getElement(arr2, i, j, 3, 6) + " ");
            }
            System.out.println();
        }
    }

    /**
     * 按对角线存储取值
     *
     * @param arr 对角矩阵的数组存储
     * @param i   行
     * @param j   列
     * @param d   第一行有效的数字个数
     * @param n   矩阵的高
     * @return 返回元素值
     */
    public static int getElement(int[] arr, int i, int j, int d, int n) {
        try {
            int newRowIndex = i - j;
            newRowIndex = newRowIndex + d - 1;
            int[] newIndexSub = getNewIndexSub(n, d);
            int sub = 0;
            if (newRowIndex > 0) {
                sub = newIndexSub[newRowIndex - 1];
            }
            int col = Math.min(j, i);
            int index = (newRowIndex * n) - sub + col;
            return arr[index];
        }catch (Exception e){

        }
        return 0;
    }

    public static int[] getNewIndexSub(int n, int d) {
        int resultLength = 1 + (d - 1) * 2;
        int[] result = new int[resultLength];
        int middleIndex = resultLength / 2;
        int count = 0;
        for (int i = 0; i < resultLength; i++) {
            count = Math.abs(i - middleIndex);
            int add = 0;
            if (i >= 1) {
                add = result[i - 1];
            }
            result[i] = count + add;
        }
        return result;
    }
}
