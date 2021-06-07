package dataStructure.com.atguigu.linkedlist;

public class DoubleLinkedListDemo {
    public static void main(String[] args) {
        HeroNode2 heroNode1 = new HeroNode2(1,"宋江","及时雨");
        HeroNode2 heroNode2 = new HeroNode2(2,"卢俊义","玉麒麟");
        HeroNode2 heroNode3 = new HeroNode2(3,"吴用","智多星");
        HeroNode2 heroNode4 = new HeroNode2(4,"林冲","豹子头");

        DoubleLinkedList doubleLinkedList = new DoubleLinkedList();
        doubleLinkedList.add(heroNode1);
        doubleLinkedList.add(heroNode4);
        doubleLinkedList.add(heroNode2);
        doubleLinkedList.add(heroNode3);

        doubleLinkedList.list();

        HeroNode2 newHeroNode2 = new HeroNode2(2,"小卢","玉麒麟~~");
        doubleLinkedList.update(newHeroNode2);
        System.out.println("修改后的链表情况~~");
        doubleLinkedList.list();

        doubleLinkedList.del(1);
        doubleLinkedList.del(4);
        doubleLinkedList.del(3);
        doubleLinkedList.del(2);
        System.out.println("删除后的链表情况~~");
        doubleLinkedList.list();
    }
}

class DoubleLinkedList {
    //先初始化一个头节点，不存放具体的数据
    private HeroNode2 head = new HeroNode2(0,"","");

    public HeroNode2 getHead(){
        return head;
    }

    /**
     * 添加一个节点的内容
     * @param heroNode
     */
    public void add(HeroNode2 heroNode){
        HeroNode2 temp = head;
        while(true){
            if(temp.next == null){
                break;
            }
            temp = temp.next;
        }
        temp.next = heroNode;
        heroNode.pre = temp;
    }

    //修改节点的信息，根据no编号来修改
    public void update(HeroNode2 newHeroNode2){
        if(head.next == null){
            System.out.println("链表为空~");
            return;
        }
        HeroNode2 temp = head.next;
        boolean flag = false;
        while(true){
            if(temp == null){
                break;
            }
            if(temp.no == newHeroNode2.no){
                flag = true;
                break;
            }
            temp = temp.next;
        }
        if(flag){
            temp.name = newHeroNode2.name;
            temp.nickname = newHeroNode2.nickname;
        }else{
            System.out.printf("没有找到 编号 %d 的节点，不能修改\n",temp.no);
        }
    }

    public void del(int no){
        HeroNode2 temp = head;
        while(temp != null){
            if(temp.no == no){
                temp.pre.next = temp.next;
                if(temp.next != null){
                    temp.next.pre = temp.pre;
                }
                break;
            }
            temp = temp.next;
        }
    }

    /**
     * 遍历双向链表的方法
     */
    public void list(){
        if(head.next == null){
            System.out.println("链表为空");
            return;
        }
        HeroNode2 temp = head.next;
        while(true){
            if(temp == null){
                break;
            }
            System.out.println(temp);
            temp = temp.next;
        }
    }
}

class HeroNode2{
    public int no;
    public String name;
    public String nickname;
    public HeroNode2 next;
    public HeroNode2 pre;

    public HeroNode2(int no,String name,String nickname){
        this.no = no;
        this.name = name;
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "HeroNode2{" +
                "no=" + no +
                ", name='" + name + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
