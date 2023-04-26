package com.yhao.webdemo.dao.dynamic;

public class DataSourceContextHolder {

    private static final ThreadLocal<String> context = new InheritableThreadLocal<>();

    public static void set(String dataSourceType) {
        context.set(dataSourceType);
    }

    public static String get() {
        return context.get();
    }

    public static void clear() {
        context.remove();
    }
}
