package stone.mycode;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 单个符号
 * <p>
 * 1、英文句点.符号：匹配单个恣意字符。
 * <p>
 * 表达式t.o能够匹配：tno，t#o，teo等等。不能够匹配：tnno，to，Tno，t正o等。
 * <p>
 * 2、中括号[]：只有方括号里边指定的字符才参与匹配，也只能匹配单个字符。
 * <p>
 * 表达式：t[abcd]n只能够匹配：tan，tbn，tcn，tdn。不能够匹配：thn，tabn，tn等。
 * <p>
 * 3、|符号。相当与“或”，能够匹配指定的字符，可是也只能挑选其中一项进行匹配。
 * <p>
 * 表达式：t(a|b|c|dd)n只能够匹配：tan，tbn，tcn，tddn。不能够匹配taan，tn，tabcn等。2db27310093b40acc5c029c5686a8daa.png
 * <p>
 * 4、表明匹配次数的符号
 * <p>
 * 在这里刺进图片描述
 * <p>
 * 表达式：[0—9]{3}\—[0-9]{2}\—[0-9]{3}的匹配格局为：999—99—999
 * <p>
 * 因为—符号在正则表达式中有特殊的含义，它表明一个规模，所以在前面加转义字符\。
 * <p>
 * 5、^符号：表明否，假如用在方括号内，^表明不想匹配的字符。
 * <p>
 * 表达式：[^x]第一个字符不能是x
 * <p>
 * 6、\S符号：非空字符
 * <p>
 * 7、\s符号：空字符，只能够匹配一个空格、制表符、回车符、换页符，不能够匹配自己输入的多个空格。
 * <p>
 * 8、\r符号：空格符，与\n、\tab相同
 * <p>
 * 方便符号
 * <p>
 * 1、\d表明[0—9]
 * <p>
 * 2、\D表明[^0—9]
 * <p>
 * 3、\w表明[0—9A—Z_a—z]
 * <p>
 * 4、\W表明[^0—9A—Z_a—z]
 * <p>
 * 5、\s表明[\t\n\r\f]
 * <p>
 * 6、\S表明[^\t\n\r\f]
 */
public class TestPattern {
    public static String regexPat
            = "\\s*((//.*)|([0-9]+)|(\"(\\\\\"|\\\\\\\\|\\\\n|[^\"])*\")"
            + "|[A-Z_a-z][A-Z_a-z0-9]*|==|<=|>=|&&|\\|\\||\\p{Punct})?";

    @Test
    public void testPattern() {
        String regex = "\\d";
        Pattern pattern = Pattern.compile(regex);
        String text = "357294";
        Matcher matcher = pattern.matcher(text);
        boolean b = matcher.matches();
        System.out.println(b);
        System.out.println(regexPat);// \s*((//.*)|([0-9]+)|("(\\"|\\\\|\\n|[^"])*")|[A-Z_a-z][A-Z_a-z0-9]*|==|<=|>=|&&|\|\||\p{Punct})?

        // 可以有空白字符，注释，数字，字符串（双引号之间内容可以是,空,\",\\,\n,不能是,"），字母或下划线开头可以包含数字的变量名，==，<=，>=，&&，||，标点符号

        Pattern pattern1 = Pattern.compile(regexPat);

        List<String> list = Arrays.asList("\"\\\"\"", "\"\\\\\"", "\"\\n\"", "\"你\"");

        for (String input : list) {
            System.out.println("input:" + input);
            Matcher matcher1 = pattern1.matcher(input);
            System.out.println(matcher1.matches());
        }

        String input1 = "123\"sfs\\nsg\\\\\"<=";
        System.out.println(input1);
        Matcher matcher1 = pattern1.matcher(input1);
        matcher.useTransparentBounds(true).useAnchoringBounds(false);
        int start = 0;
        int end = input1.length();
        while (start < end) {
            matcher1.region(start, end);
            if (matcher1.lookingAt()) {  // 匹配到的字符串在前面
                System.out.println(matcher1.start() + "------" + matcher1.end());
                start = matcher1.end();
            }
        }
    }

