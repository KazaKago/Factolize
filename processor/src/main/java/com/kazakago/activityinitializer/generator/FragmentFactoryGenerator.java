package com.kazakago.activityinitializer.generator;

import com.kazakago.activityinitializer.FactoryParam;
import com.kazakago.activityinitializer.constants.Annotations;
import com.kazakago.activityinitializer.constants.BundleTypes;
import com.kazakago.activityinitializer.constants.Types;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.Arrays;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;

/**
 * Fragment factory class generator.
 * <p>
 * Created by KazaKago on 2017/03/16.
 */
public class FragmentFactoryGenerator {

    private ProcessingEnvironment processingEnv;

    public FragmentFactoryGenerator(ProcessingEnvironment processingEnv) {
        this.processingEnv = processingEnv;
    }

    public void execute(Element element) throws IOException {
        String packageName = processingEnv.getElementUtils().getPackageOf(element).getQualifiedName().toString();
        ClassName modelClassName = ClassName.get(packageName, element.getSimpleName().toString());
        ClassName generatedClassName = ClassName.get(packageName, element.getSimpleName().toString() + "Factory");

        MethodSpec constructor = generateConstructor();
        MethodSpec createIntentMethod = generateCreateIntentMethod(element, modelClassName);

        TypeSpec generatedClass = TypeSpec.classBuilder(generatedClassName)
                .addModifiers(Modifier.PUBLIC)
                .addMethod(constructor)
                .addMethod(createIntentMethod)
                .build();

        JavaFile.builder(packageName, generatedClass)
                .build()
                .writeTo(processingEnv.getFiler());
    }

    private MethodSpec generateConstructor() {
        return MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PRIVATE)
                .build();
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
                BundleTypes bundleTypes = BundleTypes.resolve(processingEnv,  el.asType());
                if (bundleTypes != null) {
                    TypeName fieldType = TypeName.get(el.asType());
                    String fieldName = el.getSimpleName().toString();
                    ParameterSpec.Builder paramBuilder = ParameterSpec.builder(fieldType, fieldName);
                    if (!fieldType.isPrimitive()) {
                        paramBuilder.addAnnotation(Annotations.NonNull);
                    }
                    methodBuilder.addParameter(paramBuilder.build())
                            .addAnnotations(Arrays.asList(bundleTypes.annotations))
                            .addStatement("arguments.$L($S, $L)", bundleTypes.putMethodName, fieldName, fieldName);
                }
            }
        }
        return methodBuilder.addStatement("fragment.setArguments(arguments)")
                .addStatement("return fragment")
                .returns(modelClassName)
                .build();
    }

}
