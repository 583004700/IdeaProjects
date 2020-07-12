package readSource;

import org.springframework.util.PropertyPlaceholderHelper;

import java.util.Properties;

public class PropertyPlaceholderHelperTest {
    public static void main(String[] args) {
        PropertyPlaceholderHelper propertyPlaceholderHelper
                = new PropertyPlaceholderHelper("${","}");


        Properties p = new Properties();
        p.setProperty("m","mv");
        p.setProperty("amv","value-----");
        String value = propertyPlaceholderHelper.replacePlaceholders("${a${m}}",p);
        System.out.println(value);

    }
}
