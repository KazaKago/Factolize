package com.kazakago.activityfactory.constants;

import com.kazakago.activityfactory.utils.TypeUtils;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.TypeName;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.type.TypeMirror;

/**
 * Created by tamura_k on 2017/03/17.
 */
public enum IntentTypes {

    //ArrayList<Class>
    IntegerArrayList(Types.IntegerArrayList, "putIntegerArrayListExtra"),
    StringArrayList(Types.StringArrayList, "putStringArrayListExtra"),
    CharSequenceArrayList(Types.CharSequenceArrayList, "putCharSequenceArrayListExtra"),

    //ArrayList<Interface>
    ParcelableArrayList(Types.ParcelableArrayList, "putParcelableArrayListExtra"),

    //Class[]
    StringArray(Types.StringArray, "putExtra"),
    CharSequenceArray(Types.CharSequenceArray, "putExtra"),

    //Interface[]
    ParcelableArray(Types.ParcelableArray, "putExtra"),

    //Class
    String(Types.String, "putExtra"),
    CharSequence(Types.CharSequence, "putExtra"),
    Bundle(Types.Bundle, "putExtra"),

    //Primitive[]
    CharArray(Types.CharArray, "putExtra"),
    ByteArray(Types.ByteArray, "putExtra"),
    ShortArray(Types.ShortArray, "putExtra"),
    IntArray(Types.IntArray, "putExtra"),
    LongArray(Types.LongArray, "putExtra"),
    FloatArray(Types.FloatArray, "putExtra"),
    DoubleArray(Types.DoubleArray, "putExtra"),
    BooleanArray(Types.BooleanArray, "putExtra"),

    //Primitive
    Char(Types.Char, "putExtra"),
    Byte(Types.Byte, "putExtra"),
    Short(Types.Short, "putExtra"),
    Int(Types.Int, "putExtra"),
    Long(Types.Long, "putExtra"),
    Float(Types.Float, "putExtra"),
    Double(Types.Double, "putExtra"),
    Boolean(Types.Boolean, "putExtra"),

    //Interface
    Serializable(Types.Serializable, "putExtra"),
    Parcelable(Types.Parcelable, "putExtra");

    public TypeName typeName;
    public String putMethodName;
    public AnnotationSpec[] annotations;

    IntentTypes(TypeName typeName, String putMethodName, AnnotationSpec... annotations) {
        this.typeName = typeName;
        this.putMethodName = putMethodName;
        this.annotations = annotations;
    }

    public static IntentTypes resolve(ProcessingEnvironment processingEnv, TypeMirror typeMirror) {
//        processingEnv.getMessager().printMessage(Diagnostic.Kind.OTHER, "--- " + typeMirror.toString() + " ---");
        for (IntentTypes intentType : IntentTypes.values()) {
            if (TypeUtils.isContainType(processingEnv, typeMirror, intentType.typeName)) {
//                processingEnv.getMessager().printMessage(Diagnostic.Kind.OTHER, " OK " + intentType.typeName.toString() + "    ");
                return intentType;
//            } else {
//                processingEnv.getMessager().printMessage(Diagnostic.Kind.OTHER, " NG " + intentType.typeName.toString() + "    ");
            }
        }
        return null;
    }

}
