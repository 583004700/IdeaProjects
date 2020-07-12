package com.demo.mr.table;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

@Setter
@Getter
//@Accessors(chain = true) 如果加了这个，BeanUtils复制属性方法无效
public class TableBean implements Writable {
    private String id;  //订单id
    private String pid;     //产品id
    private int amount;     //数量
    private String pName;   //产品名称
    private String flag;    //标记是产品表还是订单表

    public TableBean(){}

    public TableBean(String id, String pid, int amount, String pName, String flag) {
        this.id = id;
        this.pid = pid;
        this.amount = amount;
        this.pName = pName;
        this.flag = flag;
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeUTF(id);
        dataOutput.writeUTF(pid);
        dataOutput.writeInt(amount);
        dataOutput.writeUTF(pName);
        dataOutput.writeUTF(flag);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        id = dataInput.readUTF();
        pid = dataInput.readUTF();
        amount = dataInput.readInt();
        pName = dataInput.readUTF();
        flag = dataInput.readUTF();
    }

    @Override
    public String toString() {
        return id + "\t" + amount + "\t" + pName;
    }
}
