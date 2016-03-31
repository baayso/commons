package com.baayso.commons.spring;

import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

/**
 * Spring工具类。
 *
 * @author ChenFangjie (2014/12/24 14:41:52)
 * @since 1.0.0
 */
public final class SpringUtils implements BeanFactoryPostProcessor {

    private static ConfigurableListableBeanFactory beanFactory; // Spring应用上下文环境

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        SpringUtils.beanFactory = beanFactory;
    }

    public static ConfigurableListableBeanFactory getBeanFactory() {
        return beanFactory;
    }

    /**
     * 获取对象。
     *
     * @param name bean的名称
     *
     * @return 所给名字注册的bean实例
     *
     * @throws BeansException
     * @see org.springframework.beans.factory.BeanFactory#getBean(String name)
     * @since 1.0.0
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) throws BeansException {
        T bean = null;

        if (beanFactory != null) {
            bean = (T) beanFactory.getBean(name);
        }

        return bean;
    }

    /**
     * 获取类型为requiredType的对象。
     *
     * @param clz bean class
     *
     * @return bean实例
     *
     * @throws BeansException
     * @see org.springframework.beans.factory.BeanFactory#getBean(Class requiredType)
     * @since 1.0.0
     */
    public static <T> T getBean(Class<T> clz) throws BeansException {
        T bean = null;

        if (beanFactory != null) {
            bean = beanFactory.getBean(clz);
        }

        return bean;
    }

    /**
     * 获取类型为requiredType的一组对象。
     *
     * @param clz bean class
     *
     * @return 一组bean实例
     *
     * @throws BeansException
     * @see org.springframework.beans.factory.ListableBeanFactory#getBeansOfType(Class type)
     * @since 1.0.0
     */
    public static <T> Map<String, T> getBeans(Class<T> clz) throws BeansException {
        Map<String, T> beans = null;

        if (beanFactory != null) {
            beans = beanFactory.getBeansOfType(clz);
        }

        return beans;
    }

    /**
     * 如果BeanFactory包含一个与所给名称匹配的bean定义，则返回true。
     *
     * @param name bean name
     *
     * @return boolean
     *
     * @see org.springframework.beans.factory.BeanFactory#containsBean(String name)
     * @since 1.0.0
     */
    public static boolean containsBean(String name) {
        boolean contains = false;

        if (beanFactory != null) {
            contains = beanFactory.containsBean(name);
        }

        return contains;
    }

    /**
     * 判断以给定名字注册的bean定义是一个singleton还是一个prototype。 如果与给定名字相应的bean定义没有被找到，将会抛出一个异常（NoSuchBeanDefinitionException）。
     *
     * @param name bean name
     *
     * @return boolean
     *
     * @throws NoSuchBeanDefinitionException
     * @see org.springframework.beans.factory.BeanFactory#isSingleton(String name)
     * @since 1.0.0
     */
    public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
        boolean isSingleton = false;

        if (beanFactory != null) {
            isSingleton = beanFactory.isSingleton(name);
        }

        return isSingleton;
    }

    /**
     * 确定与给定名称的bean的类型。更具体地，确定对象的的getBean将返回给定名称的类型。
     *
     * @param name bean name
     *
     * @return Class 注册对象的类型
     *
     * @throws NoSuchBeanDefinitionException
     * @see org.springframework.beans.factory.BeanFactory#getType(String name)
     * @since 1.0.0
     */
    public static Class<?> getType(String name) throws NoSuchBeanDefinitionException {
        Class<?> clazz = null;

        if (beanFactory != null) {
            clazz = beanFactory.getType(name);
        }

        return clazz;
    }

    /**
     * 如果给定的bean名字在bean定义中有别名，则返回这些别名。
     *
     * @param name bean name
     *
     * @return bean aliases
     *
     * @throws NoSuchBeanDefinitionException
     * @see org.springframework.beans.factory.BeanFactory#getAliases(String name)
     * @since 1.0.0
     */
    public static String[] getAliases(String name) throws NoSuchBeanDefinitionException {
        String[] aliases = null;

        if (beanFactory != null) {
            aliases = beanFactory.getAliases(name);
        }

        return aliases;
    }

}
