package com.kazakago.activityinitializer;

import com.google.auto.service.AutoService;
import com.kazakago.activityinitializer.constants.Types;
import com.kazakago.activityinitializer.generator.ActivityFactoryGenerator;
import com.kazakago.activityinitializer.generator.FragmentFactoryGenerator;
import com.kazakago.activityinitializer.utils.TypeUtils;

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
        "com.kazakago.activityinitializer.Factory",
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
        fragmentFactoryGenerator = new FragmentFactoryGenerator(processingEnv);
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
