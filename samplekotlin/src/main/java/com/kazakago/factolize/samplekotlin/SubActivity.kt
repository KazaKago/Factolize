package com.kazakago.factolize.samplekotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.kazakago.factolize.Factory
import com.kazakago.factolize.FactoryParam

@Factory
class SubActivity : AppCompatActivity() {

    @FactoryParam
    var intValue: Int = 0
    @FactoryParam
    var stringValue: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub)
        SubActivityFactory.injectArgument(this)

        if (savedInstanceState == null) {
            replaceSubFragment()
        }
    }

    private fun replaceSubFragment() {
        val fragment = SubFragmentFactory.createInstance(intValue, stringValue)

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        fragmentTransaction.commit()
    }

}
