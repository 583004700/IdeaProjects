package chapter4.algorithm;

/**
 * 不是最原始的，自己改写可以求出所有匹配的下标
 */

public class KMP {
    public static void main(String[] args) {
        String s = "abaababaababoabaabab";
        String t = "abaabab";

        int[] next = getNext(t+" ");
        int i = 0;
        int j = 0;
        while(i<s.length()){
            if(j == t.length() -1 && s.charAt(i) == t.charAt(j)){
                System.out.println(i-t.length()+1);
                j = next[j+1];
                i++;
                continue;
            }
            if(s.charAt(i) == t.charAt(j)){
                i++;
                j++;
            }else{
                j = next[j];
            }
            if(j == 0){
                i++;
            }
        }
    }

    /**
     * 求出 j 分别为每个下标时，最大的前后缀相等
     * @param t
     * @return
     */
    public static int[] getNext(String t) {
        int[] res = new int[t.length()];
//        res[0] = 0;
        res[0] = -1;
        res[1] = 0;
        char[] arr = t.toCharArray();
        int j = 1;
        int k = res[j];
        while (j < t.length() - 1) {
            /*if (arr[k] == arr[j] || k == 0) {
                if(k == 0 && arr[k] != arr[j]){
                    res[++j] = k++;
                    continue;
                }
                res[++j] = ++k;
            }else{
                k = res[k];
            }*/
            if (k == -1 || arr[k] == arr[j]) {
                res[++j] = ++k;
            }else{
                // 例如：abeabcabeabe，当j指向最后的e时，k指向c，判断e和c是否相等，如果不等， k 指向 res[k]，也就是 k 时，最大
                // 前缀后缀相等， k 指向c 时，最大前后缀相等的值为 2 ，指向 2 时，判断e和最后的e是否相等，相等则最后的 res[++j] = ++k;
                // 为什么需要回退到 k = res[k],因为可以保证 0 到 下标j时，最长的前后缀依旧是k ，也就是 k 为 5时，和 k 为 2 时，
                // 都是前后缀相等，k为5时，前后缀都是abeab，k为2时，前后缀都是ab
                k = res[k];
            }
        }
        res[0] = 0;
        return res;
    }
}
