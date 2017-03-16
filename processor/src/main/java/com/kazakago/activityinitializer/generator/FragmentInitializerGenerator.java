package com.kazakago.activityinitializer.generator;

import com.kazakago.activityinitializer.constants.Annotations;
import com.kazakago.activityinitializer.constants.Types;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.util.Elements;

/**
 * Preferences management class generator.
 * <p>
 * Created by KazaKago on 2017/03/16.
 */
public class FragmentInitializerGenerator {

    private Filer filer;
    private Elements elements;

    public FragmentInitializerGenerator(Filer filer, Elements elements) {
        this.filer = filer;
        this.elements = elements;
    }

    public void execute(Element element) throws IOException {
        String packageName = elements.getPackageOf(element).getQualifiedName().toString();
        ClassName modelClassName = ClassName.get(packageName, element.getSimpleName().toString());
        ClassName generatedClassName = ClassName.get(packageName, element.getSimpleName().toString() + "Builder");

        MethodSpec createIntentMethod = generateCreateIntentMethod(modelClassName);

        TypeSpec generatedClass = TypeSpec.classBuilder(generatedClassName)
                .addModifiers(Modifier.PUBLIC)
                .addMethod(createIntentMethod)
                .build();

        JavaFile.builder(packageName, generatedClass)
                .build()
                .writeTo(filer);
    }

    private MethodSpec generateCreateIntentMethod(ClassName modelClassName) {
        return MethodSpec.methodBuilder("createInstance")
                .addModifiers(Modifier.PUBLIC)
                .addModifiers(Modifier.STATIC)
                .addAnnotation(Annotations.NonNull)
                .addStatement("$T fragment = new $T()", modelClassName, modelClassName)
                .addStatement("$T arguments = new $T()", Types.Bundle, Types.Bundle)
                .addStatement("fragment.setArguments(arguments)")
                .addStatement("return fragment")
                .returns(modelClassName)
                .build();
    }

}
