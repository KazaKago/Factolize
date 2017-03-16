package com.kazakago.activityinitializer.constants;

import com.squareup.javapoet.ClassName;

/**
 * Type Constants
 *
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

}
