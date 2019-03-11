package com.example.timer

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.run {
            if (findFragmentById(R.id.fragment_container) == null) {
                val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
                beginTransaction()
                    .add(R.id.fragment_container,
                        if (readIsInSetup(sharedPreferences)) {
                            SetupFragment()
                        } else {
                            TimerFragment()
                        }
                    )
                    .commit()

            }
        }
    }
}
