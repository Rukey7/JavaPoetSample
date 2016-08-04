package com.example.processor.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.annotation.Annotation;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.tools.Diagnostic;

/**
 * Created by long on 2016/8/4.
 * Log工具类
 */
public class LogUtils {

    private Messager mMessager;

    public LogUtils(Messager messager) {
        this.mMessager = messager;
    }


    /**
     * 输出异常
     * @param element
     * @param annotation
     * @param e
     */
    public void logError(Element element, Class<? extends Annotation> annotation, Exception e) {
        StringWriter stackTrace = new StringWriter();
        e.printStackTrace(new PrintWriter(stackTrace));
        error(element, "Unable to parse @%s binding.\n\n%s", annotation.getSimpleName(), stackTrace);
    }

    private void error(Element element, String message, Object... args) {
        if (args.length > 0) {
            message = String.format(message, args);
        }
        mMessager.printMessage(Diagnostic.Kind.ERROR, message, element);
    }
}
