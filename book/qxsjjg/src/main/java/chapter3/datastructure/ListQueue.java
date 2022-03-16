package chapter3.datastructure;

import java.lang.reflect.Array;

/**
 * 顺序队列
 * @param <T>
 */
public class ListQueue<T> {

    private T[] q;
    private int front;
    private int rear;
    private int maxSize;

    public ListQueue(Class<T> c, int maxSize) {
        // 因为判断队列是否满了需要空出一个表示，front==rear不能既表示空，又表示满
        this.maxSize = maxSize+1;
        this.q = (T[]) Array.newInstance(c, this.maxSize);
    }

    public boolean enQueue(T ele) {
        // 满了
        if ((rear + 1) % maxSize == front) {
            return false;
        }
        q[rear] = ele;
        this.rear = (rear + 1) % maxSize;
        return true;
    }

    public T deQueue() {
        if (front == rear) {
            return null;
        }
        T ele = q[front];
        front = (front + 1) % maxSize;
        return ele;
    }

    public T getHead(){
        if (front == rear) {
            return null;
        }
        T ele = q[front];
        return ele;
    }

    public int getLength(){
        return rear >= front ? rear - front : (rear + maxSize) - front;
    }
}
