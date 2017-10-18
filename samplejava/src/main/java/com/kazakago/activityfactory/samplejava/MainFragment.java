package com.kazakago.activityfactory.samplejava;

import android.os.Binder;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Size;
import android.util.SizeF;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kazakago.activityfactory.Factory;
import com.kazakago.activityfactory.FactoryParam;

import java.io.Serializable;
import java.util.ArrayList;

@Factory
public class MainFragment extends Fragment {

    //ArrayList<Class>
    @FactoryParam(required = false)
    ArrayList<Integer> _integerArrayList;
    @FactoryParam(required = false)
    ArrayList<String> _stringArrayList;
    @FactoryParam(required = false)
    ArrayList<CharSequence> _charSequenceArrayList;

    //ArrayList<Interface>
    @FactoryParam(required = false)
    ArrayList<Parcelable> _parcelableArrayList;

    //SparseArray<Interface>
    @FactoryParam(required = false)
    SparseArray<Parcelable> _parcelableSparseArray;

    //Class[]
    @FactoryParam(required = false)
    String[] _stringArray;
    @FactoryParam(required = false)
    CharSequence[] _charSequenceArray;

    //Interface[]
    @FactoryParam(required = false)
    Parcelable[] _parcelableArray;

    //Class
    @FactoryParam(required = false)
    String _string;
    @FactoryParam(required = false)
    CharSequence _charSequence;
    @FactoryParam(required = false)
    Size _size;
    @FactoryParam(required = false)
    SizeF _sizeF;
    @FactoryParam(required = false)
    Bundle _bundle;
    @FactoryParam(required = false)
    Binder _iBinder;

    //Primitive[]
    @FactoryParam(required = false)
    char[] _charArray;
    @FactoryParam(required = false)
    boolean[] _booleanArray;
    @FactoryParam(required = false)
    int[] _intArray;
    @FactoryParam(required = false)
    long[] _longArray;
    @FactoryParam(required = false)
    float[] _floatArray;
    @FactoryParam(required = false)
    double[] _doubleArray;
    @FactoryParam(required = false)
    byte[] _byteArray;
    @FactoryParam(required = false)
    short[] shortArray;

    //Primitive
    @FactoryParam(required = false)
    char _char;
    @FactoryParam(required = false)
    byte _byte;
    @FactoryParam(required = false)
    short _short;
    @FactoryParam(required = false)
    int _int;
    @FactoryParam(required = false)
    long _long;
    @FactoryParam(required = false)
    float _float;
    @FactoryParam(required = false)
    double _double;
    @FactoryParam(required = false)
    boolean _boolean;

    //Interface
    @FactoryParam(required = false)
    Parcelable _parcelable;
    @FactoryParam(required = false)
    Serializable _serializable;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainFragmentFactory.injectArgument(this, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

}
