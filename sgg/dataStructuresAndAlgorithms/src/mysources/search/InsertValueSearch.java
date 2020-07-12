package mysources.search;

public class InsertValueSearch {
    public static int arr[] = { 1, 3, 4, 5, 6, 7, 8, 9, 10 , 11, 12, 13,14,15,16,17,18,19,25 };

    public static void main(String[] args) {
        System.out.println(insertValueSearch(arr,21));
    }

    public static int insertValueSearch(int[] arr,int val){
        int left = 0;
        int right = arr.length - 1;
        int index = 0;
        do{
            if(left>right){
                return -1;
            }else if(left == right && arr[left] != val){
                return -1;
            }else if(left == right && arr[left] == val){
                return left;
            }
            index = left + (right - left) * (val - arr[left]) / (arr[right] - arr[left]);
            if(arr[index] < val) {
                left = index + 1;
            }
            if(arr[index] > val) {
                right = index - 1;
            }
        }while(arr[index] != val);
        return index;
    }
}
