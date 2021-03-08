package two.ref;

import java.util.HashMap;
import java.util.WeakHashMap;

public class WeakHashMapDemo {

    // 如果map中的key不再被外面引用，则map中这个对象也没了
    static WeakHashMap<Integer, String> map;

    public static void main(String[] args) {
        myHashmap();
        System.out.println("===============================");
        myWeakHashmap();
        System.out.println("================================");
        System.gc();
        System.out.println(map);
    }

    private static void myWeakHashmap() {
        map = new WeakHashMap<Integer, String>();
        Integer key = new Integer(1);
        String value = "HashMap";
        map.put(key,value);
        System.out.println(map);
        //key = null;
        System.out.println(map);
        System.gc();
        System.out.println(map+"\t"+map.size());
    }

    private static void myHashmap() {
        HashMap<Integer, String> map = new HashMap<Integer, String>();
        Integer key = new Integer(1);
        String value = "HashMap";
        map.put(key,value);
        System.out.println(map);
        key = null;
        System.out.println(map);
        System.gc();
        System.out.println(map+"\t"+map.size());
    }
}
