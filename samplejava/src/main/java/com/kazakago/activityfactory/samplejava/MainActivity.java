package com.kazakago.activityfactory.samplejava;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;

import com.kazakago.activityfactory.Factory;
import com.kazakago.activityfactory.FactoryParam;

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
    Parcelable _parcelable;
    @FactoryParam
    Serializable _serializable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainActivityFactory.injectArgument(this, savedInstanceState);

//        Intent intent = MainActivityFactory.createIntent(this, 0, "hugahuga");
//        MainFragment fragment = MainFragmentFactory.createInstance(0, "hugahuga");
    }

}
