/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.utilities;

import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 *
 * @author filip
 */
public class ReflectionUtils {

    public static void readClassAndMembersInfo(Class<?> clazz, StringBuilder classAndMembersInfo) {

        readClassInfo(clazz, classAndMembersInfo);
        appendFields(clazz, classAndMembersInfo);
        appendMethods(clazz, classAndMembersInfo);
        appendConstructors(clazz, classAndMembersInfo);
    }

    public static void readClassInfo(Class<?> clazz, StringBuilder classInfo) {

        classInfo.append("<h3>");

        appendModifiers(clazz, classInfo);
        classInfo.append(" ").append(" class ").append(" ").append(clazz.getSimpleName());

        appendInterfaces(clazz, classInfo);
        classInfo.append("");

        classInfo.append("</h3>");
    }

    public static void appendPackage(Class<?> clazz, StringBuilder classInfo) {
        classInfo
                .append(clazz.getPackage())
                .append("\n\n");
    }

    public static void appendModifiers(Class<?> clazz, StringBuilder classInfo) {

        classInfo.append(Modifier.toString(clazz.getModifiers()));
    }

    public static void appendInterfaces(Class<?> clazz, StringBuilder classInfo) {
        if (clazz.getInterfaces().length > 0) {
            classInfo.append(" implements ");
            classInfo.append(
                    Arrays.stream(clazz.getInterfaces())
                            .map(Class::getName)
                            .collect(Collectors.joining(" "))
            );
        }
    }

    public static void appendFields(Class<?> clazz, StringBuilder classAndMembersInfo) {
        classAndMembersInfo.append("<li>FIELDS:<ul>");
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            classAndMembersInfo.append("<li>");
            classAndMembersInfo.append(field.toString()).append("\n");
            classAndMembersInfo.append("</li>");
        }

        classAndMembersInfo.append("<br/></ul></li>");
    }

    public static void appendMethods(Class<?> clazz, StringBuilder classAndMembersInfo) {
        classAndMembersInfo.append("<li>METHODS:<ul>");
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            classAndMembersInfo.append("<li>");
            classAndMembersInfo
                    .append("\n")
                    .append(Modifier.toString(method.getModifiers()))
                    .append(" ")
                    .append(method.getReturnType())
                    .append(" ")
                    .append(method.getName());
            appendParameters(method, classAndMembersInfo);
            appendExceptions(method, classAndMembersInfo);
            classAndMembersInfo.append("</li>");
        }
        classAndMembersInfo.append("<br/></ul></li>");
    }

    public static void appendParameters(Executable executable, StringBuilder classAndMembersInfo) {
        classAndMembersInfo.append(
                Arrays.stream(executable.getParameters())
                        .map(Objects::toString)
                        .collect(Collectors.joining(", ", "(", ")"))
        );
    }

    public static void appendExceptions(Executable executable, StringBuilder classAndMembersInfo) {
        if (executable.getExceptionTypes().length > 0) {
            classAndMembersInfo.append(" throws ");
            classAndMembersInfo.append(
                    Arrays.stream(executable.getExceptionTypes())
                            .map(Class::getName)
                            .collect(Collectors.joining(" "))
            );
        }
    }

    public static void appendConstructors(Class<?> clazz, StringBuilder classAndMembersInfo) {
        classAndMembersInfo.append("<li>CONSTRUCTORS:<ul>");
        Constructor[] constructors = clazz.getDeclaredConstructors();
        for (Constructor constructor : constructors) {
            classAndMembersInfo.append("<li>");
            classAndMembersInfo
                    .append(Modifier.toString(constructor.getModifiers()))
                    .append(" ")
                    .append(constructor.getName());
            appendParameters(constructor, classAndMembersInfo);
            appendExceptions(constructor, classAndMembersInfo);
            classAndMembersInfo.append("</li>");
        }
        classAndMembersInfo.append("<br/></ul></li>");
    }
}
