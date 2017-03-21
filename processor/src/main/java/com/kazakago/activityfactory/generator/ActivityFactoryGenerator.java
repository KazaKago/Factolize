package com.kazakago.activityfactory.generator;

import com.kazakago.activityfactory.FactoryParam;
import com.kazakago.activityfactory.constants.Annotations;
import com.kazakago.activityfactory.constants.IntentTypes;
import com.kazakago.activityfactory.constants.Types;
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
 * Activity factory class generator.
 * <p>
 * Created by KazaKago on 2017/03/16.
 */
public class ActivityFactoryGenerator {

    private ProcessingEnvironment processingEnv;

    public ActivityFactoryGenerator(ProcessingEnvironment processingEnv) {
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
        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("createIntent")
                .addModifiers(Modifier.PUBLIC)
                .addModifiers(Modifier.STATIC)
                .addAnnotation(Annotations.NonNull)
                .addParameter(ParameterSpec.builder(Types.Context, "context")
                        .addAnnotation(Annotations.NonNull)
                        .build())
                .addStatement("$T intent = new $T(context, $L.class)", Types.Intent, Types.Intent, modelClassName.simpleName());
        for (Element el : element.getEnclosedElements()) {
            if (el.getAnnotation(FactoryParam.class) != null) {
                IntentTypes intentType = IntentTypes.resolve(processingEnv,  el.asType());
                if (intentType != null) {
                    TypeName fieldType = TypeName.get(el.asType());
                    String fieldName = el.getSimpleName().toString();
                    ParameterSpec.Builder paramBuilder = ParameterSpec.builder(fieldType, fieldName);
                    if (!fieldType.isPrimitive()) {
                        paramBuilder.addAnnotation(Annotations.NonNull);
                    }
                    methodBuilder.addParameter(paramBuilder.build())
                            .addAnnotations(Arrays.asList(intentType.annotations))
                            .addStatement("intent.$L($S, $L)", intentType.putMethodName, fieldName, fieldName);
                }
            }
        }
        return methodBuilder.addStatement("return intent")
                .returns(Types.Intent)
                .build();
    }

}