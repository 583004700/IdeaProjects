package com.demo.hbase.test;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

/**
 * DDL:
 * 1。判断表是否存在
 * 2.创建表
 * 3.创建命名空间
 * 4.删除表
 * DML:
 * 5.插入数据
 * 6.查数据（get）
 * 7.查数据（scan）
 * 8.删除数据
 */
public class TestAPI {
    private static Configuration configuration;
    private static Connection connection;
    private static Admin admin;

    static {
        try {
            //HBaseConfiguration configuration = new HBaseConfiguration();
            configuration = HBaseConfiguration.create();
            configuration.set("hbase.zookeeper.quorum", "hadoop102,hadoop103,hadoop104");
            //HBaseAdmin admin = new HBaseAdmin(configuration);
            connection = ConnectionFactory.createConnection(configuration);
            admin = connection.getAdmin();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //1.判断表是否存在
    public static boolean isTableExists(String tableName) throws Exception {
        boolean exists = admin.tableExists(TableName.valueOf(tableName));
        return exists;
    }

    public static void close() throws Exception {
        if (admin != null) {
            admin.close();
        }
        if (connection != null) {
            connection.close();
        }
    }

    //2.创建表
    public static void createTable(String tableName, String... cfs) throws Exception {
        if (cfs.length <= 0) {
            System.out.println("请设置列族信息");
            return;
        }
        if (isTableExists(tableName)) {
            System.out.println(tableName + "表名已存在");
            return;
        }
        HTableDescriptor hTableDescriptor = new HTableDescriptor(TableName.valueOf(tableName));
        for (String cf : cfs) {
            HColumnDescriptor hColumnDescriptor = new HColumnDescriptor(cf);
            //添加列族
            hTableDescriptor.addFamily(hColumnDescriptor);
        }
        admin.createTable(hTableDescriptor);
    }

    //3.删除表
    public static void dropTable(String tableName) throws Exception {
        if (!isTableExists(tableName)) {
            System.out.println("表不存在！");
            return;
        }
        TableName table = TableName.valueOf(tableName);
        admin.disableTable(table);
        admin.deleteTable(table);
    }

    //4.创建命名空间
    public static void createNameSpace(String ns) {
        NamespaceDescriptor namespaceDescriptor = NamespaceDescriptor.create(ns).build();
        try {
            admin.createNamespace(namespaceDescriptor);
        } catch (NamespaceExistException e) {
            System.out.println(ns + "命名空间已存在");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //5.向表插入数据
    public static void putData(String tableName, String rowKey, String cf, String cn, String value) throws Exception {
        //获取表对象
        Table table = connection.getTable(TableName.valueOf(tableName));
        Put put = new Put(Bytes.toBytes(rowKey));
        put.addColumn(Bytes.toBytes(cf), Bytes.toBytes(cn), Bytes.toBytes(value));
        table.put(put);
        table.close();
    }

    //获取数据（get）
    public static void getData(String tableName, String rowKey, String cf, String cn) throws IOException {
        Table table = connection.getTable(TableName.valueOf(tableName));
        Get get = new Get(Bytes.toBytes(rowKey));
        //指定获取的列族
        //get.addFamily(Bytes.toBytes(cf));
        get.addColumn(Bytes.toBytes(cf), Bytes.toBytes(cn));
        //设置获取数据的版本数
        get.setMaxVersions();

        Result result = table.get(get);
        for (Cell cell : result.rawCells()) {
            System.out.println("CF:" + Bytes.toString(CellUtil.cloneFamily(cell)) + ",CN:" + Bytes.toString(CellUtil.cloneQualifier(cell))
                    + ",Value:" + Bytes.toString(CellUtil.cloneValue(cell)));
        }
        table.close();
    }

    //获取数据（scan）
    public static void scanTable(String tableName) throws IOException {
        Table table = connection.getTable(TableName.valueOf(tableName));
        Scan scan = new Scan(Bytes.toBytes("1001"), Bytes.toBytes("1003"));
        ResultScanner resultScanner = table.getScanner(scan);
        for (Result result : resultScanner) {
            for (Cell cell : result.rawCells()) {
                System.out.println("RK:" + Bytes.toString(CellUtil.cloneRow(cell)) + ",CF:" + Bytes.toString(CellUtil.cloneFamily(cell)) + ",CN:" + Bytes.toString(CellUtil.cloneQualifier(cell))
                        + ",Value:" + Bytes.toString(CellUtil.cloneValue(cell)));
            }
        }
        table.close();
    }

    //删除数据
    public static void deleteData(String tableName, String rowKey, String cf, String cn) throws Exception{
        Table table = connection.getTable(TableName.valueOf(tableName));

        Delete delete = new Delete(Bytes.toBytes(rowKey));
        //设置删除的列,如果加了时间戳，只删除指定的时间戳的版本，如果时间戳小于最大时间戳，则还能scan到数据，其它的加了时间戳删除时间戳小于参数的最新版本
        //delete.addColumns(Bytes.toBytes(cf),Bytes.toBytes(cn));
        //设置删除的列，删除一个版本后，另一个最新版本会出来。如果flush了，另一个版本又不会出来。慎用
        //delete.addColumn(Bytes.toBytes(cf),Bytes.toBytes(cn));
        //删除指定列族
        delete.addFamily(Bytes.toBytes(cf));

        table.delete(delete);
        table.close();
    }

    public static void main(String[] args) throws Exception {
        //测试表是否存在
//        System.out.println(isTableExists("stu5"));
//        //创建表测试
//        createTable("0408:stu5", "info1", "info2");
//        System.out.println(isTableExists("stu5"));
//        dropTable("stu5");
//        System.out.println(isTableExists("stu5"));
//
//        createNameSpace("0408");

//        putData("stu", "1001", "info2", "name", "zhangsan");
//        getData("stu","1002","info2","name");

        scanTable("stu");
        //deleteData("stu","1006","info1","name");
        close();
    }
}
