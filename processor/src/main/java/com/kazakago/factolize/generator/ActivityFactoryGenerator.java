package com.kazakago.factolize.generator;

import com.kazakago.factolize.FactoryParam;
import com.kazakago.factolize.constants.Annotations;
import com.kazakago.factolize.constants.BundleTypes;
import com.kazakago.factolize.constants.Types;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;

/**
 * Activity factory class generator.
 * <p>
 * Created by KazaKago on 2017/03/16.
 */
public class ActivityFactoryGenerator extends CodeGenerator {

    public ActivityFactoryGenerator(ProcessingEnvironment processingEnv) {
        super(processingEnv);
    }

    @Override
    public void execute(Element element) throws IOException {
        String packageName = processingEnv.getElementUtils().getPackageOf(element).getQualifiedName().toString();
        ClassName modelClassName = ClassName.get(packageName, element.getSimpleName().toString());
        ClassName generatedClassName = ClassName.get(packageName, element.getSimpleName().toString() + "Factory");

        MethodSpec constructor = generateConstructor();
        MethodSpec createIntentMethod = generateCreateIntentMethod(element, modelClassName);
        List<MethodSpec> createIntentOverloadMethods = generateCreateIntentOverloadMethods(element);
        MethodSpec injectArgumentMethod = generateInjectArgumentMethod(modelClassName);
        MethodSpec injectArgumentWithSavedInstanceStateMethod = generateInjectArgumentMethodWithSavedInstanceState(element, modelClassName);
        TypeSpec generatedClass = TypeSpec.classBuilder(generatedClassName)
                .addModifiers(Modifier.PUBLIC)
                .addMethod(constructor)
                .addMethod(createIntentMethod)
                .addMethods(createIntentOverloadMethods)
                .addMethod(injectArgumentMethod)
                .addMethod(injectArgumentWithSavedInstanceStateMethod)
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
                .addStatement("$T intent = new $T(context, $L.class)", Types.Intent, Types.Intent, modelClassName.simpleName())
                .addStatement("Bundle arguments = new Bundle()");
        for (Element el : element.getEnclosedElements()) {
            FactoryParam factoryParamAnnotation = el.getAnnotation(FactoryParam.class);
            if (factoryParamAnnotation != null) {
                BundleTypes bundleTypes = BundleTypes.resolve(processingEnv, el.asType());
                if (bundleTypes != null) {
                    TypeName fieldType = TypeName.get(el.asType());
                    String fieldName = el.getSimpleName().toString();
                    methodBuilder.beginControlFlow("if ($L != null)", fieldName);
                    ParameterSpec.Builder paramBuilder = ParameterSpec.builder(fieldType.box(), fieldName);
                    if (factoryParamAnnotation.required()) {
                        paramBuilder.addAnnotation(Annotations.NonNull);
                    } else {
                        paramBuilder.addAnnotation(Annotations.Nullable);
                    }
                    methodBuilder.addParameter(paramBuilder.build())
                            .addStatement("arguments.$L($S, $L)", bundleTypes.putMethodName, fieldName, fieldName);
                    methodBuilder.endControlFlow();
                }
            }
        }
        return methodBuilder.addStatement("intent.putExtras(arguments)")
                .addStatement("return intent")
                .returns(Types.Intent)
                .build();
    }

    private List<MethodSpec> generateCreateIntentOverloadMethods(Element element) {
        List<MethodSpec> methodSpecs = new ArrayList<>();
        int nonRequiredCount = 0;
        for (Element el : element.getEnclosedElements()) {
            FactoryParam factoryParamAnnotation = el.getAnnotation(FactoryParam.class);
            if (factoryParamAnnotation != null && !factoryParamAnnotation.required()) {
                nonRequiredCount++;
            }
        }
        for (int i = 0; i < nonRequiredCount; i++) {
            methodSpecs.add(generateCreateIntentOverloadMethod(element, i));
        }
        return methodSpecs;
    }

