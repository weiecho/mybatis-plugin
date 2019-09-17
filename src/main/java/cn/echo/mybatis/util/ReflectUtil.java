package cn.echo.mybatis.util;

import java.lang.reflect.Field;

/**
 * 反射类和方法
 * @author lonyee
 */
public class ReflectUtil {


    /**
     * 根据成员变量名称获取对象值
     * @param clazzInstance 对象实例
     * @param field 字段名称
     * @return 返回对象值
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    public static <T> Object getFieldValue(Object clazzInstance, Object field) throws IllegalArgumentException, IllegalAccessException {

        Field[] fields = getFields(clazzInstance.getClass());

        for (int i = 0; i < fields.length; i++) {
            if (fields[i].getName().equals(field)) {
                //对于私有变量的访问权限，在这里设置，这样即可访问Private修饰的变量
                fields[i].setAccessible(true);
                return fields[i].get(clazzInstance);
            }
        }
        return null;
    }

    /**
     * 利用反射设置指定对象的指定属性为指定的值
     * @param obj 对象
     * @param fieldName 字段名称
     * @param fieldValue 字段值
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public static void setFieldValue(Object obj, String fieldName, String fieldValue) throws IllegalArgumentException, IllegalAccessException {
        Field field = getField(obj.getClass(), fieldName);
        if (field != null) {
            field.setAccessible(true);
            field.set(obj, fieldValue);
        }
    }

    /**
     * 返回指定字段
     * @param clazz 类
     * @param fieldName 字段名称
     * @return 字段
     */
    public static <T> Field getField(Class<T> clazz, String fieldName) {
        try {
            Field field = clazz.getDeclaredField(fieldName);
            if (field == null) {
                field = clazz.getSuperclass().getDeclaredField(fieldName);
            }
            return field;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取所有的成员变量 (包含父类的成员变量)
     * @param clazz 类
     * @return Field[] 字段数组
     */
    public static <T> Field[] getFields(Class<T> clazz) {

        Field[] superFields = clazz.getSuperclass().getDeclaredFields();
        Field[] extFields = clazz.getDeclaredFields();
        Field[] fields = new Field[superFields.length + extFields.length];
        System.arraycopy(superFields, 0, fields, 0, superFields.length);
        System.arraycopy(extFields, 0, fields, superFields.length, extFields.length);

        return fields;
    }
}
