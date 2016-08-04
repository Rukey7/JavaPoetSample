package com.example.processor;

import com.example.annotation.ControlFlow;
import com.example.processor.utils.MethodUtils;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.LinkedHashSet;
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
public class ControlFlowProcessor extends AbstractProcessor {

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
        for (Element element : roundEnv.getElementsAnnotatedWith(ControlFlow.class)) {
            if (element.getKind() == ElementKind.CLASS) {
                MethodSpec flowOne = MethodSpec.methodBuilder("flowOne")
                        .addModifiers(Modifier.PROTECTED)
                        .addCode(""
                                + "int total = 0;\n"
                                + "for (int i = 0; i < 10; i++) {\n"
                                + "  total += i;\n"
                                + "}\n")
                        .build();

                MethodSpec flowTwo = MethodSpec.methodBuilder("flowTwo")
                        .addModifiers(Modifier.PRIVATE, Modifier.STATIC)
                        .addStatement("int total = 0")
                        .beginControlFlow("for (int i = 0; i < 10; i++)")
                        .addStatement("total += i")
                        .endControlFlow()
                        .build();

                MethodSpec flowThree = MethodUtils.computeRange("flowThree", 0, 15, "*");

                TypeSpec controlFlow = TypeSpec.classBuilder("ControlFlow")
                        .addModifiers(Modifier.PUBLIC)
                        .addMethod(flowOne)
                        .addMethod(flowTwo)
                        .addMethod(flowThree)
                        .build();

                try {
                    JavaFile.builder("com.example", controlFlow)
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
        annotations.add(ControlFlow.class.getCanonicalName());
        return annotations;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
}
