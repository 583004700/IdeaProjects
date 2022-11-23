package s03logging;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * SpringBoot单元测试;
 *
 * 可以在测试期间很方便的类似编码一样进行自动注入等容器的功能
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBoot03LoggingApplication.class)
public class SpringBoot03LoggingApplicationTest {
	@Test
	public void contextLoads(){
		Logger logger = LoggerFactory.getLogger(getClass());
		//日志级别由低到高
		logger.trace("这是trace日志...");
		logger.debug("这是debug日志...");
		//Springboot默认使用的是info级别
		logger.info("这是info日志...");
		logger.warn("这是warn日志...");
		logger.error("这是error日志...");
	}
}
