package com.kazakago.factolize;

import com.google.auto.service.AutoService;
import com.kazakago.factolize.constants.Types;
import com.kazakago.factolize.generator.ActivityFactoryGenerator;
import com.kazakago.factolize.generator.FragmentFactoryGenerator;
import com.kazakago.factolize.generator.ViewModelFactoryGenerator;
import com.kazakago.factolize.utils.TypeUtils;

import java.io.IOException;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

/**
 * Annotation processor class.
 *
 * Created by KazaKago on 2017/03/08.
 */
@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes({
        "com.kazakago.factolize.*",
})
public class FactoryProcessor extends AbstractProcessor {

    private Messager messager;
    private ActivityFactoryGenerator activityFactoryGenerator;
    private FragmentFactoryGenerator fragmentFactoryGenerator;
    private ViewModelFactoryGenerator viewModelFactoryGenerator;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        messager = processingEnv.getMessager();
        activityFactoryGenerator = new ActivityFactoryGenerator(processingEnv);
        fragmentFactoryGenerator = new FragmentFactoryGenerator(processingEnv);
        viewModelFactoryGenerator = new ViewModelFactoryGenerator(processingEnv);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(Factory.class)) {
            ElementKind kind = element.getKind();
            if (kind == ElementKind.CLASS) {
                try {
                    if (TypeUtils.isContainType(processingEnv, element.asType(), Types.Activity) || TypeUtils.isContainType(processingEnv, element.asType(), Types.AppCompatActivity)) {
                        activityFactoryGenerator.execute(element);
                    } else if (TypeUtils.isContainType(processingEnv, element.asType(), Types.Fragment) || TypeUtils.isContainType(processingEnv, element.asType(), Types.AppCompatFragment)) {
                        fragmentFactoryGenerator.execute(element);
                    } else if (TypeUtils.isContainType(processingEnv, element.asType(), Types.ViewModel)){
                        viewModelFactoryGenerator.execute(element);
                    } else {
                        messager.printMessage(Diagnostic.Kind.ERROR, "Not activity or fragment");
                    }
                } catch (IOException e) {
                    messager.printMessage(Diagnostic.Kind.ERROR, e.getLocalizedMessage());
                }
            } else {
                messager.printMessage(Diagnostic.Kind.ERROR, "Not class");
            }
        }
        return true;
    }
}
