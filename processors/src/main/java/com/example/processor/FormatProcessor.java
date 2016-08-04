package com.example.processor;

import com.example.annotation.FormatExam;
import com.example.processor.utils.MethodUtils;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.Date;
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
public class FormatProcessor extends AbstractProcessor {

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
        for (Element element : roundEnv.getElementsAnnotatedWith(FormatExam.class)) {
            if (element.getKind() == ElementKind.CLASS) {

                MethodSpec range = MethodUtils.computeRange2("range", 0, 100, "-");
                MethodSpec goodJob = MethodUtils.whatMyName("goodJob");

                MethodSpec formatClass = MethodSpec.methodBuilder("formatClass")
                        .returns(Date.class)
                        .addStatement("return new $T()", Date.class)
                        .build();

                ClassName list = ClassName.get("java.util", "List");
                ClassName arrayList = ClassName.get("java.util", "ArrayList");
                ClassName date = ClassName.get("java.util", "Date");
                TypeName typeName = ParameterizedTypeName.get(list, date);
                MethodSpec formatList = MethodSpec.methodBuilder("formatList")
                        .returns(typeName)
                        .addStatement("$T result = new $T<>()", typeName, arrayList)
                        .addStatement("result.add(new $T())", date)
                        .addStatement("result.add(new $T())", date)
                        .addStatement("result.add(new $T())", date)
                        .addStatement("return result")
                        .build();

                MethodSpec hexDigit = MethodSpec.methodBuilder("hexDigit")
                        .addParameter(int.class, "i")
                        .returns(char.class)
                        .addStatement("return (char) (i < 10 ? i + '0' : i - 10 + 'a')")
                        .build();

                MethodSpec byteToHex = MethodSpec.methodBuilder("byteToHex")
                        .addParameter(int.class, "b")
                        .returns(String.class)
                        .addStatement("char[] result = new char[2]")
                        .addStatement("result[0] = $N((b >>> 4) & 0xf)", hexDigit)
                        .addStatement("result[1] = $N(b & 0xf)", hexDigit)
                        .addStatement("return new String(result)")
                        .build();

                TypeSpec formatExam = TypeSpec.classBuilder("FormatExam")
                        .addModifiers(Modifier.PUBLIC)
                        .addMethod(range)
                        .addMethod(goodJob)
                        .addMethod(formatClass)
                        .addMethod(formatList)
                        .addMethod(hexDigit)
                        .addMethod(byteToHex)
                        .build();
                try {
                    JavaFile.builder("com.example", formatExam)
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
        annotations.add(FormatExam.class.getCanonicalName());
        return annotations;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
}
