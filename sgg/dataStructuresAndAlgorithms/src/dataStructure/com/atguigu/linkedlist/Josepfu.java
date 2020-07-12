package dataStructure.com.atguigu.linkedlist;

public class Josepfu {
    public static void main(String[] args) {
        CircleSingleLinkedList singleLinkedList = new CircleSingleLinkedList();
        singleLinkedList.addBoy(5);
        singleLinkedList.showBoy();

        singleLinkedList.countBoy(1,2,5);
    }
}

class CircleSingleLinkedList{
    private Boy first = new Boy(-1);

    public void addBoy(int nums){
        if(nums < 1){
            System.out.println("nums的值不正确");
            return;
        }
        Boy curBoy = null;
        for(int i = 1; i <= nums; i++){
            Boy boy = new Boy(i);
            if(i == 1){
                first = boy;
                first.setNext(first);
                curBoy = first;
            }else{
                curBoy.setNext(boy);
                boy.setNext(first);
                curBoy = boy;
            }
        }
    }

    public void showBoy(){
        if(first == null){
            System.out.println("没有任务小孩");
            return;
        }
        Boy curBoy = first;
        while(true){
            System.out.printf("小孩的编号 %d\n",curBoy.getNo());
            if(curBoy.getNext() == first){
                break;
            }
            curBoy = curBoy.getNext();
        }
    }

    public void countBoy(int startNo, int countNum, int nums){
        if(first == null || startNo < 1 || startNo > nums){
            System.out.println("参数输入有误，请重新输入");
            return;
        }
        Boy helper = first;
        while(true){
            if(helper.getNext() == first){
                break;
            }
            helper = helper.getNext();
        }
        for(int j=0;j<startNo-1;j++){
            first = first.getNext();
            helper = helper.getNext();
        }
        while(true){
            if(helper == first){
                break;
            }
            for(int j=0;j<countNum-1;j++){
                first = first.getNext();
                helper = helper.getNext();
            }
            System.out.printf("小孩%d出圈\n",first.getNo());
            first = first.getNext();
            helper.setNext(first);
        }
        System.out.printf("最后留在圈中小孩编号%d \n",first.getNo());
    }
}

class Boy{
    private int no;
    private Boy next;

    public Boy(int no) {
        this.no = no;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public Boy getNext() {
        return next;
    }

    public void setNext(Boy next) {
        this.next = next;
    }
}
