package org.nizz.springgradle;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/*
* =================================
*      BEAN POST PROCESSORS
* =================================
* Runs for every bean initialization - similar to init-method and destroy-method but in this case, we don't need to
* add the method definitions for each class individually - adding to a centralized place such as this is enough
*
* */
public class DisplayBeanNamePostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object beanObject, String beanName) throws BeansException {
        System.out.println("Inside postProcessBeforeInitialization for bean : " + beanName);
        return beanObject;
    }

    @Override
    public Object postProcessAfterInitialization(Object beanObject, String beanName) throws BeansException {
        System.out.println("Inside postProcessAfterInitialization for bean : " + beanName + "\n");
        return beanObject;
    }
}
