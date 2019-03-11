package com.example.timer.views

import android.content.Context
import android.util.AttributeSet
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageButton
import com.example.timer.R

class NumericKeypadView : FrameLayout {
    var onKeyClickListener: ((Int) -> Unit)? = null
    var onKeyBackspaceClickListener: (() -> Unit)? = null

    constructor(context: Context): super(context)
    constructor(context: Context, attributeSet: AttributeSet?): super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet?, defaultStyle: Int): super(context, attributeSet, defaultStyle)

    init {
        inflate(context, R.layout.view_numeric_keypad, this)
        listOf(
            R.id.buttonKey0,
            R.id.buttonKey1,
            R.id.buttonKey2,
            R.id.buttonKey3,
            R.id.buttonKey4,
            R.id.buttonKey5,
            R.id.buttonKey6,
            R.id.buttonKey7,
            R.id.buttonKey8,
            R.id.buttonKey9
        ).map {
            findViewById<Button>(it)
        }.forEachIndexed {
            index, button -> button.setOnClickListener {
                onKeyClick(index)
            }
        }
        findViewById<ImageButton>(R.id.buttonKeyBackspace).setOnClickListener {
            onKeyBackspaceClick()
        }
    }

    private fun onKeyClick(number: Int) {
        onKeyClickListener?.invoke(number)
    }

    private fun onKeyBackspaceClick() {
        onKeyBackspaceClickListener?.invoke()
    }

}