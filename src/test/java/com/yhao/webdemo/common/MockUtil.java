package com.yhao.webdemo.common;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class MockUtil {
    private static List<String> iocAnnotations = Arrays.asList(
            Autowired.class.getName(), Resource.class.getName());

    public static <T> T innerMockObject(Class<T> clazz) {
        T object = null;
        try {
            object = clazz.newInstance();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                Annotation[] annotations = field.getDeclaredAnnotations();
                boolean isInjectedBean = false;
                for (Annotation annotation : annotations) {
                    if (iocAnnotations.contains(annotation.annotationType().getName())) {
                        isInjectedBean = true;
                        break;
                    }
                }
                if (isInjectedBean) {
                    field.setAccessible(true);
                    Class<?> fieldClazz = field.getType();
                    field.set(object, Mockito.mock(fieldClazz));
                }
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return object;
    }
}
