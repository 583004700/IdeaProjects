package com.demo.mydemo.udf;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.json.JSONObject;

public class BaseFieldUDF extends UDF {
    public String evaluate(String line, String jsonkeysString){
        StringBuffer sb = new StringBuffer();
        try {
            // 1 获取所有 key mid uv
            String[] jsonKeys = jsonkeysString.split(",");
            // 2 line 服务器时间|json
            String[] logContents = line.split("\\|");
            // 3 校验
            if(logContents.length != 2 || StringUtils.isBlank(logContents[1])){
                return "";
            }else{
                // 4 对 logContents[1]创建json对象
                JSONObject jsonObject = new JSONObject(logContents[1]);
                JSONObject cmJson = jsonObject.getJSONObject("cm");
                for (int i = 0; i < jsonKeys.length; i++) {
                    String jsonKey = jsonKeys[i].trim();
                    if(cmJson.has(jsonKey)){
                        sb.append(cmJson.getString(jsonKey)).append("\t");
                    }else{
                        sb.append("\t");
                    }
                }
                sb.append(jsonObject.getString("et")).append("\t");
                sb.append(logContents[0]).append("\t");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return sb.toString();
    }
}
