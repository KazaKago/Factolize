package com.kazakago.activityinitializer.samplejava;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Size;
import android.util.SizeF;
import android.util.SparseArray;

import com.kazakago.activityinitializer.Factory;
import com.kazakago.activityinitializer.FactoryParam;

import java.io.Serializable;
import java.util.ArrayList;

@Factory
public class MainActivity extends AppCompatActivity {

    //ArrayList<Class>
    @FactoryParam
    ArrayList<Integer> _integerArrayList;
    @FactoryParam
    ArrayList<String> _stringArrayList;
    @FactoryParam
    ArrayList<CharSequence> _charSequenceArrayList;

    //ArrayList<Interface>
    @FactoryParam
    ArrayList<Parcelable> _parcelableArrayList;

    //Class[]
    @FactoryParam
    String[] _stringArray;
    @FactoryParam
    CharSequence[] charSequenceArray;

    //Interface[]
    @FactoryParam
    Parcelable[] _parcelableArray;

    //Class
    @FactoryParam
    String _string;
    @FactoryParam
    CharSequence _charSequence;
    @FactoryParam
    Bundle _bundle;

    //Primitive[]
    @FactoryParam
    char[] _charArray;
    @FactoryParam
    byte[] _byteArray;
    @FactoryParam
    short[] _shortArray;
    @FactoryParam
    int[] _intArray;
    @FactoryParam
    long[] _longArray;
    @FactoryParam
    float[] _floatArray;
    @FactoryParam
    double[] _doubleArray;
    @FactoryParam
    boolean[] _booleanArray;

    //Primitive
    @FactoryParam
    char _char;
    @FactoryParam
    byte _byte;
    @FactoryParam
    short _short;
    @FactoryParam
    int _int;
    @FactoryParam
    long _long;
    @FactoryParam
    float _float;
    @FactoryParam
    double _double;
    @FactoryParam
    boolean _boolean;

    //Interface
    @FactoryParam
    Serializable _serializable;
    @FactoryParam
    Parcelable _parcelable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = MainActivityFactory.createIntent(this, 0, "hugahuga");
        startActivity(intent);
        MainFragment fragment = MainFragmentFactory.createInstance(0, "hugahuga");
    }

    @NonNull
    public static MainFragment createInstance(@NonNull ArrayList<Integer> _integerArrayList,
                                              @NonNull ArrayList<String> _stringArrayList,
                                              @NonNull ArrayList<CharSequence> _charSequenceArrayList,
                                              @NonNull ArrayList<Parcelable> _parcelableArrayList,
                                              @NonNull SparseArray<Parcelable> _parcelableSparseArray, @NonNull String[] _stringArray,
                                              @NonNull CharSequence[] _charSequenceArray, @NonNull Parcelable[] _parcelableArray,
                                              @NonNull String _string, @NonNull CharSequence _charSequence, @NonNull Size _size,
                                              @NonNull SizeF _sizeF, @NonNull Bundle _bundle, @NonNull Binder _iBinder,
                                              @NonNull char[] _charArray, @NonNull boolean[] _booleanArray, @NonNull int[] _intArray,
                                              @NonNull long[] _longArray, @NonNull float[] _floatArray, @NonNull double[] _doubleArray,
                                              @NonNull byte[] _byteArray, @NonNull short[] shortArray, int _int, long _long, float _float,
                                              double _double, short _short, char _char, boolean _boolean, byte _byte,
                                              @NonNull Serializable _serializable, @NonNull Parcelable _parcelable) {
        MainFragment fragment = new MainFragment();
        Bundle arguments = new Bundle();
        arguments.putIntegerArrayList("_integerArrayList", _integerArrayList);
        arguments.putStringArrayList("_stringArrayList", _stringArrayList);
        arguments.putCharSequenceArrayList("_charSequenceArrayList", _charSequenceArrayList);
        arguments.putParcelableArrayList("_parcelableArrayList", _parcelableArrayList);
        arguments.putSparseParcelableArray("_parcelableSparseArray", _parcelableSparseArray);
        arguments.putStringArray("_stringArray", _stringArray);
        arguments.putCharSequenceArray("_charSequenceArray", _charSequenceArray);
        arguments.putParcelableArray("_parcelableArray", _parcelableArray);
        arguments.putString("_string", _string);
        arguments.putCharSequence("_charSequence", _charSequence);
        arguments.putSize("_size", _size);
        arguments.putSizeF("_sizeF", _sizeF);
        arguments.putBundle("_bundle", _bundle);
        arguments.putBinder("_iBinder", _iBinder);
        arguments.putCharArray("_charArray", _charArray);
        arguments.putBooleanArray("_booleanArray", _booleanArray);
        arguments.putIntArray("_intArray", _intArray);
        arguments.putLongArray("_longArray", _longArray);
        arguments.putFloatArray("_floatArray", _floatArray);
        arguments.putDoubleArray("_doubleArray", _doubleArray);
        arguments.putByteArray("_byteArray", _byteArray);
        arguments.putShortArray("shortArray", shortArray);
        arguments.putInt("_int", _int);
        arguments.putLong("_long", _long);
        arguments.putFloat("_float", _float);
        arguments.putDouble("_double", _double);
        arguments.putShort("_short", _short);
        arguments.putChar("_char", _char);
        arguments.putBoolean("_boolean", _boolean);
        arguments.putByte("_byte", _byte);
        arguments.putSerializable("_serializable", _serializable);
        arguments.putParcelable("_parcelable", _parcelable);
        fragment.setArguments(arguments);
        return fragment;
    }

}
