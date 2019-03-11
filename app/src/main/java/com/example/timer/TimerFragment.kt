package com.example.timer

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.timer.views.TimerView
import kotlin.properties.Delegates

class TimerFragment : Fragment() {
    private var fragmentContainerID: Int by Delegates.notNull()

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var timerView: TimerView
    private lateinit var floatingActionButtonPlay: FloatingActionButton
    private lateinit var floatingActionButtonReset: FloatingActionButton
    private lateinit var floatingActionButtonClear: FloatingActionButton
    private lateinit var floatingActionButtonPause: FloatingActionButton

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentContainerID = container!!.id
        return inflater.inflate(R.layout.fragment_timer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(view.context.applicationContext)
        timerView = view.findViewById(R.id.textViewTime)
        floatingActionButtonPlay= view.findViewById(R.id.floatingActionButtonPlay)
        floatingActionButtonReset = view.findViewById(R.id.floatingActionButtonReset)
        floatingActionButtonClear = view.findViewById(R.id.floatingActionButtonClear)
        floatingActionButtonPause = view.findViewById(R.id.floatingActionButtonPause)

        val totalSeconds = readTotalSeconds(sharedPreferences)
        val previousElapsedSeconds = readPreviousElapsedSeconds(sharedPreferences)

        floatingActionButtonPlay.setOnClickListener {
            play(sharedPreferences)
            timerView.play {
                floatingActionButtonPause.hide()
                floatingActionButtonReset.show()
                floatingActionButtonClear.show()
                writeIsRunning(sharedPreferences, false)
                writeIsFinished(sharedPreferences, true)
            }
            floatingActionButtonPlay.hide(object : FloatingActionButton.OnVisibilityChangedListener() {
                override fun onHidden(fab: FloatingActionButton?) {
                    super.onHidden(fab)
                    floatingActionButtonPause.show()
                }
            })
            floatingActionButtonReset.hide()
            floatingActionButtonClear.hide()
        }

        floatingActionButtonReset.setOnClickListener {
            reset(sharedPreferences)
            timerView.seconds = totalSeconds
            floatingActionButtonPlay.show()
        }

        floatingActionButtonClear.setOnClickListener {
            clearSetup(sharedPreferences)
            activity
                ?.supportFragmentManager
                ?.beginTransaction()
                ?.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up)
                ?.replace(fragmentContainerID, SetupFragment())
                ?.commit()
        }

        floatingActionButtonPause.setOnClickListener {
            pause(sharedPreferences)
            timerView.pause()
            floatingActionButtonPause.hide(object : FloatingActionButton.OnVisibilityChangedListener() {
                override fun onHidden(fab: FloatingActionButton?) {
                    super.onHidden(fab)
                    floatingActionButtonPlay.show()
                    floatingActionButtonReset.show()
                    floatingActionButtonClear.show()
                }
            })
        }

        if (readIsFinished(sharedPreferences)) {
            timerView.seconds = 0
            floatingActionButtonPlay.hide()
            floatingActionButtonPause.hide()
            floatingActionButtonReset.show()
            floatingActionButtonClear.show()
        } else {
            if (readIsRunning(sharedPreferences)) {
                val startTimeMilliseconds = readStartTimeMilliseconds(sharedPreferences)
                timerView.seconds =
                    totalSeconds - previousElapsedSeconds - ((System.currentTimeMillis() - startTimeMilliseconds) / 1000).toInt()
                floatingActionButtonPlay.callOnClick()
            } else {
                timerView.seconds = totalSeconds - previousElapsedSeconds
            }
        }
    }

    override fun onStop() {
        timerView.pause()
        super.onStop()
    }
}
