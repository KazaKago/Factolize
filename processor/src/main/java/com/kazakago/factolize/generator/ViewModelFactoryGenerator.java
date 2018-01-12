package com.kazakago.factolize.generator;

import com.kazakago.factolize.constants.Annotations;
import com.kazakago.factolize.constants.Types;
import com.kazakago.factolize.utils.AnnotationUtils;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
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
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.VariableElement;

/**
 * ViewModel factory class generator.
 * <p>
 * Created by KazaKago on 2017/03/16.
 */
public class ViewModelFactoryGenerator extends CodeGenerator {

    public ViewModelFactoryGenerator(ProcessingEnvironment processingEnv) {
        super(processingEnv);
    }

    @Override
    public void execute(Element element) throws IOException {
        String packageName = processingEnv.getElementUtils().getPackageOf(element).getQualifiedName().toString();
        ClassName modelClassName = ClassName.get(packageName, element.getSimpleName().toString());
        ClassName generatedClassName = ClassName.get(packageName, element.getSimpleName().toString() + "Factory");

        List<FieldSpec> fields = generateField(element);
        List<MethodSpec> constructors = generateConstructors(element);
        MethodSpec createMethod = generateCreateMethod(element, modelClassName);

        TypeSpec generatedClass = TypeSpec.classBuilder(generatedClassName)
                .addSuperinterface(Types.ViewModelProviderFactory)
                .addModifiers(Modifier.PUBLIC)
                .addFields(fields)
                .addMethods(constructors)
                .addMethod(createMethod)
                .build();

        JavaFile.builder(packageName, generatedClass)
                .build()
                .writeTo(processingEnv.getFiler());
    }

    private List<FieldSpec> generateField(Element element) {
        List<FieldSpec> fields = new ArrayList<>();
        for (Element el : element.getEnclosedElements()) {
            if (el.getKind() == ElementKind.CONSTRUCTOR) {
                for (Element constructorParameter : ((ExecutableElement) el).getParameters()) {
                    FieldSpec.Builder fieldBuilder = FieldSpec.builder(TypeName.get(constructorParameter.asType()), constructorParameter.getSimpleName().toString())
                            .addModifiers(Modifier.PRIVATE);
                    if (!ClassName.get(constructorParameter.asType()).isPrimitive()) {
                        if (AnnotationUtils.hasNonNullAnnotation(constructorParameter)) {
                            fieldBuilder.addAnnotation(Types.NonNull);
                        } else {
                            fieldBuilder.addAnnotation(Types.Nullable);
                        }
                    }
                    fields.add(fieldBuilder.build());
                }
                break;
            }
        }
        return fields;
    }

    private List<MethodSpec> generateConstructors(Element element) {
        List<MethodSpec> constructors = new ArrayList<>();
        for (Element el : element.getEnclosedElements()) {
            if (el.getKind() == ElementKind.CONSTRUCTOR) {
                MethodSpec.Builder constructorBuilder = MethodSpec.constructorBuilder()
                        .addModifiers(Modifier.PUBLIC);
                for (Element constructorParameter : ((ExecutableElement) el).getParameters()) {
                    ParameterSpec.Builder parameterBuilder = ParameterSpec.builder(TypeName.get(constructorParameter.asType()), constructorParameter.getSimpleName().toString());
                    if (!ClassName.get(constructorParameter.asType()).isPrimitive()) {
                        if (AnnotationUtils.hasNonNullAnnotation(constructorParameter)) {
                            parameterBuilder.addAnnotation(Types.NonNull);
                        } else {
                            parameterBuilder.addAnnotation(Types.Nullable);
                        }
                    }
                    constructorBuilder.addParameter(parameterBuilder.build())
                            .addStatement("this.$L = $L", constructorParameter.getSimpleName().toString(), constructorParameter.getSimpleName().toString());
                }
                constructors.add(constructorBuilder.build());
                break;
            }
        }
        return constructors;
    }

    private MethodSpec generateCreateMethod(Element element, ClassName modelClassName) {
        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("create")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Annotations.NonNull)
                .addAnnotation(Override.class)
                .addCode("return new $T(", modelClassName);

        for (Element el : element.getEnclosedElements()) {
            if (el.getKind() == ElementKind.CONSTRUCTOR) {
                List<? extends VariableElement> constructorParameters = ((ExecutableElement) el).getParameters();
                for (int i = 0; i < constructorParameters.size(); i++) {
                    Element constructorParameter = constructorParameters.get(i);
                    methodBuilder.addCode("$L", constructorParameter.getSimpleName().toString());
                    if (i < constructorParameters.size() - 1) {
                        methodBuilder.addCode(", ");
                    }
                }
                break;
            }
        }

        methodBuilder.addCode(");\n")
                .returns(modelClassName);
        ParameterSpec.Builder paramBuilder = ParameterSpec.builder(Class.class, "modelClass");
        paramBuilder.addAnnotation(Annotations.NonNull);
        methodBuilder.addParameter(paramBuilder.build());

        return methodBuilder.build();
    }

}
