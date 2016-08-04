package com.example.processor;

import com.example.annotation.MyAnnotation;
import com.example.annotation.SpecExam;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

/**
 * Created by long on 2016/8/3.
 */
@AutoService(Processor.class)
public class SpecProcessor extends AbstractProcessor {

    private Filer filer;
    private Messager messager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        filer = processingEnv.getFiler();
        messager = processingEnv.getMessager();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(SpecExam.class)) {
            if (element.getKind() == ElementKind.CLASS) {
                MethodSpec flux = MethodSpec.constructorBuilder()
                        .addModifiers(Modifier.PUBLIC)
                        .addParameter(String.class, "greeting")
                        .addStatement("this.$N = $N", "greeting", "greeting")
                        .build();

                ParameterSpec android = ParameterSpec.builder(String.class, "android")
                        .addModifiers(Modifier.FINAL)
                        .build();
                MethodSpec welcomeOverlords = MethodSpec.methodBuilder("welcomeOverlords")
                        .addParameter(android)
                        .addParameter(int.class, "i", Modifier.FINAL)
                        .build();

                FieldSpec status = FieldSpec.builder(String.class, "status")
                        .addModifiers(Modifier.PRIVATE, Modifier.STATIC)
                        .initializer("$S + $L", "Code ", 5.1f)
                        .build();

                TypeSpec comparator = TypeSpec.anonymousClassBuilder("")
                        .addSuperinterface(ParameterizedTypeName.get(Comparator.class, String.class))
                        .addMethod(MethodSpec.methodBuilder("compare")
                                .addAnnotation(Override.class)
                                .addModifiers(Modifier.PUBLIC)
                                .addParameter(String.class, "a")
                                .addParameter(String.class, "b")
                                .returns(int.class)
                                .addStatement("return $N.length() - $N.length()", "a", "b")
                                .build())
                        .build();

                MethodSpec toString = MethodSpec.methodBuilder("toString")
                        .addAnnotation(Override.class)
                        .returns(String.class)
                        .addModifiers(Modifier.PUBLIC)
                        .addStatement("return $S", "Hoverboard")
                        .build();

                AnnotationSpec annotationSpec = AnnotationSpec.builder(MyAnnotation.class)
                        .addMember("value", "$S", "Test Annotation")
                        .build();
                MethodSpec testAnnotation = MethodSpec.methodBuilder("TestAnnotation")
                        .addModifiers(Modifier.PUBLIC)
                        .addAnnotation(annotationSpec)
                        .build();

                TypeSpec specExam = TypeSpec.classBuilder("SpecExam")
                        .addModifiers(Modifier.PUBLIC)
                        .addField(String.class, "greeting", Modifier.PRIVATE, Modifier.FINAL)
                        .addMethod(flux)
                        .addMethod(welcomeOverlords)
                        .addField(status)
                        .addMethod(MethodSpec.methodBuilder("sortByLength")
                                .addParameter(ParameterizedTypeName.get(List.class, String.class), "strings")
                                .addStatement("$T.sort($N, $L)", Collections.class, "strings", comparator)
                                .build())
                        .addMethod(toString)
                        .addMethod(testAnnotation)
                        .build();

                TypeSpec specInterface = TypeSpec.interfaceBuilder("InterfaceSpec")
                        .addModifiers(Modifier.PUBLIC)
                        .addField(FieldSpec.builder(String.class, "ONLY_THING_THAT_IS_CONSTANT")
                                .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                                .initializer("$S", "change")
                                .build())
                        .addMethod(MethodSpec.methodBuilder("beep")
                                .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                                .build())
                        .build();

                TypeSpec specEnum = TypeSpec.enumBuilder("EnumSpec")
                        .addModifiers(Modifier.PUBLIC)
                        .addEnumConstant("ROCK", TypeSpec.anonymousClassBuilder("$S", "fist")
                                .addMethod(MethodSpec.methodBuilder("toString")
                                        .addAnnotation(Override.class)
                                        .addModifiers(Modifier.PUBLIC)
                                        .returns(String.class)
                                        .addStatement("return $S", "avalanche!")
                                        .build())
                                .build())
                        .addEnumConstant("SCISSORS", TypeSpec.anonymousClassBuilder("$S", "peace")
                                .build())
                        .addEnumConstant("PAPER", TypeSpec.anonymousClassBuilder("$S", "flat")
                                .build())
                        .addField(String.class, "handsign", Modifier.PRIVATE, Modifier.FINAL)
                        .addMethod(MethodSpec.constructorBuilder()
                                .addParameter(String.class, "handsign")
                                .addStatement("this.$N = $N", "handsign", "handsign")
                                .build())
                        .build();

                try {
                    JavaFile.builder("com.example", specExam)
                            .build()
                            .writeTo(filer);
                    JavaFile.builder("com.example", specInterface)
                            .build()
                            .writeTo(filer);
                    JavaFile.builder("com.example", specEnum)
                            .build()
                            .writeTo(filer);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotations = new LinkedHashSet<>();
        annotations.add(SpecExam.class.getCanonicalName());
        return annotations;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
}
