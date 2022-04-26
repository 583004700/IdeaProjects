package question.triangle;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class Triangle {

    @Setter
    @Getter
    public static class MyList<T> extends ArrayList<Integer> implements Comparable {
        private int cz;

        @Override
        public int compareTo(Object o) {
            MyList<Integer> oo = (MyList) o;
            return this.cz - oo.getCz();
        }

        @Override
        public String toString() {
            return super.toString() + ":" + this.getCz();
        }
    }

    static List<MyList<Integer>> list = new ArrayList();
    static int[] arr = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21};

    public static void main(String[] args) {
        Long startTime = System.currentTimeMillis();
        /*int[] arr = new int[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};*/
        for (int i = 0; i < arr.length; i++) {
            for (int j = i; j < arr.length; j++) {
                for (int k = j; k < arr.length; k++) {
                    if (arr[i] != arr[j] && arr[i] != arr[k] && arr[j] != arr[k] && (arr[i] + arr[j] + arr[k]) % 3 == 0) {
                        if ((arr[k] - arr[j] - 1) % 3 != 0 || (arr[j] - arr[i] - 1) % 3 != 0) {
                            continue;
                        }
                        MyList<Integer> san = new MyList<Integer>();
                        san.add(arr[i]);
                        san.add(arr[j]);
                        san.add(arr[k]);
                        //san.setCz(Math.abs(arr[j] - arr[i] - 7));
                        list.add(san);
                    }
                }
            }
        }
        //Collections.sort(list);
        System.out.println(list);
        System.out.println("list长度：" + list.size());
        for (int i = 0; i < list.size(); i++) {
            xz(0, i, arr.length / 3);
        }
        Long endTime = System.currentTimeMillis();
        for (Object o : resultFinally) {
            System.out.println(o);
        }
        System.out.println("数据条数为：" + resultFinally.size());
        System.out.println("执行时间为：" + (endTime - startTime));
    }

    // 已经被选择的下标
    static Set<Integer> selectedIndex = new HashSet<Integer>();

    public static boolean bXj(B b1, B b2) {
        boolean l = b1.getBMin() < b2.getBMin() && b2.getBMin() < b1.getBMax();
        boolean k = b1.getBMin() < b2.getBMax() && b2.getBMax() < b1.getBMax();
        if (l) {
            if (k) {
                return false;
            }
        }
        if (!l) {
            if (!k) {
                return false;
            }
        }
        return true;
    }

    @Setter
    @Getter
    @Accessors(chain = true)
    public static class B {
        private int bMin;
        private int bMax;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            B b = (B) o;
            return bMin == b.bMin && bMax == b.bMax;
        }

        @Override
        public int hashCode() {
            return Objects.hash(bMin, bMax);
        }
    }

    static Map<String, Boolean> cache = new HashMap();

    public static boolean canSelect(int index) {
        boolean result = true;
        MyList<Integer> integers = list.get(index);
        w:
        for (Integer selectIndex : selectedIndex) {
            List<Integer> selectList = list.get(selectIndex);
            int min = Math.min(selectIndex, index);
            int max = Math.max(selectIndex, index);
            String key = min + "," + max;
            if (cache.containsKey(key) && !cache.get(key)) {
                return false;
            }
            if (cache.containsKey(key) && cache.get(key)) {
                continue;
            }
            for (int i1 = 0; i1 < selectList.size(); i1++) {
                int a = selectList.get(i1);
                for (int i2 = 0; i2 < integers.size(); i2++) {
                    int b = integers.get(i2);
                    if (a == b) {
                        result = false;
                        cache.put(key, false);
                        break w;
                    }
                }
            }
        }
        if (result) {
            w:
            for (Integer select : selectedIndex) {
                List<Integer> xz = list.get(select);
                List<Integer> addA = list.get(index);
                int min = Math.min(select, index);
                int max = Math.max(select, index);
                String key = min + "," + max;
                if (cache.containsKey(key) && cache.get(key)) {
                    continue;
                }
                if (cache.containsKey(key) && !cache.get(key)) {
                    return false;
                }
                for (int i = 0; i < 3; i++) {
                    for (int j = i + 1; j < 3; j++) {
                        B b = new B().setBMin(xz.get(i)).setBMax(xz.get(j));
                        for (int k = 0; k < 3; k++) {
                            for (int l = k + 1; l < 3; l++) {
                                B bl = new B().setBMin(addA.get(k)).setBMax(addA.get(l));
                                if (bXj(b, bl)) {
                                    result = false;
                                    cache.put(key, false);
                                    break w;
                                }
                            }
                        }
                    }
                }
                cache.put(key, true);
            }
        }
        return result;
    }

    public static Map<Integer, Integer> levelIndex = new HashMap<Integer, Integer>();

    static List resultFinally = new ArrayList();
    static Set<String> resultFinallySetKey = new HashSet<String>();

    public static void xz(int count, int current, int groupCount) {
        if (groupCount <= 0) {
            return;
        }
        selectedIndex.add(current);
        levelIndex.put(count, current);
        for (int i = 0; i < list.size(); i++) {
            if (canSelect(i)) {
                if (count == arr.length / 3 - 1 - 1) {
                    levelIndex.put(count + 1, i);
                    List l = new ArrayList();
                    String key = "";
                    List<Integer> s = new ArrayList<Integer>();
                    for (int j = 0; j <= count + 1; j++) {
                        l.add(list.get(levelIndex.get(j)));
                        s.add(levelIndex.get(j));
                    }
                    Collections.sort(s);
                    key = s.toString();
                    if (!resultFinallySetKey.contains(key)) {
                        resultFinally.add(l);
                        resultFinallySetKey.add(key);
                    }
                } else {
                    xz(count + 1, i, groupCount - 1);
                }
            }
        }
        selectedIndex.remove(current);
        levelIndex.remove(count);
    }
}
