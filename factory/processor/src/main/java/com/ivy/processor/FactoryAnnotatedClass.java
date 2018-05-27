package com.ivy.processor;

import com.ivy.annotation.Factory;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.MirroredTypeException;

/**
 * Function: com.ivy.processor
 * Holds the information about a class annotated with @Factory
 *
 * @author: fangjie07
 * @date: 2018/5/27
 */
public class FactoryAnnotatedClass {
    /**
     * 加factory注解的类
     */
    private TypeElement annotatedClassElement;
    /**
     * 类的全限定名
     */
    private String qualifiedGroupClassName;
    /**
     * 类的简单名字
     */
    private String simpleFactoryGroupName;
    /**
     * factory的唯一标识
     */
    private String id;

    /**
     * @throws ProcessingException if id() from annotation is null
     */
    public FactoryAnnotatedClass(TypeElement classElement) throws ProcessingException {
        this.annotatedClassElement = classElement;
        Factory annotation = classElement.getAnnotation(Factory.class);
        id = annotation.id();

        if (id == null) {
            throw new ProcessingException(classElement,
                    "id() in @%s for class %s is null or empty! that's not allowed",
                    Factory.class.getSimpleName(), classElement.getQualifiedName().toString());
        }

        // Get the full QualifiedTypeName
        try {
            Class<?> clazz = annotation.type();
            qualifiedGroupClassName = clazz.getCanonicalName();
            simpleFactoryGroupName = clazz.getSimpleName();
        } catch (MirroredTypeException mte) {
            DeclaredType classTypeMirror = (DeclaredType) mte.getTypeMirror();
            TypeElement classTypeElement = (TypeElement) classTypeMirror.asElement();
            qualifiedGroupClassName = classTypeElement.getQualifiedName().toString();
            simpleFactoryGroupName = classTypeElement.getSimpleName().toString();
        }
    }


    public String getId() {
        return id;
    }


    public String getQualifiedFactoryGroupName() {
        return qualifiedGroupClassName;
    }


    public String getSimpleFactoryGroupName() {
        return simpleFactoryGroupName;
    }

    /**
     * The original element that was annotated with @Factory
     */
    public TypeElement getTypeElement() {
        return annotatedClassElement;
    }
}
