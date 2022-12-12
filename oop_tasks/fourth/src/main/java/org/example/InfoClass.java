package org.example;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.stream.Collectors;

public class InfoClass {
    private final String classPath;
    private final Class<?> loadedClass;
    private static final String SEPARATOR = "   ";

    public InfoClass(String classPath) {
        CustomClassLoader myLoader = new CustomClassLoader();
        this.classPath = classPath;
        this.loadedClass = myLoader.findClass(classPath);
    }

    public void showClassInfo(){
        System.out.println("The class is from " + classPath);
        showMainInfo();
        showInterfaces();
        showFields();
        showMethods();
        System.out.println("--------------------------------------------------");
    }

    private void showMainInfo() {
        System.out.println("The modifiers of the class: " + Modifier.toString(loadedClass.getModifiers()));
        if(loadedClass.getSuperclass() != null){
            System.out.println("It extends: " + loadedClass.getSuperclass().getName());
        } else {
            System.out.println("It doesn't extend any class.");
        }
    }

    private void showInterfaces() {
        Class<?>[] interfaceClasses = loadedClass.getInterfaces();
        if(interfaceClasses.length == 0){
            System.out.println("It does not implement any interfaces.");
        }
        else{
            System.out.println("It implements these interfaces: ");
            for(Class<?> interfaceClass: interfaceClasses){
                System.out.println(SEPARATOR + Modifier.toString(interfaceClass.getModifiers()) + " " +
                        interfaceClass.getName());
            }
        }
    }

    private void showFields(){
        Field[] listOfFields = loadedClass.getDeclaredFields();
        if(listOfFields.length == 0){
            System.out.println("No fields in this class. ");
            return;
        } else{
            System.out.println("Fields of this class: ");
        }
        for(Field field: listOfFields){
            System.out.println(SEPARATOR + Modifier.toString(field.getModifiers()) + " " +
                    field.getType().getName() + " " + field.getName());
        }
    }

    private void showMethods(){
        Method[] listOfMethods = loadedClass.getDeclaredMethods();
        if(listOfMethods.length == 0){
            System.out.println("No methods in this class. ");
            return;
        } else{
            System.out.println("Methods of this class: ");
        }
        for(Method method: listOfMethods){
            System.out.println(SEPARATOR + Modifier.toString(method.getModifiers()) + " " +
                    method.getReturnType().getName() + " " + method.getName());
            if(method.getParameterTypes().length > 0) {
                System.out.println(SEPARATOR + "parameter types: " +
                        Arrays.stream(method.getParameterTypes())
                                .map(Class::getName)
                                .collect(Collectors.joining(",")));
            } else {
                System.out.println(SEPARATOR + SEPARATOR+ "no parameters");
            }
        }
    }
}
