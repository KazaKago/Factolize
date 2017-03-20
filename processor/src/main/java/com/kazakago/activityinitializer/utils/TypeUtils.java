package com.kazakago.activityinitializer.utils;

import com.squareup.javapoet.ArrayTypeName;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

/**
 * Created by Kensuke on 2017/03/18.
 */
public class TypeUtils {

    public static boolean isContainType(ProcessingEnvironment processingEnv, TypeMirror targetType, TypeName compareType) {
        if (compareType instanceof ParameterizedTypeName) {
            return isContainParameterizedType(processingEnv, targetType, (ParameterizedTypeName) compareType);
        } else if (compareType instanceof ClassName) {
            return isContainClass(processingEnv, targetType, (ClassName) compareType);
        } else if (compareType instanceof ArrayTypeName) {
            return isArrayType(processingEnv, targetType, (ArrayTypeName) compareType);
        } else {
            return isType(processingEnv, targetType, compareType);
        }
    }

    private static boolean isContainParameterizedType(ProcessingEnvironment processingEnv, TypeMirror targetType, ParameterizedTypeName compareParameterizedTypeName) {
        TypeElement firstType = processingEnv.getElementUtils().getTypeElement(compareParameterizedTypeName.rawType.reflectionName());
        TypeMirror intentTypeMirror;
        if (!compareParameterizedTypeName.typeArguments.isEmpty()) {
            TypeMirror secondType = processingEnv.getElementUtils().getTypeElement(((ClassName) compareParameterizedTypeName.typeArguments.get(0)).reflectionName()).asType();
            intentTypeMirror = processingEnv.getTypeUtils().getDeclaredType(firstType, secondType);
        } else {
            intentTypeMirror = firstType.asType();
        }
        return processingEnv.getTypeUtils().isAssignable(targetType, intentTypeMirror);
    }

    private static boolean isContainClass(ProcessingEnvironment processingEnv, TypeMirror targetType, ClassName compareClass) {
        TypeMirror intentTypeMirror = processingEnv.getElementUtils().getTypeElement(compareClass.reflectionName()).asType();
        return processingEnv.getTypeUtils().isAssignable(targetType, intentTypeMirror);
    }

    private static boolean isArrayType(ProcessingEnvironment processingEnv, TypeMirror targetType, ArrayTypeName compareArrayTypeName) {
        TypeMirror componentTypeMirror;
        if (compareArrayTypeName.componentType.isPrimitive()) {
            TypeMirror componentBoxedTypeMirror = processingEnv.getElementUtils().getTypeElement(compareArrayTypeName.componentType.box().toString()).asType();
            componentTypeMirror = processingEnv.getTypeUtils().unboxedType(componentBoxedTypeMirror);
        } else {
            componentTypeMirror = processingEnv.getElementUtils().getTypeElement(compareArrayTypeName.componentType.toString()).asType();
        }
        TypeMirror intentTypeMirror = processingEnv.getTypeUtils().getArrayType(componentTypeMirror);
        return processingEnv.getTypeUtils().isAssignable(targetType, intentTypeMirror);
    }

    private static boolean isType(ProcessingEnvironment processingEnv, TypeMirror targetType, TypeName compareTypeName) {
        TypeMirror intentBoxedTypeMirror = processingEnv.getElementUtils().getTypeElement(compareTypeName.box().toString()).asType();
        TypeMirror intentTypeMirror;
        if (compareTypeName.isPrimitive()) {
            intentTypeMirror = processingEnv.getTypeUtils().unboxedType(intentBoxedTypeMirror);
        } else {
            intentTypeMirror = intentBoxedTypeMirror;
        }
        return processingEnv.getTypeUtils().isAssignable(targetType, intentTypeMirror);
    }
}
