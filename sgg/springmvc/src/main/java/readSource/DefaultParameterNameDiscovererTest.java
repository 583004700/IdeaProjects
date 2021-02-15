package readSource;

import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;

import java.lang.reflect.Method;
import java.util.Arrays;

public class DefaultParameterNameDiscovererTest {
    public static void main(String[] args) throws Exception {
        DefaultParameterNameDiscoverer pnd = new DefaultParameterNameDiscoverer();
        Method method1 = DefaultParameterNameDiscovererTest.class.getMethod("method1", String.class, String.class, int.class);
        String[] params = pnd.getParameterNames(method1);
        System.out.println(Arrays.asList(params));

        LocalVariableTableParameterNameDiscoverer lvtpn = new LocalVariableTableParameterNameDiscoverer();
        params = lvtpn.getParameterNames(method1);
        System.out.println(Arrays.asList(params));
    }

    public void method1(String id, String name, int age) {

    }
}