    private MethodSpec generateCreateIntentOverloadMethod(Element element, final int nonRequiredCount) {
        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("createIntent")
                .addModifiers(Modifier.PUBLIC)
                .addModifiers(Modifier.STATIC)
                .addAnnotation(Annotations.NonNull)
                .addParameter(ParameterSpec.builder(Types.Context, "context")
                        .addAnnotation(Annotations.NonNull)
                        .build());
        int currentNonRequiredCount = 0;
        for (Element el : element.getEnclosedElements()) {
            FactoryParam factoryParamAnnotation = el.getAnnotation(FactoryParam.class);
            if (factoryParamAnnotation != null) {
                BundleTypes bundleTypes = BundleTypes.resolve(processingEnv, el.asType());
                if (bundleTypes != null) {
                    TypeName fieldType = TypeName.get(el.asType());
                    String fieldName = el.getSimpleName().toString();
                    ParameterSpec.Builder paramBuilder = ParameterSpec.builder(fieldType.box(), fieldName);
                    if (factoryParamAnnotation.required()) {
                        paramBuilder.addAnnotation(Annotations.NonNull);
                    } else {
                        paramBuilder.addAnnotation(Annotations.Nullable);
                    }
                    if (factoryParamAnnotation.required()) {
                        methodBuilder.addParameter(paramBuilder.build());
                    } else if (!factoryParamAnnotation.required() && currentNonRequiredCount < nonRequiredCount) {
                        methodBuilder.addParameter(paramBuilder.build());
                        currentNonRequiredCount++;
                    }
                }
            }
        }
        currentNonRequiredCount = 0;
        StringBuilder parameter = new StringBuilder();
        for (Element el : element.getEnclosedElements()) {
            FactoryParam factoryParamAnnotation = el.getAnnotation(FactoryParam.class);
            if (factoryParamAnnotation != null) {
                BundleTypes bundleTypes = BundleTypes.resolve(processingEnv, el.asType());
                if (bundleTypes != null) {
                    String fieldName = el.getSimpleName().toString();
                    if (0 < parameter.length()) {
                        parameter.append(", ");
                    }
                    if (factoryParamAnnotation.required()) {
                        parameter.append(fieldName);
                    } else if (!factoryParamAnnotation.required() && currentNonRequiredCount < nonRequiredCount) {
                        parameter.append(fieldName);
                        currentNonRequiredCount++;
                    } else {
                        parameter.append("null");
                    }
                }
            }
        }
        methodBuilder.addStatement("return createIntent(context, $L)", parameter.toString())
                .returns(Types.Intent);
        return methodBuilder.build();
    }

    private MethodSpec generateInjectArgumentMethod(ClassName modelClassName) {
        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("injectArgument")
                .addModifiers(Modifier.PUBLIC)
                .addModifiers(Modifier.STATIC)
                .addParameter(ParameterSpec.builder(modelClassName, "activity")
                        .addAnnotation(Annotations.NonNull)
                        .build())
                .addStatement("injectArgument(activity, null)");
        return methodBuilder.build();
    }

    private MethodSpec generateInjectArgumentMethodWithSavedInstanceState(Element element, ClassName modelClassName) {
        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("injectArgument")
                .addModifiers(Modifier.PUBLIC)
                .addModifiers(Modifier.STATIC)
                .addParameter(ParameterSpec.builder(modelClassName, "activity")
                        .addAnnotation(Annotations.NonNull)
                        .build())
                .addParameter(ParameterSpec.builder(Types.Bundle, "savedInstanceState")
                        .addAnnotation(Annotations.Nullable)
                        .build())
                .addStatement("Bundle arguments = activity.getIntent().getExtras()");
        methodBuilder.beginControlFlow("if (arguments != null && savedInstanceState == null)");
        for (Element el : element.getEnclosedElements()) {
            if (el.getAnnotation(FactoryParam.class) != null) {
                BundleTypes bundleTypes = BundleTypes.resolve(processingEnv, el.asType());
                if (bundleTypes != null) {
                    TypeName fieldType = TypeName.get(el.asType());
                    String fieldName = el.getSimpleName().toString();
                    methodBuilder.beginControlFlow("if (arguments.containsKey($S))", fieldName);
                    if (el.getModifiers().contains(Modifier.PRIVATE)) {
                        if (bundleTypes.getDefaultValue != null) {
                            methodBuilder.addStatement("activity.set$L(($T) arguments.$L($S, $L))", StringUtils.capitalize(fieldName), fieldType, bundleTypes.getMethodName, fieldName, bundleTypes.getDefaultValue);
                        } else {
                            methodBuilder.addStatement("activity.set$L(($T) arguments.$L($S))", StringUtils.capitalize(fieldName), fieldType, bundleTypes.getMethodName, fieldName);
                        }
                    } else {
                        if (bundleTypes.getDefaultValue != null) {
                            methodBuilder.addStatement("activity.$L = ($T) arguments.$L($S, $L)", fieldName, fieldType, bundleTypes.getMethodName, fieldName, bundleTypes.getDefaultValue);
                        } else {
                            methodBuilder.addStatement("activity.$L = ($T) arguments.$L($S)", fieldName, fieldType, bundleTypes.getMethodName, fieldName);
                        }
                    }
                    methodBuilder.endControlFlow();
                }
            }
        }
        methodBuilder.endControlFlow();
        return methodBuilder.build();
    }

}
