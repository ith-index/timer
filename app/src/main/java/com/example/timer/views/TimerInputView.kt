package com.example.timer.views

import android.content.Context
import android.graphics.Canvas
import android.os.Bundle
import android.os.Parcelable
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet

private const val KEY_SUPER = "KEY_SUPER"
private const val KEY_INPUT_ARRAY = "KEY_INPUT_ARRAY"

class TimerInputView : AppCompatTextView {
    var onValueChangeListener: ((Int) -> Unit)? = null
        set(value) {
            field = value
            field?.invoke(getTotalSeconds())
        }
    private var inputList = mutableListOf<Int>()

    constructor(context: Context): super(context)
    constructor(context: Context, attributeSet: AttributeSet?): super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet?, defaultStyle: Int): super(context, attributeSet, defaultStyle)

    fun push(value: Int) {
        if (inputList.size != 0 || value != 0) {
            if (inputList.size < 6) {
                inputList.add(value)
                onValueChangeListener?.invoke(getTotalSeconds())
                invalidate()
            }
        }
    }

    fun pop() {
        if (inputList.size > 0) {
            inputList.removeAt(inputList.lastIndex)
            onValueChangeListener?.invoke(getTotalSeconds())
            invalidate()
        }
    }

    fun getTotalSeconds(): Int =
        (inputList.reversed() + List(6 - inputList.size, { 0 })).run {
            val hours = get(5) * 10 + get(4)
            val minutes = get(3) * 10 + get(2)
            val seconds = get(1) * 10 + get(0)
            return hours * 60 * 60 + minutes * 60 + seconds
        }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        (inputList.reversed() + List(6 - inputList.size, { 0 })).run {
            val text = "${get(5)}${get(4)}h ${get(3)}${get(2)}m ${get(1)}${get(0)}s"
            setText(text)
        }
    }


    override fun onRestoreInstanceState(state: Parcelable?) {
        (state as? Bundle)?.run {
            getIntArray(KEY_INPUT_ARRAY)?.forEach {
                inputList.add(it)
            }
            onValueChangeListener?.invoke(getTotalSeconds())
            super.onRestoreInstanceState(getParcelable(KEY_SUPER))
        }
    }

    override fun onSaveInstanceState(): Parcelable? =
        Bundle().apply {
            putIntArray(KEY_INPUT_ARRAY, IntArray(inputList.size).apply {
                inputList.forEachIndexed {
                    index, value -> this[index] = value
                }
            })
            putParcelable(KEY_SUPER, super.onSaveInstanceState())
        }
}