package com.kazakago.factolize.generator;

import java.io.IOException;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;

/**
 * Abstract CodeGenerator class.
 * <p>
 * Created by KazaKago on 2017/05/08.
 */
abstract class CodeGenerator {

    ProcessingEnvironment processingEnv;

    CodeGenerator(ProcessingEnvironment processingEnv) {
        this.processingEnv = processingEnv;
    }

    public abstract void execute(Element element) throws IOException;

}
