package org.nizz.springgradle;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

/*
 * =================================
 *   BEAN FACTORY POST PROCESSORS
 * =================================
 * Runs during bean factory initialization - before the beans are initialized
 * This is called even if we use Application Context because it is ultimately a bean-factory with additional features
 *
 * */
public class CustomBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        System.out.println("Inside postProcessBeanFactory -----");
    }
}
