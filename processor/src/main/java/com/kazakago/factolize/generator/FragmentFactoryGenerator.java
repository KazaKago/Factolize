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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;

/**
 * Fragment factory class generator.
 * <p>
 * Created by KazaKago on 2017/03/16.
 */
public class FragmentFactoryGenerator extends CodeGenerator {

    public FragmentFactoryGenerator(ProcessingEnvironment processingEnv) {
        super(processingEnv);
    }

    @Override
    public void execute(Element element) throws IOException {
        String packageName = processingEnv.getElementUtils().getPackageOf(element).getQualifiedName().toString();
        ClassName modelClassName = ClassName.get(packageName, element.getSimpleName().toString());
        ClassName generatedClassName = ClassName.get(packageName, element.getSimpleName().toString() + "Factory");

        MethodSpec constructor = generateConstructor();
        MethodSpec createInstanceMethod = generateCreateIntentMethod(element, modelClassName);
        List<MethodSpec> createInstanceOverloadMethods = generateCreateIntentOverloadMethods(element, modelClassName);
        MethodSpec injectArgumentMethod = generateInjectArgumentMethod(modelClassName);
        MethodSpec injectArgumentWithSavedInstanceStateMethod = generateInjectArgumentMethodWithSavedInstanceState(element, modelClassName);

        TypeSpec generatedClass = TypeSpec.classBuilder(generatedClassName)
                .addModifiers(Modifier.PUBLIC)
                .addMethod(constructor)
                .addMethod(createInstanceMethod)
                .addMethods(createInstanceOverloadMethods)
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
        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("createInstance")
                .addModifiers(Modifier.PUBLIC)
                .addModifiers(Modifier.STATIC)
                .addAnnotation(Annotations.NonNull)
                .addStatement("$T fragment = new $T()", modelClassName, modelClassName)
                .addStatement("$T arguments = new $T()", Types.Bundle, Types.Bundle);
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
        return methodBuilder.addStatement("fragment.setArguments(arguments)")
                .addStatement("return fragment")
                .returns(modelClassName)
                .build();
    }

    private List<MethodSpec> generateCreateIntentOverloadMethods(Element element, ClassName modelClassName) {
        List<MethodSpec> methodSpecs = new ArrayList<>();
        int nonRequiredCount = 0;
        for (Element el : element.getEnclosedElements()) {
            FactoryParam factoryParamAnnotation = el.getAnnotation(FactoryParam.class);
            if (factoryParamAnnotation != null && !factoryParamAnnotation.required()) {
                nonRequiredCount++;
            }
        }
        for (int i = 0; i < nonRequiredCount; i++) {
            methodSpecs.add(generateCreateIntentOverloadMethod(element, modelClassName, i));
        }
        return methodSpecs;
    }

    private MethodSpec generateCreateIntentOverloadMethod(Element element, ClassName modelClassName, final int nonRequiredCount) {
        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("createInstance")
                .addModifiers(Modifier.PUBLIC)
                .addModifiers(Modifier.STATIC)
                .addAnnotation(Annotations.NonNull);
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
        methodBuilder.addStatement("return createInstance($L)", parameter.toString())
                .returns(modelClassName);
        return methodBuilder.build();
    }

    private MethodSpec generateInjectArgumentMethod(ClassName modelClassName) {
        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("injectArgument")
                .addModifiers(Modifier.PUBLIC)
                .addModifiers(Modifier.STATIC)
                .addParameter(ParameterSpec.builder(modelClassName, "fragment")
                        .addAnnotation(Annotations.NonNull)
                        .build())
                .addStatement("injectArgument(fragment, null)");
        return methodBuilder.build();
    }

    private MethodSpec generateInjectArgumentMethodWithSavedInstanceState(Element element, ClassName modelClassName) {
        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("injectArgument")
                .addModifiers(Modifier.PUBLIC)
                .addModifiers(Modifier.STATIC)
                .addParameter(ParameterSpec.builder(modelClassName, "fragment")
                        .addAnnotation(Annotations.NonNull)
                        .build())
                .addParameter(ParameterSpec.builder(Types.Bundle, "savedInstanceState")
                        .addAnnotation(Annotations.Nullable)
                        .build());
        methodBuilder.addStatement("Bundle arguments = fragment.getArguments()");
        methodBuilder.beginControlFlow("if (arguments != null && savedInstanceState == null)");
        for (Element el : element.getEnclosedElements()) {
            if (el.getAnnotation(FactoryParam.class) != null) {
                BundleTypes bundleType = BundleTypes.resolve(processingEnv, el.asType());
                if (bundleType != null) {
                    TypeName fieldType = TypeName.get(el.asType());
                    String fieldName = el.getSimpleName().toString();
                    methodBuilder.beginControlFlow("if (arguments.containsKey($S))", fieldName);
                    if (bundleType.getDefaultValue != null) {
                        methodBuilder.addStatement("fragment.$L = ($T) arguments.$L($S, $L)", fieldName, fieldType, bundleType.getMethodName, fieldName, bundleType.getDefaultValue);
                    } else {
                        methodBuilder.addStatement("fragment.$L = ($T) arguments.$L($S)", fieldName, fieldType, bundleType.getMethodName, fieldName);
                    }
                    methodBuilder.endControlFlow();
                }
            }
        }
        methodBuilder.endControlFlow();
        return methodBuilder.build();
    }

}
