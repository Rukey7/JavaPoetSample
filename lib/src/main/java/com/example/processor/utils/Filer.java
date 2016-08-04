package com.example.processor.utils;

import java.io.IOException;

import javax.lang.model.element.Element;
import javax.tools.FileObject;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;

/**
 * 此接口支持通过注解处理器创建新文件.
 */
public interface Filer {
    /**
     * 创建一个新的源文件，并返回一个对象以允许写入它.
     *
     * @param name  将在此文件中声明的主要类型的规范（完全限定）名称；对于包信息文件，则是后跟 &quot;.package-info&quot; 的包名称
     * @param originatingElements 与此文件的创建有因果关联的类型或包元素，该参数可以省略或者为 null
     * @return 写入新源文件的 JavaFileObject
     * @throws FilerException 如果创建了相同的路径名，创建了相同的类型，或者名称对于某一类型无效
     * @throws IOException 如果无法创建文件
     */
    JavaFileObject createSourceFile(CharSequence name,
                                    Element... originatingElements) throws IOException;

    /**
     * 创建一个新的类文件，并返回一个对象以允许写入它.
     *
     * @param name 将写入的类型的二进制名称；对于包信息文件，是后跟 &quot;.package-info&quot; 的包名称
     * @param originatingElements 与此文件的创建有因果关联的类型或包元素，该参数可以省略或者为 null
     * @return 写入新类文件的 JavaFileObject
     * @throws FilerException 如果创建了相同的路径名，创建了相同的类型，或者名称对于某一类型无效
     * @throws IOException 如果无法创建文件
     */
    JavaFileObject createClassFile(CharSequence name,
                                   Element... originatingElements) throws IOException;

    /**
     * 创建一个用于写入操作的新辅助资源文件，并为它返回一个文件对象。
     * 该文件可以与新创建的源文件、新创建的二进制文件或者其他受支持的位置一起被查找。
     * 位置 CLASS_OUTPUT 和 SOURCE_OUTPUT 必须受支持。
     * 资源可以是相对于某个包（该包是源文件和类文件）指定的，并通过相对路径名从中取出。
     * 从不太严格的角度说，新文件的完全路径名将是 location、pkg 和 relativeName 的串联.
     *
     * <p>Files created via this method are not registered for
     * annotation processing, even if the full pathname of the file
     * would correspond to the full pathname of a new source file
     * or new class file.
     *
     * @param location 新文件的位置
     * @param pkg 文件将相对于其进行指定的包；如果没有这样的包，则该参数是一个空字符串
     * @param relativeName 文件的最终路径名部分
     * @param originatingElements 与此文件的创建有因果关联的类型或包元素，该参数可以忽略或者为 null
     * @return 写入新资源的 FileObject
     * @throws IOException 如果无法创建文件
     * @throws FilerException 如果创建了相同的路径名
     * @throws IllegalArgumentException 如果位置不受支持
     * @throws IllegalArgumentException 如果 relativeName 不是相对名称
     */
    FileObject createResource(JavaFileManager.Location location,
                              CharSequence pkg,
                              CharSequence relativeName,
                              Element... originatingElements) throws IOException;

    /**
     * 返回一个用于读取现有资源的对象。位置 CLASS_OUTPUT 和 SOURCE_OUTPUT 必须受支持.
     *
     * @param location 新文件的位置
     * @param pkg 文件将相对于其进行指定的包；如果没有这样的包，则该参数是一个空字符串
     * @param relativeName 文件的最终路径名部分
     * @return 读取文件的对象
     * @throws IOException 如果无法创建文件
     * @throws FilerException 如果创建了相同的路径名
     * @throws IllegalArgumentException 如果位置不受支持
     * @throws IllegalArgumentException 如果 relativeName 不是相对名称
     */
    FileObject getResource(JavaFileManager.Location location,
                           CharSequence pkg,
                           CharSequence relativeName) throws IOException;
}
