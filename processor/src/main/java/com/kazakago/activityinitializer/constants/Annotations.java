package com.kazakago.activityinitializer.constants;

import com.squareup.javapoet.AnnotationSpec;
import com.sun.org.apache.bcel.internal.classfile.Deprecated;

/**
 * Annotation Constants
 *
 * Created by KazaKago on 2017/03/14.
 */
public class Annotations {

    public static final AnnotationSpec NonNull = AnnotationSpec.builder(Types.NonNull).build();

    public static final AnnotationSpec Nullable = AnnotationSpec.builder(Types.Nullable).build();

    public static final AnnotationSpec Deprecated = AnnotationSpec.builder(Deprecated.class).build();

}
