package ru.gui.components.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import ru.gui.R


class Footer : Fragment() {

    open var finger: Button?=null


    @Override
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.footer,container,true)

    }

    @Override
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        println("okok")

/*        view.findViewById<Button>(R.id.showFingerScreen) .setOnClickListener {
            println(it)
            println("okok1")
        }*/

       //fingerPrintValidation.fingerClick(view.findViewById<Button>(R.id.finger),this.context)
    }


}