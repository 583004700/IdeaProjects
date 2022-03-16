package chapter3.datastructure;

import java.lang.reflect.Array;

/**
 * 双端队列
 */
public class DoubleQueue<T> {
    private T[] arr;
    private int maxSize;

    private int front;
    private int rear;

    public DoubleQueue(Class<T> c, int maxSize) {
        this.maxSize = maxSize + 1;
        this.arr = (T[]) Array.newInstance(c, this.maxSize);
    }

    public boolean isFull() {
        return (rear + 1) % maxSize == front;
    }

    public boolean isEmpty() {
        return rear == front;
    }

    public boolean pushBack(T ele) {
        if (isFull()) {
            return false;
        }
        arr[rear] = ele;
        rear = (rear + 1) % maxSize;
        return true;
    }

    public T popBack() {
        if (isEmpty()) {
            return null;
        }
        rear = (rear - 1 + this.maxSize) % maxSize;
        T ele = arr[rear];
        return ele;
    }

    public boolean pushFront(T ele) {
        if (isFull()) {
            return false;
        }
        front = (front - 1 + this.maxSize) % maxSize;
        arr[front] = ele;
        return true;
    }

    public T popFront() {
        if (isEmpty()) {
            return null;
        }
        T ele = arr[front];
        front = (front + 1) % maxSize;
        return ele;
    }

    public T getFront() {
        if (isEmpty()) {
            return null;
        }
        T ele = arr[front];
        return ele;
    }

    public T getBack() {
        if (isEmpty()) {
            return null;
        }
        int r = (rear - 1 + this.maxSize) % maxSize;
        T ele = arr[r];
        return ele;
    }

    public int getLength() {
        return (rear - front + maxSize) % maxSize;
    }
}
