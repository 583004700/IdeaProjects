package chapter4.algorithm;

public class BF {
    public static void main(String[] args) {
        String s = "abaababaababoabaababc";
        String t = "abaabab";

        int i = 0;
        int j = 0;

        while (i < s.length()) {
            char sc = s.charAt(i);
            char tc = t.charAt(j);
            if (sc == tc && j == t.length() - 1) {
                System.out.println(i - j);
                i = i - j + 1;
                j = 0;
            } else if (sc == tc) {
                i++;
                j++;
            } else {
                i = i - j + 1;
                j = 0;
            }
        }
    }
}
