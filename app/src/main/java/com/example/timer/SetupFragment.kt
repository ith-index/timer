package com.example.timer

import android.os.Bundle
import android.preference.PreferenceManager
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.timer.views.NumericKeypadView
import com.example.timer.views.TimerInputView
import kotlin.properties.Delegates

class SetupFragment : Fragment() {
    private var fragmentContainerID: Int by Delegates.notNull()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentContainerID = container!!.id
        return inflater.inflate(R.layout.fragment_setup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val timerInputView: TimerInputView = view.findViewById(R.id.textViewTime)
        val floatingActionButtonPlay: FloatingActionButton = view.findViewById(R.id.floatingActionButtonPlay)
        val numericKeypadView: NumericKeypadView = view.findViewById(R.id.numericKeypadView)

        timerInputView.onValueChangeListener = {
            if (it == 0) {
                floatingActionButtonPlay.hide()
            } else {
                floatingActionButtonPlay.show()
            }
        }

        numericKeypadView.run {
            onKeyClickListener = {
                timerInputView.push(it)
            }
            onKeyBackspaceClickListener = {
                timerInputView.pop()
            }
        }

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(view.context.applicationContext)
        floatingActionButtonPlay.setOnClickListener {
            establishSetup(sharedPreferences, timerInputView.getTotalSeconds())
            activity
                ?.supportFragmentManager
                ?.beginTransaction()
                ?.setCustomAnimations(R.anim.slide_in_down, R.anim.slide_out_down)
                ?.replace(fragmentContainerID, TimerFragment())
                ?.commit()
        }
    }
}
