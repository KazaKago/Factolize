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

/**
 * Created by tamura_k on 2017/03/16.
 */
@Factory
public class MainFragment extends Fragment {

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

    //SparseArray<Interface>
    @FactoryParam
    SparseArray<Parcelable> _parcelableSparseArray;

    //Class[]
    @FactoryParam
    String[] _stringArray;
    @FactoryParam
    CharSequence[] _charSequenceArray;

    //Interface[]
    @FactoryParam
    Parcelable[] _parcelableArray;

    //Class
    @FactoryParam
    String _string;
    @FactoryParam
    CharSequence _charSequence;
    @FactoryParam
    Size _size;
    @FactoryParam
    SizeF _sizeF;
    @FactoryParam
    Bundle _bundle;
    @FactoryParam
    Binder _iBinder;

    //Primitive[]
    @FactoryParam
    char[] _charArray;
    @FactoryParam
    boolean[] _booleanArray;
    @FactoryParam
    int[] _intArray;
    @FactoryParam
    long[] _longArray;
    @FactoryParam
    float[] _floatArray;
    @FactoryParam
    double[] _doubleArray;
    @FactoryParam
    byte[] _byteArray;
    @FactoryParam
    short[] shortArray;

    //Primitive
    @FactoryParam
    int _int;
    @FactoryParam
    long _long;
    @FactoryParam
    float _float;
    @FactoryParam
    double _double;
    @FactoryParam
    short _short;
    @FactoryParam
    char _char;
    @FactoryParam
    boolean _boolean;
    @FactoryParam
    byte _byte;

    @FactoryParam
    Parcelable _parcelable;
    @FactoryParam
    Serializable _serializable;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        return view;
    }

}
