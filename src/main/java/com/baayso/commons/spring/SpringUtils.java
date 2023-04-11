package com.baayso.commons.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import java.util.Map;

/**
 * Spring工具类。
 *
 * @author ChenFangjie (2014/12/24 14:41:52)
 * @since 1.0.0
 */
public final class SpringUtils implements BeanFactoryPostProcessor {

    /**
     * Spring应用上下文环境
     */
    private static ConfigurableListableBeanFactory beanFactory;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        SpringUtils.beanFactory = beanFactory;
    }

    public static ConfigurableListableBeanFactory getBeanFactory() {
        return beanFactory;
    }

    /**
     * 返回指定Bean的实例，该实例可以是共享的或独立的。
     *
     * @param name Bean的名称
     * @return Bean的实例
     * @throws BeansException 如果无法获得Bean
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
     * 返回唯一匹配给定对象类型（如果有）的Bean实例。
     *
     * @param requiredType Bean必须匹配的类型，可以是接口或超类
     * @return 与所需类型匹配的单个Bean的实例
     * @throws BeansException 如果无法创建Bean
     * @see org.springframework.beans.factory.BeanFactory#getBean(Class requiredType)
     * @since 1.0.0
     */
    public static <T> T getBean(Class<T> requiredType) throws BeansException {
        T bean = null;

        if (beanFactory != null) {
            bean = beanFactory.getBean(requiredType);
        }

        return bean;
    }

    /**
     * 返回与给定对象类型（包括子类）匹配的Bean实例，根据Bean定义或在FactoryBeans的情况下根据getObjectType的值判断。
     * <p>
     * 注意：此方法仅自检顶级Bean。它不会检查可能与指定类型匹配的嵌套Bean。
     *
     * @param type 要匹配的类或接口，传null则为所有具体Bean
     * @return 带有匹配Bean的Map，包含Bean名称作为键，对应的Bean实例作为值
     * @throws BeansException 如果无法创建Bean
     * @see org.springframework.beans.factory.ListableBeanFactory#getBeansOfType(Class type)
     * @since 1.0.0
     */
    public static <T> Map<String, T> getBeans(Class<T> type) throws BeansException {
        Map<String, T> beans = null;

        if (beanFactory != null) {
            beans = beanFactory.getBeansOfType(type);
        }

        return beans;
    }

    /**
     * 如果BeanFactory包含一个与所给名称匹配的Bean实例，则返回true。
     *
     * @param name 要查询的Bean的名称
     * @return 是否存在具有给定名称的Bean
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
     * 判断以给定名字注册的Bean定义是一个singleton还是一个prototype。
     * <p>
     * 如果与给定名字相应的Bean定义没有被找到，将会抛出一个NoSuchBeanDefinitionException异常。
     *
     * @param name 要查询的Bean名称
     * @return 此Bean是否对应于singleton实例
     * @throws NoSuchBeanDefinitionException 如果没有具有给定名称的Bean
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
     * 确定给定名称Bean的类型。更具体地，确定对象的的getBean将返回给定名称的类型。
     *
     * @param name bean name
     * @return Bean的类型，如果为null则无法确定Bean的类型
     * @throws NoSuchBeanDefinitionException 如果没有具有给定名称的Bean
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
     * 返回给定Bean名称的别名（如果有）。
     * <p>
     * 在调用中使用getBean时，所有这些别名都指向相同的Bean。
     * <p>
     * 如果给定名称是别名，则将返回相应的原始Bean名称和其他别名（如果有），原始Bean名称是数组中的第一个元素。
     *
     * @param name 要检查别名的Bean名称
     * @return Bean的所有别名，如果没有则为空数组
     * @see org.springframework.beans.factory.BeanFactory#getAliases(String name)
     * @since 1.0.0
     */
    public static String[] getAliases(String name) {
        String[] aliases = null;

        if (beanFactory != null) {
            aliases = beanFactory.getAliases(name);
        }

        return aliases;
    }

}
