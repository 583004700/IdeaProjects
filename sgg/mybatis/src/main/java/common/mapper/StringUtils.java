package common.mapper;

public class StringUtils {
    public static boolean isNotEmpty(String str){
        if(str == null || str.trim().length() == 0){
            return false;
        }
        return true;
    }
}
