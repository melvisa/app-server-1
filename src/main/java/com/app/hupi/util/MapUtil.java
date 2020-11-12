package com.app.hupi.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.util.ObjectUtils;

import net.sf.cglib.beans.BeanMap;


public class MapUtil  extends org.apache.commons.collections.MapUtils{
	
	
	public static Map<String,Object> getMap(String key,Object value){
		 Map<String,Object> map=new HashMap<>();
		 map.put(key, value);
		 return map;
	}
	
	public static Map<String,Object> addMap(Map<String,Object> map,String key,Object value){
		 map.put(key, value);
		 return map;
	}
	
	
	 /**
     * map转bean
     *
     * @param map
     * @param beanClass
     * @param ignoreKeys
     * @return <br>
     */
    public static <T> T mapToBean(Map<String, ?> map, Class<T> beanClass, String... ignoreKeys) {
        if (Objects.isNull(beanClass)) {
            return null;
        }
        T bean = BeanUtils.instantiate(beanClass);
        return mapToBean(map, bean, ignoreKeys);
    }

    /**
     * map 转 bean
     * <li>借助 Spring.BeanUtils 修复 builder 模式 bean 的转换失效问题</li>
     *
     * @param map
     * @param bean
     * @param ignoreKeys
     * @return
     */
    public static <T> T mapToBean(Map<String, ?> map, T bean, String... ignoreKeys) {
        if (Objects.isNull(map) || map.isEmpty() || Objects.isNull(bean)) {
            return bean;
        }
        List<String> ignoreKeyList = new ArrayList<>();
        if (!ObjectUtils.isEmpty(ignoreKeys)) {
            ignoreKeyList = Arrays.asList(ignoreKeys);
        }
    
        
        PropertyDescriptor[] propertyDescriptors = BeanUtils.getPropertyDescriptors(bean.getClass());
        for (PropertyDescriptor property : propertyDescriptors) {
            String key = property.getName();
            if (ignoreKeyList.contains(key)) {
                continue;
            }
            if (map.containsKey(key)) {
                Object value = map.get(key);
                // 得到property对应的setter方法
                Method setter = property.getWriteMethod();
                
                try {
                    try {
						setter.invoke(bean, value);
					} catch (Exception e) {
						System.out.println("异常key:"+key+"值："+value+"属性类型："+property.getPropertyType());
						e.printStackTrace();
					} 
                }finally {
                	
                }
            }
        }
        return bean;
    }

    /**
     * bean转map
     *
     * @param bean
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <K, V> Map<K, V> beanToMap(Object bean) {
        Map<K, V> map = new HashMap<>();
        if (Objects.nonNull(bean)) {
            BeanMap beanMap = BeanMap.create(bean);
            Set<Entry<K, V>> entrySet = beanMap.entrySet();
            for (Entry<K, V> entry : entrySet) {
                map.put(entry.getKey(), entry.getValue());
            }
        }
        return map;
    }

    /**
     * Key下划线转驼峰
     *
     * @param map
     * @return
     */
    public static <T> Map<String, T> underlineToCamel(Map<String, T> map) {
        if (Objects.isNull(map) || map.isEmpty()) {
            return map;
        }

        Map<String, T> tempMap = new HashMap<>();
        map.entrySet()
            .forEach(entry -> tempMap.put(StringUtil.underlineToCamel(entry.getKey()), entry.getValue()));

        map.clear();
        map.putAll(tempMap);
        return map;
    }

    /**
     * Key驼峰转下划线
     *
     * @param map
     * @return
     */
    public static <T> Map<String, T> camelToUnderline(Map<String, T> map) {
        if (Objects.isNull(map) || map.isEmpty()) {
            return map;
        }

        Map<String, T> tempMap = new HashMap<>();
        map.entrySet()
            .forEach(entry -> tempMap.put(StringUtil.camelToUnderline(entry.getKey()), entry.getValue()));

        map.clear();
        map.putAll(tempMap);
        return map;
    }

    /**
     * Description: <br>
     * 是否包含非null
     *
     * @author lifl<br>
     * @taskId <br>
     * @param map
     * @param name
     * @return <br>
     */
    public static boolean containValue(Map<?, ?> map, String name) {
        return Optional.ofNullable(map).map(m -> map.get(name) != null).orElse(false);
    }

    /**
     * 根据key路径获取Map的value
     *
     * @param map Map对象
     * @param keys key的层级
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getValue(Map<String, ?> map, String... keys) {
        if (Objects.isNull(map) || map.isEmpty() || ObjectUtils.isEmpty(keys)) {
            return null;
        }

        Object value = map;
        for (String key : keys) {
            if (value instanceof Map) {
                value = ((Map<String, Object>) value).get(key);
            }
            else if (value instanceof List) {
                List<Map<String, Object>> list = (List<Map<String, Object>>) value;
                if (ListUtil.isEmpty(list)) {
                    // 路径中有节点是数组，只能获取第一个
                    value = list.get(0).get(key);
                }
                else {
                    return null;
                }
            }
            else {
                return null;
            }
        }

        return (T) value;
    }

    /**
     * 根据key路径获取Map的字符串value
     *
     * @param map Map对象
     * @param keys key的层级
     * @return
     */
    public static String getString(Map<String, ?> map, String... keys) {
        Object value = getValue(map, keys);
        return Optional.ofNullable(value).map(v -> v.toString()).orElse("");
    }

    /**
     * 根据key获取Map的字符串value 不存在则返回空字符串 上面那个方法假如只有一个key，会调到父类的getString方法
     *
     * @param map
     * @param key
     * @return
     */
    public static String getString(Map<String, ?> map, String key) {
        Object value = getValue(map, key);
        if (Objects.isNull(value)) {
            key = key.toUpperCase();
            value = getValue(map, key);
        }
        return Optional.ofNullable(value).map(v -> v.toString()).orElse("");
    }

}
