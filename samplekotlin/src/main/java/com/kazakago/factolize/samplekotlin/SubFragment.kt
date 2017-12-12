package com.kazakago.factolize.samplekotlin

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kazakago.factolize.Factory
import com.kazakago.factolize.FactoryParam
import kotlinx.android.synthetic.main.fragment_sub.*

@Factory
class SubFragment : Fragment() {

    @FactoryParam
    var intValue: Int = 0
    @FactoryParam
    var stringValue: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SubFragmentFactory.injectArgument(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sub, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        intValueTextView.text = "intValue : " + intValue
        stringValueTextView.text = "stringValue : " + stringValue
    }
}
