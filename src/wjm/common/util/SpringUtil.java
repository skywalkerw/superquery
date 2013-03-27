package wjm.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

public class SpringUtil {
	private static ApplicationContext context;

	public synchronized static void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		context = applicationContext;
	}

	public static Object findBean(String name) {
		return context.getBean(name);
	}
}
