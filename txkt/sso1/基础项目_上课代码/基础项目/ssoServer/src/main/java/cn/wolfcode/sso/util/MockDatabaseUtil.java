package cn.wolfcode.sso.util;


import cn.wolfcode.sso.vo.ClientInfoVo;
import com.sun.org.apache.bcel.internal.generic.NEW;

import java.util.*;

/**
 * Created by wolfcode-lanxw
 */
public class MockDatabaseUtil {
    public static Set<String> T_TOKEN = new HashSet<String>();
    public static Map<String,List<ClientInfoVo>> T_CLIENT_INFO =new HashMap<String,List<ClientInfoVo>>();
}
