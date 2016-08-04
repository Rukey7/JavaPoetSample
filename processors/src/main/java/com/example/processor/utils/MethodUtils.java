package com.example.processor.utils;

import com.squareup.javapoet.MethodSpec;

import javax.lang.model.element.Modifier;

/**
 * Created by long on 2016/8/3.
 */
public class MethodUtils {

    private MethodUtils() {
    }


    public static MethodSpec computeRange(String name, int from, int to, String op) {
        return MethodSpec.methodBuilder(name)
                .returns(int.class)
                .addStatement("int result = 0")
                .beginControlFlow("for (int i = " + from + "; i < " + to + "; i++)")
                .addStatement("result = result " + op + " i")
                .endControlFlow()
                .addStatement("return result")
                .build();
    }

    // $L for Literals
    public static MethodSpec computeRange2(String name, int from, int to, String op) {
        return MethodSpec.methodBuilder(name)
                .returns(int.class)
                .addStatement("int result = 0")
                .beginControlFlow("for (int i = $L; i < $L; i++)", from, to)
                .addStatement("result = result $L i", op)
                .endControlFlow()
                .addStatement("return result")
                .build();
    }

    public static MethodSpec whatMyName(String name) {
        return MethodSpec.methodBuilder(name)
                .addModifiers(Modifier.PUBLIC)
                .returns(String.class)
                .addStatement("return $S", name)
                .build();
    }
}
