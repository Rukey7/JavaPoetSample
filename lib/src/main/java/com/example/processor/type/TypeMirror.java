package com.example.processor.type;

import javax.lang.model.type.TypeKind;

/**
 * Created by long on 2016/8/9.
 * 表示 Java 编程语言中的类型，这些类型包括基本类型、声明类型（类和接口类型）、数组类型、类型变量和 null 类型。
 * 还可以表示通配符类型参数、executable 的签名和返回类型，以及对应于包和关键字 void 的伪类型;
 * 子类实现：ArrayType, DeclaredType, ErrorType, ExecutableType, NoType, NullType,
 * PrimitiveType, ReferenceType, TypeVariable, WildcardType
 */
public interface TypeMirror {
    /**
     * 返回此类型的种类，一个 TypeKind 枚举值：
     */
    TypeKind getKind();
}
