package com.kazakago.activityinitializer.constants;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ArrayTypeName;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

/**
 * Created by tamura_k on 2017/03/17.
 */
public enum IntentTypes {
    Boolean(Types.BOOLEAN, "putExtra"),
    Byte(Types.BYTE, "putExtra"),
    Char(Types.CHAR, "putExtra"),
    Short(Types.SHORT, "putExtra"),
    Int(Types.INT, "putExtra"),
    Long(Types.LONG, "putExtra"),
    Float(Types.FLOAT, "putExtra"),
    Double(Types.DOUBLE, "putExtra"),
    String(Types.STRING, "putExtra"),
    CharSequence(Types.CHARSEQUENCE, "putExtra"),
    Parcelable(Types.PARCELABLE, "putExtra"),
    ParcelableArray(Types.PARCELABLE_ARRAY, "putExtra"),
    ParcelableArrayList(Types.PARCELABLE_ARRAY_LIST, "putParcelableArrayListExtra"),
    IntegerArrayList(Types.INTEGER_ARRAY_LIST, "putIntegerArrayListExtra"),
    StringArrayList(Types.STRING_ARRAY_LIST, "putStringArrayListExtra"),
    CharSequenceArrayList(Types.CHARSEQUENCE_ARRAY_LIST, "putCharSequenceArrayListExtra"),
    Serializable(Types.SERIALIZABLE, "putExtra"),
    BooleanArray(Types.BOOLEAN_ARRAY, "putExtra"),
    ByteArray(Types.BYTE_ARRAY, "putExtra"),
    ShortArray(Types.SHORT_ARRAY, "putExtra"),
    CharArray(Types.CHAR_ARRAY, "putExtra"),
    IntArray(Types.INT_ARRAY, "putExtra"),
    LongArray(Types.LONG_ARRAY, "putExtra"),
    FloatArray(Types.FLOAT_ARRAY, "putExtra"),
    DoubleArray(Types.DOUBLE_ARRAY, "putExtra"),
    StringArray(Types.STRING_ARRAY, "putExtra"),
    CharSequenceArray(Types.CHARSEQUENCE_ARRAY, "putExtra"),
    Bundle(Types.BUNDLE, "putExtra"),
    IBinder(Types.IBINDER, "putExtra", Annotations.Deprecated),
    Bundles(Types.BUNDLE, "putExtras");

    public TypeName typeName;
    public String putMethodName;
    public AnnotationSpec[] annotations;

    IntentTypes(TypeName typeName, String putMethodName, AnnotationSpec... annotations) {
        this.typeName = typeName;
        this.putMethodName = putMethodName;
        this.annotations = annotations;
    }

    public static IntentTypes resolve(ProcessingEnvironment processingEnv, TypeMirror typeMirror) {
        for (IntentTypes intentType : IntentTypes.values()) {
            if (intentType.typeName instanceof ClassName) {
                if (isContainClass(processingEnv, typeMirror, (ClassName) intentType.typeName)) return intentType;
            } else if (intentType.typeName instanceof ParameterizedTypeName) {
                if (isContainParameterizedType(processingEnv, typeMirror, (ParameterizedTypeName) intentType.typeName)) return intentType;
            } else if (intentType.typeName instanceof ArrayTypeName) {
                if (isArrayType(typeMirror, (ArrayTypeName) intentType.typeName)) return intentType;
            } else {
                if (isType(typeMirror, intentType.typeName)) return intentType;
            }
        }
        return null;
    }

    private static boolean isContainClass(ProcessingEnvironment processingEnv, TypeMirror typeMirror, ClassName className) {
        TypeMirror intentTypeMirror = processingEnv.getElementUtils().getTypeElement(className.reflectionName()).asType();
        return processingEnv.getTypeUtils().isSubtype(typeMirror, intentTypeMirror);
    }

    private static boolean isContainParameterizedType(ProcessingEnvironment processingEnv, TypeMirror typeMirror, ParameterizedTypeName parameterizedTypeName) {
        TypeElement firstType = processingEnv.getElementUtils().getTypeElement(parameterizedTypeName.rawType.reflectionName());
        TypeMirror secondType = processingEnv.getElementUtils().getTypeElement(((ClassName)parameterizedTypeName.typeArguments.get(0)).reflectionName()).asType();
        TypeMirror intentTypeMirror = processingEnv.getTypeUtils().getDeclaredType(firstType, secondType);
        return processingEnv.getTypeUtils().isSubtype(typeMirror, intentTypeMirror);
    }

    private static boolean isArrayType(TypeMirror typeMirror, ArrayTypeName arrayTypeName) {
        return typeMirror.equals(arrayTypeName);
    }

    private static boolean isType(TypeMirror typeMirror, TypeName typeName) {
        return typeMirror.equals(typeName);
    }

}
