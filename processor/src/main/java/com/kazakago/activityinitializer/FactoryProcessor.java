package com.kazakago.activityinitializer;

import com.google.auto.service.AutoService;
import com.kazakago.activityinitializer.generator.ActivityFactoryGenerator;
import com.kazakago.activityinitializer.generator.FragmentFactoryGenerator;

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
 * <p>
 * Created by KazaKago on 2017/03/08.
 */
@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_7)
@SupportedAnnotationTypes({
        "com.kazakago.activityinitializer.ActivityFactory",
        "com.kazakago.activityinitializer.FragmentFactory",
        "com.kazakago.activityinitializer.FactoryParam"})
public class FactoryProcessor extends AbstractProcessor {

    private Messager messager;
    private ActivityFactoryGenerator activityFactoryGenerator;
    private FragmentFactoryGenerator fragmentFactoryGenerator;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        messager = processingEnv.getMessager();
        activityFactoryGenerator = new ActivityFactoryGenerator(processingEnv);
        fragmentFactoryGenerator = new FragmentFactoryGenerator(processingEnv.getFiler(), processingEnv.getElementUtils());
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(ActivityFactory.class)) {
            ElementKind kind = element.getKind();
            if (kind == ElementKind.CLASS) {
                try {
                    activityFactoryGenerator.execute(element);
                } catch (IOException e) {
                    messager.printMessage(Diagnostic.Kind.ERROR, "IO error");
                }
            } else {
                messager.printMessage(Diagnostic.Kind.ERROR, "Type error");
            }
        }
        for (Element element : roundEnv.getElementsAnnotatedWith(FragmentFactory.class)) {
            ElementKind kind = element.getKind();
            if (kind == ElementKind.CLASS) {
                try {
                    fragmentFactoryGenerator.execute(element);
                } catch (IOException e) {
                    messager.printMessage(Diagnostic.Kind.ERROR, "IO error");
                }
            } else {
                messager.printMessage(Diagnostic.Kind.ERROR, "Type error");
            }
        }
        return true;
    }
}
