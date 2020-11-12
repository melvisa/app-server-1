package com.app.hupi.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BeanUtil extends  org.springframework.beans.BeanUtils {


	  /**
   * 将列表中的所有元素进行类型转换，转换时会把同名同类的属性复制到新类型实例对象中
   *
   * @param src 源列表
   * @param clazz 转换类型
   * @param <T> 转换类型的泛型标记
   * @return 转换后的新类型实例列表
   */
  public static <T> List<T> copyPropsForList(List<?> src, Class<T> clazz) {
      List<T> list = new ArrayList<>(src.size());
      for (Object each : src) {
          try {
              list.add(transform(each, clazz));
          }
          catch (Exception e) {
             
          }
      }
      return list;
  }
  
  /**
   * 把对象的属性拷贝到指定的类实例中
   * @param source
   * @param targetClass
   * @return
   */
  public static <T> T transform(Object source, Class<T> targetClass) {
      return transform(source, targetClass, (String[]) null);
  }
  
  /**
   * 把对象的属性拷贝到指定的类实例中
   * @param source
   * @param targetClass
   * @param ignoreProperties
   * @return
   */
  public static <T> T transform(Object source, Class<T> targetClass, String... ignoreProperties) {
      if (Objects.isNull(source) || Objects.isNull(targetClass)) {
          return null;
      }

      T instance = instantiate(targetClass);
      copyProperties(source, instance, ignoreProperties);
      return instance;
  }
}
