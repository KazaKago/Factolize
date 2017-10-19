package com.kazakago.factolize.constants;

import com.kazakago.factolize.utils.TypeUtils;
import com.squareup.javapoet.TypeName;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.type.TypeMirror;

public enum BundleTypes {

    //ArrayList<Class>
    IntegerArrayList(Types.IntegerArrayList, "putIntegerArrayList", "getIntegerArrayList", null),
    StringArrayList(Types.StringArrayList, "putStringArrayList", "getStringArrayList", null),
    CharSequenceArrayList(Types.CharSequenceArrayList, "putCharSequenceArrayList", "getCharSequenceArrayList", null),

    //ArrayList<Interface>
    ParcelableArrayList(Types.ParcelableArrayList, "putParcelableArrayList", "getParcelableArrayList", null),

    //SparseArray<Interface>
    ParcelableSparseArray(Types.ParcelableSparseArray, "putSparseParcelableArray", "getSparseParcelableArray", null),

    //Class[]
    StringArray(Types.StringArray, "putStringArray", "getStringArray", null),
    CharSequenceArray(Types.CharSequenceArray, "putCharSequenceArray", "getCharSequenceArray", null),

    //Interface[]
    ParcelableArray(Types.ParcelableArray, "putParcelableArray", "getParcelableArray", null),

    //Class
    String(Types.String, "putString", "getString", null),
    CharSequence(Types.CharSequence, "putCharSequence", "getCharSequence", null),
    Size(Types.Size, "putSize", "getSize", null),
    SizeF(Types.SizeF, "putSizeF", "getSizeF", null),
    IBinder(Types.IBinder, "putBinder", "getBinder", null),
    Bundle(Types.Bundle, "putBundle", "getBundle", null),

    //Primitive[]
    CharArray(Types.CharArray, "putCharArray", "getCharArray", null),
    ByteArray(Types.ByteArray, "putByteArray", "getByteArray", null),
    ShortArray(Types.ShortArray, "putShortArray", "getShortArray", null),
    IntArray(Types.IntArray, "putIntArray", "getIntArray", null),
    LongArray(Types.LongArray, "putLongArray", "getLongArray", null),
    FloatArray(Types.FloatArray, "putFloatArray", "getFloatArray", null),
    DoubleArray(Types.DoubleArray, "putDoubleArray", "getDoubleArray", null),
    BooleanArray(Types.BooleanArray, "putBooleanArray", "getBooleanArray", null),

    //Primitive
    Char(Types.Char, "putChar", "getChar", "(char) 0"),
    Byte(Types.Byte, "putByte", "getByte", "(byte) 0"),
    Short(Types.Short, "putShort", "getShort", "(short) 0"),
    Int(Types.Int, "putInt", "getInt", "0"),
    Long(Types.Long, "putLong", "getLong", "0"),
    Float(Types.Float, "putFloat", "getFloat", "0"),
    Double(Types.Double, "putDouble", "getDouble", "0"),
    Boolean(Types.Boolean, "putBoolean", "getBoolean", "false"),

    //Interface
    Parcelable(Types.Parcelable, "putParcelable", "getParcelable", null),
    Serializable(Types.Serializable, "putSerializable", "getSerializable", null);

    public TypeName typeName;
    public String putMethodName;
    public String getMethodName;
    public String getDefaultValue;

    BundleTypes(TypeName typeName, String putMethodName, String getMethodName, String getDefaultValue) {
        this.typeName = typeName;
        this.putMethodName = putMethodName;
        this.getMethodName = getMethodName;
        this.getDefaultValue = getDefaultValue;
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
