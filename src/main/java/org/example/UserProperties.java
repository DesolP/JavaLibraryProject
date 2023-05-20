package org.example;

import com.mysql.cj.xdevapi.Column;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class UserProperties {
    public void help(Object obj){
        Method[] methods = obj.getClass().getDeclaredMethods();
        MethodDescriptor methodDescriptor;
         for(Method method : methods){
             try {
                 methodDescriptor = method.getAnnotation(MethodDescriptor.class);
                 System.out.println("Type " + method.getName() + " " + methodDescriptor.methodDescription());
             }catch (NullPointerException e){

             }
        }
    }
}
