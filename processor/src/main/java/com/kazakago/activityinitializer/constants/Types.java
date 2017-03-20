package com.kazakago.activityinitializer.constants;

import com.squareup.javapoet.ArrayTypeName;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Type Constants
 * <p>
 * Created by Kensuke on 2017/03/14.
 */
public class Types {

    public static final ClassName Context = ClassName.get("android.content", "Context");

    public static final ClassName Intent = ClassName.get("android.content", "Intent");

    public static final ClassName Bundle = ClassName.get("android.os", "Bundle");

    public static final ClassName Activity = ClassName.get("android.app", "Activity");

    public static final ClassName Fragment = ClassName.get("android.app", "Fragment");

    public static final ClassName AppCompatActivity = ClassName.get("android.support.v7.app", "AppCompatActivity");

    public static final ClassName AppCompatFragment = ClassName.get("android.support.v4.app", "Fragment");

    public static final ClassName NonNull = ClassName.get("android.support.annotation", "NonNull");

    public static final ClassName Nullable = ClassName.get("android.support.annotation", "Nullable");

    public static final ClassName TargetApi = ClassName.get("android.annotation", "TargetApi");

    public static final TypeName Boolean = TypeName.BOOLEAN;

    public static final TypeName Byte = TypeName.BYTE;

    public static final TypeName Char = TypeName.CHAR;

    public static final TypeName Short = TypeName.SHORT;

    public static final TypeName Int = TypeName.INT;

    public static final TypeName Long = TypeName.LONG;

    public static final TypeName Float = TypeName.FLOAT;

    public static final TypeName Double = TypeName.DOUBLE;

    public static final ClassName String = ClassName.get(String.class);

    public static final ClassName CharSequence = ClassName.get(CharSequence.class);

    public static final ClassName Parcelable = ClassName.get("android.os", "Parcelable");

    public static final ArrayTypeName ParcelableArray = ArrayTypeName.of(Parcelable);

    public static final ParameterizedTypeName ParcelableArrayList = ParameterizedTypeName.get(ClassName.get(ArrayList.class), Parcelable);

    public static final ParameterizedTypeName IntegerArrayList = ParameterizedTypeName.get(ClassName.get(ArrayList.class), ClassName.get(Integer.class));

    public static final ParameterizedTypeName StringArrayList = ParameterizedTypeName.get(ClassName.get(ArrayList.class), String);

    public static final ParameterizedTypeName CharSequenceArrayList = ParameterizedTypeName.get(ClassName.get(ArrayList.class), CharSequence);

    public static final ClassName Serializable = ClassName.get(Serializable.class);

    public static final ArrayTypeName BooleanArray = ArrayTypeName.of(Boolean);

    public static final ArrayTypeName ByteArray = ArrayTypeName.of(Byte);

    public static final ArrayTypeName ShortArray = ArrayTypeName.of(Short);

    public static final ArrayTypeName CharArray = ArrayTypeName.of(Char);

    public static final ArrayTypeName IntArray = ArrayTypeName.of(Int);

    public static final ArrayTypeName LongArray = ArrayTypeName.of(Long);

    public static final ArrayTypeName FloatArray = ArrayTypeName.of(Float);

    public static final ArrayTypeName DoubleArray = ArrayTypeName.of(Double);

    public static final ArrayTypeName StringArray = ArrayTypeName.of(String);

    public static final ArrayTypeName CharSequenceArray = ArrayTypeName.of(CharSequence);

    public static final ClassName Size = ClassName.get("android.util", "Size");

    public static final ClassName SizeF = ClassName.get("android.util", "SizeF");

    public static final ParameterizedTypeName ParcelableSparseArray = ParameterizedTypeName.get(ClassName.get("android.util", "SparseArray"), Parcelable);

    public static final ClassName IBinder =  ClassName.get("android.os", "IBinder");
}
