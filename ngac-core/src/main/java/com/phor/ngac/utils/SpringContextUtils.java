package com.phor.ngac.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/*
 * 获取spring容器中bean工具类
 */
//这个注解的目的是为了把的SpringContextUtils的实例化交给Spring容器管理
@Component("springContextUtils")
public class SpringContextUtils implements ApplicationContextAware {
    private static ApplicationContext applicationContext = null;

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /*
     * Spring容器启动后，会把 applicationContext 给自动注入进来，然后我们把 applicationContext 赋值到静态变量中，方便后续拿到容器对象
     * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtils.applicationContext = applicationContext;

    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String beanId) {
        return (T) applicationContext.getBean(beanId);
    }

    public static <T> T getBean(Class<T> requiredType) {
        return (T) applicationContext.getBean(requiredType);
    }

}