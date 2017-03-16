package com.kazakago.activityinitializer;

import com.google.auto.service.AutoService;
import com.kazakago.activityinitializer.constants.Types;
import com.kazakago.activityinitializer.generator.ActivityInitializerGenerator;
import com.kazakago.activityinitializer.generator.FragmentInitializerGenerator;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;

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
        "com.kazakago.activityinitializer.Initializable",
        "com.kazakago.activityinitializer.InitializeParam"})
public class InitializerProcessor extends AbstractProcessor {

    private Messager messager;
    private ActivityInitializerGenerator activityInitializerGenerator;
    private FragmentInitializerGenerator fragmentInitializerGenerator;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        messager = processingEnv.getMessager();
        activityInitializerGenerator = new ActivityInitializerGenerator(processingEnv.getFiler(), processingEnv.getElementUtils());
        fragmentInitializerGenerator = new FragmentInitializerGenerator(processingEnv.getFiler(), processingEnv.getElementUtils());
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Class<Initializable> intentable = Initializable.class;
        for (Element element : roundEnv.getElementsAnnotatedWith(intentable)) {
            ElementKind kind = element.getKind();
            if (kind == ElementKind.CLASS) {
                try {
                    TypeName fieldType = ClassName.get(element.asType());
                    if (fieldType.getClass().isAssignableFrom(Types.Activity.getClass()) || fieldType.getClass().isAssignableFrom(Types.AppCompatActivity.getClass())) {
                        activityInitializerGenerator.execute(element);
                    } else if (fieldType.getClass().isAssignableFrom(Types.Fragment.getClass()) || fieldType.getClass().isAssignableFrom(Types.AppCompatFragment.getClass())) {
                        fragmentInitializerGenerator.execute(element);
                    } else {
                        messager.printMessage(Diagnostic.Kind.ERROR, "this class not Activity or Fragment");
                    }
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
