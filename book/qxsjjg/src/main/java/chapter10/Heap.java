package chapter10;

/**
 * 堆（实现优先队列）
 */
public class Heap {

    private int[] arr = new int[1024];
    private int length;
    // 为true时表示最小堆
    private boolean min;

    public Heap(boolean min) {
        this.min = min;
    }

    public boolean push(int value) {
        if (this.length < arr.length - 1) {
            arr[length] = value;
            up(length);
            this.length++;
            return true;
        }
        return false;
    }

    public Integer pop() {
        if (this.length > 0) {
            int result = arr[0];
            swap(0, --length);
            down(0);
            return result;
        }
        return null;
    }

    public boolean isEmpty() {
        return this.length <= 0;
    }

    private void up(int i) {
        int parentIndex = 0;
        while ((parentIndex = (i - 1) / 2) >= 0 && i > 0) {
            boolean b = arr[i] < arr[parentIndex];
            if (min && b) {
                swap(i, parentIndex);
            }
            if(!min && !b){
                swap(i, parentIndex);
            }
            i = parentIndex;
        }
    }

    private void down(int i) {
        while (i * 2 + 1 < this.length) {
            int left = i * 2 + 1;
            int right = i * 2 + 2;
            if (min) {
                int minIndex = left;
                if (right < this.length) {
                    if (arr[right] < arr[minIndex]) {
                        minIndex = right;
                    }
                }
                if (arr[i] > arr[minIndex]) {
                    swap(i, minIndex);
                    i = minIndex;
                }else{
                    break;
                }
            } else {
                int maxIndex = left;
                if (right < this.length) {
                    if (arr[right] > arr[maxIndex]) {
                        maxIndex = right;
                    }
                }
                if (arr[i] < arr[maxIndex]) {
                    swap(i, maxIndex);
                    i = maxIndex;
                }else{
                    break;
                }
            }
        }
    }

    private void swap(int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        Heap heap = new Heap(false);
        heap.push(51);
        heap.push(6);
        heap.push(2);
        heap.push(8);
        heap.push(1);
        heap.push(5);
        heap.push(10);
        heap.push(12);
        heap.push(59);
        while (!heap.isEmpty()) {
            System.out.println(heap.pop());
        }
    }
}
