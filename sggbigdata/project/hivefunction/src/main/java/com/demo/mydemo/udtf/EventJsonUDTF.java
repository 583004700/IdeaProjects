package com.demo.mydemo.udtf;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDTF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.StructObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class EventJsonUDTF extends GenericUDTF {
    @Override
    public StructObjectInspector initialize(ObjectInspector[] argOIs) throws UDFArgumentException {
        List<String> filedNames = new ArrayList<String>();
        List<ObjectInspector> fieldsType = new ArrayList<ObjectInspector>();
        filedNames.add("event_name");
        fieldsType.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);
        filedNames.add("event_json");
        fieldsType.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);
        return ObjectInspectorFactory.getStandardStructObjectInspector(filedNames, fieldsType);
    }

    @Override
    public void process(Object[] objects) throws HiveException {
        String input = objects[0].toString();
        if(StringUtils.isBlank(input)){
            return;
        }
        JSONArray ja = new JSONArray(input);
        for (int i = 0; i < ja.length(); i++) {
            String[] results = new String[2];
            try {
                results[0] = ja.getJSONObject(i).getString("en");
                results[1] = ja.getString(i);
            }catch (Exception e){
                e.printStackTrace();
                continue;
            }
            forward(results);
        }
    }

    @Override
    public void close() throws HiveException {

    }
}
