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


    public static final TypeName BOOLEAN = TypeName.BOOLEAN;

    public static final TypeName BYTE = TypeName.BYTE;

    public static final TypeName CHAR = TypeName.CHAR;

    public static final TypeName SHORT = TypeName.SHORT;

    public static final TypeName INT = TypeName.INT;

    public static final TypeName LONG = TypeName.LONG;

    public static final TypeName FLOAT = TypeName.FLOAT;

    public static final TypeName DOUBLE = TypeName.DOUBLE;

    public static final ClassName STRING = ClassName.get(String.class);

    public static final ClassName CHARSEQUENCE = ClassName.get(CharSequence.class);

    public static final ClassName PARCELABLE = ClassName.get("android.os", "Parcelable");

    public static final ArrayTypeName PARCELABLE_ARRAY = ArrayTypeName.of(PARCELABLE);

    public static final ParameterizedTypeName PARCELABLE_ARRAY_LIST = ParameterizedTypeName.get(ClassName.get(ArrayList.class), PARCELABLE);

    public static final ParameterizedTypeName INTEGER_ARRAY_LIST = ParameterizedTypeName.get(ClassName.get(ArrayList.class), ClassName.get(Integer.class));

    public static final ParameterizedTypeName STRING_ARRAY_LIST = ParameterizedTypeName.get(ClassName.get(ArrayList.class), STRING);

    public static final ParameterizedTypeName CHARSEQUENCE_ARRAY_LIST = ParameterizedTypeName.get(ClassName.get(ArrayList.class), CHARSEQUENCE);

    public static final ClassName SERIALIZABLE = ClassName.get(Serializable.class);

    public static final ArrayTypeName BOOLEAN_ARRAY = ArrayTypeName.of(BOOLEAN);

    public static final ArrayTypeName BYTE_ARRAY = ArrayTypeName.of(BYTE);

    public static final ArrayTypeName SHORT_ARRAY = ArrayTypeName.of(SHORT);

    public static final ArrayTypeName CHAR_ARRAY = ArrayTypeName.of(CHAR);

    public static final ArrayTypeName INT_ARRAY = ArrayTypeName.of(INT);

    public static final ArrayTypeName LONG_ARRAY = ArrayTypeName.of(LONG);

    public static final ArrayTypeName FLOAT_ARRAY = ArrayTypeName.of(FLOAT);

    public static final ArrayTypeName DOUBLE_ARRAY = ArrayTypeName.of(DOUBLE);

    public static final ArrayTypeName STRING_ARRAY = ArrayTypeName.of(STRING);

    public static final ArrayTypeName CHARSEQUENCE_ARRAY = ArrayTypeName.of(CHARSEQUENCE);

    public static final ClassName BUNDLE = ClassName.get("android.os", "Bundle");

    public static final ClassName IBINDER = ClassName.get("android.os", "IBinder");
}
