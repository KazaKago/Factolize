package com.kazakago.activityfactory.constants;

import com.kazakago.activityfactory.utils.TypeUtils;
import com.squareup.javapoet.TypeName;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.type.TypeMirror;

/**
 * Created by tamura_k on 2017/03/17.
 */
public enum IntentTypes {

    //ArrayList<Class>
    IntegerArrayList(Types.IntegerArrayList, "putIntegerArrayListExtra", "getIntegerArrayListExtra", null),
    StringArrayList(Types.StringArrayList, "putStringArrayListExtra", "getStringArrayListExtra", null),
    CharSequenceArrayList(Types.CharSequenceArrayList, "putCharSequenceArrayListExtra", "getCharSequenceArrayListExtra", null),

    //ArrayList<Interface>
    ParcelableArrayList(Types.ParcelableArrayList, "putParcelableArrayListExtra", "getParcelableArrayListExtra", null),

    //Class[]
    StringArray(Types.StringArray, "putExtra", "getStringArrayExtra", null),
    CharSequenceArray(Types.CharSequenceArray, "putExtra", "getCharSequenceArrayExtra", null),

    //Interface[]
    ParcelableArray(Types.ParcelableArray, "putExtra", "getParcelableArrayExtra", null),

    //Class
    String(Types.String, "putExtra", "getStringExtra", null),
    CharSequence(Types.CharSequence, "putExtra", "getCharSequenceExtra", null),
    Bundle(Types.Bundle, "putExtra", "getBundleExtra", null),

    //Primitive[]
    CharArray(Types.CharArray, "putExtra", "getCharArrayExtra", null),
    ByteArray(Types.ByteArray, "putExtra", "getByteArrayExtra", null),
    ShortArray(Types.ShortArray, "putExtra", "getShortArrayExtra", null),
    IntArray(Types.IntArray, "putExtra", "getIntArrayExtra", null),
    LongArray(Types.LongArray, "putExtra", "getLongArrayExtra", null),
    FloatArray(Types.FloatArray, "putExtra", "getFloatArrayExtra", null),
    DoubleArray(Types.DoubleArray, "putExtra", "getDoubleArrayExtra", null),
    BooleanArray(Types.BooleanArray, "putExtra", "getBooleanArrayExtra", null),

    //Primitive
    Char(Types.Char, "putExtra", "getCharExtra", "(char) 0"),
    Byte(Types.Byte, "putExtra", "getByteExtra", "(byte) 0"),
    Short(Types.Short, "putExtra", "getShortExtra", "(short) 0"),
    Int(Types.Int, "putExtra", "getIntExtra", "0"),
    Long(Types.Long, "putExtra", "getLongExtra", "0"),
    Float(Types.Float, "putExtra", "getFloatExtra", "0"),
    Double(Types.Double, "putExtra", "getDoubleExtra", "0"),
    Boolean(Types.Boolean, "putExtra", "getBooleanExtra", "false"),

    //Interface
    Parcelable(Types.Parcelable, "putExtra", "getParcelableExtra", null),
    Serializable(Types.Serializable, "putExtra", "getSerializableExtra", null);

    public TypeName typeName;
    public String putMethodName;
    public String getMethodName;
    public String getDefaultValue;

    IntentTypes(TypeName typeName, String putMethodName, String getMethodName, String getDefaultValue) {
        this.typeName = typeName;
        this.putMethodName = putMethodName;
        this.getMethodName = getMethodName;
        this.getDefaultValue = getDefaultValue;
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
