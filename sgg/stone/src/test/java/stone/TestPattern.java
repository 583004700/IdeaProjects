package stone;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 单个符号
 *
 * 1、英文句点.符号：匹配单个恣意字符。
 *
 * 表达式t.o能够匹配：tno，t#o，teo等等。不能够匹配：tnno，to，Tno，t正o等。
 *
 * 2、中括号[]：只有方括号里边指定的字符才参与匹配，也只能匹配单个字符。
 *
 * 表达式：t[abcd]n只能够匹配：tan，tbn，tcn，tdn。不能够匹配：thn，tabn，tn等。
 *
 * 3、|符号。相当与“或”，能够匹配指定的字符，可是也只能挑选其中一项进行匹配。
 *
 * 表达式：t(a|b|c|dd)n只能够匹配：tan，tbn，tcn，tddn。不能够匹配taan，tn，tabcn等。2db27310093b40acc5c029c5686a8daa.png
 *
 * 4、表明匹配次数的符号
 *
 * 在这里刺进图片描述
 *
 * 表达式：[0—9]{3}\—[0-9]{2}\—[0-9]{3}的匹配格局为：999—99—999
 *
 * 因为—符号在正则表达式中有特殊的含义，它表明一个规模，所以在前面加转义字符\。
 *
 * 5、^符号：表明否，假如用在方括号内，^表明不想匹配的字符。
 *
 * 表达式：[^x]第一个字符不能是x
 *
 * 6、\S符号：非空字符
 *
 * 7、\s符号：空字符，只能够匹配一个空格、制表符、回车符、换页符，不能够匹配自己输入的多个空格。
 *
 * 8、\r符号：空格符，与\n、\tab相同
 *
 * 方便符号
 *
 * 1、\d表明[0—9]
 *
 * 2、\D表明[^0—9]
 *
 * 3、\w表明[0—9A—Z_a—z]
 *
 * 4、\W表明[^0—9A—Z_a—z]
 *
 * 5、\s表明[\t\n\r\f]
 *
 * 6、\S表明[^\t\n\r\f]
 */
public class TestPattern {

    @Test
    public void testPattern(){
        String regex = "\\S+";
        Pattern pattern = Pattern.compile(regex);
        String text = "s f";
        Matcher matcher = pattern.matcher(text);
        boolean b = matcher.matches();
        System.out.println(b);
    }

    /**
     * 使用透明边界，则匹配时，起始位置和终止位置的前后字符也参与
     */
    @Test
    public void testUseTransparentBounds(){
        Pattern p = Pattern.compile("\\bvvv\\b");
        Matcher m = p.matcher("aaavvv aaaaaaaaaa");
        m.useTransparentBounds(true);
        // 搜索的起始和终止位置
        m.region(3, 8);
        // 查找字符串
        boolean b = m.find();
        System.out.println(b);
        if(b){
            System.out.println(m.start()+"---"+m.end());
        }
    }

    public void testUseAnchoringBounds(){

    }

}
