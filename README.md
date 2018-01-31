![./artwork/logo.png](./artwork/logo.png)

Factolize
====

[![Download](https://api.bintray.com/packages/kazakago/maven/factolize/images/download.svg)](https://bintray.com/kazakago/maven/factolize/_latestVersion)
[![Build Status](https://www.bitrise.io/app/5e61361019dd0f7c/status.svg?token=oJRivuoE4u64orV4wDsBHg)](https://www.bitrise.io/app/5e61361019dd0f7c)
[![license](https://img.shields.io/github/license/kazakago/factolize.svg)](LICENSE.md)

Generate Android's Activity &amp; Fragment's factory method that can pass arguments easily and safely.

## Requirement

- Android 4.0.3 (API 15) or later

## Install

Add the following gradle dependency exchanging x.x.x for the latest release.  

### Java

```groovy
dependencies {
    implementation 'com.kazakago.factolize:factolize:x.x.x'
    annotationProcessor 'com.kazakago.factolize:factolize-processor:x.x.x'
}
```

### Kotlin

```groovy
apply plugin: 'kotlin-kapt'

dependencies {
    implementation 'com.kazakago.factolize:factolize:x.x.x'
    kapt 'com.kazakago.factolize:factolize-processor:x.x.x'
}
```

## Usage

add `@Factory`　annotation to Activity / Fragment class you want to generate Factory method.  
Also add `@FactoryParam` annotation to the field variable you want to specify as an argument.  

```java
@Factory // `@Factory` is nessesary to generate Factory class & method.
public class SubActivity extends Activity {

    @FactoryParam // `@FactoryParam` is nessesary to add factory method arguments.
    String param;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        // This method assigns arguments to field variables.
        SubActivityFactory.injectArgument(this);
        
        System.out.print(param);　// print "your parameter."
    }

}
```

When building, `[ACTIVITY_OR_FRAGMENT_NAME] + Factory` class is auto generated based on the class with `@Factory` annotation.  

```java
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Button goToSubActivityButton = view.findViewById(R.id.goToSubActivityButton);
        goToSubActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // `SubActivityFactory` class is auto generated.
                Intent intent = SubActivityFactory.createIntent(getActivity(), "your parameter.");
                startActivity(intent);
            }
        });
    }

}
```

Refer to the sample module ([Java](https://github.com/KazaKago/Factolize/tree/master/samplejava) & [Kotlin](https://github.com/KazaKago/Factolize/tree/master/samplekotlin)) for details.  

## Advanced

### Required flag & Overload factory method.

If you want to make an argument optional, you can make it arbitrary by describing `@FactoryParam(requred = false)`. This default is true.  

```java
@Factory
public class SubActivity extends Activity {

    @FactoryParam
    String param1; //this parameter is required. (added @NonNull)
    @FactoryParam(required = true)
    String param2; //this parameter is required. (added @NonNull)
    @FactoryParam(required = false)
    String param3; //this parameter is optional. (added @Nullable)

}
```

If you specify `@FactoryParam(required = false)`, an overloaded method will be added.  

```java
Intent intent = SubActivityFactory.createIntent(getActivity(), "parameter1", "parameter2", "parameter3"); // with optional argments
Intent intent = SubActivityFactory.createIntent(getActivity(), "parameter1", "parameter2"); // only required argments
```

### Handling Activity / Fragment lifecycle & recreate behavior.

If you consider Andifid's Lifecycle, pass `savedInstanceState` to the `injectArgment()` method argument.  
If this is specified, if Activity / Fragment recreates, it will not assign to the parameter.  

In this case, implementing the saving process of the variable across the recreation of Activity / Fragment. [More details](https://developer.android.com/guide/components/activities/activity-lifecycle.html#saras).  

```java
@Factory
public class SubActivity extends Activity {

    @FactoryParam
    String param;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        SubActivityFactory.injectArgument(this, savedInstanceState); // pass savedInstanceState
    }

}
```

This behavior is the same as the following code.  

```java
if (savedInstanceState == null) {
    SubActivityFactory.injectArgument(this);
}
```

## Supported Type

- Interface
  - Parcelable
  - Serializable
- Interface[]
  - Parcelable[]
- Class
  - String
  - CharSequence
  - Size
  - SizeF
  - IBinder
  - Bundle
  - ArrayList\<Integer\>
  - ArrayList\<String\>
  - ArrayList\<CharSequence\>
  - ArrayList\<Parcelable\>
  - SparseArray\<Parcelable\>
- Class[]
  - String[]
  - CharSequence[]
- Primitive
  - Char
  - Byte
  - Short
  - Int
  - Long
  - Float
  - Double
  - Boolean
- Primitive[]
  - Char[]
  - Byte[]
  - Short[]
  - Int[]
  - Long[]
  - Float[]
  - Double[]
  - Boolean[]

This is the same as Android's Bundle specification. [More details](https://developer.android.com/reference/android/os/Bundle.html).  

## License
MIT License

Copyright (c) 2017 KazaKago

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
