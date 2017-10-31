package com.decom.chunks.annotation;

/**
 * Created by Administrator on 2017/10/16.
 */

import java.lang.annotation.*;

/**
 * 元注解，jkd 5.0带有4个标准元注解
 * 1.@Target
 * 指注解的应用范围
 * 取值(ElementType)有：
 * 　　　　1.CONSTRUCTOR:用于描述构造器
 * 　　　　1.CONSTRUCTOR:用于描述构造器
 * 　　　　2.FIELD:用于描述域
 * 　　　　3.LOCAL_VARIABLE:用于描述局部变量
 * 　　　　4.METHOD:用于描述方法
 * 　　　　5.PACKAGE:用于描述包
 * 　　　　6.PARAMETER:用于描述参数
 * 　　　　7.TYPE:用于描述类、接口(包括注解类型) 或enum声明
 *
 * 2.@Retention
 * 指注解的生命期
 * 取值（RetentionPoicy）有：
 * 　　　　1.SOURCE:在源文件中有效（即源文件保留）
 * 　　　　2.CLASS:在class文件中有效（即class保留）
 * 　　　　3.RUNTIME:在运行时有效（即运行时保留）
 *
 * 3.@Documented
 * 指注解是否可以有文档化
 * Documented是一个标记注解，没有成员；
 * 标记是否可被javadoc此类的工具文档化
 *
 * 4.@Inherited
 * 定义该注释和子类的关系，即是否允许子类继承该注解
 *
 * 自定义注解类编写的一些规则:
 * 1. Annotation型定义为@interface, 所有的Annotation会自动继承java.lang.Annotation这一接口,并且不能再去继承别的类或是接口.
 * 定义注解格式：
 * 　public @interface 注解名 {定义体}
 * 2. 参数成员只能用public或默认(default)这两个访问权修饰
 * 3. 参数成员只能用基本类型byte,short,char,int,long,float,double,boolean八种基本数据类型和String、Enum、Class、
 *    Annotation等数据类型,以及这一些类型的数组.
 * 4. 要获取类方法和字段的注解信息，必须通过Java的反射技术来获取 Annotation对象,因为你除此之外没有别的获取注解对象的方法
 * 5. 注解也可以没有定义成员, 不过这样注解就没啥用了
 * PS:自定义注解需要使用到元注解
 *
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface CustomAnnotation {
    public String author() default "micropc";
    String date();
    int version() default 1;
    String comments();
}
