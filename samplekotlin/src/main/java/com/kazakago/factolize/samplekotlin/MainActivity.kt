package com.kazakago.factolize.samplekotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.kazakago.factolize.Factory

@Factory
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        MainActivityFactory.injectArgument(this)

        if (savedInstanceState == null) {
            replaceMainFragment()
        }
    }

    private fun replaceMainFragment() {
        val fragment = MainFragmentFactory.createInstance()

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        fragmentTransaction.commit()
    }

}