    /**
     * 使用透明边界，则匹配时，起始位置和终止位置的前后字符也参与
     */
    @Test
    public void testUseTransparentBounds() {
        Pattern p = Pattern.compile("\\bvvv\\b");
        Matcher m = p.matcher("aaavvv aaaaaaaaaa");
        m.useTransparentBounds(true);
        // 搜索的起始和终止位置
        m.region(3, 8);
        // 查找字符串
        boolean b = m.find();
        System.out.println(b);
        if (b) {
            System.out.println(m.start() + "---" + m.end());
        }
    }

    /**
     * 锚定边界
     */
    @Test
    public void testUseAnchoringBounds() {
        String regex = "^car";
        String text = "Madagascar";
        Matcher m = Pattern.compile(regex).matcher(text);
        // 使用非锚定边界找不到，因为会把整个字符串当作开始。
        m.useAnchoringBounds(true);
        m.region(7, text.length());
        m.find();
        System.out.println("Matches starting at character " + m.start());
    }

    /**
     * 测试组
     */
    @Test
    public void testGroup() {
        String str = "Hello,World! in Java.";
        Pattern pattern = Pattern.compile("W(or)(ld!)");
        Matcher matcher = pattern.matcher(str);
        while(matcher.find()){
            System.out.println("Group 0:"+matcher.group(0));//得到第0组——整个匹配
            System.out.println("Group 1:"+matcher.group(1));//得到第一组匹配——与(or)匹配的
            System.out.println("Group 2:"+matcher.group(2));//得到第二组匹配——与(ld!)匹配的，组也就是子表达式
            System.out.println("Start 0:"+matcher.start(0)+" End 0:"+matcher.end(0));//总匹配的索引
            System.out.println("Start 1:"+matcher.start(1)+" End 1:"+matcher.end(1));//第一组匹配的索引
            System.out.println("Start 2:"+matcher.start(2)+" End 2:"+matcher.end(2));//第二组匹配的索引
            System.out.println(str.substring(matcher.start(0),matcher.end(1)));//从总匹配开始索引到第1组匹配的结束索引之间子串——Wor
        }

        Matcher matcher1 = Pattern.compile("(\\d+)|(\\s*)").matcher("6789");
        matcher1.useTransparentBounds(true).useAnchoringBounds(false);
        while(matcher1.find()){
            System.out.println("group 0:"+matcher1.group(0));
            System.out.println("group 1:"+matcher1.group(1));
            System.out.println("group 2:"+matcher1.group(2));
        }

        Matcher matcher2 = Pattern.compile("\\s*").matcher("67");   //可以找到三次，0-2 true，1-2 true，2-2 true
        int i = 1;
        while (matcher2.find()) {
            System.out.println(i++);
        }

        matcher2 = Pattern.compile("\\s+").matcher("  67 ");   //可以找到二次，0-5 true，2，5 true，5-5 false
        i = 1;
        while (matcher2.find()) {
            System.out.println(i++);
        }

        String text = " asoftyg987\"string\"";

        System.out.println("----------------------");
        matcher2 = Pattern.compile(regexPat).matcher(text);
        matcher2.useTransparentBounds(true).useAnchoringBounds(false);
        while(matcher2.find()){
            System.out.println("group(0)"+matcher2.group(0));
            System.out.println("group(1)"+matcher2.group(1));
            System.out.println("group(2)"+matcher2.group(2));
            System.out.println("group(3)"+matcher2.group(3));
            System.out.println("group(4)"+matcher2.group(4));
        }

        System.out.println("---------------------");
        matcher2 = Pattern.compile(regexPat).matcher(text);
        matcher2.useTransparentBounds(true).useAnchoringBounds(false);
        int pos = 0;
        int endPos = text.length();
        while(pos<endPos){
            matcher2.region(pos,endPos);
            if(matcher2.lookingAt()){
                System.out.println("group(0)"+matcher2.group(0));
                System.out.println("group(1)"+matcher2.group(1));
                System.out.println("group(2)"+matcher2.group(2));
                System.out.println("group(3)"+matcher2.group(3));
                System.out.println("group(4)"+matcher2.group(4));
                pos = matcher2.end();
            }
        }
    }

}
