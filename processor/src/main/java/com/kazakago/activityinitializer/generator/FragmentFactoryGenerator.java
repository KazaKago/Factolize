package com.kazakago.activityinitializer.generator;

import com.kazakago.activityinitializer.FactoryParam;
import com.kazakago.activityinitializer.constants.Annotations;
import com.kazakago.activityinitializer.constants.Types;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.Set;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.util.Elements;

/**
 * Fragment factory class generator.
 * <p>
 * Created by KazaKago on 2017/03/16.
 */
public class FragmentFactoryGenerator {

    private Filer filer;
    private Elements elements;

    public FragmentFactoryGenerator(Filer filer, Elements elements) {
        this.filer = filer;
        this.elements = elements;
    }

    public void execute(Element element) throws IOException {
        String packageName = elements.getPackageOf(element).getQualifiedName().toString();
        ClassName modelClassName = ClassName.get(packageName, element.getSimpleName().toString());
        ClassName generatedClassName = ClassName.get(packageName, element.getSimpleName().toString() + "Factory");

        MethodSpec createIntentMethod = generateCreateIntentMethod(element, modelClassName);

        TypeSpec generatedClass = TypeSpec.classBuilder(generatedClassName)
                .addModifiers(Modifier.PUBLIC)
                .addMethod(createIntentMethod)
                .build();

        JavaFile.builder(packageName, generatedClass)
                .build()
                .writeTo(filer);
    }

    private MethodSpec generateCreateIntentMethod(Element element, ClassName modelClassName) {
        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("createInstance")
                .addModifiers(Modifier.PUBLIC)
                .addModifiers(Modifier.STATIC)
                .addAnnotation(Annotations.NonNull)
                .addStatement("$T fragment = new $T()", modelClassName, modelClassName)
                .addStatement("$T arguments = new $T()", Types.Bundle, Types.Bundle);
        for (Element el : element.getEnclosedElements()) {
            if (el.getAnnotation(FactoryParam.class) != null) {
                TypeName fieldType = TypeName.get(el.asType());
                String fieldName = el.getSimpleName().toString();
                String methodNameParam;
                if (fieldType.equals(TypeName.INT)) {
                    fieldType = TypeName.INT;
                    methodNameParam = "Int";
                } else if (fieldType.equals(TypeName.INT.box())) {
                    fieldType = TypeName.INT.box();
                    methodNameParam = "Int";
                } else if (fieldType.equals(TypeName.LONG)) {
                    fieldType = TypeName.LONG;
                    methodNameParam = "Long";
                } else if (fieldType.equals(TypeName.LONG.box())) {
                    fieldType = TypeName.LONG.box();
                    methodNameParam = "Long";
                } else if (fieldType.equals(TypeName.FLOAT)) {
                    fieldType = TypeName.FLOAT;
                    methodNameParam = "Float";
                } else if (fieldType.equals(TypeName.FLOAT.box())) {
                    fieldType = TypeName.FLOAT.box();
                    methodNameParam = "Float";
                } else if (fieldType.equals(TypeName.BOOLEAN)) {
                    fieldType = TypeName.BOOLEAN;
                    methodNameParam = "Boolean";
                } else if (fieldType.equals(TypeName.BOOLEAN.box())) {
                    fieldType = TypeName.BOOLEAN.box();
                    methodNameParam = "Boolean";
                } else if (fieldType.equals(TypeName.get(String.class))) {
                    fieldType = TypeName.get(String.class);
                    methodNameParam = "String";
                } else if (fieldType.getClass().isInstance(ParameterizedTypeName.get(Set.class, String.class))) {
                    fieldType = ParameterizedTypeName.get(Set.class, String.class);
                    methodNameParam = "StringSet";
                } else {
                    methodNameParam = "Serializable";
                }
                ParameterSpec.Builder paramBuilder = ParameterSpec.builder(fieldType, fieldName);
                if (!fieldType.isPrimitive()) {
                    paramBuilder.addAnnotation(Annotations.NonNull);
                }
                methodBuilder.addParameter(paramBuilder.build())
                        .addStatement("arguments.put$L($S, $L)", methodNameParam, fieldName, fieldName);
            }
        }

        return methodBuilder.addStatement("fragment.setArguments(arguments)")
                .addStatement("return fragment")
                .returns(modelClassName)
                .build();
    }

}
