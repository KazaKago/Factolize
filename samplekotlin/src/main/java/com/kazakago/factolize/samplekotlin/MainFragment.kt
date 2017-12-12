package com.kazakago.factolize.samplekotlin

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.kazakago.factolize.Factory
import kotlinx.android.synthetic.main.fragment_main.*

@Factory
class MainFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MainFragmentFactory.injectArgument(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val goToSubActivityButton = view.findViewById<Button>(R.id.goToSubActivityButton)
        goToSubActivityButton.setOnClickListener {
            val intValue = intEditText.text.toString().toIntOrNull() ?: 0
            val stringValue = stringEditText.text.toString()

            goSubActivity(intValue, stringValue)
        }
    }

    private fun goSubActivity(intValue: Int, stringValue: String) {
        val intent = SubActivityFactory.createIntent(activity!!, intValue, stringValue)
        startActivity(intent)
    }

}
