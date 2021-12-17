package common.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import ognl.Ognl;
import ognl.OgnlContext;

public class ObjectUtils {
    /**
     * 从对象上取值
     * @param data
     * @param key
     * @return
     */
    public static Object getValue(Object data,String key){
        return doGetValue(data,key);
    }

    private static Object doGetValue(Object data,String key){
        try {
            int sp = key.indexOf(".");
            String front = key;
            String last = "";
            if (sp != -1) {
                front = key.substring(0, sp);
                last = key.substring(sp + 1);
                Object o = doGetValue(data, front);
                return doGetValue(o, last);
            } else {
                if (data == null) {
                    return null;
                }
                if (data instanceof JSONObject) {
                    int leftIndex = key.indexOf("[");
                    if(leftIndex == -1) {
                        return ((JSONObject) data).get(key);
                    }else{
                        int rightIndex = key.indexOf("]");
                        int index = Integer.valueOf(key.substring(leftIndex+1,rightIndex));
                        String realKey = key.substring(0,leftIndex);
                        ((JSONObject) data).getJSONArray(realKey).get(index);
                    }
                }
                if(data instanceof String){
                    String r = (String) data;
                    JSONObject jsonObject = JSONObject.parseObject(r);
                    return doGetValue(jsonObject,key);
                }
                OgnlContext ognlContext = new OgnlContext();
                ognlContext.setRoot(data);
                Object value = Ognl.getValue(key, ognlContext, ognlContext.getRoot());
                return value;
            }
        }catch (Exception e){

        }
        return null;
    }

    public static void main(String[] args) {
        String jsonStr = "{\"extendField2\":\"{\\\"extendField3\\\":\\\"aaa\\\",\\\"extendField1\\\":\\\"CC2100-总经办\\\"}\",\"extendField1\":\"CC2100-总经办\"}";
        System.out.println("原对象："+jsonStr);
        System.out.println("取extendField2为："+ObjectUtils.getValue(jsonStr,"extendField2"));
        System.out.println("取extendField2.extendField3为："+ObjectUtils.getValue(jsonStr,"extendField2.extendField3"));

        System.out.println("-----------------");
        JSONObject jod = new JSONObject();
        jod.put("d","d");
        JSONObject joc = new JSONObject();
        joc.put("c",jod);
        JSONObject job = new JSONObject();
        job.put("b",joc);
        JSONObject joa = new JSONObject();
        joa.put("a",job);
        System.out.println(joa.toJSONString());
        System.out.println(ObjectUtils.getValue(joa.toJSONString(),"a.b.c"));
        System.out.println(ObjectUtils.getValue(joa.toJSONString(),"a.b.c.d"));

        System.out.println("----------------------");

        JSONObject jbd = new JSONObject();
        JSONArray ja = new JSONArray();
        ja.add(1);
        ja.add(2);
        jbd.put("d",ja);
        JSONObject jbc = new JSONObject();
        jbc.put("c",jbd.toJSONString());
        JSONObject jbb = new JSONObject();
        jbb.put("b",jbc.toJSONString());
        JSONObject jba = new JSONObject();
        jba.put("a",jbb.toJSONString());
        String jbaStr = jba.toJSONString();
        System.out.println(jbaStr);
        System.out.println(ObjectUtils.getValue(jbaStr,"a.b.c.d"));
        System.out.println(ObjectUtils.getValue(jbaStr,"a.b.c.d[0]"));
        System.out.println(ObjectUtils.getValue(jbaStr,"a.b.c.d["));
    }
}
