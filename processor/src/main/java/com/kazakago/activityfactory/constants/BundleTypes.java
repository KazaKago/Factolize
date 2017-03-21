package com.kazakago.activityfactory.constants;

import com.kazakago.activityfactory.utils.TypeUtils;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.TypeName;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.type.TypeMirror;

/**
 * Created by Kensuke on 2017/03/20.
 */
public enum BundleTypes {

    //ArrayList<Class>
    IntegerArrayList(Types.IntegerArrayList, "putIntegerArrayList"),
    StringArrayList(Types.StringArrayList, "putStringArrayList"),
    CharSequenceArrayList(Types.CharSequenceArrayList, "putCharSequenceArrayList"),

    //ArrayList<Interface>
    ParcelableArrayList(Types.ParcelableArrayList, "putParcelableArrayList"),

    //SparseArray<Interface>
    ParcelableSparseArray(Types.ParcelableSparseArray, "putSparseParcelableArray"),

    //Class[]
    StringArray(Types.StringArray, "putStringArray"),
    CharSequenceArray(Types.CharSequenceArray, "putCharSequenceArray"),

    //Interface[]
    ParcelableArray(Types.ParcelableArray, "putParcelableArray"),

    //Class
    String(Types.String, "putString"),
    CharSequence(Types.CharSequence, "putCharSequence"),
    Size(Types.Size, "putSize"),
    SizeF(Types.SizeF, "putSizeF"),
    Binder(Types.IBinder, "putBinder"),
    Bundle(Types.Bundle, "putBundle"),

    //Primitive[]
    CharArray(Types.CharArray, "putCharArray"),
    ByteArray(Types.ByteArray, "putByteArray"),
    ShortArray(Types.ShortArray, "putShortArray"),
    IntArray(Types.IntArray, "putIntArray"),
    LongArray(Types.LongArray, "putLongArray"),
    FloatArray(Types.FloatArray, "putFloatArray"),
    DoubleArray(Types.DoubleArray, "putDoubleArray"),
    BooleanArray(Types.BooleanArray, "putBooleanArray"),

    //Primitive
    Char(Types.Char, "putChar"),
    Byte(Types.Byte, "putByte"),
    Short(Types.Short, "putShort"),
    Int(Types.Int, "putInt"),
    Long(Types.Long, "putLong"),
    Float(Types.Float, "putFloat"),
    Double(Types.Double, "putDouble"),
    Boolean(Types.Boolean, "putBoolean"),

    //Interface
    Serializable(Types.Serializable, "putSerializable"),
    Parcelable(Types.Parcelable, "putParcelable");

    public TypeName typeName;
    public String putMethodName;
    public AnnotationSpec[] annotations;

    BundleTypes(TypeName typeName, String putMethodName, AnnotationSpec... annotations) {
        this.typeName = typeName;
        this.putMethodName = putMethodName;
        this.annotations = annotations;
    }

    public static BundleTypes resolve(ProcessingEnvironment processingEnv, TypeMirror typeMirror) {
//        processingEnv.getMessager().printMessage(Diagnostic.Kind.OTHER, "--- " + typeMirror.toString() + " ---");
        for (BundleTypes bundleType : BundleTypes.values()) {
            if (TypeUtils.isContainType(processingEnv, typeMirror, bundleType.typeName)) {
//                processingEnv.getMessager().printMessage(Diagnostic.Kind.OTHER, " OK " + bundleType.typeName.toString() + "    ");
                return bundleType;
//            } else {
//                processingEnv.getMessager().printMessage(Diagnostic.Kind.OTHER, " NG " + bundleType.typeName.toString() + "    ");
            }
        }
        return null;
    }

}
