package mysources;

import java.util.*;

public class Dao {
    public static void main(String[] args) throws Exception{
        Set<CupList> set = new HashSet<CupList>();
        Queue<CupList> queue = new LinkedList<CupList>();

        CupList cups = new CupList();
        cups.add(new Cup(16,16));
        cups.add(new Cup(9,0));
        cups.add(new Cup(7,0));

        set.add(cups);
        queue.offer(cups);


        while(queue.size() > 0){
            CupList cups1 = queue.remove();
            if(cups1.success()){
                System.out.println("成功");
                while(cups1.getPre() != null){
                    System.out.println(cups1);
                    cups1 = cups1.getPre();
                }
                break;
            }

            CupList cups2 = (CupList)cups1.clone();
            cups2.setFrom(0);
            cups2.setTo(1);
            while(cups2.change()){
                CupList cupl = (CupList) cups2.clone();
                cupl.setPre(cups1);
                if(set.add(cupl)){
                    queue.offer(cupl);
                }
                CupList temp = cups2;
                cups2 = (CupList)cups1.clone();
                cups2.setFrom(temp.getFrom());
                cups2.setTo(temp.getTo());
            }
        }
    }
}


class Cup{
    public Cup(int size, int ha) {
        this.size = size;
        this.ha = ha;
    }

    //能装多少水
    private int size;
    //已经装了多少水
    private int ha;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getHa() {
        return ha;
    }

    public void setHa(int ha) {
        this.ha = ha;
    }
    /**
     * 将倒水到进另一个杯子
     * @param other
     */
    public void ds(Cup other){
        int z = other.size - other.ha;
        if(z > this.ha){
            z = this.ha;
        }
        this.ha = this.ha - z;
        other.ha = other.ha + z;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return new Cup(this.size,this.ha);
    }

    @Override
    public String toString() {
        return "Cup{" +
                "size=" + size +
                ", ha=" + ha +
                '}';
    }
}

class CupList extends ArrayList<Cup> {
    //上一个节点，用来知道过程
    private CupList pre;
    private int from = 0;
    private int to = 1;

    @Override
    public boolean equals(Object o) {
        return this.get(0).getHa() == ((CupList)o).get(0).getHa() && this.get(1).getHa() == ((CupList)o).get(1).getHa() && this.get(2).getHa() == ((CupList)o).get(2).getHa();
    }

    @Override
    public int hashCode() {
        return this.get(0).getHa() * this.get(1).getHa() * this.get(2).getHa();
    }

    @Override
    public Object clone() {
        CupList cupsC = null;
        try {
            cupsC = new CupList();
            cupsC.add((Cup) this.get(0).clone());
            cupsC.add((Cup) this.get(1).clone());
            cupsC.add((Cup) this.get(2).clone());
            cupsC.from = this.from;
            cupsC.to = this.to;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cupsC;
    }

    public boolean change(){
        if(from == -1 && to == -1){
            return false;
        }

        this.get(from).ds(this.get(to));
        if(from == 0 && to == 1){
            from = 0;
            to = 2;
            return true;
        }
        if(from == 0 && to == 2){
            from = 1;
            to = 0;
            return true;
        }
        if(from == 1 && to == 0){
            from = 1;
            to = 2;
            return true;
        }
        if(from == 1 && to == 2){
            from = 2;
            to = 0;
            return true;
        }
        if(from == 2 && to ==0){
            from =2;
            to = 1;
            return true;
        }
        if(from == 2 && to == 1){
            from = -1;
            to = -1;
            return true;
        }
        return false;
    }

    public boolean success(){
        return this.get(0).getHa() == 8 && this.get(1).getHa() == 8;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public CupList getPre() {
        return pre;
    }

    public void setPre(CupList pre) {
        this.pre = pre;
    }
}
