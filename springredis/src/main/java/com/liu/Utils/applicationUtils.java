package com.liu.Utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

//用于获取springboot 创建好的工厂
@Configuration
public class applicationUtils  implements ApplicationContextAware {

    //保留下来工厂
    private static ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }

    //提供在工厂中获取对象的方法 //RedisTemplate  redisTemplate
    public static Object getBean(String beanName){
        return applicationContext.getBean(beanName);
    }
}
