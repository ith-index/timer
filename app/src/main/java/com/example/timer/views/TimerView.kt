package com.example.timer.views

import android.content.Context
import android.graphics.Canvas
import android.os.CountDownTimer
import android.support.v4.content.ContextCompat
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet
import com.example.timer.R

class TimerView : AppCompatTextView {
    var seconds: Int = 0
        set(value) {
            field = value
            invalidate()
        }
    var countDownTimer: CountDownTimer? = null

    constructor(context: Context): super(context)
    constructor(context: Context, attributeSet: AttributeSet?): super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet?, defaultStyle: Int): super(context, attributeSet, defaultStyle)

    fun play(onFinishListener: () -> Unit) {
        countDownTimer = object : CountDownTimer((seconds * 1000).toLong(), 1000) {
            override fun onTick(millisecondsLeft: Long) {
                seconds = (millisecondsLeft / 1000).toInt()
                if (seconds == 0) {
                    onFinishListener()
                }
            }

            override fun onFinish() {}
        }.apply { start() }
    }

    fun pause() {
        countDownTimer?.cancel()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (seconds == 0) {
            setTextColor(ContextCompat.getColor(context, R.color.colorAccent))
        } else {
            setTextColor(ContextCompat.getColor(context, R.color.colorPrimary))
        }
        val remainderSeconds = seconds % 60
        val totalMinutes = seconds / 60
        val remainderMinutes = totalMinutes % 60
        val totalHours = totalMinutes / 60
        val text = "${totalHours.toString().padStart(2, '0')}:${remainderMinutes.toString().padStart(2, '0')}:${remainderSeconds.toString().padStart(2, '0')}"
        setText(text)
    }
}