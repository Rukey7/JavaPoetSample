package com.example.processor.utils;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.tools.Diagnostic;

/**
 * 提供注解处理器用来报告错误消息、警告和其他通知的方式.
 */
public interface Messager {
    /**
     * 打印指定种类的消息.
     *
     * @param kind 消息的种类：ERROR, WARNING, MANDATORY_WARNING, NOTE, OTHER
     * @param msg  消息；如果没有消息，则该参数是一个空字符串
     */
    void printMessage(Diagnostic.Kind kind, CharSequence msg);

    /**
     * 在元素的位置上打印指定种类的消息.
     *
     * @param kind 消息的种类：ERROR, WARNING, MANDATORY_WARNING, NOTE, OTHER
     * @param msg  消息；如果没有消息，则该参数是一个空字符串
     * @param e    用作位置提示的元素
     */
    void printMessage(Diagnostic.Kind kind, CharSequence msg, Element e);

    /**
     * 在已注解元素的注解镜像位置上打印指定种类的消息.
     *
     * @param kind 消息的种类：ERROR, WARNING, MANDATORY_WARNING, NOTE, OTHER
     * @param msg  消息；如果没有消息，则该参数是一个空字符串
     * @param e    已注解的元素
     * @param a    用作位置提示的注解
     */
    void printMessage(Diagnostic.Kind kind, CharSequence msg, Element e, AnnotationMirror a);

    /**
     * 在已注解元素的注解镜像内部注解值的位置上打印指定种类的消息.
     *
     * @param kind 消息的种类：ERROR, WARNING, MANDATORY_WARNING, NOTE, OTHER
     * @param msg  消息；如果没有消息，则该参数是一个空字符串
     * @param e    已注解的元素
     * @param a    用作位置提示的注解
     * @param v    用作位置提示的注解值
     */
    void printMessage(Diagnostic.Kind kind,
                      CharSequence msg,
                      Element e,
                      AnnotationMirror a,
                      AnnotationValue v);
}
