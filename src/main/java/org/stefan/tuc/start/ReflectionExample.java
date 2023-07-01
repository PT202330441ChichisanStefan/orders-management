package org.stefan.tuc.start;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ReflectionExample {

//    public static <T> String[][] getTableContent(List<T> elements){
//        for(T element: elements){
//            // extrac field values
//        }
//    }
//
//    public static <T> String[] getTableHeader(List<T> elements){
//        // extract field names
//    }

    public static void retrieveProperties(Object object) {
        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Object value;

            try {
                value = field.get(object);
                System.out.println(field.getName() + "=" + value);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public static List<Object> retrievePropertiesAsList(Object object) {
        List<Object> propertyList = new ArrayList<>();

        for (Field field : object.getClass().getDeclaredFields()) {
            if (!isFieldAccessible(field)) {
                continue;
            }

            field.setAccessible(true);
            Object value;

            try {
                value = field.get(object);
                propertyList.add(value);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return propertyList;
    }

    private static boolean isFieldAccessible(Field field) {
        int modifiers = field.getModifiers();
        return !(java.lang.reflect.Modifier.isStatic(modifiers) && java.lang.reflect.Modifier.isFinal(modifiers));
    }
}
