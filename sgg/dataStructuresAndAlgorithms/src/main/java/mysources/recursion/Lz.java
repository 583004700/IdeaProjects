package mysources.recursion;

import java.util.ArrayList;
import java.util.List;

/**
 * 五个球放三个篮子里面，有多少种方式
 */
public class Lz {
    public static List<LzC> lzs = new ArrayList<LzC>();

    static{
        lzs.add(new LzC(0));
        lzs.add(new LzC(1));
        lzs.add(new LzC(2));
        lzs.add(new LzC(3));
    }

    public static void main(String[] args) {
        f(lzs.get(0));
    }

    public static void f(LzC lz){
        if(lz.no == 3){
            return;
        }
        for(int i=0;i<=5;i++){
            lz.fruit = i;
            if(lzs.get(0).fruit+lzs.get(1).fruit+lzs.get(2).fruit == 5){
                System.out.println(lzs.get(0).fruit+"\t"+lzs.get(1).fruit+"\t"+lzs.get(2).fruit);
            }
            f(lzs.get(lz.no+1));
        }
    }
}

class LzC{
    public int fruit;
    public int no;

    public LzC(int no) {
        this.no = no;
    }
}
